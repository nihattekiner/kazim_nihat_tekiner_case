package pages;

import base.BaseTest;
import base.Drivers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ElementHelper;
import static base.BaseTest.Config;
import static base.BaseTest.FilterParam;

public class HomePage {
    private ElementHelper elementHelper;

    // Element Helper'ı BaseTest'ten gelen driver ile başlatmak için constructor
    public HomePage(ElementHelper elementHelper) {
        this.elementHelper = elementHelper;
    }
    // ---------LOCATOR-----
    private final By COOKIES_ACCEPT_ALL_LOCATOR = By.xpath("//a[@data-cli_action='accept_all']");    // ---------METHOD-----
    public boolean isHomePageOpened() {
        try {
        WebDriverWait wait = new WebDriverWait(BaseTest.getDriver(), java.time.Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(COOKIES_ACCEPT_ALL_LOCATOR));
        elementHelper.clickElement(COOKIES_ACCEPT_ALL_LOCATOR);
        } catch (Exception e) {
            System.out.println("INFO: Cookie pop-up'ı görünmedi veya zaten kapatılmış.");
        }
        return elementHelper.shouldSee("https://useinsider.com/");}

    // ---------LOCATOR-----
    private final By HOME_PAGE_COMPANY_MENU_LOCATOR = By.xpath("//a[text()='Company' or contains(.,'Company')]"); // COMPANY Menü Linki
    private final By HOME_PAGE_COMPANY_MENU_CAREERS_LINK_LOCATOR = By.xpath("//a[text()='Careers' and @href='https://useinsider.com/careers/']"); // Alt Menü Linki (Careers)

    // ---------METHOD-----
    public boolean selectCompanySelectCarersAndCheckCareerPage() {
        WebElement companyDropDownMenu = elementHelper.findElement(HOME_PAGE_COMPANY_MENU_LOCATOR); // Menü Elementleri
        WebElement companyDropDownMenu_Careers = elementHelper.findElement(HOME_PAGE_COMPANY_MENU_CAREERS_LINK_LOCATOR); // Menü Elementleri
        elementHelper.hoverAndClick(companyDropDownMenu, companyDropDownMenu_Careers);
        String expectedUrlPart = Config.getString("CAREERS_URL");
        return elementHelper.shouldSee(expectedUrlPart);}

    // ---------LOCATOR-----
    private final By CAREERS_PAGE_OUR_LOCATIONS_TEXT_LOCATOR = By.xpath("//h3[contains(text(), 'Our Locations')]");
    private final By CAREERS_PAGE_SEE_ALL_TEAMS_TEXT_LOCATOR = By.xpath("//a[contains(text(), 'See all teams')]");
    private final By CAREERS_PAGE_SEE_ALL_TEAMS_QA_TEXT_LOCATOR = By.xpath("//h3[contains(text(), 'Quality Assurance')]");
    private final By CAREERS_PAGE_LIFE_AT_INSIDER_TEXT_LOCATOR = By.xpath("//h2[contains(text(), 'Life at Insider')]");

    // ---------METHOD-----
    public boolean checkCareerPageBlocksAreOpen() {

        // 1. Our Locations yazisinin görünürlüğünü kontrol et
        boolean isOurLocationsTextVisible = elementHelper.isDisplayedBy(CAREERS_PAGE_OUR_LOCATIONS_TEXT_LOCATOR);
        System.out.println("Our Locations Text Visible: " + isOurLocationsTextVisible);

        // 2. See All Teams yazisinin görünürlüğünü kontrol et
        boolean isAllTeamsTextVisible = elementHelper.isDisplayedBy(CAREERS_PAGE_SEE_ALL_TEAMS_TEXT_LOCATOR);
        System.out.println("See All Teams Text Visible: " + isAllTeamsTextVisible);
        elementHelper.clickElement(CAREERS_PAGE_SEE_ALL_TEAMS_TEXT_LOCATOR);
        elementHelper.waitForTheElement(CAREERS_PAGE_SEE_ALL_TEAMS_QA_TEXT_LOCATOR);
        boolean isQaInAllTeamsVisible = elementHelper.isDisplayedBy(CAREERS_PAGE_SEE_ALL_TEAMS_QA_TEXT_LOCATOR);
        System.out.println("Quality Assurance Text in All Teams Visible: " + isQaInAllTeamsVisible);

        // 3. Life at Insider yazisinin görünürlüğünü kontrol et
        boolean isLifeAtInsiderTextVisible = elementHelper.isDisplayedBy(CAREERS_PAGE_LIFE_AT_INSIDER_TEXT_LOCATOR);
        System.out.println("Life at Insider Text Visible: " + isLifeAtInsiderTextVisible);

        // Üç bloğun da görünür olması gerekiyor
        return isOurLocationsTextVisible && isAllTeamsTextVisible && isQaInAllTeamsVisible && isLifeAtInsiderTextVisible ;
    }

    // ---------LOCATOR-----

    // ---------METHOD-----

    /*

    // --- Locators (XPATH/CSS ile optimize edilmelidir) ---
    // 2. Adım: Company menüsü (Header'da yer alan)
    private final By companyMenu = By.xpath("//a[normalize-space()='Company']");
    // 2. Adım: Careers alt menüsü
    private final By careersOption = By.xpath("//a[normalize-space()='Careers']");


     //* Adım 1: Anasayfanın URL'ini veya başlığını kontrol eder.
     //* @return boolean Anasayfanın yüklü olup olmadığını gösterir.

    public boolean isHomePageOpened() {
        // BASE_URL'i Constants.java'dan veya Config.properties'ten çekebilirsiniz.
        // Şimdilik sadece URL kontrolü yapalım.
        return elementHelper.shouldSee("useinsider.com");
    }


     //* Adım 2: "Company" menüsü üzerine gelip/tıklayıp "Careers" sayfasına gider.

    public void goToCareersPage() {
        // ElementHelper içinde hoverAndClick metodunuz olduğu için onu kullanalım.
        // Ancak Company menüsü tek tıklamayla da çalışabilir.

        elementHelper.clickElement(companyMenu);
        elementHelper.clickElement(careersOption);
    }
    */
}