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
    
    
    //---------------- Methods
    
    public Point getPoint(int index){
        return this.points[index];
    }
    
    public Cell getCell(){
        return this.cell;
    }
    
    protected void setCell(Cell cell){
        this.cell = cell;
    }
    
    public boolean isAssigned(){
        if(this.cell != null){
            return true;
        }
        return false;
    }
}

