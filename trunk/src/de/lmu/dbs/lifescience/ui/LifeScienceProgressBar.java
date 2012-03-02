package de.lmu.dbs.lifescience.ui;

import ij.gui.ProgressBar;
import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author bea
 */
public class LifeScienceProgressBar extends ProgressBar implements Serializable {

    //---------------- Attributes
    //---------------- Constructor
    /**
     * Create new Progress Bar as Java Beans
     */
    public LifeScienceProgressBar() {
        super(410, 15);
    }
    //---------------- Methods
}
