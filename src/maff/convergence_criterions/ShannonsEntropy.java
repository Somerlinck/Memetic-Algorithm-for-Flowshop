package maff.convergence_criterions;

import maff.Solution;

import java.util.ArrayList;
import java.util.TreeSet;

public class ShannonsEntropy implements ConvergenceCriterion {
    @Override
    public boolean hasConverged(TreeSet<Solution> solution) {
        return false;
    }
}
