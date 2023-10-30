package Conexion;

import static Conexion.Conexion.getConnection;
import Entidades.Comida;
import Entidades.Dieta;
import Entidades.DietaComida;
import Entidades.Horario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class DietaComidaDAO {

    private Connection con;

    public DietaComidaDAO() {
        con = getConnection();
    }

    public DietaComida insertar(DietaComida dietacomida) {
        String SQL_INSERT = "INSERT INTO dietacomida (idComida,idDieta, porcion, horario,estado) VALUES (?,?,?,?,?)";

        try (PreparedStatement ps = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, dietacomida.getComida().getIdComida());
            ps.setInt(2, dietacomida.getDieta().getIdDieta());
            ps.setInt(3, dietacomida.getPorcion());
            ps.setString(4, "" + dietacomida.getHorario());
            ps.setBoolean(5, dietacomida.isEstado());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    dietacomida.setId(rs.getInt(1));
                    JOptionPane.showMessageDialog(null, "Dieta inscripta");
                } else {
                    JOptionPane.showMessageDialog(null, "Inscripcion fallida");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al insertar Dieta");
        }
        return dietacomida;
    }

    public void actualizar(DietaComida dietaComida) {
        String SQL_UPDATE = "UPDATE dietaComida  SET  porcion = ?, horario = ? WHERE idComida  = ? AND idDieta  = ?";
        System.out.println("dietaComida = " + dietaComida);
        try (PreparedStatement ps = con.prepareStatement(SQL_UPDATE)) {
            ps.setInt(1, dietaComida.getPorcion());
            ps.setString(2, dietaComida.getHorario() + "");
            ps.setInt(3, dietaComida.getComida().getIdComida());
            ps.setInt(4, dietaComida.getDieta().getIdDieta());

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

    public ArrayList<DietaComida> buscar(int estado) {
        String SQL_SELECT = null;

        switch (estado) {
            case 1:
                SQL_SELECT = "SELECT iddietacomida, idComida, idDieta, porcion, horario, estado FROM dietacomida WHERE estado=1";
                break;
            case 0:
                SQL_SELECT = "SELECT iddietacomida, idComida, idDieta, porcion, horario, estado FROM dietacomida WHERE estado=0";
                break;
            default:
                SQL_SELECT = "SELECT iddietacomida, idComida, idDieta, porcion, horario, estado FROM dietacomida";
                break;
        }

        DietaComida dietaComida = null;
        ArrayList<DietaComida> dietaComidas = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(SQL_SELECT); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                dietaComida = new DietaComida();
                dietaComida.setId(rs.getInt("iddietacomida"));

                int idComida = rs.getInt("idComida");
                ComidaDAO comidaDAO = new ComidaDAO();
                Comida comida = comidaDAO.buscar(idComida, 3);
                dietaComida.setComida(comida);

                int idDieta = rs.getInt("idDieta");
                DietaDAO dietaDAO = new DietaDAO();
                Dieta dieta = dietaDAO.buscarPorId(idDieta, 1);
                dietaComida.setDieta(dieta);
                dietaComida.setEstado(rs.getBoolean("estado"));
                dietaComida.setPorcion(rs.getInt("porcion"));
                dietaComida.setHorario(Horario.valueOf(rs.getString("horario")));

                dietaComidas.add(dietaComida);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al obtener Dietas");
        }
        if (dietaComidas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontraron Dietas.", "Sin resultados", JOptionPane.WARNING_MESSAGE);
        }
        return dietaComidas;
    }

    public DietaComida buscarPorId(int idDietaComida, int estado) {
        String SQL_SELECT_ID = null;
        DietaComida dietaComida = null;

        switch (estado) {
            case 1:
                SQL_SELECT_ID = "SELECT iddietacomida, idComida, idDieta, porcion, horario, estado FROM dietacomida WHERE iddietacomida = ? AND estado=1";
                break;
            case 0:
                SQL_SELECT_ID = "SELECT iddietacomida, idComida, idDieta, porcion, horario, estado FROM dietacomida WHERE iddietacomida = ? AND estado=0";
                break;
            default:
                SQL_SELECT_ID = "SELECT iddietacomida, idComida, idDieta, porcion, horario, estado FROM dietacomida WHERE iddietacomida = ?";
                break;
        }

        try (PreparedStatement ps = con.prepareStatement(SQL_SELECT_ID)) {
            ps.setInt(1, idDietaComida);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dietaComida = new DietaComida();
                    dietaComida.setId(idDietaComida);

                    int idComida = rs.getInt("idComida");
                    ComidaDAO comidaDAO = new ComidaDAO();
                    Comida comida = comidaDAO.buscar(idComida, 3);
                    dietaComida.setComida(comida);

                    int idDieta = rs.getInt("idDieta");
                    DietaDAO dietaDAO = new DietaDAO();
                    Dieta dieta = dietaDAO.buscarPorId(idDieta, 1);
                    dietaComida.setDieta(dieta);
                    dietaComida.setEstado(rs.getBoolean("estado"));
                    dietaComida.setPorcion(rs.getInt("porcion"));
                    dietaComida.setHorario(Horario.valueOf(rs.getString("horario")));
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró ninguna Dieta con ese ID.", "DietaComida no encontrada", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al obtener la Dieta por ID");
        }
        return dietaComida;
    }

    public ArrayList<DietaComida> buscarPorIdDieta(int idDieta, int estado) {
        String SQL_SELECT_X_ID_DIETA = null;

        switch (estado) {
            case 1:
                SQL_SELECT_X_ID_DIETA = "SELECT iddietacomida, idComida, idDieta, porcion, horario, estado FROM dietacomida WHERE idDieta = ? AND estado=1";
                break;
            case 0:
                SQL_SELECT_X_ID_DIETA = "SELECT iddietacomida, idComida, idDieta, porcion, horario, estado FROM dietacomida WHERE idDieta = ? AND estado=0";
                break;
            default:
                SQL_SELECT_X_ID_DIETA = "SELECT iddietacomida, idComida, idDieta, porcion, horario, estado FROM dietacomida WHERE idDieta = ?";
                break;
        }

        DietaComida dietaComida = null;
        ArrayList<DietaComida> dietaComidas = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(SQL_SELECT_X_ID_DIETA)) {
            ps.setInt(1, idDieta);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    dietaComida = new DietaComida();
                    dietaComida.setId(rs.getInt("iddietacomida"));

                    int idComida = rs.getInt("idComida");
                    ComidaDAO comidaDAO = new ComidaDAO();
                    Comida comida = comidaDAO.buscar(idComida, estado);
                    dietaComida.setComida(comida);

                    DietaDAO dietaDAO = new DietaDAO();
                    Dieta dieta = dietaDAO.buscarPorId(idDieta, estado);
                    dietaComida.setDieta(dieta);

                    dietaComida.setPorcion(rs.getInt("porcion"));
                    dietaComida.setHorario(Horario.valueOf(rs.getString("horario")));
                    dietaComida.setEstado(rs.getBoolean("estado"));

                    dietaComidas.add(dietaComida);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al obtener Dietas por ID de Tratamiento");
        }
        if (dietaComidas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontraron Dietas", "Sin resultados", JOptionPane.WARNING_MESSAGE);
        }
        return dietaComidas;
    }

    public ArrayList<DietaComida> buscarPorPorcion(int porcion, int condicion) {
        String SQL_SELECT = null;
        DietaComida dietaComida = null;
        ArrayList<DietaComida> dietaComidas = new ArrayList<>();

        condicion = (condicion > 0) ? 1 : (condicion < 0) ? -1 : 0;

        switch (condicion) {
            case 0:
                SQL_SELECT = "SELECT iddietacomida, idComida, idDieta, porcion, horario, estado FROM dietacomida WHERE porcion = ?";
                break;
            case 1:
                SQL_SELECT = "SELECT iddietacomida, idComida, idDieta, porcion, horario, estado FROM dietacomida WHERE porcion > ?";
                break;
            case -1:
                SQL_SELECT = "SELECT iddietacomida, idComida, idDieta, porcion, horario, estado FROM dietacomida WHERE porcion < ?";
                break;
        }

        try (PreparedStatement ps = con.prepareStatement(SQL_SELECT)) {
            ps.setInt(1, porcion);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    dietaComida = new DietaComida();
                    dietaComida.setId(rs.getInt("iddietacomida"));

                    int idComida = rs.getInt("idComida");
                    ComidaDAO comidaDAO = new ComidaDAO();
                    Comida comida = comidaDAO.buscar(idComida, 1);
                    dietaComida.setComida(comida);

                    int idDieta = rs.getInt("idDieta");
                    DietaDAO dietaDAO = new DietaDAO();
                    Dieta dieta = dietaDAO.buscarPorId(idDieta, 1);
                    dietaComida.setDieta(dieta);

                    dietaComida.setPorcion(rs.getInt("porcion"));
                    dietaComida.setHorario(Horario.valueOf(rs.getString("horario")));
                    dietaComida.setEstado(rs.getBoolean("estado"));

                    dietaComidas.add(dietaComida);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al buscar Dietas por porción");
        }
        if (dietaComidas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontraron Dietas.", "Sin resultados", JOptionPane.WARNING_MESSAGE);
        }
        return dietaComidas;
    }

    public ArrayList<DietaComida> buscarPorIdComida(int idComida, int estado) {
        String SQL_SELECT = null;
        DietaComida dietaComida = null;
        ArrayList<DietaComida> dietaComidas = new ArrayList<>();

        switch (estado) {
            case 1:
                SQL_SELECT = "SELECT iddietacomida, idComida, idDieta, porcion, horario, estado FROM dietacomida WHERE idComida = ? AND estado = 1";
                break;
            case 0:
                SQL_SELECT = "SELECT iddietacomida, idComida, idDieta, porcion, horario, estado FROM dietacomida WHERE idComida = ? AND estado = 0";
                break;
            default:
                SQL_SELECT = "SELECT iddietacomida, idComida, idDieta, porcion, horario, estado FROM dietacomida WHERE idComida = ?";
                break;
        }

        try (PreparedStatement ps = con.prepareStatement(SQL_SELECT)) {
            ps.setInt(1, idComida);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    dietaComida = new DietaComida();
                    dietaComida.setId(rs.getInt("iddietacomida"));

                    int idDieta = rs.getInt("idDieta");
                    ComidaDAO comidaDAO = new ComidaDAO();
                    Comida comida = comidaDAO.buscar(idComida, 1);
                    dietaComida.setComida(comida);

                    DietaDAO dietaDAO = new DietaDAO();
                    Dieta dieta = dietaDAO.buscarPorId(idDieta, 1);
                    dietaComida.setDieta(dieta);

                    dietaComida.setPorcion(rs.getInt("porcion"));
                    dietaComida.setHorario(Horario.valueOf(rs.getString("horario")));
                    dietaComida.setEstado(rs.getBoolean("estado"));

                    dietaComidas.add(dietaComida);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al buscar Dietas por idComida");
        }
        if (dietaComidas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontraron Dietas", "Sin resultados", JOptionPane.WARNING_MESSAGE);
        }
        return dietaComidas;
    }

    public ArrayList<DietaComida> buscarPorHorario(String horario, int estado) {
        String SQL_SELECT = null;
        DietaComida dietaComida = null;
        ArrayList<DietaComida> dietaComidas = new ArrayList<>();

        switch (estado) {
            case 1:
                SQL_SELECT = "SELECT iddietacomida, idComida, idDieta, porcion, horario, estado FROM dietacomida WHERE horario = ? AND estado = 1";
                break;
            case 0:
                SQL_SELECT = "SELECT iddietacomida, idComida, idDieta, porcion, horario, estado FROM dietacomida WHERE horario = ? AND estado = 0";
                break;
            default:
                SQL_SELECT = "SELECT iddietacomida, idComida, idDieta, porcion, horario, estado FROM dietacomida WHERE horario = ?";
                break;
        }

        try (PreparedStatement ps = con.prepareStatement(SQL_SELECT)) {
            ps.setString(1, horario);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    dietaComida = new DietaComida();
                    dietaComida.setId(rs.getInt("iddietacomida"));

                    int idDieta = rs.getInt("idDieta");
                    ComidaDAO comidaDAO = new ComidaDAO();
                    Comida comida = comidaDAO.buscar(idDieta, 1);
                    dietaComida.setComida(comida);

                    DietaDAO dietaDAO = new DietaDAO();
                    Dieta dieta = dietaDAO.buscarPorId(idDieta, 1); // 1 significa que está activo
                    dietaComida.setDieta(dieta);

                    dietaComida.setPorcion(rs.getInt("porcion"));
                    dietaComida.setHorario(Horario.valueOf(rs.getString("horario")));
                    dietaComida.setEstado(rs.getBoolean("estado"));

                    dietaComidas.add(dietaComida);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al buscar Dietas por horario");
        }
        if (dietaComidas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontraron Dietas", "Sin resultados", JOptionPane.WARNING_MESSAGE);
        }
        return dietaComidas;
    }

    public void anularDietaComida(int idDietaComida) {
        String SQL_UPDATE_ESTADO = "UPDATE dietacomida SET estado = 0 WHERE iddietacomida = ?";

        try (PreparedStatement ps = con.prepareStatement(SQL_UPDATE_ESTADO)) {
            ps.setInt(1, idDietaComida);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Dieta anulada con éxito");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo anular");
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al anular la Dieta");
        }
    }

    public void eliminarDietaComida(int idDietaComida) {
        String SQL_DELETE = "DELETE FROM dietacomida WHERE iddietacomida = ?";

        try (PreparedStatement ps = con.prepareStatement(SQL_DELETE)) {
            ps.setInt(1, idDietaComida);

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Dieta eliminada con éxito");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar la Dieta");
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al eliminar la Dieta");
        }
    }
}
