import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.time.Duration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(value = Parameterized.class)
public class UiTest {
    private Capabilities capabilities;
    private WebDriver driver;
    private final By btn = By.id("btn");
    private final By content = By.id("content");

    public UiTest(Capabilities capabilities) {
        this.capabilities = capabilities;
    }

    @Parameters(name = "Browser {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new ChromeOptions()},
                {new FirefoxOptions()},
                {new EdgeOptions()}
        });
    }

    @Before
    public void setup() throws Exception {
        this.driver = new RemoteWebDriver(new URL("http://selenium-hub:4444"), this.capabilities);
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