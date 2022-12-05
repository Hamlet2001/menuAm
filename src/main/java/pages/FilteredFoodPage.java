package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.time.Duration.ofSeconds;

public class FilteredFoodPage extends BasePage {

    public FilteredFoodPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//h2[@data-id='name']")
    protected List<WebElement> listOdPartners;
    protected String xpathForPartner = "//h2[text()='%s']";
    protected String xpathForPartnerShippingCost =
            "//h2[text()='%s']//parent::a/parent::div/parent::div/following-sibling::div//span[@data-id='fee']";

    public void waitForAllPartnersLoaded() {
        new WebDriverWait(driver, ofSeconds(20)).
                until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//h2[@data-id='name']"), 0));
        new WebDriverWait(driver, ofSeconds(20)).
                until(ExpectedConditions.elementToBeClickable(listOdPartners.get(listOdPartners.size() - 1)));
    }

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
}
