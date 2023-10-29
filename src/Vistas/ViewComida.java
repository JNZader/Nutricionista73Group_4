/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import Conexion.ComidaDAO;
import Entidades.Comida;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ViewComida extends javax.swing.JPanel {

    private ComidaDAO ComiData;
    private DefaultTableModel mod;
    private Comida comidaActual;

    public ViewComida() {
        initComponents();
        mod = (DefaultTableModel) tablaComida.getModel();
        llenarTabla();
        jBmodifComida.setEnabled(false);
        checkActivos.setSelected(true);
    }

    public ViewComida(Comida comida) {
        this();
        this.comidaActual = comida;
        llenarDatos(comida);
        jBmodifComida.setEnabled(true);
        jBagregarComida.setEnabled(false);
    }

    private void llenarDatos(Comida comida) {

        jTnombreComida.setText(comida.getNombre());
        jTdetalleComida.setText(comida.getDetalle());
        jTcantCalorias.setText(comida.getCantCalorias() + "");
        jCestadoComida.setSelected(comida.isEstado());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTnombreComida = new javax.swing.JTextField();
        jTcantCalorias = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTdetalleComida = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jBagregarComida = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jCestadoComida = new javax.swing.JRadioButton();
        botonSalir = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaComida = new javax.swing.JTable();
        botonEliminar = new javax.swing.JButton();
        jBmodifComida = new javax.swing.JButton();
        checkInactivos = new javax.swing.JRadioButton();
        checkActivos = new javax.swing.JRadioButton();
        checkTodos = new javax.swing.JRadioButton();

        setBackground(new java.awt.Color(180, 220, 160));
        setMaximumSize(new java.awt.Dimension(840, 690));
        setMinimumSize(new java.awt.Dimension(840, 690));
        setPreferredSize(new java.awt.Dimension(840, 690));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("INGRESO DE COMIDAS");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel1.setText("Nombre Comida : ");

        jLabel3.setText("Cant. Calorias :");

        jTnombreComida.setToolTipText("Ingrese el nombre de la comida");
        jTnombreComida.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTnombreComidaKeyTyped(evt);
            }
        });

        jTcantCalorias.setToolTipText("Ingrese la Cantidad de Calorias");
        jTcantCalorias.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTcantCaloriasKeyTyped(evt);
            }
        });

        jLabel4.setText("Detalle de Comida :");

        jTdetalleComida.setToolTipText("Describa la comida");
        jTdetalleComida.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTdetalleComidaKeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel5.setText("LISTA DE COMIDAS");

        jBagregarComida.setBackground(new java.awt.Color(150, 200, 130));
        jBagregarComida.setText("Agregar Comida");
        jBagregarComida.setToolTipText("Inserta Una Comida");
        jBagregarComida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBagregarComidaActionPerformed(evt);
            }
        });

        jLabel7.setText("Estado :");

        botonSalir.setBackground(new java.awt.Color(150, 200, 130));
        botonSalir.setText("Salir");
        botonSalir.setToolTipText("Salir Al Principal");
        botonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalirActionPerformed(evt);
            }
        });

        tablaComida.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID COMIDA", "NOMBRE COMIDA", "DETALLE COMIDA", "CANT.CALORIAS", "ESTADO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tablaComida);
        if (tablaComida.getColumnModel().getColumnCount() > 0) {
            tablaComida.getColumnModel().getColumn(0).setResizable(false);
            tablaComida.getColumnModel().getColumn(1).setResizable(false);
            tablaComida.getColumnModel().getColumn(2).setResizable(false);
            tablaComida.getColumnModel().getColumn(3).setResizable(false);
            tablaComida.getColumnModel().getColumn(4).setResizable(false);
        }

        botonEliminar.setBackground(new java.awt.Color(150, 200, 130));
        botonEliminar.setText("Eliminar");
        botonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarActionPerformed(evt);
            }
        });

        jBmodifComida.setBackground(new java.awt.Color(150, 200, 130));
        jBmodifComida.setText("Modificar Comida");
        jBmodifComida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBmodifComidaActionPerformed(evt);
            }
        });

        buttonGroup1.add(checkInactivos);
        checkInactivos.setText("Inactivos");
        checkInactivos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkInactivosActionPerformed(evt);
            }
        });

        buttonGroup1.add(checkActivos);
        checkActivos.setText("Activos");
        checkActivos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkActivosActionPerformed(evt);
            }
        });

        buttonGroup1.add(checkTodos);
        checkTodos.setText("Todos");
        checkTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkTodosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel7))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTnombreComida, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jCestadoComida, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTcantCalorias, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTdetalleComida, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jBmodifComida)
                                        .addGap(18, 18, 18)
                                        .addComponent(jBagregarComida)))
                                .addGap(70, 70, 70))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(208, 208, 208)
                        .addComponent(jLabel5)
                        .addGap(0, 396, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(botonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(114, 114, 114)
                                .addComponent(checkInactivos)
                                .addGap(100, 100, 100)
                                .addComponent(checkActivos)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(checkTodos)
                                .addGap(18, 18, 18)
                                .addComponent(botonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2))))
                .addContainerGap())
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTnombreComida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addComponent(jTdetalleComida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTcantCalorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jCestadoComida, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(35, 35, 35)
                            .addComponent(jLabel7)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBagregarComida)
                            .addComponent(jBmodifComida))))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(checkActivos)
                        .addComponent(checkTodos)
                        .addComponent(checkInactivos)
                        .addComponent(botonEliminar))
                    .addComponent(botonSalir))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jBagregarComidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBagregarComidaActionPerformed
        ComiData = new ComidaDAO();
        try {
            if (jTnombreComida.getText().isEmpty()
                    || jTdetalleComida.getText().isEmpty()
                    || jTcantCalorias.getText().isEmpty()
                    || !jCestadoComida.isSelected()) {
                JOptionPane.showMessageDialog(this, "Ingrese todos los valores");
            } else {

                int cantc = Integer.parseInt(jTcantCalorias.getText());
                String nc = jTnombreComida.getText();
                String dc = jTdetalleComida.getText();
                boolean estc = jCestadoComida.isSelected();
                Comida com = ComiData.insertar(new Comida(cantc, nc, dc, estc));

                jTnombreComida.setText(null);
                jTdetalleComida.setText(null);
                jTcantCalorias.setText(null);
                jCestadoComida.setSelected(false);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace(System.out);
        }
        llenarTabla();
    }//GEN-LAST:event_jBagregarComidaActionPerformed

    public void limpiarTabla() {
        mod.setRowCount(0);
    }

    public void llenarTabla() {
        int estado = checkInactivos.isSelected() ? 0 : (checkActivos.isSelected() ? 1 : (checkTodos.isSelected() ? 3 : -1));

        ComidaDAO codao = new ComidaDAO();
        ArrayList<Comida> comidas = codao.listarComidas(estado);
        limpiarTabla();
        if (comidas != null) {
            for (Comida aux : comidas) {
                Object[] filas = new Object[5];
                filas[0] = aux.getIdComida();
                filas[1] = aux.getNombre();
                filas[2] = aux.getDetalle();
                filas[3] = aux.getCantCalorias();
                if (aux.isEstado()) {
                    filas[4] = "Activo";
                } else {
                    filas[4] = "Inactivo";
                }
                mod.addRow(filas);
            }
        }
    }

//    public void llenarTablaActivos() {
//
//        ComidaDAO codao = new ComidaDAO();
//        ArrayList<Comida> comidas = codao.listarComidas(1);
//        limpiarTabla();
//        if (comidas != null) {
//            for (Comida aux : comidas) {
//                Object[] filas = new Object[5];
//                filas[0] = aux.getIdComida();
//                filas[1] = aux.getNombre();
//                filas[2] = aux.getDetalle();
//                filas[3] = aux.getCantCalorias();
//                if (aux.isEstado()) {
//                    filas[4] = "Activo";
//                } else {
//                    filas[4] = "Inactivo";
//                }
//                mod.addRow(filas);
//            }
//        }
//    }
//
//    public void llenarTablaInactivos() {
//
//        ComidaDAO codao = new ComidaDAO();
//        ArrayList<Comida> comidas = codao.listarComidas(0);
//        limpiarTabla();
//        if (comidas != null) {
//            for (Comida aux : comidas) {
//                Object[] filas = new Object[5];
//                filas[0] = aux.getIdComida();
//                filas[1] = aux.getNombre();
//                filas[2] = aux.getDetalle();
//                filas[3] = aux.getCantCalorias();
//                if (aux.isEstado()) {
//                    filas[4] = "Activo";
//                } else {
//                    filas[4] = "Inactivo";
//                }
//                mod.addRow(filas);
//            }
//        }
//    }

    private void botonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalirActionPerformed
        Dashboardv2 db = new Dashboardv2();
        this.setVisible(false);
        db.setVisible(true);
    }//GEN-LAST:event_botonSalirActionPerformed

    private void jTnombreComidaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTnombreComidaKeyTyped
        char c = evt.getKeyChar();
        if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && (c < ' ' || c > ' ')) {
            evt.consume();
        }
    }//GEN-LAST:event_jTnombreComidaKeyTyped

    private void jTdetalleComidaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTdetalleComidaKeyTyped
        char c = evt.getKeyChar();
        if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && (c < ' ' || c > ' ')) {
            evt.consume();
        }
    }//GEN-LAST:event_jTdetalleComidaKeyTyped

    private void jTcantCaloriasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTcantCaloriasKeyTyped
        char c = evt.getKeyChar();
        if ((c < '0' || c > '9')) {
            evt.consume();
        }
        if (jTcantCalorias.getText().length() > 3) {
            evt.consume();
        }
    }//GEN-LAST:event_jTcantCaloriasKeyTyped

    private void botonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarActionPerformed
        int selectedRow = tablaComida.getSelectedRow();
        ComidaDAO comidaDAO = new ComidaDAO();
        if (selectedRow != -1) {
            int idcomida = (int) tablaComida.getValueAt(selectedRow, 0);
            int estado = "Activo".equals((String) tablaComida.getValueAt(selectedRow, 4)) ? 1 : 0;
            Comida comida = comidaDAO.buscar(idcomida, estado);
            if (comida != null) {
                comidaDAO.borrarTotal(comida);
            }
        }
        limpiarTabla();
        llenarTabla();
    }//GEN-LAST:event_botonEliminarActionPerformed

    private void jBmodifComidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBmodifComidaActionPerformed
        ComiData = new ComidaDAO();
        if (comidaActual != null) {
            try {
                if (jTnombreComida.getText().isEmpty()
                        || jTdetalleComida.getText().isEmpty()
                        || jTcantCalorias.getText().isEmpty()
                        || !jCestadoComida.isSelected()) {
                    JOptionPane.showMessageDialog(this, "Ingrese todos los valores");
                } else {
                    int cantc = Integer.parseInt(jTcantCalorias.getText());
                    String nc = jTnombreComida.getText();
                    String dc = jTdetalleComida.getText();
                    boolean estc = jCestadoComida.isSelected();

                    comidaActual.setNombre(nc);
                    comidaActual.setDetalle(dc);
                    comidaActual.setCantCalorias(cantc);
                    comidaActual.setEstado(estc);

                    ComiData.modificar(comidaActual);

                    jTnombreComida.setText(null);
                    jTdetalleComida.setText(null);
                    jTcantCalorias.setText(null);
                    jCestadoComida.setSelected(false);
                    jBmodifComida.setEnabled(false);
                    jBagregarComida.setEnabled(true);
                    llenarTabla();
                }
            } catch (NumberFormatException e) {
                e.printStackTrace(System.out);
            }
        }
    }//GEN-LAST:event_jBmodifComidaActionPerformed

    private void checkInactivosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkInactivosActionPerformed
        llenarTabla();
    }//GEN-LAST:event_checkInactivosActionPerformed

    private void checkActivosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkActivosActionPerformed
        llenarTabla();
    }//GEN-LAST:event_checkActivosActionPerformed

    private void checkTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkTodosActionPerformed
        llenarTabla();
    }//GEN-LAST:event_checkTodosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonEliminar;
    private javax.swing.JButton botonSalir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JRadioButton checkActivos;
    private javax.swing.JRadioButton checkInactivos;
    private javax.swing.JRadioButton checkTodos;
    private javax.swing.JButton jBagregarComida;
    private javax.swing.JButton jBmodifComida;
    private javax.swing.JRadioButton jCestadoComida;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTcantCalorias;
    private javax.swing.JTextField jTdetalleComida;
    private javax.swing.JTextField jTnombreComida;
    private javax.swing.JTable tablaComida;
    // End of variables declaration//GEN-END:variables
}
