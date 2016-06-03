package com.company;

import com.company.MLPerceptron.CellType;
import com.company.MLPerceptron.MLPerceptron;
import com.company.MLPerceptron.MLPerceptronImpl;
import com.company.MLPerceptron.TeachingPolicies.ClassicalMomentumTP;
import com.company.MLPerceptron.TeachingPolicies.TeachingPolicy;
import com.company.MLPerceptron.Utils.ErrorApproximator;
import com.company.MLPerceptron.Utils.Vector;
import com.sun.org.apache.xerces.internal.impl.dv.ValidationContext;

import static com.company.MLPerceptron.CellType.ARCTANGENT;
import static com.company.MLPerceptron.CellType.LINEAR;

public class Main {

    public static void main(String[] args) {

        // Prepare parameters
        final int INPUT_SIZE = 5;
        final int OUTPUT_SIZE = 8;
        final double BetaV = 0.01;
        TeachingPolicy teachingPolicy = new ClassicalMomentumTP(BetaV);


        // Build the neural network
        int[] sizes = new int[] {INPUT_SIZE, 20, 20, OUTPUT_SIZE};
        CellType[] cellTypes = new CellType[] {ARCTANGENT, ARCTANGENT, ARCTANGENT};
        MLPerceptron mlperceptron = new MLPerceptronImpl(sizes, cellTypes);




        // Prepare model


        // Approximate

        /// Zaślepki
        Vector Zinput = new Vector(INPUT_SIZE);
        Vector ZoutputD = new Vector(OUTPUT_SIZE);


        Vector error;

        error = ErrorApproximator
                .GetError(mlperceptron.Approximate(Zinput), ZoutputD);
        mlperceptron.BackPropagate(error);
        mlperceptron.ApplyWeights(teachingPolicy);



    }
}
