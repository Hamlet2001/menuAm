import initDriver.BrowserType;
import initDriver.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pages.*;

public class CartFunctionalityTests extends BaseTest {
    private final String partnerName = "Պիցցա Միցցա";
    private final String concreteTypeOfFood = "Պիցցա Կեսար";

    public void addProductFromFiltersToCart() {
        String preferredFoodType = "Պիցցա";
        new LoginPage(DriverFactory.getDriver())
                .chooseFoodType(preferredFoodType)
                .waitForAllPartnersLoaded()
                .choosePartner(partnerName)
                .waitForSelectedPartnersPageLoaded()
                .chooseFood(concreteTypeOfFood)
                .addToCart();
    }

    @BeforeMethod
    public void openHomePageAndSignInAccountAndSetDeliveryAddress() {
        new HomePage(DriverFactory.getDriver())
                .openHomePage()
                .signIn()
                .waitForLoginPageLoaded()
                .setDeliveryAddress();
    }

    @Test
    public void TestOne() {
        addProductFromFiltersToCart();
        CartPage cartPage = new CartPage(DriverFactory.getDriver())
                .openCart()
                .waitForCartPageLoaded();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals("1", cartPage.getCountOfItemsInTheCart());
        softAssert.assertAll();
    }

    @Test
    public void TestTwo() {
        addProductFromFiltersToCart();
        CartPage cartPage = new CartPage(DriverFactory.getDriver())
                .openCart()
                .waitForCartPageLoaded();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(partnerName, cartPage.getPartnerName());
        softAssert.assertAll();
    }

    @Test
    public void TestThree() {
        addProductFromFiltersToCart();
        CartPage cartPage = new CartPage(DriverFactory.getDriver())
                .openCart()
                .waitForCartPageLoaded();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(cartPage.getTheActualFullCost(), cartPage.getTheExpectedFullCost());
        softAssert.assertAll();
    }

    @Test
    public void TestFour() {
        addProductFromFiltersToCart();
        SelectedPartnerSPage selectedPartnerSPage = new SelectedPartnerSPage(DriverFactory.getDriver());
        int costOfFoodFromFoodPage = selectedPartnerSPage.getSelectedFoodCost(concreteTypeOfFood);
        CartPage cartPage = new CartPage(DriverFactory.getDriver())
                .openCart()
                .waitForCartPageLoaded();
        int costOfFoodFromCartPage = cartPage.getTheCostOfFood();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(costOfFoodFromFoodPage, costOfFoodFromCartPage);
        softAssert.assertAll();
    }

    @Test
    public void TestFive() {
        addProductFromFiltersToCart();
        new SelectedPartnerSPage(DriverFactory.getDriver())
                .waitForSelectedPartnersPageLoaded()
                .chooseFood(concreteTypeOfFood)
                .addToCart();
        FilteredFoodPage filteredFoodPage = new FilteredFoodPage(DriverFactory.getDriver());
        int shippingCostFromFilteredFoodPage = filteredFoodPage.getShippingCost(partnerName);
        CartPage cartPage = new CartPage(DriverFactory.getDriver())
                .openCart()
                .waitForCartPageLoaded();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(cartPage.getCountOfItemsInTheCart(),"2");
        softAssert.assertEquals(shippingCostFromFilteredFoodPage, cartPage.getShippingCost());
        softAssert.assertAll();
    }

    @Test
    public void TestSix() {
        String concreteProductFromDiscountsPage = "Ընտանեկան կոմբո";
        new LoginPage(DriverFactory.getDriver())
                .clickOnDiscountsButton()
                .waitForDiscountsPageLoaded()
                .addAConcreteProductToTheCart(concreteProductFromDiscountsPage);
        CartPage cartPage = new CartPage(DriverFactory.getDriver())
                .openCart()
                .waitForCartPageLoaded();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals("1", cartPage.getCountOfItemsInTheCart());
        softAssert.assertEquals(concreteProductFromDiscountsPage, cartPage.getTheNameOfTheProductFromTheCart());
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void clearCartAndLogOut() {
        new CartPage(DriverFactory.getDriver())
                .clearCart()
                .logout();
    }

    @AfterTest
    public void tearDown() {
        WebDriver driver = DriverFactory.getDriver();
        if (driver != null) {
            driver.quit();
        }
    }
}
