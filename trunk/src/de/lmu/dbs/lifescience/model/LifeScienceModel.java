package de.lmu.dbs.lifescience.model;

import de.lmu.dbs.lifescience.LifeScience;
import ij.ImagePlus;
import ij.gui.ImageCanvas;
import ij.gui.Overlay;
import ij.gui.PointRoi;
import ij.measure.Calibration;
import ij.plugin.ContrastEnhancer;
import ij.plugin.filter.Analyzer;
import ij.plugin.filter.MaximumFinder;
import ij.plugin.filter.RankFilters;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;
import java.awt.Point;
import java.io.IOException;
import java.util.Observable;

/**
 *
 * @author bea
 */
public class LifeScienceModel extends Observable{

    //---------------- Constants
    /** Image status */
    public static enum Status {
        START, IMAGEREADY, CELLSDETECTED, EXPORTED
    }
    
    
    //---------------- Attributes
    
    /**
     * Holds Image Data, ImageProcessor and AWT Reprensentation of the Sequence to be examined
     */
    private ImagePlus image;
    
    /**
     * Detected nuclei
     */
    private PointRoi nuclei;
    
    /**
     * Overlay on which additional Info is displayed (upon image)
     */
    private Overlay overlay;
    
    /**
     * Status of tool chain (represented by constants)
     */
    private Status status;
    
    /**
     * Table to show results
     */
    private Analyzer table;
    
    
    
    //---------------- Constructor
    
    /**
     * Constructor of Model - sets initial status
     */
    public LifeScienceModel() {
        // set status
        this.status = LifeScienceModel.Status.START;
        
    }
    
    
    
    
    //---------------- Methods
    
    /**
     * Set new Image
     * @param image ImagePlus
     */
    public void setImage(ImagePlus image){
        if(this.image != null){
            this.image.close();
        }
        this.image = image;
        showImage();
        this.status = LifeScienceModel.Status.IMAGEREADY;
        this.setChanged();
        this.notifyObservers();
    }
    
    /**
     * Creates a new Window to hold the image
     */
    public void showImage(){
        // create Window and show
        this.image.show();
    
        // store overlay
        this.overlay = new Overlay();
        this.image.getCanvas().setOverlay(overlay);
    }
    
    /**
     * Get Info about the loaded image
     * @return String html with info
     */
    public String getImageInfoString(){
        Calibration cal = this.image.getCalibration();
        String info = "<html>";
        // get type
        String type = "";
        switch(this.getImage().getType()){
            case ImagePlus.COLOR_256: 
                type="8 Bit Color";
                break;
            case ImagePlus.COLOR_RGB: 
                type="32 Bit RGB";
                break;
            case ImagePlus.GRAY8: 
                type="8 Bit Greyscale";
                break;
            case ImagePlus.GRAY16: 
                type="16 Bit Greyscale";
                break;
            case ImagePlus.GRAY32: 
                type="32 Bit Greyscale";
                break;
        }        
        info += type + "<br><br>";
        info += "Dimensions <br>" + this.getImage().getWidth() + " x " + this.getImage().getHeight() + " px <br>" +  
                                 Math.round(cal.pixelDepth*this.image.getWidth()*100)/100.0 + " x " + Math.round(cal.pixelDepth*this.image.getHeight()*100)/100.0 + " " + cal.getUnit();
        info += "</html>";
        return info;
    }
    
    /**
     * Get Info about detected cells
     * @return String html with info
     */
    public String getDetectionInfoString(){
        String info = "<html>";
        // get type
        
        info += this.nuclei.getNCoordinates() + " nuclei found <br><br>";
        info += "</html>";
        return info;
    }
    
    
    /**
     * Detects all Cells in images
     */
    public void detectCells(){
        // get processor
        ByteProcessor process = (ByteProcessor) this.image.getProcessor();
       
        // Reduce Noise
        RankFilters filter = new RankFilters();
        filter.rank(process, RankFilters.MEDIAN, 4);
                
        // Enhance Contrast
        ContrastEnhancer contrast = new ContrastEnhancer();
        contrast.stretchHistogram(process, 0.5);
                
        // Find Maxima
        MaximumFinder maxfind = new MaximumFinder();
        maxfind.setup(null, this.image);
        maxfind.findMaxima(process, 10, ImageProcessor.NO_THRESHOLD, MaximumFinder.POINT_SELECTION, true, false);
        
        // Set Results
        this.nuclei = (PointRoi) this.image.getRoi();
        this.nuclei.setHideLabels(false);
        
        // update image
        this.image.updateAndDraw();
        
        // set status and notifyobservers
        this.status = LifeScienceModel.Status.CELLSDETECTED;
        this.setChanged();
        this.notifyObservers();
    }
    
    
    /**
     * Export detected nuclei as csv
     * @param path
     * @throws IOException 
     */
    public void exportCSV(String path) throws IOException{
        //ResultsTable table = ResultsTable.getResultsTable();
        if(this.table != null){
            this.table.measure();
        }else{
            this.table = new Analyzer(this.image);
            this.table.measure();
        }
        Analyzer.getResultsTable().saveAs(path);
        
        // set status and notifyobservers
        this.status = LifeScienceModel.Status.EXPORTED;
        this.setChanged();
        this.notifyObservers();
    }
    
    
    public ImageCanvas getCanvas(){
        return this.image.getCanvas();
    }
    
    public PointRoi getNuclei(){
        return this.nuclei;
    }
    
    public ImagePlus getImage(){
        return this.image;
    }
    
    /**
     * Set next frame of image sequence
     * @return true if next frame setable
     */
    public boolean nextFrame(){
        int slice = this.image.getSlice();
        int size = this.image.getStackSize();
                
        if((slice) < size){ // get next Frame if not last
            this.image.setSlice(slice+1);
            this.image.updateAndDraw();
            
            this.setChanged();
            this.notifyObservers();
            return true;
        }
        return false;
    }
    
    
    /**
     * Set next frame of image sequence
     * @return true if previous frame setable
     */
    public boolean previousFrame(){
        int slice = this.image.getSlice();
                
        if(slice > 0){ // get next Frame if not last
            this.image.setSlice(slice-1);
            this.image.updateAndDraw();
            
            this.setChanged();
            this.notifyObservers();
            return true;
        }
        return false;
    }
    
    /**
     * Shows Labels of Nuclei or not
     * @param show 
     */
    public void showLabels(boolean show){
        this.nuclei.setHideLabels(!show);
        this.image.updateAndDraw();
    }
    
    
    /**
     * Shows Tabel with all nuclei
     * @param show 
     */
    public void showTable(boolean show){
        if(this.table != null){
            this.table.measure();
            Analyzer.getResultsTable().show("Detected Nuclei");
        }else{
            this.table = new Analyzer(this.image);
            this.table.measure();
            Analyzer.getResultsTable().show("Detected Nuclei");
        }
        
        
    }
    
    
    /**
     * Returns the current status in tool chain
     * @return status of the model
     */
    public Status getStatus(){
        return this.status;
    }

    /**
     * Edit Nuclei selection
     * @param point 
     */
    public void editNuclei(Point point) {
        

        double oxd=this.image.getCanvas().offScreenXD(point.x), oyd=this.image.getCanvas().offScreenYD(point.y);
        
        int i = this.nuclei.isHandle(point.x, point.y);
        if( i != (-1)){
             //this.nuclei.deleteHandle(oxd, oyd);
             int newSize = this.nuclei.getNCoordinates()-1;
             int[] newX = new int[newSize];
             int[] newY = new int[newSize];
             
             for(int j = 0; j<= newSize; j++){
                 if(i > j){
                    newX[j] = this.nuclei.getXCoordinates()[j];
                    newY[j] = this.nuclei.getYCoordinates()[j];
                 }else if(i < j){
                    newX[j-1] = this.nuclei.getXCoordinates()[j];
                    newY[j-1] = this.nuclei.getYCoordinates()[j];
                 }
             }
             PointRoi newroi = new PointRoi(newX, newY, newSize);
             newroi.setLocation(this.nuclei.getBounds().x, this.nuclei.getBounds().y);
             this.nuclei = newroi;
             
             //this.nuclei.subtractPoints(new Roi(new Rectangle(200, 200, 500, 200)));
             //this.nuclei.update(false, true);
        }else{
            this.nuclei = this.nuclei.addPoint(oxd, oyd);
            
        }
        
        this.image.setRoi(this.nuclei);
        this.image.updateAndDraw();
        this.setChanged();
        this.notifyObservers();
    }
    
}
