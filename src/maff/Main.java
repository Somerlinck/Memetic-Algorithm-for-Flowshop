package maff;

import maff.model.Problem;

public class Main {

    public static void main(String[] args) {
        Problem problem = new Problem("res/tai21.txt");
        PBSE pbse = new PBSE(problem);
        pbse.search();
    }

}
