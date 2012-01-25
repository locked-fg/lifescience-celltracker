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
    
    /** True if cell has 4 chromosome sets (2 nuclei)   */
    private boolean tetraploid;
    
    /** Parent cell of this cell */
    private Cell parent;
    
    /** Nuclei that belong to this cell */
    private Nucleus[] nuclei;
    
    /** Daughter cells of this cell (coming from mitosis) */
    private ArrayList<Cell> offspring;
    
    
    
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
    
    /**
     * Get nuclei that belong to this cell
     * @return array of nuclei
     */
    public Nucleus[] getNuclei(){
        return this.nuclei;
    }
    
    
    /**
     * Determine wether cell is tetraploid (2 nuclei, 4 chromosome sets)
     * @return true if cell is tetraploid
     */
    public boolean isTetraploid(){
        return this.tetraploid;
    }
    
    
    
}
