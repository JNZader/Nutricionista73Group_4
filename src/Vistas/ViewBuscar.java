package Vistas;

import Conexion.ComidaDAO;
import Conexion.ConsultaDAO;
import Conexion.DietaComidaDAO;
import Conexion.DietaDAO;
import Conexion.PacienteDAO;
import Entidades.Comida;
import Entidades.Consulta;
import Entidades.Dieta;
import Entidades.DietaComida;
import Entidades.Horario;
import Entidades.Paciente;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

public class ViewBuscar extends javax.swing.JPanel {

    private String entidad, atributo, atributoTF;
    private int estado, tf;
    private DefaultTableModel modDieta, modPaciente, modComida, modConsulta, modDietaComida;
    private ComidaDAO comidaDAO;
    private DietaDAO dietaDAO;
    private PacienteDAO pacienteDAO;
    private ConsultaDAO consultaDAO;
    private DietaComidaDAO dietaComidaDAO;
    private ArrayList<Consulta> consultas;
    private ArrayList<Comida> comidas;
    private ArrayList<Object> lista;
    private ArrayList<Dieta> dietas;
    private ArrayList<DietaComida> dietaComidas;
    private ArrayList<Paciente> pacientes;
    private DocumentFilter filtroNumeros;
    private DocumentFilter filtroLetras;
    private DocumentFilter filtroMix;
    private NumericRangeFilter3 rangeFilterCel;
    private NumericRangeFilter rangeFilterPeso;
    private NumericRangeFilter2 rangeFilterCal;
    public static Paciente pct;
    public static Dieta diet;
    public static Consulta consul;
    public static Comida com;
    public static DietaComida dietCom;

    public ViewBuscar() {
        initComponents();
        rangeFilterCel = new NumericRangeFilter3();
        rangeFilterCal = new NumericRangeFilter2();
        rangeFilterPeso = new NumericRangeFilter();
        filtroNumeros = new FiltraEntrada(FiltraEntrada.SOLO_NUMEROS);
        filtroLetras = new FiltraEntrada(FiltraEntrada.SOLO_LETRAS);
        filtroMix = new FiltraEntrada(FiltraEntrada.NUM_LETRAS);

        jComboBoxAtribSelect.setEnabled(false);
        jDateChooser1.setEnabled(false);
        jButtonBuscar.setEnabled(false);
        jButtonEditar.setEnabled(false);
        jButtonEliminar.setEnabled(false);
        jRadioButtonActivo.setSelected(true);
        jButtonAnular.setEnabled(false);
        jTextField1.setEditable(false);
        ((JTextFieldDateEditor) jDateChooser1.getDateEditor()).setEditable(false);
        jComboBoxAtributos.addItemListener(event -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                jLabelVariable.setText(event.getItem().toString() + ":");
            }//lambda que agrega un itemlistener al combobox  y modifica el label de acuerdo al item seleccionado en el combo
        });

        JTableHeader tbh = jTable1.getTableHeader();
        tbh.setReorderingAllowed(false);
        jTable1.setTableHeader(tbh);

        ListSelectionModel selectionModel = jTable1.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        jTextField1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent de) {
                jButtonBuscar.setEnabled(true);
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                jButtonBuscar.setEnabled(false);
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }

    private void limpiarTabla() {
        DefaultTableModel mod = (DefaultTableModel) jTable1.getModel();
        mod.setRowCount(0);
    }

    private void llenarTabla(ArrayList<Object> list, String tipo) {
        switch (tipo) {
            case "Consulta":
                modConsulta = new DefaultTableModel() {
                    @Override
                    public boolean isCellEditable(int i, int i1) {
                        return false;
                    }
                };
                modConsulta.setColumnIdentifiers(new String[]{"Objeto", "ID", "Nombre Paciente", "Peso actual"});

                if (list != null && !list.isEmpty()) {
                    for (Object oaux : list) {
                        if (oaux instanceof Consulta) {
                            Consulta aux = (Consulta) oaux;
                            Object[] filas = new Object[4];
                            filas[0] = aux;
                            filas[1] = aux.getIdConsulta();
                            filas[2] = aux.getPaciente().getNombre();
                            filas[3] = aux.getPesoActual();
                            modConsulta.addRow(filas);
                        }
                    }
                }
                jTable1.setModel(modConsulta);
                for (int i = 0; i < jTable1.getColumnCount(); i++) {
                    jTable1.getTableHeader().getColumnModel().getColumn(i).setResizable(false);
                }
                jTable1.getColumnModel().getColumn(0).setMinWidth(0);
                jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
                jTable1.getColumnModel().getColumn(0).setWidth(0);
                jTable1.getColumnModel().getColumn(1).setMinWidth(30);
                jTable1.getColumnModel().getColumn(1).setMaxWidth(30);
                jTable1.getColumnModel().getColumn(1).setWidth(30);
                jTable1.getColumnModel().getColumn(3).setMinWidth(100);
                jTable1.getColumnModel().getColumn(3).setMaxWidth(100);
                jTable1.getColumnModel().getColumn(3).setWidth(100);
                break;

            case "Dieta":
                modDieta = new DefaultTableModel() {
                    @Override
                    public boolean isCellEditable(int i, int i1) {
                        return false;
                    }
                };
                modDieta.setColumnIdentifiers(new String[]{"Objeto", "ID", "Nombre", "Paciente", "Fecha de inicio", "Fecha de Fin", "Peso final", "Estado"});

                if (list != null && !list.isEmpty()) {
                    for (Object oaux : list) {
                        if (oaux instanceof Dieta) {
                            Dieta aux = (Dieta) oaux;
                            Object[] filas = new Object[8];
                            filas[0] = aux;
                            filas[1] = aux.getIdDieta();
                            filas[2] = aux.getNombre();
                            filas[3] = aux.getPaciente();
                            filas[4] = aux.getFechaInicial();
                            filas[5] = aux.getFechaFinal();
                            filas[6] = aux.getPesoFinal();
                            if (aux.isEstado()) {
                                filas[7] = "Activo";
                            } else {
                                filas[7] = "Inactivo";
                            }
                            modDieta.addRow(filas);
                        }
                    }
                }
                jTable1.setModel(modDieta);
                for (int i = 0; i < jTable1.getColumnCount(); i++) {
                    jTable1.getTableHeader().getColumnModel().getColumn(i).setResizable(false);
                }
                jTable1.getColumnModel().getColumn(0).setMinWidth(0);
                jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
                jTable1.getColumnModel().getColumn(0).setWidth(0);
                jTable1.getColumnModel().getColumn(1).setMinWidth(30);
                jTable1.getColumnModel().getColumn(1).setMaxWidth(30);
                jTable1.getColumnModel().getColumn(1).setWidth(30);
                jTable1.getColumnModel().getColumn(6).setMinWidth(100);
                jTable1.getColumnModel().getColumn(6).setMaxWidth(100);
                jTable1.getColumnModel().getColumn(6).setWidth(100);
                jTable1.getColumnModel().getColumn(7).setMinWidth(50);
                jTable1.getColumnModel().getColumn(7).setMaxWidth(50);
                jTable1.getColumnModel().getColumn(7).setWidth(50);
                break;

            case "Paciente":
                modPaciente = new DefaultTableModel() {
                    @Override
                    public boolean isCellEditable(int i, int i1) {
                        return false;
                    }
                };
                modPaciente.setColumnIdentifiers(new String[]{"Objeto", "ID", "Nombre", "DNI", "Telefono", "Domicilio", "Peso Actual", "Estado"});

                if (list != null && !list.isEmpty()) {
                    for (Object oaux : list) {
                        if (oaux instanceof Paciente) {
                            Paciente aux = (Paciente) oaux;
                            Object[] filas = new Object[8];
                            filas[0] = aux;
                            filas[1] = aux.getIdPaciente();
                            filas[2] = aux.getNombre();
                            filas[3] = aux.getDni();
                            filas[4] = aux.getTelefono();
                            filas[5] = aux.getDomicilio();
                            filas[6] = aux.getPesoActual();
                            if (aux.isEstado()) {
                                filas[7] = "Activo";
                            } else {
                                filas[7] = "Inactivo";
                            }
                            modPaciente.addRow(filas);
                        }
                    }
                }
                jTable1.setModel(modPaciente);
                for (int i = 0; i < jTable1.getColumnCount(); i++) {
                    jTable1.getTableHeader().getColumnModel().getColumn(i).setResizable(false);
                }
                jTable1.getColumnModel().getColumn(0).setMinWidth(0);
                jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
                jTable1.getColumnModel().getColumn(0).setWidth(0);
                jTable1.getColumnModel().getColumn(1).setMinWidth(30);
                jTable1.getColumnModel().getColumn(1).setMaxWidth(30);
                jTable1.getColumnModel().getColumn(1).setWidth(30);
                jTable1.getColumnModel().getColumn(6).setMinWidth(100);
                jTable1.getColumnModel().getColumn(6).setMaxWidth(100);
                jTable1.getColumnModel().getColumn(6).setWidth(100);
                jTable1.getColumnModel().getColumn(7).setMinWidth(50);
                jTable1.getColumnModel().getColumn(7).setMaxWidth(50);
                jTable1.getColumnModel().getColumn(7).setWidth(50);
                break;
            case "Comida":
                limpiarTabla();
                modComida = new DefaultTableModel() {
                    @Override
                    public boolean isCellEditable(int i, int i1) {
                        return false;
                    }
                };
                modComida.setColumnIdentifiers(new String[]{"Objeto", "ID", "Nombre", "Detalle", "Cantidad de calorias", "Estado"});
                if (list != null && !list.isEmpty()) {
                    for (Object oaux : list) {
                        if (oaux instanceof Comida) {
                            Comida aux = (Comida) oaux;
                            Object[] filas = new Object[6];
                            filas[0] = aux;
                            filas[1] = aux.getIdComida();
                            filas[2] = aux.getNombre();
                            filas[3] = aux.getDetalle();
                            filas[4] = aux.getCantCalorias();
                            if (aux.isEstado()) {
                                filas[5] = "Activo";
                            } else {
                                filas[5] = "Inactivo";
                            }
                            modComida.addRow(filas);
                        }
                    }
                }

                jTable1.setModel(modComida);
                for (int i = 0; i < jTable1.getColumnCount(); i++) {
                    jTable1.getTableHeader().getColumnModel().getColumn(i).setResizable(false);
                }
                jTable1.getColumnModel().getColumn(0).setMinWidth(0);
                jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
                jTable1.getColumnModel().getColumn(0).setWidth(0);
                jTable1.getColumnModel().getColumn(1).setMinWidth(30);
                jTable1.getColumnModel().getColumn(1).setMaxWidth(30);
                jTable1.getColumnModel().getColumn(1).setWidth(30);
                jTable1.getColumnModel().getColumn(4).setMinWidth(150);
                jTable1.getColumnModel().getColumn(4).setMaxWidth(150);
                jTable1.getColumnModel().getColumn(4).setWidth(150);
                jTable1.getColumnModel().getColumn(5).setMinWidth(50);
                jTable1.getColumnModel().getColumn(5).setMaxWidth(50);
                jTable1.getColumnModel().getColumn(5).setWidth(50);
                break;
            case "DietaComida":
                limpiarTabla();
                modDietaComida = new DefaultTableModel() {
                    @Override
                    public boolean isCellEditable(int i, int i1) {
                        return false;
                    }
                };
                modDietaComida.setColumnIdentifiers(new String[]{"", "ID", "Comida", "Tratamiento", "Porcion", "Horario", "Estado"});
                if (list != null && !list.isEmpty()) {
                    for (Object oaux : list) {
                        if (oaux instanceof DietaComida) {
                            DietaComida aux = (DietaComida) oaux;
                            Object[] filas = new Object[7];
                            filas[0] = aux;
                            filas[1] = aux.getId();
                            filas[2] = aux.getComida();
                            filas[3] = aux.getDieta();
                            filas[4] = aux.getPorcion();
                            filas[5] = aux.getHorario();
                            if (aux.isEstado()) {
                                filas[6] = "Activo";
                            } else {
                                filas[5] = "Inactivo";
                            }
                            modDietaComida.addRow(filas);
                        }
                    }
                }
                jTable1.setModel(modDietaComida);
                for (int i = 0; i < jTable1.getColumnCount(); i++) {
                    jTable1.getTableHeader().getColumnModel().getColumn(i).setResizable(false);
                }
                jTable1.getColumnModel().getColumn(0).setMinWidth(0);
                jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
                jTable1.getColumnModel().getColumn(0).setWidth(0);
                jTable1.getColumnModel().getColumn(1).setMinWidth(40);
                jTable1.getColumnModel().getColumn(1).setMaxWidth(40);
                jTable1.getColumnModel().getColumn(1).setWidth(40);
                jTable1.getColumnModel().getColumn(6).setMinWidth(50);
                jTable1.getColumnModel().getColumn(6).setMaxWidth(50);
                jTable1.getColumnModel().getColumn(6).setWidth(50);
                break;
            default:
                System.out.println("Opcion no valida");
        }
    }

    private void llenarTabla(DietaComida dietaComida) {
        limpiarTabla();
        modDietaComida = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };
        modDietaComida.setColumnIdentifiers(new String[]{"", "ID", "Comida", "Tratamiento", "Porcion", "Horario", "Estado"});

        if (dietaComida != null) {
            Object[] filas = new Object[7];
            filas[0] = dietaComida;
            filas[1] = dietaComida.getId();
            filas[2] = dietaComida.getComida();
            filas[3] = dietaComida.getDieta();
            filas[4] = dietaComida.getPorcion();
            filas[5] = dietaComida.getHorario();
            if (dietaComida.isEstado()) {
                filas[6] = "Activo";
            } else {
                filas[5] = "Inactivo";
            }
            modDietaComida.addRow(filas);;
        }
        jTable1.setModel(modDietaComida);
        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            jTable1.getTableHeader().getColumnModel().getColumn(i).setResizable(false);
        }
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(0).setWidth(0);
        jTable1.getColumnModel().getColumn(1).setMinWidth(40);
        jTable1.getColumnModel().getColumn(1).setMaxWidth(40);
        jTable1.getColumnModel().getColumn(1).setWidth(40);
        jTable1.getColumnModel().getColumn(6).setMinWidth(50);
        jTable1.getColumnModel().getColumn(6).setMaxWidth(50);
        jTable1.getColumnModel().getColumn(6).setWidth(50);
    }

    private void llenarTabla(Comida comida) {
        limpiarTabla();
        modComida = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };
        modComida.setColumnIdentifiers(new String[]{"Objeto", "ID", "Nombre", "Detalle", "Cantidad de calorias", "Estado"});

        if (comida != null) {
            Object[] filas = new Object[6];
            filas[0] = comida;
            filas[1] = comida.getIdComida();
            filas[2] = comida.getNombre();
            filas[3] = comida.getDetalle();
            filas[4] = comida.getCantCalorias();
            if (comida.isEstado()) {
                filas[5] = "Activo";
            } else {
                filas[5] = "Inactivo";
            }
            modComida.addRow(filas);
        }

        jTable1.setModel(modComida);
        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            jTable1.getTableHeader().getColumnModel().getColumn(i).setResizable(false);
        }
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(0).setWidth(0);
        jTable1.getColumnModel().getColumn(1).setMinWidth(30);
        jTable1.getColumnModel().getColumn(1).setMaxWidth(30);
        jTable1.getColumnModel().getColumn(1).setWidth(30);
        jTable1.getColumnModel().getColumn(4).setMinWidth(150);
        jTable1.getColumnModel().getColumn(4).setMaxWidth(150);
        jTable1.getColumnModel().getColumn(4).setWidth(150);
        jTable1.getColumnModel().getColumn(5).setMinWidth(50);
        jTable1.getColumnModel().getColumn(5).setMaxWidth(50);
        jTable1.getColumnModel().getColumn(5).setWidth(50);

    }

    private void llenarTabla(Consulta consulta) {
        limpiarTabla();
        modConsulta = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };
        modConsulta.setColumnIdentifiers(new String[]{"Objeto", "ID", "Nombre Paciente", "Peso actual"});

        if (consulta != null) {
            Object[] filas = new Object[4];
            filas[0] = consulta;
            filas[1] = consulta.getIdConsulta();
            filas[2] = consulta.getPaciente().getNombre();
            filas[3] = consulta.getPesoActual();
            modConsulta.addRow(filas);
        }

        jTable1.setModel(modConsulta);
        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            jTable1.getTableHeader().getColumnModel().getColumn(i).setResizable(false);
        }
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(0).setWidth(0);
        jTable1.getColumnModel().getColumn(1).setMinWidth(30);
        jTable1.getColumnModel().getColumn(1).setMaxWidth(30);
        jTable1.getColumnModel().getColumn(1).setWidth(30);
        jTable1.getColumnModel().getColumn(3).setMinWidth(100);
        jTable1.getColumnModel().getColumn(3).setMaxWidth(100);
        jTable1.getColumnModel().getColumn(3).setWidth(100);

    }

    private void llenarTabla(Dieta dieta) {
        limpiarTabla();
        modDieta = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };
        modDieta.setColumnIdentifiers(new String[]{"Objeto", "ID", "Nombre", "Paciente", "Fecha de inicio", "Fecha de Fin", "Peso final", "Estado"});

        if (dieta != null) {
            Object[] filas = new Object[8];
            filas[0] = dieta;
            filas[1] = dieta.getIdDieta();
            filas[2] = dieta.getNombre();
            filas[3] = dieta.getPaciente();
            filas[4] = dieta.getFechaInicial();
            filas[5] = dieta.getFechaFinal();
            filas[6] = dieta.getPesoFinal();
            if (dieta.isEstado()) {
                filas[7] = "Activo";
            } else {
                filas[7] = "Inactivo";
            }
            modDieta.addRow(filas);
        }

        jTable1.setModel(modDieta);
        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            jTable1.getTableHeader().getColumnModel().getColumn(i).setResizable(false);
        }
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(0).setWidth(0);
        jTable1.getColumnModel().getColumn(1).setMinWidth(30);
        jTable1.getColumnModel().getColumn(1).setMaxWidth(30);
        jTable1.getColumnModel().getColumn(1).setWidth(30);
        jTable1.getColumnModel().getColumn(7).setMinWidth(50);
        jTable1.getColumnModel().getColumn(7).setMaxWidth(50);
        jTable1.getColumnModel().getColumn(7).setWidth(50);
    }

    private void llenarTabla(Paciente paciente) {
        limpiarTabla();
        modPaciente = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };
        modPaciente.setColumnIdentifiers(new String[]{"Objeto", "ID", "Nombre", "DNI", "Telefono", "Domicilio", "Peso Actual", "Estado"});

        if (paciente != null) {
            Object[] filas = new Object[8];
            filas[0] = paciente;
            filas[1] = paciente.getIdPaciente();
            filas[2] = paciente.getNombre();
            filas[3] = paciente.getDni();
            filas[4] = paciente.getTelefono();
            filas[5] = paciente.getDomicilio();
            filas[6] = paciente.getPesoActual();
            if (paciente.isEstado()) {
                filas[7] = "Activo";
            } else {
                filas[7] = "Inactivo";
            }
            modPaciente.addRow(filas);
        }

        jTable1.setModel(modPaciente);
        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            jTable1.getTableHeader().getColumnModel().getColumn(i).setResizable(false);
        }
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(0).setWidth(0);
        jTable1.getColumnModel().getColumn(1).setMinWidth(30);
        jTable1.getColumnModel().getColumn(1).setMaxWidth(30);
        jTable1.getColumnModel().getColumn(1).setWidth(30);
        jTable1.getColumnModel().getColumn(7).setMinWidth(50);
        jTable1.getColumnModel().getColumn(7).setMaxWidth(50);
        jTable1.getColumnModel().getColumn(7).setWidth(50);

    }

    private void llenarComboBox() {
        jComboBoxAtribSelect.removeAllItems();
        jComboBoxAtribSelect.addItem(null);

        String atributoSeleccionado = jComboBoxAtributos.getSelectedItem().toString();

        switch (atributoSeleccionado) {
            case "Paciente":
                pacienteDAO = new PacienteDAO();
                pacientes = pacienteDAO.listarPaciente(1);
                for (Paciente aux : pacientes) {
                    jComboBoxAtribSelect.addItem(aux);
                }
                break;
            case "Comida":
                comidaDAO = new ComidaDAO();
                comidas = comidaDAO.listarComidas(1);
                for (Comida aux : comidas) {
                    jComboBoxAtribSelect.addItem(aux);
                }
                break;
            case "Tratamiento":
                dietaDAO = new DietaDAO();
                dietas = dietaDAO.buscar(1);
                for (Dieta aux : dietas) {
                    jComboBoxAtribSelect.addItem(aux);
                }
                break;
            case "Horario":
                for (Horario aux : Horario.values()) {
                    jComboBoxAtribSelect.addItem(aux.toString());
                }
                break;
        }
    }

    public void actualizarBotones() {
        if (jComboBoxEntidades.getSelectedIndex() > 0
                && jComboBoxAtributos.getSelectedIndex() > 0
                && (jComboBoxAtributos.getSelectedItem() == null
                || (!jComboBoxAtributos.getSelectedItem().toString().equals("Paciente")
                && !jComboBoxAtributos.getSelectedItem().toString().equals("Horario")
                && !jComboBoxAtributos.getSelectedItem().toString().equals("Comida")
                && !jComboBoxAtributos.getSelectedItem().toString().equals("Tratamiento"))
                || (jComboBoxAtribSelect.getSelectedItem() != null))) {
            jButtonBuscar.setEnabled(true);
            if (jTable1.getSelectedRow() != -1 && jTable1.getSelectedRow() < jTable1.getModel().getRowCount()) {
                jButtonEditar.setEnabled(true);
                jButtonEliminar.setEnabled(true);
                if (!jComboBoxEntidades.getSelectedItem().toString().equals("Consultas")) {
                    jButtonAnular.setEnabled(true);
                }
            }
        } else {
            jButtonBuscar.setEnabled(false);
            jButtonEditar.setEnabled(false);
            jButtonEliminar.setEnabled(false);
            jButtonAnular.setEnabled(false);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jComboBoxEntidades = new javax.swing.JComboBox<>();
        jComboBoxAtributos = new javax.swing.JComboBox<>();
        jRadioButtonActivo = new javax.swing.JRadioButton();
        jRadioButtonInactivo = new javax.swing.JRadioButton();
        jRadioButtonAmbos = new javax.swing.JRadioButton();
        jButtonBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButtonEditar = new javax.swing.JButton();
        jButtonEliminar = new javax.swing.JButton();
        jButtonSalir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabelVariable = new javax.swing.JLabel();
        jComboBoxAtribSelect = new javax.swing.JComboBox<>();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jButtonAnular = new javax.swing.JButton();

        setBackground(new java.awt.Color(180, 220, 160));
        setMaximumSize(new java.awt.Dimension(840, 690));
        setMinimumSize(new java.awt.Dimension(840, 690));
        setPreferredSize(new java.awt.Dimension(840, 690));

        jComboBoxEntidades.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Comidas", "Consultas", "Tratamientos", "Pacientes", "Dietas" }));
        jComboBoxEntidades.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxEntidadesItemStateChanged(evt);
            }
        });
        jComboBoxEntidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxEntidadesActionPerformed(evt);
            }
        });

        jComboBoxAtributos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxAtributosItemStateChanged(evt);
            }
        });
        jComboBoxAtributos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxAtributosActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButtonActivo);
        jRadioButtonActivo.setText("Activo");

        buttonGroup1.add(jRadioButtonInactivo);
        jRadioButtonInactivo.setText("Inactivo");

        buttonGroup1.add(jRadioButtonAmbos);
        jRadioButtonAmbos.setText("Ambos");

        jButtonBuscar.setBackground(new java.awt.Color(150, 200, 130));
        jButtonBuscar.setText("Buscar");
        jButtonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTable1MouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButtonEditar.setBackground(new java.awt.Color(150, 200, 130));
        jButtonEditar.setText("Editar");
        jButtonEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditarActionPerformed(evt);
            }
        });

        jButtonEliminar.setBackground(new java.awt.Color(150, 200, 130));
        jButtonEliminar.setText("Eliminar");
        jButtonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarActionPerformed(evt);
            }
        });

        jButtonSalir.setBackground(new java.awt.Color(150, 200, 130));
        jButtonSalir.setText("Salir");
        jButtonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalirActionPerformed(evt);
            }
        });

        jLabel1.setText("Buscar:");

        jLabel2.setText("Atributo:");

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });

        jLabelVariable.setText("                ");

        jComboBoxAtribSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxAtribSelectActionPerformed(evt);
            }
        });

        jDateChooser1.setMaxSelectableDate(new java.util.Date(1735704071000L));
        jDateChooser1.setMinSelectableDate(new java.util.Date(1577851271000L));

        jButtonAnular.setBackground(new java.awt.Color(150, 200, 130));
        jButtonAnular.setText("Anular");
        jButtonAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAnularActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonEditar)
                        .addGap(130, 130, 130)
                        .addComponent(jButtonEliminar)
                        .addGap(166, 166, 166)
                        .addComponent(jButtonAnular)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 180, Short.MAX_VALUE)
                        .addComponent(jButtonSalir))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jRadioButtonActivo)
                        .addGap(73, 73, 73)
                        .addComponent(jRadioButtonInactivo)
                        .addGap(95, 95, 95)
                        .addComponent(jRadioButtonAmbos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBoxEntidades, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addGap(53, 53, 53)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBoxAtributos, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(128, 128, 128)
                                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(jComboBoxAtribSelect, 0, 200, Short.MAX_VALUE)
                            .addComponent(jLabelVariable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(41, 41, 41))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jComboBoxAtributos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jComboBoxEntidades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelVariable)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jComboBoxAtribSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonBuscar)
                    .addComponent(jRadioButtonActivo)
                    .addComponent(jRadioButtonInactivo)
                    .addComponent(jRadioButtonAmbos))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonEditar)
                    .addComponent(jButtonEliminar)
                    .addComponent(jButtonSalir)
                    .addComponent(jButtonAnular))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxEntidadesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxEntidadesItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            if (this.jComboBoxEntidades.getSelectedIndex() > 0) {
                this.jComboBoxAtributos.setModel(new DefaultComboBoxModel<>(atributos(this.jComboBoxEntidades.getSelectedItem().toString())));
                jLabelVariable.setText(":");
            }
            if (this.jComboBoxEntidades.getSelectedIndex() == 0) {
                this.jComboBoxAtributos.setModel(new DefaultComboBoxModel<>(atributos("")));
            }
        }
        jComboBoxAtribSelect.removeAllItems();
        jComboBoxAtribSelect.setEnabled(false);

        actualizarBotones();
        limpiarTabla();
    }//GEN-LAST:event_jComboBoxEntidadesItemStateChanged
  
    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        entidad = jComboBoxEntidades.getSelectedItem().toString();
        atributo = jComboBoxAtributos.getSelectedItem().toString();
        atributoTF = jTextField1.getText();
        estado = (jRadioButtonActivo.isSelected()) ? 1 : (jRadioButtonInactivo.isSelected()) ? 0 : (jRadioButtonAmbos.isSelected()) ? 3 : -1;
        if ("".equals(entidad) && "".equals(atributo) && "".equals(atributoTF) && !entidad.isEmpty() && !atributo.isEmpty() && !atributoTF.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione datos validos");
        } else {
            if (entidad.equalsIgnoreCase("Comidas")) {
                switch (atributo) {
                    case "ID":
                        if (atributoTF != null && !atributoTF.isEmpty() && !atributoTF.equalsIgnoreCase("")) {
                            tf = Integer.parseInt(atributoTF);
                            comidaDAO = new ComidaDAO();
                            llenarTabla(comidaDAO.buscar(tf, estado));
                        } else {
                            comidaDAO = new ComidaDAO();
                            comidas = comidaDAO.listarComidas(estado);
                            lista = new ArrayList<>(comidas);
                            llenarTabla(lista, "Comida");
                        }
                        break;
                    case "Nombre":
                        if (!jTextField1.getText().isEmpty()) {
                            comidaDAO = new ComidaDAO();
                            llenarTabla(comidaDAO.buscarPorNombre(atributoTF, estado));
                        } else {
                            JOptionPane.showMessageDialog(this, "Ingrese un nombre a buscar", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case "Detalle":
                        comidaDAO = new ComidaDAO();
                        comidas = comidaDAO.buscarPorDetalle(atributoTF, estado);
                        lista = new ArrayList<>(comidas);
                        llenarTabla(lista, "Comida");
                        break;
                    case "Cantidad de calorias":
                        if (!jTextField1.getText().isEmpty()) {
                            comidaDAO = new ComidaDAO();
                            estado = (jRadioButtonActivo.isSelected()) ? 1 : (jRadioButtonInactivo.isSelected()) ? 0 : (jRadioButtonAmbos.isSelected()) ? -1 : -3;
                            comidas = comidaDAO.buscarXCantCalorias(Integer.parseInt(jTextField1.getText()), estado);
                            lista = new ArrayList<>(comidas);
                            llenarTabla(lista, "Comida");
                        } else {
                            JOptionPane.showMessageDialog(this, "Ingrese la cantidad de calorias a buscar", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case "Estado":
                        comidaDAO = new ComidaDAO();
                        comidas = comidaDAO.listarComidas(estado);
                        lista = new ArrayList<>(comidas);
                        llenarTabla(lista, "Comida");
                        break;
                }
            } else if (entidad.equalsIgnoreCase("Consultas")) {
                switch (atributo) {
                    case "ID"://id consulta
                        if (atributoTF != null && !atributoTF.isEmpty() && !atributoTF.equalsIgnoreCase("")) {
                            tf = Integer.parseInt(atributoTF);
                            consultaDAO = new ConsultaDAO();
                            llenarTabla(consultaDAO.buscar(tf));
                        } else {
                            consultaDAO = new ConsultaDAO();
                            consultas = consultaDAO.buscar();
                            lista = new ArrayList<>(consultas);
                            llenarTabla(lista, "Consulta");
                        }
                        break;
                    case "Paciente":
                        consultaDAO = new ConsultaDAO();
                        consultas = consultaDAO.buscar((Paciente) jComboBoxAtribSelect.getSelectedItem());
                        lista = new ArrayList<>(consultas);
                        llenarTabla(lista, "Consulta");
                        break;
                    case "Fecha":
                        consultaDAO = new ConsultaDAO();
                        estado = (jRadioButtonActivo.isSelected()) ? 1 : (jRadioButtonInactivo.isSelected()) ? 0 : (jRadioButtonAmbos.isSelected()) ? -1 : -3;
                        if (jDateChooser1.getDate() != null) {
                            LocalDate fecha = jDateChooser1.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                            consultas = consultaDAO.buscarPorFecha(fecha, estado);
                            lista = new ArrayList<>(consultas);
                            llenarTabla(lista, "Consulta");
                        } else {
                            JOptionPane.showMessageDialog(this, "Ingrese una fecha valida", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case "Peso actual":
                        if (!jTextField1.getText().isEmpty()) {
                            double tfp = Double.parseDouble(atributoTF);
                            consultaDAO = new ConsultaDAO();
                            estado = (jRadioButtonActivo.isSelected()) ? 1 : (jRadioButtonInactivo.isSelected()) ? 0 : (jRadioButtonAmbos.isSelected()) ? -1 : 0;
                            consultas = consultaDAO.buscarPorPesoActual(tfp, estado);
                            lista = new ArrayList<>(consultas);
                            llenarTabla(lista, "Consulta");
                        } else {
                            JOptionPane.showMessageDialog(this, "Ingrese el peso a buscar", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                }
            } else if (entidad.equalsIgnoreCase("Tratamientos")) {
                switch (atributo) {
                    case "ID":
                        if (atributoTF != null && !atributoTF.isEmpty() && !atributoTF.equalsIgnoreCase("")) {
                            tf = Integer.parseInt(atributoTF);
                            dietaDAO = new DietaDAO();
                            llenarTabla(dietaDAO.buscarPorId(tf, estado));
                        } else {
                            dietaDAO = new DietaDAO();
                            dietas = dietaDAO.buscar(estado);
                            lista = new ArrayList<>(dietas);
                            llenarTabla(lista, "Dieta");
                        }
                        break;
                    case "Nombre":
                        dietaDAO = new DietaDAO();
                        dietas = dietaDAO.buscarDietasPorNombre(atributoTF, estado);
                        lista = new ArrayList<>(dietas);
                        llenarTabla(lista, "Dieta");
                        break;
                    case "Paciente":
                        Paciente p = (Paciente) jComboBoxAtribSelect.getSelectedItem();
                        dietaDAO = new DietaDAO();
                        llenarTabla(new ArrayList<>(dietaDAO.buscarDietasPorPaciente(p.getIdPaciente(), estado)), "Dieta");
                        break;
                    case "Fecha Inicial"://falta x modif vista
                        dietaDAO = new DietaDAO();
                        if (jDateChooser1.getDate() != null) {
                            LocalDate fechaInicial = jDateChooser1.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                            dietas = dietaDAO.buscarDietasPorFecha(fechaInicial, estado, true);
                            lista = new ArrayList<>(dietas);
                            llenarTabla(lista, "Dieta");
                        } else {
                            JOptionPane.showMessageDialog(this, "Ingrese una fecha valida", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case "Fecha Final":
                        dietaDAO = new DietaDAO();
                        if (jDateChooser1.getDate() != null) {
                            LocalDate fechaFinal = jDateChooser1.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                            dietas = dietaDAO.buscarDietasPorFecha(fechaFinal, estado, false);
                            lista = new ArrayList<>(dietas);
                            llenarTabla(lista, "Dieta");
                        } else {
                            JOptionPane.showMessageDialog(this, "Ingrese una fecha valida", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case "Peso Final":
                        dietaDAO = new DietaDAO();
                        if (!jTextField1.getText().isEmpty()) {
                            estado = (jRadioButtonActivo.isSelected()) ? 1 : (jRadioButtonInactivo.isSelected()) ? 0 : (jRadioButtonAmbos.isSelected()) ? -1 : 0;
                            double tfp = Double.parseDouble(atributoTF);
                            dietas = dietaDAO.buscarDietasPorPesoFinal(tfp, estado);
                            lista = new ArrayList<>(dietas);
                            llenarTabla(lista, "Dieta");
                        } else {
                            JOptionPane.showMessageDialog(this, "Ingrese el peso final a buscar", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case "Estado":
                        dietaDAO = new DietaDAO();
                        dietas = dietaDAO.buscar(estado);
                        lista = new ArrayList<>(dietas);
                        llenarTabla(lista, "Dieta");
                        break;
                }
            } else if (entidad.equalsIgnoreCase("Pacientes")) {
                switch (atributo) {
                    case "ID":
                        if (atributoTF != null && !atributoTF.isEmpty() && !atributoTF.equalsIgnoreCase("")) {
                            tf = Integer.parseInt(atributoTF);
                            pacienteDAO = new PacienteDAO();
                            llenarTabla(pacienteDAO.buscarPaciente(tf, estado));
                        } else {
                            pacienteDAO = new PacienteDAO();
                            pacientes = pacienteDAO.listarPaciente(estado);
                            lista = new ArrayList<>(pacientes);
                            llenarTabla(lista, "Paciente");
                        }
                        break;
                    case "Nombre Completo":
                        if (atributoTF != null && !atributoTF.isEmpty() && !atributoTF.equalsIgnoreCase("")) {
                            pacienteDAO = new PacienteDAO();
                            pacientes = pacienteDAO.buscarPacientesPorNombre(atributoTF, estado);
                            lista = new ArrayList<>(pacientes);
                            llenarTabla(lista, "Paciente");
                        } else {
                            JOptionPane.showMessageDialog(this, "Ingrese un nombre a buscar", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case "Domicilio":
                        if (atributoTF != null && !atributoTF.isEmpty() && !atributoTF.equalsIgnoreCase("")) {
                            pacienteDAO = new PacienteDAO();
                            pacientes = pacienteDAO.buscarPacientesPorDomicilio(atributoTF, estado);
                            lista = new ArrayList<>(pacientes);
                            llenarTabla(lista, "Paciente");
                        } else {
                            JOptionPane.showMessageDialog(this, "Ingrese un domicilio a buscar", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case "DNI":
                        if (atributoTF != null && !atributoTF.isEmpty() && !atributoTF.equalsIgnoreCase("")) {
                            tf = Integer.parseInt(atributoTF);
                            pacienteDAO = new PacienteDAO();
                            llenarTabla(pacienteDAO.buscarPacientePorDni(tf, estado));
                        }
                        break;
                    case "Peso actual":
                        if (!jTextField1.getText().isEmpty()) {
                            double tfp = Double.parseDouble(atributoTF);
                            pacienteDAO = new PacienteDAO();
                            estado = (jRadioButtonActivo.isSelected()) ? 1 : (jRadioButtonInactivo.isSelected()) ? 0 : (jRadioButtonAmbos.isSelected()) ? -1 : 0;
                            pacientes = pacienteDAO.buscarPacientesPorPesoActual(tfp, estado);
                            lista = new ArrayList<>(pacientes);
                            llenarTabla(lista, "Paciente");
                        } else {
                            JOptionPane.showMessageDialog(this, "Ingrese el peso actual a buscar", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case "Estado":
                        pacienteDAO = new PacienteDAO();
                        pacientes = pacienteDAO.listarPaciente(estado);
                        lista = new ArrayList<>(pacientes);
                        llenarTabla(lista, "Paciente");
                        break;
                    case "Celular":
                        if (atributoTF != null && !atributoTF.isEmpty() && !atributoTF.equalsIgnoreCase("")) {
                            tf = Integer.parseInt(atributoTF);
                            pacienteDAO = new PacienteDAO();
                            pacientes = pacienteDAO.buscarPacientesPorCelular(tf, estado);
                            lista = new ArrayList<>(pacientes);
                            llenarTabla(lista, "Paciente");
                        } else {
                            pacienteDAO = new PacienteDAO();
                            pacientes = pacienteDAO.listarPaciente(estado);
                            lista = new ArrayList<>(pacientes);
                            llenarTabla(lista, "Paciente");
                        }
                        break;
                }
            } else if (entidad.equalsIgnoreCase("Dietas")) {
                switch (atributo) {
                    case "ID":
                        if (atributoTF != null && !atributoTF.isEmpty() && !atributoTF.equalsIgnoreCase("")) {
                            tf = Integer.parseInt(atributoTF);
                            dietaComidaDAO = new DietaComidaDAO();
                            llenarTabla(dietaComidaDAO.buscarPorId(tf, estado));
                        } else {
                            dietaComidaDAO = new DietaComidaDAO();
                            dietaComidas = dietaComidaDAO.buscar(estado);
                            lista = new ArrayList<>(dietaComidas);
                            llenarTabla(lista, "DietaComida");
                        }
                        break;
                    case "Comida":
                        Comida c = (Comida) jComboBoxAtribSelect.getSelectedItem();
                        dietaComidaDAO = new DietaComidaDAO();
                        dietaComidas = dietaComidaDAO.buscarPorIdComida(c.getIdComida(), estado);
                        lista = new ArrayList<>(dietaComidas);
                        llenarTabla(lista, "DietaComida");
                        break;
                    case "Tratamiento":
                        Dieta d = (Dieta) jComboBoxAtribSelect.getSelectedItem();
                        dietaComidaDAO = new DietaComidaDAO();
                        dietaComidas = dietaComidaDAO.buscarPorIdDieta(d.getIdDieta(), estado);
                        lista = new ArrayList<>(dietaComidas);
                        llenarTabla(lista, "DietaComida");
                        break;
                    case "Porcion":
                        if (!jTextField1.getText().isEmpty()) {
                            int tfp = Integer.parseInt(atributoTF);
                            dietaComidaDAO = new DietaComidaDAO();
                            estado = (jRadioButtonActivo.isSelected()) ? 1 : (jRadioButtonInactivo.isSelected()) ? 0 : (jRadioButtonAmbos.isSelected()) ? -1 : 0;
                            dietaComidas = dietaComidaDAO.buscarPorPorcion(tfp, estado);
                            lista = new ArrayList<>(dietaComidas);
                            llenarTabla(lista, "DietaComida");
                        } else {
                            JOptionPane.showMessageDialog(this, "Ingrese la porcion a buscar", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case "Horario":
                        String h = jComboBoxAtribSelect.getSelectedItem().toString();
                        dietaComidaDAO = new DietaComidaDAO();
                        dietaComidas = dietaComidaDAO.buscarPorHorario(h, estado);
                        lista = new ArrayList<>(dietaComidas);
                        llenarTabla(lista, "DietaComida");
                        break;
                    case "Estado":
                        dietaComidaDAO = new DietaComidaDAO();
                        dietaComidas = dietaComidaDAO.buscar(estado);
                        lista = new ArrayList<>(dietaComidas);
                        llenarTabla(lista, "DietaComida");
                        break;
                }
            }
        }
        actualizarBotones();
    }//GEN-LAST:event_jButtonBuscarActionPerformed

    private void jComboBoxEntidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxEntidadesActionPerformed
        actualizarBotones();
    }//GEN-LAST:event_jComboBoxEntidadesActionPerformed

    private void jComboBoxAtributosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxAtributosActionPerformed
        limpiarTabla();
        actualizarBotones();
    }//GEN-LAST:event_jComboBoxAtributosActionPerformed

    private void jButtonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalirActionPerformed
        Dashboardv2 db = new Dashboardv2();
        this.setVisible(false);
        db.setVisible(true);
    }//GEN-LAST:event_jButtonSalirActionPerformed

    private void jComboBoxAtributosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxAtributosItemStateChanged
        String atributoSelect = (String) jComboBoxAtributos.getSelectedItem();

        jTextField1.setEnabled(true);
        jDateChooser1.setEnabled(false);
        jTextField1.setEditable(true);
        jComboBoxAtribSelect.setEnabled(false);
        jComboBoxAtribSelect.removeAllItems();

        if (atributoSelect.equals("Estado") || atributoSelect.equals("Paciente")) {
            jTextField1.setEnabled(false);
        }

        if (atributoSelect.equals("Cantidad de calorias")
                || atributoSelect.equals("Peso Final")
                || atributoSelect.equals("Fecha")
                || atributoSelect.equals("Porcion")
                || atributoSelect.equals("Peso actual")) {
            jRadioButtonActivo.setText("Mayor a");
            jRadioButtonAmbos.setText("Menor a");
            jRadioButtonInactivo.setText("Igual a");
        } else {
            jRadioButtonActivo.setText("Activo");
            jRadioButtonAmbos.setText("Ambos");
            jRadioButtonInactivo.setText("Inactivo");
        }

        if (atributoSelect.equals("Fecha Inicial")
                || atributoSelect.equals("Fecha Final")
                || atributoSelect.equals("Fecha")) {
            jTextField1.setEnabled(false);
            jDateChooser1.setEnabled(true);
        }

        if (atributoSelect.equals("Paciente")
                || atributoSelect.equals("Horario")
                || atributoSelect.equals("Tratamiento")
                || atributoSelect.equals("Comida")) {
            jTextField1.setEditable(false);
            jComboBoxAtribSelect.setEnabled(true);
            jComboBoxAtribSelect.removeAllItems();
            llenarComboBox();
        }
        ((AbstractDocument) jTextField1.getDocument()).setDocumentFilter(null);
        jTextField1.setText("");
        switch ((String) jComboBoxAtributos.getSelectedItem()) {
            case "Nombre":
                ((AbstractDocument) jTextField1.getDocument()).setDocumentFilter(filtroLetras);
                break;
            case "Detalle":
            case "Domicilio":
                ((AbstractDocument) jTextField1.getDocument()).setDocumentFilter(filtroMix);
                break;
            case "Cantidad de calorias":
            case "Porcion":
                ((AbstractDocument) jTextField1.getDocument()).setDocumentFilter(rangeFilterCal);
                break;
            case "DNI":
            case "ID":
            case "Celular":
                ((AbstractDocument) jTextField1.getDocument()).setDocumentFilter(rangeFilterCel);
                break;
            case "Peso Inicial":
            case "Peso Final":
            case "Peso actual":
                ((AbstractDocument) jTextField1.getDocument()).setDocumentFilter(rangeFilterPeso);
                break;
            default:
                ((AbstractDocument) jTextField1.getDocument()).setDocumentFilter(null);
                break;
        }
    }//GEN-LAST:event_jComboBoxAtributosItemStateChanged

    private void jComboBoxAtribSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxAtribSelectActionPerformed
        limpiarTabla();
        actualizarBotones();
    }//GEN-LAST:event_jComboBoxAtribSelectActionPerformed

    private void jTable1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseReleased
        actualizarBotones();
    }//GEN-LAST:event_jTable1MouseReleased

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        char c = evt.getKeyChar();
        if (Character.isDigit(c)) {
            String text = jTextField1.getText();
            if (text.isEmpty() || (text.length() == 1 && Character.isDigit(text.charAt(0)))) {
                if (text.length() >= 11) {
                    evt.consume();
                }
            }
        }
        if (Character.isLetter(c)) {
            if (jTextField1.getText().length() >= 100) {
                evt.consume();
            }
        }
        if (Character.isWhitespace(c)) {
            int length = jTextField1.getText().length();
            if (length > 0 && jTextField1.getText().charAt(length - 1) == ' ') {
                evt.consume();
            }
        }
        if (c == '.' && jTextField1.getText().contains(".")) {//controla que solo se pueda ingresar un solo punto
            evt.consume();
        }
    }//GEN-LAST:event_jTextField1KeyTyped

    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
        int selectedRow = jTable1.getSelectedRow();

        if (selectedRow != -1) {
            Object selectedObject = jTable1.getValueAt(selectedRow, 0);

            if (selectedObject instanceof Paciente) {
                Paciente paciente = (Paciente) selectedObject;
                pacienteDAO.eliminarPacienteFisico(paciente.getIdPaciente());
                jButtonBuscarActionPerformed(evt);
            } else if (selectedObject instanceof Dieta) {
                Dieta dieta = (Dieta) selectedObject;
                dietaDAO.eliminarDieta(dieta.getIdDieta());
                jButtonBuscarActionPerformed(evt);
            } else if (selectedObject instanceof Comida) {
                Comida comida = (Comida) selectedObject;
                comidaDAO.borrarTotal(comida);
                jButtonBuscarActionPerformed(evt);
            } else if (selectedObject instanceof Consulta) {
                Consulta consulta = (Consulta) selectedObject;
                consultaDAO.eliminar(consulta.getIdConsulta());
                jButtonBuscarActionPerformed(evt);
            } else if (selectedObject instanceof DietaComida) {
                DietaComida dietaCom = (DietaComida) selectedObject;
                dietaComidaDAO.eliminarDietaComida(dietaCom.getId());
                jButtonBuscarActionPerformed(evt);
            }
        }
    }//GEN-LAST:event_jButtonEliminarActionPerformed

    private void jButtonAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAnularActionPerformed
        int selectedRow = jTable1.getSelectedRow();

        if (selectedRow != -1) {
            Object selectedObject = jTable1.getValueAt(selectedRow, 0);
            if (selectedObject instanceof Paciente) {
                Paciente paciente = (Paciente) selectedObject;
                pacienteDAO.eliminarPacienteLogico(paciente.getIdPaciente());
                jButtonBuscarActionPerformed(evt);
            } else if (selectedObject instanceof Dieta) {
                Dieta dieta = (Dieta) selectedObject;
                dietaDAO.anularDieta(dieta.getIdDieta());
                jButtonBuscarActionPerformed(evt);
            } else if (selectedObject instanceof Comida) {
                Comida comida = (Comida) selectedObject;
                comidaDAO.borrar(comida.getIdComida());
                jButtonBuscarActionPerformed(evt);
            } else if (selectedObject instanceof DietaComida) {
                DietaComida dieta = (DietaComida) selectedObject;
                dietaComidaDAO.anularDietaComida(dietCom.getId());
                jButtonBuscarActionPerformed(evt);
            }
        }
    }//GEN-LAST:event_jButtonAnularActionPerformed

    private void jButtonEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarActionPerformed
        int selectedRow = jTable1.getSelectedRow();

        if (selectedRow != -1) {
            Object selectedObject = jTable1.getValueAt(selectedRow, 0);

            if (selectedObject instanceof Paciente) {
                pct = (Paciente) selectedObject;
                if (pct != null) {
                    ViewPaciente VP = new ViewPaciente(pct);
                    this.setVisible(false);
                    Dashboardv2 db = new Dashboardv2();
                    db.ShowJPanel(VP);
                    db.setVisible(true);
                }
            } else if (selectedObject instanceof Dieta) {
                diet = (Dieta) selectedObject;
                if (diet != null) {
                    ViewDieta VD = new ViewDieta(diet);
                    this.setVisible(false);
                    Dashboardv2 db = new Dashboardv2();
                    db.ShowJPanel(VD);
                    db.setVisible(true);
                }
            } else if (selectedObject instanceof Comida) {
                com = (Comida) selectedObject;
                if (com != null) {
                    ViewComida VCom = new ViewComida(com);
                    this.setVisible(false);
                    Dashboardv2 db = new Dashboardv2();
                    db.ShowJPanel(VCom);
                    db.setVisible(true);
                }
            } else if (selectedObject instanceof Consulta) {
                consul = (Consulta) selectedObject;
                if (consul != null) {
                    ViewConsulta VCon = new ViewConsulta(consul);
                    this.setVisible(false);
                    Dashboardv2 db = new Dashboardv2();
                    db.ShowJPanel(VCon);
                    db.setVisible(true);
                }
            } else if (selectedObject instanceof DietaComida) {
                dietCom = (DietaComida) selectedObject;
                if (dietCom != null) {
                    ViewDieta VDC = new ViewDieta(dietCom);
                    this.setVisible(false);
                    Dashboardv2 db = new Dashboardv2();
                    db.ShowJPanel(VDC);
                    db.setVisible(true);
                }
            }
        }
    }//GEN-LAST:event_jButtonEditarActionPerformed

    public String[] atributos(String entidad) {
        String at[] = null;
        if (entidad.equalsIgnoreCase("Comidas")) {
            String atributosComidas[] = {"", "ID", "Nombre", "Detalle", "Cantidad de calorias", "Estado"};
            at = atributosComidas;
        }
        if (entidad.equalsIgnoreCase("Tratamientos")) {
            String atributosDietas[] = {"", "ID", "Nombre", "Paciente", "Fecha Inicial", "Fecha Final", "Peso Final", "Estado"};
            at = atributosDietas;
        }
        if (entidad.equalsIgnoreCase("Pacientes")) {
            String atributosPacientes[] = {"", "ID", "Nombre Completo", "Domicilio", "DNI", "Peso actual", "Celular", "Estado"};
            at = atributosPacientes;
        }
        if (entidad.equalsIgnoreCase("Consultas")) {
            String atributosConsultas[] = {"", "ID", "Paciente", "Fecha", "Peso actual"};
            at = atributosConsultas;
        }
        if (entidad.equalsIgnoreCase("Dietas")) {
            String atributos[] = {"", "ID", "Comida", "Tratamiento", "Porcion", "Horario", "Estado"};
            at = atributos;
        }
        if (entidad.equalsIgnoreCase("")) {
            String atributos[] = {""};
            at = atributos;
        }
        return at;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButtonAnular;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonEditar;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonSalir;
    private javax.swing.JComboBox<Object> jComboBoxAtribSelect;
    private javax.swing.JComboBox<String> jComboBoxAtributos;
    private javax.swing.JComboBox<String> jComboBoxEntidades;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelVariable;
    private javax.swing.JRadioButton jRadioButtonActivo;
    private javax.swing.JRadioButton jRadioButtonAmbos;
    private javax.swing.JRadioButton jRadioButtonInactivo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
class FiltraEntrada extends DocumentFilter {

        public static final char SOLO_NUMEROS = 'N';
        public static final char SOLO_LETRAS = 'L';
        public static final char NUM_LETRAS = 'M';
        public static final char DEFAULT = '*';

        private char tipoEntrada;
        private int longitudCadena = 0;
        private int longitudActual = 0;

        public FiltraEntrada() {
            tipoEntrada = DEFAULT;
        }

        public FiltraEntrada(char tipoEntrada) {
            this.tipoEntrada = tipoEntrada;
        }

        public FiltraEntrada(char tipoEntrada, int longitudCadena) {
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
}

class NumericRangeFilter3 extends DocumentFilter {

    @Override
    public void replace(DocumentFilter.FilterBypass fb, int i, int i1, String string, AttributeSet as) throws BadLocationException {
        String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());

        String nextText = currentText.substring(0, i) + string + currentText.substring(i + i1);

        try {
            long num = Long.parseLong(nextText);

            if (num >= 1 && num <= 9999999999L) {
                super.replace(fb, i, i1, string, as);
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        } catch (NumberFormatException e) {
            Toolkit.getDefaultToolkit().beep();
        }
    }
}

class NumericRangeFilter extends DocumentFilter {

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

class NumericRangeFilter2 extends DocumentFilter {

    @Override
    public void replace(DocumentFilter.FilterBypass fb, int i, int i1, String string, AttributeSet as) throws BadLocationException {
        String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());

        String nextText = currentText.substring(0, i) + string + currentText.substring(i + i1);

        try {
            int num = Integer.parseInt(nextText);

            if (num >= 0 && num <= 10000) {
                super.replace(fb, i, i1, string, as);
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        } catch (NumberFormatException e) {
            Toolkit.getDefaultToolkit().beep();
        }
    }
}
