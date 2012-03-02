package de.lmu.dbs.lifescience.ui;

import ij.IJ;
import ij.gui.ImageCanvas;
import ij.ImagePlus;
import ij.gui.Overlay;
import java.awt.Rectangle;
import java.io.Serializable;

/**
 * @deprecated 
 * @author bea
 */
public class LifeScienceImageCanvas extends ImageCanvas implements Serializable{

    //---------------- Attributes
    
    //---------------- Constructor
    /**
     * Constructs a new ImageCanvas with empty image
     */
    public LifeScienceImageCanvas() {
        super(new ImagePlus());
    }
    
    /**
     * Constructs a new ImageCanvas with given image
     * @param path to Image 
     */
    public LifeScienceImageCanvas(String path) {
        super(IJ.openImage(path));
        this.setOverlay(new Overlay());
    }
    
    /**
     * Constructs a new ImageCanvas with given image
     * @param img ImagePlus image 
     */
    public LifeScienceImageCanvas(ImagePlus img) {
        super(img);
        
    }
    
    
    //---------------- Methods
    
    /**
     * Set next frame of image sequence
     * @return true if next frame setable
     */
    public boolean nextFrame(){
        int slice = this.getImage().getSlice();
        int size = this.getImage().getStackSize();
                
        if((slice) < size){ // get next Frame if not last
            this.getImage().setSlice(slice+1);
            this.setImageUpdated();
            this.repaint();
            return true;
        }
        return false;
    }
    
    
    /**
     * Set next frame of image sequence
     * @return true if previous frame setable
     */
    public boolean previousFrame(){
        int slice = this.getImage().getSlice();
                
        if(slice > 0){ // get next Frame if not last
            this.getImage().setSlice(slice-1);
            this.setImageUpdated();
            this.repaint();
            return true;
        }
        return false;
    }
    
    /**
     * Set new image
     * @param path 
     */
    public void setNewImage(String path){
        // Open new ImageJ
        ImagePlus img = new ImagePlus(path);
        this.getImage().setImage(img);
        this.setImageUpdated();
        this.validate();
        this.repaint();
    }
    
    
    /**
     * Resize Image in Canvas to specified width
     * @param width 
     * @param height
     */
    public void setImageSize(int width, int height){
        this.dstHeight = height;
        this.dstWidth = width;
       
        System.out.println(this.getImage().getWidth());
        if(this.getImage().getRoi() != null){
            this.getImage().getRoi().setNonScalable(false);
            System.out.println(this.getOverlay().get(0).getBounds().getWidth());
            
        }
        
        // set SourceRect
        this.setSourceRect(new Rectangle(0, 0, this.getImage().getWidth(), this.getImage().getHeight()));
        
        // calculate magnification
        double xmagnificator = (double) width / (double) this.getImage().getWidth();
        double ymagnificator = (double) height / (double) this.getImage().getHeight();
        if(xmagnificator > ymagnificator){
            this.setDrawingSize((int) (this.getImage().getWidth() * ymagnificator), (int) (this.getImage().getHeight() * ymagnificator));
            this.setMagnification(ymagnificator);
            
        }else{
            this.setDrawingSize((int) (this.getImage().getWidth() * xmagnificator), (int) (this.getImage().getHeight() * xmagnificator));
            this.setMagnification(xmagnificator);
        }
        this.getImage().repaintWindow();
        if(this.getImage().getRoi() != null){
            this.getImage().notifyAll();
        }
        this.setImageUpdated();
        
        this.repaint();
        
    }
    
    
    /** Enlarge the canvas if the user enlarges the window.
     * @param width 
     * @param height 
     */
	public void resizeCanvas(int width, int height) {
		
		if (IJ.altKeyDown())
			{fitToWindow(); return;}
		//if (srcRect.width<imageWidth || srcRect.height<imageHeight) {
			if (width>imageWidth*magnification)
				width = (int)(imageWidth*magnification);
			if (height>imageHeight*magnification)
				height = (int)(imageHeight*magnification);
			setDrawingSize(width, height);
			srcRect.width = (int)(dstWidth/magnification);
			srcRect.height = (int)(dstHeight/magnification);
			if ((srcRect.x+srcRect.width)>imageWidth)
				srcRect.x = imageWidth-srcRect.width;
			if ((srcRect.y+srcRect.height)>imageHeight)
				srcRect.y = imageHeight-srcRect.height;
			repaint();
		
		//IJ.log("resizeCanvas2: "+srcRect+" "+dstWidth+"  "+dstHeight+" "+width+"  "+height);
	}
  
}
