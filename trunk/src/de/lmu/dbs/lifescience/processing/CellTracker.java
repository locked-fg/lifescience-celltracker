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
public class CellTracker extends Processor {

    //---------------- Attributes
    /** Maximum delta distance from frame i to i+1 in px  */
    int maxDistance = 0;
    
    /** Model    */
    LifeScienceModel model;
    
    /** Cell Detector */
    CellDetector detector;

    
    
    //---------------- Constructor
    /**
     * Create a new instance for the cell detector that performs video tracking on the sequence
     * 
     * @param image
     * @param maxDistance 
     */
    public CellTracker(ImagePlus image, LifeScienceModel model, CellDetector detector, int maxDistance) {
        super(image);
        this.model = model;
        this.detector = detector;
        this.maxDistance = maxDistance;
    }
    
    
    
    
    //---------------- Methods
    
    /**
     * run CellTracker
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
                    //LifeScience.LOG.info("Point: " + poi.getLocation().toString() + " - Roi: " + this.image.getRoi().toString());
                    // Detect Nuclei...
                    this.detector.run();

                    // Nearest Neighbor search
                    if(this.model.getPointCount()>=1){
                        nuc.setPoint(this.model.getPoint(this.model.getPointCount()-1), k);
                        //LifeScience.LOG.info("Point: " + this.model.getPoint(this.model.getPointCount()-1));
                    }
                }

                // create new image clip and search for maximum
                
            }
            
        }
        // update image
        this.image.updateAndDraw();
    }
}
