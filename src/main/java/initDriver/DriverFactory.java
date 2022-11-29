package initDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

public class DriverFactory {
    private static WebDriver driver;

    public static void initDriver(BrowserType browser) {
        if (driver == null) {
            switch (browser) {
                case CHROME -> {
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                }
                case FIREFOX -> {
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                }
                case SAFARI -> {
                    WebDriverManager.safaridriver().setup();
                    driver = new SafariDriver();
                }
            }
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }
}