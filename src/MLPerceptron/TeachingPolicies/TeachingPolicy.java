package MLPerceptron.TeachingPolicies;

import MLPerceptron.Utils.Matrix;

public interface TeachingPolicy {
    Matrix Execute(final Matrix weights, final Matrix newWeights, final Matrix oldWeights);
}
