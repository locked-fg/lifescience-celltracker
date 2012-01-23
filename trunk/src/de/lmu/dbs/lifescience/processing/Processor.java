package de.lmu.dbs.lifescience.processing;

import de.lmu.dbs.lifescience.LifeScience;
import ij.ImagePlus;


/**
 * This abstract class provides the structure for all image processing classes. Extend this class to implement your own.
 * @author bea
 */
public abstract class Processor {

    //---------------- Attributes
    /** Imageplus that should be processed */
    protected ImagePlus image;
    
    
    
    //---------------- Constructor
    
    public Processor(ImagePlus image) {
        this.image = image;
    }
    
    
    
    
    //---------------- Methods
    /**
     * Run Processor
     */
    abstract public void run();
}
