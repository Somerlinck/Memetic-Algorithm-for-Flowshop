package maff.convergence_criterions;

import maff.model.Solution;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

public class ShannonsEntropy implements ConvergenceCriterion {

    private float threshold;

    private HashMap<Solution, Float> probabilities = new HashMap<>();
    private int size;
    private float lastEntropy = -1;

    public ShannonsEntropy() {
        this(0.9999f);
    }

    public ShannonsEntropy(float threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean hasConverged(TreeSet<Solution> population) {
        for (Solution s1 : population) {
            boolean test = true;
            Iterator<Map.Entry<Solution,Float>> iterator = probabilities.entrySet().iterator();
            while(iterator.hasNext()) {
                Map.Entry<Solution, Float> entry = iterator.next();
                Solution s2 = entry.getKey();
                if (s1.equals(s2)) {
                    test = false;
                    float p = entry.getValue();
                    probabilities.put(s2, p * (float) size / (size + 1) + 1.0f / (size + 1));
                    size++;
                    break;
                }
            }

            if (test) probabilities.put(s1, 1.0f / ++size);
        }

        float entropy = 0;
        for (float value : probabilities.values()) {
            entropy -= 10 * value * Math.log(value);
        }
        if (lastEntropy == -1) {
            lastEntropy = entropy;
            return false;
        }

        boolean test = lastEntropy / entropy > threshold;
        lastEntropy = entropy;


        return test;
    }

}
