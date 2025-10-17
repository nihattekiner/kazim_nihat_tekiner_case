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

import static constants.Constants.DEFAULT_MAX_ITERATION_COUNT;
import static constants.Constants.DEFAULT_MILLISECOND_WAIT_AMOUNT;

public class ElementHelper {

    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
    private static final ThreadLocal<Actions> actionsThread = new ThreadLocal<>();


    public static WebDriver getDriver() {
        return driverThread.get();
    }

    public Actions getActions() {
        return actionsThread.get();
    }

    public void setUp(String browserName) {
        if (driverThread.get() == null) {
            WebDriver driver = Drivers.getDriverType(browserName).getDriver();
            driverThread.set(driver);
            actionsThread.set(new Actions(driver));

            // Pencereyi tam ekran yap
            driver.manage().window().maximize();

            // ÖNEMLİ: Implicit Wait (Örtülü Bekleme) ekleyin.
            // Bu, findElement metotlarınızda TimeoutException almadan önce kısa bir bekleme sağlar.
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Sayfa yükleme zaman aşımı (300 saniye çok uzun, 60 saniye yeterli)
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        }
    }

    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
            driverThread.remove();
            actionsThread.remove();
        }
    }

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
        while (counter < maxNoOfRetries) {
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
            try {
                JavascriptExecutor js = (JavascriptExecutor) getDriver();
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

    public boolean isDisplayedBy(By by) {
        try {
            WebDriverWait webDriverWait = new WebDriverWait(BaseTest.getDriver(), Duration.ofSeconds(10));
            WebElement element = webDriverWait.until(ExpectedConditions.presenceOfElementLocated(by));
            webDriverWait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void waitForTheElement(By by) {
        WebDriverWait longWait = new WebDriverWait(getDriver(), Duration.ofSeconds(60));
        longWait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public WebElement findElement(By infoParam) {
        WebDriverWait webDriverWait = new WebDriverWait(BaseTest.getDriver(), Duration.ofSeconds(10));
        WebElement webElement = webDriverWait.until(ExpectedConditions.presenceOfElementLocated(infoParam));
        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'})",
                webElement);
        return webElement;
    }

    public void clickElement(WebElement element) {
        try {
            element.click();
        } catch (ElementClickInterceptedException exception) {
            javaScriptClicker(element);
        }
    }

    public void clickElement(By by) {
        try {
            clickElement(findElement(by));
        } catch (ElementClickInterceptedException exception) {
            scrollByJs(by);
            clickElement(findElement(by));
        }
    }

    public void javaScriptClicker(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", element);
    }

    public boolean shouldSee(String text) {
        WebDriverWait webDriverWait = new WebDriverWait(BaseTest.getDriver(), Duration.ofSeconds(10));
        return webDriverWait.until(ExpectedConditions.urlContains(text));
    }

    public void selectTextFromDropDown(String text, By by) {
        if (!isDisplayedBy(by)) {
            waitForTheElement(by);
        }
        Select select = new Select(findElement(by));
        select.selectByVisibleText(text);
    }

    public void switchNewWindow() {
        for (String winHandle : getDriver().getWindowHandles()) {
            getDriver().switchTo().window(winHandle);
        }
    }

}
