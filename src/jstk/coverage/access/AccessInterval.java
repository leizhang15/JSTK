/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jstk.coverage.access;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class contains the access start time and the access end time for one access interval
 * @author nozomihitomi
 */
public class AccessInterval implements Serializable, Comparable<AccessInterval>{
    private static final long serialVersionUID = -6668745852347737450L;
    
    private final double start;
    private final double end;

    /**
     * Start time must be smaller than end time
     * @param start start time of access 
     * @param end end time of access
     */
    public AccessInterval(double start, double end) {
        if(start>end)
            throw new IllegalArgumentException("Access start time " + start + " must be before the access end time" + end);
        
        this.start = start;
        this.end = end;
    }

    public double getStart() {
        return start;
    }

    public double getEnd() {
        return end;
    }
    
    /**
     * This function sees if the two access intervals overlap at all.
     * @param interval2
     * @return true if this accessInterval and the specified interval2 overlap. otherwise false;
     */
    public boolean isOverlap(AccessInterval interval2){
        List<AccessInterval> list = Arrays.asList(new AccessInterval[]{this,interval2});
        Collections.sort(list);
        AccessInterval first = list.get(0);
        AccessInterval second = list.get(1);
        return second.getStart()<first.getEnd();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (int) (Double.doubleToLongBits(this.start) ^ (Double.doubleToLongBits(this.start) >>> 32));
        hash = 23 * hash + (int) (Double.doubleToLongBits(this.end) ^ (Double.doubleToLongBits(this.end) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AccessInterval other = (AccessInterval) obj;
        if (Double.doubleToLongBits(this.start) != Double.doubleToLongBits(other.start)) {
            return false;
        }
        if (Double.doubleToLongBits(this.end) != Double.doubleToLongBits(other.end)) {
            return false;
        }
        return true;
    }

    /**
     * This implementation compares accessIntervals by the start time.
     * @param o
     * @return 
     */
    @Override
    public int compareTo(AccessInterval o) {
        if(this.start == o.getStart())
            return 0;
        else if(this.start > o.getStart())
            return 1;
        else
            return -1;
    }
   
    
}
