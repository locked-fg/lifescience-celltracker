package de.lmu.dbs.lifescience.model;

import java.awt.Point;
import java.util.ArrayList;


/**
 * Represents a Cell
 * 
 * @author beas
 */
public class Cell {
    
    
    
    
    //---------------- Attributes
    
    /** Frame number the Cell first occured */
    private int timeBirth;
    
    /** Frame number the Cell dieds */
    private int timeDeath;
    
    /** Frame number mitosis started */
    private int timeMitosisStart;
    
    /** Frame number mitosis was complete  */
    private int timeMitosisEnd;
    
    /** True if cell has 4 chromosome sets (2 nuclei)   */
    private boolean tetraploid;
    
    /** Parent cell of this cell */
    private Cell parent;
    
    /** Nuclei that belong to this cell */
    private Nucleus[] nuclei;
    
    /** Daughter cells of this cell (coming from mitosis) */
    private ArrayList<Cell> offspring;
    
    /** Comment or note about this cell */
    private String comment;
    
    
    
    //---------------- Constructor
    
    /**
     * Creates a new cell with one nucleus
     * @param nucleus Array of nuclei
     */
    public Cell(Nucleus nucleus){
        this.nuclei = new Nucleus[2];
        nucleus.setCell(this);
        this.nuclei[0] = nucleus;
        this.tetraploid = false;
    }
    
    
    
    //---------------- Methods
    
    /**
     * Adds a nucleus to the cell
     * @param nucleus 
     * @return true if adding of nucleus was successful, otherwise false
     */
    public boolean addNucleus(Nucleus nucleus){
        if(!this.tetraploid){
            nucleus.setCell(this);
            this.nuclei[1] = nucleus;
            this.tetraploid = true;
            return true;
        }
        return false; 
    }
    
    /*
     * Remove second nucleus from cell and set cell as non tetraploid
     */
    public void removeNucleus(){
        this.nuclei[1].setCell(null);
        this.nuclei[1]=null;
        this.tetraploid = false;
    }
    
    /**
     * Get nuclei that belong to this cell
     * @return array of nuclei
     */
    public Nucleus[] getNuclei(){
        return this.nuclei;
    }
    
    /**
     * Get comment about this cell
     * @return String comment
     */
    public String getComment(){
        return this.comment;
    }
    
    
    /**
     * Set comment or note about this cell
     * @param comment String
     */
    public void setComment(String comment){
        this.comment = comment;
    }
    
    
    /**
     * Return frame at which mitosis started
     * @return frame number
     */
    public int getTimeEndMitosis(){
        return this.timeMitosisEnd+1;
    }
    
    /**
     * Return frame at which mitosis started
     * @return frame number
     */
    public int getTimeStartMitosis(){
        return this.timeMitosisStart+1;
    }
    
    /**
     * Get the frame index at which cell died
     * @return timeDeath
     */
    public int getTimeDeath(){
        return this.timeDeath+1;
    }
    
    /**
     * Determine wether cell is tetraploid (2 nuclei, 4 chromosome sets)
     * @return true if cell is tetraploid
     */
    public boolean isTetraploid(){
        return this.tetraploid;
    }
    
    /**
     * Calculates and returns the center of the cell at the specified frame position
     * @param index Frame indexs
     * @return Point Center or null if no nuclei set for specified frame
     */
    public Point getCenter(int index){
        Point p1 = this.nuclei[0].getPoint(index);
        if(this.nuclei[1]==null){
            return p1;
        }
        Point p2 = this.nuclei[1].getPoint(index);
                
        if(p1!=null && p2 !=null){
            int newx = p2.x + ((p1.x - p2.x) /2);
            int newy = p2.y + ((p1.y - p2.y) /2);
            
            return new Point(newx, newy);
        }else if(p1 != null){
            return p1;
        }else if(p2 != null){
            return p2;
        }
      
        return null;
    }
    
    
     /**
     * Calculates and returns the top left corner of the cell's bounding box at the specified frame position
     * @param index Frame indexs
     * @return Point Top left or null if no nuclei set for specified frame
     */
    public Point getTopLeftCorner(int index){
        Point p1 = this.nuclei[0].getPoint(index);
        Point p2 = this.nuclei[1].getPoint(index);
                
        if(p1!=null && p2 !=null){
            int newx = Math.min(p1.x, p2.x);
            int newy = Math.min(p1.y, p2.y);
            
            return new Point(newx, newy);
        }
        return null;
    }
    
    
 
    
    
}
