package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum Drivers {
    CHROME {
        @Override
        public WebDriver getDriver() {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
            chromeOptions.addArguments(
                    "--lang=tr",
                    "--disable-popup-blocking",
                    "--disable-blink-features=AutomationControlled",
                    "--disable-gpu",
                    "--no-sandbox",
                    "--disable-infobars",
                    "--ignore-certificate-errors",
                    "--disable-translate",
                    "--disable-extensions",
                    "--disable-notifications",
                    "--remote-allow-origins=*"
            );

            Map<String, Object> prefs = new HashMap<>();
            prefs.put("credentials_enable_service", false);
            prefs.put("password_manager_enabled", false);
            chromeOptions.setExperimentalOption("prefs", prefs);

            return new ChromeDriver(chromeOptions);
        }
    },

    FIREFOX {
        @Override
        public WebDriver getDriver() {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.addArguments(
                    "--lang=tr",
                    "--disable-popup-blocking",
                    "--disable-gpu",
                    "--no-sandbox",
                    "--disable-infobars",
                    "--ignore-certificate-errors",
                    "--disable-notifications"
            );
            return new FirefoxDriver(firefoxOptions);
        }
    };

    public abstract WebDriver getDriver();

    public static Drivers getDriverType(String driverName) {
        for (Drivers driver : Drivers.values()) {
            if (driver.toString().equalsIgnoreCase(driverName)) {
                return driver;
            }
        }
        throw new IllegalArgumentException("Invalid driver type provided: " + driverName);
    }
}
