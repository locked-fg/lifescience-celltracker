package de.lmu.dbs.lifescience.model;

import de.lmu.dbs.lifescience.LifeScience;
import ij.ImagePlus;
import ij.gui.ImageCanvas;
import ij.gui.Line;
import ij.gui.Overlay;
import ij.gui.PointRoi;
import ij.gui.TextRoi;
import ij.measure.Calibration;
import ij.plugin.ScaleBar;
import ij.plugin.filter.Analyzer;
import ij.plugin.frame.RoiManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author bea
 */
public class LifeScienceModel extends Observable{

    //---------------- Constants
    /** Image status */
    public static enum Status {
        START, IMAGEREADY, IMAGEENHANCED, CELLSDETECTED, CELLSTRACKED, EXPORTED
    }
    
    
    //---------------- Attributes
    
    /**
     * Holds Image Data, ImageProcessor and AWT Reprensentation of the Sequence to be examined
     */
    private ImagePlus image;
    
    /**
     * Detected nuclei
     */
    private PointRoi pointroi;
    
    /**
     * Detected cells
     */
    private RoiManager roimanager;
    
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
    
    /**
     * List of detected cells
     */
    private ArrayList<Cell> cells;
    
    /**
     * List of detected cells
     */
    private ArrayList<Nucleus> nuclei;
    
    /**
     * Scalebar to show upon images
     */
    private Line scalebar;
    
    /**
     * Label for scalebar
     */
    private TextRoi scaleinfo;

    
    
    
    //---------------- Constructor
    
    /**
     * Constructor of Model - sets initial status
     */
    public LifeScienceModel() {
        // set status
        this.status = LifeScienceModel.Status.START;
        
        // set cell collection
        this.roimanager = new RoiManager(true);
        
        //set progress to 0 percent
        this.status = LifeScienceModel.Status.START;
        
        //set cell collection
        this.cells = new ArrayList<>();
        this.nuclei = new ArrayList<>();
        
    }
    
    
    
    
    //---------------- Methods
    
    /**
     * Set new Image
     * @param image ImagePlus
     */
    public void setImage(ImagePlus image){
        if(this.image != null){ // reset all
            this.image.close();
        }
        this.image = image;
         // configure scale
        
        showImage();   
        
        // set scalebar
        this.scalebar = new Line(20, 20, 50, 20);
        this.scalebar.setStrokeColor(Color.CYAN);
        this.scalebar.setName("Approximate nuclei width");
        this.scaleinfo = new TextRoi(20, 30, Math.round(this.image.getCalibration().pixelDepth*this.scalebar.getBounds().width * 100 /100.0) + " " + this.image.getCalibration().getUnit() + " (" + this.scalebar.getBounds().width + " px)",  new Font("Verdana", Font.PLAIN, 14));
        this.scaleinfo.setStrokeColor(Color.CYAN);
        this.scalebar.setPosition(0);
        this.scaleinfo.setPosition(0);
        this.overlay.add(this.scalebar);
        this.overlay.add(this.scaleinfo);
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
        info += this.getImage().getWidth() + " x " + this.getImage().getHeight() + " px <br>" +  
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
        
        info += this.pointroi.getNCoordinates() + " nuclei found <br><br>";
        info += "</html>";
        return info;
    }
    
    /**
     * Get the average diameter of cells
     * @return Pixel of diameter
     */
    public int getNucleiDiameter(){
        return this.scalebar.getBounds().width;
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
    }
    
    
    public ImageCanvas getCanvas(){
        return this.image.getCanvas();
    }
    
    /**
     * Return nucleus with index
     * @param index
     * @return Nucleus
     */
    public Nucleus getNucleus(int index){
        return this.nuclei.get(index);
    }
    
    /**
     * Add a new cell to cell collection
     * @param cell 
     */
    public void addCell(Cell cell){
        this.cells.add(cell);
    }
    
    
    /**
     * Set nuclei as PointRoi
     * @param nuclei 
     */
    public void addNucleus(Nucleus nucleus){
        this.nuclei.add(nucleus);
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
        this.pointroi.setHideLabels(!show);
        this.image.updateAndDraw();
    }
    
    
    /**
     * Shows Tabel with all nuclei
     * @param show 
     */
    public void showTable(boolean show){
        if(this.table != null){
            this.table.measure();
            Analyzer.getResultsTable().show("Results");
        }else{
            this.table = new Analyzer(this.image);
            this.table.measure();
            Analyzer.getResultsTable().show("Results");
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
     * Set Status and notify Observers
     * @param status Enum
     */
    public void setStatus(Status status){
        this.status = status;
        this.setChanged();
        this.notifyObservers();
    }
    
    /**
     * Number of Cells detected
     * @return number of cells
     */
    public int getCellCount(){
        return this.cells.size();
    }
    
    /**
     * Number of Nuclei detected
     * @return number of nuclei
     */
    public int getNucleiCount(){
        return this.nuclei.size();
    }
    
    /**
     * Set new Pointroi according to cells
     */
    public void drawNuclei(){
        int index = this.image.getCurrentSlice()-1;
        int[] ox = new int[this.nuclei.size()];
        int[] oy = new int[this.nuclei.size()];
        int offx = this.image.getWidth();
        int offy = this.image.getHeight();
        for (int i=0; i<this.nuclei.size(); i++){
            Point p = this.nuclei.get(i).getPoint(index);
            ox[i] = p.x;
            oy[i] = p.y;
            if(p.x < offx){
                offx = p.x;
            }
            if(p.y < offy){
                offy = p.y;
            }
        }
        PointRoi points = new PointRoi(ox, oy, this.nuclei.size());
        points.setLocation(offx, offy);
        points.setPosition(this.image.getCurrentSlice());
        points.setStrokeColor(Color.cyan);
        this.overlay.add(points);
        this.pointroi = points;
        this.image.updateAndDraw();
    }
    
    /**
     * Set new ovalrois according to cells
     */
    public void drawCells(){
        for (int i=0; i<this.nuclei.size(); i++){
            if(this.nuclei.get(i).getCell()!=null){
                
            }
        }
    }
    
    
    /**
     * Delete a nucleus on location xcoord,ycoord on slice
     * @param slice
     * @param xcoord
     * @param ycoord 
     * @return true if nucleus could be deleted
     */
    public boolean deleteNucleus(int slice, int xcoord, int ycoord){
        for(int i=0; i<this.nuclei.size(); i++){
            if(this.nuclei.get(i).getPoint(slice).equals(new Point(xcoord, ycoord))){
                this.nuclei.remove(i);
                return true;
            }
        }
        return false;
    }
    
   
    
    /**
     * Edit Nuclei selection
     * @param point 
     */
    public void editNuclei(Point point) {
        // delete roi from overlay
        this.overlay.remove(this.pointroi);
        
        double oxd=this.image.getCanvas().offScreenXD(point.x), oyd=this.image.getCanvas().offScreenYD(point.y);
        
        int i = this.pointroi.isHandle(point.x, point.y);
        
        //remove
        if(i!=(-1)){
            this.deleteNucleus(this.image.getCurrentSlice()-1, this.pointroi.getXCoordinates()[i] + this.pointroi.getBounds().x, this.pointroi.getYCoordinates()[i] + this.pointroi.getBounds().y);
        }else{
            this.addNucleus(new Nucleus(this.image.getStackSize(), (int)oxd, (int)oyd));
        }
     
        
        /** old direct manipulation of roi
        double oxd=this.image.getCanvas().offScreenXD(point.x), oyd=this.image.getCanvas().offScreenYD(point.y);
        
        int i = this.pointroi.isHandle(point.x, point.y);
        
        if( i != (-1)){
             int newSize = this.pointroi.getNCoordinates()-1;
             int[] newX = new int[newSize];
             int[] newY = new int[newSize];
             
             for(int j = 0; j<= newSize; j++){
                 if(i > j){
                    newX[j] = this.pointroi.getXCoordinates()[j];
                    newY[j] = this.pointroi.getYCoordinates()[j];
                 }else if(i < j){
                    newX[j-1] = this.pointroi.getXCoordinates()[j];
                    newY[j-1] = this.pointroi.getYCoordinates()[j];
                 }
             }
             PointRoi newroi = new PointRoi(newX, newY, newSize);
             // calculate offset
             int xoffset = 0;
             int yoffset = 0;
             if(this.pointroi.getXCoordinates()[i] == 0){
                 xoffset = this.pointroi.getBounds().width - newroi.getBounds().width;
             }
             if(this.pointroi.getYCoordinates()[i] == 0){
                 yoffset = this.pointroi.getBounds().height - newroi.getBounds().height;
             }
             
             newroi.setLocation(this.pointroi.getBounds().x+xoffset, this.pointroi.getBounds().y+yoffset);
             this.pointroi = newroi; 
             
        }else{
            this.pointroi = this.pointroi.addPoint(oxd, oyd);
        }
        
        this.pointroi.setPosition(this.image.getCurrentSlice());
        this.pointroi.setStrokeColor(Color.cyan);
        this.image.getOverlay().add(this.pointroi);
        */
        
        
        this.drawNuclei();
        this.setChanged();
        this.notifyObservers();
        this.image.updateAndDraw();
    }
    
}
