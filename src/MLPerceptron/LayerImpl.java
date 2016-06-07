package MLPerceptron;

import MLPerceptron.Utils.Matrix;
import MLPerceptron.TeachingPolicies.TeachingPolicy;
import MLPerceptron.Utils.Vector;

import java.util.Random;
import java.util.function.Function;


public class LayerImpl implements Layer {

    LayerImpl(final int inputSize, final int outputSize, final CellType cellType, final TeachingPolicy teachingPolicy) {
        Build(inputSize, outputSize, cellType, teachingPolicy);
    }


    /*-----Public methods------*/

    final public Vector Forward(final Vector input) {
        Input = TweakInput(input);
        Net = Input.MlpVectorMatrix(weights);
        return ActivationFunction.apply(Net);
    }

    final public Vector Backward(final Vector error) {
        Vector dOutput = DeactivationFunction.apply(Net);
        Vector dNet = dOutput.MlpVectorVector(error);
        dWeights = Vector.MlpVectorVectorM(dNet, Input);
        return Vector.MlpVectorMatrixV(dNet, weights);
    }

    final public void ApplyWeights() {
        teachingPolicy.Execute(weights, dWeights, Momentum);
    }

    final public Matrix GetWeights() { return weights; }


    /*-----Private methods------*/

    private void Build(final int inputSize, final int outputSize, final CellType cellType, final TeachingPolicy teachingPolicy) {
        weights = new Matrix(outputSize, inputSize);
        Initialize(weights);
        Momentum = new Matrix(weights.GetHeight(), weights.GetLength());
        this.teachingPolicy = teachingPolicy;

        switch (cellType) {
            case LINEAR:
                ActivationFunction = this::ActivationFunctionLinear;
                DeactivationFunction = this::DeactivationFunctionLinear;
                break;

            case ARCTANGENT:
                ActivationFunction = (final Vector value) -> ActivationFunctionTan(value);
                DeactivationFunction = (final Vector value) -> DeactivationFunctionTan(value);
                break;

            default:
                throw new IllegalArgumentException("LayerImpl::LayerImpl()");
        }
    }

    private void Initialize(final Matrix m) {
        Random random = new Random();

        final int HEIGHT = m.GetHeight();
        final int LENGTH = m.GetLength();

        for (int x = 0; x < HEIGHT; x++)
            for (int y = 0; y < LENGTH; y++)
                m.Set(x, y, (random.nextDouble()-0.5)*2);
    }

    private Vector TweakInput(final Vector input) {
        final int LENGTH = input.GetLength() + 1;
        double[] result = new double[LENGTH];

        for (int i = 0; i < LENGTH; i++)
            result[i] = input.Get(i);
        result[LENGTH] = 1.0;

        return new Vector(result);
    }

    private Vector ActivationFunctionTan(final Vector values) {
        final int VECTOR_SIZE = values.GetLength();
        final double[] result = new double[VECTOR_SIZE];

        for (int i = 0; i < VECTOR_SIZE; i++)
            result[i] = Math.atan(values.Get(i));

        return new Vector(result);
    }

    private Vector DeactivationFunctionTan(final Vector values) {
        final int VECTOR_SIZE = values.GetLength();
        final double[] result = new double[VECTOR_SIZE];

        for (int i = 0; i < VECTOR_SIZE; i++)
            result[i] = 1.0 / (1.0 + values.Get(i) * values.Get(i));

        return new Vector(result);
    }

    private Vector ActivationFunctionLinear(final Vector values) {
        final int VECTOR_SIZE = values.GetLength();
        final double[] result = new double[VECTOR_SIZE];

        for (int i = 0; i < VECTOR_SIZE; i++)
            result[i] = values.Get(i);

        return new Vector(result);
    }

    private Vector DeactivationFunctionLinear(final Vector values) {
        final int VECTOR_SIZE = values.GetLength();
        final double[] result = new double[VECTOR_SIZE];

        for (int i = 0; i < VECTOR_SIZE; i++)
            result[i] = 1.0;

        return new Vector(result);
    }


    /*-----Variables------*/

    private Vector Input;
    private Matrix weights;
    private Matrix dWeights;
    private Vector Net;
    private Matrix Momentum;
    private TeachingPolicy teachingPolicy;
    private Function<Vector, Vector> ActivationFunction;
    private Function<Vector, Vector> DeactivationFunction;
}
