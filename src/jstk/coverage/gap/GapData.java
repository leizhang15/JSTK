/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jstk.coverage.gap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import jstk.coverage.CoverageData;
import jstk.coverage.Point;
import jstk.coverage.access.AccessData;
import jstk.coverage.access.AccessInterval;

/**
 * This class holds the access gaps for each of the grid points in the STK
 * coverage definition. Gaps are computed from the access data. A gap is defined
 * as the time between two consecutive accesses. Therefore the gap before the 
 * first access and the gap after the last access are ignored
 * @author nozomihitomi
 */
public class GapData implements Serializable, CoverageData{
    private static final long serialVersionUID = 1662589465389170013L;
    
    /**
     * The list of coverage gaps for each grid point
     */
    private HashMap<Point,ArrayList<Double>> gapData;
    
    /**
     * stores the number of points per latitude
     */
    private HashMap<Double,Integer> pointsPerLat;

    /**
     * Computes the coverage gaps from the AccessData
     * @param accessData 
     */
    public GapData(AccessData accessData) {
        gapData = new HashMap<Point, ArrayList<Double>>();
        pointsPerLat =  new HashMap<Double, Integer>();
        Iterator<Point> iter = accessData.getAllPoints().iterator();
        while(iter.hasNext()){
            Point pt = iter.next();
            if(accessData.getAccessIntervals(pt).size()>1){
                gapData.put(pt, computeGaps(accessData.getAccessIntervals(pt)));
            }
            if(pointsPerLat.containsKey(pt.getLatitude()))
                pointsPerLat.put(pt.getLatitude(), pointsPerLat.get(pt.getLatitude())+1);
            else
                pointsPerLat.put(pt.getLatitude(), 1);
        }
    }
    
    /**
     * Assumes the ArrayList of access intervals are in order starting with the 
     * first access at index 0 
     * @param accessIntervals
     * @return 
     */
    private ArrayList<Double> computeGaps(ArrayList<AccessInterval> accessIntervals){
        //Sort by start times
        Collections.sort(accessIntervals);
        ArrayList<Double> gaps = new ArrayList<Double>(accessIntervals.size()-1);
        for(int i=0;i<accessIntervals.size()-1;i++){
            gaps.add(i,accessIntervals.get(i+1).getStart()-accessIntervals.get(i).getEnd());
        }
        return gaps;
    }
    
    /**
     * Gets the coverage gaps for the specified point
     * @param pt
     * @return 
     */
    @Override
    public Collection<Double> getDurations(Point pt){
        return gapData.get(pt);
    }
    
    /**
     * Returns the number of points that are stored in the object
     * @return 
     */
    @Override
    public int size(){
        return gapData.size();
    }
    
    /**
     * Returns the set of all points stored in the object
     * @return 
     */
    @Override
    public Set<Point> pointSet(){
        return gapData.keySet();
    }
    
    /**
     * Returns all the points stored in this object
     * @return 
     */
    @Override
    public ArrayList<Point> getAllPoints(){
        return new ArrayList<Point>(gapData.keySet());
    }
    
    /**
     * Returns the number of points in the coverage grid at a specified latitude
     * @param latitude in radians
     * @return 
     */
    @Override
    public int getPtsPerLatitude(double latitude){
        return pointsPerLat.get(latitude);
    }
}
