package QLearinging;

import MLPerceptron.Utils.Vector;

public interface Model {
    double getReward(final Vector state);
    Vector[] stateFunction(final Vector state, final int[] actions);
}
