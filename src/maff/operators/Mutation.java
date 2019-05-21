package maff.operators;

import maff.model.Job;
import maff.model.JobsList;
import maff.model.Solution;

import java.util.TreeSet;

public class Mutation extends Operator {

    private float mutationRate;

    public Mutation() {
        this(0.001f);
    }

    public Mutation(float mutationRate) {
        this.mutationRate = mutationRate;
    }

    @Override
    public TreeSet<Solution> abstractApply(TreeSet<Solution> population) {
        TreeSet<Solution> pop = new TreeSet<>();
        for (Solution solution : population) pop.add(solution.clone());
        boolean first = true;
        for (Solution solution : pop) {
            // We don't want to alter the best solution
            if(first) {
                first = false;
                continue;
            }
            for (int i = 0; i < solution.getSequence().nombreJobs(); i++) {
                if (Math.random() > mutationRate) continue;
                swap(i, solution.getSequence());
//                insert(i, solution.getSequence());
            }
        }
        return pop;
    }

    private static void swap(int i, JobsList jobsList) {
        int j = (int) (Math.random() * jobsList.nombreJobs());
        if (j == i) j = (j + 1) % jobsList.nombreJobs();

        Job jobi = jobsList.getJob(i);
        Job jobj = jobsList.getJob(j);

        jobsList.supprimerJob(i);
        jobsList.ajouterJob(jobj, i);

        jobsList.supprimerJob(j);
        jobsList.ajouterJob(jobi, j);
    }

    private static void insert(int i, JobsList jobsList) {
        int j = (int) (Math.random() * jobsList.nombreJobs());
        if (j == i) j = (j + 1) % jobsList.nombreJobs();

        Job jobi = jobsList.getJob(i);

        jobsList.supprimerJob(jobi);
        jobsList.ajouterJob(jobi, j);
    }

    public static void main(String[] args) {
        JobsList jobs = new JobsList();
        jobs.ajouterJob(new Job(0, new int[0]));
        jobs.ajouterJob(new Job(1, new int[0]));
        jobs.ajouterJob(new Job(2, new int[0]));
        jobs.ajouterJob(new Job(3, new int[0]));
        jobs.ajouterJob(new Job(4, new int[0]));
        jobs.ajouterJob(new Job(5, new int[0]));

        jobs.afficher();

        swap(5, jobs);
//        insert(5, jobs);

        jobs.afficher();
    }

}
