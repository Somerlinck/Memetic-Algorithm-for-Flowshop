package maff.convergence_criterions;

import maff.Solution;

import java.util.ArrayList;
import java.util.TreeSet;

public interface ConvergenceCriterion {

    boolean hasConverged(TreeSet<Solution> solution);

}
