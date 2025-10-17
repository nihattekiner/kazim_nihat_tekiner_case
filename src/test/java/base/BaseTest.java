package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import org.testng.ITestResult;
import utils.ElementHelper;
import utils.ExtentManager;
import utils.ReadProperties;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.ResourceBundle;

public class BaseTest {
    private ElementHelper elementHelper;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public static ResourceBundle FilterParam;
    public static ResourceBundle Config;

    protected static ExtentReports extent;
    protected ExtentTest test;

    @Parameters("browser")
    @BeforeClass(alwaysRun = true)
    public void setupDriver(@Optional("chrome") String driverType) {
        readProperties();
        elementHelper = new ElementHelper();
        elementHelper.setUp(driverType);
    }

    static {
        Locale.setDefault(new Locale("en", "US"));
    }

    @BeforeSuite(alwaysRun = true)
    public void setUpExtent() throws IOException {
        if (extent == null) {
            extent = ExtentManager.getInstance();
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void startTest(Method method) {
        // Her @Test metodu başlamadan önce 'test' nesnesini oluştur.
        test = extent.createTest(method.getName());
    }

    //Test sonuçlarını kaydetmek için
    @AfterMethod(alwaysRun = true)
    public void logTestStatus(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            // Test başarısız olursa, hatayı (exception) rapora kaydeder.
            test.fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.skip(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test passed successfully.");
        }
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownExtent() {
        if (extent != null) {
            extent.flush();
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (getDriver() != null) {
            logger.info("Driver will be closed");
            elementHelper.tearDown();
        }
    }

    public static void readProperties() {
        FilterParam = ReadProperties.readProp("FilterParam.properties");
        Config = ReadProperties.readProp("Config.properties");
    }

    public static WebDriver getDriver() {
        return ElementHelper.getDriver();
    }

}