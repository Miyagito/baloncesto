import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PruebasPhantomjsIT {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "/usr/bin/phantomjs");
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[]{"--web-security=no", "--ignore-ssl-errors=yes"});
        driver = new PhantomJSDriver(caps);
        wait = new WebDriverWait(driver, 10);
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
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://localhost:8080/Baloncesto/");

        // Buscar y hacer clic en el botón "Poner votos a cero"
        WebElement botonResetear = driver.findElement(By.xpath("//input[@type='submit'][@name='accion'][@value='resetVotos']"));
        botonResetear.click();

        // Esperar a que el botón "Volver al inicio" sea visible y hacer clic en él para asegurarse de que la página se ha recargado
        WebElement botonVolverAlInicio = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Volver al inicio']")));
        botonVolverAlInicio.click();

        // Esperar a que la página de inicio cargue de nuevo
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='submit'][@name='B1'][@value='Votar']")));

        // Buscar y hacer clic en el botón "Ver votos"
        WebElement botonVerVotos = driver.findElement(By.xpath("//input[@type='submit'][@name='accion'][@value='VerVotos']"));
        botonVerVotos.click();

        // Esperar a que la página de votos se cargue y verificar que todos los votos son cero
        List<WebElement> filasVotos = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//table//tr/td[2]"))); // Segunda columna de cada fila

        for (WebElement voto : filasVotos) {
            assertEquals("0", voto.getText().trim(), "El voto del jugador no es cero.");
        }
    }

    @Test
    public void votarJugadorOtroYVerificar() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://localhost:8080/Baloncesto/");

        // Introducir el nombre de un nuevo jugador
        WebElement campoNombreOtro = driver.findElement(By.name("txtOtros"));
        campoNombreOtro.sendKeys("Jugador Nuevo");

        // Marcar la opción "Otro"
        WebElement radioOtro = driver.findElement(By.xpath("//input[@type='radio'][@name='R1'][@value='Otros']"));
        radioOtro.click();

        // Pulsar el botón "Votar"
        WebElement botonVotar = driver.findElement(By.xpath("//input[@type='submit'][@name='B1'][@value='Votar']"));
        botonVotar.click();

        // Volver a la página principal
        driver.navigate().to("http://localhost:8080/Baloncesto/");

        // Simular la pulsación del botón "Ver votos"
        WebElement botonVerVotos = driver.findElement(By.xpath("//input[@type='submit'][@name='accion'][@value='VerVotos']"));
        botonVerVotos.click();

        // Comprobar que el nuevo jugador tiene 1 voto
        WebElement votoJugadorNuevo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(), 'Jugador Nuevo')]/following-sibling::td")));
        assertEquals("1", votoJugadorNuevo.getText().trim(), "El jugador nuevo no tiene 1 voto.");
    }

}
