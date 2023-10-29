package Vistas;

import Conexion.ConsultaDAO;
import Conexion.PacienteDAO;
import Entidades.Consulta;
import Entidades.*;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Color;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ViewConsulta extends javax.swing.JPanel {

    ConsultaDAO conDAO = new ConsultaDAO();

    DefaultTableModel model;
    private Consulta consu;

    public ViewConsulta() {
        initComponents();

        cargarCombo();
        ((JTextFieldDateEditor) jDateChFecha.getDateEditor()).setEditable(false);//controla que no se pueda editar el campo de fecha
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };

        JTableHeader tbh = jTTablaHistorial.getTableHeader();
        tbh.setReorderingAllowed(false);
        jTTablaHistorial.setTableHeader(tbh);

        ListSelectionModel selectionModel = jTTablaHistorial.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        armarCabecera();
        jBmodificar.setEnabled(false);
    }

    public ViewConsulta(Consulta consulta) {
        this();
        this.consu = consulta;
        jBGuargarConsulta.setEnabled(false);
        jBmodificar.setEnabled(true);

        llenarDatos();
    }

    public void llenarDatos() {
        jTPeso.setText(consu.getPesoActual() + "");
        jDateChFecha.setDate(Date.valueOf(consu.getFecha()));
        cargarComboBoxConPaciente(consu.getPaciente());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jCbSeleccionPaciente = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jDateChFecha = new com.toedter.calendar.JDateChooser();
        jTPeso = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jBGuargarConsulta = new javax.swing.JButton();
        jBVerHistorialDesdeCombo = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLPesoInicial = new javax.swing.JLabel();
        jBmodificar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTTablaHistorial = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();

        setBackground(new java.awt.Color(180, 220, 160));
        setMaximumSize(new java.awt.Dimension(840, 690));
        setMinimumSize(new java.awt.Dimension(840, 690));

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Historial paciente");

        jPanel1.setBackground(new java.awt.Color(160, 200, 140));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Consulta", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setText("Selecciona un paciente");

        jCbSeleccionPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCbSeleccionPacienteActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setText("Peso");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setText("Fecha");

        jDateChFecha.setMaxSelectableDate(new java.util.Date(1735704062000L));
        jDateChFecha.setMinSelectableDate(new java.util.Date(1672545662000L));

        jTPeso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTPesoKeyTyped(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setText("Kg");

        jBGuargarConsulta.setBackground(new java.awt.Color(150, 200, 130));
        jBGuargarConsulta.setText("Guardar");
        jBGuargarConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGuargarConsultaActionPerformed(evt);
            }
        });

        jBVerHistorialDesdeCombo.setBackground(new java.awt.Color(150, 200, 130));
        jBVerHistorialDesdeCombo.setText("Ver historial");
        jBVerHistorialDesdeCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBVerHistorialDesdeComboActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setText("Peso inicial");

        jLPesoInicial.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLPesoInicial.setText("          ");

        jBmodificar.setBackground(new java.awt.Color(150, 200, 130));
        jBmodificar.setText("Modificar");
        jBmodificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBmodificarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jDateChFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 580, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTPeso, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel1))
                            .addComponent(jBmodificar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBGuargarConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jCbSeleccionPaciente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jBVerHistorialDesdeCombo, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(123, 123, 123)
                                .addComponent(jLabel8))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLPesoInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(17, 17, 17))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCbSeleccionPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBVerHistorialDesdeCombo))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTPeso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jDateChFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBmodificar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jBGuargarConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)
                        .addComponent(jLabel8)
                        .addComponent(jLPesoInicial)))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jTTablaHistorial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTTablaHistorial);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 715, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(64, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 715, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61))
            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(67, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jBVerHistorialDesdeComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVerHistorialDesdeComboActionPerformed
        actualizarTabla();
        if (jCbSeleccionPaciente.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un paciente");
            return;
        } else {
            Paciente pac = (Paciente) jCbSeleccionPaciente.getSelectedItem();
            List<Consulta> listaConsulta = conDAO.buscar(pac);

            for (Consulta consulta : listaConsulta) {
                model.addRow(new Object[]{consulta.getPaciente().getNombre(), consulta.getPesoActual(), consulta.getFecha()});
            }
            jTTablaHistorial.setModel(model);
        }
    }//GEN-LAST:event_jBVerHistorialDesdeComboActionPerformed

    private void jBGuargarConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuargarConsultaActionPerformed
        Consulta consulta;
        String aux = jTPeso.getText();
        if (jCbSeleccionPaciente.getSelectedItem() != null
                && jDateChFecha.getDate() != null && Character.isDigit((jTPeso.getText()).charAt(0))) {
            double peso = Double.parseDouble(jTPeso.getText());
            LocalDate fecha = jDateChFecha.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Paciente paciente = (Paciente) jCbSeleccionPaciente.getSelectedItem();

            consulta = new Consulta(paciente, fecha, peso);
            conDAO.insertar(consulta);
            limpiar();
        } else {
            JOptionPane.showMessageDialog(this, "Ingresa datos validos");
        }
    }//GEN-LAST:event_jBGuargarConsultaActionPerformed

    private void jCbSeleccionPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCbSeleccionPacienteActionPerformed
        if (jCbSeleccionPaciente.getSelectedItem() != null) {

            Paciente pac = (Paciente) jCbSeleccionPaciente.getSelectedItem();
            jLPesoInicial.setBackground(new Color(0, 255, 0));
            jLPesoInicial.setOpaque(true);

            jLPesoInicial.setHorizontalAlignment(JLabel.CENTER);
            jLPesoInicial.setVerticalAlignment(JLabel.CENTER);
            jLPesoInicial.setText(pac.getPesoActual() + " Kg");
        } else {
            jLPesoInicial.setText(" ");
        }
        actualizarTabla();
    }//GEN-LAST:event_jCbSeleccionPacienteActionPerformed

    private void jTPesoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPesoKeyTyped
        char c = evt.getKeyChar();

        if (!(Character.isDigit(c) || c == '.')) {
            evt.consume();
        }

        if (c == '.' && jTPeso.getText().contains(".")) {//controla que solo se pueda ingresar un solo punto
            evt.consume();
        }

        if (jTPeso.getText().length() > 5) {
            evt.consume();
        }
    }//GEN-LAST:event_jTPesoKeyTyped

    private void jBmodificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBmodificarActionPerformed
        String aux = jTPeso.getText();
        if (jCbSeleccionPaciente.getSelectedItem() != null
                && jDateChFecha.getDate() != null && Character.isDigit((jTPeso.getText()).charAt(0))) {
            double peso = Double.parseDouble(jTPeso.getText());
            LocalDate fecha = jDateChFecha.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Paciente paciente = (Paciente) jCbSeleccionPaciente.getSelectedItem();

            conDAO.actualizar(new Consulta(consu.getIdConsulta(), paciente, fecha, peso));
            limpiar();
        } else {
            JOptionPane.showMessageDialog(this, "Ingresa datos validos");
        }
        jBmodificar.setEnabled(false);
        jBGuargarConsulta.setEnabled(true);
    }//GEN-LAST:event_jBmodificarActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBGuargarConsulta;
    private javax.swing.JButton jBVerHistorialDesdeCombo;
    private javax.swing.JButton jBmodificar;
    private javax.swing.JComboBox<Paciente> jCbSeleccionPaciente;
    private com.toedter.calendar.JDateChooser jDateChFecha;
    private javax.swing.JLabel jLPesoInicial;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTPeso;
    private javax.swing.JTable jTTablaHistorial;
    // End of variables declaration//GEN-END:variables
    public void armarCabecera() {
        model.addColumn("Nombre");
        model.addColumn("peso");
        model.addColumn("fecha");

        jTTablaHistorial.setModel(model);

        for (int i = 0; i < jTTablaHistorial.getColumnCount(); i++) {
            jTTablaHistorial.getTableHeader().getColumnModel().getColumn(i).setResizable(false);
        }
    }

    public void cargarCombo() {
        PacienteDAO pacDAO = new PacienteDAO();
        Paciente pac;
        List<Paciente> listado = pacDAO.listarPaciente(1);
        jCbSeleccionPaciente.addItem(null);

        for (Paciente paciente : listado) {
            jCbSeleccionPaciente.addItem(paciente);
        }
    }

    public void limpiar() {
        jTPeso.setText("");
        jDateChFecha.setDate(null);
        jCbSeleccionPaciente.setSelectedIndex(0);
    }

    private void actualizarTabla() {
        while (model.getRowCount() > 0) {
            model.removeRow(0);// Mientras haya filas en el modelo de la tabla, elimina la primera
        }
        jTTablaHistorial.setModel(model);// Establece el modelo actualizado de la tabla
    }

    public void cargarComboBoxConPaciente(Paciente paciente) {
        jCbSeleccionPaciente.removeAllItems();

        PacienteDAO pacDAO = new PacienteDAO();
        List<Paciente> listado = pacDAO.listarPaciente(1);

        for (Paciente p : listado) {
            jCbSeleccionPaciente.addItem(p);
        }

        if (paciente != null) {
            jCbSeleccionPaciente.setSelectedItem(paciente);
        }
    }
}
