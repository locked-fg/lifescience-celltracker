/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.lmu.dbs.lifescience;

import de.lmu.dbs.lifescience.ui.LifeScienceController;
import de.lmu.dbs.lifescience.model.LifeScienceModel;
import de.lmu.dbs.lifescience.ui.LifeScienceView;



/**
 * Main class
 * @author bea
 */
public class LifeScience {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
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
