package de.lmu.dbs.lifescience.processing;

import de.lmu.dbs.lifescience.LifeScience;
import de.lmu.dbs.lifescience.model.Cell;
import de.lmu.dbs.lifescience.model.LifeScienceModel;
import de.lmu.dbs.lifescience.model.Nucleus;
import ij.ImagePlus;
import ij.gui.OvalRoi;
import ij.gui.PointRoi;
import ij.plugin.filter.MaximumFinder;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;
import java.awt.Point;
import java.awt.geom.Point2D;


/**
 *
 * @author bea
 */
public class CellDetector extends Processor {

    //---------------- Attributes
    /** Model */
    ImagePlus image;
    
    
    //---------------- Constructor
    
    /**
     * Set up detection of nuclei and group
     * @param image
     * @param model 
     */
    public CellDetector(LifeScienceModel model) {
        super(model);
        this.image = model.getImage();
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
        // rolling ball 8 - else 10
        maxfind.findMaxima(process, 10, ImageProcessor.NO_THRESHOLD, MaximumFinder.POINT_SELECTION, true, false);

        if (this.image.getRoi() instanceof PointRoi){
            // Build data set
            PointRoi points = (PointRoi) this.image.getRoi();
            // info: coordinates sorted by intensity 
            int[] xcoords = points.getXCoordinates();
            int[] ycoords = points.getYCoordinates();
            int xoff = points.getBounds().x;
            int yoff = points.getBounds().y;

            // check if initial detection or followup
            if(this.model.getNucleiCount()>0){
                for(int i=0; i < xcoords.length; i++){
                    // add Points
                    this.model.addPoint(new Point(xcoords[i]+xoff, ycoords[i]+yoff));
                }

            }else{
                for(int i=0; i < xcoords.length; i++){
                    //TODO look in previous 10 cells and group nuclei if distance is smaller than cell radius
                    // works becaus coordinates are sorted by intensity of pixels. corresponding nuclei mostly have similar intensities.
                    //TODO split nuclei through watershed algorithm
                    //create new nucleus

                    this.model.addNucleus(new Nucleus(this.image.getImageStackSize(), xcoords[i]+xoff, ycoords[i]+yoff));
                }
            }
        }
        
        
        this.image.killRoi();
                
        // update image
        this.image.updateAndDraw();
        
    }
    
    /**
     * This class does an initial grouping of nuclei. Nuclei are grouped to belong to one cell if their distance is smaller than the average nuclei diameter.
     */
    public void groupNuclei(){
        int index = this.model.getImage().getCurrentSlice()-1;
        for(int i=0; i < this.model.getNucleiCount(); i++){
            Nucleus nuc = this.model.getNucleus(i);
            if(nuc.getCell()!=null){
                continue;
            }
            for(int j=-20; j < 20; j++){
                // group nuclei if distance is smaller than average nuclei diameter
                if((0 <= i+j) && (i+j < this.model.getNucleiCount()) && (j !=0) ){
                    Nucleus nucc = this.model.getNucleus(i+j);
                    if(nucc.getCell()==null &&
                            nucc.getPoint(index)!=null &&
                            nuc.getPoint(index)!=null &&
                            nucc.getPoint(index).distance(nuc.getPoint(index)) < (this.model.getNucleiDiameter())){
                        Cell cell = new Cell(nuc);
                        cell.addNucleus(nucc);
                        this.model.addCell(cell);
                        break;
                    }
                }
            }
        }
    }
    
    /**
     * Evaluate grouping of nuclei over whole sequence.
     * Break up grouping if nuclei wander apart.
     */
    public void evaluateGrouping(){
        for(int i=0; i<this.model.getCellCount(); i++){
            for(int j=0; j<this.image.getStackSize(); j++){
                Nucleus[] nucs = this.model.getCell(i).getNuclei();
                if(this.model.getCell(i).isTetraploid() &&
                    nucs[0].getPoint(j)!=null && nucs[1].getPoint(j)!=null){
                    // calculate euclidean distance and delete second nucleus if necessary
                    double euclid = Math.sqrt(Math.pow((nucs[0].getPoint(j).x - nucs[1].getPoint(j).x), 2.0) + Math.pow((nucs[0].getPoint(j).y - nucs[1].getPoint(j).y), 2.0));
                    if(euclid > this.model.getNucleiDiameter()*2){
                        this.model.getCell(i).removeNucleus();
                    }
                }
            }
        }
    }
}
