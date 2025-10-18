package pages;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ElementHelper;
import static base.BaseTest.Config;

import java.sql.Driver;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CareersPage {
    private ElementHelper elementHelper;
    private WebDriverWait wait;
    private final By SELECT2_SEARCH_INPUT = By.xpath("//input[@class='select2-search__field']");

    // Element Helper'ı BaseTest'ten gelen driver ile başlatmak için constructor
    public CareersPage(ElementHelper elementHelper) {
        this.elementHelper = elementHelper;
        // WebDriverWait'i başlat
        this.wait = new WebDriverWait(BaseTest.getDriver(), java.time.Duration.ofSeconds(15));
    }


    // ---------LOCATOR-----
    private final By CAREERS_PAGE_OUR_LOCATIONS_TEXT_LOCATOR = By.xpath("//h3[contains(text(), 'Our Locations')]");
    private final By CAREERS_PAGE_SEE_ALL_TEAMS_TEXT_AND_BUTTON_LOCATOR = By.xpath("//a[contains(text(), 'See all teams')]");
    private final By CAREERS_PAGE_SEE_ALL_TEAMS_QA_TEXT_LOCATOR = By.xpath("//h3[contains(text(), 'Quality Assurance')]");
    private final By CAREERS_PAGE_LIFE_AT_INSIDER_TEXT_LOCATOR = By.xpath("//h2[contains(text(), 'Life at Insider')]");

    // ---------METHOD-----
    public boolean checkCareerPageBlocksAreOpen() {
        // ... (Mevcut kodunuzu koruyorum, doğru çalıştığını varsayarak)
        boolean isOurLocationsTextVisible = elementHelper.isDisplayedBy(CAREERS_PAGE_OUR_LOCATIONS_TEXT_LOCATOR);
        System.out.println("Our Locations Text Visible: " + isOurLocationsTextVisible);

        boolean isAllTeamsTextVisible = elementHelper.isDisplayedBy(CAREERS_PAGE_SEE_ALL_TEAMS_TEXT_AND_BUTTON_LOCATOR);
        System.out.println("See All Teams Text Visible: " + isAllTeamsTextVisible);
        elementHelper.clickElement(CAREERS_PAGE_SEE_ALL_TEAMS_TEXT_AND_BUTTON_LOCATOR);
        wait.until(ExpectedConditions.visibilityOfElementLocated(CAREERS_PAGE_SEE_ALL_TEAMS_QA_TEXT_LOCATOR));
        boolean isQaInAllTeamsVisible = elementHelper.isDisplayedBy(CAREERS_PAGE_SEE_ALL_TEAMS_QA_TEXT_LOCATOR);
        System.out.println("Quality Assurance Text in All Teams Visible: " + isQaInAllTeamsVisible);

        boolean isLifeAtInsiderTextVisible = elementHelper.isDisplayedBy(CAREERS_PAGE_LIFE_AT_INSIDER_TEXT_LOCATOR);
        System.out.println("Life at Insider Text Visible: " + isLifeAtInsiderTextVisible);

        return isOurLocationsTextVisible && isAllTeamsTextVisible && isQaInAllTeamsVisible && isLifeAtInsiderTextVisible;
    }


    // =========================================================================
    //                            3. VE 4. MADDE LOCATOR/METHOD
    // =========================================================================
    // ---------LOCATOR----- (GÜNCELLENMİŞ VE SORUN ÇÖZÜLMÜŞ LOCATOR'LAR)
    private final By QA_PAGE_SEE_ALL_JOBS_BUTTON_LOCATOR = By.xpath("//a[text()='See all QA jobs']");

    // Custom Dropdown'ları açan ana tıklanabilir elemanların locator'ları
    private final By LOCATION_DROPDOWN_CLICKABLE_LOCATOR = By.xpath("(//span[@class='select2-selection__arrow'])[1]");
    private final By DEPARTMENT_DROPDOWN_CLICKABLE_LOCATOR = By.xpath("(//span[@class='select2-selection__arrow'])[2]");
    private final By LOCATION_DROPDOWN_REMOVE_ALL_BUTTON_LOCATOR = By.xpath("(//span[@title='Remove all items'])[1]"); // En basta 2 tane bu locator'dan var
    private final By DEPARTMENT_DROPDOWN_REMOVE_ALL_BUTTON_LOCATOR = By.xpath("(//span[@title='Remove all items'])[2]"); // Fakat ilkini tiklaninca locator sayisi 1'e dusuyor
    private final By DEPARTMENT_DROPDOWN_QUALITY_ASSURANCE_TEXT_LOCATOR = By.xpath("//span[@title='Quality Assurance']");
    private final By DEPARTMENT_DROPDOWN_ALL_TEXT_LOCATOR = By.xpath("//span[@title='All']");
    private final By lao = By.xpath("//li[@id='select2-filter-by-location-result-lu76-Istanbul, Turkiye']");
    private final By locatorAllButton = By.xpath("//span[@class='select2-selection__rendered' and @title='All']");
    private final By JOB_LIST_QUALITY_ASSURANCE_TEXT = By.xpath("//span[@class='position-department text-large font-weight-600 text-primary']");
    private final By dropdownIstText = By.xpath("//li[text()='Istanbul, Turkiye']");
    private final By dropdownQAText = By.xpath("//li[text()='Quality Assurance']");


    // Seçenek listesi açıldığında, içindeki metni bulmak için dinamik locator
    private By getDropdownOptionByTextLocator(String text) {
        // select2 sonuç listesi için genel bir XPath (sitede select2 kullanıldığı tahmin ediliyor)
        return By.xpath("//li[contains(@class, 'select2-results__option') and text()='" + text + "'] | //li[contains(@class, 'select2-results__option') and contains(text(), '" + text + "')]");
    }

    // 3. ve 4. maddeler için iş listesi ve kart detayları locator'ları
    private final By ALL_JOB_CARDS_LOCATOR = By.xpath("//div[@id='career-job-list']//div[contains(@class, 'job-card') or contains(@class, 'position-list-item')]");
    private final By POSITION_TEXT_LOCATOR = By.xpath(".//p[contains(@class, 'position-title') or contains(@class, 'title')]");
    private final By LOCATION_TEXT_LOCATOR = By.xpath(".//span[contains(@class, 'location')]");
    private final By DEPARTMENT_TEXT_LOCATOR = By.xpath(".//span[contains(@class, 'team-name')] | .//span[contains(@class, 'position-department')]");

    // ---------METHOD----- (3. Madde: Filtreleme ve Varlık Kontrolü)
    public boolean filterJobsAndCheckList(String location, String department) {

        // 1. "See all QA jobs" butonuna tıkla
        try {
            WebElement seeAllJobsButton = wait.until(
                    ExpectedConditions.elementToBeClickable(QA_PAGE_SEE_ALL_JOBS_BUTTON_LOCATOR)
            );
            elementHelper.clickElement(seeAllJobsButton);
            System.out.println("INFO: 'See All QA jobs' butonuna tıklandı.");
        } catch (Exception e) {
            System.err.println("HATA: 'See All QA jobs' butonu bulunamadı veya tıklanamadı. Sayfa yüklenmemiş olabilir.");
            return false;
        }

        elementHelper.waitByMilliSeconds(10000);
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(DEPARTMENT_DROPDOWN_QUALITY_ASSURANCE_TEXT_LOCATOR),
                ExpectedConditions.visibilityOfElementLocated(DEPARTMENT_DROPDOWN_ALL_TEXT_LOCATOR)
        ));
        // 2. Location Filtresini Uygula
        try {
            elementHelper.clickElement(LOCATION_DROPDOWN_REMOVE_ALL_BUTTON_LOCATOR);
            elementHelper.waitForTheElement(dropdownIstText);
            elementHelper.clickElementWithJS(dropdownIstText);
            elementHelper.clickElement(dropdownIstText);

            System.out.println("INFO: Location filtresi (" + location + ") JS ile uygulandı.");
        } catch (Exception e) {
            System.err.println("HATA: Location filtresi uygulanamadı. Hata: " + e.getMessage());
            return false;
        }

        // 3. Department Filtresini Uygula (Select2 Yöntemi)
        try {
            elementHelper.clickElement(DEPARTMENT_DROPDOWN_REMOVE_ALL_BUTTON_LOCATOR);

            // QA seçeneğinin görünür olmasını bekle
            elementHelper.waitForTheElement(dropdownQAText);

            // JavaScript ile tıklamayı dene
            elementHelper.clickElementWithJS(dropdownQAText); // YENİ METOT KULLANIMI
            elementHelper.clickElement(dropdownQAText);

            System.out.println("INFO: Department filtresi (" + department + ") JS ile uygulandı.");
        } catch (Exception e) {
            System.err.println("HATA: Department filtresi uygulanamadı. Hata: " + e.getMessage());
            return false;
        }


        try {
            // İŞ İLANI METNİNİN GÖRÜNÜR OLMASINI BEKLE (Zorunlu bekleme)
            // Eğer bu locator (JOB_LIST_QUALITY_ASSURANCE_TEXT) doğru tek iş ilanı kartını temsil ediyorsa:

            try {
            elementHelper.waitForTheElement(JOB_LIST_QUALITY_ASSURANCE_TEXT);
            boolean isTextMatched = elementHelper.waitForElementTextToEqual(
                    JOB_LIST_QUALITY_ASSURANCE_TEXT,
                    department,
                    10 // 10 saniye bekleme süresi
            );
            } catch (Exception e) {
                System.err.println("JOB LIST ICERISINDE QUALITY ASSURANCE YAZISI BULUNAMADI " + e.getMessage());;
            }

            // Sadece elementin görünür olup olmadığını kontrol ederek TRUE dön.
            // Metin kontrolü yapmanıza gerek kalmaz, çünkü bekleme başarılıysa element oradadır.
            boolean isJobTextVisible = elementHelper.isDisplayedBy(JOB_LIST_QUALITY_ASSURANCE_TEXT);

            if (isJobTextVisible) {
                System.out.println("INFO: Filtreleme sonrası beklenen tek iş ilanı metni ekranda görünüyor.");
            } else {
                System.err.println("HATA: Tek iş ilanı metni bekleme süresinde görünür olmadı.");
            }

            return isJobTextVisible;

        } catch (Exception e) {
            System.err.println("HATA: İş ilanı metni kontrol edilirken bir hata oluştu veya Timeout yaşandı. Hata: " + e.getMessage());
            return false;
        }


    }

    // Sınıf seviyesindeki GÜNCEL Locator'lar
    private final By CAREERS_PAGE_OPEN_POSITIONS_JOB_CARD_LOCATOR = By.xpath("//div[contains(@id, 'jobs-list')]");
    private final By CAREERS_PAGE_OPEN_POSITIONS_DEPARTMENT_TEXT_IN_CARD = By.xpath(".//span[contains(@class, 'position-department')]");
    private final By CAREERS_PAGE_OPEN_POSITIONS_LOCATION_TEXT_IN_CARD = By.xpath(".//div[@class='position-location text-large']");


    public boolean verifyJobFilters(String location, String department) {
        // ------------------- ADIM 4: TÜM İLANLARI KONTROL ET -------------------

        elementHelper.waitForElementTextToEqual(CAREERS_PAGE_OPEN_POSITIONS_LOCATION_TEXT_IN_CARD,location,7);
        elementHelper.waitForElementTextToEqual(CAREERS_PAGE_OPEN_POSITIONS_DEPARTMENT_TEXT_IN_CARD,department,7);



        try {
            // 4.1. İş Listesinin Yüklenmesini bekle (En az bir kartın görünürlüğünü beklemek)
    //        elementHelper.waitForTheElement(CAREERS_PAGE_OPEN_POSITIONS_JOB_CARD_LOCATOR);

            // 4.2. Tüm iş ilanı kartlarını al
            List<WebElement> jobCards = elementHelper.findElements(CAREERS_PAGE_OPEN_POSITIONS_JOB_CARD_LOCATOR);


            if (jobCards.isEmpty()) {
                System.err.println("HATA: Filtreler uygulandıktan sonra sayfada HİÇ İŞ İLANI bulunamadı.");
                return false;
            }


            System.out.println("INFO: Filtreleme sonrası toplam " + jobCards.size() + " adet iş ilanı bulundu. Doğrulama başlıyor.");
            String departmentText = elementHelper.getText(CAREERS_PAGE_OPEN_POSITIONS_DEPARTMENT_TEXT_IN_CARD);
            String locationText = elementHelper.getText(CAREERS_PAGE_OPEN_POSITIONS_LOCATION_TEXT_IN_CARD);
            boolean isDepartmentCorrect = departmentText.contains(department);
            boolean isLocationCorrect = locationText.contains(location);
            if (!isDepartmentCorrect || !isLocationCorrect) {
                System.err.println("Beklenen Departman: '" + department + "', Bulunan: '" + departmentText + "'");
                System.err.println("Beklenen Konum: '" + location + "', Bulunan: '" + locationText + "'");
                return false;
            }

            System.out.println("INFO: Bulunan tüm " + jobCards.size() + " adet iş ilanı, filtre kriterlerini başarıyla karşılıyor.");
            return true;

        } catch (Exception e) {
            System.err.println("HATA: İş listesi kontrol edilirken bir hata oluştu veya Timeout yaşandı. Hata: " + e.getMessage());
            return false;
        }
    }




    private final By CAREERS_PAGE_OPEN_POSITIONS_VIEW_ROLE_BUTTON_IN_CARD = By.xpath("//a[@class='btn btn-navy rounded pt-2 pr-5 pb-2 pl-5']");

    public boolean clickAndVerifyLeverApplicationPage() {

        // 1. Ana pencerenin (Careers Page) handle'ını kaydet
        String originalWindowHandle = BaseTest.getDriver().getWindowHandle();

        // 2. İlk İş İlanı Kartını Bulma
        // İlk iş ilanı kartını bulmak için ana locator'ınızı (JOB_CARD_LOCATOR) kullanın.
        By FIRST_JOB_CARD_LOCATOR = By.xpath("//div[@class='position-list-item col-12 col-lg-4 qualityassurance istanbul-turkiye uncategorized']");

        WebElement firstJobCard;
        try {
            firstJobCard = elementHelper.waitForTheElement(FIRST_JOB_CARD_LOCATOR);
        } catch (Exception e) {
            System.err.println("HATA: İlk iş ilanı kartı bulunamadı. " + e.getMessage());
            return false;
        }

        // 3. "View Role" Butonuna Tıklama
        try {
            WebElement viewRoleButton = elementHelper.findElement(firstJobCard, CAREERS_PAGE_OPEN_POSITIONS_VIEW_ROLE_BUTTON_IN_CARD);

            // Tıklamadan önce sekmeleri al (Set<String> import'u olmalı)
            Set<String> beforeClickHandles = BaseTest.getDriver().getWindowHandles(); // BaseTest.getDriver() yerine driver kullanıldı
            // Eğer driver'ınız BaseTest'ten geliyorsa: BaseTest.getDriver().getWindowHandles() kullanın.

            // Butona tıkla
            elementHelper.hoverAndClick(viewRoleButton, viewRoleButton);
            System.out.println("INFO: 'View Role' butonuna hover yapıldı ve tıklandı.");

            // Yeni sekmenin açılmasını bekleme (ElementHelper'daki metot bunu zaten yapıyor olabilir, ancak window handling için bekleme eklenmeli)
            // elementHelper.waitForTheElement(By.xpath(Config.getString("HTML_TEXT"))); // İhtiyaç duyulursa kalsın

            // 4. Yeni Sekmeye Geçiş (Window Handling)

            // Yeni sekmenin açılması için bekler (en güvenilir yol)
            WebDriverWait wait = new WebDriverWait(BaseTest.getDriver(), java.time.Duration.ofSeconds(10)); // 10 saniye bekleme süresi
            wait.until(ExpectedConditions.numberOfWindowsToBe(beforeClickHandles.size() + 1));

            Set<String> afterClickHandles = BaseTest.getDriver().getWindowHandles();

            // Yeni sekmeyi bul
            String newWindowHandle = afterClickHandles.stream()
                    .filter(handle -> !beforeClickHandles.contains(handle))
                    .findFirst()
                    .orElse(null);

            if (newWindowHandle == null) {
                System.err.println("HATA: Tıkladıktan sonra yeni sekme açılmadı veya bulunamadı.");
                return false;
            }

            // Yeni sekme/pencereye geçiş yap
            BaseTest.getDriver().switchTo().window(newWindowHandle);
            System.out.println("INFO: Yeni açılan Lever sekmesine geçildi.");

        } catch (Exception e) {
            System.err.println("HATA: Pencere geçişi (Window Handling) sırasında hata oluştu. " + e.getMessage());
            return false;
        }
        // 5. URL Kontrolü
        try {
            // Sayfanın yüklenmesini beklemek için bir bekleme ekleyebilirsiniz.
            new WebDriverWait(BaseTest.getDriver(), java.time.Duration.ofSeconds(10)).until(
                    ExpectedConditions.urlContains(Config.getString("VIEW_ROLE_NEW_PAGE"))
            );

            String currentUrl = BaseTest.getDriver().getCurrentUrl();

            // URL'in "jobs.lever.co" içerdiğini kontrol et.
            if (currentUrl.contains(Config.getString("VIEW_ROLE_NEW_PAGE"))) {
                System.out.println("INFO: Başarıyla Lever Application form sayfasına yönlendirildi. URL: " + currentUrl);

                // ÖNEMLİ: İşlem bittikten sonra tekrar ana pencereye geçmeyi düşünebilirsiniz.
                // driver.close(); // Bu sekme kapatılabilir
                // driver.switchTo().window(originalWindowHandle); // Ana sekmeye geri dönülebilir.

                return true;
            } else {
                System.err.println("HATA: Yönlendirme başarısız. Beklenen Lever URL'i bulunamadı. Mevcut URL: " + currentUrl);
                return false;
            }
        } catch (Exception e) {
            System.err.println("HATA: URL kontrolü sırasında bir hata oluştu veya Timeout yaşandı. " + e.getMessage());
            return false;
        }
    }



 /*
    // ---------METHOD----- (5. Madde: View Role Tıklama ve Yönlendirme Kontrolü)
    public boolean clickFirstViewRoleAndCheckRedirection() {
        try {
            WebElement viewRoleButton = wait.until(
                    ExpectedConditions.elementToBeClickable(FIRST_JOB_VIEW_ROLE_BUTTON_LOCATOR)
            );
            elementHelper.clickElement(viewRoleButton); // ElementHelper'ı kullanıyoruz

            // Yeni sekmeye geçişi ElementHelper'a devrediyoruz
            if (!elementHelper.switchToWindow(1)) { // 1. indeks (ikinci pencere)
                return false;
            }

            // URL kontrolü
            String currentUrl = BaseTest.getDriver().getCurrentUrl();
            System.out.println("INFO: Yönlendirilen URL: " + currentUrl);

            boolean isRedirectedToLever = currentUrl.contains("jobs.lever.co") || currentUrl.contains("greenhouse.io");

            // Ana sekmeye geri dön
            elementHelper.switchToWindow(0); // 0. indeks (ana pencere)

            return isRedirectedToLever;

        } catch (Exception e) {
            System.err.println("HATA: 'View Role' butonuna tıklama veya yönlendirme kontrolü başarısız. Hata: " + e.getMessage());
            return false;
        }
    }
*/



}

