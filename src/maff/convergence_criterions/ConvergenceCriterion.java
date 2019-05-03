package maff.convergence_criterions;

import maff.Solution;

import java.util.ArrayList;

public interface ConvergenceCriterion {

    boolean hasConverged(ArrayList<Solution> solution);

}
