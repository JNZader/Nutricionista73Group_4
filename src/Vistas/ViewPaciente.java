package Vistas;

import Conexion.PacienteDAO;
import Entidades.*;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

public class ViewPaciente extends javax.swing.JPanel {

    private DocumentFilter filtroMix, filtroLetras;
    private NumericRangeFilter6 fp;
    PacienteDAO pd = new PacienteDAO();
//    private ImageIcon imagen;
//    private Icon icono;

    public ViewPaciente() {
        initComponents();
        jBModificar.setEnabled(false);
        jBEliminar.setEnabled(false);
        ViewBuscar VB = new ViewBuscar();
        filtroMix = new FiltraEntrada6(FiltraEntrada6.NUM_LETRAS);
        filtroLetras = new FiltraEntrada6(FiltraEntrada6.SOLO_LETRAS);
        ((AbstractDocument) jTDomicilio.getDocument()).setDocumentFilter(filtroMix);
        ((AbstractDocument) jTNombre.getDocument()).setDocumentFilter(filtroLetras);
        fp = new NumericRangeFilter6();
        ((AbstractDocument) jTPesoActual.getDocument()).setDocumentFilter(fp);
        jTID.setEditable(false);
    }

    public ViewPaciente(Paciente paciente) {
        this();
        llenarDatos(paciente);
        jBModificar.setEnabled(true);
        jBGuardar.setEnabled(false);
        jTID.setEditable(false);
    }

    public void llenarDatos(Paciente paciente) {
        jTNombre.setText(paciente.getNombre());
        jTDni.setText(paciente.getDni() + "");
        jTDomicilio.setText(paciente.getDomicilio());
        jTID.setText(paciente.getIdPaciente() + "");
        jTPesoActual.setText(paciente.getPesoActual() + "");
        jTTelefono.setText(paciente.getTelefono() + "");
        if (paciente.isEstado()) {
            jChEstado.setSelected(true);
        } else {
            jChEstado.setSelected(false);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTNombre = new javax.swing.JTextField();
        jTDni = new javax.swing.JTextField();
        jTDomicilio = new javax.swing.JTextField();
        jTTelefono = new javax.swing.JTextField();
        jTPesoActual = new javax.swing.JTextField();
        jChEstado = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        jTID = new javax.swing.JTextField();
        jBBuscar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jBGuardar = new javax.swing.JButton();
        jBEliminar = new javax.swing.JButton();
        jBModificar = new javax.swing.JButton();
        jBSalir = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jBLimpiar = new javax.swing.JButton();

        jLabel2.setText("jLabel2");

        setBackground(new java.awt.Color(180, 220, 160));
        setMaximumSize(new java.awt.Dimension(840, 690));
        setMinimumSize(new java.awt.Dimension(840, 690));

        jPanel1.setBackground(new java.awt.Color(160, 200, 140));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos ", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 18))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setText("Domicilio");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setText("Nombre Completo");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setText("DNI");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setText("Telefono");

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setText("Estado");

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel7.setText("Peso Actual");

        jTNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTNombreKeyTyped(evt);
            }
        });

        jTDni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTDniKeyTyped(evt);
            }
        });

        jTDomicilio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTDomicilioKeyTyped(evt);
            }
        });

        jTTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTTelefonoKeyTyped(evt);
            }
        });

        jTPesoActual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTPesoActualKeyTyped(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel9.setText("ID");

        jBBuscar.setBackground(new java.awt.Color(150, 200, 130));
        jBBuscar.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jBBuscar.setText("Buscar");
        jBBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBuscarActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel10.setText("Buscar paciente por DNI");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel9))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTID, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTPesoActual, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 170, Short.MAX_VALUE)
                        .addComponent(jBBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jChEstado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTDni, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(jTDomicilio, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTNombre, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTTelefono, javax.swing.GroupLayout.Alignment.LEADING))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTPesoActual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jChEstado))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jTID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(11, 11, 11))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jBGuardar.setBackground(new java.awt.Color(150, 200, 130));
        jBGuardar.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jBGuardar.setText("Guardar");
        jBGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGuardarActionPerformed(evt);
            }
        });

        jBEliminar.setBackground(new java.awt.Color(150, 200, 130));
        jBEliminar.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jBEliminar.setText("Eliminar");
        jBEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBEliminarActionPerformed(evt);
            }
        });

        jBModificar.setBackground(new java.awt.Color(150, 200, 130));
        jBModificar.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jBModificar.setText("Modificar");
        jBModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBModificarActionPerformed(evt);
            }
        });

        jBSalir.setBackground(new java.awt.Color(150, 200, 130));
        jBSalir.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jBSalir.setText("Salir");
        jBSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSalirActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel8.setText("Gestion Paciente");

        jBLimpiar.setBackground(new java.awt.Color(150, 200, 130));
        jBLimpiar.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jBLimpiar.setText("Limpiar");
        jBLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBLimpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(240, 240, 240))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 100, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 686, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jBGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
            .addGroup(layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jBModificar)
                        .addComponent(jBLimpiar))
                    .addComponent(jBSalir))
                .addContainerGap(80, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jBGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuardarActionPerformed
        Paciente paciente;
        try {
            if (jTNombre.getText().isEmpty() || jTDni.getText().isEmpty() || jTDomicilio.getText().isEmpty() || jTTelefono.getText().isEmpty() || jTPesoActual.getText().isEmpty() || (!jChEstado.isSelected())) {
                JOptionPane.showMessageDialog(null, "debe llenar todos los campos");
                return;
            }

            String nombre = jTNombre.getText();
            int dni = Integer.parseInt(jTDni.getText());
            String domicilio = jTDomicilio.getText();
            int tel = Integer.parseInt(jTTelefono.getText());
            double pesoActual = Double.parseDouble(jTPesoActual.getText());
            boolean estado = jChEstado.isSelected();

            paciente = new Paciente(nombre, dni, domicilio, tel, pesoActual, estado);

            pd.guardarPaciente(paciente);
            jBGuardar.setEnabled(false);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Debe ingresar datos validos");
        }
        jBLimpiarActionPerformed(evt);
    }//GEN-LAST:event_jBGuardarActionPerformed

    private void jBModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBModificarActionPerformed
        Paciente paciente;
        try {
            String nombre = jTNombre.getText();
            int dni = Integer.parseInt(jTDni.getText());
            String domicilio = jTDomicilio.getText();
            int telefono = Integer.parseInt(jTTelefono.getText());
            double peso = Double.parseDouble(jTPesoActual.getText());
            boolean estado = jChEstado.isSelected();
            int id = Integer.parseInt(jTID.getText());
            paciente = new Paciente(nombre, dni, domicilio, telefono, id, peso, estado);

            pd.modificarPaciente(paciente);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Debe ingresar datos validos");
        }
        jBModificar.setEnabled(false);
        jBGuardar.setEnabled(true);
        jBLimpiarActionPerformed(evt);
    }//GEN-LAST:event_jBModificarActionPerformed

    private void jBSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalirActionPerformed
        Dashboardv2 db = new Dashboardv2();
        this.setVisible(false);
        db.setVisible(true);
    }//GEN-LAST:event_jBSalirActionPerformed

    private void jBEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEliminarActionPerformed
        Paciente paciente = new Paciente();
        try {
            int dni = Integer.parseInt(jTDni.getText());
            paciente = pd.buscarPacientePorDni(dni, 2);
            int id = paciente.getIdPaciente();
            pd.eliminarPacienteLogico(id);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Debe ingresar datos validos");
        }
        jBLimpiarActionPerformed(evt);
    }//GEN-LAST:event_jBEliminarActionPerformed

    private void jBBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscarActionPerformed
        Paciente paciente = new Paciente();
        try {

            if (jTDni.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El campo dni esta vacio");
            } else {
                int dni = Integer.parseInt(jTDni.getText());
                paciente = pd.buscarPacientePorDni(dni, 2);
                jTNombre.setText(paciente.getNombre());
                jTDomicilio.setText(paciente.getDomicilio());
                jTTelefono.setText(paciente.getTelefono() + "");
                jTPesoActual.setText(paciente.getPesoActual() + "");
                jChEstado.setSelected(true);
                jTID.setText(paciente.getIdPaciente() + "");

                jTID.setEditable(false);
                jBGuardar.setEnabled(false);
                jBModificar.setEnabled(true);
                jBEliminar.setEnabled(true);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Debe ingresar datos validos");
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "Intenta con otro dni");
        }
    }//GEN-LAST:event_jBBuscarActionPerformed

    private void jBLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLimpiarActionPerformed
        limpiar();
        jTID.setEditable(false);
        jBGuardar.setEnabled(true);
        jBModificar.setEnabled(false);
        jBEliminar.setEnabled(false);
        jBModificar.setEnabled(false);
    }//GEN-LAST:event_jBLimpiarActionPerformed

    private void jTNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNombreKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();

        if (Character.isDigit(c)) {
            evt.consume();
        }

        if (Character.isLetter(c)) {
            if (jTNombre.getText().length() >= 100) {
                evt.consume();
            }
        }
        if (Character.isWhitespace(c)) {
            int length = jTNombre.getText().length();
            if (length > 0 && jTNombre.getText().charAt(length - 1) == ' ') {
                evt.consume();
            }
        }
        if (c == '.' && jTNombre.getText().contains(".")) {//controla que solo se pueda ingresar un solo punto
            evt.consume();
        }
    }//GEN-LAST:event_jTNombreKeyTyped

    private void jTDniKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDniKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();

        if (!Character.isDigit(c)) {
            evt.consume();
        }

        if (jTDni.getText().length() >= 9) {
            evt.consume();
        }
    }//GEN-LAST:event_jTDniKeyTyped

    private void jTDomicilioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDomicilioKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            if (jTDomicilio.getText().length() >= 100) {
                evt.consume();
            }
        }
        if (Character.isWhitespace(c)) {
            int length = jTDomicilio.getText().length();
            if (length > 0 && jTDomicilio.getText().charAt(length - 1) == ' ') {
                evt.consume();
            }
        }
    }//GEN-LAST:event_jTDomicilioKeyTyped

    private void jTTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelefonoKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }

        if (jTTelefono.getText().length() >= 9) {
            evt.consume();
        }
    }//GEN-LAST:event_jTTelefonoKeyTyped

    private void jTPesoActualKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPesoActualKeyTyped
        if (jTPesoActual.getText().length() >= 6) {
            evt.consume();
        }
    }//GEN-LAST:event_jTPesoActualKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBBuscar;
    private javax.swing.JButton jBEliminar;
    private javax.swing.JButton jBGuardar;
    private javax.swing.JButton jBLimpiar;
    private javax.swing.JButton jBModificar;
    private javax.swing.JButton jBSalir;
    private javax.swing.JCheckBox jChEstado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField jTDni;
    private javax.swing.JTextField jTDomicilio;
    private javax.swing.JTextField jTID;
    private javax.swing.JTextField jTNombre;
    private javax.swing.JTextField jTPesoActual;
    private javax.swing.JTextField jTTelefono;
    // End of variables declaration//GEN-END:variables
 public void limpiar() {
        jTNombre.setText("");
        jTDni.setText("");
        jTDomicilio.setText("");
        jTTelefono.setText("");
        ((AbstractDocument) jTPesoActual.getDocument()).setDocumentFilter(null);
        jTPesoActual.setText("");
        ((AbstractDocument) jTPesoActual.getDocument()).setDocumentFilter(fp);
        jChEstado.setSelected(false);
        jTID.setText("");
        jTID.setEditable(false);
    }

    class FiltraEntrada6 extends DocumentFilter {

        public static final char SOLO_NUMEROS = 'N';
        public static final char SOLO_LETRAS = 'L';
        public static final char NUM_LETRAS = 'M';
        public static final char DEFAULT = '*';

        private char tipoEntrada;
        private int longitudCadena = 0;
        private int longitudActual = 0;

        public FiltraEntrada6() {
            tipoEntrada = DEFAULT;
        }

        public FiltraEntrada6(char tipoEntrada) {
            this.tipoEntrada = tipoEntrada;
        }

        public FiltraEntrada6(char tipoEntrada, int longitudCadena) {
            this.tipoEntrada = tipoEntrada;
            this.longitudCadena = longitudCadena;
        }

        @Override
        public void insertString(DocumentFilter.FilterBypass fb, int i, String string, javax.swing.text.AttributeSet as) throws BadLocationException {
            if (string != null && !string.isEmpty()) { // verifica que el texto no sea nulo ni este vacio
                Document dc = fb.getDocument();
                longitudActual = dc.getLength();
                if (longitudCadena == 0 || longitudActual < longitudCadena) {
                    fb.insertString(i, string, as); // Inserta el texto si no se supera la longitud máxima
                }
            }
        }

        @Override
        public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length);
        }

        /*
        En este método:
        /// @Override: Indica que estás anulando el método remove de la superclase DocumentFilter.

        /// public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException:
        Esto es la declaración del método, que acepta tres parámetros: fb (un objeto FilterBypass que permite realizar la eliminación),
        offset (la posición desde la cual se eliminará el texto) y length (la cantidad de caracteres a eliminar).  
        
        ///super.remove(fb, offset, length);: Este es el llamado al método remove de la superclase DocumentFilter, 
        que se encarga de realizar la eliminación del texto en el documento. 
        No se requiere ninguna lógica adicional en este método, ya que simplemente delega la operación de eliminación a la implementación predeterminada de la superclase.
         */
        @Override
        public void replace(DocumentFilter.FilterBypass fb, int i, int i1, String string, javax.swing.text.AttributeSet as) throws BadLocationException {
            Document dc = fb.getDocument();
            if (string == null) {
                fb.replace(0, i1, "", as);
                return;
            }
            if (string.isEmpty()) {
                fb.replace(0, i1, "", as);
                return;
            }
            longitudActual = dc.getLength();
            if (esValido(string)) {
                if (this.longitudCadena == 0 || longitudActual < longitudCadena) {
                    fb.replace(i, i1, string, as);
                }
            }
        }

        private boolean esValido(String valor) {
            char[] letras = valor.toCharArray();
            boolean valido = false;
            for (int i = 0; i < letras.length; i++) {

                switch (tipoEntrada) {
                    case SOLO_NUMEROS:
                        return valor.matches("[0-9]+");// verifica si solo contiene numeros
                    case SOLO_LETRAS:
                        return valor.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]+");// verifica si solo contiene letras y espacios
                    case NUM_LETRAS:
                        return valor.matches("[0-9a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]+");// verifica si contiene números, letras y espacios
                    default:
                        valido = true;
                        return valido;
                }
            }
            return valido;
        }
    }

    class NumericRangeFilter6 extends DocumentFilter {

        @Override
        public void replace(DocumentFilter.FilterBypass fb, int i, int i1, String string, AttributeSet as) throws BadLocationException {
            String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());//obtiene el texto actual del jtf

            String nextText = currentText.substring(0, i) + string + currentText.substring(i + i1);//concatena el texto a insertar con el texto acutal

            try {
                double num = Double.parseDouble(nextText);//intenta convertir el texto en numero

                if (num >= 0.0 && num <= 500.0) {//verifica si el numero esta en el rango de 0.0 a 10.0
                    super.replace(fb, i, i1, string, as);
                } else {
                    //fuera de rango
                    Toolkit.getDefaultToolkit().beep();//sonido de error
                }
            } catch (NumberFormatException e) {
                Toolkit.getDefaultToolkit().beep(); //El texto no es un número válido...Emite un sonido de error.
            }
        }
    }

}
