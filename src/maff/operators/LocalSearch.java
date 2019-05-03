package maff.operators;

import maff.Solution;
import maff.model.ListeJobs;
import maff.model.Ordonnancement;

import java.util.ArrayList;

public class LocalSearch implements Operator {
    @Override
    public ArrayList<Solution> apply(ArrayList<Solution> solution) {
        return null;
    }
    
    public Solution findBestNeighbor(Solution solution) {
    	
		return null;    	
    }
    
    public Solution swap(int i, int j, Solution solution) {
    	ListeJobs jobs = solution.getSequence();
    	Solution res = new Ordonnancement(solution.getNbMachines());
    	for(int k = 0 ; k <= jobs.nombreJobs() ; k++) {
    		switch(k) {
    		case i :
    		case j :
    		case default : 
    		}
    	}
    	return res;
    }
    
}
