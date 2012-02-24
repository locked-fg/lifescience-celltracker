/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.lmu.dbs.lifescience.ui;

import de.lmu.dbs.lifescience.LifeScience;
import de.lmu.dbs.lifescience.model.LifeScienceModel;
import de.lmu.dbs.lifescience.processing.CellDetector;
import de.lmu.dbs.lifescience.processing.CellTrackerFollow;
import de.lmu.dbs.lifescience.processing.CellTrackerRelate;
import de.lmu.dbs.lifescience.processing.ImageEnhancer;
import ij.IJ;
import ij.gui.ImageWindow;
import ij.gui.OvalRoi;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import javax.swing.JFileChooser;
import javax.swing.JToggleButton;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * The LifeScienceController controls all user interactions provided by the LifeScienceGUI.
 * All Events are forwarded to this controller and processed.
 * 
 * @author bea
 */
public class LifeScienceController implements ActionListener, MouseListener, WindowListener, AdjustmentListener {

  
  
    
    //---------------- Constants
    
    /** Edit Mode */
    public static enum Mode {
        EDIT, VIEW
    }
    
    
    //---------------- Attributes
    
    /** ImageJ */
    private final IJ imagej = new IJ();
    
    /** Model representing the data structure */
    private final LifeScienceModel model;
    
    /** View that for user interaction */
    private final LifeScienceView view;
    
    /** Cell Detector */
    private CellDetector detector;
    
    /** Modes */
    private Mode mode;
    
    
    //---------------- Controller
    public LifeScienceController(LifeScienceModel model, LifeScienceView view){
        this.model = model;
        this.view = view;
        
        // add Observers to model
        this.model.addObserver(view);
        
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
                    // add Window listener to dock windows
                    this.model.getImage().getWindow().removeWindowListener(this.model.getImage().getWindow().getWindowListeners()[0]);
                    this.model.getImage().getWindow().addWindowListener(this);
                    // add Listener to scrollbar
                    ij.gui.ScrollbarWithLabel scroll = (ij.gui.ScrollbarWithLabel) this.model.getImage().getWindow().getComponent(1);
                    scroll.addAdjustmentListener(this);
                    this.model.setStatus(LifeScienceModel.Status.IMAGEREADY);
                }
                break;
            case "Enhance Images":
                ImageEnhancer enhancer = new ImageEnhancer(model.getImage(), true);
                enhancer.run();
                this.model.setStatus(LifeScienceModel.Status.IMAGEENHANCED);
                break;
            case "Detect Cells":
                this.detector = new CellDetector(model.getImage(), this.model);
                detector.run();
                detector.groupNuclei();
                // change mouselistener of image window
                if(model.getImage().getCanvas().getMouseListeners().length > 0){
                    model.getImage().getCanvas().removeMouseListener(model.getImage().getCanvas().getMouseListeners()[0]);
                    model.getImage().getCanvas().addMouseListener(this);
                }
                this.model.drawNuclei();
                this.model.drawCells();
                this.model.setStatus(LifeScienceModel.Status.CELLSDETECTED);
                break;
            case "Track Cells":
                CellTrackerRelate tracker = new CellTrackerRelate(this.model.getImage(), this.model, this.detector, this.model.getNucleiDiameter()*4, 40, 4);
                tracker.run();
                
                this.model.drawNuclei();
                this.model.drawCells();
                
                this.model.setStatus(LifeScienceModel.Status.CELLSTRACKED);
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
                        this.model.setStatus(LifeScienceModel.Status.EXPORTED);
                    } catch (IOException ex) {
                        LifeScience.LOG.log(Level.WARNING, "CSV export failed: {0}", ex.getStackTrace().toString());
                    }
                }
                break;
            case "Open ImageJ":
                {
                    JToggleButton btn = (JToggleButton) e.getSource();
                    if(btn.isSelected()){
                        this.view.showImageJ(true);
                        btn.setSelected(true);
                    }else{
                        this.view.showImageJ(false);
                        btn.setSelected(false);
                    }
                    break;
                }
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
        LifeScience.LOG.log(Level.FINEST, "Mouse pressed - no action implementet yet.");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        LifeScience.LOG.log(Level.FINEST, "Mouse released - no action implementet yet.");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        LifeScience.LOG.log(Level.FINEST, "Mouse entered - no action implementet yet.");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        LifeScience.LOG.log(Level.FINEST, "Mouse exited - no action implementet yet.");
    }

    @Override
    public void windowOpened(WindowEvent e) {
        LifeScience.LOG.log(Level.FINEST, "Window opened - no action implementet yet.");
    }

    @Override
    public void windowClosing(WindowEvent e) {
        // Group and link windows
        if(this.model.getImage()!= null && e.getSource().equals(this.model.getImage().getWindow())){
            this.model.getImage().getWindow().setVisible(false);
            this.view.update(this.model, e);
        }else if("ij.ImageJ".equals(e.getSource().getClass().getName())){
            this.view.showImageJ(false);
            this.view.update(this.model, e);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
        LifeScience.LOG.log(Level.FINEST, "Window closed - no action implementet yet.");
    }

    @Override
    public void windowIconified(WindowEvent e) {
        // Group and link windows
        if(this.model.getImage()!= null && e.getSource().equals(this.view)){
            this.model.getImage().getWindow().setState(Frame.ICONIFIED);
            this.view.getImageJ().setState(Frame.ICONIFIED);
        }
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // Group and link windows
        if(this.model.getImage()!= null && e.getSource().equals(this.view)){
            this.model.getImage().getWindow().setState(Frame.NORMAL);
            this.view.getImageJ().setState(Frame.NORMAL);
        }
    }

    @Override
    public void windowActivated(WindowEvent e) {
        LifeScience.LOG.log(Level.FINEST, "Window activated - no action implementet yet.");
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        LifeScience.LOG.log(Level.FINEST, "Window deactivated - no action implementet yet.");
    }
    
     @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        this.view.update(model, e);
    }

    

    
    
}
