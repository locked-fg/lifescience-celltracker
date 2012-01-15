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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JToggleButton;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * The LifeScienceController controls all user interactions provided by the LifeScienceGUI.
 * All Events are forwarded to this controller and processed.
 * 
 * @author bea
 */
public class LifeScienceController implements ActionListener, MouseListener, WindowListener {
    
    
    //---------------- Constants
    
    /** Edit Mode */
    public static enum Mode {
        EDIT, VIEW
    }
    
    
    //---------------- Attributes
    private static final Logger LOG = Logger.getLogger("LifeScience Log");
    
    /** ImageJ */
    private final IJ imagej = new IJ();
    
    /** Model representing the data structure */
    private final LifeScienceModel model;
    
    /** View that for user interaction */
    private final LifeScienceView view;
    
    /** Modes */
    private Mode mode;
    
    
    //---------------- Controller
    public LifeScienceController(LifeScienceModel model, LifeScienceView view){
        this.model = model;
        this.view = view;
        
        // add Observers to model
        this.model.addObserver(view);
        
        // configurate Logger
        LOG.addHandler(new ConsoleHandler());
        
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
                    this.model.getImage().getWindow().addWindowListener(this);
                }
                break;
            case "Detect Cells":
                this.model.detectCells();
                // change mouselistener of image window
                if(model.getImage().getCanvas().getMouseListeners().length > 0){
                    model.getImage().getCanvas().removeMouseListener(model.getImage().getCanvas().getMouseListeners()[0]);
                    model.getImage().getCanvas().addMouseListener(this);
                }
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
            case "Show Image":
                {
                    JToggleButton btn = (JToggleButton) e.getSource();
                    if(!btn.isSelected() && this.model.getImage() != null && this.model.getImage().getWindow() != null){
                        this.model.getImage().getWindow().setVisible(false);
                    }else{
                        this.model.getImage().getWindow().setVisible(true);
                    }
                    break;
                }
            case "Next Frame":
                {
                    this.model.nextFrame();
                    break;
                }
            case "Previous Frame":
                {
                    this.model.previousFrame();
                    break;
                }
            case "Show Labels":
                {
                    JToggleButton btn = (JToggleButton) e.getSource();
                    this.model.showLabels(btn.isSelected());
                    break;
                }
            case "Show Table":
                {
                    this.model.showTable(true);
                    break;
                }
            case "Edit Nuclei":
                {
                    JToggleButton btn = (JToggleButton) e.getSource();
                    if(btn.isSelected()){
                        this.mode = LifeScienceController.Mode.EDIT;
                    }else{
                        this.mode = LifeScienceController.Mode.VIEW;
                    }
                    break;
                }
        }
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // if edit mode, edit nuclei
        if(this.mode == LifeScienceController.Mode.EDIT){
            model.editNuclei(e.getPoint());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        LOG.log(Level.FINEST, "Mouse pressed - no action implementet yet.");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        LOG.log(Level.FINEST, "Mouse released - no action implementet yet.");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        LOG.log(Level.FINEST, "Mouse entered - no action implementet yet.");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        LOG.log(Level.FINEST, "Mouse exited - no action implementet yet.");
    }

    @Override
    public void windowOpened(WindowEvent e) {
        LOG.log(Level.FINEST, "Window obened - no action implementet yet.");
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if(this.model.getImage()!= null && e.getSource().equals(this.model.getImage().getWindow())){
            this.view.update(this.model, e);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
        LOG.log(Level.FINEST, "Window closed - no action implementet yet.");
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
        LOG.log(Level.FINEST, "Window activated - no action implementet yet.");
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        LOG.log(Level.FINEST, "Window deactivated - no action implementet yet.");
    }

    
    
}
