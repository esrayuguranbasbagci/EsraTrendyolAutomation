package com.trendyol.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductDetailPage extends BasePage {

    @FindBy(css = "h1[data-testid='product-name']")
    private WebElement productName;

    @FindBy(css = "span[data-testid='price']")
    private WebElement productPrice;

    @FindBy(css = "button[data-testid='add-to-cart']")
    private WebElement addToCartButton;

    @FindBy(css = "div[data-testid='availability-status']")
    private WebElement availabilityStatus;

    @FindBy(css = "div[data-testid='product-details']")
    private WebElement productDetailsContainer;

    public ProductDetailPage(WebDriver driver) {
        super(driver);
    }

    public boolean isProductDetailsPageDisplayed() {
        try {
            waitForElementToBeVisible(productDetailsContainer);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getProductName() {
        waitForElementToBeVisible(productName);
        return getText(productName);
    }

    public String getProductPrice() {
        waitForElementToBeVisible(productPrice);
        return getText(productPrice);
    }

    public String getAvailabilityStatus() {
        try {
            waitForElementToBeVisible(availabilityStatus);
            return getText(availabilityStatus);
        } catch (Exception e) {
            return "Status not available";
        }
    }

    public void addProductToCart() {
        waitForElementToBeClickable(addToCartButton);
        clickElement(addToCartButton);
    }

    public boolean isAddToCartButtonDisplayed() {
        try {
            return addToCartButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
