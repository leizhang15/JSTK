/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jstk.coverage.gap;

import jstk.coverage.Stats;

/**
 * Stores statistics on the coverage gaps for a series of gap durations
 * @author nozomihitomi
 */
public class GapStats extends Stats{
    
    /**
     * Maintains a representative portion of the gaps used for cumsum metric. Stores the gap durations
     */
    private final double[] cumsumGapDuration;
    
    /**
     * Maintains a representative portion of the gaps used for cumsum metric. Stores the cumsum percentage
     */
    private final double[] cumsum;
    
    /**
     * Sum that violates a threshold
     */
    private double violatedSum;
    
    /**
     * The number of points considered in the statistic
     */
    private final int numPoints;
    
    /**
     * Computes several statistics including the mean, median, cdf, maximum, minimum and standarad deviation.
     * @param gaptimes an array of gap times to apply statistics
     * @param numPoints the number of coverage grid points that is included in this analysis
     * @param threshold the threshold that gaps should be shorter than in seconds
     */
    public GapStats(Double[] gaptimes,int numPoints,double threshold){
        super(gaptimes);
        this.numPoints = numPoints;

        violatedSum = 0.0;
        for(Double gap:getData()){
            if(gap>threshold)
                violatedSum+=gap;
        }
        
        //only store 500 cumSumGaps
        double percent = 0;
        cumsumGapDuration = new double[500];
        cumsum = new double[500];
        for(int i=0;i<getCumSum().length-1;i++){
            if((getCumSum()[i]/getSumTotal()-percent/100)>=0){
                cumsumGapDuration[(int)(percent*5)] = getData()[i];
                cumsum[(int)(percent*5)] = getCumSum()[i]/getSumTotal();
                percent+=100.0/500.0;
            }
        }
    }

    public double[] getCumsumGapDuration() {
        return cumsumGapDuration;
    }
    
    public double[] getCumsum() {
        return cumsum;
    }
    
    public double getViolatedSum(){
        return violatedSum;
    }

    public int getNumPoints() {
        return numPoints;
    }
}
