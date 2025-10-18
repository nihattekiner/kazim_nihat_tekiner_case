package locators;

import org.openqa.selenium.By;

public class HomeLocators {

    public final By COOKIES_ACCEPT_ALL_LOCATOR = By.xpath("//a[@data-cli_action='accept_all']");
    public final By HOME_PAGE_COMPANY_MENU_LOCATOR = By.xpath("//a[text()='Company' or contains(.,'Company')]");
    public final By HOME_PAGE_COMPANY_MENU_CAREERS_LINK_LOCATOR = By.xpath("//a[text()='Careers' and @href='https://useinsider.com/careers/']");
}
