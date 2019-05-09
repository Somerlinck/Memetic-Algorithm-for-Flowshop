package maff;

import maff.convergence_criterions.ConvergenceCriterion;
import maff.convergence_criterions.ShannonsEntropy;
import maff.finishing_criterions.FinishingCriterion;
import maff.finishing_criterions.IterationCount;
import maff.model.Problem;
import maff.model.Solution;
import maff.operators.LocalSearch;
import maff.operators.Mutation;
import maff.operators.Operator;
import maff.operators.Reproduction;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Solution based search engine
 */
public class PBSE {

    private TreeSet<Solution> population;

    private Problem problem;
    private int populationSize;
    private Operator populationGenerator;
    private ArrayList<Operator> operators;
    private ConvergenceCriterion convergenceCriterion;
    private FinishingCriterion finishingCriterion;


    public PBSE(Problem problem) {
        this(
                problem,
                100,
                new LocalSearch(),
                new ArrayList<>() {{
                    add(new Reproduction());
                    add(new LocalSearch());
                    add(new Mutation());
                    add(new LocalSearch());
                }},
                new ShannonsEntropy(),
                new IterationCount(100));
    }

    public PBSE(Problem problem, int populationSize, Operator populationGenerator, ArrayList<Operator> operators) {
        this(problem, populationSize, populationGenerator, operators, new ShannonsEntropy(), new IterationCount(100));
    }

    public PBSE(Problem problem, int populationSize, Operator populationGenerator, ArrayList<Operator> operators, ConvergenceCriterion convergenceCriterion, FinishingCriterion finishingCriterion) {
        this.problem = problem;
        this.populationSize = populationSize;
        this.populationGenerator = populationGenerator;
        this.operators = operators;
        this.convergenceCriterion = convergenceCriterion;
        this.finishingCriterion = finishingCriterion;
    }

    public void search() {
        System.out.println("Solution based search-engine initialization");
        System.out.println("Using " + operators.size() + " operators:");
        System.out.println(operators);
        System.out.println("Using " + convergenceCriterion + " convergence criterion");

        generateInitialPopulation();
        System.out.println("Solution initialized");
        System.out.println("Searching...");

        while (!finishingCriterion.hasFinished(population)) {
            updatePopulation(population);
            if (convergenceCriterion.hasConverged(population)) restartPopulation(population);
        }

        System.out.println("Search finished!");
        Solution best = getBestSolution();
        System.out.println("Best solution comes with  score of " + best.getScore() + ":");
        System.out.println(best);
    }

    private void generateInitialPopulation() {
        population = new TreeSet<>();
        for (int i = 0; i < populationSize; i++) population.add(problem.generateRandomSolution());
        population = populationGenerator.apply(population);
    }

    private void updatePopulation(TreeSet<Solution> solution) {
        for (Operator operator : operators) solution = operator.apply(solution);
    }

    // TODO implement me
    private void restartPopulation(TreeSet<Solution> population) {
        if (true) this.population = commaStrategy(population);
        else this.population = plusStrategy(population);
    }

    // TODO implement me
    private TreeSet<Solution> commaStrategy(TreeSet<Solution> population) {
        return null;
    }

    // TODO implement me
    private TreeSet<Solution> plusStrategy(TreeSet<Solution> population) {
        return null;
    }

    public Solution getBestSolution() {
        return population.first();
    }

}
