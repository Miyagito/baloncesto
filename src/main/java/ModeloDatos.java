import java.sql.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class ModeloDatos {

    private Connection con;
    private Statement set;
    private ResultSet rs;
    private static final Logger LOGGER = Logger.getLogger(ModeloDatos.class.getName());

    public void abrirConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbHost = System.getenv().get("DATABASE_HOST");
            String dbPort = System.getenv().get("DATABASE_PORT");
            String dbName = System.getenv().get("DATABASE_NAME");
            String dbUser = System.getenv().get("DATABASE_USER");
            String dbPass = System.getenv().get("DATABASE_PASS");

            String url = dbHost + ":" + dbPort + "/" + dbName;
            con = DriverManager.getConnection(url, dbUser, dbPass);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "No se ha podido conectar", e);
        }
    }

    public boolean existeJugador(String nombre) {
        boolean existe = false;
        try {
            set = con.createStatement();
            rs = set.executeQuery("SELECT * FROM Jugadores");
            while (rs.next()) {
                String cad = rs.getString("Nombre").trim();
                if (cad.equals(nombre.trim())) {
                    existe = true;
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "No lee de la tabla", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (set != null) set.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error al cerrar Statement o ResultSet", e);
            }
        }
        return existe;
    }

    public void actualizarJugador(String nombre) {
        try {
            set = con.createStatement();
            set.executeUpdate("UPDATE Jugadores SET votos = votos + 1 WHERE nombre LIKE '%" + nombre + "%'");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "No modifica la tabla", e);
        } finally {
            try {
                if (set != null) set.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error al cerrar Statement", e);
            }
        }
    }

    public void insertarJugador(String nombre) {
        try {
            set = con.createStatement();
            set.executeUpdate("INSERT INTO Jugadores (nombre, votos) VALUES ('" + nombre + "', 1)");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "No inserta en la tabla", e);
        } finally {
            try {
                if (set != null) set.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error al cerrar Statement", e);
            }
        }
    }

    public void resetearVotos() {
        try {
            set = con.createStatement();
            set.executeUpdate("UPDATE Jugadores SET votos=0");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "No se pudo resetear los votos", e);
        } finally {
            try {
                if (set != null) set.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error al cerrar Statement", e);
            }
        }
    }
    
    public void cerrarConexion() {
        try {
            if (con != null) con.close();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error al cerrar la conexión", e);
        }
    }
}
