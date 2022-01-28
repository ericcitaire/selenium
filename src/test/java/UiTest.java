import static org.junit.Assert.assertEquals;

import java.net.URL;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class UiTest {
    @Test
    public void test() throws Exception {
        ChromeOptions chromeOptions = new ChromeOptions();
        WebDriver driver = new RemoteWebDriver(new URL("http://chrome:4444"), chromeOptions);
        driver.get("http://sample:8000");
        assertEquals("Sample", driver.getTitle());
        driver.quit();
    }
}