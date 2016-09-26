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
public class Instrument {
    
    
    /**
     * Field of view of the instrument in 
     */
    private final double fov;

    public Instrument(double fov) {
        this.fov = fov;
    }

    public double getFov() {
        return fov;
    }
    
    
}
