package de.lmu.dbs.lifescience.processing;

import de.lmu.dbs.lifescience.LifeScience;
import de.lmu.dbs.lifescience.model.LifeScienceModel;
import de.lmu.dbs.lifescience.model.Nucleus;
import ij.ImagePlus;
import ij.gui.OvalRoi;
import ij.plugin.filter.MaximumFinder;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;
import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * This class provides methods to track cells over images.
 * 
 * @author bea
 */
public class CellTrackerFollow extends Processor {

    //---------------- Attributes
    /** Maximum delta distance from frame i to i+1 in px  */
    int maxDistance = 0;
    
    /** Intensity Variance */
    int intesityVar = 10;
    
    /** Model    */
    LifeScienceModel model;
    
    /** Cell Detector */
    CellDetector detector;

    
    
    //---------------- Constructor
    /**
     * Create a new instance for the cell detector that performs video tracking on the sequence
     * 
     * Tracking is achieved by running the CellDetector in the surrounding of previously detected blobs. 
     * In this area of interest detected blobs are compared by intensity to ensure correct correlation.
     * 
     * @param image
     * @param model LifeScienceModel
     * @param detector CellDetector Class
     * @param maxDistance 
     */
    public CellTrackerFollow(ImagePlus image, LifeScienceModel model, CellDetector detector, int maxDistance) {
        super(image);
        this.model = model;
        this.detector = detector;
        this.maxDistance = maxDistance;
    }
    
    
    
    
    //---------------- Methods
    
    /**
     * run CellTrackerFollow
     */
    public void run(){
        
        // track each nucleus through all images
        for(int i=0; i<this.model.getNucleiCount(); i++){
            this.model.clearPoints();
            Nucleus nuc = this.model.getNucleus(i);
            for(int k=1; k<this.image.getStackSize(); k++){
                // Search in surroundings
                if(nuc.getPoint(k-1)!=null){
                    Point poi = nuc.getPoint(k-1);
                    this.image.setSlice(k+1);
                    this.image.setRoi(new OvalRoi((int) poi.getX()-(this.maxDistance/2), (int) poi.getY()-(this.maxDistance/2), this.maxDistance, this.maxDistance));

                    // Detect Nuclei...
                    this.detector.run();

                    // process hits in surrounding of previous found nucleus
                    boolean pointset = false;
                    if(this.model.getPointCount()>=1){
                        for(int l=0; l<this.model.getPointCount(); l++){
                            Point newpoi = this.model.getPoint(l);
                            int oldintensity = this.image.getPixel(poi.x, poi.y)[0];
                            int newintensity = this.image.getPixel(newpoi.x, newpoi.y)[0];
                            int diffintensity = 255;
                            
                            // improve tracking and avoid false clustering by comparing intensities
                            if((oldintensity + this.intesityVar > newintensity) && (oldintensity - this.intesityVar < newintensity)){
                                // set new Point in nucleus if intensity difference is smaller than before
                                int newdiffintensity = Math.abs(newintensity-oldintensity);
                                if(newdiffintensity<diffintensity){
                                    diffintensity = newdiffintensity;
                                    nuc.setPoint(newpoi, k);
                                    pointset = true;
                                }   
                            }
                        }
                    }
                    if(!pointset){
                        // set old Point in nucleus
                        nuc.setPoint(this.model.getNucleus(i).getPoint(k-1), k);
                    }
                }

                // create new image clip and search for maximum
                
            }
            
        }
        // update image
        this.image.updateAndDraw();
    }
}
