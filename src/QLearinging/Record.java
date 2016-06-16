package QLearinging;


import MLPerceptron.Utils.Vector;

public class Record {

    public Record(final Vector state, final int decision, final Vector nextState, final double reward) {
        this.state = state.Clone();
        this.decision = decision;
        this.nextState = nextState;
        this.reward = reward;
    }

    /*-----Variables------*/

    public Vector state;
    public int decision;
    public Vector nextState;
    public double reward;
}
