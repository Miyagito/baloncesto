import org.junit.jupiter.api.Test;
import java.util.logging.Logger;
import java.util.logging.Level;
import static org.junit.jupiter.api.Assertions.*;

public class ModeloDatosTest {
    
    private static final Logger LOGGER = Logger.getLogger(ModeloDatosTest.class.getName());

    @Test
    void testExisteJugador() {
        LOGGER.info("Prueba de existeJugador");
        String nombre = "NombreDelJugador"; // Coloca un nombre válido para la prueba
        ModeloDatos instance = new ModeloDatos();
        instance.abrirConexion();
        boolean result = instance.existeJugador(nombre);
        instance.cerrarConexion();
        // Suponiendo que el nombre del jugador existe en la base de datos, el resultado esperado es 'true'
        assertTrue(result, "El jugador debería existir en la base de datos.");
    }

    @Test
void testActualizarJugador() {
    String nombre = "JugadorExistente"; // Asegúrate de que este jugador ya exista en la base de datos para la prueba
    ModeloDatos instance = new ModeloDatos();
    instance.abrirConexion();
    
    // Realiza la actualización
    instance.actualizarJugador(nombre);

    // Ahora verifica que los votos del jugador se hayan incrementado en 1
    // Puedes hacer esto realizando una consulta a la base de datos y comprobando el valor de los votos.
    // Asumiendo que tienes un método que obtiene los votos de un jugador, sería algo así:
    int votosActualizados = instance.getVotosJugador(nombre);
    assertTrue(votosActualizados > 0, "Los votos del jugador deberían haberse incrementado.");

    instance.cerrarConexion();
}
}
