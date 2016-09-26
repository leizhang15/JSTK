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
public class EarthModel implements SimulationObject{
    
    /**
     * Earth radius in kilometers
     */
    public static final double radius = 6378.000;
    
    /**
     * The rotation rate of the Earth
     */
    public static final double angularVelocity = 2.0*Math.PI*23.9345;
    
    /**
     * The coordinates of the Earth
     */
    public static CoordinateSystem coor;
    
    
    private static final EarthModel Instance = new EarthModel();
    
    
    /**
     * Private constructor to enforce singleton property
     */
    private EarthModel(){
    }

    /**
     * In this implementation, the Earth will rotate over the course of the time step;
     * @param timeStep 
     */
    @Override
    public void step(double timeStep) {
        
    }
}
