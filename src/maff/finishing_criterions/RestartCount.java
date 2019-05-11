package maff.finishing_criterions;

import java.util.TreeSet;

import maff.PBSE;
import maff.model.Solution;

public class RestartCount implements FinishingCriterion {
	private int max;

	public RestartCount(int max) {
		this.max = max;
	}

	@Override
	public boolean hasFinished(TreeSet<Solution> population) {
		// TODO Auto-generated method stub
		return PBSE.restartCount >= max;
	}

}
