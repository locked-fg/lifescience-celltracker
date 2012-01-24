package de.lmu.dbs.lifescience.processing;

import de.lmu.dbs.lifescience.LifeScience;
import de.lmu.dbs.lifescience.model.Cell;
import de.lmu.dbs.lifescience.model.LifeScienceModel;
import de.lmu.dbs.lifescience.model.Nucleus;
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

        // Build data set
        PointRoi points = (PointRoi) this.image.getRoi();
        int[] xcoords = points.getXCoordinates();
        int[] ycoords = points.getYCoordinates();
        int xoff = points.getBounds().x;
        int yoff = points.getBounds().y;
        
        // Detect Cells in first Image
        for(int i=0; i < xcoords.length; i++){
            this.model.addCell(new Cell(new Nucleus(this.image.getImageStackSize(), xcoords[i]+xoff, ycoords[i]+yoff)));
        }
        this.image.killRoi();
                
        // update image
        this.image.updateAndDraw();
        
    }
}
