import MLPerceptron.CellType;
import MLPerceptron.MLPerceptron;
import MLPerceptron.MLPerceptronImpl;
import MLPerceptron.TeachingPolicies.ClassicalMomentumTP;
import MLPerceptron.TeachingPolicies.TeachingPolicy;
import MLPerceptron.Utils.ErrorApproximator;
import QLearinging.Record;
import MLPerceptron.Utils.Vector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static MLPerceptron.CellType.ARCTANGENT;
import static MLPerceptron.CellType.LINEAR;

public class Main {

    public static void main(String[] args) {

        // Prepare parameters
        final int INPUT_SIZE = 2;
        final int OUTPUT_SIZE = 1;
        final double BetaV = 0.01;
        TeachingPolicy teachingPolicy = new ClassicalMomentumTP(BetaV);


        // Build the neural network
        int[] sizes = new int[] {INPUT_SIZE, OUTPUT_SIZE};
        CellType[] cellTypes = new CellType[] {LINEAR };
        MLPerceptron mlperceptron = new MLPerceptronImpl(sizes, cellTypes, teachingPolicy);


        // Approximate

        /// Zaślepki
        Vector Zinput = new Vector(INPUT_SIZE);
        Vector ZoutputD = new Vector(OUTPUT_SIZE);


        Vector error;

        error = ErrorApproximator
                .GetError(mlperceptron.Approximate(Zinput).Get(0), ZoutputD.Get(0));
        mlperceptron.BackPropagate(error);
        mlperceptron.ApplyWeights();




        // Database
        ArrayList<Record> data = new ArrayList<>();
        List<Integer> decisions = new LinkedList<>();



    }
}
