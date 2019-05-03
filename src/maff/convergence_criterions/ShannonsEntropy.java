package maff.convergence_criterions;

import maff.Population;

public class ShannonsEntropy implements ConvergenceCriterion {
    @Override
    public boolean hasConverged(Population population) {
        return false;
    }
}
