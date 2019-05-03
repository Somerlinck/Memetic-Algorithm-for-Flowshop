package maff;

import maff.convergence_criterions.ConvergenceCriterion;
import maff.operators.Operator;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Solution based search engine
 */
public abstract class PBSE {

    private TreeSet<Solution> population;

    private Problem problem;
    private int populationSize;
    private Operator populationGenerator;
    private ArrayList<Operator> operators;
    private ConvergenceCriterion convergenceCriterion;

    public PBSE(Problem problem, int populationSize, Operator populationGenerator, ArrayList<Operator> operators, ConvergenceCriterion convergenceCriterion) {
        this.problem = problem;
        this.populationSize = populationSize;
        this.populationGenerator = populationGenerator;
        this.operators = operators;
        this.convergenceCriterion = convergenceCriterion;
    }

    public abstract boolean hasFinished(TreeSet<Solution> solution);


    public void search() {
        System.out.println("Solution based search-engine initialization");
        System.out.println("Using " + operators.size() + " operators:");
        System.out.println(operators);
        System.out.println("Using " + convergenceCriterion + " convergence criterion");

        generateInitialPopulation();
        System.out.println("Solution initialized");
        System.out.println("Searching...");

        while (!hasFinished(population)) {
            updatePopulation(population);
            if (convergenceCriterion.hasConverged(population)) restartPopulation(population);
        }

        System.out.println("Search finished!");
        Solution best = getBestSolution();
        System.out.println("Best solution comes with  score of " + best.getScore() + ":");
        System.out.println(best);
    }

    public void generateInitialPopulation() {
        population = new TreeSet<>();
        for (int i = 0; i < populationSize; i++) population.add(problem.generateRandomSolution());
        population = populationGenerator.apply(population);
    }

    public void updatePopulation(TreeSet<Solution> solution) {
        for (Operator operator : operators) solution = operator.apply(solution);
    }

    // TODO implement me
    public void restartPopulation(TreeSet<Solution> population) {
        if (true) this.population = commaStrategy(population);
        else this.population = plusStrategy(population);
    }

    // TODO implement me
    public TreeSet<Solution> commaStrategy(TreeSet<Solution> population) {
        return null;
    }

    // TODO implement me
    public TreeSet<Solution> plusStrategy(TreeSet<Solution> population) {
        return null;
    }

    public Solution getBestSolution() {
        return population.first();
    }

}
