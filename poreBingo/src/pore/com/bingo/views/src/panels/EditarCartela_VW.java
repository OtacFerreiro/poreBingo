/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pore.com.bingo.views.src.panels;

import javax.swing.JDialog;

import pore.com.bingo.controllers.EditarCartela_Controller;

/**
 *
 * @author Otacilio
 */
@SuppressWarnings("serial")
public class EditarCartela_VW extends javax.swing.JDialog  {
	
	public EditarCartela_Controller controller;

    /**
     * Creates new form RegistrarCartela_VW
     */
    public EditarCartela_VW(java.awt.Frame parent, boolean modal) {
    	super(parent, modal);
        initComponents();
        
        controller = new EditarCartela_Controller(this);
    }
    
    public EditarCartela_VW(javax.swing.JDialog parent, boolean modal) {
    	super(parent, modal);
        initComponents();
        
        controller = new EditarCartela_Controller(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabelNumero = new javax.swing.JLabel();
        jLabelPortador = new javax.swing.JLabel();
        jTextFieldNumero = new javax.swing.JTextField();
        jTextFieldPortador = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableNumeros = new javax.swing.JTable();
        jButtonConfirmar = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemSair = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("EDITAR CARTELA");
        setPreferredSize(new java.awt.Dimension(600, 600));
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setAlignmentX(1.5F);
        jPanel1.setAlignmentY(1.5F);
        jPanel1.setPreferredSize(new java.awt.Dimension(570, 512));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CARTELA");
        jLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(204, 204, 255), null));

        jLabelNumero.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelNumero.setText("N�MERO:");

        jLabelPortador.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelPortador.setText("PORTADOR:");

        jTextFieldNumero.setEnabled(false);

        jTableNumeros.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTableNumeros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "B", "I", "N", "G", "O"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableNumeros.getTableHeader().setResizingAllowed(false);
        jTableNumeros.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTableNumeros);
        if (jTableNumeros.getColumnModel().getColumnCount() > 0) {
            jTableNumeros.getColumnModel().getColumn(0).setResizable(false);
            jTableNumeros.getColumnModel().getColumn(1).setResizable(false);
            jTableNumeros.getColumnModel().getColumn(2).setResizable(false);
            jTableNumeros.getColumnModel().getColumn(3).setResizable(false);
            jTableNumeros.getColumnModel().getColumn(4).setResizable(false);
        }

        jButtonConfirmar.setText("Confirmar");
        jButtonConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabelPortador, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                            .addComponent(jLabelNumero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldPortador, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 26, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(205, 205, 205)
                .addComponent(jButtonConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPortador, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPortador, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenuBar1.setBackground(new java.awt.Color(204, 204, 255));
        jMenuBar1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jMenu1.setText("Menu");

        jMenuItemSair.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        jMenuItemSair.setText("Sair");
        jMenuItemSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSairActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemSair);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSairActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMenuItemSairActionPerformed

    private void jButtonConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfirmarActionPerformed
        controller.confirmar();
    }//GEN-LAST:event_jButtonConfirmarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	JDialog dialog = new EditarCartela_VW(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonConfirmar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelNumero;
    private javax.swing.JLabel jLabelPortador;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemSair;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jTableNumeros;
    public javax.swing.JTextField jTextFieldNumero;
    public javax.swing.JTextField jTextFieldPortador;
    // End of variables declaration//GEN-END:variables
}
