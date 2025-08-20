package com.trendyol.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductDetailPage extends BasePage {

    // Updated locators for actual Trendyol HTML structure
    @FindBy(css = "h1.product-title")
    private WebElement productTitle;

    @FindBy(css = "span.discounted")
    private WebElement productPrice;

    @FindBy(css = "span.add-to-cart-button-text")
    private WebElement addToCartButtonText;

    // Add to cart button locator
    @FindBy(css = "button[data-testid='add-to-cart-button'], button.add-to-cart-button")
    private WebElement addToCartButton;

    // Basket item count locator - shows number of items in cart
    @FindBy(css = "div.basket-item-count")
    private WebElement basketItemCount;

    // "Sepetim" (cart) button locator
    @FindBy(css = "a.link.account-basket, a[href='/sepet']")
    private WebElement sepetimButton;

    // "Sepete Eklendi" text locator - appears after successful add to cart
    @FindBy(css = "span:contains('Sepete Eklendi'), span")
    private WebElement addedToCartText;

    // Availability status - check if add to cart button is enabled
    @FindBy(css = "button[data-testid='add-to-cart-button']:not([disabled]), button.add-to-cart-button:not([disabled])")
    private WebElement enabledAddToCartButton;

    private String storedProductPrice;
    private String storedProductName;

    public ProductDetailPage(WebDriver driver) {
        super(driver);
    }

    public boolean isProductDetailsPageDisplayed() {
        try {
            System.out.println("Checking if product details page is displayed...");
            System.out.println("Current URL: " + driver.getCurrentUrl());
            
            // Wait 10 seconds for any popup/layer to appear and for manual inspection
            System.out.println("Waiting 10 seconds for page to fully load and for manual inspection...");
            Thread.sleep(10000);
            
            // Wait for product title to be visible
            waitForElementToBeVisible(productTitle);
            boolean isDisplayed = productTitle.isDisplayed();
            
            if (isDisplayed) {
                String titleText = getText(productTitle);
                System.out.println("Product title found: " + titleText);
            } else {
                System.out.println("Product title is not displayed");
            }
            
            return isDisplayed;
        } catch (Exception e) {
            System.out.println("Error checking product details page: " + e.getMessage());
            return false;
        }
    }

    public String getProductName() {
        try {
            String name = productTitle.getText().trim();
            System.out.println("Product name retrieved: " + name);
            // Store the name for later comparison
            storedProductName = name;
            return name;
        } catch (Exception e) {
            System.out.println("Error getting product name: " + e.getMessage());
            return null;
        }
    }

    public String getProductPrice() {
        try {
            String price = productPrice.getText().trim();
            System.out.println("Product price retrieved: " + price);
            // Store the price for later comparison
            storedProductPrice = price;
            return price;
        } catch (Exception e) {
            System.out.println("Error getting product price: " + e.getMessage());
            return null;
        }
    }

    // Getter methods for stored values
    public String getStoredProductPrice() {
        return storedProductPrice;
    }

    public String getStoredProductName() {
        return storedProductName;
    }

    public String getAvailabilityStatus() {
        try {
            // Check if add to cart button is enabled (product is available)
            if (enabledAddToCartButton.isDisplayed()) {
                System.out.println("Product availability: Available (Add to cart button is enabled)");
                return "Available";
            } else {
                System.out.println("Product availability: Not Available (Add to cart button is disabled)");
                return "Not Available";
            }
        } catch (Exception e) {
            System.out.println("Error checking availability status: " + e.getMessage());
            return "Status Unknown";
        }
    }

    public boolean isAddToCartButtonDisplayed() {
        try {
            return addToCartButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void addProductToCart() {
        try {
            // First try to close any onboarding tour overlay if present
            closeOnboardingTourOverlay();
            
            // Wait longer for any remaining overlays to disappear
            Thread.sleep(3000);
            
            // Verify the add-to-cart button is visible and clickable
            waitForElementToBeClickable(addToCartButton);
            
            // Double-check that no overlay is blocking the button
            try {
                WebElement overlay = driver.findElement(By.cssSelector("div.onboarding-tour__overlay"));
                if (overlay.isDisplayed()) {
                    System.out.println("Overlay still visible, trying to close again...");
                    closeOnboardingTourOverlay();
                    Thread.sleep(2000);
                }
            } catch (Exception e) {
                System.out.println("No overlay found, button should be clickable");
            }
            
            // Now click the add-to-cart button
            clickElement(addToCartButton);
            System.out.println("Product added to cart from product details page");
            
            // Wait for basket counter to update to show "1"
            waitForBasketCounterToUpdate();
            
            // Wait a bit more for any loading to complete
            Thread.sleep(3000);
            
            // Click on "Sepetim" button to go to cart page
            try {
                waitForElementToBeClickable(sepetimButton);
                clickElement(sepetimButton);
                System.out.println("Clicked on 'Sepetim' button to navigate to cart");
            } catch (Exception e) {
                System.out.println("Could not click 'Sepetim' button: " + e.getMessage());
                // Alternative: try to navigate directly to cart URL
                try {
                    driver.get("https://www.trendyol.com/sepet");
                    System.out.println("Navigated to cart page directly via URL");
                } catch (Exception ex) {
                    System.out.println("Could not navigate to cart page: " + ex.getMessage());
                }
            }
            
        } catch (Exception e) {
            System.out.println("Error adding product to cart: " + e.getMessage());
        }
    }

    // Close onboarding tour overlay if present
    private void closeOnboardingTourOverlay() {
        try {
            // Wait a bit for overlay to appear
            Thread.sleep(2000);
            
            // Try multiple strategies to close the overlay
            WebElement overlay = driver.findElement(By.cssSelector("div.onboarding-tour__overlay"));
            if (overlay.isDisplayed()) {
                System.out.println("Found onboarding tour overlay, attempting to close it...");
                
                // Strategy 1: Try to find and click a close button
                try {
                    WebElement closeButton = driver.findElement(By.cssSelector("button.onboarding-tour__close, .onboarding-tour__close, [aria-label='Close'], .close-button"));
                    if (closeButton.isDisplayed()) {
                        closeButton.click();
                        System.out.println("Clicked close button on overlay");
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    System.out.println("No close button found, trying alternative methods");
                }
                
                // Strategy 2: Click on the overlay itself to dismiss it
                try {
                    overlay.click();
                    System.out.println("Clicked on overlay to dismiss it");
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println("Could not click on overlay");
                }
                
                // Strategy 3: Send ESC key
                try {
                    Actions actions = new Actions(driver);
                    actions.sendKeys(Keys.ESCAPE).perform();
                    System.out.println("Sent ESC key to close overlay");
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println("Could not send ESC key");
                }
                
                // Strategy 4: Click on body element
                try {
                    WebElement body = driver.findElement(By.tagName("body"));
                    body.click();
                    System.out.println("Clicked on body to close overlay");
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println("Could not click on body");
                }
                
                // Verify overlay is gone
                try {
                    if (!overlay.isDisplayed()) {
                        System.out.println("Overlay successfully closed");
                    } else {
                        System.out.println("Overlay still visible after attempts to close");
                    }
                } catch (Exception e) {
                    System.out.println("Overlay element no longer found (likely closed)");
                }
            } else {
                System.out.println("No onboarding tour overlay found");
            }
        } catch (Exception e) {
            System.out.println("Error handling onboarding tour overlay: " + e.getMessage());
        }
    }

    // Wait for basket counter to update after adding product
    private void waitForBasketCounterToUpdate() {
        try {
            // First wait for "Sepete Eklendi" text to appear
            System.out.println("Waiting for 'Sepete Eklendi' text to appear...");
            int maxWaitTime = 10; // Maximum 10 seconds wait
            int currentWait = 0;
            
            while (currentWait < maxWaitTime) {
                try {
                    if (addedToCartText.isDisplayed()) {
                        String buttonText = getText(addedToCartText);
                        if (buttonText.contains("Sepete Eklendi")) {
                            System.out.println("Add to cart button text changed to: " + buttonText);
                            break;
                        }
                    }
                } catch (Exception e) {
                    // Element might not be visible yet, continue waiting
                }
                
                Thread.sleep(1000); // Wait 1 second
                currentWait++;
            }
            
            // Then wait for basket item count to be visible and show "1"
            System.out.println("Waiting for basket counter to update...");
            waitForElementToBeVisible(basketItemCount);
            
            // Wait for the counter to show "1"
            currentWait = 0;
            while (currentWait < maxWaitTime) {
                String counterText = getText(basketItemCount);
                if (counterText.contains("1")) {
                    System.out.println("Basket counter updated: " + counterText);
                    return;
                }
                
                Thread.sleep(1000); // Wait 1 second
                currentWait++;
            }
            
            System.out.println("Basket counter did not update within expected time");
            
        } catch (Exception e) {
            System.out.println("Error waiting for basket counter update: " + e.getMessage());
        }
    }
}
