package maff.finishing_criterions;

import maff.model.Solution;

import java.util.TreeSet;

public class LastImprovement implements FinishingCriterion {
    private Solution best;
    private int max;
    private int count;

    public LastImprovement(int max) {
        this.max = max;
        this.best = null;
        this.count = 0;
    }

    @Override
    public boolean hasFinished(TreeSet<Solution> population) {
        // TODO Auto-generated method stub
        if (best != null) {
            if (best.getDuree() >= population.first().getDuree()) {
                best = population.first();
            } else {
                count++;
            }
        } else {
            best = population.first();
        }
        return count >= max;
    }

    @Override
    public float getProgress() {
        return (float) count / max;
    }

}
