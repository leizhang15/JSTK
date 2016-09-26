/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jstk.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import jstk.coverage.IOCoverage;
import jstk.coverage.ParallelDataCollector;
import jstk.coverage.Point;
import jstk.coverage.access.AccessData;
import jstk.coverage.access.AccessMerger;
import jstk.coverage.access.CoverageAccessAnalyzer;
import jstk.coverage.gap.CoverageGapAnalyzer;
import jstk.coverage.gap.GapData;
import jstk.doe.ExperimentFactory;
import jstk.doe.Factor;
import jstk.simulator.Constellation;
import jstk.simulator.Satellite;

/**
 *
 * @author nozomihitomi
 */
public class JSTK {

    /**
     * @param args the list of filenames to read
     */
    public static void main(String[] args) {
        
        String path1 = "/Users/nozomihitomi/Downloads/coverage4972616.cvaa";
        CoverageGapAnalyzer CGA = new CoverageGapAnalyzer(90.0*Math.PI/180.0, 120.0*60.0);
        CoverageAccessAnalyzer CAA = new CoverageAccessAnalyzer(90.0*Math.PI/180.0, 120.0*60.0);
        IOCoverage iocov = new IOCoverage();
        AccessMerger AM = new AccessMerger();
        AccessData ad = iocov.convertCVAA(path1);
        AccessData ad2= AM.merge(ad);
        
        CAA.analyze(ad2);
        GapData gd = new GapData(ad2);
        
        HashSet<Double> latitudes = new HashSet();
        for(Point pt:gd.getAllPoints())
            latitudes.add(pt.getLatitude());
        CGA.analyze(gd);
        
       String path = "/Users/nozomihitomi/Dropbox/nozomi - dani/Blackwell Dome/TROPICS 1wk Database";

       Factor orb1_alt = new Factor("Orbit1 altitude", new double[]{500,600});
       Factor orb1_inc = new Factor("Orbit1 inclination", new double[]{35,40,45});
       Factor orb1_raan = new Factor("Orbit1 raan", new double[]{0,30,60,90,120,180,210,240,270,300,330});
       
       Factor orb2_alt = new Factor("Orbit2 altitude", new double[]{500,600});
       Factor orb2_inc = new Factor("Orbit2 inclination", new double[]{35,40,45}); //-1 is SSO
       Factor orb2_raan = new Factor("Orbit2 raan", new double[]{0,30,60,90,120,180,210,240,270,300,330});
       
       Factor orb3_alt = new Factor("Orbit3 altitude", new double[]{500,600});
       Factor orb3_inc = new Factor("Orbit3 inclination", new double[]{35,40,45}); //-1 is SSO
       Factor orb3_raan = new Factor("Orbit3 raan", new double[]{0,30,60,90,120,180,210,240,270,300,330});
       
       ExperimentFactory expFact = new ExperimentFactory(Arrays.asList(
               new Factor[]{orb1_alt,orb1_inc,orb1_raan,orb2_alt,orb2_inc,orb2_raan,orb3_alt,orb3_inc,orb3_raan}));
       int[][] experiments = expFact.fullfact();
       
       
       ArrayList<Constellation> allCons = new ArrayList<Constellation>();
       for(int[] exp: experiments){
           ArrayList<Satellite> satellites = new ArrayList();
           
           allCons.add(new Constellation(satellites));
       }
       ParallelDataCollector pdc = new ParallelDataCollector(path, 1);
//       try{
//           pdc.collect(allCons, 60);
//       }catch(Exception e){
//           System.exit(-1);
//       }
       
//       DataCollector dc = new DataCollector(path, con, 60.0, "test");
//       dc.call();
    }
    
    
//    //Create constellation
//           ArrayList<Satellite> satellites = new ArrayList();
//           satellites.add(new Satellite("Sat11",new Orbit(400, 0, 51.6, 0, 0, 0),new Instrument(57)));
//           satellites.add(new Satellite("Sat12",new Orbit(400, 0, 51.6, 0, 0, 0),new Instrument(57)));
//           satellites.add(new Satellite("Sat13",new Orbit(400, 0, 51.6, 0, 0, 0),new Instrument(57)));
//           satellites.add(new Satellite("Sat14",new Orbit(400, 0, 51.6, 0, 0, 0),new Instrument(57)));
//           
//           satellites.add(new Satellite("Sat21",new Orbit(400, 0, 51.6, 0, 0, 0),new Instrument(57)));
//           satellites.add(new Satellite("Sat22",new Orbit(400, 0, 51.6, 0, 0, 0),new Instrument(57)));
//           satellites.add(new Satellite("Sat23",new Orbit(400, 0, 51.6, 0, 0, 0),new Instrument(57)));
//           satellites.add(new Satellite("Sat24",new Orbit(400, 0, 51.6, 0, 0, 0),new Instrument(57)));
//           
//           satellites.add(new Satellite("Sat31",new Orbit(400, 0, 51.6, 0, 0, 0),new Instrument(57)));
//           satellites.add(new Satellite("Sat32",new Orbit(400, 0, 51.6, 0, 0, 0),new Instrument(57)));
//           satellites.add(new Satellite("Sat33",new Orbit(400, 0, 51.6, 0, 0, 0),new Instrument(57)));
//           satellites.add(new Satellite("Sat34",new Orbit(400, 0, 51.6, 0, 0, 0),new Instrument(57)));
    
}
