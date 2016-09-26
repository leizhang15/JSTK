/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jstk.simulator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.UUID;
import jstk.simulator.Satellite;

/**
 * Structure to store information about the constellation
 * @author nozomihitomi
 */
public class Constellation implements Collection{
    
    private final ArrayList<Satellite> satellites;
    
    private final UUID id = UUID.randomUUID();
    
    public Constellation(Collection<Satellite> satellites){
        this.satellites = new ArrayList(satellites);
    }

    @Override
    public int size() {
        return satellites.size();
    }

    @Override
    public boolean isEmpty() {
        return satellites.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return satellites.contains(o);
    }

    @Override
    public Iterator iterator() {
        return satellites.iterator();
    }

    @Override
    public Object[] toArray() {
        return satellites.toArray();
    }

    @Override
    public Object[] toArray(Object[] a) {
        return satellites.toArray(a);
    }

    @Override
    public boolean add(Object e) {
        return satellites.add((Satellite)e);
    }

    @Override
    public boolean remove(Object o) {
        return satellites.remove(o);
    }

    @Override
    public boolean containsAll(Collection c) {
        return satellites.containsAll(c);
    }

    @Override
    public boolean addAll(Collection c) {
        return satellites.addAll(c);
    }

    @Override
    public boolean removeAll(Collection c) {
        return satellites.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection c) {
        return satellites.retainAll(c);
    }

    @Override
    public void clear() {
        satellites.clear();
    }
    
    /**
     * This implementation returns a string containing the unique values for
     * FOV, altitude, inclination, raan, and mu. The string is finished with a
     * unique identifier
     * @return 
     */
    @Override
    public String toString(){
        Iterator<Satellite> iter = satellites.iterator();
        TreeSet<Double> FOVs = new TreeSet();
        TreeSet<Double> altitudes = new TreeSet();
        TreeSet<Double> inclinations = new TreeSet();
        TreeSet<Double> raans = new TreeSet();
        TreeSet<Double> mus = new TreeSet();
        
        while(iter.hasNext()){
            Satellite sat = iter.next();
            FOVs.add(sat.getPayload().getFov());
            altitudes.add(sat.getOrbit().getAltitude());
            inclinations.add(sat.getOrbit().getInclination());
            raans.add(sat.getOrbit().getRaan());
            mus.add(sat.getOrbit().getMeanAnomaly());
        }
        
        String str = "fov";
        Iterator<Double> strIter = FOVs.iterator();
        while(strIter.hasNext())
            str = str + Double.toString(strIter.next()) + "_";
        str = str + "alt";
        strIter = altitudes.iterator();
        while(strIter.hasNext())
            str = str + Double.toString(strIter.next()) + "_";
        str = str + "inc";
        strIter = inclinations.iterator();
        while(strIter.hasNext())
            str = str + Double.toString(strIter.next()) + "_";
        str = str + "raan";
        strIter = raans.iterator();
        while(strIter.hasNext())
            str = str + Double.toString(strIter.next()) + "_";
        str = str + "mu";
        strIter = mus.iterator();
        while(strIter.hasNext())
            str = str + Double.toString(strIter.next()) + "_";
        str = str + "id" + id.toString();
        return str;
    }
    
    
}
