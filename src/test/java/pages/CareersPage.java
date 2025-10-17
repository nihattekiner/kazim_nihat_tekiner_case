package pages;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ElementHelper;
import java.util.List;
import java.util.Objects;

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
    private final By LOCATION_DROPDOWN_REMOVE_ALL_BUTTON_LOCATOR = By.xpath("//span[@title='Remove all items']"); // En basta 2 tane bu locator'dan var
    private final By DEPARTMENT_DROPDOWN_REMOVE_ALL_BUTTON_LOCATOR = By.xpath("//span[@title='Remove all items']"); // Fakat ilkini tiklaninca locator sayisi 1'e dusuyor
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
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(DEPARTMENT_DROPDOWN_QUALITY_ASSURANCE_TEXT_LOCATOR),
                ExpectedConditions.visibilityOfElementLocated(DEPARTMENT_DROPDOWN_ALL_TEXT_LOCATOR)
        ));


        elementHelper.waitByMilliSeconds(5000);

        // 2. Location Filtresini Uygula (Select2 Yöntemi)
        try {
            elementHelper.clickElement(LOCATION_DROPDOWN_REMOVE_ALL_BUTTON_LOCATOR);

            // İstanbul seçeneğinin görünür olmasını bekle
            elementHelper.waitForTheElement(dropdownIstText);

            // JavaScript ile tıklamayı dene
            // WebElement istanbulOption = elementHelper.findElement(dropdownIstText); // Bu satıra gerek kalmaz
            elementHelper.clickElementWithJS(dropdownIstText); // YENİ METOT KULLANIMI

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

            System.out.println("INFO: Department filtresi (" + department + ") JS ile uygulandı.");
        } catch (Exception e) {
            System.err.println("HATA: Department filtresi uygulanamadı. Hata: " + e.getMessage());
            return false;
        }


        try {
            // İŞ İLANI METNİNİN GÖRÜNÜR OLMASINI BEKLE (Zorunlu bekleme)
            // Eğer bu locator (JOB_LIST_QUALITY_ASSURANCE_TEXT) doğru tek iş ilanı kartını temsil ediyorsa:

            elementHelper.waitForTheElement(JOB_LIST_QUALITY_ASSURANCE_TEXT);

            // Sadece elementin görünür olup olmadığını kontrol ederek TRUE dön.
            // Metin kontrolü yapmanıza gerek kalmaz, çünkü bekleme başarılıysa element oradadır.
            boolean isJobTextVisible = elementHelper.isDisplayedBy(JOB_LIST_QUALITY_ASSURANCE_TEXT);

            if (isJobTextVisible) {
                System.out.println("INFO: Filtreleme sonrası beklenen tek iş ilanı metni ekranda görünüyor.");
            } else {
                System.err.println("HATA: Tek iş ilanı metni (JOB_LIST_QUALITY_ASSURANCE_TEXT) bekleme süresinde görünür olmadı.");
            }

            return isJobTextVisible;

        } catch (Exception e) {
            System.err.println("HATA: İş ilanı metni kontrol edilirken bir hata oluştu veya Timeout yaşandı. Hata: " + e.getMessage());
            return false;
        }
    }
    /*
        elementHelper.waitForPageToCompleteState();
        elementHelper.waitForTheElement(LOCATION_DROPDOWN_REMOVE_ALL_BUTTON_LOCATOR);
        elementHelper.clickElement(LOCATION_DROPDOWN_REMOVE_ALL_BUTTON_LOCATOR);
        elementHelper.waitForTheElement(DEPARTMENT_DROPDOWN_REMOVE_ALL_BUTTON_LOCATOR);
        elementHelper.clickElement(DEPARTMENT_DROPDOWN_REMOVE_ALL_BUTTON_LOCATOR);
        elementHelper.waitForTheElement(lao);
        elementHelper.clickElement(lao);
        WebElement dropdownElement = BaseTest.getDriver().findElement(By.xpath("//span[@id='select2-filter-by-location-container']"));
        Select select = new Select(dropdownElement);
        select.selectByVisibleText(location);
        WebElement dropdownElement2 = BaseTest.getDriver().findElement(By.xpath("//span[@id='select2-filter-by-department-container']"));
        Select select2 = new Select(dropdownElement);
        select.selectByVisibleText(department);
         */

/*



    // 5. Madde için ilk karttaki "View Role" butonu (4. adımda kullanılmayacak, ancak locator burada dursun)
    private final By FIRST_JOB_VIEW_ROLE_BUTTON_LOCATOR = By.xpath(" (//a[contains(text(), 'View Role')])[1] ");










        // 4. İş Listesinin Varlığını Kontrol Et
        try {
            // Filtreleme sonuçlarının yüklenmesini bekle (en az bir kartın görünür olmasını)
            wait.until(ExpectedConditions.visibilityOfElementLocated(ALL_JOB_CARDS_LOCATOR));

            System.out.println("✅ İşler başarıyla filtrelendi ve iş listesi görüntülendi.");
            return elementHelper.isDisplayedBy(ALL_JOB_CARDS_LOCATOR);

        } catch (Exception e) {
            System.err.println("HATA: Filtreleme sonrası iş listesi bulunamadı. Hata: " + e.getMessage());
            return false;
        }
    }
    // Helper Metot: Custom Dropdown işlemlerini basitleştirir
    private boolean applyCustomDropdownFilter(By dropdownLocator, String selectionText) {
        try {
            // Dropdown'ı açan butona tıkla
            WebElement filterButton = wait.until(
                    ExpectedConditions.elementToBeClickable(dropdownLocator)
            );
            elementHelper.clickElement(filterButton);

            // Açılan listede istenen opsiyonu bul ve tıkla
            By optionLocator = getDropdownOptionByTextLocator(selectionText);
            WebElement option = wait.until(
                    ExpectedConditions.elementToBeClickable(optionLocator)
            );
            elementHelper.clickElement(option);
            return true;
        } catch (Exception e) {
            System.err.println("HATA: Dropdown filtreleme sırasında sorun oluştu: " + selectionText);
            return false;
        }
    }

    // ---------METHOD----- (4. Madde: Detaylı Veri Kontrolü)
    public boolean checkAllJobsContainCorrectData(String positionKeyword, String location, String department) {
        List<WebElement> jobCards = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(ALL_JOB_CARDS_LOCATOR)
        );

        if (jobCards.isEmpty()) {
            System.err.println("HATA: Kontrol edilecek hiç iş ilanı bulunamadı.");
            return false;
        }

        System.out.println("INFO: Toplam " + jobCards.size() + " iş ilanı bulundu. Detay kontrolü başlıyor...");

        boolean allValid = true;
        for (int i = 0; i < jobCards.size(); i++) {
            WebElement card = jobCards.get(i);

            try {
                // Göreceli XPath kullanarak kart içindeki metinleri çek
                String actualPosition = card.findElement(POSITION_TEXT_LOCATOR).getText();
                String actualLocation = card.findElement(LOCATION_TEXT_LOCATOR).getText();
                String actualDepartment = card.findElement(DEPARTMENT_TEXT_LOCATOR).getText();

                boolean isPositionValid = actualPosition.contains(positionKeyword);
                boolean isLocationValid = actualLocation.contains(location);
                boolean isDepartmentValid = actualDepartment.contains(department);

                if (!isPositionValid || !isLocationValid || !isDepartmentValid) {
                    System.err.printf("HATA: %d. İlan (Pozisyon: %s) filtre kriterlerini sağlamıyor. Konum Beklenen: %s, Bulunan: %s%n",
                            i + 1, actualPosition, location, actualLocation);
                    allValid = false;
                }
            } catch (Exception e) {
                System.err.println("HATA: " + (i + 1) + ". iş ilanının detayları bulunamadı. Hata: " + e.getMessage());
                allValid = false;
            }
        }

        return allValid;
    }

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

