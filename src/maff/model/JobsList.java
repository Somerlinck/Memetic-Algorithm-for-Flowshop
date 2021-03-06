package maff.model;/*
 * Nom de classe : ListeJobs
 *
 * Description :
 *
 * Version : 1.0
 *
 * Date : 21/09/2010
 *
 * Auteur : Chams LAHLOU
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class JobsList implements Cloneable, Iterable<Job> {
    private List<Job> liste;                // liste des éléments

    // constructeur par défaut
    public JobsList() {
        liste = new ArrayList<Job>();        // liste vide
    }

    public Job getJob(int i) {
        return liste.get(i);
    }

    public Job pop(int i) {
        Job res = getJob(i);
        supprimerJob(i);
        return res;
    }

    public void ajouterJob(Job j) {
//        if(liste.contains(j)) throw new Error("Job already in job list!");
        liste.add(j);
    }

    public void ajouterJob(Job j, int i) {        // ajoute à la position i
//        if(liste.contains(j)) throw new Error("Job already in job list!");
        liste.add(i, j);
    }

    public void supprimerJob(Job j) {
        liste.remove(j);
    }

    public void supprimerJob(int i) {
        liste.remove(i);
    }

    public void vider() {
        liste.clear();
    }

    public int nombreJobs() {
        return liste.size();
    }

    public int position(Job j) {
        return liste.indexOf(j);
    }

    public void afficher() {            // affiche la liste des jobs
        System.out.print("( ");
        for (Job j : liste) {
            System.out.print(j.getNumero() + " ");
        }
        System.out.println(")");
    }

    public void trierDureesDecroissantes() { // renvoie une liste triée selon critère NEH
        Collections.sort(liste, Collections.reverseOrder()); // on trie selon durées décroissantes
    }

    // pour utiliser le "foreach"
    public Iterator<Job> iterator() {
        Iterator<Job> iJob = liste.iterator();
        return iJob;
    }

    // pour créer une copie
    public JobsList clone() {
        JobsList l = null;
        try {
            l = (JobsList) super.clone();
        } catch (CloneNotSupportedException cnse) {
            cnse.printStackTrace(System.err);
        }
        // copie de la liste des jobs : nécessaire !
        l.liste = new ArrayList<Job>();
        for (Job j : liste) {
            l.liste.add(j.clone());
        }
        return l;
    }

    public Boolean contains(Job j) {
        return this.liste.contains(j);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof JobsList)) return false;
        JobsList other = (JobsList) o;
        for (int i = 0; i < nombreJobs(); i++) if (!getJob(i).equals(other.getJob(i))) return false;
        return true;
    }

}