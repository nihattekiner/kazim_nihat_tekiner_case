package utils;

import base.BaseTest;
import base.Drivers;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Set;

import static constants.Constants.DEFAULT_MAX_ITERATION_COUNT;
import static constants.Constants.DEFAULT_MILLISECOND_WAIT_AMOUNT;


public class ElementHelper {

    // driverThread, ActionsThread ve waitThread (Thread-safe bekleme)
    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
    private static final ThreadLocal<Actions> actionsThread = new ThreadLocal<>();
    private static final ThreadLocal<WebDriverWait> waitThread = new ThreadLocal<>();
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Duration TIMEOUT = Duration.ofSeconds(10); // Standart bekleme süresi (10 saniye)

    // Bu nesne, BaseTest'ten bir WebDriver alarak başlatılır,
    // ancak gerçek işlemleri ThreadLocal'daki driver ile yapacaktır.
    // Bu kurucu metot, BaseTest'teki hatayı çözmek için zorunludur.
    public ElementHelper(WebDriver driver) {
        // Bu kurucu metot BaseTest'teki ElementHelper = new ElementHelper(ElementHelper.getDriver());
        // satırını desteklemek için var, ancak driverThread'ı yönetmek asıl işimiz.
        // waitThread'i de başlatıyoruz.
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
        if (waitThread.get() == null && driver != null) {
            waitThread.set(new WebDriverWait(driver, Duration.ofSeconds(10)));
        }
    }

    // --- THREADLOCAL GET METOTLARI ---
    public static WebDriver getDriver() {
        return driverThread.get();
    }

    // BaseTest'te kullandığınız getDriver() metodu bu metodu çağırır.
    public static WebDriver getDriverForBaseTest() {
        return driverThread.get();
    }

    public WebDriverWait getWait() {
        return waitThread.get();
    }

    public Actions getActions() {
        return actionsThread.get();
    }

    // --- SETUP/TEARDOWN METOTLARI ---

    // BaseTest'ten çağrılacak statik metot. ThreadLocal'ı hazırlar. (setUpStaticDriver adıyla BaseTest'e uyumlu)
    public static void setUpStaticDriver(String browserName) {
        if (driverThread.get() == null) {
            WebDriver driver = Drivers.getDriverType(browserName).getDriver();
            driverThread.set(driver);
            actionsThread.set(new Actions(driver));
            waitThread.set(new WebDriverWait(driver, Duration.ofSeconds(10))); // WaitThread'i de burada başlatın

            // Pencereyi tam ekran yap
            driver.manage().window().maximize();

            // Implicit Wait
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Sayfa yükleme zaman aşımı
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        }
    }

    // BaseTest'in @AfterClass metodundan çağrılır.
    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
            driverThread.remove();
            actionsThread.remove();
            waitThread.remove();
        }
    }

    // --- TEMEL SÜRÜCÜ İŞLEMLERİ ---

    public void goToUrl(String url) {
        getDriver().get(url);
        try {
            getDriver().switchTo().alert().accept();
        } catch (NoAlertPresentException ignore) {
        }
    }

    public void waitForPageToCompleteState() {
        int counter = 0;
        int maxNoOfRetries = DEFAULT_MAX_ITERATION_COUNT;
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        while (counter < maxNoOfRetries) {
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
            try {
                if ("complete".equals(js.executeScript("return document.readyState").toString())) {
                    break;
                }
            } catch (Exception ignored) {
            }
            counter++;
        }
    }

    public void waitByMilliSeconds(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // --- ELEMENT BULMA ve BEKLEME ---

    public WebElement findElement(By infoParam) {
        // findElement'ta da waitThread'i kullanıyoruz.
        WebElement webElement = getWait().until(ExpectedConditions.presenceOfElementLocated(infoParam));

        // Scroll işlemi
        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'})",
                webElement);
        return webElement;
    }
    /**
     * Elementin görünür olmasını bekler.
     * @param locator Beklenecek elementin bulucusu.
     * @return Bulunan WebElement.
     */
    public WebElement waitForTheElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public boolean isDisplayedBy(By by) {
        try {
            // isDisplayedBy'da BaseTest.getDriver() yerine getWait() kullanıyoruz.
            getWait().until(ExpectedConditions.presenceOfElementLocated(by));
            getWait().until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // --- TIKLAMA İŞLEMLERİ ---

    public void clickElement(WebElement element) {
        try {
            element.click();
        } catch (ElementClickInterceptedException exception) {
            javaScriptClicker(element);
        }
    }

    public void clickElement(By locator) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        } catch (ElementClickInterceptedException exception) {
            scrollByJs(locator);
            clickElement(findElement(locator));
        }
    }

    /**
     * Elementin görünür olmasını bekler, içeriğini temizler ve metin gönderir.
     * @param locator Metin gönderilecek elementin bulucusu.
     * @param text Gönderilecek metin.
     */
    public void sendKeys(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }


    public void javaScriptClicker(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", element);
    }

    // --- DİĞER İŞLEMLER ---

    public void scrollByJs(By by) {
        WebElement element = getDriver().findElement(by);
        ((JavascriptExecutor) getDriver()).executeScript(
                "var element = arguments[0];" +
                        "var rect = element.getBoundingClientRect();" +
                        "window.scrollBy({ top: rect.top + window.scrollY - (window.innerHeight / 2), behavior: 'smooth' });",
                element
        );
        waitForPageToCompleteState();
    }

    public void hoverAndClick(WebElement toHover, WebElement toClick) {
        getActions().moveToElement(toHover)
                .pause(Duration.ofMillis(300))
                .moveToElement(toClick)
                .click()
                .build()
                .perform();
        waitForPageToCompleteState();
    }

    public void checkIfElementExistLogCurrentText(By locator) {
        int loopCount = 0;
        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            if (getDriver().findElements(locator).size() > 0) {
                String elementText = getDriver().findElement(locator).getText();
                System.out.println("Element found. Text: " + elementText);
                return;
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
        }
        Assertions.fail("Element was not visible with the given locator: " + locator);
    }

    public boolean shouldSee(String text) {
        // BaseTest.getDriver() yerine getWait() kullanıldı.
        return getWait().until(ExpectedConditions.urlContains(text));
    }

    public void selectTextFromDropDown(String text, By by) {
        if (!isDisplayedBy(by)) {
            waitForTheElement(by);
        }
        Select select = new Select(findElement(by));
        select.selectByVisibleText(text);
    }

    // --- YENİ EKLENEN SELECT2 METHODU ---

    /**
     * Select2 tarzı dropdown menüden güvenli bir şekilde seçim yapar.
     * Bu metot, Açık Beklemeler kullanarak tıklama, metin gönderme ve seçeneği tıklama adımlarını kapsar.
     *
     * @param dropdownLocator Dropdown'ı açmak için tıklanacak element.
     * @param inputLocator Filtreleme için metin yazılacak input alanı.
     * @param optionText Seçilecek olan metin değeri.
     */
    public void selectSelect2Option(By dropdownLocator, By inputLocator, String optionText) {
        // 1. Dropdown'ı açmak için tıklanabilir olmasını bekleyin ve tıklayın.
        System.out.println("INFO: Dropdown'a tıklanıyor: " + dropdownLocator);
        clickElement(dropdownLocator);

        // 2. Arama input'unun görünür olmasını bekleyin ve metni yazın.
        System.out.println("INFO: Input'a metin yazılıyor: " + optionText);
        sendKeys(inputLocator, optionText);

        // 3. Seçeneğin ortaya çıkmasını bekleyin. Bu, genellikle metni içeren bir XPath ile yapılır.
        // ** ÖNEMLİ: Bu, Select2 yapısının varsayılan bir XPath'idir. Sitenize özel XPath gerekebilir. **
        By optionLocator = By.xpath("//li[contains(text(), '" + optionText + "')] | //span[text()='" + optionText + "'] | //div[text()='" + optionText + "']");

        // 4. Seçeneğin tıklanabilir olmasını bekleyin ve tıklayın.
        System.out.println("INFO: Seçenek bekleniyor ve tıklanıyor: " + optionText);
        clickElement(optionLocator);
    }

    // --- PENCERE YÖNETİM METOTLARI (GÜNCELLENDİ) ---

    /**
     * Belirtilen indeksteki (0'dan başlayan) pencereye geçer.
     * Yeni pencere açıldığında kullanışlıdır.
     * @param windowIndex Geçilmek istenen pencerenin indeksi (0: Ana pencere, 1: İlk yeni pencere vb.).
     * @return Geçiş başarılıysa true, değilse false.
     */
    public boolean switchToWindow(int windowIndex) {
        try {
            // İstenen sayıda pencere açılana kadar bekle
            getWait().until(ExpectedConditions.numberOfWindowsToBe(windowIndex + 1));

            // Tüm pencere tutamaçlarını (handles) al
            Set<String> allWindowHandles = getDriver().getWindowHandles();

            // Tutamaçları listeye çevirip indeksleme yap
            ArrayList<String> windowHandlesList = new ArrayList<>(allWindowHandles);

            if (windowHandlesList.size() > windowIndex) {
                // Belirtilen indeksteki pencereye geç
                getDriver().switchTo().window(windowHandlesList.get(windowIndex));
                System.out.println("INFO: Başarıyla " + windowIndex + ". indeksteki pencereye geçildi.");
                return true;
            } else {
                System.err.println("HATA: İstenen pencere indeksi (" + windowIndex + ") mevcut değil. Toplam pencere sayısı: " + windowHandlesList.size());
                return false;
            }
        } catch (Exception e) {
            System.err.println("HATA: Pencere geçişi başarısız oldu. Hata: " + e.getMessage());
            return false;
        }
    }


    /**
     * Bir elementin üzerine fareyle gelinmesini sağlar, ardından ikinci bir elementin tıklanabilir olmasını bekler ve tıklar.
     * Bu metot, hedefin tıklanabilir olmasını bekleyerek ElementNotInteractableException hatasını engeller.
     *
     * @param hoverLocator Üzerine gelinecek elementin bulucusu (örneğin, 'Company' menü öğesi).
     * @param clickLocator Tıklanacak elementin bulucusu (örneğin, 'Careers' alt menü bağlantısı).
     */
    public void hoverAndClick(By hoverLocator, By clickLocator) {
        // 1. Üzerine gelinecek elementin görünür olmasını bekleyin ve hover işlemini gerçekleştirin.
        WebElement hoverElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(hoverLocator)
        );

        Actions actions = new Actions(driver);
        actions.moveToElement(hoverElement).perform();

        // 2. *** KRİTİK ADIM: Hedef elementin tıklanabilir olması için açıkça bekleyin. ***
        // Bu, elementin boyutunun, konumunun ve etkileşim için hazır olmasının (başka bir element tarafından kapatılmamasının) garanti eder.
        try {
            WebElement clickElement = wait.until(
                    ExpectedConditions.elementToBeClickable(clickLocator)
            );

            // 3. Tıklanabilir hale gelen elemente tıklama işlemini gerçekleştirin.
            // Hover sonrası tıklama için Actions kullanmak bazen daha güvenilir olabilir.
            actions.moveToElement(clickElement).click().build().perform();

            System.out.println("Başarıyla " + hoverLocator + " üzerine gelindi ve " + clickLocator + " tıklandı.");

        } catch (Exception e) {
            // Hata mesajını özelleştirin
            System.err.println("Element tıklanabilir veya hazır değil: " + clickLocator);
            throw new org.openqa.selenium.ElementNotInteractableException(
                    "Hover işleminden sonra element tıklanamadı. Hedef element: " + clickLocator, e
            );
        }
    }


    /**
     * STATİK BEKLEME (TAVSİYE EDİLMEZ): Programın belirtilen süre kadar duraklamasına neden olur.
     * Testlerinizi yavaşlatır ve güvensiz hale getirir. Yalnızca zorunlu durumlarda (örneğin, animasyonların bitmesi gibi) kullanılmalıdır.
     *
     * @param seconds Beklenecek süre (saniye cinsinden).
     */
    public static void staticWait (int seconds) {
        try {
            // Saniye cinsinden gelen süreyi milisaniyeye çevirip bekletiyoruz.
            Thread.sleep(seconds * 1000L);
            System.out.println("Statik olarak " + seconds + " saniye beklenildi (Tavsiye Edilmez).");
        } catch (InterruptedException e) {
            // Eğer bekleme kesintiye uğrarsa, mevcut iş parçacığını kesintiye uğramış olarak işaretleriz.
            Thread.currentThread().interrupt();
        }
    }
    /**
     * Locator (By) kullanarak ilgili WebElement'in metin içeriğini (getText) döner.
     * Elementin görünür olmasını bekler.
     *
     * @param by Elementin bulunması için kullanılan By (Locator).
     * @return Elementin metin içeriği (String).
     */
    public String getText(By by) {
        // Elementi bulmak için zaten bekleme yapan findElement metodunu kullan
        WebElement element = findElement(by);

        // Ek olarak, metin almadan önce elementin görünürlüğünü bekle.
        // findElement içinde presence bekleniyor, burada visibility bekleyebiliriz.
        getWait().until(ExpectedConditions.visibilityOf(element));

        // Metni al ve dön
        return element.getText();
    }

    /**
     * Doğrudan WebElement nesnesinin metin içeriğini (getText) döner.
     * Bu metot, genellikle List<WebElement> üzerinden döngü yaparken kullanılır.
     *
     * @param element Metni alınacak WebElement.
     * @return Elementin metin içeriği (String).
     */
    public String getText(WebElement element) {
        // Elementin görünür olmasını bekle
        getWait().until(ExpectedConditions.visibilityOf(element));

        // Metni al ve dön
        return element.getText();
    }
    /**
     * JavaScript executor kullanarak elemente tıklar. Elementin görünürlüğüne bakılmaksızın zorla tıklama yapar.
     * Bu metot, klasik .click() metodunun başarısız olduğu durumlarda kullanılmalıdır.
     *
     * @param by Elementin bulunması için kullanılan By (Locator).
     */
    public void clickElementWithJS(By by) {
        WebElement element = findElement(by); // Elementin DOM'da varlığını bekler
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    /**
     * JavaScript executor kullanarak doğrudan WebElement nesnesine tıklar.
     *
     * @param element Tıklanacak WebElement.
     */
    public void clickElementWithJS(WebElement element) {
        // Elementin varlığını (presence) zaten findElement'tan veya başka bir yerden biliyoruz.
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }



}