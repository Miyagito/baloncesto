import java.sql.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class ModeloDatos {

    private Connection con;
    private static final Logger LOGGER = Logger.getLogger(ModeloDatos.class.getName());
    private static final String ERROR_CLOSING_STATEMENT = "Error al cerrar Statement";
    private static final String ERROR_MODIFYING_TABLE = "No modifica la tabla";
    private static final String ERROR_INSERTING_TABLE = "No inserta en la tabla";
    private static final String ERROR_RESETTING_VOTES = "No se pudo resetear los votos";

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
        String sql = "SELECT * FROM Jugadores WHERE nombre = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            try (ResultSet rs = pstmt.executeQuery()) {
                existe = rs.next();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "No se pudo comprobar la existencia del jugador", e);
        }
        return existe;
    }

    public void actualizarJugador(String nombre) {
        PreparedStatement pstmt = null;
        try {
            String sql = "UPDATE Jugadores SET votos = votos + 1 WHERE nombre = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, nombre);
            pstmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, ERROR_MODIFYING_TABLE, e);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, ERROR_CLOSING_STATEMENT, e);
            }
        }
    }
    
    public void insertarJugador(String nombre) {
        PreparedStatement pstmt = null;
        try {
            String sql = "INSERT INTO Jugadores (nombre, votos) VALUES (?, 1)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, nombre);
            pstmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, ERROR_INSERTING_TABLE, e);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, ERROR_CLOSING_STATEMENT, e);
            }
        }
    }
    
    public void resetearVotos() {
        PreparedStatement pstmt = null;
        try {
            String sql = "UPDATE Jugadores SET votos = 0";
            pstmt = con.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, ERROR_RESETTING_VOTES, e);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, ERROR_CLOSING_STATEMENT, e);
            }
        }
    }

    public void cerrarConexion() {
        try {
            if (con != null) con.close();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error al cerrar la conexión", e);
        }
    }
}
