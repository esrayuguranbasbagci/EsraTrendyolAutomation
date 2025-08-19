package com.trendyol.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SearchResultsPage extends BasePage {

    @FindBy(css = "div[data-testid='product-card']")
    private List<WebElement> productCards;

    @FindBy(css = "div[data-testid='product-card'] h3")
    private List<WebElement> productNames;

    @FindBy(css = "div[data-testid='product-card'] span[data-testid='price']")
    private List<WebElement> productPrices;

    @FindBy(css = "div[data-testid='search-results']")
    private WebElement searchResultsContainer;

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public boolean areSearchResultsDisplayed() {
        try {
            waitForElementToBeVisible(searchResultsContainer);
            return productCards.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean doResultsContainKeyword(String keyword) {
        for (WebElement productName : productNames) {
            if (getText(productName).toLowerCase().contains(keyword.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public void clickFirstProduct() {
        if (productCards.size() > 0) {
            clickElement(productCards.get(0));
        }
    }

    public String getFirstProductName() {
        if (productNames.size() > 0) {
            return getText(productNames.get(0));
        }
        return "";
    }

    public String getFirstProductPrice() {
        if (productPrices.size() > 0) {
            return getText(productPrices.get(0));
        }
        return "";
    }

    public int getProductCount() {
        return productCards.size();
    }
}
