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
        // info: coordinates sorted by intensity 
        int[] xcoords = points.getXCoordinates();
        int[] ycoords = points.getYCoordinates();
        int xoff = points.getBounds().x;
        int yoff = points.getBounds().y;
        
        Nucleus nucleus;

        // Detect Nuclei in first Image
        for(int i=0; i < xcoords.length; i++){
            //TODO look in previous 10 cells and group nuclei if distance is smaller than cell radius
            // works becaus coordinates are sorted by intensity of pixels. corresponding nuclei mostly have similar intensities.
            //TODO split nuclei through watershed algorithm
            //create new nucleus

            this.model.addNucleus(new Nucleus(this.image.getImageStackSize(), xcoords[i]+xoff, ycoords[i]+yoff));
        }
        this.image.killRoi();
                
        // update image
        this.image.updateAndDraw();
        
    }
    
    /**
     * This class does an initial grouping of nuclei. Nuclei are grouped to belong to one cell if their distance is smaller than the average nuclei diameter.
     */
    public void groupNuclei(){
        for(int i=0; i < this.model.getNucleiCount(); i++){
            Nucleus nuc = this.model.getNucleus(i);
            if(nuc.getCell()!=null){
                break;
            }
            for(int j=-5; j < 5; j++){
                // group nuclei if distance is smaller than average nuclei diameter
                if(0 <= i+j && i+j < this.model.getNucleiCount()){
                    Nucleus nucc = this.model.getNucleus(i+j);
                    if(nucc.getPoint(this.model.getImage().getCurrentSlice()-1).distance(nuc.getPoint(this.model.getImage().getCurrentSlice()-1)) < this.model.getNucleiDiameter()){
                        Cell cell = new Cell(nuc);
                        cell.addNucleus(nucc);
                        this.model.addCell(cell);
                    }
                }
            }
        }
    }
}
