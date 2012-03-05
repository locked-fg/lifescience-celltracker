package de.lmu.dbs.lifescience.processing;

import de.lmu.dbs.lifescience.LifeScience;
import de.lmu.dbs.lifescience.model.LifeScienceModel;
import ij.ImagePlus;


/**
 * This abstract class provides the structure for all image processing classes. Extend this class to implement your own.
 * @author bea
 */
public abstract class Processor {

    //---------------- Attributes
    /** Imageplus that should be processed */
    protected LifeScienceModel model;
    
    
    
    //---------------- Constructor
    /**
     * Setup processor
     * @param image 
     */
    public Processor(LifeScienceModel model) {
        this.model = model;
    }
    
    
    
    
    //---------------- Methods
    /**
     * Run Processor
     */
    abstract public void run();
}
