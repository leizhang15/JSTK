/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jstk.doe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * This class creates a collection of constellations to run by enumerating the 
 * desired experiments
 * @author nozomihitomi
 */
public class ExperimentFactory {
    
    private final ArrayList<Factor> factors;

    public ExperimentFactory(Collection<Factor> factors) {
        this.factors = new ArrayList(factors);
    }

    public Collection<Factor> getFactors() {
        return factors;
    }
    
    /**
     * Will create full factorial design of experiments. Code is based on 2013a Matlab fullfact(levels)
     * @return a matrix with a full factorial design of experiments. Rows of the matrix are experiments.
     */
    public int[][] fullfact(){
        Iterator<Factor> iter = factors.iterator();
        int numExp = 1;
        while(iter.hasNext()){
            numExp*=iter.next().getNumLevels();
        }
        int[][] experiments = new int[numExp][factors.size()];
        
        int ncycles = numExp;
        
        for(int k=0;k<factors.size();k++){
            int numLevels4kthFactor = factors.get(k).getNumLevels();
            int nreps = numExp/ncycles;
            ncycles = ncycles/numLevels4kthFactor;
            int[] settingReps = new int[nreps*numLevels4kthFactor];
            int index = 0;
            for(int j=0;j<numLevels4kthFactor;j++){
                for(int i=0;i<nreps;i++){
                    settingReps[index] = j;
                    index++;
                }
            }
            
            index = 0;
            for(int j=0;j<ncycles;j++){
                for(int i=0;i<settingReps.length;i++){
                    experiments[index][k] = settingReps[i];
                    index++;
                }
            }
        }
        return experiments;
    }
    
    
}
