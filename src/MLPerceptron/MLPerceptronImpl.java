package MLPerceptron;

import MLPerceptron.TeachingPolicies.TeachingPolicy;
import MLPerceptron.Utils.Vector;

import java.util.LinkedList;
import java.util.List;


public class MLPerceptronImpl implements MLPerceptron {

    public MLPerceptronImpl(final int[] sizes, final CellType[] cellTypes) {
        Build(sizes, cellTypes);
    }


    /*-----Public methods------*/

    final public Vector Approximate(final Vector input) { // TODO: Verify
        Vector lastLayersOutput = input;

        for (Layer layer : layers) {
            lastLayersOutput = layer.Forward(lastLayersOutput);
        }

        return lastLayersOutput;
    }

    final public void BackPropagate(final Vector gradient) {
        Vector passedValue = gradient;

        for (Layer layer : layers)
            passedValue = layer.Backward(passedValue);
    }

    final public void ApplyWeights(final TeachingPolicy teachingPolicy) {
        for (Layer layer : layers)
            layer.ApplyWeights(teachingPolicy);
    }


    /*-----Private methods------*/

    /**
     * Constructs the <b>Neural Network</b>.
     * @param sizes Array containing the number of values passed between the layers of the network,
     *              also the input and the output.
     */
    private void Build(int[] sizes, final CellType[] cellTypes) {
        final int ARRAY_SIZE_M_1 = sizes.length - 1;

        for (int i = 0; i < ARRAY_SIZE_M_1; i++) {
            layers.add(new LayerImpl(sizes[i] + 1, sizes[i+1], cellTypes[i]));
        }
    }


    /*-----Variables------*/

    private List<Layer> layers = new LinkedList<>();
}