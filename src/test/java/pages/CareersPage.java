package pages;

import base.BaseTest;
import locators.CareersLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ElementHelper;
import static base.BaseTest.Config;
import java.util.List;
import java.util.Set;

public class CareersPage {
    private ElementHelper elementHelper;
    private WebDriverWait wait;
    CareersLocators careersLocators = new CareersLocators();

    // Element Helper'ı BaseTest'ten gelen driver ile başlatmak için constructor
    public CareersPage(ElementHelper elementHelper) {
        this.elementHelper = elementHelper;
        // WebDriverWait'i başlat
        this.wait = new WebDriverWait(BaseTest.getDriver(), java.time.Duration.ofSeconds(15));
    }

    public boolean checkCareerPageBlocksAreOpen() {
        boolean isOurLocationsTextVisible = elementHelper.isDisplayedBy(careersLocators.CAREERS_PAGE_OUR_LOCATIONS_TEXT_LOCATOR);
        System.out.println("INFO: 'Our Locations' metni görünüyor mu? " + isOurLocationsTextVisible);

        boolean isAllTeamsTextVisible = elementHelper.isDisplayedBy(careersLocators.CAREERS_PAGE_SEE_ALL_TEAMS_TEXT_AND_BUTTON_LOCATOR);
        System.out.println("INFO: 'See All Teams' butonu görünüyor mu? " + isAllTeamsTextVisible);

        elementHelper.clickElement(careersLocators.CAREERS_PAGE_SEE_ALL_TEAMS_TEXT_AND_BUTTON_LOCATOR);
        wait.until(ExpectedConditions.visibilityOfElementLocated(careersLocators.CAREERS_PAGE_SEE_ALL_TEAMS_QA_TEXT_LOCATOR));

        boolean isQaInAllTeamsVisible = elementHelper.isDisplayedBy(careersLocators.CAREERS_PAGE_SEE_ALL_TEAMS_QA_TEXT_LOCATOR);
        System.out.println("INFO: 'Quality Assurance' Ekibi metni görünüyor mu? " + isQaInAllTeamsVisible);

        boolean isLifeAtInsiderTextVisible = elementHelper.isDisplayedBy(careersLocators.CAREERS_PAGE_LIFE_AT_INSIDER_TEXT_LOCATOR);
        System.out.println("INFO: 'Life at Insider' metni görünüyor mu? " + isLifeAtInsiderTextVisible);

        return isOurLocationsTextVisible && isAllTeamsTextVisible && isQaInAllTeamsVisible && isLifeAtInsiderTextVisible;
    }



    private final By LOCATION_DROPDOWN_REMOVE_ALL_BUTTON_LOCATOR = By.xpath("(//span[@title='Remove all items'])[1]");
    private final By DEPARTMENT_DROPDOWN_REMOVE_ALL_BUTTON_LOCATOR = By.xpath("(//span[@title='Remove all items'])[2]");
    private final By DEPARTMENT_DROPDOWN_QUALITY_ASSURANCE_TEXT_LOCATOR = By.xpath("//span[@title='Quality Assurance']");
    private final By DEPARTMENT_DROPDOWN_ALL_TEXT_LOCATOR = By.xpath("//span[@title='All']");
    private final By JOB_LIST_QUALITY_ASSURANCE_TEXT = By.xpath("//span[@class='position-department text-large font-weight-600 text-primary']");
    private final By dropdownIstText = By.xpath("//li[text()='Istanbul, Turkiye']");
    private final By dropdownQAText = By.xpath("//li[text()='Quality Assurance']");




}