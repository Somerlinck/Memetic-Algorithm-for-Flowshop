package maff.convergence_criterions;

import maff.Population;

public interface ConvergenceCriterion {

    boolean hasConverged(Population population);

}
