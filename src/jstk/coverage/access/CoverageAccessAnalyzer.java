/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jstk.coverage.access;

import java.util.HashMap;
import jstk.coverage.CoverageAnalyzer;

/**
 *
 * @author nozomihitomi
 */
public class CoverageAccessAnalyzer extends CoverageAnalyzer<AccessStats>{
    
    public CoverageAccessAnalyzer(double maxLatitude,double threshold) {
        super(maxLatitude,threshold);
    }

    @Override
    protected void updateStatsByLat(HashMap<Double, AccessStats> latStats,double latitude,int ptsAtLat) {
        latStats.put(latitude, new AccessStats(getDataByLat(latitude),ptsAtLat,threshold));
    }

    @Override
    protected AccessStats updateAllStats(int numPoints) {
        return new AccessStats(getAllData(),numPoints,threshold);
    }
}
