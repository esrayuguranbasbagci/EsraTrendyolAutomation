package com.trendyol.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    // Primary search input selector from Trendyol
    @FindBy(css = "input[data-testid='suggestion']")
    private WebElement searchInput;
    
    // Alternative selectors if the above doesn't work
    @FindBy(css = "input[data-testid='search-bar-input']")
    private WebElement searchInputAlt1;
    
    @FindBy(css = "input.search-bar")
    private WebElement searchInputAlt2;
    
    @FindBy(css = "input[placeholder*='Aradığınız ürün']")
    private WebElement searchInputAlt3;
    
    @FindBy(css = "input[placeholder*='Search']")
    private WebElement searchInputAlt4;
    
    @FindBy(css = "input[type='text']")
    private WebElement searchInputAlt5;
    
    @FindBy(css = "input[class*='search']")
    private WebElement searchInputAlt6;

    // Cookie accept button - language independent
    @FindBy(css = "button#onetrust-accept-btn-handler")
    private WebElement acceptCookiesButton;
    
    // Alternative cookie button selectors
    @FindBy(css = "button[data-testid='cookie-accept-button']")
    private WebElement acceptCookiesButtonAlt;
    
    @FindBy(css = "button:contains('Accept'), button:contains('Kabul'), button:contains('Tümünü')")
    private WebElement acceptCookiesButtonGeneric;

    @FindBy(css = "div[data-testid='cookie-banner']")
    private WebElement cookieBanner;
    
    // Language selection button (SVG path)
    @FindBy(css = "path#Combined-Shape")
    private WebElement languageButton;
    
    // Alternative language button selectors
    @FindBy(css = "button[aria-label*='language'], button[aria-label*='dil']")
    private WebElement languageButtonAlt;
    
    @FindBy(css = "svg[class*='language'], svg[class*='dil']")
    private WebElement languageButtonSvg;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void navigateToHomePage() {
        driver.get("https://www.trendyol.com/");
    }

    public void acceptCookiesIfPresent() {
        try {
            // Try to find and click cookie accept button with multiple selectors
            WebElement cookieButton = findCookieButton();
            if (cookieButton != null) {
                clickElement(cookieButton);
                // Wait a bit for cookie banner to disappear
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (Exception e) {
            // Cookies banner not present, continue
        }
    }
    
    public void clickLanguageButtonIfPresent() {
        try {
            // Try to find and click language button with multiple selectors
            WebElement langButton = findLanguageButton();
            if (langButton != null) {
                clickElement(langButton);
                // Wait a bit for language selection to complete
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (Exception e) {
            // Language button not present, continue
        }
    }
    
    private WebElement findCookieButton() {
        try {
            if (acceptCookiesButton.isDisplayed()) {
                return acceptCookiesButton;
            }
        } catch (Exception e) {
            // Try alternative selectors
        }
        
        try {
            if (acceptCookiesButtonAlt.isDisplayed()) {
                return acceptCookiesButtonAlt;
            }
        } catch (Exception e) {
            // Continue to next alternative
        }
        
        // Try to find by text content (language independent)
        try {
            return driver.findElement(org.openqa.selenium.By.xpath(
                "//button[contains(text(), 'Accept') or contains(text(), 'Kabul') or contains(text(), 'Tümünü')]"
            ));
        } catch (Exception e) {
            // No cookie button found
        }
        
        return null;
    }
    
    private WebElement findLanguageButton() {
        try {
            if (languageButton.isDisplayed()) {
                return languageButton;
            }
        } catch (Exception e) {
            // Try alternative selectors
        }
        
        try {
            if (languageButtonAlt.isDisplayed()) {
                return languageButtonAlt;
            }
        } catch (Exception e) {
            // Continue to next alternative
        }
        
        try {
            if (languageButtonSvg.isDisplayed()) {
                return languageButtonSvg;
            }
        } catch (Exception e) {
            // Continue to next alternative
        }
        
        // Try to find by SVG path
        try {
            return driver.findElement(org.openqa.selenium.By.cssSelector("path#Combined-Shape"));
        } catch (Exception e) {
            // No language button found
        }
        
        return null;
    }

    public void searchForProduct(String productName) {
        // First accept cookies if present
        acceptCookiesIfPresent();
        
        // Then click language button if present
        clickLanguageButtonIfPresent();
        
        // Wait for page to fully load
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Try to find search input with multiple selectors
        WebElement inputToUse = findSearchInput();
        if (inputToUse != null) {
            // Click on search input first
            clickElement(inputToUse);
            
            // Wait a bit for input to be ready
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            // Clear and type the product name
            sendKeys(inputToUse, productName);
            
            // Press Enter key to search
            inputToUse.sendKeys(org.openqa.selenium.Keys.ENTER);
        } else {
            throw new RuntimeException("Search input not found on page");
        }
    }

    private WebElement findSearchInput() {
        try {
            if (searchInput.isDisplayed()) {
                return searchInput;
            }
        } catch (Exception e) {
            // Try alternative selectors
        }
        
        try {
            if (searchInputAlt1.isDisplayed()) {
                return searchInputAlt1;
            }
        } catch (Exception e) {
            // Continue to next alternative
        }
        
        try {
            if (searchInputAlt2.isDisplayed()) {
                return searchInputAlt2;
            }
        } catch (Exception e) {
            // Continue to next alternative
        }
        
        try {
            if (searchInputAlt3.isDisplayed()) {
                return searchInputAlt3;
            }
        } catch (Exception e) {
            // Continue to next alternative
        }
        
        try {
            if (searchInputAlt4.isDisplayed()) {
                return searchInputAlt4;
            }
        } catch (Exception e) {
            // Continue to next alternative
        }
        
        try {
            if (searchInputAlt5.isDisplayed()) {
                return searchInputAlt5;
            }
        } catch (Exception e) {
            // Continue to next alternative
        }
        
        try {
            if (searchInputAlt6.isDisplayed()) {
                return searchInputAlt6;
            }
        } catch (Exception e) {
            // Continue to next alternative
        }
        
        // Try to find by placeholder text (language independent)
        try {
            return driver.findElement(org.openqa.selenium.By.xpath(
                "//input[@placeholder and (contains(@placeholder, 'Aradığınız') or contains(@placeholder, 'Search') or contains(@placeholder, 'Ara') or contains(@placeholder, 'ürün') or contains(@placeholder, 'product') or contains(@placeholder, 'kategori') or contains(@placeholder, 'category') or contains(@placeholder, 'marka') or contains(@placeholder, 'brand')]"
            ));
        } catch (Exception e) {
            // No search input found by placeholder
        }
        
        return null;
    }

    public boolean isSearchInputDisplayed() {
        return findSearchInput() != null;
    }
}
