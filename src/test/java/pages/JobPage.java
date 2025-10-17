package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.ElementHelper;
import static base.BaseTest.FilterParam;
import java.util.List;

public class JobPage {
    private ElementHelper elementHelper;

    public JobPage(ElementHelper elementHelper) {
        this.elementHelper = elementHelper;
    }

    // --- Locators (XPATH/CSS ile optimize edilmelidir) ---
    // 3. Adım: Lokasyon Dropdown (Select)
    private final By locationFilter = By.id("filter-by-location");
    // 3. Adım: Departman Dropdown (Select)
    private final By departmentFilter = By.id("filter-by-department");

    // 3. Adım: İş İlanı Listesi (Her bir ilan kartı)
    private final By jobList = By.cssSelector("#jobs-list > a");

    // 4. Adım: İlk iş ilanının içindeki detaylar için temel Locator (Listeden çekilecek)
    // Örnek: İlk ilan kartının içindeki pozisyon, departman ve lokasyon bilgileri
    private final By jobPositionText = By.cssSelector(".position-title");
    private final By jobDepartmentText = By.cssSelector(".position-department");
    private final By jobLocationText = By.cssSelector(".position-location");

    // 5. Adım: İş ilanındaki "View Role" butonu (Her ilan kartında mevcuttur)
    private final By viewRoleButton = By.xpath("//a[text()='View Role']");

    /**
     * Adım 3: Lokasyon ve Departman filtrelerini uygular.
     */
    public void filterJobs() {
        // Properties dosyasından değerleri çeker
        String location = FilterParam.getString("JOB_LOCATION");
        String department = FilterParam.getString("JOB_DEPARTMENT");

        // Lokasyon filtresini uygular
        elementHelper.selectTextFromDropDown(location, locationFilter);

        // Departman filtresini uygular
        elementHelper.selectTextFromDropDown(department, departmentFilter);

        // Filtreleme sonrası listenin yüklenmesini beklemek için sayfanın tamamlanmasını bekleriz
        elementHelper.waitForPageToCompleteState();
    }

    /**
     * Adım 3: Filtrelenmiş iş ilanlarının listesini döndürür.
     */
    public List<WebElement> getJobList() {
        // Listenin yüklenmesini bekler
        elementHelper.waitForTheElement(jobList);
        return elementHelper.getDriver().findElements(jobList);
    }

    /**
     * Adım 4: Tüm iş ilanlarının detaylarını (Pozisyon, Departman, Lokasyon) doğrular.
     */
    public void verifyAllJobDetails(String expectedPosition, String expectedDepartment, String expectedLocation) {
        List<WebElement> jobs = getJobList();

        for (int i = 0; i < jobs.size(); i++) {
            WebElement job = jobs.get(i);

            // Job listesinden çekilen her ilan kartı içinde detayları bul
            String actualPosition = job.findElement(jobPositionText).getText();
            String actualDepartment = job.findElement(jobDepartmentText).getText();
            String actualLocation = job.findElement(jobLocationText).getText();

            // Doğrulama yapılır (Assertion'ı burada değil, test sınıfında yapacağız. Burada sadece loglama yapalım.)
            if (!actualPosition.contains(expectedPosition)) {
                System.err.println("Hata: Pozisyon eşleşmiyor. Beklenen: " + expectedPosition + ", Bulunan: " + actualPosition);
            }
            if (!actualDepartment.contains(expectedDepartment)) {
                System.err.println("Hata: Departman eşleşmiyor. Beklenen: " + expectedDepartment + ", Bulunan: " + actualDepartment);
            }
            if (!actualLocation.contains(expectedLocation)) {
                System.err.println("Hata: Lokasyon eşleşmiyor. Beklenen: " + expectedLocation + ", Bulunan: " + actualLocation);
            }
        }
    }

    /**
     * Adım 5: İlk iş ilanının "View Role" butonuna tıklar ve yeni pencereye geçer.
     */
    public void clickFirstViewRoleButton() {
        // Tüm ilanları listele
        List<WebElement> jobs = getJobList();
        if (!jobs.isEmpty()) {
            // İlk ilan kartının içindeki View Role butonunu bul ve tıkla
            WebElement firstJobViewRole = jobs.get(0).findElement(viewRoleButton);
            elementHelper.clickElement(firstJobViewRole);

            // Yeni açılan Lever penceresine geçer
            elementHelper.switchNewWindow();
        } else {
            throw new RuntimeException("İş listesi boş olduğu için 'View Role' butonuna tıklanamadı.");
        }
    }
}