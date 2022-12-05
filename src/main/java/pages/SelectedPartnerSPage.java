package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static java.time.Duration.ofSeconds;

public class SelectedPartnerSPage extends BasePage {
    public SelectedPartnerSPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//span[text()='Ավելացնել զամբյուղ']")
    protected WebElement buttonToAddToCart;
    @FindBy(xpath = "//img[@data-id='image']")
    protected List<WebElement> listOfFoods;
    protected String xpathForPreferredFood = "//h3[text()='%s']";
    protected String xpathForAmount = "//h3[text()='%s']/parent::div/parent::div//span[@data-id='amount']";

    public void chooseFoodAndAddToCard(String preferredFood) {
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions
                .elementToBeClickable(By.xpath(String.format(xpathForPreferredFood, preferredFood))));
        try {
            driver.findElement(By.xpath(String.format(xpathForPreferredFood, preferredFood))).click();
        } catch (Exception e) {
            clickByJavaScriptExecutor(driver.findElement(By.xpath(String.format(xpathForPreferredFood, preferredFood))));
        }
        buttonToAddToCart.click();
    }

    public int getSelectedFoodCost(String preferredFood) {
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions
                .elementToBeClickable(By.xpath(String.format(xpathForPreferredFood, preferredFood))));
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath(String.format(xpathForAmount, preferredFood))));
        return Integer.parseInt(driver.findElement(By.xpath(String.format(xpathForAmount, preferredFood))).getText());
    }

    public void waitSelectedFoodPageLoaded() {
        new WebDriverWait(driver, ofSeconds(20)).
                until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//img[@data-id='image']"), 0));
        new WebDriverWait(driver, ofSeconds(20)).
                until(ExpectedConditions.elementToBeClickable(listOfFoods.get(listOfFoods.size() - 1)));
    }
}
