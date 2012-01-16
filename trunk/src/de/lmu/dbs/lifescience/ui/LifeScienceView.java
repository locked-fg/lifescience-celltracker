
/*
 * LifeScienceGUI.java
 * GUI of LifeScience CellTracker
 * 
 * Created on 18.11.2011, 23:19:40
 */
package de.lmu.dbs.lifescience.ui;

import de.lmu.dbs.lifescience.model.LifeScienceModel;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.util.Observable;
import java.util.Observer;


/**
 * This Class opens a new graphical user interface and provides access to all features of this LifeScience application.
 * 
 * @author bea
 */
public class LifeScienceView extends javax.swing.JFrame implements Observer, ActionListener {

    /** Creates new form LifeScienceGUI 
     * 
     * @param LifeScienceController controller to manage user interactions
     * 
     */
    public LifeScienceView() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButtonImport = new javax.swing.JButton();
        jButtonDetection = new javax.swing.JButton();
        jButtonExport = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanelSequence = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabelSequenceFrame = new javax.swing.JLabel();
        jButtonSequencePrev = new javax.swing.JButton();
        jButtonSequenceNext = new javax.swing.JButton();
        jLabelSequenceInfo = new javax.swing.JLabel();
        jToggleButtonSequenceWindow = new javax.swing.JToggleButton();
        jToggleButtonSequenceWindow.setSelected(true);
        jPanelDetection = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jToggleButtonDetectionLabels = new javax.swing.JToggleButton();
        jToggleButtonDetectionLabels.setSelected(true);
        jLabelDetectionInfo = new javax.swing.JLabel();
        jButtonDetectionTable = new javax.swing.JButton();
        jToggleButtonDetectionEdit = new javax.swing.JToggleButton();
        jToggleButtonDetectionLabels.setSelected(true);
        jPanelExport = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabelExportInfo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LifeScience");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/de/lmu/dbs/lifescience/resources/lifescience-logo.png")).getImage());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), "Steps", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 12), java.awt.Color.lightGray)); // NOI18N
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.setFont(new java.awt.Font("Verdana", 0, 11));
        jPanel1.setPreferredSize(new java.awt.Dimension(160, (int) GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight()-80));

        jButtonImport.setFont(new java.awt.Font("Tahoma", 1, 11));
        jButtonImport.setForeground(new java.awt.Color(33, 90, 167));
        jButtonImport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/lmu/dbs/lifescience/resources/lifescience-icon-import.png"))); // NOI18N
        jButtonImport.setToolTipText("Opens a file chooser to select TIF Sequence");
        jButtonImport.setLabel("Import TIF");

        jButtonDetection.setForeground(new java.awt.Color(33, 90, 167));
        jButtonDetection.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/lmu/dbs/lifescience/resources/lifescience-icon-detect.png"))); // NOI18N
        jButtonDetection.setToolTipText("Detect nuclei in image...");
        jButtonDetection.setEnabled(false);
        jButtonDetection.setLabel("Detect Cells");
        jButtonDetection.setMaximumSize(new java.awt.Dimension(83, 23));
        jButtonDetection.setMinimumSize(new java.awt.Dimension(83, 23));

        jButtonExport.setFont(new java.awt.Font("Tahoma", 1, 11));
        jButtonExport.setForeground(new java.awt.Color(33, 90, 167));
        jButtonExport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/lmu/dbs/lifescience/resources/lifescience-icon-export.png"))); // NOI18N
        jButtonExport.setToolTipText("Export detected cells as csv...");
        jButtonExport.setEnabled(false);
        jButtonExport.setLabel("Export CSV");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/lmu/dbs/lifescience/resources/lifescience-icon-spacer.png"))); // NOI18N

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/lmu/dbs/lifescience/resources/lifescience-icon-spacer.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonImport, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                    .addComponent(jButtonDetection, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                    .addComponent(jButtonExport, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jButtonImport, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonDetection, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonExport, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(516, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), "Info", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 12), java.awt.Color.lightGray)); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(200, (int) GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight()-80));

        jLabel10.setForeground(new java.awt.Color(153, 153, 153));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("Sequence");
        jLabel10.setToolTipText("");

        jLabelSequenceFrame.setForeground(new java.awt.Color(89, 97, 107));
        jLabelSequenceFrame.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelSequenceFrame.setToolTipText("");

        jButtonSequencePrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/lmu/dbs/lifescience/resources/lifescience-icon-prev.png"))); // NOI18N
        jButtonSequencePrev.setToolTipText("Go to previous frame of the sequence.");
        jButtonSequencePrev.setActionCommand("Previous Frame");
        jButtonSequencePrev.setEnabled(false);

        jButtonSequenceNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/lmu/dbs/lifescience/resources/lifescience-icon-next.png"))); // NOI18N
        jButtonSequenceNext.setToolTipText("Go to next frame of the sequence.");
        jButtonSequenceNext.setActionCommand("Next Frame");
        jButtonSequenceNext.setEnabled(false);

        jLabelSequenceInfo.setForeground(new java.awt.Color(89, 97, 107));
        jLabelSequenceInfo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelSequenceInfo.setToolTipText("");
        jLabelSequenceInfo.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jToggleButtonSequenceWindow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/lmu/dbs/lifescience/resources/lifescience-icon-window.gif"))); // NOI18N
        jToggleButtonSequenceWindow.setToolTipText("Show image window");
        jToggleButtonSequenceWindow.setActionCommand("Show Image");
        jToggleButtonSequenceWindow.addActionListener(this);

        javax.swing.GroupLayout jPanelSequenceLayout = new javax.swing.GroupLayout(jPanelSequence);
        jPanelSequence.setLayout(jPanelSequenceLayout);
        jPanelSequenceLayout.setHorizontalGroup(
            jPanelSequenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSequenceLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSequenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSequenceInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addGroup(jPanelSequenceLayout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                        .addGap(56, 56, 56)
                        .addComponent(jToggleButtonSequenceWindow, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelSequenceLayout.createSequentialGroup()
                        .addComponent(jLabelSequenceFrame, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonSequencePrev, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSequenceNext, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanelSequenceLayout.setVerticalGroup(
            jPanelSequenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSequenceLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSequenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelSequenceLayout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabelSequenceFrame, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelSequenceLayout.createSequentialGroup()
                        .addComponent(jToggleButtonSequenceWindow)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelSequenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonSequencePrev)
                            .addComponent(jButtonSequenceNext))))
                .addGap(11, 11, 11)
                .addComponent(jLabelSequenceInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel11.setForeground(new java.awt.Color(153, 153, 153));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("Detection");
        jLabel11.setToolTipText("");

        jToggleButtonDetectionLabels.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/lmu/dbs/lifescience/resources/lifescience-icon-label.gif"))); // NOI18N
        jToggleButtonDetectionLabels.setToolTipText("Show labels of nuclei.");
        jToggleButtonDetectionLabels.setActionCommand("Show Labels");
        jToggleButtonDetectionLabels.addActionListener(this);

        jLabelDetectionInfo.setForeground(new java.awt.Color(89, 97, 107));
        jLabelDetectionInfo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelDetectionInfo.setToolTipText("");
        jLabelDetectionInfo.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jButtonDetectionTable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/lmu/dbs/lifescience/resources/lifescience-icon-export.png"))); // NOI18N
        jButtonDetectionTable.setToolTipText("Show all detected nuclei in a table");
        jButtonDetectionTable.setActionCommand("Show Table");

        jToggleButtonDetectionEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/lmu/dbs/lifescience/resources/lifescience-icon-edit.png"))); // NOI18N
        jToggleButtonDetectionEdit.setToolTipText("Edit detected nuclei.");
        jToggleButtonDetectionEdit.setActionCommand("Edit Nuclei");
        jToggleButtonDetectionEdit.addActionListener(this);

        javax.swing.GroupLayout jPanelDetectionLayout = new javax.swing.GroupLayout(jPanelDetection);
        jPanelDetection.setLayout(jPanelDetectionLayout);
        jPanelDetectionLayout.setHorizontalGroup(
            jPanelDetectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDetectionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDetectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDetectionLayout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76)
                        .addComponent(jButtonDetectionTable, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDetectionLayout.createSequentialGroup()
                        .addComponent(jLabelDetectionInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jToggleButtonDetectionLabels, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jToggleButtonDetectionEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanelDetectionLayout.setVerticalGroup(
            jPanelDetectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDetectionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDetectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonDetectionTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDetectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToggleButtonDetectionLabels)
                    .addComponent(jToggleButtonDetectionEdit)
                    .addGroup(jPanelDetectionLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabelDetectionInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(84, Short.MAX_VALUE))
        );

        jLabel12.setForeground(new java.awt.Color(153, 153, 153));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("Export");
        jLabel12.setToolTipText("");

        jLabelExportInfo.setForeground(new java.awt.Color(89, 97, 107));
        jLabelExportInfo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelExportInfo.setToolTipText("");
        jLabelExportInfo.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanelExportLayout = new javax.swing.GroupLayout(jPanelExport);
        jPanelExport.setLayout(jPanelExportLayout);
        jPanelExportLayout.setHorizontalGroup(
            jPanelExportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelExportLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelExportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelExportLayout.createSequentialGroup()
                        .addComponent(jLabelExportInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanelExportLayout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                        .addGap(92, 92, 92))))
        );
        jPanelExportLayout.setVerticalGroup(
            jPanelExportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelExportLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelExportInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelSequence, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanelDetection, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanelExport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelSequence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelDetection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelExport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(227, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 719, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 719, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2.getAccessibleContext().setAccessibleDescription("");

        pack();
    }

    // Code for dispatching events from components to event handlers.

    public void actionPerformed(java.awt.event.ActionEvent evt) {
        if (evt.getSource() == jToggleButtonSequenceWindow) {
            LifeScienceView.this.jToggleButtonSequenceWindowActionPerformed(evt);
        }
        else if (evt.getSource() == jToggleButtonDetectionLabels) {
            LifeScienceView.this.jToggleButtonDetectionLabelsActionPerformed(evt);
        }
        else if (evt.getSource() == jToggleButtonDetectionEdit) {
            LifeScienceView.this.jToggleButtonDetectionEditActionPerformed(evt);
        }
    }// </editor-fold>//GEN-END:initComponents

    private void jToggleButtonDetectionLabelsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonDetectionLabelsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButtonDetectionLabelsActionPerformed

    private void jToggleButtonDetectionEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonDetectionEditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButtonDetectionEditActionPerformed

    private void jToggleButtonSequenceWindowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonSequenceWindowActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButtonSequenceWindowActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LifeScienceView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        

    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonDetection;
    private javax.swing.JButton jButtonDetectionTable;
    private javax.swing.JButton jButtonExport;
    private javax.swing.JButton jButtonImport;
    private javax.swing.JButton jButtonSequenceNext;
    private javax.swing.JButton jButtonSequencePrev;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelDetectionInfo;
    private javax.swing.JLabel jLabelExportInfo;
    private javax.swing.JLabel jLabelSequenceFrame;
    private javax.swing.JLabel jLabelSequenceInfo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelDetection;
    private javax.swing.JPanel jPanelExport;
    private javax.swing.JPanel jPanelSequence;
    private javax.swing.JToggleButton jToggleButtonDetectionEdit;
    private javax.swing.JToggleButton jToggleButtonDetectionLabels;
    private javax.swing.JToggleButton jToggleButtonSequenceWindow;
    // End of variables declaration//GEN-END:variables

    
    
    /**
     * Initialise Controller and add Actionlisteners to Buttons
     * @param controller 
     */
    public void initController(ActionListener controller){
        this.jButtonImport.addActionListener(controller);
        this.jButtonDetection.addActionListener(controller);
        this.jButtonExport.addActionListener(controller);
        this.jToggleButtonSequenceWindow.addActionListener(controller);
        this.jButtonSequenceNext.addActionListener(controller);
        this.jButtonSequencePrev.addActionListener(controller);
        this.jButtonDetectionTable.addActionListener(controller);
        this.jToggleButtonDetectionLabels.addActionListener(controller);
        this.jToggleButtonDetectionEdit.addActionListener(controller);
        this.jToggleButtonSequenceWindow.addActionListener(controller);
        this.addWindowListener((WindowListener) controller);  
        // Hide Panels
        this.jPanelSequence.setVisible(false);
        this.jPanelDetection.setVisible(false);
        this.jPanelExport.setVisible(false);
       
    }

    
   

    
    /**
     * Update all View components: Buttons, Info texts, etc.
     * @param o
     * @param arg 
     */
    @Override
    public void update(Observable o, Object arg) {
        
        // get model
        LifeScienceModel model = (LifeScienceModel) o;
        
        // set view like model status
        if(model.getStatus() == LifeScienceModel.Status.IMAGEREADY){
            // locate imgage window on right screen side and resize accordingly
            int windowwidth =(int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
            model.getImage().getWindow().setLocation(this.getWidth(), 0);
            model.getImage().getWindow().setBounds(this.getWidth(), 0, windowwidth-this.getWidth(), this.getHeight());
            model.getImage().getWindow().getCanvas().fitToWindow();
            // change logo and background of imagewindow
            model.getImage().getWindow().setIconImage(new javax.swing.ImageIcon(getClass().getResource("/de/lmu/dbs/lifescience/resources/lifescience-logo.png")).getImage());
            model.getImage().getWindow().setBackground(new Color(240, 240, 240));
            // activate panel
            this.jPanelSequence.setVisible(true);
            // update info
            this.jLabelSequenceInfo.setText(model.getImageInfoString());
            // update buttons
            this.jButtonImport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/lmu/dbs/lifescience/resources/lifescience-icon-check.png")));
            this.jButtonDetection.setEnabled(true);
            
        }else if(model.getStatus() == LifeScienceModel.Status.CELLSDETECTED){
            // set roi color
            model.getImage().getRoi().setStrokeColor(new Color(0, 166, 151));
            // update buttons
            this.jButtonDetection.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/lmu/dbs/lifescience/resources/lifescience-icon-check.png")));
            this.jButtonExport.setEnabled(true);
            // update info
            this.jLabelDetectionInfo.setText(model.getDetectionInfoString());
            // activate panel
            this.jPanelDetection.setVisible(true);
            
        }else if(model.getStatus() == LifeScienceModel.Status.EXPORTED){
            // update buttons
            this.jButtonExport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/lmu/dbs/lifescience/resources/lifescience-icon-check.png")));
        }
        // update window button
        this.jToggleButtonSequenceWindow.setSelected(model.getImage().getWindow().isShowing());
        // update next and prev button
        int slice = model.getImage().getSlice();
        int size = model.getImage().getStackSize();
        this.jButtonSequencePrev.setEnabled(true);
        this.jButtonSequenceNext.setEnabled(true);
        if(slice <= 1){
            this.jButtonSequencePrev.setEnabled(false);
        }
        if (slice >= size){
            this.jButtonSequenceNext.setEnabled(false);
        }
        // update frame info
        this.jLabelSequenceFrame.setText("Frame " + slice + " of " + size);        
       
        this.repaint();
    }
    
    
    
    
    
    
}
