package MLPerceptron;

import MLPerceptron.TeachingPolicies.TeachingPolicy;
import MLPerceptron.Utils.Vector;


public interface MLPerceptron {

    /**
     * Returns the value which approximated function should return for the given input.
     * @param input - approximated function input.
     * @return - output for the given input of the approximated function.
     */
    Vector Approximate(Vector input);

    /**
     * Runs the Back Propagation algorithm and computes the weights gradient.
     * @param gradient - difference between the expected value and received value.
     */
    void BackPropagate(final Vector gradient);

    /**
     * Applies the new weights gradient to the current weights of the neural network.
     * @param teachingPolicy - defines the way in which the new weights are applied,
     *                       in other words defines the teaching algorithm.
     */
    void ApplyWeights(final TeachingPolicy teachingPolicy);

}
