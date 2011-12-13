/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.lmu.dbs.lifescience.ui;

import de.lmu.dbs.lifescience.model.LifeScienceModel;
import ij.IJ;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * The LifeScienceController controls all user interactions provided by the LifeScienceGUI.
 * All Events are forwarded to this controller and processed.
 * 
 * @author bea
 */
public class LifeScienceController implements ActionListener, MouseListener, WindowListener {
    
    
    //---------------- Attributes
    private static final Logger log = Logger.getLogger("LifeScience Log");
    
    /** Model representing the data structure */
    private LifeScienceModel model;
    
    /** View that for user interaction */
    private LifeScienceView view;
    
    /** ImageJ */
    private IJ imagej;
    
    
    //---------------- Controller
    public LifeScienceController(LifeScienceModel model, LifeScienceView view){
        this.model = model;
        this.view = view;
        
        // add Observers to model
        this.model.addObserver(view);
        
        // create ImageJ instance
        this.imagej = new IJ();
        
        // configurate Logger
        log.addHandler(new ConsoleHandler());
        
        // add Listeners to the view
        this.view.initController(this);
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Import TIF":
                // Open FileDialog...
                JFileChooser openDialog = new JFileChooser();
                openDialog.setDialogTitle("TIF Sequenz laden...");
                openDialog.setDialogType(JFileChooser.OPEN_DIALOG);
                openDialog.setFileFilter(new FileNameExtensionFilter("TIF Sequenz", "tif", "tiff"));
                int action = openDialog.showOpenDialog(view);
                if(action == JFileChooser.APPROVE_OPTION){
                    this.model.setImage(IJ.openImage(openDialog.getSelectedFile().getPath()));
                }
                break;
            case "Detect Cells":
                this.model.detectCells();
                break;
            case "Export CSV":
                // Open FileDialog...
                JFileChooser saveDialog = new JFileChooser();
                saveDialog.setDialogTitle("Export detected nuclei as CSV...");
                saveDialog.setDialogType(JFileChooser.SAVE_DIALOG);
                saveDialog.setFileFilter(new FileNameExtensionFilter("CSV Tabelle", "csv"));
                saveDialog.setSelectedFile(new File("table.csv"));
                int option = saveDialog.showSaveDialog(view);
                if(option == JFileChooser.APPROVE_OPTION){
                    try {
                        this.model.exportCSV(saveDialog.getSelectedFile().getPath().toString());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                break;
            case "Next Frame":
                {
                    JButton btn = (JButton) e.getSource();
                    this.model.nextFrame();
                    break;
                }
            case "Previous Frame":
                {
                    JButton btn = (JButton) e.getSource();
                    this.model.previousFrame();
                    break;
                }
        }
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void windowOpened(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void windowClosing(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void windowClosed(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void windowIconified(WindowEvent e) {
        if(this.model.getImage() != null){
            this.model.getImage().getWindow().setState(Frame.ICONIFIED);
        }
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        if(this.model.getImage() != null){
            this.model.getImage().getWindow().setState(Frame.NORMAL);
        }
    }

    @Override
    public void windowActivated(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    
    
}
