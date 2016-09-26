/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jstk.simulator;

/**
 * Currently only supports stationary Earth and two body dynamics 
 * @author nozomihitomi
 */
public class Simulator implements Runnable{
    
    private final double startTime;
    
    private final double endTime;
    
    private final double timeStep;

    public Simulator(double startTime, double endTime, double timeStep) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeStep = timeStep;
    }
    
    
    
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
