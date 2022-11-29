package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectedFoodPage extends BasePage {

    public SelectedFoodPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//span[text()='Ավելացնել զամբյուղ']")
    protected WebElement buttonToAddToCart;
    protected String xpathForPartner = "//h2[text()='%s']";
    protected String xpathForPartnerShippingCost = "//h2[text()='%s']//parent::a/parent::div/parent::div/following-sibling::div//span[@data-id='fee']";
    protected String xpathForPreferredFood = "//h3[text()='%s']";
    protected String xpathForAmount = "//h3[text()='%s']/parent::div/parent::div//span[@data-id='amount']";

    public void choosePartner(String partner) {
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions
                .elementToBeClickable(By.xpath(String.format(xpathForPartner, partner))));
        driver.findElement(By.xpath(String.format(xpathForPartner, partner))).click();
    }

    public int getShippingCost(String partner) {
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath(String.format(xpathForPartnerShippingCost, partner))));
        String string = driver.findElement(By.xpath(String.format(xpathForPartnerShippingCost, partner))).getText();
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(string);
        matcher.find();
        int shippingCost = Integer.parseInt(matcher.group());
        return shippingCost;
    }

    public int getSelectedFoodCost(String preferredFood) {
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions
                .elementToBeClickable(By.xpath(String.format(xpathForPreferredFood, preferredFood))));
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath(String.format(xpathForAmount, preferredFood))));
        return Integer.parseInt(driver.findElement(By.xpath(String.format(xpathForAmount, preferredFood))).getText());
    }

    public void chooseFoodAndAddToCard(String preferredFood) {
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions
                .elementToBeClickable(By.xpath(String.format(xpathForPreferredFood, preferredFood))));
        driver.findElement(By.xpath(String.format(xpathForPreferredFood, preferredFood))).click();
        buttonToAddToCart.click();
    }
}
