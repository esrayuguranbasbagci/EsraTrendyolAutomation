package com.trendyol.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CartPage extends BasePage {

    @FindBy(css = "div[data-testid='cart-item']")
    private List<WebElement> cartItems;

    @FindBy(css = "div[data-testid='cart-item'] h3")
    private List<WebElement> cartItemNames;

    @FindBy(css = "div[data-testid='cart-item'] span[data-testid='price']")
    private List<WebElement> cartItemPrices;

    @FindBy(css = "span[data-testid='total-price']")
    private WebElement totalPrice;

    @FindBy(css = "button[data-testid='remove-item']")
    private List<WebElement> removeButtons;

    @FindBy(css = "div[data-testid='cart-container']")
    private WebElement cartContainer;

    @FindBy(css = "div[data-testid='empty-cart-message']")
    private WebElement emptyCartMessage;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToCart() {
        driver.get("https://www.trendyol.com/sepetim");
    }

    public boolean isCartPageDisplayed() {
        try {
            waitForElementToBeVisible(cartContainer);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int getCartItemCount() {
        return cartItems.size();
    }

    public String getCartItemName(int index) {
        if (index < cartItemNames.size()) {
            return getText(cartItemNames.get(index));
        }
        return "";
    }

    public String getCartItemPrice(int index) {
        if (index < cartItemPrices.size()) {
            return getText(cartItemPrices.get(index));
        }
        return "";
    }

    public String getTotalPrice() {
        waitForElementToBeVisible(totalPrice);
        return getText(totalPrice);
    }

    public void removeItemFromCart(int index) {
        if (index < removeButtons.size()) {
            clickElement(removeButtons.get(index));
        }
    }

    public boolean isCartEmpty() {
        try {
            return emptyCartMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public double calculateTotalFromItems() {
        double total = 0.0;
        for (WebElement priceElement : cartItemPrices) {
            String priceText = getText(priceElement);
            // Remove currency symbols and convert to double
            priceText = priceText.replaceAll("[^0-9.,]", "").replace(",", ".");
            try {
                total += Double.parseDouble(priceText);
            } catch (NumberFormatException e) {
                // Skip invalid prices
            }
        }
        return total;
    }
}
