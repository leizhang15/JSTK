/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jstk.coverage.gap;

import java.util.HashMap;
import jstk.coverage.CoverageAnalyzer;

/**
 *
 * @author nozomihitomi
 */
public class CoverageGapAnalyzer extends CoverageAnalyzer<GapStats>{

    public CoverageGapAnalyzer(double maxLatitude, double threshold) {
        super(maxLatitude,threshold);
    }

    @Override
    protected void updateStatsByLat(HashMap<Double, GapStats> latStats,double latitude,int ptsAtLat) {
        latStats.put(latitude, new GapStats(getDataByLat(latitude),ptsAtLat,threshold));
    }

    @Override
    protected GapStats updateAllStats(int numPoints) {
        return new GapStats(getAllData(),numPoints,threshold);
    }
}
