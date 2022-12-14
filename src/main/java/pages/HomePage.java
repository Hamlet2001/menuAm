package pages;

import initDriver.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    protected final String HOME_URL = "https://menu.am";

    public HomePage openHomePage() {
        driver.manage().window().maximize();
        driver.get(HOME_URL);
        return this;
    }

    @FindBy(xpath = "//div[@role='dialog']")
    private WebElement advertisement;
    @FindBy(xpath = "//span[text() = 'Լավ']")
    private WebElement buttonForCloseAdvertisement;
    @FindBy(xpath = "//span[text()='Մուտք']")
    private WebElement loginButton;
    @FindBy(xpath = "//span[text()='Ցույց տալ ավելին']")
    private WebElement showMoreButton;

    public LoginPage signIn() {
        try {
            if (advertisement.isDisplayed()) {
                new WebDriverWait(driver, Duration.ofSeconds(15)).
                        until(ExpectedConditions.elementToBeClickable(buttonForCloseAdvertisement));
                buttonForCloseAdvertisement.click();
            }
        } catch (NoSuchElementException ignored) {
        }
        try {
            new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(loginButton));
            loginButton.click();
        } catch (TimeoutException e) {
            clickByJavaScriptExecutor(loginButton);
        }
        driver.findElement(By.cssSelector("input[name='user']")).sendKeys("hamlet2001@inbox.ru");
        driver.findElement(By.cssSelector("input[name='pwd']")).sendKeys("h123456", Keys.ENTER);
        return new LoginPage(DriverFactory.getDriver());
    }

//    public void waitForHomePageLoaded() {
//        try {
//            if (advertisement.isDisplayed()) {
//                new WebDriverWait(driver, Duration.ofSeconds(15)).
//                        until(ExpectedConditions.elementToBeClickable(buttonForCloseAdvertisement));
//                buttonForCloseAdvertisement.click();
//            }
//        } catch (NoSuchElementException ignored) {
//        }
//        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.
//                elementToBeClickable(showMoreButton));
//    }

    @Override
    protected void load() {

    }

    @Override
    protected void isLoaded() throws Error {
        try {
            if (advertisement.isDisplayed()) {
                new WebDriverWait(driver, Duration.ofSeconds(15)).
                        until(ExpectedConditions.elementToBeClickable(buttonForCloseAdvertisement));
                buttonForCloseAdvertisement.click();
            }
        } catch (NoSuchElementException ignored) {
        }
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.
                elementToBeClickable(showMoreButton));
    }
}
