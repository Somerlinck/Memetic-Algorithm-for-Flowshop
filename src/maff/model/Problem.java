package maff.model;/*
 * Nom de classe : Problem
 *
 * Description :
 *
 * Version : 1.0
 *
 * Date : 21/09/2010
 *
 * Auteur : Chams LAHLOU
 */

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Problem {

    private int nbJobs;        // nombre de jobs
    private int nbMachines;    // nombre de machines
    private Job[] jobs;        // tableau des jobs

    // constructeur par défaut
    public Problem() {
        nbJobs = 0;
        nbMachines = 0;
        jobs = null;
    }

    // crée un problème à m machines à partir d'un tableau de jobs
    public Problem(Job[] t, int m) {
        nbMachines = m;
        nbJobs = t.length;
        jobs = new Job[nbJobs]; // on réserve la place pour les jobs
        for (int i = 0; i < nbJobs; i++) {
            jobs[i] = t[i];
        }
    }

    // crée un problème à partir d'un fichier
    public Problem(String s) {
        try {
            Scanner scanner = new Scanner(new FileReader(s));

            // lecture du nombre de jobs
            if (scanner.hasNextInt()) {
                nbJobs = scanner.nextInt();
            }

            // lecture du nombre de machines
            if (scanner.hasNextInt()) {
                nbMachines = scanner.nextInt();
            }

            jobs = new Job[nbJobs]; // on réserve la place pour les jobs
            int[] d = new int[nbMachines];
            int i = 0; // indice du job
            int j = 0; // indice de l'opération
            while (scanner.hasNextInt()) {
                d[j] = scanner.nextInt();
//                System.out.println(j + " " + d[j]);
                if (j < nbMachines - 1) {
                    j++; // opération suivante
                } else { // sinon on crée le job et on passe au suivant
                    jobs[i] = new Job(i, d);
                    i++;
                    j = 0;
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.err.println("Erreur : " + e.getMessage());
            System.exit(2);
        }
    }

    public int getNbJobs() {
        return nbJobs;
    }

    public int getNbMachines() {
        return nbMachines;
    }

    public Job getJob(int i) {
        return jobs[i];
    }

    // crée une liste correspondant au tableau des jobs
    public JobsList creerListeJobs() {
        JobsList l = new JobsList();
        for (int i = 0; i < nbJobs; i++) {
            l.ajouterJob(jobs[i].clone());
        }
        return l;
    }

    /************************************************
     / exo 5
     /************************************************/

    public JobsList creerListeNEH(int m) { // renvoie une liste selon l'ordre NEH
        JobsList res = new JobsList();
        for (Job job : jobs) res.ajouterJob(job);
        res.trierDureesDecroissantes();

        JobsList partielle = new JobsList();
        partielle.ajouterJob(res.pop(0));

        int size = res.nombreJobs();
        for (int i = 0; i < size; i++) {
            Job job = res.pop(0);
            JobsList best = partielle.clone();
            best.ajouterJob(job, 0);
            for (int j = 0; j < i + 1; j++) {
                JobsList test = partielle.clone();
                test.ajouterJob(job, j + 1);
                if (new Solution(test, m).getDuree() < new Solution(best, m).getDuree()) best = test;
            }
            partielle = best;
        }

        return partielle;
    }

    /************************************************
     / exo 6
     /************************************************/

    // calcul de r_kj
    public int calculerDateDispo(int k, int j) {
        if (k == 0) return 0;
        int r = 0;
        for (int i = 0; i < k; i++) r += this.getJob(j).getDureeOperation(i);
        return r;
    }

    // calcul de q_kj
    public int calculerDureeLatence(int k, int j) {
        if (k == nbMachines - 1) return 0;
        int q = 0;
        for (int i = k + 1; i < getNbMachines(); i++) q += getJob(j).getDureeOperation(i);
        return q;
    }

    // calcul de la somme des durées des opérations exécutées sur la machine k
    public int calculerDureeJobs(int k) {
        int sum = 0;
        for (int j = 0; j < getNbJobs(); j++) sum += getJob(j).getDureeOperation(k);
        return sum;
    }

    public int calculerBorneInf(JobsList lJobs) {
        int max = calculerBorneInf(0, lJobs);
        for (int k = 1; k < nbMachines; k++) max = Math.max(calculerBorneInf(k, lJobs), max);
        return max;
    }

    public int calculerBorneInf(int k, JobsList lJobs) {
        int res = 0;

        int min = Integer.MAX_VALUE;
        for (Job job : lJobs) min = Math.min(calculerDateDispo(k, job.getNumero()), min);

        res += min;

        min = Integer.MAX_VALUE;
        for (Job job : lJobs) min = Math.min(calculerDureeLatence(k, job.getNumero()), min);

        return res + min + calculerDureeJobs(k);
    }

    /************************************************
     / exo 7
     /************************************************/

    // calcul de r_kj tenant compte d'un ordo en cours
    public int calculerDateDispo(Solution o, int k, int j) {
        if (k == 0) return o.getDateDisponibilite(0);
        int pred = calculerDateDispo(o, k - 1, j) + getJob(j).getDureeOperation(k - 1);
        return Math.max(o.getDateDisponibilite(k), pred);
    }

    // calcul de la somme des durées des opérations d'une liste
    // exécutées sur la machine k
    public int calculerDureeJobs(int k, JobsList l) {
        int sum = 0;
        for (Job job : l) sum += job.getDureeOperation(k);
        return sum;
    }

    public int calculerBorneInf(Solution o, JobsList lJobs) {
        int max = calculerBorneInf(o, 0, lJobs);
        for (int k = 1; k < nbMachines; k++) max = Math.max(calculerBorneInf(o, k, lJobs), max);
        return max;
    }

    public int calculerBorneInf(Solution o, int k, JobsList lJobs) {
        int res = 0;

        int min = Integer.MAX_VALUE;
        for (Job job : lJobs) min = Math.min(calculerDateDispo(o, k, job.getNumero()), min);
        res += min;

        min = Integer.MAX_VALUE;
        for (Job job : lJobs) min = Math.min(calculerDureeLatence(k, job.getNumero()), min);

        return res + min + calculerDureeJobs(k, lJobs);
    }

    /************************************************
     / exo 8
     /************************************************/

    // procédure par évaluation et séparation
//    public void EvaluationSeparation() {
//        int compteur = 0;
//        int cmax = Integer.MAX_VALUE;
//        Solution meilleurOrdonnancement = null;
//
//        NodesPriorityQueue fp = new NodesPriorityQueue();
//        int bInf = calculerBorneInf(creerListeJobs());
//        System.out.println("LB : " + bInf);
//        fp.ajouterSommet(new Node(new Solution(nbMachines), creerListeJobs(), bInf, compteur++));
//
//        while (!fp.estVide()) {
//            Node s = fp.recupererTete();
//            for (Job nonPlace : s.getNonPlaces()) {
//                Solution o = s.getOrdonnancement().clone();
//                o.ordonnancerJob(nonPlace);
//
//                JobsList nonPlaces = new JobsList();
//                for (Job nonPlace2 : s.getNonPlaces()) {
//                    if (nonPlace2.equals(nonPlace)) continue;
//                    nonPlaces.ajouterJob(nonPlace2);
//                }
//
//                if (nonPlaces.nombreJobs() == 0) {
//                    if (cmax > o.getDuree()) {
//                        cmax = o.getDuree();
//                        meilleurOrdonnancement = o;
//                        System.out.println("Nouveau cmax : " + cmax);
//                    }
//                } else {
//                    bInf = calculerBorneInf(o, nonPlaces);
//                    if (bInf < cmax) fp.ajouterSommet(new Node(o, nonPlaces, bInf, compteur++));
//                }
//            }
//        }
//
//        meilleurOrdonnancement.afficher();
//    }

    public Solution generateRandomSolution() {
        Solution random = new Solution(nbMachines);
        ArrayList<Job> shuffle = new ArrayList<>(Arrays.asList(jobs));
        Collections.shuffle(shuffle);
        shuffle.forEach(random::ordonnancerJob);
        random.getScore();
        return random;
    }
}