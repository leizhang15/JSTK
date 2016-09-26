/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jstk.coverage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Computes basic statistics on the numbers provided
 * @author nozomihitomi
 */
public class Stats {
    
    /*
     * Minimum
     */
    private final double min;
    
    /**
     * Maximum
     */
    private final double max;
    
    /**
     * Mean
     */
    private final double mean;
    
    /**
     * Median
     */
    private final double median;
    
    /**
     * Standard deviation
     */
    private final double stdDev;
    
    /**
     * CDF
     */
    private final CDF cdf;
    
    /**
     * Maintains the sum total
     */
    private double sumTotal;
            
    /**
     * Maintains the cumulative sum
     */
    private double[] cumSum;
    
    /**
     * the sorted raw data in ascending order
     */
    private final Double[] data;
    
    /**
     * Computes several statistics including the mean, median, cdf, maximum, minimum and standarad deviation.
     * @param gaptimes an array of data to apply statistics
     */
    public Stats(Double[] gaptimes){
        List<Double> listGaptimes = Arrays.asList(gaptimes);
        Collections.sort(listGaptimes);
        data = listGaptimes.toArray(new Double[listGaptimes.size()]);
        int listSize = listGaptimes.size();
        
        min = listGaptimes.get(0);
        max = listGaptimes.get(listSize-1);
        
        mean = mean(gaptimes);
        stdDev = std(gaptimes);
        cdf = new CDF(listGaptimes);
        median = cdf.getValue(50.0);
        
        sumTotal = 0.0;
        cumSum = new double[gaptimes.length];
        int ind = 0;
        for(Double gap:listGaptimes){
            sumTotal+=gap;
            cumSum[ind] = sumTotal;
            ind++;
        }
    }
    /**
     * Computes the mean of the doubles
     * @param data
     * @return 
     */
    private double mean(Double[] data){
        double sum = 0;
        for(double time:data)
            sum+=time;
        return sum/(double)data.length;
    }
    
    /**
     * Computes the population standard deviation of the doubles
     * @param data
     * @return 
     */
    private double std(Double[] data){
        double sum = 0;
        for(double time:data)
            sum+=Math.pow(mean - time, 2);
        return sum/(double)data.length;
    }
    
    /**
     * Returns the sorted data in ascending order
     * @return 
     */
    public Double[] getData(){
        return data;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getMean() {
        return mean;
    }

    public double getMedian() {
        return median;
    }

    public double getStdDev() {
        return stdDev;
    }    

    public CDF getCdf() {
        return cdf;
    }

    public double getSumTotal() {
        return sumTotal;
    }

    public double[] getCumSum() {
        return cumSum;
    }

}
