/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jstk.coverage;

import jstk.coverage.access.AccessData;
import jstk.coverage.access.AccessMerger;
import jstk.simulator.Constellation;
import java.io.File;
import java.util.Iterator;
import java.util.concurrent.Callable;
import jstk.coverage.gap.CoverageGapAnalyzer;
import jstk.coverage.gap.GapData;
import jstk.simulator.Orbit;
import jstk.simulator.Satellite;

/**
 * This is a wrapper that uses all the methods from other classes so input is 
 * just the cvaa file and output is the gap statistics
 * @author nozomihitomi
 * @param <T> the type of analyzer to use; gap or access
 */
public class DataCollector implements Callable{
    
    private String[] filenames;
    
    /**
     * The +/- latitude of the grid points to include in the analysis
     */
    private final double maxLatitude;
    
    /**
     * The threshold that an access must be longer than or a gap must be shorter than in seconds
     */
    private final double threshold;
    
    /**
     * Constructor for object. Need to set the maximum latitude for which all 
     * points within +/- maxLatitude will be included in analysis. Points 
     * outside this range are ignored
     * @param path to the database containing the cvaa files
     * @param constellation
     * @param maxLatitude 
     * @param threshold hreshold that an access must be longer than or a gap must be shorter than in seconds
     * @param saveFilename 
     */
    public DataCollector(String path,Constellation constellation,double maxLatitude, double threshold,String saveFilename){
        this.maxLatitude = maxLatitude;
        this.threshold = threshold;
        this.filenames = getFilenames(path,constellation);
    }
    
    private String[] getFilenames(String path,Constellation constellation){
        filenames = new String[constellation.size()];
        Iterator<Satellite> iter = constellation.iterator();
        int ind = 0;
        while(iter.hasNext()){
            Satellite sat = iter.next();
            Orbit orb = sat.getOrbit();
            String fov = String.format("%.2f", sat.getPayload().getFov());
            String alt = String.format("%.2f", orb.getAltitude());
            String inc = String.format("%.2f", orb.getInclination());
            String mu = String.format("%.2f", orb.getMeanAnomaly());
            String raan = String.format("%.2f", orb.getRaan());
            filenames[ind] = path + File.separator + "coverage_alt_" + alt +
                    "_inc_" + inc + "_mu_" + mu + "_RAAN_" + raan + ".cvaa";
            ind++;
        }
        return filenames;
    }
    
    /**
     * This implementation converts the cvaa into a .cvaaGapDAT file with the
     * computed gaps. It then computes the statistics.
     *
     * @return the analyzer from which data can be extracted
     */
    @Override
    public CoverageAnalyzer call() {
        IOCoverage iocov = new IOCoverage();
        AccessMerger AM = new AccessMerger();
        CoverageGapAnalyzer CGA = new CoverageGapAnalyzer(maxLatitude,threshold);
        
        AccessData[] allAccessData = new AccessData[filenames.length];
        for(int i=0;i<filenames.length;i++){
            allAccessData[i] = iocov.convertCVAA(filenames[i]);
            iocov.saveAccessDAT(allAccessData[i], filenames[i]+"DAT");
        }
        AccessData ad = AM.merge(allAccessData);
        GapData gd = new GapData(ad);
        
        CGA.analyze(gd);
        return CGA;
    }
    
    
}
