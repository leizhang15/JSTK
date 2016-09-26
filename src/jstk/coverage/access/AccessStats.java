/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jstk.coverage.access;

import jstk.coverage.Stats;

/**
 * Stores statistics on the coverage access for a series of access durations
 * @author nozomihitomi
 */
public class AccessStats extends Stats{
    
    /**
     * Maintains a representative portion of the accesses used for cumsum metric
     */
    private final double[] accDuration;
    
    /**
     * Sum that is less than some threshold 
     */
    private double violatedSum;
    
    /**
     * The number of points considered in the statistic
     */
    private final int numPoints;
    
    /**
     * Computes several statistics including the mean, median, cdf, maximum, minimum and standarad deviation.
     * @param accesstimes an array of access times to apply statistics
     * @param numPoints the number of coverage grid points that is included in this analysis
     * @param threshold the threshold that access has to last for in seconds
     */
    public AccessStats(Double[] accesstimes,int numPoints, double threshold){
        super(accesstimes);
        this.numPoints = numPoints;

        violatedSum = 0.0;
        for(Double access:getData()){
            if(access<threshold)
                violatedSum+=access;
        }
        
        //only store 500 cumSumAccesses
        double percent = 0;
        accDuration = new double[500];
        for(int i=0;i<getCumSum().length-1;i++){
            if((getCumSum()[i]/getSumTotal()-percent/100)>=0){
                accDuration[(int)(percent*5)] = getData()[i];
                percent+=100.0/500.0;
            }
        }
    }

    public double[] getAccessDuration() {
        return accDuration;
    }
    
    public double getViolatedSum(){
        return violatedSum;
    }

    public int getNumPoints() {
        return numPoints;
    }
}
