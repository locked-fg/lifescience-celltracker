package de.lmu.dbs.lifescience.model;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Represents a nucleus of a cell
 * 
 * @author bea
 */
public class Nucleus {

    
    
    //---------------- Attributes
    
    /** Number of chromosome pairsin this nucleus */
    private int numberChromosomes;
    
    /** Course of this nucleus throughout frames, specified by list of ellipses */
    private Point[] points;
    
    /** The cell to which contains this nucleus */
    private Cell cell;
    
    /** Time nucleus was first detected */
    private int starttime;
    
    /** Time nucleus was last detected */
    private int stoptime;
    
    
    //---------------- Constructor
    /**
     * Create a new nucleus with an inital point
     * @param sequenceLength Length of image sequence
     * @param p Point
     */
    public Nucleus(int sequenceLength, Point p) {
        this.points = new Point[sequenceLength];
        this.points[0] = p;
    }
    
    /**
     * Create new nucleus with inital point on given coordinates
     * @param sequenceLength Length of image sequence
     * @param x X-coordinate
     * @param y Y-coordinate
     */
    public Nucleus(int sequenceLength, int x, int y) {
        this.points = new Point[sequenceLength];
        this.points[0] = new Point(x, y);
    }
    
    
    /**
     * Create new Nucleus with first point at specified index position
     * @param sequenceLength Length of image sequence
     * @param p Point
     * @param index Index at which first Point should be set
     */
    public Nucleus(int sequenceLength, Point p, int index){
        this.points = new Point[sequenceLength];
        this.points[index] = p;
        this.starttime = index;
    }
    
    
    
    
    //---------------- Methods
    /**
     * Get point at position index
     * @param index
     * @return Point
     */
    public Point getPoint(int index){
        return this.points[index];
    }
    
    /**
     * Return cell of this nucleus
     * @return Cell
     */
    public Cell getCell(){
        return this.cell;
    }
    
    /**
     * Set cell vor this Nucleus
     * This method is only needed by the Cell class to add a nucleus
     * @param cell 
     */
    protected void setCell(Cell cell){
        this.cell = cell;
    }
    
    /**
     * Set point at index
     * @param p Point
     * @param index 
     */
    public void setPoint(Point p, int index){
        this.points[index] = p;
    }
    
    /**
     * Return true if this Nucleus is assigned to a cell
     * @return true if assigned
     */
    public boolean isAssigned(){
        if(this.cell != null){
            return true;
        }
        return false;
    }
}

