/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jstk.coverage;

import jstk.coverage.DataCollector;
import jstk.simulator.Constellation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import jstk.coverage.gap.CoverageGapAnalyzer;

/**
 *
 * @author nozomihitomi
 */
public class ParallelDataCollector {
    
    private final ExecutorService pool;
    
    private ArrayList<Future<CoverageGapAnalyzer>> futures;
    
    private final String path;
            
    public ParallelDataCollector(String path, int threadCount){
        pool = Executors.newFixedThreadPool(threadCount);
        futures = new ArrayList<Future<CoverageGapAnalyzer>>();
        this.path = path;
    }
    
    /**
     *
     * @param constellations
     * @param maxLatitude
     * @return
     */
    public boolean collect(Collection<Constellation> constellations, double maxLatitude, double threshold){
        for(Constellation constellation:constellations){
            futures.add(pool.submit(new DataCollector(path, constellation, maxLatitude,threshold, constellation.toString())));
        }
        for(Future<CoverageGapAnalyzer> fut:futures){
            try {
                CoverageGapAnalyzer CGA = fut.get();
            } catch (InterruptedException ex) {
                Logger.getLogger(ParallelDataCollector.class.getName()).log(Level.SEVERE, null, ex);
                System.exit(-1);
            } catch (ExecutionException ex) {
                Logger.getLogger(ParallelDataCollector.class.getName()).log(Level.SEVERE, null, ex);
                System.exit(-1);
            }
        }
        pool.shutdown();
        return true;
    }
    
    
}
