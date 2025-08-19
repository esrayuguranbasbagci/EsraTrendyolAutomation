# Trendyol Automation Test Project

Bu proje, Trendyol web sitesi için Cucumber, Java ve Extent Reports kullanarak oluşturulmuş test otomasyon projesidir.

## 🎯 Proje Amacı

Trendyol web sitesinin temel alışveriş fonksiyonlarını test etmek:
- Ürün arama
- Ürün detaylarını görüntüleme
- Sepete ürün ekleme
- Sepet fiyat doğrulama
- Sepetten ürün çıkarma

## 🛠️ Teknolojiler

- **Java 8+**
- **Maven** - Dependency Management
- **Cucumber 7.18.0** - BDD Framework
- **Selenium WebDriver 4.21.0** - Web Automation
- **JUnit 4.13.2** - Test Framework
- **Extent Reports 5.1.1** - Test Reporting
- **WebDriverManager 5.8.0** - Driver Management

## 📁 Proje Yapısı

```
src/
├── test/
│   ├── java/
│   │   └── com/
│   │       └── trendyol/
│   │           ├── pages/           # Page Object Model
│   │           ├── steps/           # Cucumber Step Definitions
│   │           ├── hooks/           # Cucumber Hooks
│   │           └── TestRunner.java  # Test Runner
│   └── resources/
│       ├── features/                # Cucumber Feature Files
│       └── extent.properties        # Extent Reports Configuration
```

## 🚀 Kurulum ve Çalıştırma

### Gereksinimler
- Java 8 veya üzeri
- Maven 3.6+
- Chrome Browser

### 1. Projeyi Klonlayın
```bash
git clone <repository-url>
cd TrendyolAutomation
```

### 2. Bağımlılıkları Yükleyin
```bash
mvn clean install
```

### 3. Testleri Çalıştırın

#### Tüm Testleri Çalıştırma
```bash
mvn test
```

#### Belirli Tag ile Test Çalıştırma
```bash
# Sadece arama testleri
mvn test -Dcucumber.filter.tags="@search"

# Sadece sepet testleri
mvn test -Dcucumber.filter.tags="@add_to_cart"

# Birden fazla tag
mvn test -Dcucumber.filter.tags="@search or @add_to_cart"
```

#### IDE'den Çalıştırma
- `TestRunner.java` dosyasını açın
- Sağ tık yapıp "Run TestRunner" seçin

## 📊 Test Raporları

### Cucumber HTML Raporu
- Konum: `target/cucumber-reports/cucumber-pretty.html`
- Cucumber test sonuçlarını gösterir

### Extent Reports
- Konum: `target/extent-reports/`
- Detaylı test raporları
- Screenshot'lar
- Test timeline
- Dashboard

### JSON Raporu
- Konum: `target/cucumber-reports/CucumberTestReport.json`
- CI/CD entegrasyonu için

## 🧪 Test Senaryoları

### 1. Arama Fonksiyonu (@search)
- "kablosuz kulaklik" ürünü için arama yapma
- Arama sonuçlarının görüntülenmesi
- Sonuçlarda arama kelimesinin bulunması

### 2. Ürün Detayları (@product_details)
- Arama sonuçlarından ürün seçme
- Ürün adı, fiyat ve stok durumunun görüntülenmesi

### 3. Sepete Ekleme (@add_to_cart)
- Ürünü sepete ekleme
- Sepette ürün detaylarının doğrulanması

### 4. Sepet Fiyat Doğrulama (@cart_validation)
- Birden fazla ürün ekleme
- Toplam fiyatın doğrulanması

### 5. Sepetten Çıkarma (@remove_from_cart)
- Sepetten ürün çıkarma
- Toplam fiyatın güncellenmesi

## 🔧 Konfigürasyon

### Extent Reports
`src/test/resources/extent.properties` dosyasından rapor ayarlarını yapabilirsiniz:

- Rapor formatları (HTML, PDF, JSON, XML)
- Screenshot ayarları
- Tema ve stil ayarları
- Sistem bilgileri

### Browser Ayarları
`TrendyolShoppingSteps.java` dosyasından browser ayarlarını değiştirebilirsiniz:

```java
@Before
public void setUp() {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    // Diğer ayarlar...
}
```

## 📝 Test Yazma

### Yeni Test Senaryosu Ekleme

1. **Feature Dosyası Oluşturma**
```gherkin
@new_feature
Scenario: Yeni test senaryosu
  Given ön koşul
  When aksiyon
  Then sonuç
```

2. **Step Definition Ekleme**
```java
@When("aksiyon")
public void aksiyon() {
    // Test kodu
}
```

3. **Page Object Ekleme**
```java
@FindBy(css = "selector")
private WebElement element;

public void method() {
    clickElement(element);
}
```

## 🐛 Sorun Giderme

### Yaygın Sorunlar

1. **ChromeDriver Hatası**
   - Chrome browser'ın güncel olduğundan emin olun
   - WebDriverManager otomatik olarak uygun driver'ı indirir

2. **Element Bulunamadı Hatası**
   - CSS selector'ları kontrol edin
   - Sayfa yüklenme sürelerini artırın
   - Explicit wait kullanın

3. **Test Fail Hatası**
   - Screenshot'ları kontrol edin
   - Extent Reports'ta detayları inceleyin
   - Console log'ları kontrol edin

### Debug Modu
```bash
mvn test -Dmaven.surefire.debug
```

## 📈 CI/CD Entegrasyonu

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

## 🤝 Katkıda Bulunma

1. Fork yapın
2. Feature branch oluşturun (`git checkout -b feature/AmazingFeature`)
3. Commit yapın (`git commit -m 'Add some AmazingFeature'`)
4. Push yapın (`git push origin feature/AmazingFeature`)
5. Pull Request oluşturun

## 📄 Lisans

Bu proje MIT lisansı altında lisanslanmıştır.

## 📞 İletişim

- Proje: [GitHub Repository](https://github.com/username/TrendyolAutomation)
- Sorular için: [Issues](https://github.com/username/TrendyolAutomation/issues)

## 🔄 Güncellemeler

### v1.0.0
- Temel test senaryoları eklendi
- Page Object Model implementasyonu
- Extent Reports entegrasyonu
- Cucumber BDD framework
- Maven build sistemi

---

**Not:** Bu proje eğitim amaçlı oluşturulmuştur. Gerçek web sitesi testlerinde site sahibinden izin alınması önerilir.
