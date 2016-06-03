import MLPerceptron.CellType;
import MLPerceptron.MLPerceptron;
import MLPerceptron.MLPerceptronImpl;
import MLPerceptron.TeachingPolicies.ClassicalMomentumTP;
import MLPerceptron.TeachingPolicies.TeachingPolicy;
import MLPerceptron.Utils.ErrorApproximator;
import MLPerceptron.Utils.Vector;

import static MLPerceptron.CellType.ARCTANGENT;

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


        /// TODO sadasd

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
