package pages;

import org.openqa.selenium.By;
import utils.ElementHelper;
import static org.junit.jupiter.api.Assertions.assertTrue; // Assertion'ı Page'e taşımak yerine test sınıfında kullanacağız.

public class CareersPage {
    private ElementHelper elementHelper;

    public CareersPage(ElementHelper elementHelper) {
        this.elementHelper = elementHelper;
    }

    // --- Locators ---
    // 2. Adım: Locations, Teams ve Life at Insider bloklarının başlıkları
    private final By locationsBlock = By.xpath("//h3[text()='Our Locations']");
    private final By teamsBlock = By.xpath("//h3[text()='Our Teams']");
    private final By lifeAtInsiderBlock = By.xpath("//h3[text()='Life at Insider']");

    // 3. Adım: "See all QA jobs" butonu
    private final By seeAllQaJobsButton = By.xpath("//a[contains(@href, 'quality-assurance') and text()='See all QA jobs']");

    /**
     * Adım 2: Kariyer sayfasındaki gerekli blokların görünürlüğünü kontrol eder.
     * @return boolean Tüm blokların görünür olup olmadığını gösterir.
     */
    public boolean verifyPageSectionsAreVisible() {
        // Tüm elementlerin görüntülenip görüntülenmediğini kontrol eder
        boolean locVisible = elementHelper.isDisplayedBy(locationsBlock);
        boolean teamsVisible = elementHelper.isDisplayedBy(teamsBlock);
        boolean lifeVisible = elementHelper.isDisplayedBy(lifeAtInsiderBlock);

        return locVisible && teamsVisible && lifeVisible;
    }

    /**
     * Adım 3: "See all QA jobs" butonuna tıklar ve JobPage'e gider.
     */
    public void goToQAJobs() {
        // Butona tıkla
        elementHelper.clickElement(seeAllQaJobsButton);
    }
}