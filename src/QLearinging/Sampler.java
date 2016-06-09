package QLearinging;

import java.util.Random;

public class Sampler {
    static private Random random = new Random();

    static public double sampleFromNormal(final double mean, final double std_dev) {
        double z = -Math.log(1.0 - random.nextDouble());
        double alpha = random.nextDouble() * Math.PI * 2;
        double norm = Math.sqrt(z * 2) * Math.cos(alpha);
        return mean + norm * std_dev;
    }
}
