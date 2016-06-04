package QLearinging;


import MLPerceptron.Utils.Vector;

public class Record {

    public Record(final Vector state, final int[] actions) {
        this.state = state.Clone();
        this.actions = actions; /// TODO Verify Arrays.copyOf(actions, actions.length);
    }

    /*-----Variables------*/

    public Vector state;
    public int[] actions;
}
