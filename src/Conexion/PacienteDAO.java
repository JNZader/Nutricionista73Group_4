package Conexion;

import Entidades.Paciente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class PacienteDAO {

    private Connection con;

    public PacienteDAO() {
        con = Conexion.getConnection();
    }

public void guardarPaciente(Paciente paciente) {
    String sqlInsert = "INSERT INTO paciente (nombreCompleto, DNI,domicilio, celular, pesoActual, estado) "
            + "SELECT ?, ?, ?, ?, ?,? "
            + "WHERE NOT EXISTS (SELECT 1 FROM paciente WHERE DNI = ?)";

    try (PreparedStatement ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
        ps.setString(1, paciente.getNombre());
        ps.setInt(2, paciente.getDni());
        ps.setString(3, paciente.getDomicilio());
        ps.setInt(4, paciente.getTelefono());
        ps.setDouble(5, paciente.getPesoActual());
        ps.setBoolean(6, paciente.isEstado());
        ps.setInt(7, paciente.getDni());

        int filasAfectadas = ps.executeUpdate();
        if (filasAfectadas == 1) {
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    paciente.setIdPaciente(rs.getInt(1));
                    JOptionPane.showMessageDialog(null, "Paciente añadido con éxito");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error al añadir el paciente. No se realizaron cambios en la base de datos o el DNI ya existe.");
        }
    } catch (SQLException ex) {
        ex.printStackTrace(System.err);
        JOptionPane.showMessageDialog(null, "Error al acceder a la tabla paciente");
    }
}

    public void modificarPaciente(Paciente paciente) {
        String sql = "UPDATE paciente SET nombreCompleto=?,DNI=?,domicilio=?,celular=?,pesoActual=?,estado=? WHERE idPaciente=? ";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, paciente.getNombre());
            ps.setInt(2, paciente.getDni());
            ps.setString(3, paciente.getDomicilio());
            ps.setInt(4, paciente.getTelefono());
            ps.setDouble(5, paciente.getPesoActual());
            ps.setBoolean(6, paciente.isEstado());
            ps.setInt(7, paciente.getIdPaciente());
            // Verificación del resultado de la ejecución
            int exito = ps.executeUpdate();
            if (exito == 1) {
                JOptionPane.showMessageDialog(null, "Modificado Exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "El paciente no existe");
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla paciente ");
        }
    }

    public void eliminarPacienteFisico(int id) {
        String sql = "DELETE FROM paciente WHERE idPaciente = ? AND idPaciente NOT IN (SELECT idPaciente FROM consulta) AND idPaciente NOT IN (SELECT idPaciente FROM dieta)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            // Verificación del resultado de la ejecución
            int fila = ps.executeUpdate();
            if (fila == 1) {
                JOptionPane.showMessageDialog(null, " Se eliminó el paciente.");
            } else {
                JOptionPane.showMessageDialog(null, "El paciente no se pudo eliminar");
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, " Error al acceder a la tabla paciente");
        }
    }

    public void eliminarPacienteLogico(int id) {
        String sql = "UPDATE paciente SET estado=0 WHERE idPaciente = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            // Verificación del resultado de la ejecución
            int fila = ps.executeUpdate();
            if (fila == 1) {
                JOptionPane.showMessageDialog(null, " Se anulo el paciente.");
            } else {
                JOptionPane.showMessageDialog(null, "El paciente no existe");
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, " Error al acceder a la tabla paciente");
        }
    }

    public ArrayList<Paciente> listarPaciente(int estado) {
        String sql = "";
        ArrayList<Paciente> pacientes = new ArrayList<>();

        switch (estado) {
            case 1:
                sql = "SELECT * FROM paciente where estado=1 ";
                break;
            case 0:
                sql = "SELECT * FROM paciente where estado=0 ";
                break;
            default:
                sql = "SELECT * FROM paciente";
                break;
        }

        try (PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            // iterar a través de los resultados
            while (rs.next()) {

                Paciente paciente = new Paciente();

                // setea los atributos del alumno
                paciente.setNombre(rs.getString("nombreCompleto"));
                paciente.setDni(rs.getInt("dni"));
                paciente.setDomicilio(rs.getString("domicilio"));
                paciente.setTelefono(rs.getInt("celular"));
                paciente.setIdPaciente(rs.getInt("idPaciente"));
                paciente.setPesoActual(rs.getDouble("pesoActual"));
                paciente.setEstado(rs.getBoolean("estado"));

                pacientes.add(paciente);// Agregar el alumno a la lista
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, " Error al acceder a la tabla paciente ");
        }
        if (pacientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontraron pacientes.", "Sin resultados", JOptionPane.WARNING_MESSAGE);
        }
        return pacientes;
    }

    public Paciente buscarPacientePorDni(int dni, int estado) {
        String sql = "";
        Paciente paciente = null;

        switch (estado) {
            case 1:
                sql = "SELECT * FROM paciente WHERE dni=? and estado=1";
                break;
            case 0:
                sql = "SELECT * FROM paciente WHERE dni=? and estado=0";
                break;
            default:
                sql = "SELECT * FROM paciente WHERE dni=?";
                break;
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, dni);//asigna el valor del parametro dni a la consulta sql

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    paciente = new Paciente();
                    paciente.setIdPaciente(rs.getInt("idPaciente"));
                    paciente.setDni(rs.getInt("dni"));
                    paciente.setNombre(rs.getString("nombreCompleto"));
                    paciente.setDomicilio(rs.getString("domicilio"));
                    paciente.setTelefono(rs.getInt("celular"));
                    paciente.setPesoActual(rs.getDouble("pesoActual"));
                    paciente.setEstado(rs.getBoolean("estado"));
                } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún paciente con DNI " + dni, "Paciente no encontrado", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al acceder la tabla paciente");
        }
        return paciente;
    }

    public Paciente buscarPaciente(int id, int estado) {
        String sql = "";
        Paciente paciente = null;

        switch (estado) {
            case 1:
                sql = "SELECT nombreCompleto,DNI,nombreCompleto,domicilio,celular,pesoActual,idPaciente, estado FROM paciente WHERE idPaciente=? and estado=1";
                break;
            case 0:
                sql = "SELECT nombreCompleto,DNI,nombreCompleto,domicilio,celular,pesoActual,idPaciente, estado FROM paciente WHERE idPaciente=? and estado=0";
                break;
            default:
                sql = "SELECT nombreCompleto,DNI,nombreCompleto,domicilio,celular,pesoActual,idPaciente, estado FROM paciente WHERE idPaciente=?";
                break;
        }
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);//asigna el valor del parametro dni a la consulta sql

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    paciente = new Paciente();
                    paciente.setIdPaciente(rs.getInt("idPaciente"));
                    paciente.setDni(rs.getInt("DNI"));
                    paciente.setNombre(rs.getString("nombreCompleto"));
                    paciente.setDomicilio(rs.getString("domicilio"));
                    paciente.setTelefono(rs.getInt("celular"));
                    paciente.setPesoActual(rs.getDouble("pesoActual"));
                    paciente.setEstado(rs.getBoolean("estado"));
                } else {
                    JOptionPane.showMessageDialog(null, "No existe el paciente");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al acceder la tabla paciente");
        }
        return paciente;
    }

    public ArrayList<Paciente> buscarPacientesPorNombre(String nombre, int estado) {
        String sql = "";
        ArrayList<Paciente> pacientes = new ArrayList<>();

        switch (estado) {
            case 1:
                sql = "SELECT * FROM paciente WHERE nombreCompleto LIKE ? AND estado=1";
                break;
            case 0:
                sql = "SELECT * FROM paciente WHERE nombreCompleto LIKE ? AND estado=0";
                break;
            default:
                sql = "SELECT * FROM paciente WHERE nombreCompleto LIKE ?";
                break;
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + nombre + "%"); // Agrega '%' para buscar en cualquier parte del nombre

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Paciente paciente = new Paciente();
                    paciente.setIdPaciente(rs.getInt("idPaciente"));
                    paciente.setDni(rs.getInt("dni"));
                    paciente.setNombre(rs.getString("nombreCompleto"));
                    paciente.setDomicilio(rs.getString("domicilio"));
                    paciente.setTelefono(rs.getInt("celular"));
                    paciente.setPesoActual(rs.getDouble("pesoActual"));
                    paciente.setEstado(rs.getBoolean("estado"));
                    pacientes.add(paciente);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla paciente");
        }
        if (pacientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontraron pacientes.", "Sin resultados", JOptionPane.WARNING_MESSAGE);
        }
        return pacientes;
    }

    public ArrayList<Paciente> buscarPacientesPorDomicilio(String domicilio, int estado) {
        String sql = "";
        ArrayList<Paciente> pacientes = new ArrayList<>();

        switch (estado) {
            case 1:
                sql = "SELECT * FROM paciente WHERE domicilio LIKE ? AND estado=1";
                break;
            case 0:
                sql = "SELECT * FROM paciente WHERE domicilio LIKE ? AND estado=0";
                break;
            default:
                sql = "SELECT * FROM paciente WHERE domicilio LIKE ?";
                break;
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + domicilio + "%"); // Agrega '%' para buscar en cualquier parte del domicilio

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Paciente paciente = new Paciente();
                    paciente.setIdPaciente(rs.getInt("idPaciente"));
                    paciente.setDni(rs.getInt("dni"));
                    paciente.setNombre(rs.getString("nombreCompleto"));
                    paciente.setDomicilio(rs.getString("domicilio"));
                    paciente.setTelefono(rs.getInt("celular"));
                    paciente.setPesoActual(rs.getDouble("pesoActual"));
                    paciente.setEstado(rs.getBoolean("estado"));
                    pacientes.add(paciente);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla paciente");
        }
        if (pacientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontraron pacientes.", "Sin resultados", JOptionPane.WARNING_MESSAGE);
        }
        return pacientes;
    }
    
    public ArrayList<Paciente> buscarPacientesPorCelular(int celular, int estado) {
        String sql = "";
        ArrayList<Paciente> pacientes = new ArrayList<>();

        switch (estado) {
            case 1:
                sql = "SELECT * FROM paciente WHERE celular LIKE ? AND estado=1";
                break;
            case 0:
                sql = "SELECT * FROM paciente WHERE celular LIKE ? AND estado=0";
                break;
            default:
                sql = "SELECT * FROM paciente WHERE celular LIKE ?";
                break;
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + celular + "%"); // Agrega '%' para buscar en cualquier parte del domicilio

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Paciente paciente = new Paciente();
                    paciente.setIdPaciente(rs.getInt("idPaciente"));
                    paciente.setDni(rs.getInt("dni"));
                    paciente.setNombre(rs.getString("nombreCompleto"));
                    paciente.setDomicilio(rs.getString("domicilio"));
                    paciente.setTelefono(rs.getInt("celular"));
                    paciente.setPesoActual(rs.getDouble("pesoActual"));
                    paciente.setEstado(rs.getBoolean("estado"));
                    pacientes.add(paciente);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla paciente");
        }
        if (pacientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontraron pacientes.", "Sin resultados", JOptionPane.WARNING_MESSAGE);
        }
        return pacientes;
    }

    public ArrayList<Paciente> buscarPacientesPorPesoActual(double peso, int condicion) {
        String SQL_SELECT = "";
        Paciente paciente = null;
        ArrayList<Paciente> pacientes = new ArrayList<>();
        condicion = (condicion > 0) ? 1 : (condicion < 0) ? -1 : 0;

        switch (condicion) {
            case 0:
                SQL_SELECT = "SELECT * FROM paciente WHERE pesoActual = ? AND estado = 1";
                break;
            case 1:
                SQL_SELECT = "SELECT * FROM paciente WHERE pesoActual > ? AND estado = 1";
                break;
            case -1:
                SQL_SELECT = "SELECT * FROM paciente WHERE pesoActual < ? AND estado = 1";
                break;
        }

        try (PreparedStatement ps = con.prepareStatement(SQL_SELECT)) {
            ps.setDouble(1, peso);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    paciente = new Paciente();
                    paciente.setIdPaciente(rs.getInt("idPaciente"));
                    paciente.setNombre(rs.getString("nombreCompleto"));
                    paciente.setDni(rs.getInt("DNI"));
                    paciente.setDomicilio(rs.getString("domicilio"));
                    paciente.setTelefono(rs.getInt("celular"));
                    paciente.setPesoActual(rs.getDouble("pesoActual"));
                    paciente.setEstado(rs.getBoolean("estado"));
                    pacientes.add(paciente);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al buscar pacientes por peso actual");
        }
        if (pacientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontraron pacientes.", "Sin resultados", JOptionPane.WARNING_MESSAGE);
        }
        return pacientes;
    }
}
