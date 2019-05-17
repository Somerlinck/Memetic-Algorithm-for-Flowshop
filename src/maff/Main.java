package maff;

import maff.model.Problem;

public class Main {

    public static void main(String[] args) {
        Problem problem = new Problem("res/tai11.txt");
//        problem.EvaluationSeparation();
        PBSE pbse = new PBSE(problem);
        pbse.search();
    }

}
