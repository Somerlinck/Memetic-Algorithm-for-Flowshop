package maff.ConvergenceCriterions;

import maff.Population;

public class BayesianMethod implements ConvergenceCriterion {
    @Override
    public boolean hasConverged(Population population) {
        return false;
    }
}
