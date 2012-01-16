/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.lmu.dbs.lifescience;

import de.lmu.dbs.lifescience.ui.LifeScienceController;
import de.lmu.dbs.lifescience.model.LifeScienceModel;
import de.lmu.dbs.lifescience.ui.LifeScienceView;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;



/**
 * Main class
 * @author bea
 */
public class LifeScience {
    
    
    /**
     * Logger for information about this application
     */
    public static final Logger LOG = Logger.getLogger("LifeScience Log");


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // configurate Logger
        LOG.addHandler(new ConsoleHandler());
        
        // Create Model
        LifeScienceModel model = new LifeScienceModel();
        
        // Create GUI
        LifeScienceView view = new LifeScienceView();
        
        // Create Controller
        LifeScienceController controller = new LifeScienceController(model, view);
        
        // Open GUI and display
        view.setVisible(true);
       
    }
}
