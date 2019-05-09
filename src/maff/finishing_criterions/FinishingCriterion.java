package maff.finishing_criterions;

import maff.model.Solution;

import java.util.TreeSet;

public interface FinishingCriterion {

    boolean hasFinished(TreeSet<Solution> population);

}
