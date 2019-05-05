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

import java.util.*;

public class NodesPriorityQueue implements Iterable<Node> {
    private PriorityQueue<Node> file; // file de priorité des sommets
    
    // constructeur par défaut
    public NodesPriorityQueue() {
    	file = new PriorityQueue<Node>();
    }
    
    // renvoie la tête
    public Node tete() {
    	return file.peek();
    }
    
    // renvoie et supprime la tête
    public Node recupererTete() {
    	return file.poll();
    }
    
    public int taille() {
    	return file.size();
    }
    
    public void ajouterSommet(Node s) {
    	file.add(s);
    }
    
    public boolean estVide() {
    	if (file.size() == 0) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    // pour utiliser le "foreach"
    public Iterator<Node> iterator() {
        Iterator<Node> iSommet = file.iterator();
        return iSommet;
    }
}