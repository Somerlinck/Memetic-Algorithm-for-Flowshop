package maff;

import maff.convergence_criterions.ConvergenceCriterion;
import maff.operators.Operator;

import java.util.ArrayList;

/**
 * Population based search engine
 */
public abstract class PBSE {

    private Population population;
    private ArrayList<Operator> operators;
    private ConvergenceCriterion convergenceCriterion;

    public PBSE(ArrayList<Operator> operators, ConvergenceCriterion convergenceCriterion) {
        this.operators = operators;
        this.convergenceCriterion = convergenceCriterion;
    }

    public abstract Population generateInitialPopulation();

    public abstract Population restartPopulation(Population population);

    public abstract boolean hasFinished(Population population);

    public void updatePopulation(Population population) {
        for (Operator operator : operators) population = operator.apply(population);
    }

    public void search() {
        population = generateInitialPopulation();
        while (!hasFinished(population)) {
            updatePopulation(population);
            if (convergenceCriterion.hasConverged(population)) population = restartPopulation(population);
        }
    }

}
