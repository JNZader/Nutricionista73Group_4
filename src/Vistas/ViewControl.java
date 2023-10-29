package Vistas;

import Conexion.DietaDAO;
import Entidades.Dieta;
import java.util.ArrayList;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ViewControl extends javax.swing.JPanel {

    DefaultTableModel modelo;

    public ViewControl() {
        initComponents();
        JTableHeader tbh = jTable1.getTableHeader();
        tbh.setReorderingAllowed(false);
        jTable1.setTableHeader(tbh);

        ListSelectionModel selectionModel = jTable1.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };
        jTable1.setModel(modelo);
        modelo.setColumnIdentifiers(new String[]{"Nombre Paciente", "DNI", "Peso Actual", "Peso Objetivo", "Fecha Culminación"});
        limpiarTabla();
        botonDietasFinalizadas.setSelected(true);
        limpiarTabla();
    }

    private void limpiarTabla() {
        modelo.setRowCount(0);
    }

    private void llenarTablaNo() {
        DietaDAO ddao = new DietaDAO();
        ArrayList<String[]> pacientesData = ddao.listarPacientesNoAlcanzaronPesoObjetivo();
        for (String[] pacienteData : pacientesData) {
            modelo.addRow(pacienteData);
        }
        jTable1.setModel(modelo);
    }

    private void llenarTablasi() {
        DietaDAO ddao = new DietaDAO();
        ArrayList<String[]> pacientesData = ddao.listarPacientesAlcanzaronPesoDeseado();
        for (String[] pacienteData : pacientesData) {
            modelo.addRow(pacienteData);
        }
        jTable1.setModel(modelo);
    }
public void llenarTabla() {

    ArrayList<Dieta> dietas = new ArrayList<>();
    boolean soloNoCumplidas = false;
    DietaDAO didao=new DietaDAO();
    
    if (botonDietasNoFinalizadas.isSelected()) {
        dietas = didao.listarDietas(true); // Obtener dietas no cumplidas
        soloNoCumplidas = true;
    } else if (botonDietasFinalizadas.isSelected()) {
        dietas = didao.listarDietas(false); // Obtener todas las dietas
    }

    for (Dieta dieta : dietas) {
        // Agregar datos de las dietas a la tabla
        modelo.addRow(new Object[]{
            dieta.getPaciente().getNombre(),
            dieta.getPaciente().getDni(),
            dieta.getPaciente().getPesoActual(),
            dieta.getPesoFinal(),
            dieta.getFechaFinal()
        });
    }
}    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        botonDietasFinalizadas = new javax.swing.JRadioButton();
        botonDietasNoFinalizadas = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        salirControlTratamiento = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setBackground(new java.awt.Color(180, 220, 160));
        setMaximumSize(new java.awt.Dimension(840, 690));
        setMinimumSize(new java.awt.Dimension(840, 690));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Control Tratamientos");

        buttonGroup1.add(botonDietasFinalizadas);
        botonDietasFinalizadas.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        botonDietasFinalizadas.setSelected(true);
        botonDietasFinalizadas.setText("Lista de Dietas");
        botonDietasFinalizadas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonDietasFinalizadasActionPerformed(evt);
            }
        });

        buttonGroup1.add(botonDietasNoFinalizadas);
        botonDietasNoFinalizadas.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        botonDietasNoFinalizadas.setText("Dietas con objetivos no cumplidos a la fecha");
        botonDietasNoFinalizadas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonDietasNoFinalizadasActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        salirControlTratamiento.setBackground(new java.awt.Color(150, 200, 130));
        salirControlTratamiento.setText("Salir");
        salirControlTratamiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirControlTratamientoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 840, Short.MAX_VALUE)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(salirControlTratamiento)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addComponent(botonDietasFinalizadas)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(botonDietasNoFinalizadas))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 653, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonDietasFinalizadas)
                    .addComponent(botonDietasNoFinalizadas))
                .addGap(41, 41, 41)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(salirControlTratamiento)
                .addGap(48, 48, 48))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void salirControlTratamientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirControlTratamientoActionPerformed
        Dashboardv2 db = new Dashboardv2();
        this.setVisible(false);
        db.setVisible(true);
    }//GEN-LAST:event_salirControlTratamientoActionPerformed
    private void botonDietasFinalizadasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonDietasFinalizadasActionPerformed
        limpiarTabla();
        llenarTabla();
    }//GEN-LAST:event_botonDietasFinalizadasActionPerformed

    private void botonDietasNoFinalizadasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonDietasNoFinalizadasActionPerformed
        limpiarTabla();
        llenarTabla();
    }//GEN-LAST:event_botonDietasNoFinalizadasActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton botonDietasFinalizadas;
    private javax.swing.JRadioButton botonDietasNoFinalizadas;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton salirControlTratamiento;
    // End of variables declaration//GEN-END:variables
}
