package de.lmu.dbs.lifescience.processing;

import de.lmu.dbs.lifescience.LifeScience;
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
    
    
    
    
    //---------------- Constructor
    /**
     * Create new ImageEnhancer with specific properties and the image to work on
     * @param image ImagePlus that should be enhanced
     * @param quickEnhance True if quick version of enhancment is to be used
     */
    public ImageEnhancer(ImagePlus image, boolean quickEnhance) {
        super(image);
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
       
        // Reduce Noise and substract background
        RankFilters filter = new RankFilters();
        BackgroundSubtracter substract = new BackgroundSubtracter();        
        for(int i=1; i<=this.image.getStackSize(); i++ ){
            this.image.setSliceWithoutUpdate(i);
            filter.rank(process, RankFilters.MEDIAN, 4);
            if(!this.quickEnhance){
                substract.rollingBallBackground(process, 20, false, false, false, true, true);
            }
        }
        //reset slice
        this.image.setSliceWithoutUpdate(1);
        
        // Enhance Contrast
        ContrastEnhancer contrast = new ContrastEnhancer();
        contrast.stretchHistogram(process, 0.5);
        
        // update image
        this.image.updateAndDraw();
        
    }
}
