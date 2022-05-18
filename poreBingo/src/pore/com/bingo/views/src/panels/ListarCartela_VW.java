/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pore.com.bingo.views.src.panels;

import javax.swing.JDialog;

import pore.com.bingo.controllers.ListarCartela_Controller;

/**
 *
 * @author Otacilio
 */
@SuppressWarnings("serial")
public class ListarCartela_VW extends javax.swing.JDialog  {

	protected ListarCartela_Controller controller;
	
    /**
     * Creates new form ListarCartela_VW
     */
    public ListarCartela_VW(java.awt.Frame parent, boolean modal) {
    	super(parent, modal);
        initComponents();
        
        controller = new ListarCartela_Controller(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelTopo = new javax.swing.JPanel();
        jLabelTopo = new javax.swing.JLabel();
        jButtonPesquisar = new javax.swing.JButton();
        jLabelNumero = new javax.swing.JLabel();
        jLabelPortador = new javax.swing.JLabel();
        jTextFieldNumero = new javax.swing.JTextField();
        jTextFieldPortador = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableListaCartelas = new javax.swing.JTable();
        jLabelTotal = new javax.swing.JLabel();
        jLabelValorTotal = new javax.swing.JLabel();
        jLabelTotalPortador = new javax.swing.JLabel();
        jLabelNumeroPortador = new javax.swing.JLabel();
        jButtonImprimir = new javax.swing.JButton();
        jMenuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemImportar = new javax.swing.JMenuItem();
        jMenuItemRemoverImportacao = new javax.swing.JMenuItem();
        jMenuItemSair = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("LISTAR CARTELAS");
        setResizable(false);

        jPanelTopo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabelTopo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelTopo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTopo.setText("LISTA DE CARTELAS");
        jLabelTopo.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(204, 204, 255), null));

        jButtonPesquisar.setText("Pesquisar");
        jButtonPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPesquisarActionPerformed(evt);
            }
        });

        jLabelNumero.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelNumero.setText("N�MERO:");

        jLabelPortador.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelPortador.setText("PORTADOR:");

        jTableListaCartelas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTableListaCartelas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "N� Cartela", "Portador", "Editar", "Remover"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableListaCartelas.setCellSelectionEnabled(true);
        jTableListaCartelas.setColumnSelectionAllowed(true);
        jTableListaCartelas.getTableHeader().setResizingAllowed(false);
        jTableListaCartelas.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTableListaCartelas);
        jTableListaCartelas.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        if (jTableListaCartelas.getColumnModel().getColumnCount() > 0) {
            jTableListaCartelas.getColumnModel().getColumn(0).setMinWidth(30);
            jTableListaCartelas.getColumnModel().getColumn(0).setMaxWidth(30);
            jTableListaCartelas.getColumnModel().getColumn(1).setMinWidth(80);
            jTableListaCartelas.getColumnModel().getColumn(1).setMaxWidth(80);
            jTableListaCartelas.getColumnModel().getColumn(2).setResizable(false);
            jTableListaCartelas.getColumnModel().getColumn(3).setMinWidth(70);
            jTableListaCartelas.getColumnModel().getColumn(3).setMaxWidth(70);
            jTableListaCartelas.getColumnModel().getColumn(4).setMinWidth(70);
            jTableListaCartelas.getColumnModel().getColumn(4).setMaxWidth(70);
        }

        jLabelTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTotal.setText("Total Listadas:");

        jLabelValorTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelValorTotal.setText("0");

        jLabelTotalPortador.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTotalPortador.setText("Total Portador:");

        jLabelNumeroPortador.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelNumeroPortador.setText("0");

        jButtonImprimir.setText("Imprimir");
        jButtonImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonImprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelTopoLayout = new javax.swing.GroupLayout(jPanelTopo);
        jPanelTopo.setLayout(jPanelTopoLayout);
        jPanelTopoLayout.setHorizontalGroup(
            jPanelTopoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
            .addGroup(jPanelTopoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTopoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelTopo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelTopoLayout.createSequentialGroup()
                        .addGroup(jPanelTopoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabelPortador, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                            .addComponent(jLabelNumero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelTopoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelTopoLayout.createSequentialGroup()
                                .addComponent(jTextFieldPortador, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonPesquisar)
                                .addGap(2, 2, 2)
                                .addComponent(jButtonImprimir)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTopoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(jLabelTotalPortador, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelNumeroPortador, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanelTopoLayout.setVerticalGroup(
            jPanelTopoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTopoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTopo, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelTopoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanelTopoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPortador, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPortador, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelTopoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelNumeroPortador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelTopoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabelTotalPortador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenuBar.setBackground(new java.awt.Color(204, 204, 255));
        jMenuBar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jMenu1.setText("Menu");

        jMenuItemImportar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItemImportar.setText("Importar Cartelas");
        jMenuItemImportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemImportarActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemImportar);

        jMenuItemRemoverImportacao.setText("Limpar Lista de Cartelas");
        jMenuItemRemoverImportacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRemoverImportacaoActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemRemoverImportacao);

        jMenuItemSair.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        jMenuItemSair.setText("Sair");
        jMenuItemSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSairActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemSair);

        jMenuBar.add(jMenu1);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelTopo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelTopo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemImportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemImportarActionPerformed
        controller.importarCartelas();
    }//GEN-LAST:event_jMenuItemImportarActionPerformed

    private void jButtonPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPesquisarActionPerformed
        controller.pesquisarCartelas();
    }//GEN-LAST:event_jButtonPesquisarActionPerformed

    private void jMenuItemSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSairActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMenuItemSairActionPerformed

    private void jMenuItemRemoverImportacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRemoverImportacaoActionPerformed
        controller.removerImportacao();
    }//GEN-LAST:event_jMenuItemRemoverImportacaoActionPerformed

    private void jButtonImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonImprimirActionPerformed
        controller.imprimirCartelas();
    }//GEN-LAST:event_jButtonImprimirActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
    	java.awt.EventQueue.invokeLater(new Runnable() {
    		public void run() {
    			JDialog dialog = new ListarCartela_VW(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jButtonImprimir;
    private javax.swing.JButton jButtonPesquisar;
    private javax.swing.JLabel jLabelNumero;
    public javax.swing.JLabel jLabelNumeroPortador;
    private javax.swing.JLabel jLabelPortador;
    private javax.swing.JLabel jLabelTopo;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JLabel jLabelTotalPortador;
    public javax.swing.JLabel jLabelValorTotal;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenuItem jMenuItemImportar;
    private javax.swing.JMenuItem jMenuItemRemoverImportacao;
    private javax.swing.JMenuItem jMenuItemSair;
    private javax.swing.JPanel jPanelTopo;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jTableListaCartelas;
    public javax.swing.JTextField jTextFieldNumero;
    public javax.swing.JTextField jTextFieldPortador;
    // End of variables declaration//GEN-END:variables
}
