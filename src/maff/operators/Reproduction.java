package maff.operators;

import maff.model.Problem;
import maff.model.Solution;

import java.util.Random;
import java.util.TreeSet;

public class Reproduction extends Operator {

    private static Random random = new Random();

    @Override
    public TreeSet<Solution> abstractApply(TreeSet<Solution> population) {
        TreeSet<Solution> pop = new TreeSet<>();
        // We wan't to keep the best solution
        pop.add(population.first());
//        weighting(population, pop);
        random(population, pop);
        return pop;
    }

    private static void weighting(TreeSet<Solution> population, TreeSet<Solution> newPopulation) {
        int totalScore = 0;
        for (Solution solution : population) totalScore += solution.getScore();

        for (int i = 0; i < population.size(); i++) {
            int index = (int) (random.nextFloat() * totalScore);
            int count = 0;
            for (Solution solution : population) {
                count += solution.getScore();
                if (count >= index) {
                    newPopulation.add(solution);
                    break;
                }
            }
        }
    }

    private static void random(TreeSet<Solution> population, TreeSet<Solution> newPopulation) {
        Object[] array = population.toArray();
        for (int i = 0; i < population.size(); i++)
            newPopulation.add((Solution) array[(int) (Math.random() * array.length)]);
    }

}
