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
public class Satellite {
    
    private final String name;
    
    private final Orbit orbit;
    
    private final Instrument payload;

    public Satellite(String name, Orbit orbit, Instrument payload) {
        this.name = name;
        this.orbit = orbit;
        this.payload = payload;
    }
    
    public Orbit getOrbit(){
        return orbit;
    }
    
    public Instrument getPayload(){
        return payload;
    }
    
}
