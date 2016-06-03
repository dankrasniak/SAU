package com.company.MLPerceptron.TeachingPolicies;

import com.company.MLPerceptron.Utils.Matrix;

public interface TeachingPolicy {
    Matrix Execute(final Matrix weights, final Matrix newWeights, final Matrix oldWeights);
}
