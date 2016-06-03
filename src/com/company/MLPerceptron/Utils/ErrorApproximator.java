package com.company.MLPerceptron.Utils;


abstract public class ErrorApproximator {

    static public Vector GetError(final Vector nnOutput, final Vector dOutput) {
        Vector result;

        result = new Vector(new double[] {nnOutput.Get(0) - dOutput.Get(0) });

        return result;
    }
}
