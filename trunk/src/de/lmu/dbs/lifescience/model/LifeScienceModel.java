package de.lmu.dbs.lifescience.model;

import de.lmu.dbs.lifescience.LifeScience;
import ij.ImagePlus;
import ij.ImageStack;
import ij.gui.EllipseRoi;
import ij.gui.ImageCanvas;
import ij.gui.Line;
import ij.gui.OvalRoi;
import ij.gui.Overlay;
import ij.gui.PointRoi;
import ij.gui.TextRoi;
import ij.measure.Calibration;
import ij.measure.ResultsTable;
import ij.plugin.filter.AVI_Writer;
import ij.plugin.filter.Analyzer;
import ij.plugin.frame.RoiManager;
import ij.process.ByteProcessor;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.TreeMap;

/**
 *
 * @author bea
 */
public class LifeScienceModel extends Observable{

    //---------------- Constants
    /** Image status */
    public static enum Status {

        /** Start of program  */
        START,
        /** Image was successfully loaded */
        IMAGEREADY,
        /** Image enhancement done   */
        IMAGEENHANCED,
        /** Cell detection complete  */
        CELLSDETECTED,
        /** Cell tracking complete  */
        CELLSTRACKED,
        /** CSV or AVI export done    */
        EXPORTED
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
     * Detected Points
     */
    private ArrayList<Point> points;
    
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
     * Results Table for export
     */
    private ResultsTable results;
    
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
        
        // set progress to 0 percent
        this.status = LifeScienceModel.Status.START;
        
        // set cell collection
        this.cells = new ArrayList<>();
        this.nuclei = new ArrayList<>();
        this.points = new ArrayList<>();
        
        // set results table
        this.results = new ResultsTable();
        
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
        this.resetOverlay();
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
        
        info += this.getNucleiCount() + " nuclei found <br><br>";
        info += this.getCellCount() + " cells found <br><br>";
        info += this.countTetraploids() + " tetraploids found <br><br>";
        info += this.countMitoticEvents() + " mitotic events found <br><br>";
        info += "</html>";
        return info;
    }
    
    
    /**
     * Number of tetraploid cells
     * @return int
     */
    public int countTetraploids(){
        int count = 0;
        for(int i=0; i<this.cells.size(); i++){
            if(this.cells.get(i).isTetraploid()){
                count++;
            }
        }
        return count;
    }
    
    /**
     * Number of mitotic events
     * @return int
     */
    public int countMitoticEvents(){
        int count = 0;
        for(int i=0; i<this.cells.size(); i++){
            if(this.cells.get(i).getTimeStartMitosis() > 1){
                count++;
            }
        }
        return count;
    }
    
    
    /**
     * Get the average diameter of cells
     * @return Pixel of diameter
     */
    public int getNucleiDiameter(){
        return this.scalebar.getBounds().width;
    }
    
    /**
     * Fills the results table with current status and infos
     */
    public void setResults(){
        // clear clear
        this.results.reset();
        this.results.incrementCounter();
        
        // fill results
        if(this.cells.size()>0){
            for(int i=0; i<this.cells.size(); i++){
                Cell cell = this.cells.get(i);
                this.results.addValue("Cell", i);
                this.results.addValue("TrackStart", cell.getTimeStart());
                this.results.addValue("TrackEnd", cell.getTimeEnd());
                int ploidy = 2;
                if(cell.isTetraploid()){
                    ploidy = 4;
                }
                this.results.addValue("Ploidy", ploidy);
                if(cell.getNuclei()[0].getPoint(0)!=null){
                    this.results.addValue("Position-X", cell.getNuclei()[0].getPoint(0).x);
                    this.results.addValue("Position-Y", cell.getNuclei()[0].getPoint(0).y);
                }
                this.results.addValue("Fate", 0);
                this.results.addValue("Mitosis", cell.getTimeStartMitosis());
                this.results.addValue("Death", cell.getTimeDeath());
                this.results.addLabel("Comment", "");
                
                // Increment results counter
                this.results.incrementCounter();
            }
        }
        this.results.addLabel("Comment", "Sequence: " + this.image.getFileInfo().fileName + " (" + this.image.getStackSize() + " Images)");
    }
    
    
    /**
     * Export detected cells as csv
     * @param path
     * @throws IOException 
     */
    public void exportCSV(String path) throws IOException{
        // generate results
        this.setResults();
        
        // save results
        this.results.saveAs(path);
        
        // set status and notifyobservers
        this.status = LifeScienceModel.Status.EXPORTED;
    }
    
    
    /**
     * Export detected cells as avi files (indices will be added)
     * @param path 
     * @throws IOException
     */
    public void exportAVI(String path) throws IOException{
        // size of avi clip
        int size = this.getNucleiDiameter() * 3;
        
        // foreach detected cell
        for(int j=0; j<this.getCellCount(); j++){
            // create new stack
            ImageStack imagestack = new ImageStack(size, size);
            for(int i=0; i<this.image.getStackSize(); i++){
                Point cellcenter = this.cells.get(j).getCenter(i);
                if(cellcenter != null){
                    // set roi
                    this.image.setSlice(i+1);
                    this.image.killRoi();
                    this.image.setRoi(new Rectangle(cellcenter.x-(size/2), cellcenter.y-(size/2), size, size));
                    ByteProcessor clipprocessor = (ByteProcessor) this.image.getProcessor().crop();
                    if(clipprocessor.getWidth() < size || clipprocessor.getHeight() < size){
                        ByteProcessor newprocessor = new ByteProcessor(size, size);
                        newprocessor.setBackgroundValue(0);
                        int xoff = 0;
                        int yoff = 0;
                        if(cellcenter.x-(size/2)<0){
                            xoff = Math.abs(cellcenter.x-(size/2));
                        }
                        if(cellcenter.y-(size/2)<0){
                            yoff = Math.abs(cellcenter.y-(size/2));
                        }
                        for(int k=0; k<clipprocessor.getWidth(); k++){
                            for(int l=0; l<clipprocessor.getHeight(); l++){
                                newprocessor.putPixel(k+xoff, l+yoff, clipprocessor.getPixel(k, l));
                            }
                        }
                        clipprocessor = newprocessor;
                    }
                    imagestack.addSlice("Label", clipprocessor);
                }
            }
            this.image.killRoi();
            ImagePlus imageclip = new ImagePlus("Clip", imagestack);
            
            // write avi
            AVI_Writer writer = new AVI_Writer();
            writer.writeImage(imageclip, path.replace(".avi", "-"+j+".avi"), AVI_Writer.JPEG_COMPRESSION, 80);
            
            // reset image
            this.image.setSlice(1);
        }
    }
    
    /**
     * Get Canvas element of activeimage
     * @return Canvas
     */
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
     * Return cell at indes
     * @param index
     * @return Cell
     */
    public Cell getCell(int index){
        return this.cells.get(index);
    }
    
    
    /**
     * Set nuclei
     * @param nucleus 
     */
    public void addNucleus(Nucleus nucleus){
        this.nuclei.add(nucleus);
    }
    
    
     /**
     * Add point to temporary List of Points (not yet assigned to a nucleus)
     * @param point 
     */
    public void addPoint(Point point){
        this.points.add(point);
    }
    
    /**
     * Return point at position index
     * @param index
     * @return Point
     */
    public Point getPoint(int index){
        return this.points.get(index);
    }
    
    /**
     * Get number of Points
     * @return int size of pointdump
     */
    public int getPointCount(){
        return this.points.size();
    }
    
    /**
     * Reset point dump
     */
    public void clearPoints(){
        this.points.clear();
    }
    
    /**
     * Get ImagePlus of active Image
     * @return ImagePlus
     */
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
        //this.overlay.drawLabels(show);
        this.overlay.drawNames(show);
        this.overlay.setLabelColor(Color.red);
        this.overlay.setLabelFont(new Font("Verdana", Font.BOLD, 12));
        for(int i=0; i<this.overlay.size(); i++){
            if(this.overlay.get(i) instanceof PointRoi){
                PointRoi roi = (PointRoi) this.overlay.get(i);
                roi.setHideLabels(true);
            }else if(this.overlay.get(i) instanceof OvalRoi){
                // no function yet
            }else{
                this.overlay.get(i).setName("");
            }
        }
        this.image.updateAndDraw();
        this.setChanged();
        this.notifyObservers();
    }
    
    
    
    /**
     * Shows Markers of nuclei and cells or not
     * @param show 
     */
    public void showMarkers(boolean show){
        if(show){
            this.drawCells();
            this.drawNuclei();
        }else{
            this.resetOverlay();
        }
        this.image.updateAndDraw();
        this.setChanged();
        this.notifyObservers();
    }
    
    
    /**
     * Clear nuclei and cells from view
     */
    public void resetOverlay(){
        this.overlay.clear();
        this.overlay.add(this.scalebar);
        this.overlay.add(this.scaleinfo);
    }
    
    
    /**
     * Shows Tabel with all nuclei
     * @param show 
     */
    public void showTable(boolean show){
        // set results
        this.setResults();
        
        // show table
        this.results.show("Results");
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
     * Return k nearest Nuclei to a given point(x,y) if they are within given radius.
     * Only search in specified slice.
     * 
     * TODO why only one return value not k??? => radius
     * 
     * @param k size of return set
     * @param x - coordinate of search point
     * @param y - coordinate of search point
     * @param index of image stack
     * @param radius in which to search for kNN
     * @return indices of found nuclei
     */
    public Integer[] getkNearestNuclei(int k, int x, int y, int index, int radius){
        
        // Treemap to administer results
        TreeMap<Double, Integer> nucs = new TreeMap<>();

        // got through all found nuclei
        for(int i=0; i<this.nuclei.size(); i++){
            
            // get point at current index
            Point p = this.nuclei.get(i).getPoint(index);
            
            if(p != null){
                // calculate euclidean distance to p
                double euclid = Math.sqrt(Math.pow((p.x - x), 2.0) + Math.pow((p.y - y), 2.0));

                // check if point is in bounds defined through diameter
                if(euclid < radius){
                    
                    // insert into tree
                    if(nucs.size() >= k){
                        nucs.put(euclid, i);
                        nucs.pollLastEntry();
                    }else{
                        nucs.put(euclid, i);
                    }
                }   
            }
            
        }
        //LifeScience.LOG.info("Treemap: " + nucs.toString());
        return (Integer[]) nucs.values().toArray(new Integer[k]);
    }
    
    
    
    
    
    
    /**
     * Set new Pointroi according to cells
     */
    public void drawNuclei(){
        boolean labels = this.overlay.getDrawNames();
        for(int j=0; j<this.image.getStackSize(); j++){
            this.image.setSlice(j+1);
            int[] ox = new int[this.nuclei.size()];
            int[] oy = new int[this.nuclei.size()];
            int offx = this.image.getWidth();
            int offy = this.image.getHeight();
            for (int i=0; i<this.nuclei.size(); i++){
                Point p = new Point(this.image.getWidth(), this.image.getHeight());
                if(this.nuclei.get(i).getPoint(j)!= null){
                    p = this.nuclei.get(i).getPoint(j);
                }
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
            // TODO: unuse pointroi
            if(j==0){
                this.pointroi = points;
            }
        }
        this.showLabels(labels);
        this.image.setSlice(1);
        this.image.updateAndDraw();
    }
    
    /**
     * Set new ovalrois according to cells
     */
    public void drawCells(){
        boolean labels = this.overlay.getDrawNames();
        // size of oval
        int size = (int) (this.getNucleiDiameter()*1.5);
        
        for(int j=0; j<this.image.getStackSize(); j++){
            for (int i=0; i<this.cells.size(); i++){
                //Point p1 = this.cells.get(i).getNuclei()[0].getPoint(j);
                //Point p2 = this.cells.get(i).getNuclei()[1].getPoint(j);
                Point cell = this.cells.get(i).getCenter(j);
                
                if(cell !=null){
                    //EllipseRoi oval = new EllipseRoi((double) p1.x, (double) p1.y, (double) p2.x, (double) p2.y, 0.7);
                    OvalRoi oval = new OvalRoi(cell.x-(size/2), cell.y-(size/2), size, size);
                    oval.setName("" + i);
                    if(this.cells.get(i).isTetraploid()){
                        oval.setStrokeColor(Color.red);
                    }else{
                        oval.setStrokeColor(Color.cyan);
                    }
                    
                    // draw mitosis
                    if(this.cells.get(i).getTimeStartMitosis()> 1 && this.cells.get(i).getTimeStartMitosis()==(j+1) ){
                        oval.setStrokeColor(Color.GREEN);
                    }
                    
                    oval.setPosition(j+1);
                    this.overlay.add(oval);
                    
                    
                }
            }
        }
        this.showLabels(labels);
        this.image.updateAndDraw();
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
            if(this.nuclei.get(i).getPoint(slice)!= null && this.nuclei.get(i).getPoint(slice).equals(new Point(xcoord, ycoord))){
                LifeScience.LOG.info("remove..." + i);
                LifeScience.LOG.info(this.nuclei.remove(i).toString());
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
        this.resetOverlay();
        
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
        this.drawCells();
        this.setChanged();
        this.notifyObservers();
        this.image.updateAndDraw();
    }
    
    
    /**
     * set start and end of tracking
     */
    public void setStartStop(){
        for(int i=0; i<this.cells.size(); i++){
            Cell cell = this.cells.get(i);
            Nucleus nuc1 = cell.getNuclei()[0];
            int startpoint = 0;
            int endpoint = this.image.getStackSize();
            for(int j=0; j<this.image.getStackSize(); j++){
                if(nuc1.getPoint(j)!=null && startpoint == 0){
                    startpoint = j+1;
                }
                if(startpoint != 0 && nuc1.getPoint(j)==null && endpoint == this.image.getStackSize()){
                    endpoint = j+1;
                }
            }
            if(cell.isTetraploid()){
                int startpoint2 = this.image.getStackSize();
                int endpoint2 = this.image.getStackSize();
                Nucleus nuc2 = cell.getNuclei()[1];
                for(int j=0; j<this.image.getStackSize(); j++){
                    if(nuc2.getPoint(j)!=null && startpoint2 == 0){
                        startpoint2 = j+1;
                    } 
                    if(startpoint2 != 0 && nuc2.getPoint(j)==null && endpoint2 == this.image.getStackSize()){
                        endpoint2 = j+1;
                    }
                }
                startpoint = Math.min(startpoint, startpoint2);
                endpoint = Math.max(endpoint, endpoint2);
                
            }
            cell.setTimeStart(startpoint);
            cell.setTimeEnd (endpoint);
            
            
        }
        
    }
    
    /** 
     *  detect mitotic events from intensity differences
     */
    public void detectMitosis(){
         String teststring = "";

        // go through all detected nuclei
        for(int i=0; i<this.nuclei.size(); i++){
            
            double minintensity = 255;
            double maxintensity = 0;
            int maxintensityframe = 0;
            double meanintensity = 0;
            int count = 0;
            // get intensity for each time
            for(int j=0; j<this.image.getStackSize(); j++){
                this.image.setSlice(j+1);
                Point poi = this.nuclei.get(i).getPoint(j);
                if(poi != null){
                    int intens1 = this.image.getPixel(poi.x, poi.y)[0];
                    int intens2 = this.image.getPixel(poi.x-1, poi.y)[0];
                    int intens3 = this.image.getPixel(poi.x+1, poi.y)[0];
                    int intens4 = this.image.getPixel(poi.x, poi.y-1)[0];
                    int intens5 = this.image.getPixel(poi.x, poi.y+1)[0];
                    double mean = (intens1 + intens2 + intens3 + intens4 + intens5)/5;
                    if(mean < minintensity){ minintensity = mean; }
                    if(mean > maxintensity){ maxintensity = mean; maxintensityframe = j+1; }
                    meanintensity+=mean;
                    count++;                    
                }
                
            }
            double meanintens = (meanintensity*1.0)/(count*1.0);
            if(maxintensity>0 && minintensity < 255 && (maxintensity - meanintens)>100 && this.nuclei.get(i).getCell()!=null){
                this.nuclei.get(i).getCell().setTimeStartMitosis(maxintensityframe);
                //LifeScience.LOG.info("Cell " + this.cells.indexOf(this.nuclei.get(i).getCell()) + " Minintensity " + minintensity + " Maxintensity " + maxintensity + " Meanintensity " + meanintens);
            }
        }
    }
    
    
    
    /** 
     *  detect mitotic events from intensity differences
     */
    public void printIntensityCourse(int cell){
        String teststring = "";
        Nucleus nuc = this.cells.get(cell).getNuclei()[0];
        // go through all detected nuclei
       
            // get intensity for each time
            for(int j=0; j<this.image.getStackSize(); j++){
                this.image.setSlice(j+1);
                Point poi = nuc.getPoint(j);
                if(poi != null){
                    int intens1 = this.image.getPixel(poi.x, poi.y)[0];
                    int intens2 = this.image.getPixel(poi.x-1, poi.y)[0];
                    int intens3 = this.image.getPixel(poi.x+1, poi.y)[0];
                    int intens4 = this.image.getPixel(poi.x, poi.y-1)[0];
                    int intens5 = this.image.getPixel(poi.x, poi.y+1)[0];
                    double mean = (intens1 + intens2 + intens3 + intens4 + intens5)/5;
                    
                    teststring += (";" + (int) mean);
                    
                    
                }
                
            }
        LifeScience.LOG.info(teststring);
        
    }
    
    
    
    /**
     * Reset model for new image...
     */
    public void reset(){
        this.nuclei.clear();
        this.cells.clear();
        this.points.clear();
        this.results.reset();
        
        this.setChanged();
        this.notifyObservers();
    }
    
}
