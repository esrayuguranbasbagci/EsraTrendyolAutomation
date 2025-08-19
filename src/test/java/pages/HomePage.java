package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    By searchBox = By.cssSelector("input[data-testid='search-bar-input']");
    By searchButton = By.cssSelector("i[data-testid='search-icon']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void searchProduct(String product) {
        driver.findElement(searchBox).sendKeys(product);
        driver.findElement(searchButton).click();
    }
}
