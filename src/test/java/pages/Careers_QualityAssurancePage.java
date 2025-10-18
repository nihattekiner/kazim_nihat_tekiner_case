package pages;

import base.BaseTest;
import locators.Careers_QualityAssuranceLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ElementHelper;

public class Careers_QualityAssurancePage {
    private ElementHelper elementHelper;
    private WebDriverWait wait;
    Careers_QualityAssuranceLocators careersQualityAssuranceLocators = new Careers_QualityAssuranceLocators();

    // Element Helper'ı BaseTest'ten gelen driver ile başlatmak için constructor
    public Careers_QualityAssurancePage(ElementHelper elementHelper) {
        this.elementHelper = elementHelper;
        // WebDriverWait'i başlat
        this.wait = new WebDriverWait(BaseTest.getDriver(), java.time.Duration.ofSeconds(15));
    }


    /**
     * "See all QA jobs" butonuna tıklar ve iş listesi sayfasına geçişi sağlar.
     * @return İşlem başarılıysa true, aksi halde false döner.
     */
    public boolean clickSeeAllJobsButtonInCareersQaPage() {
        try {
            WebElement seeAllJobsButton = wait.until(
                    ExpectedConditions.elementToBeClickable(careersQualityAssuranceLocators.QA_PAGE_SEE_ALL_JOBS_BUTTON_LOCATOR)
            );
            elementHelper.clickElement(careersQualityAssuranceLocators.QA_PAGE_SEE_ALL_JOBS_BUTTON_LOCATOR);
            System.out.println("INFO: 'See All QA jobs' butonuna tıklandı ve iş listesi sayfası yükleniyor.");
            return true;
        } catch (Exception e) {
            System.err.println("HATA: 'See All QA jobs' butonu bulunamadı veya tıklanamadı. Sayfa geç yüklenmiş olabilir. Hata: " + e.getMessage());
            return false;
        }
    }

}
