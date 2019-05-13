package maff.operators;

import maff.model.Solution;
import maff.PBSE;
import maff.model.JobsList;
import maff.model.Problem;

import java.util.TreeSet;
public class LocalSearch extends Operator {

    @Override
    public TreeSet<Solution> abstractApply(TreeSet<Solution> solutions) {
    	TreeSet<Solution> neighbors = new TreeSet<>();
    	for(Solution solution : solutions) {
    		Solution best = findBestNeighbor(solution);
    		best.reset();
    		neighbors.add(best);
    	}
        return neighbors;
    }

    private Solution findBestNeighbor(Solution solution) {
    	Solution best = solution;
    	int length = solution.getSequence().nombreJobs();
    	for(int i = 0 ; i < length-1 ; i++) {
    		for(int j = i+1 ; j < length ; j++) {
    			Solution swap = swap(i,j,solution);
    			if(swap.getScore() < best.getScore()) {best = swap;}
    		}
    	}
		return best;
    }

    private Solution swap(int i, int j, Solution solution) {
    	JobsList jobs = solution.getSequence();
    	Solution res = new Solution(solution.getNbMachines());
    	for(int k = 0 ; k < jobs.nombreJobs() ; k++) {
    		if(k==i) res.ajouterJob(jobs.getJob(j));
    		else {if(k==j) res.ajouterJob(jobs.getJob(i));
    		else res.ajouterJob(jobs.getJob(k));
    		}
    	}
    	return res;
    }

}
