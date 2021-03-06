package MLPerceptron.Utils;

public class Vector {

    public Vector(final int size) {
        values = new double[size];
    }
    public Vector(final double[] v) {
        values = v;
    }


    /*-----Public methods------*/

    public Vector Clone() { // TODO Verify
        return new Vector(this.values);
    }

    static public Vector MlpVectorMatrixV(final Vector net, final Matrix w) {
        final int LENGTH = w.GetLength() - 1;
        final int HEIGHT = w.GetHeight();

        if (HEIGHT != net.GetLength())
            throw new IllegalArgumentException("MlpVectorxMatrixV");

        double[] result = new double[LENGTH];

        for (int x = 0; x < LENGTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                result[x] += net.values[y] * w.Get(y, x);
            }
        }

        return new Vector(result);
    }

    static public Matrix MlpVectorVectorM(final Vector dNet, final Vector input) {
        final int LENGTH = input.values.length;
        final int HEIGHT = dNet.values.length;

        final Matrix result = new Matrix(HEIGHT, LENGTH);
        for (int y = 0; y < LENGTH; ++y)
            for (int x = 0; x < HEIGHT; ++x)
                result.Set(x, y, input.values[y] * dNet.values[x]);

        return result;
    }

    final public Vector MlpVectorVector(final Vector b) {
        final int LENGTH = this.values.length;

        if (LENGTH != b.values.length)
            throw new IllegalArgumentException("MlpVectorxVector");

        double[] result = new double[LENGTH];

        for (int i = 0; i < LENGTH; i++) {
            result[i] = values[i] * b.values[i];
        }

        return new Vector(result);
    }

    final public Vector MlpVectorMatrix(final Matrix m) {
        final int LENGTH = values.length;
        final int HEIGHT = m.GetHeight();
        Vector result = new Vector(HEIGHT);

        if (LENGTH != m.GetLength())
            throw new IllegalArgumentException("MlpVectorMatrix");

        double sum;
        for (int y = 0; y < HEIGHT; y++) {
            sum = 0.0;
            for (int x = 0; x < LENGTH; x++) {
                 sum += values[x] * m.Get(y, x);
            }
            result.values[y] = sum;
        }

        return result;
    }

    final public int GetLength() {
        return values.length;
    }

    final public double Get(final int i) {
        return values[i];
    }

    @Override
    public String toString() {
        String result = "[" + values[0];
        final int LENGTH = values.length;

        for (int i = 1; i < LENGTH; i++) {
            result += ", " + values[i];
        }
        result += "]";

        return result;
    }


    /*-----Variables------*/

    double[] values;
}
