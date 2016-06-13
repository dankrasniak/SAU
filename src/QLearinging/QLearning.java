package QLearinging;


import MLPerceptron.CellType;
import MLPerceptron.MLPerceptron;
import MLPerceptron.MLPerceptronImpl;
import MLPerceptron.TeachingPolicies.TeachingPolicy;
import MLPerceptron.Utils.ErrorApproximator;
import MLPerceptron.Utils.Vector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.*;

public class QLearning {

    public QLearning(final int[] sizes,
                     final CellType[] cellTypes,
                     final TeachingPolicy teachingPolicy,
                     final int HORIZON_LENGTH,
                     final int TIMES_TO_REWRITE_HISTORY,
                     final int TIMES_TO_PREPARE_BETTER_SOLUTION,
                     final double GAMMA,
                     final double SIGMA_MIN,
                     final double SIGMA_START,
                     final Model model) {


        // Prepare Logger
        try {
            FileHandler fh = new FileHandler("MyLogFile.log");
            logger.addHandler(fh);
            fh.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    return record.getMessage() + "\n";
                }
            });
            logger.setUseParentHandlers(false);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }

        this.HORIZON_LENGTH = HORIZON_LENGTH;
        this.TIMES_TO_REWRITE_HISTORY = TIMES_TO_REWRITE_HISTORY;
        this.TIMES_TO_PREPARE_BETTER_SOLUTION = TIMES_TO_PREPARE_BETTER_SOLUTION;
        this.GAMMA = GAMMA;
        this.SIGMA_MIN = SIGMA_MIN;
        this.SIGMA_START = SIGMA_START;
        Decisions = new int[HORIZON_LENGTH]; // TODO MORE
        this._model = model;

        Initialize(sizes, cellTypes, teachingPolicy);
    }


    /*-----Public methods------*/

    public int AdviseAction(final Vector state) {
        final double decisionValue = CalculateValue(state, Decisions);

        PrepareABetterDecisionsList(state, Decisions, decisionValue); // TODO Not sure if finished parameters

        // LOG
//        String toLog =
//                "State: " + state.toString() + "\n" +
//                "Decision: " + Decisions[0] + "\n" +
//                "DecisionValue: " + decisionValue + "\n" +
//                "-------";
//        logger.info(toLog);

        records.add(new Record(state, Decisions));

        return Decisions[0];
    }

    public void ThisHappened(final Vector nextState) {
        UpdateToTheNextIteration();

        RewriteHistoryeh();
    }


    /*-----Private methods------*/

    private void Initialize(final int[] sizes,
                            final CellType[] cellTypes,
                            final TeachingPolicy teachingPolicy) {
        QApproximator = new MLPerceptronImpl(sizes, cellTypes, teachingPolicy);
//        throw new NotImplementedException(); // TODO
    }

    private double CalculateValue(final Vector state, final int[] decisions) {
        double gammai = 1;
        double result = 0.0;
        Vector[] nextStates = _model.stateFunction(state, decisions); //new Vector[HORIZON_LENGTH];

        for (int i = 0; i < HORIZON_LENGTH; i++) {
            result += gammai * _model.getReward(nextStates[i]);
            gammai *= GAMMA;
        }
        double approx = QApproximator.Approximate(
                TweakInput(
                        new Record(
                                nextStates[HORIZON_LENGTH-1],
                                new int[]{decisions[HORIZON_LENGTH-1]})))
                .Get(0); // TODO MEH
        result += gammai * approx;
// LOG
//        String toLog =
//                "State: " + nextStates[HORIZON_LENGTH-1].toString() + "\n" +
//                        "Decision: " + decisions[HORIZON_LENGTH-1] + "\n" +
//                        "DecisionValue: " + approx + "\n" +
//                        "-------";
//        logger.info(toLog);

        return result; // TODO SAME AS V APPROXIMATOR // Update: Almost
    }

    private double PrepareABetterDecisionsList(final Vector state,
                                             int[] decisions,
                                             double currentDecisionsValue) {
        phi = 0;
        timesITried = 0;
        currentSigma = SIGMA_START;

        while (timesITried < TIMES_TO_PREPARE_BETTER_SOLUTION || currentSigma < SIGMA_MIN) {
            ++timesITried;
            currentDecisionsValue = ModifyDecisionsList(state, decisions, currentDecisionsValue);
        }

        return currentDecisionsValue;
    }

    private double ModifyDecisionsList(final Vector state, int[] decisions, double currentDecisionValue) {
        int[] modifiedDecisions = decisions.clone();
        double sigmaDiscount;
        int tmp;

        // Modify the Decisions List
        // 8 is the maximal value a decision can have
        // 2 because the mix/max value the normal gradient can return will then be -8/8 // TODO
        for (int i = 0; i < HORIZON_LENGTH; i++) {
            sigmaDiscount = 1.0 + (double) ((i + 1) / HORIZON_LENGTH);
            tmp = ( modifiedDecisions[i] + (int) (Sampler.sampleFromNormal(0, currentSigma) * sigmaDiscount) ) % 8; // 1
            modifiedDecisions[i] = (tmp < 0) ? (7 - tmp) : tmp;
        }

        // If the value of the state with new action vector is bigger, replace the previous action vector
        double newDecisionsValue = CalculateValue(state, modifiedDecisions);
        if (newDecisionsValue > currentDecisionValue) {
            System.arraycopy(modifiedDecisions, 0, decisions, 0, HORIZON_LENGTH);
            currentDecisionValue = newDecisionsValue;
            ++phi;
        }

        UpdateSigma();
        return currentDecisionValue;
    }

    private void UpdateSigma() {
        if (timesITried % M != 0)
            return;

        if (phi / M < 0.2) {
            currentSigma *= C1;
        }
        else if (phi / M > 0.2)
        {
            currentSigma *= C2;
        }
        phi = 0; // if currentSigma == 0.2
    }

    private void UpdateToTheNextIteration() {
        final int HORIZON_LENGTH_M1 = HORIZON_LENGTH - 1;
        for (int i = 0; i < HORIZON_LENGTH_M1;) {
            Decisions[i] = Decisions[++i];
        }
    }

    private void RewriteHistoryeh() {

        for (int i = 0; i < TIMES_TO_REWRITE_HISTORY; i++) {
            final Record record = records.get(random.nextInt(records.size()));

            double estimatedValue = CalculateValue(record.state, record.actions);

            estimatedValue = PrepareABetterDecisionsList(record.state, record.actions, estimatedValue);

            final double approximatedValue = QApproximator.Approximate(TweakInput(record)).Get(0);

            final Vector errorGrad = ErrorApproximator.GetError(approximatedValue, estimatedValue); //final double errorGrad = approximatedValue - estimatedValue;

            QApproximator.BackPropagate(errorGrad);
            QApproximator.ApplyWeights();
        }
    }

    private Vector TweakInput(final Record record) {
        final int STATE_SIZE = record.state.GetLength();
//        double[] result = new double[STATE_SIZE + 1];
        double[] result = new double[STATE_SIZE + 2];

        for (int i = 0; i < STATE_SIZE - 2; i++) {
            result[i] = record.state.Get(i);
        }
        //result[STATE_SIZE] = (double) record.actions[0] / 7; // TODO

        {// TMP DELETE THIS
            double ax, ay;
            switch (record.actions[0]) {
                case 0:  ax=1;ay=0;
                    break;
                case 1:  ax=0.70710678;ay=0.70710678;
                    break;
                case 2:  ax=0;ay=1;
                    break;
                case 3:  ax=-0.70710678;ay=0.70710678;
                    break;
                case 4:  ax=-1;ay=0;
                    break;
                case 5:  ax=-0.70710678;ay=-0.70710678;
                    break;
                case 6:  ax=0;ay=-1;
                    break;
                case 7:  ax=0.70710678;ay=-0.70710678;
                    break;
                default: ax=0;ay=0;
                    break;
            }
            result[STATE_SIZE-1] = ax;
            result[STATE_SIZE] = ay;

            result[0] = result[0] / 2;
            result[1] = result[1] / 3;
        }

        return new Vector(result);
    }


    /*-----Variables------*/

    private final int HORIZON_LENGTH;
    private final int TIMES_TO_REWRITE_HISTORY;
    private final int TIMES_TO_PREPARE_BETTER_SOLUTION;
    private ArrayList<Record> records = new ArrayList<>();
    private int[] Decisions;
    private final Random random = new Random();
    private MLPerceptron QApproximator;
    private final Model _model;
    private final double GAMMA;

    // Evolution Algorithm
    private int phi;
    private int timesITried;
    private double currentSigma;
    private final double SIGMA_MIN;
    private final double SIGMA_START;
    private final double M = 10;
    private final double C1 = 0.82;
    private final double C2 = 1.2;

    private static Logger logger = Logger.getLogger("MyLog");
}
