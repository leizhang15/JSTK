/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jstk.coverage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Object to Analyze coverage accesses or gaps
 *
 * @author nozomihitomi
 * @param <T>Specify the type of data to return. AccessData or GapData
 */
public abstract class CoverageAnalyzer<T extends Stats> {

    /**
     * A list of all the coverage accesses or gap durations from all the grid
     * points
     */
    private ArrayList<Double> allData;

    /**
     * The cdfs by latitude
     */
    private HashMap<Double, T> statsByLat;

    /**
     * The statistics for the accesses or gaps from all the points
     */
    private T allStats;

    /**
     * Limits the analysis to points within +/- maxLatitude in deg
     */
    private final double maxLatitude;

    /**
     * The access or gap durations by latitude
     */
    private HashMap<Double, ArrayList<Double>> dataByLat;

    /**
     * The number of points included in the analysis
     */
    private int numAllPoints;

    /**
     * The threshold that an access has to be longer than or a gap has to be
     * shorter than
     */
    protected final double threshold;

    /**
     * Creates a coverage analyzer object that is limited to analyzing any
     * points within +/- maxLatitude
     *
     * @param maxLatitude the +/- latitude limit in radians for analysis. All
     * points falling outside this range are ignored
     * @param threshold threshold that an access has to be longer than or a gap
     * has to be shorter than in seconds
     */
    public CoverageAnalyzer(double maxLatitude, double threshold) {
        this.allData = new ArrayList();
        this.maxLatitude = maxLatitude;
        this.statsByLat = new HashMap<Double, T>();
        this.threshold = threshold;
    }

    /**
     * Computes the min, max, mean, median, and std dev coverage access or gap
     * for each grid point. In addition creates data required to build a
     * cumulative density function of access or gap time vs %.
     *
     * @param data the data to analyze
     */
    public void analyze(CoverageData data) {
        System.out.print("Running Statistical Analysis...");
        Iterator<Point> iter = data.pointSet().iterator();

        dataByLat = new HashMap();

        while (iter.hasNext()) {
            Point nextPoint = iter.next();
            //skip points if they exceed desired latitude area of interest
            if (Math.abs(nextPoint.getLatitude()) <= maxLatitude) {
                //Only do analysis on points that have been visited
                ArrayList<Double> durations = new ArrayList(data.getDurations(nextPoint));
                allData.addAll(durations);

                //Gather all gaps of the same latitude to produce metrics as a function of latitude
                if (!dataByLat.containsKey(nextPoint.getLatitude())) {
                    dataByLat.put(nextPoint.getLatitude(), new ArrayList());
                }
                dataByLat.get(nextPoint.getLatitude()).addAll(durations);
                
            }
        }

        //Compute the cdfs for accesses or gaps of the same latitude
        Iterator<Double> latIter = dataByLat.keySet().iterator();
        while (latIter.hasNext()) {
            Double latitude = latIter.next();
            if(dataByLat.get(latitude).isEmpty())
                continue;
            
            //TODO fix stats so that it can take less than 100 gaps
            if (dataByLat.get(latitude).size() < 100) //IF there are less than 100 gaps skip 
            {
                System.err.print("\nSkipping latitude " + latitude * 180 / Math.PI + " because has less than 100 accesses or gaps\n");
            } else {
                updateStatsByLat(statsByLat, latitude, data.getPtsPerLatitude(latitude));
            }
        }
        allStats = updateAllStats(data.getAllPoints().size());
        System.out.println("Done");
    }

    /**
     * Method to update the stats by latitude which is specific for accesses and
     * gaps
     *
     * @param latStats
     * @param latitude data at latitude to update
     * @param ptsAtLat the number of points at a particular latitude
     */
    protected abstract void updateStatsByLat(HashMap<Double, T> latStats, double latitude, int ptsAtLat);

    /**
     * Method to update the global stats which is specific for accesses and gaps
     *
     * @param numPoints The number of points used in this analysi
     * @return 
     */
    protected abstract T updateAllStats(int numPoints);

    /**
     * Returns the access or gap durations by latitude
     *
     * @param latitude
     * @return
     */
    protected Double[] getDataByLat(double latitude) {
        return dataByLat.get(latitude).toArray(new Double[dataByLat.get(latitude).size()]);
    }

    /**
     * Returns the access or gap durations recorded for the entire domain
     * between +/- max latitude
     *
     * @return
     */
    protected Double[] getAllData() {
        return allData.toArray(new Double[allData.size()]);
    }

    /**
     * Returns the statistics on all coverage gaps
     *
     * @return
     */
    public T getAllStats() {
        return allStats;
    }

    /**
     * Returns the statistics computed on the accesses or gaps for each latitude
     *
     * @return
     */
    public HashMap<Double, T> getStatsByLat() {
        return statsByLat;
    }

    public double getMaxLatitude() {
        return maxLatitude;
    }

}
