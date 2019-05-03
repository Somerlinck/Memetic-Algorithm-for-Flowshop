package maff.model;/*
 * Nom de classe : Ordonnancement
 *
 * Description :
 *
 * Version : 1.0
 *
 * Date : 21/09/2010
 *
 * Auteur : Chams LAHLOU
 */

//import java.util.*; // nécessaire pour exo 2

import maff.Solution;

public class Ordonnancement implements Cloneable, Solution {
    private ListeJobs sequence;        // ordre des jobs dans l'ordonnancement
    private int nbMachines;            // nombre de machines
    private int duree;                // duree totale
    private int[] dateDisponibilite;// date de disponibilite sur chaque machine

    public static void main(String[] args) {
        ListeJobs l = new ListeJobs();
        l.ajouterJob(new Job(0, new int[]{1, 2, 3}));
        l.ajouterJob(new Job(1, new int[]{1, 2, 3}));
        l.ajouterJob(new Job(2, new int[]{1, 3, 3}));
        new Ordonnancement(l, 3).afficher();
    }

    // constructeur par défaut
    public Ordonnancement() {
        sequence = new ListeJobs();
        nbMachines = 0;
        dateDisponibilite = new int[0];
    }

    // crée un ordonnancement vierge sur m machines
    public Ordonnancement(int m) {
        sequence = new ListeJobs();
        nbMachines = m;
        dateDisponibilite = new int[nbMachines];
        for (int i = 0; i < nbMachines; i++) {
            dateDisponibilite[i] = 0; // machines disponibles à l'instant 0
        }
    }

    /************************************************
     / exo 4
     /************************************************/

    // crée un ordonnancement à partir d'une liste de jobs sur m machines
    // les jobs sont exécutés dans l'ordre de la liste
    public Ordonnancement(ListeJobs l, int m) {
        sequence = new ListeJobs();
        nbMachines = m;
        dateDisponibilite = new int[nbMachines];
        ordonnancer(l);
    }

    public int getDuree() {
        calculerDuree();
        return duree;
    }
    
    public int getNbMachines() {
    	return this.nbMachines;
    }

    public ListeJobs getSequence() {
        return sequence;
    }

    public int getDateDisponibilite(int i) {
        return dateDisponibilite[i];
    }

    public void ajouterJob(Job j) {
        sequence.ajouterJob(j);
    }

    public void initialiser() { // mise à zéro de l'ordonnancement
        for (Job j : sequence) for (int i = 0; i < nbMachines; i++) j.setDateDebut(i, -1); // opérations non exécutées
        for (int i = 0; i < nbMachines; i++) dateDisponibilite[i] = 0; // machines disponibles à l'instant 0
    }

    public void afficher() { // affiche l'ordonnancement
        sequence.afficher();
        for (Job j : sequence) {
            System.out.print("Job " + j.getNumero() + " : ");
            for (int i = 0; i < nbMachines; i++) {
                System.out.print("(op " + i + " à t = "
                        + j.getDateDebut(i) + ") ");
            }
            System.out.println();
        }
        System.out.println("Cmax = " + getDuree());
    }

    public Ordonnancement clone() {
        Ordonnancement o = null;
        try {
            o = (Ordonnancement) super.clone();
        } catch (CloneNotSupportedException cnse) {
            cnse.printStackTrace(System.err);
        }
        // copie de la liste des jobs : nécessaire !
        o.sequence = sequence.clone();
        // copie du tableau
        o.dateDisponibilite = dateDisponibilite.clone();
        return o;
    }

    /************************************************
     / exo 2
     /************************************************/

    public void ordonnancerJob(Job j) { // ordonnance un job en fonction de ce qui est déjà ordonnancé
        ajouterJob(j);
        j.setDateDebut(0, dateDisponibilite[0]);
        dateDisponibilite[0] += j.getDureeOperation(0);
        for (int i = 1; i < j.getNbOperations(); i++) {
            int datemin = Math.max(getDateDisponibilite(i - 1), getDateDisponibilite(i));
            j.setDateDebut(i, datemin);
            dateDisponibilite[i] = datemin + j.getDureeOperation(i);
        }
    }

    private void calculerDuree() {
        duree = 0;
        for (int i = 0; i < dateDisponibilite.length; i++)
            if (dateDisponibilite[i] > duree) duree = dateDisponibilite[i];
    }

    /************************************************
     / exo 3
     /************************************************/

    public void ordonnancer(ListeJobs l) { // ordonnance les jobs de la liste
        l.forEach(this::ordonnancerJob);
    }


    @Override
    public float getScore() {
        return getDuree();
    }
}