package QLearinging;


import MLPerceptron.Utils.Vector;

public class Record {

    public Record(final Vector state, final int[] actions) {
        this.state = state.Clone();
        this.actions = actions;
    }

    /*-----Variables------*/

    public Vector state;
    public int[] actions;
}
