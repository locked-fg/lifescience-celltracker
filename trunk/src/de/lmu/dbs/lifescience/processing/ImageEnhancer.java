package de.lmu.dbs.lifescience.processing;

import de.lmu.dbs.lifescience.LifeScience;
import de.lmu.dbs.lifescience.model.LifeScienceModel;
import ij.ImagePlus;
import ij.plugin.ContrastEnhancer;
import ij.plugin.filter.BackgroundSubtracter;
import ij.plugin.filter.RankFilters;
import ij.process.ByteProcessor;

/**
 * This class implements a set of image enhancements to prepare the image for further processing
 * 
 * @author bea
 */
public class ImageEnhancer extends Processor {

    
    
    //---------------- Attributes

    /** Quick version of enhancing (not as thorough) */
    private boolean quickEnhance;
    
    /** Image to perform enhancement on  */
    ImagePlus image;
    
    
    
    
    //---------------- Constructor
    /**
     * Create new ImageEnhancer with specific properties and the image to work on
     * @param image ImagePlus that should be enhanced
     * @param quickEnhance True if quick version of enhancment is to be used
     */
    public ImageEnhancer(LifeScienceModel model, boolean quickEnhance) {
        super(model);
        this.image = this.model.getImage();
        this.quickEnhance = quickEnhance;
    }
    
    
    //---------------- Methods
    /**
     * Run Image Enhancer
     */
    public void run(){
    
        // Clear Rois
        this.image.killRoi();
        
        // get processor
        ByteProcessor process = (ByteProcessor) this.image.getProcessor();
        
        // Enhance Contrast
        ContrastEnhancer contrast = new ContrastEnhancer();
        ij.IJ.run("Enhance Contrast", "saturated=0.4 normalize_all");
       
        // Reduce Noise and substract background
        RankFilters filter = new RankFilters();
        BackgroundSubtracter substract = new BackgroundSubtracter();        
        for(int i=1; i<=this.image.getStackSize(); i++ ){
            this.image.setSliceWithoutUpdate(i);
            filter.rank(process, 4, RankFilters.MEDIAN);
            // TODO test smoothing...
            // process.smooth();
            if(!this.quickEnhance){
                substract.rollingBallBackground(process, this.model.getNucleiDiameter()/2, false, false, false, true, true);
            }
        }
        //reset slice
        this.image.setSliceWithoutUpdate(1);
        
        
        //contrast.stretchHistogram(process, 0.1);
               
        // update image
        this.image.updateAndDraw();
        
    }
}
