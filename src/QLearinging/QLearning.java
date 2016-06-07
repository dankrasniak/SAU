package QLearinging;


import MLPerceptron.CellType;
import MLPerceptron.MLPerceptron;
import MLPerceptron.MLPerceptronImpl;
import MLPerceptron.TeachingPolicies.TeachingPolicy;
import MLPerceptron.Utils.Vector;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Random;

public class QLearning {

    public QLearning(final int[] sizes,
                     final CellType[] cellTypes,
                     final TeachingPolicy teachingPolicy,
                     final double BetaV,
                     final int HORIZON_LENGTH,
                     final int TIMES_TO_REWRITE_HISTORY) {

        this.HORIZON_LENGTH = HORIZON_LENGTH;
        this.TIMES_TO_REWRITE_HISTORY = TIMES_TO_REWRITE_HISTORY;
        decisions = new int[HORIZON_LENGTH]; // TODO MORE

        Initialize(sizes, cellTypes, teachingPolicy, BetaV);
    }


    /*-----Public methods------*/

    public int AdviseAction(final Vector state) {
        final double decisionValue = CalculateValue(state, decisions);

        PrepareABetterDecisionsList(state, decisions, decisionValue); // TODO Not sure if finished parameters

        records.add(new Record(state, decisions));

        return decisions[0];
    }

    public void ThisHappened(final Vector nextState) {
        UpdateToTheNextIteration();

        RewriteHistoryeh(); // TODO
    }


    /*-----Private methods------*/

    private void Initialize(final int[] sizes,
                            final CellType[] cellTypes,
                            final TeachingPolicy teachingPolicy,
                            final double BetaV) {
        QApproximator = new MLPerceptronImpl(sizes, cellTypes, teachingPolicy);
        throw new NotImplementedException(); // TODO
    }

    private double CalculateValue(final Vector state, final int[] decision) {
        throw new NotImplementedException();
    }

    private void PrepareABetterDecisionsList(final Vector state,
                                             final int[] decision,
                                             final double currentDecisionValue) {
        throw new NotImplementedException(); // TODO
    }

    private void UpdateToTheNextIteration() {
        for (int i = 0; i < HORIZON_LENGTH;) {
            decisions[i] = decisions[++i];
        }
    }

    private void RewriteHistoryeh() {

        for (int i = 0; i < TIMES_TO_REWRITE_HISTORY; i++) {
            final Record record = records.get(random.nextInt(records.size() - 1));

            final double estimatedValue = CalculateValue(record.state, record.actions);

            PrepareABetterDecisionsList(record.state, record.actions, estimatedValue);

            final double approximatedValue = QApproximator.Approximate(TweakInput(record)).Get(0);

            final double errorGrad = approximatedValue - estimatedValue;
            QApproximator.BackPropagate(new Vector(new double[] {errorGrad}));
            QApproximator.ApplyWeights(); // TODO Change TeachingPolicy handling in MLPerceptron
        }
        throw new NotImplementedException(); // TODO
    }

    private Vector TweakInput(final Record record) {
        throw new NotImplementedException();
    }


    /*-----Variables------*/

    private final int HORIZON_LENGTH;
    private final int TIMES_TO_REWRITE_HISTORY;
    private ArrayList<Record> records = new ArrayList<>();
    private int[] decisions;
    private final Random random = new Random();
    private MLPerceptron QApproximator;
}
