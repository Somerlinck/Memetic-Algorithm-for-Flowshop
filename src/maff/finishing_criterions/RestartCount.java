package maff.finishing_criterions;

import maff.PBSE;
import maff.model.Solution;

import java.util.TreeSet;

public class RestartCount implements FinishingCriterion {
    private int max;

    public RestartCount(int max) {
        this.max = max;
    }

    @Override
    public boolean hasFinished(TreeSet<Solution> population) {
        // TODO Auto-generated method stub
        return PBSE.restartCount >= max;
    }

    @Override
    public float getProgress() {
        return (float) PBSE.restartCount / max;
    }

}
