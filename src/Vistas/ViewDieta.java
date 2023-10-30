package Vistas;

import Conexion.ComidaDAO;
import Conexion.DietaComidaDAO;
import Conexion.DietaDAO;
import Conexion.PacienteDAO;
import Entidades.Comida;
import Entidades.Dieta;
import Entidades.DietaComida;
import Entidades.Horario;
import Entidades.Paciente;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Toolkit;
import static java.awt.image.ImageObserver.ERROR;
import static java.awt.image.ImageObserver.WIDTH;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

public class ViewDieta extends javax.swing.JPanel {

    DocumentFilter filtroLetras;
    DocumentFilter filtroNumeros;
    NumericRangeFilter5 fpf;

    private Dieta die;
    private DietaComida dietCom;
    private DietaComidaDAO dieDAO = new DietaComidaDAO();

    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel mode = new DefaultTableModel();

    public ViewDieta() {
        initComponents();

        filtroNumeros = new FiltraEntrada5(FiltraEntrada5.SOLO_NUMEROS);
        filtroLetras = new FiltraEntrada5(FiltraEntrada5.SOLO_LETRAS);
        fpf = new NumericRangeFilter5();

        jbModificar.setEnabled(false);
        jButtonModif.setEnabled(false);

        mode = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };

        llenarComboBox();

        llenarTablaDietaDisponibles();
        llenarcomboBoxComidas();
        comboBoxHorario();
        llenarCabeceraDetalle();

        JTableHeader tbh = jTablaDetalleDieta.getTableHeader();
        tbh.setReorderingAllowed(false);
        jTablaDetalleDieta.setTableHeader(tbh);

        ListSelectionModel selectionModel = jTablaDetalleDieta.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JTableHeader tbh2 = jTablaDietaDispo.getTableHeader();
        tbh2.setReorderingAllowed(false);
        jTablaDietaDispo.setTableHeader(tbh2);

        ListSelectionModel selectionModel2 = jTablaDietaDispo.getSelectionModel();
        selectionModel2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        jDChoFeInicial.addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getNewValue() != null) {
                    jdatechoFechaFinal.setMinSelectableDate(
                            Date.from(jDChoFeInicial.getDate().toInstant().plus(1, ChronoUnit.DAYS))
                    );
                }
            }
        });
        ((JTextFieldDateEditor) jDChoFeInicial.getDateEditor()).setEditable(false);
        ((JTextFieldDateEditor) jdatechoFechaFinal.getDateEditor()).setEditable(false);

        ((AbstractDocument) jtnombre.getDocument()).setDocumentFilter(filtroLetras);
        ((AbstractDocument) jtfPorcion.getDocument()).setDocumentFilter(filtroNumeros);
        ((AbstractDocument) jtpesofinal.getDocument()).setDocumentFilter(fpf);

        listSelection();
    }

    public ViewDieta(Dieta dieta) {
        this();
        this.die = dieta;
        jButtonModif.setEnabled(true);
        cargarComboBoxConPaciente(die.getPaciente());
        jtnombre.setText(die.getNombre());
        jDChoFeInicial.setDate(Date.valueOf(die.getFechaInicial()));
        jdatechoFechaFinal.setDate(Date.valueOf(die.getFechaFinal()));
        jtpesofinal.setText(die.getPesoFinal() + "");
        jCbEstado.setSelected(die.isEstado());
    }

    public ViewDieta(DietaComida dietaComida) {
        this();
        this.dietCom = dietaComida;
        jbModificar.setEnabled(true);
        cargarDatosDietaComida();
    }

    private void cargarDatosDietaComida() {
        jtfPorcion.setText(String.valueOf(dietCom.getPorcion()));
        llenarComboBoxConComida(dietCom.getComida());
        jComboBoxHorario.setSelectedItem(dietCom.getHorario());
    }

    public void cargarComboBoxConPaciente(Paciente paciente) {
        jComboPaciente.removeAllItems();

        llenarComboBox();

        if (paciente != null) {
            jComboPaciente.setSelectedItem(paciente);
        }
    }

    public void llenarComboBoxConComida(Comida comida) {
        jComboBoxComidas.removeAllItems();
        llenarcomboBoxComidas();

        System.out.println(comida);
        if (comida != null) {
            jComboBoxComidas.setSelectedItem(comida);
        }
    }

    private void llenarComboBox() {
        PacienteDAO pacient = new PacienteDAO();
        ArrayList<Paciente> pacientes = pacient.listarPaciente(1);//obtiene la una lista de materias activas de la base de datos
        jComboPaciente.removeAllItems();
        jComboPaciente.addItem(null);
//agrega un espacio vacio en el primer elemento del combobox
        for (Paciente aux : pacientes) {//itera a traves de la lista y agrega cada materia al combobox
            jComboPaciente.addItem(aux);

        }
    }

    public void llenarcomboBoxComidas() {
        jComboBoxComidas.removeAllItems();
        ComidaDAO comida = new ComidaDAO();
        ArrayList<Comida> comidas = comida.listarComidas(1);
        jComboBoxComidas.addItem(null);

        for (Comida comi : comidas) {//itera a traves de la lista y agrega cada materia al combobox
            jComboBoxComidas.addItem(comi);

        }
    }

    public void habilitarBoton() {
        if (!jtnombre.getText().isEmpty()
                && jComboPaciente.getSelectedIndex() > 0
                && jDChoFeInicial.getDate() != null
                && jdatechoFechaFinal.getDate() != null
                && !jtpesofinal.getText().isEmpty()) {
            jBGuardar.setEnabled(true);
        } else {
            jBGuardar.setEnabled(false);
        }

    }

    public void comboBoxHorario() {
        jComboBoxHorario.removeAllItems();
        ArrayList<Horario> horario = new ArrayList<>();
        jComboBoxHorario.addItem(null);
        horario.add(Horario.DESAYUNO);
        horario.add(Horario.ALMUERZO);
        horario.add(Horario.MERIENDA);
        horario.add(Horario.CENA);
        horario.add(Horario.SNACK);

        for (Horario hora : horario) {//itera a traves de la lista y agrega cada materia al combobox
            jComboBoxHorario.addItem(hora);

        }
    }

    public void llenarCabecera() {
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Paciente");
        modelo.addColumn("Fecha inicial");
        modelo.addColumn("Fecha final");
        modelo.addColumn("Estado");
        modelo.addColumn("Peso final");
        modelo.addColumn("Dieta");
        jTablaDietaDispo.setModel(modelo);
        for (int i = 0; i < jTablaDietaDispo.getColumnCount(); i++) {
            jTablaDietaDispo.getTableHeader().getColumnModel().getColumn(i).setResizable(false);
        }

        
        jTablaDietaDispo.getColumnModel().getColumn(7).setMinWidth(0);
        jTablaDietaDispo.getColumnModel().getColumn(7).setMaxWidth(0);
        jTablaDietaDispo.getColumnModel().getColumn(7).setWidth(0);
        jTablaDietaDispo.getColumnModel().getColumn(5).setMinWidth(0);
        jTablaDietaDispo.getColumnModel().getColumn(5).setMaxWidth(0);
        jTablaDietaDispo.getColumnModel().getColumn(5).setWidth(0);
        jTablaDietaDispo.getColumnModel().getColumn(0).setMinWidth(30);
        jTablaDietaDispo.getColumnModel().getColumn(0).setMaxWidth(30);
        jTablaDietaDispo.getColumnModel().getColumn(0).setWidth(30);
    }

    public void llenarCabeceraDetalle() {
        mode.addColumn("ID");
        mode.addColumn("comida");
        mode.addColumn("Porcion");
        mode.addColumn("Horario");
        mode.addColumn("obj");

        jTablaDetalleDieta.setModel(mode);
        for (int i = 0; i < jTablaDetalleDieta.getColumnCount(); i++) {
            jTablaDetalleDieta.getTableHeader().getColumnModel().getColumn(i).setResizable(false);
        }
        jTablaDetalleDieta.getColumnModel().getColumn(4).setMinWidth(0);
        jTablaDetalleDieta.getColumnModel().getColumn(4).setMaxWidth(0);
        jTablaDetalleDieta.getColumnModel().getColumn(4).setWidth(0);

        jTablaDetalleDieta.getColumnModel().getColumn(0).setMinWidth(30);
        jTablaDetalleDieta.getColumnModel().getColumn(0).setMaxWidth(30);
        jTablaDetalleDieta.getColumnModel().getColumn(0).setWidth(30);

    }

    public void llenarTablaDietaDisponibles() {
        actualizarTabla();

        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };
        modelo.setColumnIdentifiers(new String[]{"ID", "Nombre", "Paciente", "Fecha inicial", "Fecha final", "Estado", "Peso final", "Dieta"});

        DietaDAO dietadao = new DietaDAO();

        ArrayList<Dieta> buscar = dietadao.buscar(1);
        //itera a traves de la lista de materias cursadas y agrega cada una como una fila en la tabla
        for (Dieta i : buscar) {
            Object[] fila = new Object[8]; // Asumiendo que hay 7 columnas en tu tabla
            fila[0] = i.getIdDieta();
            fila[1] = i.getNombre();
            fila[2] = i.getPaciente().getNombre(); // Asegúrate de que la clase Dieta tenga un método getPaciente()
            fila[3] = i.getFechaInicial();
            fila[4] = i.getFechaFinal();
            if (i.isEstado()) {
                fila[5] = "Activo";
            } else {
                fila[5] = "Inactivo";
            }
            fila[6] = i.getPesoFinal();
            fila[7] = i;

            modelo.addRow(fila);
        }
        jTablaDietaDispo.setModel(modelo);
        for (int i = 0; i < jTablaDietaDispo.getColumnCount(); i++) {
            jTablaDietaDispo.getTableHeader().getColumnModel().getColumn(i).setResizable(false);
        }
        jTablaDietaDispo.setModel(modelo);// es
        jTablaDietaDispo.getColumnModel().getColumn(7).setMinWidth(0);
        jTablaDietaDispo.getColumnModel().getColumn(7).setMaxWidth(0);
        jTablaDietaDispo.getColumnModel().getColumn(7).setWidth(0);

        jTablaDietaDispo.getColumnModel().getColumn(0).setMinWidth(30);
        jTablaDietaDispo.getColumnModel().getColumn(0).setMaxWidth(30);
        jTablaDietaDispo.getColumnModel().getColumn(0).setWidth(30);

        jTablaDietaDispo.getColumnModel().getColumn(5).setMinWidth(0);
        jTablaDietaDispo.getColumnModel().getColumn(5).setMaxWidth(0);
        jTablaDietaDispo.getColumnModel().getColumn(5).setWidth(0);
    }

    public void llenarTablaDetalleDieta(Dieta i) {
        int id = i.getIdDieta();
        
        ArrayList<DietaComida> buscarPorIdDieta = dieDAO.buscarPorIdDieta(id, 1);
        for (DietaComida d : buscarPorIdDieta) {
            mode.addRow(new Object[]{d.getDieta().getIdDieta(), d.getComida(), d.getPorcion(), d.getHorario(), i});
        }
        

        jTablaDetalleDieta.setModel(mode);// es

    }

    public void actualizarTabla() {
        while (modelo.getRowCount() > 0) { // mientras haya fila en el modelo de la tabla
            modelo.removeRow(0);  // borra la primer fila

        }
        jTablaDietaDispo.setModel(modelo);
    }

    public void actualizarTablaDos() {
        while (mode.getRowCount() > 0) { // mientras haya fila en el modelo de la tabla
            mode.removeRow(0);  // borra la primer fila

        }
        jTablaDetalleDieta.setModel(mode);
    }

    public void limpiarTodo() {
        llenarComboBox();
        llenarcomboBoxComidas();
        comboBoxHorario();
        jDChoFeInicial.setDate(null);
        jdatechoFechaFinal.setDate(null);
        ((AbstractDocument) jtnombre.getDocument()).setDocumentFilter(null);
        ((AbstractDocument) jtfPorcion.getDocument()).setDocumentFilter(null);
        ((AbstractDocument) jtpesofinal.getDocument()).setDocumentFilter(null);
        jtnombre.setText("");
        jtpesofinal.setText("");
        jCbEstado.setSelected(false);
        jtfPorcion.setText("");
        ((AbstractDocument) jtnombre.getDocument()).setDocumentFilter(filtroLetras);
        ((AbstractDocument) jtfPorcion.getDocument()).setDocumentFilter(filtroNumeros);
        ((AbstractDocument) jtpesofinal.getDocument()).setDocumentFilter(fpf);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlNombre = new javax.swing.JLabel();
        jlPaciente = new javax.swing.JLabel();
        jlEstado = new javax.swing.JLabel();
        jDChoFeInicial = new com.toedter.calendar.JDateChooser();
        jdatechoFechaFinal = new com.toedter.calendar.JDateChooser();
        jlFeinicial = new javax.swing.JLabel();
        jlFefinal = new javax.swing.JLabel();
        jtnombre = new javax.swing.JTextField();
        jBGuardar = new javax.swing.JButton();
        jbAgregar = new javax.swing.JButton();
        jbEliminar = new javax.swing.JButton();
        jbSalir = new javax.swing.JButton();
        jlPesofinal = new javax.swing.JLabel();
        jtpesofinal = new javax.swing.JTextField();
        jCbEstado = new javax.swing.JCheckBox();
        jComboPaciente = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jComboBoxHorario = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablaDietaDispo = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jComboBoxComidas = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jScrollPaneDetalle = new javax.swing.JScrollPane();
        jTablaDetalleDieta = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButtonModif = new javax.swing.JButton();
        jtfPorcion = new javax.swing.JTextField();
        jbModificar = new javax.swing.JButton();

        setBackground(new java.awt.Color(180, 220, 160));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(new java.awt.Dimension(840, 690));
        setMinimumSize(new java.awt.Dimension(840, 690));
        setPreferredSize(new java.awt.Dimension(840, 690));

        jlNombre.setBackground(new java.awt.Color(51, 51, 51));
        jlNombre.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jlNombre.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlNombre.setText("Nombre");

        jlPaciente.setBackground(new java.awt.Color(51, 51, 51));
        jlPaciente.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jlPaciente.setText("Paciente");

        jlEstado.setBackground(new java.awt.Color(51, 51, 51));
        jlEstado.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jlEstado.setText("Estado");

        jlFeinicial.setBackground(new java.awt.Color(51, 51, 51));
        jlFeinicial.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jlFeinicial.setText("Fecha Inicial");

        jlFefinal.setBackground(new java.awt.Color(51, 51, 51));
        jlFefinal.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jlFefinal.setText("Fecha Final");

        jtnombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtnombreKeyTyped(evt);
            }
        });

        jBGuardar.setBackground(new java.awt.Color(150, 200, 130));
        jBGuardar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBGuardar.setForeground(new java.awt.Color(0, 0, 51));
        jBGuardar.setText("Guardar Dieta");
        jBGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGuardarActionPerformed(evt);
            }
        });

        jbAgregar.setBackground(new java.awt.Color(150, 200, 130));
        jbAgregar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jbAgregar.setForeground(new java.awt.Color(0, 0, 51));
        jbAgregar.setText("Agregar");
        jbAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAgregarActionPerformed(evt);
            }
        });

        jbEliminar.setBackground(new java.awt.Color(150, 200, 130));
        jbEliminar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jbEliminar.setForeground(new java.awt.Color(0, 0, 51));
        jbEliminar.setText("Eliminar");
        jbEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEliminarActionPerformed(evt);
            }
        });

        jbSalir.setBackground(new java.awt.Color(150, 200, 130));
        jbSalir.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jbSalir.setForeground(new java.awt.Color(0, 0, 51));
        jbSalir.setText("Salir");
        jbSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSalirActionPerformed(evt);
            }
        });

        jlPesofinal.setBackground(new java.awt.Color(51, 51, 51));
        jlPesofinal.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jlPesofinal.setText("Peso Final");

        jtpesofinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtpesofinalKeyTyped(evt);
            }
        });

        jComboPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboPacienteActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setText("Horario");

        jTablaDietaDispo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Paciente", "Fecha inicial", "Fecha final", "Estado", "Peso final"
            }
        ));
        jScrollPane1.setViewportView(jTablaDietaDispo);

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setText("Comidas");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setText("Porcion");

        jTablaDetalleDieta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Comida", "Porcion", "Horario"
            }
        ));
        jScrollPaneDetalle.setViewportView(jTablaDetalleDieta);

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setText("Detalle Dieta");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setText("Dietas Disponibles");

        jButtonModif.setBackground(new java.awt.Color(150, 200, 130));
        jButtonModif.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonModif.setForeground(new java.awt.Color(51, 51, 51));
        jButtonModif.setText("Modificar");
        jButtonModif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModifActionPerformed(evt);
            }
        });

        jtfPorcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfPorcionKeyTyped(evt);
            }
        });

        jbModificar.setBackground(new java.awt.Color(150, 200, 130));
        jbModificar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jbModificar.setText("Modificar");
        jbModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbModificarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jlNombre)
                                .addGap(463, 463, 463))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jlPaciente)
                                    .addComponent(jComboPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(64, 64, 64)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jDChoFeInicial, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                    .addComponent(jlFeinicial)
                                    .addComponent(jlPesofinal, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtpesofinal))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlFefinal, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCbEstado)
                            .addComponent(jdatechoFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(94, 94, 94))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(jLabel2)
                                            .addGap(172, 172, 172))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel4)
                                            .addGap(143, 143, 143)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jComboBoxComidas, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(58, 58, 58)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jbAgregar)
                                    .addComponent(jLabel1)
                                    .addComponent(jComboBoxHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(64, 64, 64)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jtfPorcion, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3))
                                    .addComponent(jbModificar)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jBGuardar)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButtonModif))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 718, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jbEliminar)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jbSalir))
                                .addComponent(jScrollPaneDetalle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 718, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlFeinicial)
                    .addComponent(jlPaciente)
                    .addComponent(jlFefinal, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDChoFeInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jdatechoFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jlNombre)
                        .addComponent(jlPesofinal))
                    .addComponent(jlEstado, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCbEstado)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtpesofinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBGuardar)
                    .addComponent(jButtonModif))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxComidas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfPorcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jbAgregar)
                    .addComponent(jbModificar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPaneDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbEliminar)
                    .addComponent(jbSalir))
                .addGap(40, 40, 40))
        );

        jComboPaciente.getAccessibleContext().setAccessibleParent(jComboPaciente);
    }// </editor-fold>//GEN-END:initComponents

    private void jComboPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboPacienteActionPerformed
//        llenarComboBox();
    }//GEN-LAST:event_jComboPacienteActionPerformed

    private void jBGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuardarActionPerformed

        if (jtnombre.getText().isEmpty()
                || jComboPaciente.getSelectedItem() == null
                || !jCbEstado.isSelected()
                || jDChoFeInicial.getDate() == null
                || jdatechoFechaFinal.getDate() == null
                || jtpesofinal.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");

        } else {

            Paciente pac;
            String nombre = jtnombre.getText();
            boolean estado = jCbEstado.isSelected();

            Paciente pacient = (Paciente) jComboPaciente.getSelectedItem();

            LocalDate fechaInicio = jDChoFeInicial.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate fechaFinal = jdatechoFechaFinal.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            double pesoFi = Double.parseDouble(jtpesofinal.getText());

            DietaDAO dietad = new DietaDAO();
            Dieta die = new Dieta(nombre, pacient, fechaInicio, fechaFinal, pesoFi, estado);

            dietad.insertar(die);
            llenarTablaDietaDisponibles();
            limpiarTodo();
        }

//    }                                         
//    private void jbEliminarActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
//       DietaDAO diet= new DietaDAO ();
//       Dieta dieta = new Dieta();
//      int id = Integer.parseInt(jTid.getText());
//       
//        
//        if (dieta != null) {// Si encontro un alumno usa el metodo eliminar de aluData para eliminarlo de la base de datos
//
//            diet.eliminarDieta(dieta.getIdDieta());
//            // Limpia los campos de texto y demas componentes
////            ((Abstract
//Document) jTid.getDocument()).setDocumentFilter(null);
//            jTid.setText("");
//        try {
//            // 1. Crear una instancia de la clase DietaDAO (o la clase correspondiente).
//            DietaDAO dietaDAO = new DietaDAO();
//
//            // 2. Obtener el valor del campo ID.
//            int id = Integer.parseInt(jTid.getText());
//            String tf = jTid.getText();
//
//            if (tf == null && tf.isEmpty()) {
//                JOptionPane.showMessageDialog(this, "El campo ID está vacío.", "Error", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//
//            // 3. Utilizar el método para buscar la dieta por ID.
//            Dieta dieta = dietaDAO.buscarPorId(id, 1);
//
//            // 4. Si se encontró la dieta, eliminarla.
//            if (dieta != null) {
//                dietaDAO.eliminarDieta(dieta.getIdDieta());
//                JOptionPane.showMessageDialog(this, "La dieta se eliminó con éxito.");
//            } else {
//                JOptionPane.showMessageDialog(this, "La dieta no se encontró en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
//            }
////
//        
//         
//     }

    }//GEN-LAST:event_jBGuardarActionPerformed

    private void jbAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAgregarActionPerformed
        actualizarTablaDos();
        try {//recopila los datos de los textfield, radio button y jdatechosser y los guarda en diferentes variables
            if (jComboBoxComidas.getSelectedItem() == null || jComboBoxHorario.getSelectedItem() == null
                    || jtfPorcion.getText().isEmpty() || jTablaDietaDispo.getSelectedRow() == -1) {

                if (jTablaDietaDispo.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(this, "Seleccione una dieta de la tabla");
                } else {
                    JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
                }
            } else {
//                      
                DietaComidaDAO comidaDie = new DietaComidaDAO();
                DietaDAO dieta = new DietaDAO();
                Dieta diet = dieta.buscarPorId((int) jTablaDietaDispo.getValueAt(jTablaDietaDispo.getSelectedRow(), 0), 1);  // cree una dieta budcando por id y el idlo consigue a travez de la tabla getvalue med devuelveun objeto que se encuentra en la celda ij donde i es el valor de la fila conseguido por el metodo getSelectRow y j es lacolumna. 
                ComidaDAO comi = new ComidaDAO();

                int porcion = Integer.parseInt(jtfPorcion.getText());
                Comida comidas = (Comida) jComboBoxComidas.getSelectedItem();
                Horario horarios = (Horario) jComboBoxHorario.getSelectedItem();
                DietaComida dietaComida = comidaDie.insertar(new DietaComida(comidas, diet, porcion, horarios, true));

                llenarTablaDetalleDieta(diet);
                
                limpiarTodo();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace(System.out);
            JOptionPane.showMessageDialog(null, "Complete la informacion con datos validos",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jbAgregarActionPerformed

    private void jbEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEliminarActionPerformed
        // TODO add your handling code here:
//       DietaDAO diet= new DietaDAO ();
//       Dieta dieta = new Dieta();
//      int id = Integer.parseInt(jTid.getText());
//       
//        
//        if (dieta != null) {// Si encontro un alumno usa el metodo eliminar de aluData para eliminarlo de la base de datos
//
//            diet.eliminarDieta(dieta.getIdDieta());
//            // Limpia los campos de texto y demas componentes
////            ((Abstract
//Document) jTid.getDocument()).setDocumentFilter(null);
//            jTid.setText("");

//        try {
//            // 1. Crear una instancia de la clase DietaDAO (o la clase correspondiente).
//            DietaDAO dietaDAO = new DietaDAO();
//
//            // 2. Obtener el valor del campo ID.
//            int id = Integer.parseInt(jTid.getText());
//            String tf = jTid.getText();
//
//            if (tf == null && tf.isEmpty()) {
//                JOptionPane.showMessageDialog(this, "El campo ID está vacío.", "Error", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//
//            // 3. Utilizar el método para buscar la dieta por ID.
//            Dieta dieta = dietaDAO.buscarPorId(id, 1);
//
//            // 4. Si se encontró la dieta, eliminarla.
//            if (dieta != null) {
//                dietaDAO.eliminarDieta(dieta.getIdDieta());
//                JOptionPane.showMessageDialog(this, "La dieta se eliminó con éxito.");
//            } else {
//                JOptionPane.showMessageDialog(this, "La dieta no se encontró en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
//            }
//
//            // 5. Limpiar los campos y realizar otras acciones necesarias.
//            jTid.setText("");
//            jtnombre.setText("");
//            jComboPaciente.setSelectedIndex(0); // Puedes establecer el índice que corresponde a la opción predeterminada
//            jDChoFeInicial.setDate(null);
//            jdatechoFechaFinal.setDate(null);
//            jCboxEstado.setSelected(false);
//            jtpesofinal.setText("");
//            // ... otros campos
//        } catch (NumberFormatException e) {
//            JOptionPane.showMessageDialog(this, "El campo ID debe ser un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
//        } catch (Exception e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Ocurrió un error al eliminar la dieta.", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//            jtnombre.setText("");
//            jComboPaciente.setSelectedItem(null);
//            jCboxEstado.setSelected(false);
//            jDChoFeInicial.setDate(null);
//            jdatechoFechaFinal.setDate(null);
//            jtpesofinal.setText("");
//        

    }//GEN-LAST:event_jbEliminarActionPerformed

    private void jbSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalirActionPerformed
        Dashboardv2 db = new Dashboardv2();
        this.setVisible(false);
        db.setVisible(true);
    }//GEN-LAST:event_jbSalirActionPerformed

    private void jButtonModifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModifActionPerformed
        String pesofinal = jtpesofinal.getText();
        String nombre = jtnombre.getText();
        DietaDAO diedao = new DietaDAO();
        if (jComboPaciente.getSelectedItem() != null
                && jDChoFeInicial.getDate() != null && jdatechoFechaFinal.getDate() != null && Character.isDigit((jtpesofinal.getText()).charAt(0))) {
            double peso = Double.parseDouble(jtpesofinal.getText());
            LocalDate FeInicial = jDChoFeInicial.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate FechaFi = jdatechoFechaFinal.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Paciente paciente = (Paciente) jComboPaciente.getSelectedItem();
            boolean ess = jCbEstado.isSelected();
            diedao.actualizar(new Dieta(die.getIdDieta(), nombre, paciente, FeInicial, FechaFi, peso, ess));
        } else {
            JOptionPane.showMessageDialog(this, "Ingresa datos validos");
        }
        jButtonModif.setEnabled(false);
        limpiarTodo();
    }//GEN-LAST:event_jButtonModifActionPerformed

    private void jbModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbModificarActionPerformed
        try {
            DietaComidaDAO comidaDie = new DietaComidaDAO();
            DietaDAO dietaDAO = new DietaDAO();

            // Obtener la fila seleccionada de la tabla jTablaDetalleDieta
            int selectedRow = jTablaDetalleDieta.getSelectedRow();

            if (selectedRow != -1) {
                // Obtener el valor de la columna que contiene el objeto DietaComida
                DietaComida dietaComida = (DietaComida) jTablaDetalleDieta.getValueAt(selectedRow, 4);

                // Realizar la actualización
                int porcion = Integer.parseInt(jtfPorcion.getText());
                Comida comidas = (Comida) jComboBoxComidas.getSelectedItem();
                Horario horarios = (Horario) jComboBoxHorario.getSelectedItem();
                dietaComida.setPorcion(porcion);
                dietaComida.setComida(comidas);
                dietaComida.setHorario(horarios);

                comidaDie.actualizar(dietaComida);

                llenarTablaDetalleDieta(die);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace(System.out);
            JOptionPane.showMessageDialog(null, "Complete la información con datos válidos",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        jbModificar.setEnabled(false);
        limpiarTodo();
    }//GEN-LAST:event_jbModificarActionPerformed

    private void jtnombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtnombreKeyTyped
        char c = evt.getKeyChar();

        if (Character.isLetter(c)) {
            if (jtnombre.getText().length() >= 100) {
                evt.consume();
            }
        }
        if (Character.isWhitespace(c)) {
            int length = jtnombre.getText().length();
            if (length > 0 && jtnombre.getText().charAt(length - 1) == ' ') {
                evt.consume();
            }
        }
        if (c == '.' && jtnombre.getText().contains(".")) {//controla que solo se pueda ingresar un solo punto
            evt.consume();
        }

    }//GEN-LAST:event_jtnombreKeyTyped

    private void jtpesofinalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtpesofinalKeyTyped
        char c = evt.getKeyChar();
        if (Character.isDigit(c)) {
            String text = jtpesofinal.getText();
            if (text.isEmpty() || (text.length() == 1 && Character.isDigit(text.charAt(0)))) {
                if (text.length() >= 11) {
                    evt.consume();
                }
            }
        }
        if (c == '.' && jtpesofinal.getText().contains(".")) {//controla que solo se pueda ingresar un solo punto
            evt.consume();
        }
    }//GEN-LAST:event_jtpesofinalKeyTyped

    private void jtfPorcionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfPorcionKeyTyped
        char c = evt.getKeyChar();
        if (jtfPorcion.getText().length() >= 5) {
            evt.consume();
        }
        if (c == '.' && jtfPorcion.getText().contains(".")) {//controla que solo se pueda ingresar un solo punto
            evt.consume();
        }
    }//GEN-LAST:event_jtfPorcionKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBGuardar;
    private javax.swing.JButton jButtonModif;
    private javax.swing.JCheckBox jCbEstado;
    private javax.swing.JComboBox<Comida> jComboBoxComidas;
    private javax.swing.JComboBox<Horario> jComboBoxHorario;
    private javax.swing.JComboBox<Paciente> jComboPaciente;
    private com.toedter.calendar.JDateChooser jDChoFeInicial;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPaneDetalle;
    private javax.swing.JTable jTablaDetalleDieta;
    private javax.swing.JTable jTablaDietaDispo;
    private javax.swing.JButton jbAgregar;
    private javax.swing.JButton jbEliminar;
    private javax.swing.JButton jbModificar;
    private javax.swing.JButton jbSalir;
    private com.toedter.calendar.JDateChooser jdatechoFechaFinal;
    private javax.swing.JLabel jlEstado;
    private javax.swing.JLabel jlFefinal;
    private javax.swing.JLabel jlFeinicial;
    private javax.swing.JLabel jlNombre;
    private javax.swing.JLabel jlPaciente;
    private javax.swing.JLabel jlPesofinal;
    private javax.swing.JTextField jtfPorcion;
    private javax.swing.JTextField jtnombre;
    private javax.swing.JTextField jtpesofinal;
    // End of variables declaration//GEN-END:variables

    // Supongamos que tu JTable se llama jTable1
    public void listSelection() {

        jTablaDietaDispo.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                actualizarTablaDos();
                // Asegúrate de que no se esté ajustando la selección
                if (!e.getValueIsAdjusting()) {
                    // Obtiene el modelo de la tabla
                    javax.swing.table.TableModel model = jTablaDietaDispo.getModel();

                    // Obtiene el índice de la fila seleccionada
                    int selectedRow = jTablaDietaDispo.getSelectedRow();

                    // Obtiene el valor de la primera columna (columna 0) en la fila seleccionada
                    Object idValue = model.getValueAt(selectedRow, 0);

                    // Convierte el valor a String o al tipo de datos que estés usando para el ID
                    String idString = String.valueOf(idValue);

                    // Haz lo que necesites hacer con el ID
                    System.out.println("ID seleccionado: " + idString);

                    ArrayList<DietaComida> buscarPorIdDieta = dieDAO.buscarPorIdDieta((int) idValue, 1);

                    for (DietaComida i : buscarPorIdDieta) {
                        mode.addRow(new Object[]{i.getDieta().getIdDieta(), i.getComida().getNombre(), i.getPorcion(), i.getHorario()});
                    }
                    jTablaDetalleDieta.setModel(mode);

                }

            }

        });
    }

//                    filtros
    class FiltraEntrada5 extends DocumentFilter {

        public static final char SOLO_NUMEROS = 'N';
        public static final char SOLO_LETRAS = 'L';
        public static final char NUM_LETRAS = 'M';
        public static final char DEFAULT = '*';

        private char tipoEntrada;
        private int longitudCadena = 0;
        private int longitudActual = 0;

        public FiltraEntrada5() {
            tipoEntrada = DEFAULT;
        }

        public FiltraEntrada5(char tipoEntrada) {
            this.tipoEntrada = tipoEntrada;
        }

        public FiltraEntrada5(char tipoEntrada, int longitudCadena) {
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

    class NumericRangeFilter5 extends DocumentFilter {

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
