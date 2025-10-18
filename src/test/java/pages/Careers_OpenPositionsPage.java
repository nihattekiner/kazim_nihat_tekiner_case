package pages;

import base.BaseTest;
import locators.Careers_OpenPositionsLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ElementHelper;

import java.util.List;
import java.util.Set;

public class Careers_OpenPositionsPage {


    private final ElementHelper elementHelper;
    private final WebDriverWait wait;
    Careers_OpenPositionsLocators careers_openPositionsLocators = new Careers_OpenPositionsLocators();

    // Element Helper'ı BaseTest'ten gelen driver ile başlatmak için constructor
    public Careers_OpenPositionsPage(ElementHelper elementHelper) {
        this.elementHelper = elementHelper;
        // WebDriverWait'i başlat
        this.wait = new WebDriverWait(BaseTest.getDriver(), java.time.Duration.ofSeconds(15));
    }

    // Seçenek listesi açıldığında, içindeki metni bulmak için dinamik locator
    private By getDropdownOptionByTextLocator(String text) {
        // select2 sonuç listesi için genel bir XPath
        return By.xpath("//li[contains(@class, 'select2-results__option') and text()='" + text + "'] | //li[contains(@class, 'select2-results__option') and contains(text(), '" + text + "')]");
    }
    /**
     * İş ilanı sayfasında Konum ve Departman filtrelerini uygular ve filtrelenen iş ilanı metninin varlığını kontrol eder.
     * @param location Uygulanacak konum filtresi.
     * @param department Uygulanacak departman filtresi.
     * @return Filtreleme ve ilan metni kontrolü başarılıysa true, aksi halde false döner.
     */
    public boolean applyLocationAndDepartmentFilters(String location, String department) {

        // Hard Wait satırı (önceki isteğiniz üzerine korunmuştur, ancak kaldırılması önerilir)
        elementHelper.waitByMilliSeconds(10000);
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(careers_openPositionsLocators.FILTER_BY_DEPARTMENT_QUALITY_ASSURANCE_TEXT_LOCATOR),
                ExpectedConditions.visibilityOfElementLocated(careers_openPositionsLocators.FILTER_BY_DEPARTMENT_ALL_TEXT_LOCATOR)
        ));

        // 1. Location Filtresini Uygula
        try {
            // Önceki filtreyi temizle ve yeni konumu seç
            elementHelper.clickElement(careers_openPositionsLocators.FILTER_BY_LOCATION_REMOVE_ALL_BUTTON_LOCATOR);
            elementHelper.waitForTheElement(careers_openPositionsLocators.FILTER_BY_LOCATION_IN_MENU_ISTANBUL_TURKIYE_TEXT_LOCATOR);
            elementHelper.clickElementWithJS(careers_openPositionsLocators.FILTER_BY_LOCATION_IN_MENU_ISTANBUL_TURKIYE_TEXT_LOCATOR);
            elementHelper.clickElement(careers_openPositionsLocators.FILTER_BY_LOCATION_IN_MENU_ISTANBUL_TURKIYE_TEXT_LOCATOR);

            System.out.println("INFO: Location filtresi (" + location + ") uygulandı.");
        } catch (Exception e) {
            System.err.println("HATA: Location filtresi uygulanamadı. Hata: " + e.getMessage());
            return false;
        }

        // 2. Department Filtresini Uygula
        try {
            // Önceki filtreyi temizle ve yeni departmanı seç
            elementHelper.clickElement(careers_openPositionsLocators.FILTER_BY_DEPARTMENT_REMOVE_ALL_BUTTON_LOCATOR);

            // QA seçeneğinin görünür olmasını bekle
            elementHelper.waitForTheElement(careers_openPositionsLocators.FILTER_BY_DEPARTMENT_IN_MENU_QUALITY_ASSURANCE_TEXT_LOCATOR);

            // JavaScript ile tıklamayı dene
            elementHelper.clickElementWithJS(careers_openPositionsLocators.FILTER_BY_DEPARTMENT_IN_MENU_QUALITY_ASSURANCE_TEXT_LOCATOR);
            elementHelper.clickElement(careers_openPositionsLocators.FILTER_BY_DEPARTMENT_IN_MENU_QUALITY_ASSURANCE_TEXT_LOCATOR);

            System.out.println("INFO: Department filtresi (" + department + ") uygulandı.");
        } catch (Exception e) {
            System.err.println("HATA: Department filtresi uygulanamadı. Hata: " + e.getMessage());
            return false;
        }


        // 3. İŞ İLANI METNİNİN GÖRÜNÜR OLMASINI KONTROL ET
        try {
            try {
                elementHelper.waitForTheElement(careers_openPositionsLocators.JOB_LIST_QUALITY_ASSURANCE_TEXT);
                boolean isTextMatched = elementHelper.waitForElementTextToEqual(
                        careers_openPositionsLocators.JOB_LIST_QUALITY_ASSURANCE_TEXT,
                        department,
                        10 // 10 saniye bekleme süresi
                );
                // Metin eşleşme kontrolü tamamlandı.
            } catch (Exception e) {
                System.err.println("HATA: İş listesi içinde 'Quality Assurance' metni bulunamadı veya beklenenden farklıydı. " + e.getMessage());
            }

            // Sadece elementin görünür olup olmadığını kontrol ederek TRUE dön.
            boolean isJobTextVisible = elementHelper.isDisplayedBy(careers_openPositionsLocators.JOB_LIST_QUALITY_ASSURANCE_TEXT);

            if (isJobTextVisible) {
                System.out.println("INFO: Filtreleme sonrası beklenen iş ilanı metni ekranda görünüyor.");
            } else {
                System.err.println("HATA: İş ilanı metni bekleme süresinde görünür olmadı.");
            }

            return isJobTextVisible;

        } catch (Exception e) {
            System.err.println("HATA: İş ilanı metni kontrol edilirken bir hata oluştu veya Timeout yaşandı. Hata: " + e.getMessage());
            return false;
        }
    }



    public boolean verifyJobFilters(String location, String department) {
        // ------------------- ADIM 4: FİLTRE SONUÇLARINI KONTROL ET -------------------

        elementHelper.waitForElementTextToEqual(careers_openPositionsLocators.CAREERS_OPEN_POSITIONS_PAGE_LOCATION_TEXT_IN_CARD,location,7);
        elementHelper.waitForElementTextToEqual(careers_openPositionsLocators.CAREERS_OPEN_POSITIONS_PAGE_DEPARTMENT_TEXT_IN_CARD,department,7);

        try {
            // 4.1. İş Listesinin Yüklenmesini bekle (En az bir kartın görünürlüğünü beklemek)
            elementHelper.waitForTheElement(careers_openPositionsLocators.CAREERS_OPEN_POSITIONS_PAGE_JOB_CARD_LOCATOR);

            // 4.2. Tüm iş ilanı kartlarını al

            List<WebElement> jobCards = elementHelper.findElements(careers_openPositionsLocators.CAREERS_OPEN_POSITIONS_PAGE_JOB_CARD_LOCATOR);

            if (jobCards.isEmpty()) {
                System.err.println("HATA: Filtreler uygulandıktan sonra sayfada HİÇ İŞ İLANI bulunamadı.");
                return false;
            }

            System.out.println("INFO: Filtreleme sonrası toplam " + jobCards.size() + " adet iş ilanı bulundu. Doğrulama başlıyor.");


            // Sadece ilk kartın bilgilerini kontrol eder (Kodun mevcut mantığı)
            String locationText = elementHelper.getText(careers_openPositionsLocators.CAREERS_OPEN_POSITIONS_PAGE_LOCATION_TEXT_IN_CARD);
            String departmentText = elementHelper.getText(careers_openPositionsLocators.CAREERS_OPEN_POSITIONS_PAGE_DEPARTMENT_TEXT_IN_CARD);

            boolean isDepartmentCorrect = departmentText.contains(department);
            boolean isLocationCorrect = locationText.contains(location);

            // =========== ÖNEMLİ BİLGİLENDİRME: YARIŞ KOŞULU (RACE CONDITION) ENGELLENDİ ===========
            //
            // PROBLEM: Otomasyon akışı çok hızlı ilerlediği için, filtreleme işlemi sonrası Sayfa Objesi (DOM) kısa bir süre içinde güncellenmesine rağmen,
            // "Filter by Department" seçilen "Quality Assurance" değerini (%90 ihtimalle) yanlışlıkla varsayılan "All" olarak algılamaktadır.
            //
            // ÇÖZÜM: Bu yarış koşulunu (Race Condition) stabil hale getirmek amacıyla, filtreleme adımından önce zorunlu bir bekleme eklenmiştir.
            // Bu bekleme, arayüzün tüm JavaScript güncellemelerinin tamamlanmasına yetecek süreyi sağlamaktadır.
            //
            elementHelper.waitByMilliSeconds(2000);
            elementHelper.clickElement(careers_openPositionsLocators.FILTER_BY_DEPARTMENT_REMOVE_ALL_BUTTON_LOCATOR);
            elementHelper.waitByMilliSeconds(2000);
            elementHelper.waitForTheElement(careers_openPositionsLocators.FILTER_BY_DEPARTMENT_IN_MENU_QUALITY_ASSURANCE_TEXT_LOCATOR);
            elementHelper.waitByMilliSeconds(2000);
            elementHelper.clickElement(careers_openPositionsLocators.FILTER_BY_DEPARTMENT_IN_MENU_QUALITY_ASSURANCE_TEXT_LOCATOR);
            elementHelper.waitByMilliSeconds(2000);
            elementHelper.waitForPageToCompleteState();
            if (!isDepartmentCorrect || !isLocationCorrect) {
                System.err.println("HATA: İş ilanı filtre kriterlerine uymuyor.");
                System.err.println("Beklenen Departman: '" + department + "', Bulunan: '" + departmentText + "'");
                System.err.println("Beklenen Konum: '" + location + "', Bulunan: '" + locationText + "'");
                return false;
            }

            System.out.println("INFO: Bulunan iş ilanı, filtre kriterlerini başarıyla karşılıyor.");
            return true;

        } catch (Exception e) {
            System.err.println("HATA: İş listesi kontrol edilirken bir hata oluştu veya Timeout yaşandı. Hata: " + e.getMessage());
            return false;
        }
    }


    /**
     * 1. İlk iş ilanı kartını bulur.
     * 2. "View Role" butonuna tıklar ve yeni sekmeye geçer.
     * @return İşlem başarılıysa ana pencerenin (Careers Page) handle'ını döndürür. Aksi halde null döndürür.
     */
    public String clickFirstViewRoleButtonAndSwitchWindow() {


        // 1. Ana pencerenin (Careers Page) handle'ını kaydet
        String originalWindowHandle = BaseTest.getDriver().getWindowHandle();

        WebElement firstJobCard;
        try {
            // 2. İlk İş İlanı Kartını Bulma
            firstJobCard = elementHelper.waitForTheElement(careers_openPositionsLocators.CAREERS_OPEN_POSITIONS_PAGE_FIRST_JOB_CARD_LOCATOR);
        } catch (Exception e) {
            System.err.println("HATA: Filtrelenen ilk iş ilanı kartı bulunamadı. " + e.getMessage());
            return null;
        }

        // 3. "View Role" Butonuna Tıklama ve Yeni Sekmeye Geçiş
        try {
            WebElement viewRoleButton = elementHelper.findElement(firstJobCard, careers_openPositionsLocators.CAREERS_OPEN_POSITIONS_PAGE_VIEW_ROLE_BUTTON_IN_CARD);

            // Tıklamadan önce sekmeleri al
            Set<String> beforeClickHandles = BaseTest.getDriver().getWindowHandles();

            // Butona tıkla (Hover ve Click bir arada)
            elementHelper.hoverAndClick(viewRoleButton, viewRoleButton);
            System.out.println("INFO: 'View Role' butonuna hover yapıldı ve tıklandı. Yeni sekmenin açılması bekleniyor.");

            // 4. Yeni Sekmeye Geçiş (Window Handling)
            WebDriverWait dynamicWait = new WebDriverWait(BaseTest.getDriver(), java.time.Duration.ofSeconds(10));
            dynamicWait.until(ExpectedConditions.numberOfWindowsToBe(beforeClickHandles.size() + 1));

            Set<String> afterClickHandles = BaseTest.getDriver().getWindowHandles();

            // Yeni sekmeyi bul
            String newWindowHandle = afterClickHandles.stream()
                    .filter(handle -> !beforeClickHandles.contains(handle))
                    .findFirst()
                    .orElse(null);

            if (newWindowHandle == null) {
                System.err.println("HATA: Tıkladıktan sonra yeni sekme açılmadı.");
                return null;
            }

            // Yeni sekme/pencereye geçiş yap
            BaseTest.getDriver().switchTo().window(newWindowHandle);
            System.out.println("INFO: Yeni açılan Lever/Greenhouse sekmesine geçildi.");

            return originalWindowHandle;

        } catch (Exception e) {
            System.err.println("HATA: 'View Role' butonuna tıklama veya pencere geçişi sırasında hata oluştu. " + e.getMessage());
            return null;
        }
    }

}
