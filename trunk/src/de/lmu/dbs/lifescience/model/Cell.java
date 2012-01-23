package de.lmu.dbs.lifescience.model;

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
    
    /** List of nuclei of this cell (normally just one, seldom two)  */
    private ArrayList<Nucleus> nuclei;
    
    /** Parent cell of this cell */
    private Cell parent;
    
    /** Daughter cells of this cell (coming from mitosis) */
    private ArrayList<Cell> offspring;
    
    
    
    //---------------- Constructor
    
    /**
     * Creates a new cell with one nucleus
     * @param nucleus Array of nuclei
     */
    public Cell(Nucleus nucleus){
        this.addNucleus(nucleus);
    }
    
    
    
    //---------------- Methods
    
    /**
     * Adds a nucleus to the cell
     * @param nucleus 
     */
    public void addNucleus(Nucleus nucleus){
        this.nuclei.add(nucleus);
    }
    
}
