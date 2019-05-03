package maff.ConvergenceCriterions;

import maff.Population;

public interface ConvergenceCriterion {

    boolean hasConverged(Population population);

}
