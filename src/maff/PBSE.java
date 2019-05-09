package maff;

import maff.convergence_criterions.ConvergenceCriterion;
import maff.convergence_criterions.ShannonsEntropy;
import maff.finishing_criterions.FinishingCriterion;
import maff.finishing_criterions.IterationCount;
import maff.model.Problem;
import maff.model.Solution;
import maff.operators.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

/**
 * Solution based search engine
 */
public class PBSE {

    private TreeSet<Solution> population;
    private TreeSet<Solution> oldPopulation;

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
                new ArrayList<>(Arrays.asList(
                        new Selection(),
                        new Reproduction(),
                        new LocalSearch(),
                        new Mutation(),
                        new LocalSearch())),
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
        System.out.println("Population Based Search Engine initialization");
        System.out.println("Using " + operators.size() + " operators:");
        System.out.println();
        for (Operator operator : operators) System.out.println("\t -" + operator.getClass().getSimpleName());
        System.out.println();
        System.out.println("Using " + convergenceCriterion.getClass().getSimpleName() + " convergence criterion");
        System.out.println("Using " + finishingCriterion.getClass().getSimpleName() + " finishing criterion");
        System.out.println();

        generateInitialPopulation();
        System.out.println("Solution initialized");
        System.out.println("Searching...");
        System.out.println();

        while (!finishingCriterion.hasFinished(population)) {
            updatePopulation();
            if (convergenceCriterion.hasConverged(population)) {
                restartPopulation();
                oldPopulation = new TreeSet<>();
                for (Solution solution : population) oldPopulation.add(solution.clone());
            }
        }

        System.out.println("Search finished!");
        Solution best = getBestSolution();
        System.out.println("Best solution comes with a score of " + best.getScore() + ":");
        System.out.println();
        System.out.println(best);
    }

    private void generateInitialPopulation() {
        population = new TreeSet<>();
        for (int i = 0; i < populationSize; i++) population.add(problem.generateRandomSolution());
        population = populationGenerator.apply(population);
    }

    private void updatePopulation() {
        for (Operator operator : operators) population = operator.apply(population);
    }

    // TODO implement me
    private void restartPopulation() {
        if (true) commaStrategy();
        else plusStrategy();
    }

    private void commaStrategy() {

    }

    private void plusStrategy() {
        population.addAll(oldPopulation);
        int size = population.size();
        for (int i = 0; i < size; i++) population.pollLast();
    }

    public Solution getBestSolution() {
        return population.first();
    }

}
