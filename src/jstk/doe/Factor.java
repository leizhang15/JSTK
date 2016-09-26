/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jstk.doe;

/**
 * A factor is for one input parameter of the experiment. Need to specify the 
 * levels to experiment
 * @author nozomihitomi
 */
public class Factor {
    
    /**
     * The levels to test for this factor
     */
    private final double[] levels;
    
    /**
     * The name of the factor
     */
    private final String name;

    /**
     * Specify the levels to test for this factor
     * @param name the name of the factor
     * @param levels 
     */
    public Factor(String name,double[] levels) {
        this.levels = levels;
        this.name = name;
    }
    
    public double[] getLevels(){
        return levels;
    }
    
    /**
     * Returns the number of levels for this factor
     * @return 
     */
    public int getNumLevels(){
        return levels.length;
    }
    
    /**
     * Returns the ith level of this factor
     * @param i
     * @return 
     */
    public Double getLevel(int i){
        return levels[i];
    }
    
    public String getName(){
        return name;
    }
}
