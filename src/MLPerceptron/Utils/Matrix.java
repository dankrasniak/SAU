package MLPerceptron.Utils;


public class Matrix {

    public Matrix(final int output, final int input) {
        values = new double[output][input];
    }

    /*-----Public methods------*/

    final public void Add(final Matrix m, final double betaV) {
        final int LENGTH = m.values.length;
        final int HEIGHT = m.values[0].length;

        if (LENGTH != values.length && HEIGHT != values[0].length)
            throw new IllegalArgumentException();

        for (int x = 0; x < LENGTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                values[x][y] += m.values[x][y] * betaV;
    }

    final public double Get(final int x, final int y) { return values[x][y]; }

    final public void Set(final int x, final int y, final double value) { values[x][y] = value; }

    final public int GetLength() { return values.length; }

    final public int GetHeight() { return values[0].length; }


    /*-----Variables------*/

    private double[][] values;

}

