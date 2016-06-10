package MLPerceptron.Utils;


abstract public class ErrorApproximator {

    static public Vector GetError(final double nnOutput, final double dOutput) {
        Vector result;

        result = new Vector(new double[] {nnOutput - dOutput });

        return result;
    }
}
