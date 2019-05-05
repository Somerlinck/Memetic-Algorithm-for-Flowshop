package maff.convergence_criterions;

import maff.model.Solution;

import java.util.TreeSet;

public class BayesianMethod implements ConvergenceCriterion {
    @Override
    public boolean hasConverged(TreeSet<Solution> solution) {
        return false;
    }
}
