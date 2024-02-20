import com.miapp.baloncesto.ModeloDatos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.miapp.baloncesto.Jugador;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;



import static org.junit.jupiter.api.Assertions.*;

public class ModeloDatosTest {


    @Mock
    private Connection mockConnection;
    @Mock
    private Statement mockStatement;
    @Mock
    private ResultSet mockResultSet;

    private ModeloDatos modeloDatos;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("votos")).thenReturn(1); // Antes del update
        
        modeloDatos = new ModeloDatos();
        modeloDatos.setConnection(mockConnection); // Asumimos que existe este método en com.miapp.baloncesto.ModeloDatos
    }

    @Test
    public void testActualizarJugadorIncrementaVotos() throws Exception {
        String nombreJugador = "Llull";

        // Simula el update y el comportamiento esperado de obtenerJugadores()
        when(mockStatement.executeUpdate(anyString())).thenReturn(1);
        when(mockResultSet.getInt("votos")).thenReturn(2); // Después del update

        // Ejecuta el método a probar
        modeloDatos.actualizarJugador(nombreJugador);

        // Verifica que el update se ejecutó
        verify(mockStatement, times(1)).executeUpdate("UPDATE Jugadores SET votos=votos+1 WHERE nombre LIKE '%" + nombreJugador + "%'");

        // Verifica que obtenerJugadores() devuelve la lista correcta
        List<Jugador> jugadores = modeloDatos.obtenerJugadores();
        Jugador jugador = jugadores.get(0);
        assertEquals(2, jugador.getVotos());
    }

    @Test
    public void testExisteJugador() {
        System.out.println("Prueba de existeJugador");
        String nombre = "";
        ModeloDatos instance = new ModeloDatos();
        boolean expResult = false;
        // Aquí debería continuar el código para realizar la prueba
        boolean result = instance.existeJugador(nombre);
        assertEquals(expResult, result);
        //fail("Fallo forzado.");
    }
}