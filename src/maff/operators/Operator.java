package maff.operators;

import maff.Solution;

import java.util.ArrayList;

public interface Operator {

    ArrayList<Solution> apply(ArrayList<Solution> solution);

}
