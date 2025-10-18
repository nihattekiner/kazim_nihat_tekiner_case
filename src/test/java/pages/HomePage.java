package pages;

import base.BaseTest;
import locators.HomeLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ElementHelper;
import java.util.List;
import static base.BaseTest.Config;
import static org.testng.Assert.fail;

public class HomePage {
    private ElementHelper elementHelper;
    private WebDriverWait wait;
    private final By SELECT2_SEARCH_INPUT = By.xpath("//input[@class='select2-search__field']");
    HomeLocators homeLocators = new HomeLocators();

    // Element Helper'ı BaseTest'ten gelen driver ile başlatmak için constructor
    public HomePage(ElementHelper elementHelper) {
        this.elementHelper = elementHelper;
        // WebDriverWait'i başlat
        this.wait = new WebDriverWait(BaseTest.getDriver(), java.time.Duration.ofSeconds(15));
    }

    // =========================================================================
    //                            1. MADDE
    // =========================================================================
    public boolean isHomePageOpened() {
        try {
            // Cookie pop-up'ını bekleyip tıkla
            wait.until(ExpectedConditions.elementToBeClickable(homeLocators.COOKIES_ACCEPT_ALL_LOCATOR));
            elementHelper.clickElement(homeLocators.COOKIES_ACCEPT_ALL_LOCATOR);
        } catch (Exception e) {
            System.out.println("INFO: Cookie pop-up'ı görünmedi veya zaten kapatılmış.");
        }
        return elementHelper.shouldSee(Config.getString("BASE_URL")); // Base URL kontrolü
    }

    // =========================================================================
    //                            2. MADDE
    // =========================================================================

    public boolean selectCompanySelectCarersAndCheckCareerPage() {
        WebElement companyDropDownMenu = elementHelper.findElement(homeLocators.HOME_PAGE_COMPANY_MENU_LOCATOR);
        WebElement companyDropDownMenu_Careers = elementHelper.findElement(homeLocators.HOME_PAGE_COMPANY_MENU_CAREERS_LINK_LOCATOR);
        elementHelper.hoverAndClick(companyDropDownMenu, companyDropDownMenu_Careers);
        String expectedUrlPart = Config.getString("CAREERS_URL");
        return elementHelper.shouldSee(expectedUrlPart);
    }


}