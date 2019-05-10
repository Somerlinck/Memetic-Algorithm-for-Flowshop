package maff.operators;

import maff.model.Problem;
import maff.model.Solution;

import java.util.TreeSet;

public class Reproduction implements Operator {

    @Override
    public TreeSet<Solution> apply(TreeSet<Solution> population) {
        TreeSet<Solution> pop = new TreeSet<>();
        random(population, pop);
        return pop;
    }

    private static void weighting(TreeSet<Solution> population, TreeSet<Solution> newPopulation) {
        int totalScore = 0;
        for (Solution solution : population) totalScore += solution.getScore();

        for (int i = 0; i < population.size(); i++) {
            int index = (int) (Math.random() * totalScore);
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

    public static void main(String[] args) {
        Problem problem = new Problem("res/tai01.txt");

        TreeSet<Solution> population = new TreeSet<>();
        for (int i = 0; i < 5; i++) population.add(problem.generateRandomSolution());

        for (Solution solution : population) solution.getSequence().afficher();
        System.out.println();

        TreeSet<Solution> newPopulation = new TreeSet<>();
        random(population, newPopulation);

        for (Solution solution : newPopulation) solution.getSequence().afficher();
    }

}
