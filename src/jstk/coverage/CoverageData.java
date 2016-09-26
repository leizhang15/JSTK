/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jstk.coverage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * Coverage access and gap data from STK or processed STK data
 *
 * @author nozomihitomi
 */
public interface CoverageData {

    /**
     * Returns the number of points that are stored in the object
     *
     * @return
     */
    public int size();

    /**
     * Returns the set of all points stored in the object
     *
     * @return
     */
    public Set<Point> pointSet();

    /**
     * Returns all the points that the accessData contains
     *
     * @return
     */
    public ArrayList<Point> getAllPoints();

    /**
     * Returns the duration of the access or gap for a specified point on the
     * coverage grid
     *
     * @param pt
     * @return
     */
    public Collection<Double> getDurations(Point pt);
    
    /**
     * Returns the number of points in this dataset that have the same latitude
     * @param latitude
     * @return 
     */
    public int getPtsPerLatitude(double latitude);
}
