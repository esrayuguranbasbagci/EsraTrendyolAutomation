package com.trendyol.steps;

import com.trendyol.pages.*;
import com.trendyol.hooks.ExtentReportHooks;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;



public class TrendyolShoppingSteps {

    private WebDriver driver;
    private HomePage homePage;
    private SearchResultsPage searchResultsPage;
    private ProductDetailPage productDetailPage;
    private CartPage cartPage;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        
        // Chrome Options
        org.openqa.selenium.chrome.ChromeOptions options = new org.openqa.selenium.chrome.ChromeOptions();
        
        // Add arguments
        options.addArguments("--start-maximized");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--remote-debugging-port=9222");
        options.addArguments("--user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/135.0.0.0 Safari/537.36");
        
        // Experimental options
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);
        
        // Create driver with options
        driver = new ChromeDriver(options);
        // Window is already maximized with --start-maximized argument
        
        // Set driver for Extent Reports
        ExtentReportHooks.setDriver(driver);
        
        homePage = new HomePage(driver);
        searchResultsPage = new SearchResultsPage(driver);
        productDetailPage = new ProductDetailPage(driver);
        cartPage = new CartPage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("I am on the Trendyol homepage")
    public void i_am_on_the_trendyol_homepage() {
        homePage.navigateToHomePage();
        Assert.assertTrue("Homepage should be displayed", homePage.isSearchInputDisplayed());
    }

    @Given("I accept cookies if present")
    public void i_accept_cookies_if_present() {
        // Cookies are now handled automatically in searchForProduct method
        // This step is kept for compatibility but doesn't need to do anything
    }

    @When("I search for {string}")
    public void i_search_for(String productName) {
        homePage.searchForProduct(productName);
    }

    @Then("search results should be displayed")
    public void search_results_should_be_displayed() {
        Assert.assertTrue("Search results should be displayed", 
            searchResultsPage.areSearchResultsDisplayed());
    }

    @Then("results should contain {string}")
    public void results_should_contain(String keyword) {
        Assert.assertTrue("Search results should contain keyword: " + keyword,
            searchResultsPage.doResultsContainKeyword(keyword));
    }

    @When("I click on the first product from search results")
    public void i_click_on_the_first_product_from_search_results() {
        searchResultsPage.clickFirstProduct();
    }

    @Then("product details page should be displayed")
    public void product_details_page_should_be_displayed() {
        Assert.assertTrue("Product details page should be displayed",
            productDetailPage.isProductDetailsPageDisplayed());
    }

    @Then("product name should be visible")
    public void product_name_should_be_visible() {
        String productName = productDetailPage.getProductName();
        Assert.assertFalse("Product name should be visible", productName.isEmpty());
    }

    @Then("product price should be visible")
    public void product_price_should_be_visible() {
        String productPrice = productDetailPage.getProductPrice();
        Assert.assertFalse("Product price should be visible", productPrice.isEmpty());
    }

    @Then("product availability status should be visible")
    public void product_availability_status_should_be_visible() {
        String availabilityStatus = productDetailPage.getAvailabilityStatus();
        Assert.assertNotNull("Availability status should be visible", availabilityStatus);
    }

    @When("I add the product to cart")
    public void i_add_the_product_to_cart() {
        Assert.assertTrue("Add to cart button should be displayed",
            productDetailPage.isAddToCartButtonDisplayed());
        productDetailPage.addProductToCart();
    }

    @Then("product should be added to cart successfully")
    public void product_should_be_added_to_cart_successfully() {
        cartPage.navigateToCart();
        Assert.assertTrue("Cart page should be displayed", cartPage.isCartPageDisplayed());
        Assert.assertTrue("Product should be in cart", cartPage.getCartItemCount() > 0);
    }

    @Then("cart should display correct product details")
    public void cart_should_display_correct_product_details() {
        String cartItemName = cartPage.getCartItemName(0);
        Assert.assertFalse("Cart item name should be displayed", cartItemName.isEmpty());
    }

    @When("I add multiple products to cart")
    public void i_add_multiple_products_to_cart() {
        // Search and add first product
        homePage.navigateToHomePage();
        homePage.searchForProduct("kablosuz kulaklik");
        searchResultsPage.clickFirstProduct();
        productDetailPage.addProductToCart();
        
        // Search and add second product
        homePage.navigateToHomePage();
        homePage.searchForProduct("bluetooth kulaklık");
        searchResultsPage.clickFirstProduct();
        productDetailPage.addProductToCart();
    }

    @Then("total price should match sum of individual product prices")
    public void total_price_should_match_sum_of_individual_product_prices() {
        cartPage.navigateToCart();
        Assert.assertTrue("Cart page should be displayed", cartPage.isCartPageDisplayed());
        
        double calculatedTotal = cartPage.calculateTotalFromItems();
        String displayedTotal = cartPage.getTotalPrice();
        
        // Extract numeric value from displayed total
        String numericTotal = displayedTotal.replaceAll("[^0-9.,]", "").replace(",", ".");
        double actualTotal = Double.parseDouble(numericTotal);
        
        Assert.assertEquals("Total price should match calculated sum", 
            calculatedTotal, actualTotal, 0.01);
    }

    @When("I remove an item from cart")
    public void i_remove_an_item_from_cart() {
        cartPage.navigateToCart();
        int initialCount = cartPage.getCartItemCount();
        if (initialCount > 0) {
            cartPage.removeItemFromCart(0);
        }
    }

    @Then("item should be removed from cart")
    public void item_should_be_removed_from_cart() {
        // Wait a moment for removal to complete
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Check if cart is empty or has fewer items
        Assert.assertTrue("Item should be removed from cart", 
            cartPage.getCartItemCount() == 0 || cartPage.isCartEmpty());
    }

    @Then("total price should be updated correctly")
    public void total_price_should_be_updated_correctly() {
        if (cartPage.getCartItemCount() > 0) {
            double calculatedTotal = cartPage.calculateTotalFromItems();
            String displayedTotal = cartPage.getTotalPrice();
            
            String numericTotal = displayedTotal.replaceAll("[^0-9.,]", "").replace(",", ".");
            double actualTotal = Double.parseDouble(numericTotal);
            
            Assert.assertEquals("Updated total price should match calculated sum", 
                calculatedTotal, actualTotal, 0.01);
        }
    }
}
