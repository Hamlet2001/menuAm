import initDriver.BrowserType;
import initDriver.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pages.*;

public class CartFunctionalityTests {
    private final String partnerName = "Պիցցա Միցցա";
    private final String concreteTypeOfFood = "Պիցցա Կեսար";

    @BeforeMethod
    public void initDriver() {
        DriverFactory.initDriver(BrowserType.CHROME);
    }

    @BeforeGroups
    public void openHomePageAndLoginAndSetDeliveryAddress() {
        HomePage homePage = new HomePage(DriverFactory.getDriver());
        homePage.openHomePage();
        homePage.waitForHomePageLoaded();
        homePage.loginAccount();
        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
        loginPage.setDeliveryAddress();
    }

    @Test(priority = 1)
    public void TestOne() {
        openHomePageAndLoginAndSetDeliveryAddress();
        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
        String preferredFoodType = "Պիցցա";
        loginPage.chooseFoodType(preferredFoodType);
        SelectedFoodPage selectedFoodPage = new SelectedFoodPage(DriverFactory.getDriver());
        selectedFoodPage.choosePartner(partnerName);
        selectedFoodPage.chooseFoodAndAddToCard(concreteTypeOfFood);
        CartPage cartPage = new CartPage(DriverFactory.getDriver());
        cartPage.openCart();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals("1", cartPage.getCountOfItemsInTheCart());
        softAssert.assertAll();
    }

    @Test(priority = 2)
    public void TestTwo() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());
        cartPage.openCart();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(partnerName, cartPage.getPartnerName());
        softAssert.assertAll();
    }

    @Test(priority = 3)
    public void TestThree() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());
        cartPage.openCart();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(cartPage.getTheActualFullCost(), cartPage.getTheExpectedFullCost());
        softAssert.assertAll();
    }

    @Test(priority = 4)
    public void TestFour() {
        SelectedFoodPage selectedFoodPage = new SelectedFoodPage(DriverFactory.getDriver());
        int costOfFoodFromFoodPage = selectedFoodPage.getSelectedFoodCost(concreteTypeOfFood);
        CartPage cartPage = new CartPage(DriverFactory.getDriver());
        cartPage.openCart();
        int costOfFoodFromCartPage = cartPage.getTheCostOfFood();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(costOfFoodFromFoodPage, costOfFoodFromCartPage);
        softAssert.assertAll();
    }

    @Test(priority = 5)
    public void TestFive() {
        SelectedFoodPage selectedFoodPage = new SelectedFoodPage(DriverFactory.getDriver());
        selectedFoodPage.chooseFoodAndAddToCard(concreteTypeOfFood);
        int shippingCostFromSelectedFoodPage = selectedFoodPage.getShippingCost(partnerName);
        CartPage cartPage = new CartPage(DriverFactory.getDriver());
        cartPage.openCart();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(shippingCostFromSelectedFoodPage, cartPage.getShippingCost());
        softAssert.assertAll();
    }

    @Test(priority = 6)
    public void TestSix() throws InterruptedException {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());
        cartPage.clearCart();
        cartPage.clickOnDiscountsButton();
        DiscountsPage discountsPage = new DiscountsPage(DriverFactory.getDriver());
        discountsPage.waitForDiscountsPageLoaded();
        String concreteProductFromDiscountsPage = "Համեղ լանչ կոմբո";
        discountsPage.addAConcreteProductToTheCart(concreteProductFromDiscountsPage);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals("1", cartPage.getCountOfItemsInTheCart());
        softAssert.assertEquals(concreteProductFromDiscountsPage, cartPage.getTheNameOfTheProductFromTheCart());
        discountsPage.addAConcreteProductToTheCart(concreteProductFromDiscountsPage);
        Thread.sleep(2000);
        softAssert.assertEquals("2", cartPage.getCountOfItemsInTheCart());
        softAssert.assertAll();
    }

    @AfterTest
    public void tearDown() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());
        cartPage.clearCart();
        WebDriver driver = DriverFactory.getDriver();
        if (driver != null) {
            driver.quit();
        }
    }
}
