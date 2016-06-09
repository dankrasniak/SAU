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

public class Main {
static void foo(int[] a) { int[] b = new int[1]; b[0] = 2; a[0]= b[0];
    //a[0]=2;
    }
    public static void main(String[] args) {
        int[] actions = new int[1];
        actions[0] = 1;
        final Record record = new Record(new Vector(1),actions);
        foo(record.actions);
        System.out.println(record.actions[0]);

        // Prepare parameters
        final int INPUT_SIZE = 5;
        final int OUTPUT_SIZE = 1;
        final double BetaV = 0.01;
        TeachingPolicy teachingPolicy = new ClassicalMomentumTP(BetaV);


        // Build the neural network
        int[] sizes = new int[] {INPUT_SIZE, 20, 20, OUTPUT_SIZE};
        CellType[] cellTypes = new CellType[] {ARCTANGENT, ARCTANGENT, ARCTANGENT};
        MLPerceptron mlperceptron = new MLPerceptronImpl(sizes, cellTypes, teachingPolicy);



        // Prepare model


        // Approximate

        /// Zaślepki
        Vector Zinput = new Vector(INPUT_SIZE);
        Vector ZoutputD = new Vector(OUTPUT_SIZE);


        Vector error;

        error = ErrorApproximator
                .GetError(mlperceptron.Approximate(Zinput), ZoutputD);
        mlperceptron.BackPropagate(error);
        mlperceptron.ApplyWeights();




        // Database
        ArrayList<Record> data = new ArrayList<>();
        List<Integer> decisions = new LinkedList<>();




        // Advice Action
        { // state

            // Approximate the reward based on the current Decisions list
            // Try to prepare a more worthy decisions list
            // Save the new state and Decisions list as a new Record
        }


        // This Happened
        // Przejście do następnego stanu, następnej decyzji
        { // state (next)
            // Move to next iteration values

            // Update THE NEURAL NETWORK - N times
            {
                // Get Random Record from Database
                // Run the StateValue function
                {
                    // Count the reward
                    // Approximate the reward
                    // Add the approximated reward to the counted one with appropriate gamma
                    // Return the value
                }

                // Try to get a better decisions list
                // Get the error gradient
                // Update THE NEURAL NETWORK

            }
        }



    }
}
