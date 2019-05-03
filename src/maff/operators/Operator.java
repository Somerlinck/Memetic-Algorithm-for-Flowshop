package maff.operators;

import maff.Solution;

import java.util.ArrayList;
import java.util.TreeSet;

public interface Operator {

    TreeSet<Solution> apply(TreeSet<Solution> solution);

}
