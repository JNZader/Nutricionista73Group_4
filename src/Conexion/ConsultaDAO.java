package Conexion;

import static Conexion.Conexion.getConnection;
import Entidades.Consulta;
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

public class ConsultaDAO {

    private Connection con;

    public ConsultaDAO() {
        con = getConnection();
    }

    public ArrayList<Consulta> buscar() {
        String SQL_SELECT = "SELECT * FROM consulta";
        Consulta consulta = null;
        ArrayList<Consulta> consultas = new ArrayList<>();
        PacienteDAO pdao = new PacienteDAO();
        Paciente paciente = new Paciente();
        try (PreparedStatement ps = con.prepareStatement(SQL_SELECT); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                consulta = new Consulta();
                consulta.setIdConsulta(rs.getInt("idConsulta"));
                paciente = pdao.buscarPaciente((rs.getInt("idPaciente")), 3);
                consulta.setPaciente(paciente);
                consulta.setFecha(rs.getDate("fecha").toLocalDate());
                consulta.setPesoActual(rs.getDouble("pesoActual"));

                consultas.add(consulta);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al obtener consultas");
        }
        return consultas;
    }

    public ArrayList<Consulta> buscar(Paciente paciente) {
        String SQL_SELECT_ID = "SELECT * FROM consulta WHERE idPaciente = ?";
        Consulta consulta = null;
        ArrayList<Consulta> consultas = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(SQL_SELECT_ID)) {
            ps.setInt(1, paciente.getIdPaciente());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    consulta = new Consulta();
                    consulta.setIdConsulta(rs.getInt("idConsulta"));
                    consulta.setPaciente(paciente);
                    consulta.setFecha(rs.getDate("fecha").toLocalDate());
                    consulta.setPesoActual(rs.getDouble("pesoActual"));
                    consultas.add(consulta);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al obtener la Consulta por Paciente");
        }
        return consultas;
    }

    public Consulta buscar(int idConsulta) {
        String SQL_SELECT_ID = "SELECT * FROM consulta WHERE idConsulta = ?";
        Consulta consulta = null;
        PacienteDAO pdao = null;
        Paciente p = null;
        try (PreparedStatement ps = con.prepareStatement(SQL_SELECT_ID)) {
            ps.setInt(1, idConsulta);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    consulta = new Consulta();
                    pdao = new PacienteDAO();
                    p = pdao.buscarPaciente(rs.getInt("idPaciente"), 1);
                    consulta.setIdConsulta(idConsulta);
                    consulta.setPaciente(p);
                    consulta.setFecha(rs.getDate("fecha").toLocalDate());
                    consulta.setPesoActual(rs.getDouble("pesoActual"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al obtener la Consulta por ID");
        }
        return consulta;
    }

    public void insertar(Consulta consulta) {
        String SQL_INSERT = "INSERT INTO consulta (idPaciente, fecha, pesoActual) VALUES (?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, consulta.getPaciente().getIdPaciente());
            ps.setDate(2, Date.valueOf(consulta.getFecha()));
            ps.setDouble(3, consulta.getPesoActual());
            ps.executeUpdate(); // Ejecuta la inserción en la base de datos

            // Actualiza el peso actual en la tabla "paciente" solo si la consulta es la más reciente
            String SQL_UPDATE_PACIENTE = "UPDATE paciente p "
                    + "SET p.pesoActual = ? "
                    + "WHERE p.idPaciente = ? "
                    + "AND NOT EXISTS (SELECT 1 FROM consulta c "
                    + "WHERE c.idPaciente = p.idPaciente AND c.fecha > ?)";

            try (PreparedStatement psUpdatePaciente = con.prepareStatement(SQL_UPDATE_PACIENTE)) {
                psUpdatePaciente.setDouble(1, consulta.getPesoActual());
                psUpdatePaciente.setInt(2, consulta.getPaciente().getIdPaciente());
                psUpdatePaciente.setDate(3, Date.valueOf(consulta.getFecha()));
                psUpdatePaciente.executeUpdate();
            }

            JOptionPane.showMessageDialog(null, "Consulta realizada con éxito");
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al guardar la consulta");
        }
    }

    public void actualizar(Consulta consulta) {
        String SQL_UPDATE = "UPDATE consulta SET idPaciente=?, fecha=?, pesoActual=? WHERE idConsulta=?";

        try (PreparedStatement ps = con.prepareStatement(SQL_UPDATE)) {
            ps.setInt(1, consulta.getPaciente().getIdPaciente());
            ps.setDate(2, Date.valueOf(consulta.getFecha()));
            ps.setDouble(3, consulta.getPesoActual());
            ps.setInt(4, consulta.getIdConsulta());

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                // Actualiza el peso actual en la tabla "paciente" solo si la consulta es la más reciente
                String SQL_UPDATE_PACIENTE = "UPDATE paciente p "
                        + "SET p.pesoActual = ? "
                        + "WHERE p.idPaciente = ? "
                        + "AND NOT EXISTS (SELECT 1 FROM consulta c "
                        + "WHERE c.idPaciente = p.idPaciente AND c.fecha > ?)";

                try (PreparedStatement psUpdatePaciente = con.prepareStatement(SQL_UPDATE_PACIENTE)) {
                    psUpdatePaciente.setDouble(1, consulta.getPesoActual());
                    psUpdatePaciente.setInt(2, consulta.getPaciente().getIdPaciente());
                    psUpdatePaciente.setDate(3, Date.valueOf(consulta.getFecha()));
                    psUpdatePaciente.executeUpdate();
                }

                JOptionPane.showMessageDialog(null, "Actualización realizada");
            } else {
                JOptionPane.showMessageDialog(null, "Actualización fallida");
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al actualizar consulta");
        }
    }

    public void eliminar(int idConsulta) {
        String sql = "DELETE FROM consulta WHERE idConsulta = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idConsulta);
            int del = ps.executeUpdate();
            if (del == 1) {
                JOptionPane.showMessageDialog(null, "Consulta eliminada con éxito");
            } else {
                JOptionPane.showMessageDialog(null, "No se puede eliminar la consulta.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al eliminar Consulta");
        }
    }

    public ArrayList<Consulta> buscarPorFecha(LocalDate fecha, int comparador) {
        String operador = "";

        switch (comparador) {
            case 1:
                operador = ">";
                break;
            case 0:
                operador = "=";
                break;
            case -1:
                operador = "<";
                break;
        }
        String SQL_SELECT = "SELECT * FROM consulta WHERE fecha " + operador + " ?";

        ArrayList<Consulta> consultas = new ArrayList<>();
        PacienteDAO pdao = new PacienteDAO();
        Paciente paciente = new Paciente();

        try (PreparedStatement ps = con.prepareStatement(SQL_SELECT)) {
            ps.setDate(1, Date.valueOf(fecha));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Consulta consulta = new Consulta();
                    consulta.setIdConsulta(rs.getInt("idConsulta"));
                    paciente = pdao.buscarPaciente((rs.getInt("idPaciente")), 3);
                    consulta.setPaciente(paciente);
                    consulta.setFecha(rs.getDate("fecha").toLocalDate());
                    consulta.setPesoActual(rs.getDouble("pesoActual"));
                    consultas.add(consulta);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al obtener consultas por fecha");
        }

        return consultas;
    }

    public ArrayList<Consulta> buscarPorPesoActual(double peso, int comparador) {
        String operador = "";

        switch (comparador) {
            case 1:
                operador = ">";
                break;
            case 0:
                operador = "=";
                break;
            case -1:
                operador = "<";
                break;
        }
        String SQL_SELECT = "SELECT * FROM consulta WHERE pesoActual " + operador + " ?";

        ArrayList<Consulta> consultas = new ArrayList<>();
        PacienteDAO pdao = new PacienteDAO();
        Paciente paciente = new Paciente();

        try (PreparedStatement ps = con.prepareStatement(SQL_SELECT)) {
            ps.setDouble(1, peso);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Consulta consulta = new Consulta();
                    consulta.setIdConsulta(rs.getInt("idConsulta"));
                    paciente = pdao.buscarPaciente((rs.getInt("idPaciente")), 3);
                    consulta.setPaciente(paciente);
                    consulta.setFecha(rs.getDate("fecha").toLocalDate());
                    consulta.setPesoActual(rs.getDouble("pesoActual"));
                    consultas.add(consulta);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al obtener consultas por peso actual");
        }

        return consultas;
    }
}
