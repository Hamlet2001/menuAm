package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//button[@aria-label='basket']")
    protected WebElement cartButton;
    @FindBy(xpath = "//span[@data-id='cost']")
    protected WebElement spanForCost;
    @FindBy(xpath = "//span[contains(@class, 'count')]/span")
    protected WebElement spanForCountOfItemsInCart;
    @FindBy(xpath = "//span[@data-id='partnerDelivery']")
    protected WebElement spanForDeliveryCost;
    @FindBy(xpath = "//*[@id=\"content\"]/div/div[3]/div[3]/div[2]/div")
    protected WebElement spanForFullCost;
    @FindBy(xpath = "//div[@data-id='partnerName']")
    protected WebElement divForPartner;
    @FindBy(xpath = "//a[@data-id='deleteAll']")
    protected WebElement clearAllButton;
    @FindBy(xpath = "//span[text()='Դատարկել']")
    protected WebElement buttonToEmpty;
    @FindBy(xpath = "//a[@href='/am']")
    protected WebElement logoMenuAm;
    @FindBy(xpath = "//div[@role='dialog']")
    protected WebElement advertisement;
    @FindBy(xpath = "//span[text() = 'Լավ']")
    protected WebElement buttonForCloseAdvertisement;
    @FindBy(xpath = "//span[text()='Զեղչեր']")
    protected WebElement discountsButton;
    @FindBy(xpath = "//div[@data-id=\"quantity\"]/parent::div/parent::div/preceding-sibling::div/div[1]")
    protected WebElement divForProductNameInCart;

    public void openCart() {
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions
                .elementToBeClickable(cartButton));
        cartButton.click();
    }

    public String getCountOfItemsInTheCart() {
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions
                .visibilityOf(spanForCountOfItemsInCart));
        return spanForCountOfItemsInCart.getText();
    }

    public int getShippingCost() {
        new WebDriverWait(driver,Duration.ofSeconds(20)).until(ExpectedConditions.visibilityOf(spanForDeliveryCost));
        return Integer.parseInt(spanForDeliveryCost.getText());
    }

    public int getTheCostOfFood() {
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions
                .visibilityOf(spanForCost));
        return Integer.parseInt(spanForCost.getText());
    }

    public int getTheActualFullCost() {
        return getShippingCost() + getTheCostOfFood();
    }

    public String getPartnerName() {
        new WebDriverWait(driver,Duration.ofSeconds(20)).until(ExpectedConditions.visibilityOf(divForPartner));
        return divForPartner.getText();
    }

    public void clearCart() {
        openCart();
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions
                .elementToBeClickable(clearAllButton));
        clearAllButton.click();
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions
                .elementToBeClickable(buttonToEmpty));
        buttonToEmpty.click();
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions
                .invisibilityOfElementLocated(By.xpath("//div[text()='Ձեր զամբյուղը դատարկ է:']")));
    }

    public int getTheExpectedFullCost() {
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions
                .visibilityOf(spanForFullCost));
        return Integer.parseInt(spanForFullCost.getText());
    }

    public String getTheNameOfTheProductFromTheCart() {
        openCart();
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions
                .visibilityOf(divForProductNameInCart));
        return divForProductNameInCart.getText();
    }

    public void clickOnDiscountsButton() {
        logoMenuAm.click();
        try {
            if (advertisement.isDisplayed()) {
                buttonForCloseAdvertisement.click();
            }
        } catch (NoSuchElementException ignored) {
        }
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions
                .elementToBeClickable(discountsButton));
        discountsButton.click();
    }
}


