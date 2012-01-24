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
    
    /** Nuclei of this cell (normally just one, seldom two)  */
    private Nucleus firstNucleus;
    private Nucleus secondNucleus;
    
    /** True if cell has 4 chromosome sets (2 nuclei)   */
    private boolean tetraploid;
    
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
        this.firstNucleus = nucleus;
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
            this.secondNucleus = nucleus;
            this.tetraploid = true;
            return true;
        }
        return false; 
    }
    
    /**
     * Determine wether cell is tetraploid (2 nuclei, 4 chromosome sets)
     * @return true if cell is tetraploid
     */
    public boolean isTetraploid(){
        return this.tetraploid;
    }
    
    /**
     * Return the first nucleus of this cell
     * @return Nucleus
     */
    public Nucleus getFirstNucleus(){
        return this.firstNucleus;
    }
    
    /**
     * Return second nucleus if cell is tetraploid
     * @return Nucleus or null if cell is not tetraploid
     */
    public Nucleus getSecondNucleus(){
        if(this.tetraploid){
            return this.secondNucleus;
        }
        return null;
    }
    
}
