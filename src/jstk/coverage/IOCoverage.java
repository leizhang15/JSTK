/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jstk.coverage;

import jstk.coverage.Point;
import jstk.coverage.access.AccessData;
import jstk.coverage.access.AccessInterval;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jstk.coverage.gap.GapData;

/**
 * IOCoverage is used to read and export coverage access files generated in STK.
 * @author nozomihitomi
 */
public class IOCoverage implements Serializable{
    private static final long serialVersionUID = 1166655237634253978L;
    
    private static final Pattern pointNumREGEX = Pattern.compile("PointNumber");
    private static final Pattern latlonREGEX = Pattern.compile("([-+ ]\\d*.\\d*[eE][-+]\\d*)");
    private static final Pattern accessREGEX = Pattern.compile("((\\d*.\\d*[eE][-+]\\d*))");

    
    /**
     * Reads in the raw coverage data from STK. file format must have accesses
     * with lines starting with 1) the point number 2) the latitude 3) the
     * longitude 4) the altitude 5) the number of accesses 6) lines containing
     * the access intervals 7) a blank line before the next grid point.
     *
     * @param filename the file name of the access data from STK
     * @return A hashmap containing all the accesses to each grid point
     */
    public AccessData convertCVAA(String filename){
        System.out.println("Loading and converting " + filename);
        AccessData out = new AccessData();
        
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(new File(filename)));
            String line;
            while ((line = br.readLine()) != null) {
                if(pointNumREGEX.matcher(line).find()){
                    
                    Matcher m = latlonREGEX.matcher(br.readLine());
                    m.find();
                    String lat = m.group(1); //latitude
                    m = latlonREGEX.matcher(br.readLine());
                    m.find();
                    String lon = m.group(1); //longitude
                    
                    Point pt = new Point(Double.parseDouble(lat),Double.parseDouble(lon));
                    
                    br.readLine(); //altitude
                    br.readLine(); //number of accesses
                    
                    ArrayList<AccessInterval> accesses = new ArrayList();
                    
                    while((m = accessREGEX.matcher(br.readLine())).find()){
                        String start = m.group(1);
                        m.find();
                        String stop = m.group(1);
                        accesses.add(new AccessInterval(Double.parseDouble(start), Double.parseDouble(stop)));
                    }
                    
                    out.setAccessIntervals(pt, accesses);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IOCoverage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IOCoverage.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            if(br!=null){
                try{
                    br.close();
                }catch (IOException ex) {
                    Logger.getLogger(IOCoverage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return out;
    }
    
    /**
     * Saves the AccessData with the specified filename
     * @param accesses
     * @param filename
     * @return true if save is successful
     */
    public boolean saveAccessDAT(AccessData accesses,String filename){
        boolean out = true;
        ObjectOutputStream os = null;
         try{
            os = new ObjectOutputStream(new FileOutputStream(filename));
            os.writeObject(accesses);
            os.close();
        } catch (IOException ex) {
            out = false;
            Logger.getLogger(IOCoverage.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
             if(os!=null){
                 try{
                     os.close();
                 }catch (IOException ex) {
                    Logger.getLogger(IOCoverage.class.getName()).log(Level.SEVERE, null, ex);
                }
             }
         }
        return out;
    }
    
    /**
     * Loads the AccessData from the specified filename
     * @param filename
     * @return true if load is successful
     */
    public AccessData loadCVAADAT(String filename){
        AccessData cvaa = null;
        ObjectInputStream is = null;
        try{
           is = new ObjectInputStream( new FileInputStream( filename ));
           cvaa = (AccessData)is.readObject();
        } catch (IOException ex) {
            Logger.getLogger(IOCoverage.class.getName()).log(Level.SEVERE, null, ex);
        }catch (ClassNotFoundException ex) {
            Logger.getLogger(IOCoverage.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
             if(is!=null){
                 try{
                     is.close();
                 }catch (IOException ex) {
                    Logger.getLogger(IOCoverage.class.getName()).log(Level.SEVERE, null, ex);
                }
             }
         }
        return cvaa;
    }
    
    /**
     * Saves the GapData with the specified filename
     * @param gaps
     * @param filename
     * @return true if save is successful
     */
    public boolean saveGapDAT(GapData gaps,String filename){
        boolean out = true;
        ObjectOutputStream os = null;
         try{
            os = new ObjectOutputStream(new FileOutputStream(filename));
            os.writeObject(gaps);
            os.close();
        } catch (IOException ex) {
            out = false;
            Logger.getLogger(IOCoverage.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
             if(os!=null){
                 try{
                     os.close();
                 }catch (IOException ex) {
                    Logger.getLogger(IOCoverage.class.getName()).log(Level.SEVERE, null, ex);
                }
             }
         }
        return out;
    }
    
    /**
     * Loads the GapData from specified file
     * @param filename
     * @return true if load is successful
     */
    public GapData loadGapDAT(String filename){
        GapData cvaa = null;
        ObjectInputStream is = null;
        try{
           is = new ObjectInputStream( new FileInputStream( filename ));
           cvaa = (GapData)is.readObject();
        } catch (IOException ex) {
            Logger.getLogger(IOCoverage.class.getName()).log(Level.SEVERE, null, ex);
        }catch (ClassNotFoundException ex) {
            Logger.getLogger(IOCoverage.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
             if(is!=null){
                 try{
                     is.close();
                 }catch (IOException ex) {
                    Logger.getLogger(IOCoverage.class.getName()).log(Level.SEVERE, null, ex);
                }
             }
         }
        return cvaa;
    }
}
