import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PruebasPhantomjsIT {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "/usr/bin/phantomjs");
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[]{"--web-security=no", "--ignore-ssl-errors=yes"});
        driver = new PhantomJSDriver(caps);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void tituloIndexTest() {
        driver.navigate().to("http://localhost:8080/Baloncesto/");
        assertEquals("Votacion mejor jugador liga ACB", driver.getTitle(), "El titulo no es correcto");
    }

    @Test
    public void resetVotosYVerificar() {
        driver.get("http://localhost:8080/Baloncesto/");

        // Haz clic en el botón para poner los votos a cero
        WebElement resetButton = driver.findElement(By.name("accion"));
        resetButton.click();

        // Asumimos que el botón para ver votos es el segundo botón con el nombre "accion"
        WebElement verVotosButton = driver.findElements(By.name("accion")).get(1);
        verVotosButton.click();

        // Verifica que la página tiene el título correcto
        assertEquals("Votación Mejor Jugador Liga ACB", driver.getTitle());

        // Verifica que los votos son todos cero
        List<WebElement> votos = driver.findElements(By.xpath("//table/tbody/tr/td[2]")); // Columna de votos
        for (WebElement voto : votos) {
            assertEquals("0", voto.getText());
        }
    }
}
