package maff.operators;

import maff.model.Solution;

import java.util.TreeSet;

public interface Operator {

    TreeSet<Solution> apply(TreeSet<Solution> solution);

}
