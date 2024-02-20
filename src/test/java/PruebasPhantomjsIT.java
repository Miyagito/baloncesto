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
    
        WebElement botonResetear = driver.findElement(By.xpath("//input[@type='submit'][@name='accion'][@value='resetVotos']"));
        botonResetear.click();
    
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='submit'][@name='B1'][@value='Votar']")));
    
        WebElement botonVerVotos = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='submit'][@name='accion'][@value='VerVotos']")));
        botonVerVotos.click();
    
        List<WebElement> filasVotos = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//table//tr/td[2]"))); // Segunda columna de cada fila
    
        for (WebElement voto : filasVotos) {
            assertEquals("0", voto.getText().trim(), "El voto del jugador no es cero.");
        }
    }
}
