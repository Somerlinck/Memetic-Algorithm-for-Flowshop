package maff;

import maff.convergence_criterions.ConvergenceCriterion;
import maff.operators.Operator;

import java.util.ArrayList;

/**
 * Solution based search engine
 */
public abstract class PBSE {

    private ArrayList<Solution> population;

    private Problem problem;
    private int size;
    private Operator populationGenerator;
    private ArrayList<Operator> operators;
    private ConvergenceCriterion convergenceCriterion;

    public PBSE(Problem problem, int size, Operator populationGenerator, ArrayList<Operator> operators, ConvergenceCriterion convergenceCriterion) {
        this.problem = problem;
        this.size = size;
        this.populationGenerator = populationGenerator;
        this.operators = operators;
        this.convergenceCriterion = convergenceCriterion;
    }

    public abstract boolean hasFinished(ArrayList<Solution> solution);



    public void search(boolean maximize) {
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
        Solution best = getBestSolution(maximize);
        System.out.println("Best solution comes with  score of " + best.getScore() + ":");
        System.out.println(best);
    }

    public void generateInitialPopulation() {
        population = new ArrayList<>();
        for (int i = 0; i < size; i++) population.add(problem.generateRandomSolution());
        population = populationGenerator.apply(population);
    }

    public void updatePopulation(ArrayList<Solution> solution) {
        for (Operator operator : operators) solution = operator.apply(solution);
    }

    // TODO implement me
    public void restartPopulation(ArrayList<Solution> population) {
        if (true) this.population = commaStrategy(population);
        else this.population = plusStrategy(population);
    }

    // TODO implement me
    public ArrayList<Solution> commaStrategy(ArrayList<Solution> population) {
        return null;
    }

    // TODO implement me
    public ArrayList<Solution> plusStrategy(ArrayList<Solution> population) {
        return null;
    }

    public Solution getBestSolution(boolean maximize) {
        return maximize ? getMaxSolution() : getMinSolution();
    }

    public Solution getMinSolution() {
        Solution min = population.get(0);
        for (int i = 1; i < population.size(); i++)
            if (population.get(i).getScore() < min.getScore()) min = population.get(i);
        return min;
    }

    public Solution getMaxSolution() {
        Solution max = population.get(0);
        for (int i = 1; i < population.size(); i++)
            if (population.get(i).getScore() > max.getScore()) max = population.get(i);
        return max;
    }

}
