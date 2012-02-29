package de.lmu.dbs.lifescience.processing;

import de.lmu.dbs.lifescience.LifeScience;
import de.lmu.dbs.lifescience.model.LifeScienceModel;
import de.lmu.dbs.lifescience.model.Nucleus;
import ij.ImagePlus;
import java.awt.Point;

/**
 * This class provides methods to track cells over images.
 * 
 * @author bea
 */
public class CellTrackerRelate extends Processor {

    //---------------- Attributes
    /** Maximum delta distance from frame i to i+1 in px  */
    int maxDistanceDiff = 0;
    
    /** Intensity Variance */
    int maxIntesityDiff = 40;
    
    /** size of nearest neighbor search */
    int k = 1;
    
    /** Model    */
    LifeScienceModel model;
    
    /** Cell Detector */
    CellDetector detector;

    
    
    //---------------- Constructor
    /**
     * Create a new instance for the cell detector that performs video tracking on the sequence
     * 
     * This cell tracker goes through all images of a sequence and runs a cell detector on them. 
     * Each detected blob is then related to blobs in the previous images as follows:
     * 1. The k nearest neighbor blobs in the previous detection that are within a threshold radius
     *    The result set is sorted with smallest distance up front
     * 2. Go through results and determine wether it is a valid candidate by comparing intensity-differences.
     *    With this technique it is possible to ensure that no false assignments can occur even if blobs are very near to each other
     * 3. Do something when blobs could not be assigned... -> TODO
     * 
     * 
     * @param image
     * @param model LifeScienceModel
     * @param detector CellDetector Class 
     * @param maxDistanceDiff Maximal distance that detected blobs can vary between slices of the sequence
     * @param maxIntensityDiff Maximal difference in intensity that can occur from one blob to corresponing blob in next slice
     * @param k Size of resultset from nearest-neighbor-search of blobs
     */
    public CellTrackerRelate(ImagePlus image, LifeScienceModel model, CellDetector detector, int maxDistanceDiff, int maxIntensityDiff, int k) {
        super(image);
        this.model = model;
        this.detector = detector;
        this.maxDistanceDiff = maxDistanceDiff;
        this.maxIntesityDiff = maxIntesityDiff;
        this.k = k;
    }
    
    
    
    
    //---------------- Methods
    
    /**
     * run CellTrackerRelate
     * Goes through all slices of images sequence and relates detected blobs
     */
    public void run(){
        
        // Go through all slices of sequence
        for(int i=1; i<this.image.getStackSize(); i++){
            
            // Set slice
            this.image.setSlice(i+1);
            
            // Clear point cache
            this.model.clearPoints();
            
            // Detect Nuclei (Run CellDetector) ...
            this.detector.run();
            
            
            // Go through detected points in pointcache
            for(int l=0; l<this.model.getPointCount(); l++){
                
                // current point to evaluate
                Point newpoi = this.model.getPoint(l);
                
                // Get potential matches in old pointset (get k nearest neighbor points)
                Integer[] kNN = this.model.getkNearestNuclei(this.k, newpoi.x, newpoi.y, i-1, this.maxDistanceDiff);
                
                // Test for matching intensity
                boolean matchfound = false;
                
                // Test all nearest neighbors until match found
                for(int j=0; j<kNN.length; j++){
                    // TODO - kNN only returns one neighbor - LifeScience.LOG.info("kNN - " + j + ": " +kNN[j]);
                    if(kNN[j] != null){
                        //LifeScience.LOG.info("kNN - " + j + ": " +kNN[j]);
                        
                        Nucleus oldnuc = this.model.getNucleus(kNN[j]);
                        Point oldpoi = oldnuc.getPoint(i-1);

                        // Select point if intensity does not variate to much
                        if(Math.abs(this.image.getPixel(oldpoi.x, oldpoi.y)[0]-this.image.getPixel(newpoi.x, newpoi.y)[0])<this.maxIntesityDiff){
                            oldnuc.setPoint(newpoi, i);
                            matchfound = true;
                            break;
                        }
                    }  
                }
                // TODO do something if no match found
                // if no match found create new nucleus ---> to be related yet
                if(!matchfound && kNN[0]!= null){
                    this.model.addNucleus(new Nucleus(this.image.getStackSize(), newpoi, i));
                }
                
                this.detector.groupNuclei();
                
            }

        }
        
        
        // update image
        this.image.updateAndDraw();
    }
}
