# Trendyol Automation Test Project

This is a test automation suite I developed for the Trendyol website. We test e-commerce scenarios using the Cucumber BDD framework. We perform web automation with Selenium WebDriver and generate detailed reports with Extent Reports.

## Project Purpose

I wanted to test the basic scenarios we might encounter while shopping on Trendyol. That's why I wrote tests covering these functions:

- **Product Search**: Searching for products like "wireless headphones"
- **Product Details**: Checking price and stock status on product pages
- **Add to Cart**: Adding products to cart and verification
- **Cart Management**: Adding multiple products, price calculation
- **Remove Products**: Removing products from cart and price updates

## Technologies

I used these technologies while developing the project:

- **Java 8+** - Main programming language
- **Maven** - Dependency management and build tool
- **Cucumber 7.18.0** - BDD framework (test writing with Gherkin syntax)
- **Selenium WebDriver 4.21.0** - For web automation
- **JUnit 4.13.2** - Test framework
- **Extent Reports 5.1.1** - Detailed test reports
- **WebDriverManager 5.8.0** - Automatic Chrome driver management

## Project Structure

The project folder structure is organized as follows:

```
src/
├── test/
│   ├── java/
│   │   └── com/
│   │       └── trendyol/
│   │           ├── pages/           # Page Object Model classes
│   │           │   ├── BasePage.java
│   │           │   ├── CartPage.java
│   │           │   ├── SearchResultsPage.java
│   │           │   └── ProductDetailPage.java
│   │           ├── steps/           # Cucumber step definitions
│   │           │   └── TrendyolShoppingSteps.java
│   │           ├── hooks/           # Cucumber hooks (setup/teardown)
│   │           └── TestRunner.java  # Main test runner
│   └── resources/
│       ├── features/                # Cucumber feature files
│       │   └── trendyol_shopping.feature
│       └── extent.properties        # Extent Reports configuration
```

## Setup and Execution

### Requirements
To run the project, you need:
- Java 8 or higher (I used Java 17)
- Maven 3.6+ (for dependency management)
- Chrome Browser (tests run on Chrome)

### 1. Clone the Project
```bash
git clone <repository-url>
cd TrendyolAutomation
```

### 2. Install Dependencies
```bash
mvn clean install
```

### 3. Run Tests

#### Run All Tests
```bash
mvn test
```

#### Run Tests with Specific Tags
```bash
# Only search tests
mvn test -Dcucumber.filter.tags="@search"

# Only cart tests
mvn test -Dcucumber.filter.tags="@add_to_cart"

# Multiple tags
mvn test -Dcucumber.filter.tags="@search or @add_to_cart"
```

#### Run from IDE
- Open `TestRunner.java` file
- Right-click and select "Run TestRunner"

## Test Reports

After tests run, you get reports in 3 different formats:

### Cucumber HTML Report
- **Location**: `target/cucumber-reports/cucumber-pretty.html`
- Shows Cucumber test results
- Step-by-step test flow

### Extent Reports
- **Location**: `target/extent-reports/`
- Most detailed report format
- Screenshots, test timeline, dashboard
- Test logs and error details
- **Feature**: Extent Reports integration added to all methods in CartPage

### JSON Report
- **Location**: `target/cucumber-reports/CucumberTestReport.json`
- For use in CI/CD pipelines

## Test Scenarios

There are 5 main test scenarios in the project. Each tests different e-commerce functions:

### 1. Search Function (@search)
- Searching for "wireless headphones" products
- Displaying search results (24 products found)
- Finding search keywords in results

### 2. Product Details (@product_details)
- Selecting products from search results
- Displaying product name, price and stock status
- Product page opens in new tab

### 3. Add to Cart (@add_to_cart)
- Adding products to cart
- Verifying product details in cart
- Checking price and product name match

### 4. Cart Price Validation (@cart_validation)
- Adding multiple products (3 different products)
- Verifying total price
- Including shipping costs and discounts

### 5. Remove from Cart (@remove_from_cart)
- Removing products from cart
- Updating total price
- Decreasing cart count

## Configuration

### Extent Reports
You can configure report settings from `src/test/resources/extent.properties` file:

- Report formats (HTML, PDF, JSON, XML)
- Screenshot settings
- Theme and style settings
- System information

**Important**: Extent Reports log integration has been added to all methods in CartPage.java. Now cart operations are displayed in detail in test reports.

### Browser Settings
You can change browser settings from `TrendyolShoppingSteps.java` file:

```java
@Before
public void setUp() {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    // Other settings...
}
```

### Element Selectors
Updated according to Trendyol's actual HTML structure:
- Cart product names: `p.pb-item`
- Cart prices: `div.pb-basket-item-price`
- Total price: `div.pb-summary-total-price`

## Test Writing

### Adding New Test Scenarios

1. **Create Feature File**
```gherkin
@new_feature
Scenario: New test scenario
  Given precondition
  When action
  Then result
```

2. **Add Step Definition**
```java
@When("action")
public void action() {
    // Test code
}
```

3. **Add Page Object**
```java
@FindBy(css = "selector")
private WebElement element;

public void method() {
    clickElement(element);
}
```

### Extent Reports Integration
To add logs to new methods:

```java
// Pass ExtentTest in CartPage constructor
CartPage cartPage = new CartPage(driver, extentTest);

// Use log methods
logInfo("Info message");
logPass("Successful operation");
logFail("Error message");
logWarning("Warning message");
```

## Troubleshooting

### Common Issues

1. **ChromeDriver Error**
   - Make sure Chrome browser is up to date
   - WebDriverManager automatically downloads the appropriate driver

2. **Element Not Found Error**
   - Check CSS selectors (Trendyol HTML structure may change)
   - Increase page load times
   - Use explicit wait

3. **Test Fail Error**
   - Check screenshots
   - Check details in Extent Reports
   - Check console logs

4. **Cart Price Calculation Error**
   - Discounts and shipping costs may change on Trendyol
   - Check `getTotalSavings()` method
   - Price formats should be current

### Debug Mode
```bash
mvn test -Dmaven.surefire.debug
```

### Element Selector Updates
If Trendyol HTML structure changes, you may need to update selectors in CartPage.java:
```java
@FindBy(css = "new-selector")
private WebElement element;
```

## CI/CD Entegrasyonu

### GitHub Actions
```yaml
name: Test Automation
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up JDK
        uses: actions/setup-java@v2
        with:
          java-version: '8'
      - name: Run Tests
        run: mvn test
```

### Jenkins
```groovy
pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Publish Reports') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'target/extent-reports',
                    reportFiles: 'index.html',
                    reportName: 'Extent Reports'
                ])
            }
        }
    }
}
```

## Katkıda Bulunma

1. Fork yapın
2. Feature branch oluşturun (`git checkout -b feature/AmazingFeature`)
3. Commit yapın (`git commit -m 'Add some AmazingFeature'`)
4. Push yapın (`git push origin feature/AmazingFeature`)
5. Pull Request oluşturun

## Lisans

Bu proje MIT lisansı altında lisanslanmıştır.

## İletişim

- Proje: [GitHub Repository](https://github.com/username/TrendyolAutomation)
- Sorular için: [Issues](https://github.com/username/TrendyolAutomation/issues)

## Updates

### v1.1.0 (Current)
- **Extent Reports Integration**: Log integration added to all methods in CartPage
- **Element Selector Updates**: Updated according to Trendyol's actual HTML structure
- **Price Calculation Improvements**: Cart price calculation methods developed
- **Error Management**: More robust error handling and fallback mechanisms

### v1.0.0
- Basic test scenarios added
- Page Object Model implementation
- Cucumber BDD framework
- Maven build system

---

**Note:** This project was created for educational purposes. Permission from the site owner is recommended for real website testing.

## Final Notes

The most challenging part while developing the project was understanding Trendyol's HTML structure. Especially the element selectors on the cart page keep changing. That's why I used fallback mechanisms and multiple selector strategies.

Extent Reports integration was also quite useful. You can see in detail what happened at each step in test reports. This makes test debugging much easier.
