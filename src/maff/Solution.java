package maff;

import maff.model.ListeJobs;

public interface Solution {

    float getScore();
    
    public ListeJobs getSequence();
    
    public int getNbMachines();

}
