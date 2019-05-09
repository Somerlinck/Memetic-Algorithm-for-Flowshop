package maff.convergence_criterions;

import maff.model.Solution;

import java.util.TreeSet;

public interface ConvergenceCriterion {

    boolean hasConverged(TreeSet<Solution> population);

}
