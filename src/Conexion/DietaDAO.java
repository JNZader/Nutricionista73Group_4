package Conexion;

import static Conexion.Conexion.getConnection;
import Entidades.Dieta;
import Entidades.Paciente;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class DietaDAO {

    private PacienteDAO pd;
    private Connection con;

    public DietaDAO() {
        con = getConnection();
    }

    public ArrayList<Dieta> buscar(int estado) {
        String SQL_SELECT = "";

        switch (estado) {
            case 1:
                SQL_SELECT = "SELECT idDieta, nombre, idPaciente, fechaInicio, fechaFin, pesoFinal, estado FROM dieta WHERE estado=1";
                break;
            case 0:
                SQL_SELECT = "SELECT idDieta, nombre, idPaciente, fechaInicio, fechaFin, pesoFinal, estado FROM dieta WHERE estado=0";
                break;
            default:
                SQL_SELECT = "SELECT idDieta, nombre, idPaciente, fechaInicio, fechaFin, pesoFinal, estado FROM dieta";
                break;
        }

        Dieta dieta = null;
        ArrayList<Dieta> dietas = new ArrayList<>();
        pd = new PacienteDAO();
        try (PreparedStatement ps = con.prepareStatement(SQL_SELECT); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                dieta = new Dieta();
                dieta.setNombre(rs.getString("nombre"));
                int idPaciente = rs.getInt("idpaciente");
                Paciente p = pd.buscarPaciente(idPaciente, 3);
                dieta.setPaciente(p);
                dieta.setFechaInicial(rs.getDate("fechaInicio").toLocalDate());
                dieta.setFechaFinal(rs.getDate("fechaFin").toLocalDate());
                dieta.setPesoFinal(rs.getDouble("pesoFinal"));
                dieta.setEstado(rs.getBoolean("estado"));
                dieta.setIdDieta(rs.getInt("idDieta"));

                dietas.add(dieta);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al obtener Dietas");
        }
        return dietas;
    }

    public Dieta buscarPorId(int idDieta, int estado) {
        String SQL_SELECT_ID = null;
        Dieta dieta = null;
        pd = new PacienteDAO();
        switch (estado) {
            case 1:
                SQL_SELECT_ID = "SELECT nombre, idPaciente, fechaInicio, fechaFin, pesoFinal, estado FROM dieta WHERE idDieta = ? AND estado=1";
                break;
            case 0:
                SQL_SELECT_ID = "SELECT nombre, idPaciente, fechaInicio, fechaFin, pesoFinal, estado FROM dieta WHERE idDieta = ? AND estado=0";
                break;
            default:
                SQL_SELECT_ID = "SELECT nombre, idPaciente, fechaInicio, fechaFin, pesoFinal, estado FROM dieta WHERE idDieta = ?";
                break;
        }
        try (PreparedStatement ps = con.prepareStatement(SQL_SELECT_ID)) {
            ps.setInt(1, idDieta);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dieta = new Dieta();
                    dieta.setNombre(rs.getString("nombre"));

                    int idPaciente = rs.getInt("idpaciente");
                    Paciente p = pd.buscarPaciente(idPaciente, 3);

                    dieta.setPaciente(p);
                    dieta.setFechaInicial(rs.getDate("fechaInicio").toLocalDate());
                    dieta.setFechaFinal(rs.getDate("fechaFin").toLocalDate());
                    dieta.setPesoFinal(rs.getDouble("pesoFinal"));
                    dieta.setIdDieta(idDieta);
                    dieta.setEstado(rs.getBoolean("estado"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al obtener la Dieta por ID");
        }
        return dieta;
    }

    public void insertar(Dieta dieta) {
        String SQL_INSERT = "INSERT INTO dieta(nombre, idPaciente, fechaInicio, fechaFin, pesoFinal, estado) VALUES (?,?,?,?,?,?)";

        try (PreparedStatement ps = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, dieta.getNombre());
            int id = dieta.getPaciente().getIdPaciente();
            ps.setInt(2, id);
            ps.setDate(3, Date.valueOf(dieta.getFechaInicial()));
            ps.setDate(4, Date.valueOf(dieta.getFechaFinal()));
            ps.setDouble(5, dieta.getPesoFinal());
            ps.setBoolean(6, true);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    dieta.setIdDieta(rs.getInt(1));
                    JOptionPane.showMessageDialog(null, "Dieta inscripta");
                } else {
                    JOptionPane.showMessageDialog(null, "Inscripcion fallida");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al insertar dieta");
        }
    }

    public void actualizar(Dieta dieta) {
        String SQL_UPDATE = "UPDATE dieta SET idPaciente = ?, fechaInicio = ?, fechaFin = ?, pesoFinal = ?, estado=? WHERE idDieta = ?";
        try (PreparedStatement ps = con.prepareStatement(SQL_UPDATE)) {

            ps.setInt(1, dieta.getPaciente().getIdPaciente());
            ps.setDate(2, Date.valueOf(dieta.getFechaInicial()));
            ps.setDate(3, Date.valueOf(dieta.getFechaFinal()));
            ps.setDouble(4, dieta.getPesoFinal());
            ps.setBoolean(5, dieta.isEstado());
            ps.setInt(6, dieta.getIdDieta());

            int on = ps.executeUpdate();
            if (on > 0) {
                JOptionPane.showMessageDialog(null, "Actualizacion realizada");
            } else {
                JOptionPane.showMessageDialog(null, "Actualizacion fallida");
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al actualizar Dieta");
        }
    }

    public void eliminarDieta(int idDieta) {
        String sql = "DELETE FROM dieta WHERE idDieta = ? AND idDieta NOT IN (SELECT idDieta FROM dietacomida)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idDieta);
            int del = ps.executeUpdate();
            if (del == 1) {
                JOptionPane.showMessageDialog(null, "Dieta eliminada con éxito");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar la dieta");
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al eliminar Dieta");
        }
    }

    public void anularDieta(int idDieta) {
        String sql = "UPDATE dieta SET estado=0 WHERE idDieta = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idDieta);
            int updel = ps.executeUpdate();
            if (updel == 1) {
                System.out.println("se ha eliminado una Dieta");
            } else {
                System.out.println("error al eliminar Dieta");
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error SQL al eliminar Dieta");
        }
    }

    public ArrayList<Dieta> buscarDietasPorFecha(LocalDate fecha, int estado, boolean buscarPorFI) {
        String SQL_SELECT = "";
        String fechaColumn = buscarPorFI ? "fechaInicio" : "fechaFin";

        Dieta dieta = null;
        ArrayList<Dieta> dietas = new ArrayList<>();
        pd = new PacienteDAO();

        switch (estado) {
            case 1:
                SQL_SELECT = "SELECT * FROM dieta WHERE " + fechaColumn + " = ? AND estado = 1";
                break;
            case 0:
                SQL_SELECT = "SELECT * FROM dieta WHERE " + fechaColumn + " = ? AND estado = 0";
                break;
            default:
                SQL_SELECT = "SELECT * FROM dieta WHERE " + fechaColumn + " = ?";
                break;
        }

        try (PreparedStatement ps = con.prepareStatement(SQL_SELECT)) {
            ps.setDate(1, Date.valueOf(fecha));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    dieta = new Dieta();
                    dieta.setIdDieta(rs.getInt("idDieta"));
                    dieta.setNombre(rs.getString("nombre"));
                    int idPaciente = rs.getInt("idPaciente");
                    Paciente p = pd.buscarPaciente(idPaciente, 3);
                    dieta.setPaciente(p);
                    dieta.setFechaInicial(rs.getDate("fechaInicio").toLocalDate());
                    dieta.setFechaFinal(rs.getDate("fechaFin").toLocalDate());
                    dieta.setPesoFinal(rs.getDouble("pesoFinal"));
                    dieta.setEstado(rs.getBoolean("estado"));
                    dietas.add(dieta);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al buscar dietas por fecha");
        }
        return dietas;
    }

    public ArrayList<Dieta> buscarDietasPorPaciente(int idPaciente, int estado) {
        String SQL_SELECT = "";
        Dieta dieta = null;
        ArrayList<Dieta> dietas = new ArrayList<>();
        pd = new PacienteDAO();

        switch (estado) {
            case 1:
                SQL_SELECT = "SELECT idDieta, nombre, idPaciente, fechaInicio, fechaFin, pesoFinal, estado FROM dieta WHERE idPaciente = ? AND estado = 1";
                break;
            case 0:
                SQL_SELECT = "SELECT idDieta, nombre, idPaciente, fechaInicio, fechaFin, pesoFinal, estado FROM dieta WHERE idPaciente = ? AND estado = 0";
                break;
            default:
                SQL_SELECT = "SELECT idDieta, nombre, idPaciente, fechaInicio, fechaFin, pesoFinal, estado FROM dieta WHERE idPaciente = ?";
                break;
        }

        try (PreparedStatement ps = con.prepareStatement(SQL_SELECT)) {
            ps.setInt(1, idPaciente);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    dieta = new Dieta();
                    dieta.setIdDieta(rs.getInt("idDieta"));
                    dieta.setNombre(rs.getString("nombre"));
                    int pacienteId = rs.getInt("idPaciente");
                    Paciente p = pd.buscarPaciente(pacienteId, 3);
                    dieta.setPaciente(p);
                    dieta.setFechaInicial(rs.getDate("fechaInicio").toLocalDate());
                    dieta.setFechaFinal(rs.getDate("fechaFin").toLocalDate());
                    dieta.setPesoFinal(rs.getDouble("pesoFinal"));
                    dieta.setEstado(rs.getBoolean("estado"));
                    dietas.add(dieta);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al buscar dietas por paciente");
        }
        return dietas;
    }

    public ArrayList<Dieta> buscarDietasPorNombre(String nombre, int estado) {
        String SQL_SELECT = "";
        Dieta dieta = null;
        ArrayList<Dieta> dietas = new ArrayList<>();
        pd = new PacienteDAO();

        switch (estado) {
            case 1:
                SQL_SELECT = "SELECT idDieta, nombre, idPaciente, fechaInicio, fechaFin, pesoFinal, estado FROM dieta WHERE nombre LIKE ? AND estado = 1";
                break;
            case 0:
                SQL_SELECT = "SELECT idDieta, nombre, idPaciente, fechaInicio, fechaFin, pesoFinal, estado FROM dieta WHERE nombre LIKE ? AND estado = 0";
                break;
            default:
                SQL_SELECT = "SELECT idDieta, nombre, idPaciente, fechaInicio, fechaFin, pesoFinal, estado FROM dieta WHERE nombre LIKE ?";
                break;
        }

        try (PreparedStatement ps = con.prepareStatement(SQL_SELECT)) {
            ps.setString(1, "%" + nombre + "%"); // con "%" para buscar el nombre en cualquier parte del campo

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    dieta = new Dieta();
                    dieta.setIdDieta(rs.getInt("idDieta"));
                    dieta.setNombre(rs.getString("nombre"));
                    int idPaciente = rs.getInt("idPaciente");
                    Paciente p = pd.buscarPaciente(idPaciente, 3);
                    dieta.setPaciente(p);
                    dieta.setFechaInicial(rs.getDate("fechaInicio").toLocalDate());
                    dieta.setFechaFinal(rs.getDate("fechaFin").toLocalDate());
                    dieta.setPesoFinal(rs.getDouble("pesoFinal"));
                    dieta.setEstado(rs.getBoolean("estado"));
                    dietas.add(dieta);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al buscar dietas por nombre");
        }
        return dietas;
    }

    public ArrayList<Dieta> buscarDietasPorPesoFinal(double pesoFinal, int estado) {
        String SQL_SELECT = "";

        Dieta dieta = null;
        ArrayList<Dieta> dietas = new ArrayList<>();
        pd = new PacienteDAO();

        switch (estado) {
            case 0:
                SQL_SELECT = "SELECT idDieta, nombre, idPaciente, fechaInicio, fechaFin, pesoFinal, estado FROM dieta WHERE pesoFinal = ? AND estado=1";
                break;
            case 1:
                SQL_SELECT = "SELECT idDieta, nombre, idPaciente, fechaInicio, fechaFin, pesoFinal, estado FROM dieta WHERE pesoFinal > ? AND estado=1";
                break;
            case -1:
                SQL_SELECT = "SELECT idDieta, nombre, idPaciente, fechaInicio, fechaFin, pesoFinal, estado FROM dieta WHERE pesoFinal < ? AND estado=1";
                break;
        }

        try (PreparedStatement ps = con.prepareStatement(SQL_SELECT)) {
            ps.setDouble(1, pesoFinal);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    dieta = new Dieta();
                    dieta.setIdDieta(rs.getInt("idDieta"));
                    dieta.setNombre(rs.getString("nombre"));
                    int idPaciente = rs.getInt("idPaciente");
                    Paciente p = pd.buscarPaciente(idPaciente, 3);
                    dieta.setPaciente(p);
                    dieta.setFechaInicial(rs.getDate("fechaInicio").toLocalDate());
                    dieta.setFechaFinal(rs.getDate("fechaFin").toLocalDate());
                    dieta.setPesoFinal(rs.getDouble("pesoFinal"));
                    dieta.setEstado(rs.getBoolean("estado"));
                    dietas.add(dieta);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al buscar dietas por peso final");
        }
        return dietas;
    }

    public ArrayList<String[]> listarPacientesNoAlcanzaronPesoObjetivo() {
        ArrayList<String[]> pacientes = new ArrayList<>();
        String SQL_SELECT = "SELECT d.idDieta, d.nombre, d.idPaciente, d.fechaInicio, d.fechaFin, d.pesoFinal, c.pesoActual "
                + "FROM dieta d "
                + "JOIN ("
                + "    SELECT idPaciente, MAX(fecha) AS max_fecha "
                + "    FROM consulta "
                + "    GROUP BY idPaciente "
                + ") subquery ON d.idPaciente = subquery.idPaciente "
                + "LEFT JOIN consulta c ON d.idPaciente = c.idPaciente AND subquery.max_fecha = c.fecha "
                + "WHERE d.estado = 1";

        try (PreparedStatement ps = con.prepareStatement(SQL_SELECT); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String[] paciente = new String[5];
                paciente[0] = String.valueOf(rs.getInt("idPaciente"));
                paciente[1] = rs.getString("nombre");
                paciente[2] = String.valueOf(rs.getDouble("pesoActual"));
                paciente[3] = String.valueOf(rs.getDouble("pesoFinal"));
                paciente[4] = rs.getDate("fechaFin").toString();

                pacientes.add(paciente);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al listar pacientes que no alcanzaron su peso objetivo");
        }
        return pacientes;
    }

    public ArrayList<String[]> listarPacientesAlcanzaronPesoDeseado() {
        ArrayList<String[]> pacientes = new ArrayList<>();

        String SQL_SELECT = "SELECT p.idPaciente, p.nombreCompleto, c.pesoActual, d.pesoFinal, d.fechaFin "
                + "FROM paciente p "
                + "JOIN dieta d ON p.idPaciente = d.idPaciente "
                + "JOIN consulta c ON p.idPaciente = c.idPaciente "
                + "WHERE d.fechaFin <= CURDATE() "
                +"AND (c.fecha IS NULL OR c.fecha = (SELECT MAX(fecha) FROM consulta WHERE idPaciente = p.idPaciente)) "
                + "AND c.pesoActual >= d.pesoFinal";

        try (PreparedStatement ps = con.prepareStatement(SQL_SELECT); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String[] pacienteData = new String[5];
                pacienteData[0] = rs.getInt("idPaciente") + "";
                pacienteData[1] = rs.getString("nombreCompleto");
                pacienteData[2] = rs.getDouble("pesoActual") + "";
                pacienteData[3] = rs.getDouble("pesoFinal") + "";
                pacienteData[4] = rs.getDate("fechaFin").toString();

                pacientes.add(pacienteData);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al listar pacientes que alcanzaron el peso deseado");
        }

        return pacientes;
    }
    
    
    public ArrayList<Dieta> listarDietas(boolean soloNoCumplidas) {
    ArrayList<Dieta> dietas = new ArrayList<>();
    
    String sql = "SELECT d.idDieta FROM dieta d, paciente p " +
                 "WHERE d.idPaciente = p.idPaciente " +
                 "AND d.estado = 1 " +
                 "AND p.estado = 1 " +
                 (soloNoCumplidas ? "AND pesoFinal < pesoActual AND fechaFin <= ?" : "") +
                 " ORDER BY d.fechaFin";
    
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        if (soloNoCumplidas) {
            ps.setDate(1, Date.valueOf(LocalDate.now()));
        }
        
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                dietas.add(buscarPorId(rs.getInt("idDieta"),1));
            }
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al acceder a la tabla dieta " + ex.getMessage());
    }
    
    return dietas;
}
}
