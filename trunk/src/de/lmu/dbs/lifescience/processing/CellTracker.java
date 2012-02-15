package de.lmu.dbs.lifescience.processing;

import de.lmu.dbs.lifescience.model.LifeScienceModel;
import ij.ImagePlus;

/**
 * This class provides methods to track cells over images.
 * 
 * @author bea
 */
public class CellTracker extends Processor {

    //---------------- Attributes
    /** Maximum delta distance from frame i to i+1 in px  */
    int maxDistance = 0;
    
    /** Model    */
    LifeScienceModel model;
    
    
    //---------------- Constructor
    /**
     * Create a new instance for the cell detector that performs video tracking on the sequence
     * 
     * @param image
     * @param maxDistance 
     */
    public CellTracker(ImagePlus image, LifeScienceModel model, int maxDistance) {
        super(image);
        this.model = model;
    }
    
    
    
    
    //---------------- Methods
    
    /**
     * run CellTracker
     */
    public void run(){
        // track each nucleus through all images
        for(int i=0; i<this.model.getNucleiCount(); i++){
            this.model.getNucleus(i);
        }
    }
}
