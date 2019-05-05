package maff;

import maff.convergence_criterions.ShannonsEntropy;
import maff.model.Problem;
import maff.model.Solution;
import maff.operators.LocalSearch;
import maff.operators.Mutation;
import maff.operators.Operator;
import maff.operators.Reproduction;

import java.util.ArrayList;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) {
        Problem problem = new Problem("res/tai01.txt");
        PBSE pbse = new PBSE(
                problem,
                100,
                new LocalSearch(),
                new ArrayList() {{
                    add(new Reproduction());
                    add(new LocalSearch());
                    add(new Mutation());
                    add(new LocalSearch());
                }},
                new ShannonsEntropy()) {

            @Override
            public boolean hasFinished(TreeSet<Solution> solution) {
                return false;
            }
        };
        pbse.search();
    }

}
