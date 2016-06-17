package QLearinging;


import MLPerceptron.CellType;
import MLPerceptron.MLPerceptron;
import MLPerceptron.MLPerceptronImpl;
import MLPerceptron.TeachingPolicies.TeachingPolicy;
import MLPerceptron.Utils.ErrorApproximator;
import MLPerceptron.Utils.Vector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class QLearning {

    public QLearning(final int[] sizes,
                     final CellType[] cellTypes,
                     final TeachingPolicy teachingPolicy,
                     final int TIMES_TO_REWRITE_HISTORY,
                     final double GAMMA,
                     final int MEMORY_LIMIT,
                     final Model model) {
        this.TIMES_TO_REWRITE_HISTORY = TIMES_TO_REWRITE_HISTORY;
        this.GAMMA = GAMMA;
        this.MEMORY_LIMIT = MEMORY_LIMIT;
        Decision = new int[1];
        this._model = model;

        Initialize(sizes, cellTypes, teachingPolicy);
    }


    /*-----Public methods------*/

    public int AdviseAction(final Vector state) {
        // Algorytm Q-Learning
        // Wylosuj a na podstawie Q
        // Wykonaj a

        State = state;

        EstimatedValue = PrepareADecision(State, Decision);
        return Decision[0];
    }

    public void ThisHappened(final Vector nextState, final double reward) {
        // Algorytm Q-Learning
        // Pobierz r_t+1 i x_t+1
        // Ucz sieć na podstawie tych danych
        // Powtarzanie

        double approximatedValue = CalculateValue(nextState, reward);

        final Vector errorGrad = ErrorApproximator.GetError(EstimatedValue, approximatedValue);
        System.out.println(EstimatedValue + " " + approximatedValue);

        // TMP
        double estimatedValue = QApproximator.Approximate(TweakInput(new Record(State, Decision[0], State, 0))).Get(0);

        QApproximator.BackPropagate(errorGrad);
        QApproximator.ApplyWeights();

        records.add(new Record(State, Decision[0], nextState, reward));
        if (records.size() > MEMORY_LIMIT)
            records.remove(0);

        RewriteHistoryeh();
    }


    /*-----Private methods------*/

    private void Initialize(final int[] sizes,
                            final CellType[] cellTypes,
                            final TeachingPolicy teachingPolicy) {
        QApproximator = new MLPerceptronImpl(sizes, cellTypes, teachingPolicy);
    }

    private double PrepareADecision(final Vector state, final int[] decision) {
        double currentDecisionsValue = 0;
        double maxValue = -1000.0;
        int checkedDecision;
        double epsilon = 0.3;
        List<Integer> decisions = new LinkedList<>();
        double[] decisionsP = new double[8];

        // Look for the biggest value
        for (checkedDecision = 0; checkedDecision < 8; ++checkedDecision) {
            currentDecisionsValue = QApproximator.Approximate(TweakInput(new Record(state, checkedDecision, state, 0))).Get(0);
            if (currentDecisionsValue > maxValue) {
                decision[0] = checkedDecision;
                maxValue = currentDecisionsValue;
            }
        }

        // Look for decisions which value is equal to the biggest value
        for (checkedDecision = 0; checkedDecision < 8; ++checkedDecision) {
            currentDecisionsValue = QApproximator.Approximate(TweakInput(new Record(state, checkedDecision, state, 0))).Get(0);
            if (currentDecisionsValue == maxValue) {
                decisions.add(checkedDecision);
            }
        }

        // Check the probability of each value
        for (int i = 0; i < 8; i++) {
            decisionsP[i] = epsilon/8 + (1-epsilon)*(decisions.contains(i) ? 1 : 0)/decisions.size();
        }

        double choosenValue = new Random().nextDouble();
        double max = 0.0;
        for (int i = 0; i < 8; i++)
            max += decisionsP[i];
        choosenValue *= max;

        double tmp = 0.0;
        for (int i = 0; i < 8; i++) {
            tmp += decisionsP[i];
            if (choosenValue <= tmp) {
                decision[0] = i;
                return QApproximator.Approximate(TweakInput(new Record(state, i, state, 0))).Get(0);
            }
        }

        return maxValue;
    }

    private double CalculateValue(final Vector nextState, final double reward) {
        double result = 0.0;

        // r
        result += reward;

        // max(u) Q(x_t+1, u)
        double approx = PrepareADecision(nextState, new int[1]);

        // r + gamma * max(u) Q(x_t+1, u)
        result += GAMMA * approx;

        return result;
    }

    private void RewriteHistoryeh() {

        for (int i = 0; i < TIMES_TO_REWRITE_HISTORY; i++) {
            final Record record = records.get(random.nextInt(records.size()));

//            double estimatedValue = PrepareADecision(record.state, new int[]{record.decision}); // ???? dla wcześniej wylosowanej wartości czy nową losować??

            double approximatedValue = CalculateValue(record.nextState, record.reward);

//            final double approximatedValue = QApproximator.Approximate(TweakInput(record)).Get(0);


            double estimatedValue = QApproximator.Approximate(TweakInput(record)).Get(0);


            final Vector errorGrad = ErrorApproximator.GetError(estimatedValue, approximatedValue);

            QApproximator.BackPropagate(errorGrad);
            QApproximator.ApplyWeights();
        }
    }

    private Vector TweakInput(final Record record) {
        final int STATE_SIZE = record.state.GetLength() - 1; // Last value is a temporary bool
        double[] result = new double[STATE_SIZE + 2];

        for (int i = 0; i < STATE_SIZE; i++) {
            result[i] = record.state.Get(i);
        }

        {
            double ax, ay;
            switch (record.decision) {
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
            result[STATE_SIZE] = ax;
            result[STATE_SIZE+1] = ay;

            // Normalize x and y
            result[0] = result[0] / _model.getSegmentSizeX();
            result[1] = result[1] / _model.getSegmentSizeY();
        }

        return new Vector(result);
    }


    /*-----Variables------*/

//    private final int HORIZON_LENGTH;
    private final int TIMES_TO_REWRITE_HISTORY;
    private ArrayList<Record> records = new ArrayList<>();
    private Vector State;
    private int[] Decision;
    private double EstimatedValue;
    private final Random random = new Random();
    private MLPerceptron QApproximator;
    private final Model _model;
    private final int MEMORY_LIMIT;
    private final double GAMMA;

    private List<Integer> error = new LinkedList<>();
    // Evolution Algorithm
//    private int phi;
//    private double currentSigma;
//    private final double SIGMA_MIN;
//    private final double SIGMA_START;
//    private final double M = 10;
//    private final double C1 = 0.82;
//    private final double C2 = 1.2;
}
