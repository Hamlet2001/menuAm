package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    protected final String HOME_URL = "https://menu.am";

    public void openHomePage() {
        driver.manage().window().maximize();
        driver.get(HOME_URL);
    }

    @FindBy(xpath = "//div[@role='dialog']")
    protected WebElement advertisement;
    @FindBy(xpath = "//span[text() = 'Լավ']")
    protected WebElement buttonForCloseAdvertisement;
    @FindBy(xpath = "//span[text()='Մուտք']")
    protected WebElement loginButton;
    @FindBy(xpath = "//span[text()='Ցույց տալ ավելին']")
    protected WebElement showMoreButton;

    public void loginAccount() {
        loginButton.click();
        driver.findElement(By.cssSelector("input[name='user']")).sendKeys("hamlet2001@inbox.ru");
        WebElement pwd = driver.findElement(By.cssSelector("input[name='pwd']"));
        pwd.sendKeys("h123456");
        pwd.submit();
    }

    public void waitForHomePageLoaded() {
        try {
            if (advertisement.isDisplayed()) {
                buttonForCloseAdvertisement.click();
            }
        } catch (NoSuchElementException ignored) {
        }
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.
                elementToBeClickable(showMoreButton));
    }
}
