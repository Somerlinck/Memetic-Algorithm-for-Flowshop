package maff.operators;

import maff.model.Solution;

import java.util.TreeSet;

public abstract class Operator {

    public TreeSet<Solution> apply(TreeSet<Solution> population) {
        System.out.println("Size: " + population.size()+"; "+getClass().getSimpleName());
        TreeSet<Solution> unsorted = abstractApply(population);
        unsorted.forEach(Solution::reset);
        TreeSet<Solution> res = new TreeSet<>();
        res.addAll(unsorted);
        return res;
    }

    protected abstract TreeSet<Solution> abstractApply(TreeSet<Solution> population);

}
