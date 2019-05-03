package maff.convergence_criterions;

import maff.Solution;

import java.util.ArrayList;

public class ShannonsEntropy implements ConvergenceCriterion {
    @Override
    public boolean hasConverged(ArrayList<Solution> solution) {
        return false;
    }
}
