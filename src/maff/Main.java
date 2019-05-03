package maff;

import maff.model.Flowshop;
import maff.model.Ordonnancement;

public class Main {

    public static void main(String[] args) {
        System.out.println("Test du NEH :");
        Flowshop Fs = new Flowshop("res/tai01.txt");
        new Ordonnancement(Fs.creerListeNEH(5), 5).afficher();

        System.out.println();

        System.out.println("Test du EvaluationSeparation :");
        Fs = new Flowshop("res/tai01.txt");
        Fs.EvaluationSeparation();
    }

}
