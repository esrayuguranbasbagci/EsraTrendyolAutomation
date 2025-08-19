package steps;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.*;
import utils.DriverFactory;

public class TrendyolSteps {
    WebDriver driver;
    HomePage homePage;

    @Given("I open Trendyol homepage")
    public void openTrendyol() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver = DriverFactory.getDriver();
        driver.get("https://www.trendyol.com/");
        driver.manage().window().maximize();
        homePage = new HomePage(driver);
    }

    @When("I search for {string}")
    public void searchForProduct(String product) {
        homePage.searchProduct(product);
    }

    @Then("I should see search results containing {string}")
    public void verifySearchResults(String keyword) {
        // Burada arama sonucu doğrulama kodları eklenecek
        DriverFactory.quitDriver();
    }
}
