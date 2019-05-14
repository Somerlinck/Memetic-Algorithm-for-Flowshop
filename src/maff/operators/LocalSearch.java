package maff.operators;

import maff.model.Solution;
import maff.PBSE;
import maff.model.JobsList;
import maff.model.Problem;

import java.util.TreeSet;
public class LocalSearch extends Operator {
	private static int step = 0;

    @Override
    public TreeSet<Solution> abstractApply(TreeSet<Solution> solutions) {
    	TreeSet<Solution> neighbors = new TreeSet<>();
    	for(Solution solution : solutions) {
    		Solution bestNeighbor = findBestNeighbor(solution);
    		neighbors.add(bestNeighbor);
    	}
    	step++;
        return neighbors;
    }

    private Solution findBestNeighbor(Solution solution) {
    	Solution bestNeighbor = solution;
    	int length = solution.getSequence().nombreJobs();
    	if(step <= 1) {
    	for(int i = 0 ; i < length-1 ; i++) {
    		for(int j = i+1 ; j < length ; j++) {
    			Solution swap = swap(i,j,solution);
    			swap.reset();
    			if(swap.getScore() < bestNeighbor.getScore()) {return swap;}
    		}
    	}
    	}
    	return bestNeighbor;
    }

    private Solution swap(int i, int j, Solution solution) {
    	JobsList jobs = solution.getSequence();
    	Solution swap = new Solution(solution.getNbMachines());
    	for(int k = 0 ; k < jobs.nombreJobs() ; k++) {
    		if(k==i) swap.ajouterJob(jobs.getJob(j));
    		else {if(k==j) swap.ajouterJob(jobs.getJob(i));
    		else swap.ajouterJob(jobs.getJob(k));
    		}
    	}
    	return swap;
    }

}
