package maff.convergence_criterions;

import maff.model.Solution;

import java.util.HashMap;
import java.util.TreeSet;

public class ShannonsEntropy implements ConvergenceCriterion {

    private float threshold;

    private HashMap<Solution, Float> probabilities = new HashMap<>();
    private int size = 0;
    private float lastEntropy = -1;

    public ShannonsEntropy() {
        this(0.90f);
    }

    public ShannonsEntropy(float threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean hasConverged(TreeSet<Solution> population) {
        for (Solution s1 : population) {
            if (probabilities.containsKey(s1)) {
                float p = probabilities.get(s1);
                probabilities.put(s1, p * (float) size / (size + 1) + 1.0f / (size + 1));
                size++;
                break;
            } else probabilities.put(s1, 1.0f / ++size);
        }

        float entropy = 0;
        for (float value : probabilities.values()) entropy -= value * Math.log(value);
        if (lastEntropy == -1) {
            lastEntropy = entropy;
            return false;
        }

        boolean test = entropy / lastEntropy > threshold;
        lastEntropy = entropy;

        System.out.println(entropy / lastEntropy / threshold * 100 + "% of convergence");

        return test;
    }

}
