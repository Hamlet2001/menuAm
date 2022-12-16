package pages;

import initDriver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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
    private WebElement deliveryAddresses;
    @FindBy(xpath = "//ul[@data-id='addresses']/li")
    private WebElement submitAddressButton;
    @FindBy(xpath = "//span[text()='Զեղչեր']")
    private WebElement discountsButton;
    @FindBy(xpath = "//button[@aria-label='account control']")
    private WebElement accountButton;
    @FindBy(xpath = "//img[@alt='Address']")
    private WebElement shippingAddressIsSelected;

    protected String xpathForFoodCategory = "//span[text()='%s']";

    public void setDeliveryAddress() {
        try {
            new WebDriverWait(driver, ofSeconds(15)).until(ExpectedConditions.elementToBeClickable(deliveryAddresses));
            deliveryAddresses.click();
        } catch (Exception e) {
            clickByJavaScriptExecutor(deliveryAddresses);
        }
        try {
            if (myDeliveryAddressIsSelected()) {

            }
        } catch (NoSuchElementException e) {
            try {
                new WebDriverWait(driver, ofSeconds(15)).until(ExpectedConditions.elementToBeClickable(submitAddressButton));
                submitAddressButton.click();
            } catch (Exception ex) {
                clickByJavaScriptExecutor(submitAddressButton);
            }
        }
    }

    public boolean myDeliveryAddressIsSelected() {
        return shippingAddressIsSelected.isDisplayed();
    }

    public FilteredFoodPage chooseFoodType(String foodType) {
        try {
            new WebDriverWait(driver, ofSeconds(15)).until(ExpectedConditions
                    .elementToBeClickable(By.xpath(String.format(xpathForFoodCategory, foodType))));
            driver.findElement(By.xpath(String.format(xpathForFoodCategory, foodType))).click();
        } catch (Exception e) {
            clickByJavaScriptExecutor(driver.findElement(By.xpath(String.format(xpathForFoodCategory, foodType))));
        }
        return new FilteredFoodPage(DriverFactory.getDriver());
    }

    public DiscountsPage clickOnDiscountsButton() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions
                    .elementToBeClickable(discountsButton));
            discountsButton.click();
        } catch (Exception e) {
            clickByJavaScriptExecutor(discountsButton);
        }
        return new DiscountsPage(DriverFactory.getDriver());
    }

    public LoginPage waitForLoginPageLoaded() {
        new WebDriverWait(driver, ofSeconds(20)).
                until(ExpectedConditions.elementToBeClickable(accountButton));
        return this;
    }
}
