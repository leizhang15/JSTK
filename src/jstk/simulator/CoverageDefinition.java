/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jstk.simulator;

/**
 *
 * @author nozomihitomi
 */
public class CoverageDefinition {
    
    private final double granularity;
    
    private final double minLatitude;
    
    private final double maxLatitude;
    
    private final double minLongitude;
    
    private final double maxLongitude;

    public CoverageDefinition(double resolution, double minLatitude, double maxLatitude, double minLongitude, double maxLongitude) {
        this.granularity = resolution;
        this.minLatitude = minLatitude;
        this.maxLatitude = maxLatitude;
        this.minLongitude = minLongitude;
        this.maxLongitude = maxLongitude;
    }
    
}
