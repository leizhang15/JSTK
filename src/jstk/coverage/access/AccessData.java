/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jstk.coverage.access;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import jstk.coverage.CoverageData;
import jstk.coverage.Point;

/**
 * This class holds the access intervals for each of the grid points in the STK
 * coverage definition
 * @author nozomihitomi
 */
public class AccessData implements Serializable, CoverageData{
    private static final long serialVersionUID = -7458396064256345351L;
    
    /**
     * The access intervals for each point on the coverage grid
     */
    private HashMap<Point,ArrayList<AccessInterval>> accessData;
    
    /**
     * stores the number of points per latitude
     */
    private HashMap<Double,Integer> pointsPerLat;
    
    public AccessData() {
        this.accessData = new HashMap();
        pointsPerLat =  new HashMap<Double, Integer>();
    }
    
    /**
     * Given a collection of access intervals, will compute the durations of each access 
     * @return a collection of access durations
     */
    private Collection<Double> computeAccessDurations(Collection<AccessInterval> intervals){
        Iterator<AccessInterval> iter = intervals.iterator();
        ArrayList<Double> out = new ArrayList(intervals.size());
        while(iter.hasNext()){
            AccessInterval interval = iter.next();
            out.add(interval.getEnd()-interval.getStart());
        }
        return out;
    }
    
    /**
     * This method is used to insert new or update access intervals similar to 
     * the put method for HashMap
     * @param pt
     * @param intervals
     * @return 
     */
    public ArrayList<AccessInterval> setAccessIntervals(Point pt,ArrayList<AccessInterval> intervals){
        if(pointsPerLat.containsKey(pt.getLatitude()))
            pointsPerLat.put(pt.getLatitude(), pointsPerLat.get(pt.getLatitude())+1);
        else
            pointsPerLat.put(pt.getLatitude(), 1);
        return accessData.put(pt, intervals);
    }
    
    /**
     * Gets the access intervals for the specified point
     * @param pt
     * @return 
     */
    public ArrayList<AccessInterval> getAccessIntervals(Point pt){
        return accessData.get(pt);
    }
    
    /**
     * Returns the number of points that are stored in the object
     * @return 
     */
    @Override
    public int size(){
        return accessData.size();
    }
    
    /**
     * Returns the set of all points stored in the object
     * @return 
     */
    @Override
    public Set<Point> pointSet(){
        return accessData.keySet();
    }
    
    /**
     * Returns all the points that the accessData contains
     * @return 
     */
    @Override
    public ArrayList<Point> getAllPoints(){
        return new ArrayList<Point>(accessData.keySet());
    }

    @Override
    public Collection<Double> getDurations(Point pt) {
        return computeAccessDurations(getAccessIntervals(pt));
    }

    @Override
    public int getPtsPerLatitude(double latitude) {
        return pointsPerLat.get(latitude);
    }
}
