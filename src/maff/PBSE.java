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

    // Memo :
    // TreeSets excludes duplicates
    // Therefore we put new solutions in restartPopulation to match populationSize
    // This prevents, for instance, applying the same determinist algorithm to identical solutions
    private TreeSet<Solution> population;
    private TreeSet<Solution> oldPopulation;

    private Problem problem;
    private int populationSize;
    private Operator populationGenerator;
    private ArrayList<Operator> operators;
    private ConvergenceCriterion convergenceCriterion;
    private FinishingCriterion finishingCriterion;
    
    public static int restartCount = 0;


    public PBSE(Problem problem) {
        this(
                problem,
                100,
                new LocalSearch(),
                new ArrayList<>(Arrays.asList(
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
                System.out.println("Current best score: " + population.first().getScore());
                oldPopulation = new TreeSet<>();
                for (Solution solution : population) oldPopulation.add(solution.clone());
                restartPopulation();
            }
        }

        System.out.println("Search finished!");
        Solution best = getBestSolution();
        System.out.println("Best solution comes with a score of " + best.getScore() + ":");
        System.out.println();
        best.afficher();
    }

    private void generateInitialPopulation() {
        population = new TreeSet<>();
        while(population.size()<populationSize) population.add(problem.generateRandomSolution());
        population = populationGenerator.apply(population);
        oldPopulation = new TreeSet<>();
        for (Solution solution : population) oldPopulation.add(solution.clone());
    }

    private void updatePopulation() {
        for (Operator operator : operators) population = operator.apply(population);
    }

    // TODO implement me
    private void restartPopulation() {
    	restartCount++;
        if (false) commaStrategy();
        else plusStrategy();
        while(population.size()<populationSize) population.add(problem.generateRandomSolution());
        population = populationGenerator.apply(population);
    }

	private void commaStrategy() {
    }

    private void plusStrategy() {
        int size = population.size();
        population.addAll(oldPopulation);
        for (int i = 0; i < size; i++) population.pollLast();
    }

    public Solution getBestSolution() {
        return population.first();
    }

}
