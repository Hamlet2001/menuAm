package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.time.Duration.ofSeconds;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[text()='Պահպանված հասցեներ']")
    protected WebElement deliveryAddresses;
    @FindBy(xpath = "//ul[@data-id='addresses']/li")
    protected WebElement submitAddressButton;
    protected String xpathForFoodCategory = "//span[text()='%s']";

    public void setDeliveryAddress() {
        new WebDriverWait(driver, ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(deliveryAddresses));
        deliveryAddresses.click();
        new WebDriverWait(driver, ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(submitAddressButton));
        submitAddressButton.click();
    }

    public void chooseFoodType(String foodType) {
        try {
            new WebDriverWait(driver, ofSeconds(20)).until(ExpectedConditions
                    .elementToBeClickable(By.xpath(String.format(xpathForFoodCategory, foodType))));
            driver.findElement(By.xpath(String.format(xpathForFoodCategory, foodType))).click();
        } catch (TimeoutException e) {
            clickByJavaScriptExecutor(driver.findElement(By.xpath(String.format(xpathForFoodCategory, foodType))));
        }
    }
}
