package maff.operators;

import maff.model.Solution;
import maff.model.JobsList;
import java.util.TreeSet;

public class LocalSearch extends Operator {

    @Override
    public TreeSet<Solution> abstractApply(TreeSet<Solution> solutions) {
    	TreeSet<Solution> neighbors = new TreeSet<>();
    	for(Solution solution : solutions) {
    		neighbors.add(findBestNeighbor(solution));
    	}
        return neighbors;
    }

    public Solution findBestNeighbor(Solution solution) {
    	Solution best = solution;
    	int length = solution.getSequence().nombreJobs();
    	for(int i = 0 ; i < length-1 ; i++) {
    		for(int j = i+1 ; j < length ; j++) {
    			Solution swap = swap(i,j,solution);
    			if(swap.getScore() < best.getScore()) best = swap;
    		}
    	}
		return best;
    }

    public Solution swap(int i, int j, Solution solution) {
    	Solution ordo = solution;
    	JobsList jobs = ordo.getSequence();
    	Solution res = new Solution(ordo.getNbMachines());
    	for(int k = 0 ; k < jobs.nombreJobs() ; k++) {
    		if(k==i) res.ajouterJob(jobs.getJob(j));
    		if(k==j) res.ajouterJob(jobs.getJob(i));
    		else res.ajouterJob(jobs.getJob(k));
    	}
    	return res;
    }

}
