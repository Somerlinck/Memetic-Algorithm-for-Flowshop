package maff.convergence_criterions;

import maff.Solution;

import java.util.ArrayList;

public class BayesianMethod implements ConvergenceCriterion {
    @Override
    public boolean hasConverged(ArrayList<Solution> solution) {
        return false;
    }
}
