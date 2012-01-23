package de.lmu.dbs.lifescience.processing;

import de.lmu.dbs.lifescience.LifeScience;
import de.lmu.dbs.lifescience.model.LifeScienceModel;
import ij.ImagePlus;
import ij.gui.PointRoi;
import ij.plugin.filter.MaximumFinder;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;


/**
 *
 * @author bea
 */
public class CellDetector extends Processor {

    //---------------- Attributes
    /** Model */
    LifeScienceModel model;
    
    
    //---------------- Constructor
    
    public CellDetector(ImagePlus image, LifeScienceModel model) {
        super(image);
        this.model = model;
    }
    
    
    
    
    //---------------- Methods
    /**
     * Run CellDetector
     */
    public void run(){
        // get processor
        ByteProcessor process = (ByteProcessor) this.image.getProcessor();
        
         
        // Find Maxima
        MaximumFinder maxfind = new MaximumFinder();
        maxfind.setup(null, this.image);
        maxfind.findMaxima(process, 10, ImageProcessor.NO_THRESHOLD, MaximumFinder.POINT_SELECTION, true, false);

        // TODO Set Results ( changing to own Type) 
        
        this.model.setNuclei((PointRoi) this.image.getRoi());
        this.model.getNuclei().setHideLabels(false);
                
        // update image
        this.image.updateAndDraw();
        
    }
}
