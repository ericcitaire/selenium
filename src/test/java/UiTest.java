import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.time.Duration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UiTest {
    private WebDriver driver;
    private final By btn = By.id("btn");
    private final By content = By.id("content");

    @Before
    public void setup() throws Exception {
        ChromeOptions chromeOptions = new ChromeOptions();
        this.driver = new RemoteWebDriver(new URL("http://chrome:4444"), chromeOptions);
    }

    @After
    public void teardown() throws Exception {
        this.driver.quit();
    }

    @Test
    public void sayHello() throws Exception {
        driver.get("http://sample:8000");
        assertEquals("Sample", driver.getTitle());
        String contentBefore = driver.findElement(content).getText();
        assertEquals("", contentBefore);
        driver.findElement(btn).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.textToBe(content, "Hello"));
        String contentAfter = driver.findElement(content).getText();
        assertEquals("Hello", contentAfter);
    }
}