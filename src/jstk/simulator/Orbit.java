/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jstk.simulator;

/**
 * The classical orbital parameters. Any parameter containing an angle is in radians
 * @author nozomihitomi
 */
public class Orbit {
    
    /**
     * Semimajor axis in meters
     */
    private final double semimajoraxis;
    
    /**
     * Eccentricity [0,1]
     */
    private final double eccentricity;
    
    /**
     * Inclination in radians [0,2pi]
     */
    private final double inclination;
    
    /**
     * RAAN in radians
     */
    private final double raan;
    
    /**
     * Mean anomaly in radians
     */
    private final double meanAnomaly;
    
    /**
     * Argument of perigee in radians
     */
    private final double argumentPerigee;
    
    /**
     * Altitude in km
     */
    private final double altitude;

    /**
     * Constructs the orbit using the classical orbital parameters
     * @param altitude in km
     * @param eccentricity Eccentricity [0,1]
     * @param inclination Inclination in radians
     * @param agrumentPerigee Argument of perigee in radians
     * @param raan RAAN in radians
     * @param meanAnomaly Mean anomaly in radians
     */
    public Orbit(double altitude, double eccentricity, double inclination,double agrumentPerigee, double raan, double meanAnomaly) {
        this.semimajoraxis = altitude + EarthModel.radius;
        this.eccentricity = eccentricity;
        this.inclination = inclination;
        this.raan = raan;
        this.meanAnomaly = meanAnomaly;
        this.argumentPerigee = agrumentPerigee;
        this.altitude = altitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public double getSemimajoraxis() {
        return semimajoraxis;
    }

    public double getEccentricity() {
        return eccentricity;
    }

    public double getInclination() {
        return inclination;
    }

    public double getRaan() {
        return raan;
    }

    public double getMeanAnomaly() {
        return meanAnomaly;
    }

    public double getAgrumentPerigee() {
        return argumentPerigee;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (int) (Double.doubleToLongBits(this.semimajoraxis) ^ (Double.doubleToLongBits(this.semimajoraxis) >>> 32));
        hash = 31 * hash + (int) (Double.doubleToLongBits(this.eccentricity) ^ (Double.doubleToLongBits(this.eccentricity) >>> 32));
        hash = 31 * hash + (int) (Double.doubleToLongBits(this.inclination) ^ (Double.doubleToLongBits(this.inclination) >>> 32));
        hash = 31 * hash + (int) (Double.doubleToLongBits(this.raan) ^ (Double.doubleToLongBits(this.raan) >>> 32));
        hash = 31 * hash + (int) (Double.doubleToLongBits(this.meanAnomaly) ^ (Double.doubleToLongBits(this.meanAnomaly) >>> 32));
        hash = 31 * hash + (int) (Double.doubleToLongBits(this.argumentPerigee) ^ (Double.doubleToLongBits(this.argumentPerigee) >>> 32));
        return hash;
    }

    /**
     * Orbits are equal if all Keplerian parameters are the same (semimajor axis, eccentricity, inclination, RAAN, mean anomaly, and argument of perigee)
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Orbit other = (Orbit) obj;
        if (Double.doubleToLongBits(this.semimajoraxis) != Double.doubleToLongBits(other.semimajoraxis)) {
            return false;
        }
        if (Double.doubleToLongBits(this.eccentricity) != Double.doubleToLongBits(other.eccentricity)) {
            return false;
        }
        if (Double.doubleToLongBits(this.inclination) != Double.doubleToLongBits(other.inclination)) {
            return false;
        }
        if (Double.doubleToLongBits(this.raan) != Double.doubleToLongBits(other.raan)) {
            return false;
        }
        if (Double.doubleToLongBits(this.meanAnomaly) != Double.doubleToLongBits(other.meanAnomaly)) {
            return false;
        }
        if (Double.doubleToLongBits(this.argumentPerigee) != Double.doubleToLongBits(other.argumentPerigee)) {
            return false;
        }
        return true;
    }
    
    
    
}
