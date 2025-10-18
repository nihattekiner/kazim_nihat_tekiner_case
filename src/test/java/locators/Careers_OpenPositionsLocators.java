package locators;

import org.openqa.selenium.By;

public class Careers_OpenPositionsLocators {

    public final By FILTER_BY_LOCATION_REMOVE_ALL_BUTTON_LOCATOR = By.xpath("(//span[@title='Remove all items'])[1]");
    public final By FILTER_BY_DEPARTMENT_REMOVE_ALL_BUTTON_LOCATOR = By.xpath("(//span[@title='Remove all items'])[2]");
    public final By FILTER_BY_DEPARTMENT_QUALITY_ASSURANCE_TEXT_LOCATOR = By.xpath("//span[@title='Quality Assurance']");
    public final By FILTER_BY_DEPARTMENT_ALL_TEXT_LOCATOR = By.xpath("//span[@title='All']");
    public final By JOB_LIST_QUALITY_ASSURANCE_TEXT = By.xpath("//span[@class='position-department text-large font-weight-600 text-primary']");
    public final By FILTER_BY_LOCATION_IN_MENU_ISTANBUL_TURKIYE_TEXT_LOCATOR = By.xpath("//li[text()='Istanbul, Turkiye']");
    public final By FILTER_BY_DEPARTMENT_IN_MENU_QUALITY_ASSURANCE_TEXT_LOCATOR = By.xpath("//li[text()='Quality Assurance']");
    public final By CAREERS_OPEN_POSITIONS_PAGE_JOB_CARD_LOCATOR = By.xpath("//div[contains(@id, 'jobs-list')]"); // Tüm iş listesi konteyneri
    public final By CAREERS_OPEN_POSITIONS_PAGE_DEPARTMENT_TEXT_IN_CARD = By.xpath(".//span[contains(@class, 'position-department')]");
    public final By CAREERS_OPEN_POSITIONS_PAGE_LOCATION_TEXT_IN_CARD = By.xpath(".//div[@class='position-location text-large']");
    public final By CAREERS_OPEN_POSITIONS_PAGE_VIEW_ROLE_BUTTON_IN_CARD = By.xpath("//a[@class='btn btn-navy rounded pt-2 pr-5 pb-2 pl-5']");
    public final By CAREERS_OPEN_POSITIONS_PAGE_FIRST_JOB_CARD_LOCATOR = By.xpath("//div[@class='position-list-item col-12 col-lg-4 qualityassurance istanbul-turkiye uncategorized']");

}
