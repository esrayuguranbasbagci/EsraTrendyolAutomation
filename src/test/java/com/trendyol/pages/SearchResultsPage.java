package com.trendyol.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.util.List;

public class SearchResultsPage extends BasePage {

    // Updated locators for actual Trendyol HTML structure
    @FindBy(css = "span.prdct-desc-cntnr-name")
    private List<WebElement> productNames;

    @FindBy(css = "div.product-desc-sub-text")
    private List<WebElement> productTitles;

    @FindBy(css = "div.price-item.discounted, div.price-item")
    private List<WebElement> productPrices;

    // Add to cart button locator
    @FindBy(css = "button.add-to-basket-button")
    private List<WebElement> addToCartButtons;

    // Cart counter locator
    @FindBy(css = "span.bs-counter-text")
    private WebElement cartCounter;

    // Basket item count container locator
    @FindBy(css = "div.basket-item-count-container.visible")
    private WebElement basketItemCountContainer;

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public boolean areSearchResultsDisplayed() {
        try {
            // Wait a bit for page to load
            Thread.sleep(2000);
            
            // Check if product names are visible (most reliable indicator)
            if (productNames.size() > 0) {
                System.out.println("Product names found: " + productNames.size());
                return true;
            }
            
            // Check if product titles are visible
            if (productTitles.size() > 0) {
                System.out.println("Product titles found: " + productTitles.size());
                return true;
            }
            
            System.out.println("No search results found");
            return false;
            
        } catch (Exception e) {
            System.out.println("Error checking search results: " + e.getMessage());
            return false;
        }
    }

    public boolean doResultsContainKeyword(String keyword) {
        try {
            // Split keyword into individual words
            String[] keywords = keyword.toLowerCase().split("\\s+");
            System.out.println("Searching for keywords: " + String.join(", ", keywords));
            
            // Check each product title
            for (WebElement titleElement : productTitles) {
                String title = getText(titleElement).toLowerCase();
                System.out.println("Checking product title: " + title);
                
                // Check if ALL keywords are present in this title
                boolean allKeywordsFound = true;
                for (String kw : keywords) {
                    // Normalize Turkish characters for better matching
                    String normalizedKw = normalizeTurkishChars(kw);
                    String normalizedTitle = normalizeTurkishChars(title);
                    
                    if (!normalizedTitle.contains(normalizedKw)) {
                        allKeywordsFound = false;
                        System.out.println("Keyword '" + kw + "' (normalized: '" + normalizedKw + "') not found in title: " + title);
                        break;
                    }
                }
                
                if (allKeywordsFound) {
                    System.out.println("Found product with all keywords: " + title);
                    return true;
                }
            }
            
            System.out.println("No product found containing all keywords: " + keyword);
            return false;
            
        } catch (Exception e) {
            System.out.println("Error checking keywords in results: " + e.getMessage());
            return false;
        }
    }

    // Helper method to normalize Turkish characters for better matching
    private String normalizeTurkishChars(String text) {
        return text
            .replace("ı", "i")
            .replace("ğ", "g")
            .replace("ü", "u")
            .replace("ş", "s")
            .replace("ö", "o")
            .replace("ç", "c")
            .replace("İ", "i")
            .replace("Ğ", "g")
            .replace("Ü", "u")
            .replace("Ş", "s")
            .replace("Ö", "o")
            .replace("Ç", "c");
    }

    public void clickFirstProduct() {
        try {
            // Store the current window handle
            String currentWindow = driver.getWindowHandle();
            
            // Click on the first product title (subtext) to go to product details
            if (productTitles.size() > 0) {
                clickElement(productTitles.get(0));
                System.out.println("Clicked on first product title to go to product details");
                
                // Wait for new tab to open
                Thread.sleep(2000);
                
                // Switch to the new tab
                for (String windowHandle : driver.getWindowHandles()) {
                    if (!windowHandle.equals(currentWindow)) {
                        driver.switchTo().window(windowHandle);
                        System.out.println("Switched to new tab: " + windowHandle);
                        break;
                    }
                }
                
            } else {
                System.out.println("No product titles found to click");
            }
        } catch (Exception e) {
            System.out.println("Error clicking first product: " + e.getMessage());
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
        return productNames.size();
    }

    // Method to check if add to cart button is visible for first product
    public boolean isAddToCartButtonVisible() {
        try {
            return addToCartButtons.size() > 0 && addToCartButtons.get(0).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Method to add first product directly to cart from search results
    public void addFirstProductToCart() {
        try {
            if (addToCartButtons.size() > 0) {
                System.out.println("Adding first product to cart from search results...");
                clickElement(addToCartButtons.get(0));
                
                // Wait for cart counter to update
                waitForCartCounterUpdate();
                System.out.println("First product added to cart successfully");
            } else {
                System.out.println("No add to cart button found");
            }
        } catch (Exception e) {
            System.out.println("Error adding first product to cart: " + e.getMessage());
        }
    }

    // Method to add product at specific index to cart
    public void addProductToCartAtIndex(int index) {
        try {
            if (index >= 0 && index < addToCartButtons.size()) {
                WebElement addToCartButton = addToCartButtons.get(index);
                
                // Debug: Check what's at the button's location
                System.out.println("=== Debug: Button Click Analysis ===");
                System.out.println("Button location: " + addToCartButton.getLocation());
                System.out.println("Button size: " + addToCartButton.getSize());
                System.out.println("Button text: " + addToCartButton.getText());
                
                // Check if button is visible and clickable
                System.out.println("Button displayed: " + addToCartButton.isDisplayed());
                System.out.println("Button enabled: " + addToCartButton.isEnabled());
                
                // Scroll to element to make it visible
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCartButton);
                Thread.sleep(1000);
                
                // Wait for button to be clickable
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
                
                // Try to find what's blocking the button
                try {
                    // Check if there are any overlapping elements
                    List<WebElement> overlappingElements = driver.findElements(By.cssSelector("div[class*='overlay'], div[class*='modal'], div[class*='popup'], div[class*='suggestion']"));
                    if (!overlappingElements.isEmpty()) {
                        System.out.println("Found " + overlappingElements.size() + " potentially overlapping elements");
                        for (int i = 0; i < Math.min(3, overlappingElements.size()); i++) {
                            WebElement element = overlappingElements.get(i);
                            System.out.println("Overlapping element " + i + ": " + element.getTagName() + " - " + element.getAttribute("class"));
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error checking overlapping elements: " + e.getMessage());
                }
                
                // Click the add to cart button
                try {
                    // First try regular click
                    addToCartButton.click();
                    System.out.println("Regular click successful");
                } catch (Exception e) {
                    System.out.println("Regular click failed: " + e.getMessage());
                    
                    // Try JavaScript click
                    try {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCartButton);
                        System.out.println("JavaScript click successful");
                    } catch (Exception jsError) {
                        System.out.println("JavaScript click failed: " + jsError.getMessage());
                        
                        // Try Actions click
                        try {
                            org.openqa.selenium.interactions.Actions actions = new org.openqa.selenium.interactions.Actions(driver);
                            actions.moveToElement(addToCartButton).click().perform();
                            System.out.println("Actions click successful");
                        } catch (Exception actionsError) {
                            System.out.println("Actions click failed: " + actionsError.getMessage());
                            throw new RuntimeException("All click methods failed");
                        }
                    }
                }
                
                System.out.println("Adding product at index " + index + " to cart...");
                
                // Wait for cart counter to update
                waitForCartCounterUpdate();
                
                System.out.println("Product at index " + index + " added to cart successfully");
            } else {
                System.out.println("Invalid index: " + index + ". Available products: " + addToCartButtons.size());
            }
        } catch (Exception e) {
            System.out.println("Error adding product at index " + index + " to cart: " + e.getMessage());
        }
    }

    // Method to wait for cart counter to update
    private void waitForCartCounterUpdate() {
        try {
            // Wait for cart counter to appear and update
            Thread.sleep(3000);
            
            // Check if cart counter is visible and shows "1 Adet" or similar
            if (cartCounter.isDisplayed()) {
                String counterText = getText(cartCounter);
                System.out.println("Cart counter updated: " + counterText);
            }
        } catch (Exception e) {
            System.out.println("Error waiting for cart counter update: " + e.getMessage());
        }
    }

    // Method to get current cart counter text
    public String getCartCounterText() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(cartCounter));
            return cartCounter.getText();
        } catch (Exception e) {
            System.out.println("Error getting cart counter text: " + e.getMessage());
            return "0";
        }
    }

    // Method to get basket item count
    public String getBasketItemCount() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(basketItemCountContainer));
            return basketItemCountContainer.getText();
        } catch (Exception e) {
            System.out.println("Error getting basket item count: " + e.getMessage());
            return "0";
        }
    }

    // Method to get number of add to cart buttons available
    public int getAddToCartButtonCount() {
        try {
            return addToCartButtons.size();
        } catch (Exception e) {
            System.out.println("Error getting add to cart button count: " + e.getMessage());
            return 0;
        }
    }

    // Method to check if search results are displayed
    public boolean isSearchResultsDisplayed() {
        try {
            // Check if we have add to cart buttons (indicating products are displayed)
            int buttonCount = getAddToCartButtonCount();
            if (buttonCount > 0) {
                System.out.println("Search results displayed with " + buttonCount + " products");
                return true;
            }
            
            // Alternative check: look for product elements
            List<WebElement> productElements = driver.findElements(By.cssSelector("div[class*='product'], div[class*='card'], div[class*='item']"));
            if (productElements.size() > 0) {
                System.out.println("Search results displayed with " + productElements.size() + " product elements");
                return true;
            }
            
            System.out.println("No search results found");
            return false;
            
        } catch (Exception e) {
            System.out.println("Error checking search results: " + e.getMessage());
            return false;
        }
    }

    // Method to close search input field if it's blocking elements
    public void closeSearchInputIfBlocking() {
        try {
            // Try to find search input field
            WebElement searchInput = driver.findElement(By.cssSelector("input[data-testid='suggestion'], input[placeholder*='Aradığınız ürün']"));
            if (searchInput.isDisplayed()) {
                // Clear the input and move focus away
                searchInput.clear();
                searchInput.sendKeys(Keys.TAB); // Move focus away
                Thread.sleep(500);
                System.out.println("Search input field cleared and focus moved");
            }
        } catch (Exception e) {
            // Search input not found or already closed
        }
    }

    // Method to get product name at specific index
    public String getProductNameAtIndex(int index) {
        try {
            if (index < productNames.size()) {
                return getText(productNames.get(index));
            }
        } catch (Exception e) {
            System.out.println("Error getting product name at index " + index + ": " + e.getMessage());
        }
        return "";
    }

    // Method to get product price at specific index
    public String getProductPriceAtIndex(int index) {
        try {
            if (index < productPrices.size()) {
                return getText(productPrices.get(index));
            }
        } catch (Exception e) {
            System.out.println("Error getting product price at index " + index + ": " + e.getMessage());
        }
        return "";
    }
}
