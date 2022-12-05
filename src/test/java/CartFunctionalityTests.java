import initDriver.BrowserType;
import initDriver.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;

public class CartFunctionalityTests {
    private final String partnerName = "Պիցցա Միցցա";
    private final String concreteTypeOfFood = "Պիցցա Կեսար";

    public void addProductFromFiltersToCart() {
        String preferredFoodType = "Պիցցա";
        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
        loginPage.chooseFoodType(preferredFoodType);
        FilteredFoodPage filteredFoodPage = new FilteredFoodPage(DriverFactory.getDriver());
        filteredFoodPage.waitForAllPartnersLoaded();
        filteredFoodPage.choosePartner(partnerName);
        SelectedPartnerSPage selectedPartnerSPage = new SelectedPartnerSPage(DriverFactory.getDriver());
        selectedPartnerSPage.waitSelectedFoodPageLoaded();
        selectedPartnerSPage.chooseFoodAndAddToCard(concreteTypeOfFood);
    }

    @BeforeMethod
    public void initDriverAndLoginAccountAndSetDeliveryAddress() {
        DriverFactory.initDriver(BrowserType.CHROME);
        HomePage homePage = new HomePage(DriverFactory.getDriver());
        homePage.openHomePage();
        homePage.waitForHomePageLoaded();
        homePage.loginAccount();
        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
        loginPage.waitForLoginPageLoaded();
        loginPage.setDeliveryAddress();
    }

    @Test
    public void TestOne() {
        addProductFromFiltersToCart();
        CartPage cartPage = new CartPage(DriverFactory.getDriver());
        cartPage.openCart();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals("1", cartPage.getCountOfItemsInTheCart());
        softAssert.assertAll();
    }

    @Test
    public void TestTwo() {
        addProductFromFiltersToCart();
        CartPage cartPage = new CartPage(DriverFactory.getDriver());
        cartPage.openCart();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(partnerName, cartPage.getPartnerName());
        softAssert.assertAll();
    }

    @Test
    public void TestThree() {
        addProductFromFiltersToCart();
        CartPage cartPage = new CartPage(DriverFactory.getDriver());
        cartPage.openCart();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(cartPage.getTheActualFullCost(), cartPage.getTheExpectedFullCost());
        softAssert.assertAll();
    }

    @Test
    public void TestFour() {
        addProductFromFiltersToCart();
        SelectedPartnerSPage selectedPartnerSPage = new SelectedPartnerSPage(DriverFactory.getDriver());
        int costOfFoodFromFoodPage = selectedPartnerSPage.getSelectedFoodCost(concreteTypeOfFood);
        CartPage cartPage = new CartPage(DriverFactory.getDriver());
        cartPage.openCart();
        int costOfFoodFromCartPage = cartPage.getTheCostOfFood();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(costOfFoodFromFoodPage, costOfFoodFromCartPage);
        softAssert.assertAll();
    }

    @Test
    public void TestFive() throws InterruptedException {
        addProductFromFiltersToCart();
        SelectedPartnerSPage selectedPartnerSPage = new SelectedPartnerSPage(DriverFactory.getDriver());
        selectedPartnerSPage.waitSelectedFoodPageLoaded();
        selectedPartnerSPage.chooseFoodAndAddToCard(concreteTypeOfFood);
        FilteredFoodPage filteredFoodPage = new FilteredFoodPage(DriverFactory.getDriver());
        int shippingCostFromFilteredFoodPage = filteredFoodPage.getShippingCost(partnerName);
        CartPage cartPage = new CartPage(DriverFactory.getDriver());
        cartPage.openCart();
        Thread.sleep(1000);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals("2", cartPage.getCountOfItemsInTheCart());
        softAssert.assertEquals(shippingCostFromFilteredFoodPage, cartPage.getShippingCost());
        softAssert.assertAll();
    }

    @Test
    public void TestSix() {
        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
        loginPage.clickOnDiscountsButton();
        DiscountsPage discountsPage = new DiscountsPage(DriverFactory.getDriver());
        discountsPage.waitForDiscountsPageLoaded();
        String concreteProductFromDiscountsPage = "Ընտանեկան կոմբո";
        discountsPage.addAConcreteProductToTheCart(concreteProductFromDiscountsPage);
        CartPage cartPage = new CartPage(DriverFactory.getDriver());
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals("1", cartPage.getCountOfItemsInTheCart());
        softAssert.assertEquals(concreteProductFromDiscountsPage, cartPage.getTheNameOfTheProductFromTheCart());
        softAssert.assertAll();
    }

    @AfterMethod
    public void clearCartAndLogOut() {
        CartPage cartPage = new CartPage(DriverFactory.getDriver());
        cartPage.openCart();
        cartPage.clearCart();
        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
        loginPage.logout();
    }

    @AfterTest
    public void tearDown() {
        WebDriver driver = DriverFactory.getDriver();
        if (driver != null) {
            driver.quit();
        }
    }
}
