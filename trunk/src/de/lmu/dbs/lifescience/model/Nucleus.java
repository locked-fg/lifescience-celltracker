package de.lmu.dbs.lifescience.model;

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
    private ArrayList<Ellipse> lifecycle;
    
    
    //---------------- Constructor
    public Nucleus() {
        
    }
    
    
    
    //---------------- Methods
}
