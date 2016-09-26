/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jstk.coverage.access;

import java.util.ArrayList;
import java.util.Collections;
import jstk.coverage.Point;

/**
 * This function scans coverage access data and merges any intervals that 
 * overlap. Can use this to combine any two coverage access files 
 * @author nozomihitomi
 */
public class AccessMerger{
    
    
    public AccessMerger(){
    }
    
    /**
     * Merges the coverage accesses in one AccessData object. Can be used if the
 AccessData is created from a scenario containing more than one satellite.
 In such scenarios some accesses overlap as all accesses from all assets
 are recorded individually
     * @param data
     * @return 
     */
    public AccessData merge(AccessData data){
        return merge(new AccessData[]{data});
    }

    /**
     * Merges multiple coverage accesses into one AccessData object
     * @param cvaa
     * @return 
     */
    public AccessData merge(AccessData[] cvaa) {
        System.out.print("Merging Accesses...");
        AccessData mergedAccess = new AccessData();
        for(Point pt:cvaa[0].pointSet())
            mergedAccess.setAccessIntervals(pt, mergeAccessForPt(pt,cvaa));
        System.out.println("Done");
        return mergedAccess;
    }
    
    /**
     * For a given point and the set of accesses, this method will find any 
     * overlapping access times and merge them
     * @param pt
     * @param cvaa
     * @return the merged access intervals in an ArrayList
     */
    private ArrayList<AccessInterval> mergeAccessForPt(Point pt,AccessData[] cvaa){
        //count the number of total accesses summed over all cvaa data
        int totalAccesses = 0;
        for(AccessData c:cvaa)
            totalAccesses += c.getAccessIntervals(pt).size();
        if (totalAccesses==0)
            return new ArrayList(0);
        
        //Create a copied list of all the accesses
        ArrayList<AccessInterval> allAccesses = new ArrayList(totalAccesses);
        for(AccessData c:cvaa)
            allAccesses.addAll(c.getAccessIntervals(pt));
        //sort the accesses based on the start times
        Collections.sort(allAccesses);
        
        //Create a merged list and initialize it with the earliest access
        ArrayList<AccessInterval> mergedPtAccess = new ArrayList();
        mergedPtAccess.add(allAccesses.get(0));
        int mergedIndex = 0;
        for(int i=1;i<allAccesses.size();i++){
            if(mergedPtAccess.get(mergedIndex).isOverlap(allAccesses.get(i))){
                double a = mergedPtAccess.get(mergedIndex).getEnd();
                double b = allAccesses.get(i).getEnd();
                double latestEnd = (a > b) ? a : b;
                AccessInterval newInterval = new AccessInterval(mergedPtAccess.get(mergedIndex).getStart(), latestEnd);
                //modify the current mergedPTAccess
                mergedPtAccess.remove(mergedIndex);
                mergedPtAccess.add(mergedIndex,newInterval);
            }else{
                mergedIndex++;
                mergedPtAccess.add(allAccesses.get(i));
            }
        }
        return mergedPtAccess;
    }
}
