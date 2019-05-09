package maff.finishing_criterions;

import maff.model.Solution;

import java.util.TreeSet;

public class IterationCount implements FinishingCriterion {

    private int max;

    private int current = 0;

    public IterationCount(int max) {
        this.max = max;
    }

    @Override
    public boolean hasFinished(TreeSet<Solution> population) {
        return ++current >= max;
    }

}
