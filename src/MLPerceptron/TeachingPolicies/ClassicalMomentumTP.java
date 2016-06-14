package MLPerceptron.TeachingPolicies;

import MLPerceptron.Utils.Matrix;

public class ClassicalMomentumTP implements TeachingPolicy {

    public ClassicalMomentumTP(final double BetaV) {
        this.BetaV = BetaV;
    }

    public Matrix Execute(final Matrix weights, final Matrix newWeights, final Matrix Momentum) {
        final int HEIGHT = weights.GetHeight();
        final int LENGTH = weights.GetLength();
        double w;

        Matrix result = new Matrix(HEIGHT, LENGTH);

        for (int h = 0; h < HEIGHT; h++) {
            for (int l = 0; l < LENGTH; l++) {
                w = weights.Get(h, l);
                Momentum.Set(h, l, Momentum.Get(h, l) * 0.9 + newWeights.Get(h, l) * BetaV);
                result.Set(h, l, w + Momentum.Get(h, l) );
            }
        }

        return result;
    }

    private final double BetaV;
}