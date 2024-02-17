import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModeloDatos {

    private Connection con;
    private Statement set;
    private ResultSet rs;
    private static final Logger LOGGER = Logger.getLogger(ModeloDatos.class.getName());

    public void abrirConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Con variables de entorno
            String dbHost = System.getenv("DATABASE_HOST");
            String dbPort = System.getenv("DATABASE_PORT");
            String dbName = System.getenv("DATABASE_NAME");
            String dbUser = System.getenv("DATABASE_USER");
            String dbPass = System.getenv("DATABASE_PASS");
            String url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
            con = DriverManager.getConnection(url, dbUser, dbPass);
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.SEVERE, "No se ha podido conectar", e);
        }
    }

    public boolean existeJugador(String nombre) {
        boolean existe = false;
        try {
            set = con.createStatement();
            rs = set.executeQuery("SELECT * FROM Jugadores WHERE nombre = '" + nombre + "'");
            existe = rs.next();
            rs.close();
            set.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "No lee de la tabla", e);
        }
        return existe;
    }

    public void actualizarJugador(String nombre) {
        try {
            set = con.createStatement();
            int count = set.executeUpdate("UPDATE Jugadores SET votos=votos+1 WHERE nombre = '" + nombre + "'");
            if (count == 0) {
                LOGGER.log(Level.INFO, "No se encontró el jugador para actualizar: " + nombre);
            }
            set.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "No modifica la tabla", e);
        }
    }

    public void insertarJugador(String nombre) {
        try {
            set = con.createStatement();
            int count = set.executeUpdate("INSERT INTO Jugadores (nombre, votos) VALUES ('" + nombre + "', 1)");
            if (count == 0) {
                LOGGER.log(Level.INFO, "No se pudo insertar el jugador: " + nombre);
            }
            set.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "No inserta en la tabla", e);
        }
    }

    public int getVotosJugador(String nombre) {
        int votos = 0;
        String sql = "SELECT votos FROM Jugadores WHERE nombre = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    votos = rs.getInt("votos");
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener los votos del jugador", e);
        }
        return votos;
    }

    public void resetearVotos() {
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate("UPDATE Jugadores SET votos = 0");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "No se pudo resetear los votos", e);
        }
    }

    public void cerrarConexion() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al cerrar la conexión", e);
        }
    }
}
