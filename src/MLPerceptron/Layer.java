package MLPerceptron;

import MLPerceptron.Utils.Matrix;
import MLPerceptron.TeachingPolicies.TeachingPolicy;
import MLPerceptron.Utils.Vector;

/**
 *
 */
public interface Layer {

    /**
     * Computes the output of the current layer for the given input.
     * @param input Vector containing input that will be given to every single neuron in the current layer.
     * @return Vector containing values returned by the neurons and that were run through the activation funtion.
     */
    Vector Forward(final Vector input);

    /**
     * Computes the new weights gradient.
     * @param gradient Vector containing the difference between the expected value and given value.
     * @return Vector containing the gradient.
     */
    Vector Backward(final Vector gradient);

    /**
     * Applies the weights.
     */
    void ApplyWeights();
}
