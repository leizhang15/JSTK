/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jstk.simulator;

/**
 * These objects are those used in the jstk simulator. They can be satellites, the earth, etc.
 * @author nozomihitomi
 */
public interface SimulationObject {
    
    /**
     * Method to step forward in the simulation
     * @param timeStep the amount of time to step forward
     */
    public void step(double timeStep);
}
