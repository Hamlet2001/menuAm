package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static java.time.Duration.ofSeconds;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[text()='Պահպանված հասցեներ']")
    protected WebElement deliveryAddresses;
    @FindBy(xpath = "//ul[@data-id='addresses']/li")
    protected WebElement submitAddressButton;
    @FindBy(xpath = "//button[@aria-label='account control']")
    protected WebElement accountButton;
    @FindBy(xpath = "//button[text()='Ելք']")
    protected WebElement buttonForLogout;
    @FindBy(xpath = "//span[text()='Զեղչեր']")
    protected WebElement discountsButton;
    @FindBy(xpath = "//span[text()='Մուտք']")
    protected WebElement loginButton;

    protected String xpathForFoodCategory = "//span[text()='%s']";

    public void setDeliveryAddress() {
        new WebDriverWait(driver, ofSeconds(15)).until(ExpectedConditions.elementToBeClickable(deliveryAddresses));
        deliveryAddresses.click();
        new WebDriverWait(driver, ofSeconds(15)).until(ExpectedConditions.elementToBeClickable(submitAddressButton));
        submitAddressButton.click();
    }

    public void chooseFoodType(String foodType) {
        try {
            new WebDriverWait(driver, ofSeconds(15)).until(ExpectedConditions
                    .elementToBeClickable(By.xpath(String.format(xpathForFoodCategory, foodType))));
            driver.findElement(By.xpath(String.format(xpathForFoodCategory, foodType))).click();
        } catch (Exception e) {
            clickByJavaScriptExecutor(driver.findElement(By.xpath(String.format(xpathForFoodCategory, foodType))));
        }
    }

    public void logout() {
        try {
            new WebDriverWait(driver, ofSeconds(15)).until(ExpectedConditions
                    .elementToBeClickable(accountButton));
            accountButton.click();
        } catch (Exception e) {
            clickByJavaScriptExecutor(accountButton);
        }
        try {
            new WebDriverWait(driver, ofSeconds(15)).until(ExpectedConditions
                    .elementToBeClickable(buttonForLogout));
            buttonForLogout.click();
        } catch (Exception e) {
            clickByJavaScriptExecutor(buttonForLogout);
        }
        new WebDriverWait(driver,ofSeconds(15)).until(ExpectedConditions.elementToBeClickable(loginButton));
    }

    public void clickOnDiscountsButton() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions
                    .elementToBeClickable(discountsButton));
            discountsButton.click();
        } catch (Exception e) {
            clickByJavaScriptExecutor(discountsButton);
        }
    }

    public void waitForLoginPageLoaded() {
        new WebDriverWait(driver, ofSeconds(20)).
                until(ExpectedConditions.elementToBeClickable(accountButton));
    }
}
