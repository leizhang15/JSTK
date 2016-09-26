/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jstk.coverage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jstk.coverage.gap.CoverageGapAnalyzer;

/**
 * Data structure for a cumulative distribution function. Default cdf will have 100 pts from 1~100%
 * @author nozomihitomi
 */
public class CDF{
    
    private HashMap<Double,Double> cdfData = new HashMap();
    
    /**
     * Computes the cdf upon construction. Cdf will have 100 pts from 1~100%
     * @param data 
     */
    public CDF(List<Double> data){
        cdfData = computeCDF(data);
    }
    
    /**
     * Creates a hashmap containing the numbers to create a CDF. 
     * @param data
     * @return 
     */
    private HashMap<Double,Double> computeCDF(List<Double> data){
       Collections.sort(data);
        HashMap<Double,Double> cdf = new HashMap();
        for(int i=1;i<=100;i++){
            int index = (int)Math.round((double)(i*data.size())/100.0);
            cdf.put((double)i, data.get(index-1));
        }
        return cdf; 
    }
    
    /**
     * Gets the value of the cdf at the specified percentile
     * @param percentile
     * @return 
     */
    public Double getValue(Double percentile){
        return cdfData.get(percentile);
    }
    
    public Double[] getAllPercentiles(){
        Double[] out = new Double[cdfData.size()];
        int ind = 0;
        for(Double percent:cdfData.keySet()){
            out[ind] = getValue(percent);
            ind++;
        }
        return out;
    }
    
    public int size(){
        return cdfData.size();
    }
    
    /**
     * Saves the cdf percentiles as a csv with two columns. The first being the 
     * percentile and the second being the value at that percentile
     * @param filename
     * @return 
     */
    public boolean saveAsCSV(String filename){
        FileWriter fw = null;
        try{
            fw = new FileWriter(new File(filename));
            
            ArrayList prcnt = new ArrayList(cdfData.keySet());
            Collections.sort(prcnt);
            Iterator<Double> iter = prcnt.iterator();
            while(iter.hasNext()){
                Double percent = iter.next();
                fw.append(percent.toString() + "," +cdfData.get(percent).toString() + "\n");
            }
            fw.flush();
        } catch (IOException ex) {
            Logger.getLogger(CoverageGapAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }finally{
            if(fw!=null){
                try {
                    fw.close();
                } catch (IOException ex) {
                    Logger.getLogger(CoverageGapAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return true;
    }
}
