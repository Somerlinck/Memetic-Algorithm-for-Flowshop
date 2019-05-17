package maff.operators;

import maff.model.Solution;

import java.util.TreeSet;

public abstract class Operator {

    public TreeSet<Solution> apply(TreeSet<Solution> population) {
        TreeSet<Solution> unsorted = abstractApply(population);
        unsorted.forEach(Solution::reset);
        TreeSet<Solution> res = new TreeSet<>();
        res.addAll(unsorted);
        return res;
    }

    protected abstract TreeSet<Solution> abstractApply(TreeSet<Solution> population);

}
