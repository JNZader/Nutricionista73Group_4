package Conexion;

import static Conexion.Conexion.getConnection;
import Entidades.Comida;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ComidaDAO {

    private ComidaDAO cd;
    private Connection con;

    public ComidaDAO() {
        con = getConnection();
    }

    public Comida insertar(Comida comida) {
        String SQL_INSERT = "INSERT INTO comida (nombre, detalle, cantCalorias, estado) "
                + "SELECT ?, ?, ?, ? "
                + "WHERE NOT EXISTS (SELECT 1 FROM comida WHERE nombre = ?)";

        try (PreparedStatement ps = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, comida.getNombre());
            ps.setString(2, comida.getDetalle());
            ps.setInt(3, comida.getCantCalorias());
            ps.setBoolean(4, comida.isEstado());
            ps.setString(5, comida.getNombre());

            int insCom = ps.executeUpdate();
            if (insCom == 1) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        comida.setIdComida(rs.getInt(1));
                        JOptionPane.showMessageDialog(null, "Comida añadida con éxito");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Inserción fallida. Existe una comida con ese nombre.");
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al insertar comida");
        }
        return comida;
    }

    public void modificar(Comida comida) {
        // Verifica si el nuevo nombre ya existe en la base de datos
        String SQL_CHECK_DUPLICATE = "SELECT COUNT(*) FROM comida WHERE nombre = ? AND idComida <> ?";

        try (PreparedStatement psCheck = con.prepareStatement(SQL_CHECK_DUPLICATE)) {
            psCheck.setString(1, comida.getNombre());
            psCheck.setInt(2, comida.getIdComida());

            try (ResultSet rsCheck = psCheck.executeQuery()) {
                if (rsCheck.next() && rsCheck.getInt(1) > 0) {
                    JOptionPane.showMessageDialog(null, "Ya existe una comida con el mismo nombre.");
                    return;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al verificar el nombre duplicado");
            return;
        }

        // Si  no existe actualiza
        String SQL_UPDATE = "UPDATE comida SET nombre=?, detalle=?, cantCalorias=?, estado=? WHERE idComida=?";

        try (PreparedStatement ps = con.prepareStatement(SQL_UPDATE)) {
            ps.setString(1, comida.getNombre());
            ps.setString(2, comida.getDetalle());
            ps.setInt(3, comida.getCantCalorias());
            ps.setBoolean(4, comida.isEstado());
            ps.setInt(5, comida.getIdComida());

            int mod = ps.executeUpdate();
            if (mod > 0) {
                JOptionPane.showMessageDialog(null, "Modificación realizada");
            } else {
                JOptionPane.showMessageDialog(null, "Modificación fallida");
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla comida");
        }
    }

    public void borrarTotal(Comida comida) {
        String SQL_DELETE = "DELETE FROM comida WHERE idComida = ? AND idComida NOT IN (SELECT idComida  FROM dietacomida)";

        try (PreparedStatement ps = con.prepareStatement(SQL_DELETE)) {
            ps.setInt(1, comida.getIdComida());
            int del = ps.executeUpdate();
            if (del > 0) {
                JOptionPane.showMessageDialog(null, "Comida eliminada con éxito");
            } else {
                JOptionPane.showMessageDialog(null, "No se puede eliminar la comida debido a relaciones con otras tablas.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error SQL al eliminar una comida");
        }
    }

    public void borrar(int id) {
        String SQL_UPDATE = "UPDATE comida SET estado = 0 WHERE idComida = ? ";

        try (PreparedStatement ps = con.prepareStatement(SQL_UPDATE)) {
            ps.setInt(1, id);
            int updel = ps.executeUpdate();
            if (updel == 1) {
                JOptionPane.showMessageDialog(null, "Se ha eliminado la comida", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar comida", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error SQL al eliminar una comida");
        }
    }

    public ArrayList<Comida> listarComidas(int estado) {
        String SQL_SELECT = "";
        Comida comida = null;
        ArrayList<Comida> comidas = new ArrayList<>();

        switch (estado) {
            case 1:
                SQL_SELECT = "SELECT idComida, nombre, detalle, cantCalorias, estado FROM comida WHERE estado=1";
                break;
            case 0:
                SQL_SELECT = "SELECT idComida, nombre, detalle, cantCalorias, estado FROM comida WHERE estado=0";
                break;
            default:
                SQL_SELECT = "SELECT idComida, nombre, detalle, cantCalorias, estado FROM comida";
                break;
        }

        try (PreparedStatement ps = con.prepareStatement(SQL_SELECT); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                comida = new Comida();
                comida.setIdComida(rs.getInt("idcomida"));
                comida.setNombre(rs.getString("nombre"));
                comida.setDetalle(rs.getString("detalle"));
                comida.setCantCalorias(rs.getInt("cantcalorias"));
                comida.setEstado(rs.getBoolean("estado"));

                comidas.add(comida);
            }
            if (comidas.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se encontraron comidas.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al obtener comidas");
        }
        return comidas;
    }

    public ArrayList<Comida> buscarXCantCalorias(int cantCalorias, int condicion) {
        String SQL_SELECT = "";
        Comida calorias = null;
        ArrayList<Comida> comidas = new ArrayList<>();
        condicion = (condicion > 0) ? 1 : (condicion < 0) ? -1 : 0;

        switch (condicion) {
            case 0:
                SQL_SELECT = "SELECT idComida, nombre, detalle, cantCalorias, estado FROM comida WHERE cantCalorias = ? AND estado=1";
                break;
            case 1:
                SQL_SELECT = "SELECT idComida, nombre, detalle, cantCalorias, estado FROM comida WHERE cantCalorias > ? AND estado=1";
                break;
            case -1:
                SQL_SELECT = "SELECT idComida, nombre, detalle, cantCalorias, estado FROM comida WHERE cantCalorias < ? AND estado=1";
                break;
        }
        try (PreparedStatement ps = con.prepareStatement(SQL_SELECT)) {
            ps.setInt(1, cantCalorias);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    calorias = new Comida();
                    calorias.setIdComida(rs.getInt("idcomida"));
                    calorias.setNombre(rs.getString("nombre"));
                    calorias.setDetalle(rs.getString("detalle"));
                    calorias.setCantCalorias(rs.getInt("cantcalorias"));
                    calorias.setEstado(true);
                    comidas.add(calorias);
                }
                if (comidas.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No se encontraron comidas con las calorias especificadas.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al buscar comidas por calorias");
        }
        return comidas;
    }

    public Comida buscar(int idComida, int estado) {
        String SQL_SELECT_ID = "";
        Comida comida = null;

        switch (estado) {
            case 1:
                SQL_SELECT_ID = "SELECT * FROM comida WHERE idComida = ? AND estado=1";
                break;
            case 0:
                SQL_SELECT_ID = "SELECT * FROM comida WHERE idComida = ? AND estado=0";
                break;
            default:
                SQL_SELECT_ID = "SELECT * FROM comida WHERE idComida = ?";
                break;
        }
        try (PreparedStatement ps = con.prepareStatement(SQL_SELECT_ID)) {
            ps.setInt(1, idComida);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    comida = new Comida();
                    comida.setIdComida(rs.getInt("idcomida"));
                    comida.setNombre(rs.getString("nombre"));
                    comida.setDetalle(rs.getString("detalle"));
                    comida.setCantCalorias(rs.getInt("cantcalorias"));
                    comida.setEstado(rs.getBoolean("estado"));
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró ninguna comida con ese ID.", "Comida no encontrada", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al obtener la Dieta por ID");
        }
        return comida;
    }

    public Comida buscarPorNombre(String nombre, int estado) {
        String SQL_SELECT_NOMBRE = "";
        Comida comida = null;

        switch (estado) {
            case 1:
                SQL_SELECT_NOMBRE = "SELECT * FROM comida WHERE nombre = ? AND estado = 1";
                break;
            case 0:
                SQL_SELECT_NOMBRE = "SELECT * FROM comida WHERE nombre = ? AND estado = 0";
                break;
            default:
                SQL_SELECT_NOMBRE = "SELECT * FROM comida WHERE nombre = ?";
                break;
        }
        try (PreparedStatement ps = con.prepareStatement(SQL_SELECT_NOMBRE)) {
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    comida = new Comida();
                    comida.setIdComida(rs.getInt("idcomida"));
                    comida.setNombre(rs.getString("nombre"));
                    comida.setDetalle(rs.getString("detalle"));
                    comida.setCantCalorias(rs.getInt("cantcalorias"));
                    comida.setEstado(rs.getBoolean("estado"));
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró ninguna comida con el nombre: " + nombre, "Comida no encontrada", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al obtener la comida por nombre");
        }
        return comida;
    }

    public ArrayList<Comida> buscarPorDetalle(String detalle, int estado) {
        String SQL_SELECT_DETALLE = "";
        ArrayList<Comida> comidas = new ArrayList<>();

        switch (estado) {
            case 1:
                SQL_SELECT_DETALLE = "SELECT * FROM comida WHERE detalle LIKE ? AND estado = 1";
                break;
            case 0:
                SQL_SELECT_DETALLE = "SELECT * FROM comida WHERE detalle LIKE ? AND estado = 0";
                break;
            default:
                SQL_SELECT_DETALLE = "SELECT * FROM comida WHERE detalle LIKE ?";
                break;
        }

        try (PreparedStatement ps = con.prepareStatement(SQL_SELECT_DETALLE)) {
            ps.setString(1, "%" + detalle + "%"); // se usa "%" para buscar el detalle en cualquier parte del campo
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Comida comida = new Comida();
                    comida.setIdComida(rs.getInt("idcomida"));
                    comida.setNombre(rs.getString("nombre"));
                    comida.setDetalle(rs.getString("detalle"));
                    comida.setCantCalorias(rs.getInt("cantcalorias"));
                    comida.setEstado(rs.getBoolean("estado"));
                    comidas.add(comida);
                }
                if (comidas.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No se encontraron comidas con ese detalle.", "Resultado Vacío", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "Error al obtener comidas por detalle");
        }

        return comidas;
    }
}
