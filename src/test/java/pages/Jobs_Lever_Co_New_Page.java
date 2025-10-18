package pages;

import base.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ElementHelper;
import static base.BaseTest.Config;
public class Jobs_Lever_Co_New_Page {

    private ElementHelper elementHelper;
    private WebDriverWait wait;

    // Element Helper'ı BaseTest'ten gelen driver ile başlatmak için constructor
    public Jobs_Lever_Co_New_Page(ElementHelper elementHelper) {
        this.elementHelper = elementHelper;
        // WebDriverWait'i başlat
        this.wait = new WebDriverWait(BaseTest.getDriver(), java.time.Duration.ofSeconds(15));
    }



    /**
     * Yeni açılan penceredeki URL'i kontrol eder ve ardından pencereleri temizler.
     * @param originalWindowHandle İlk (Careers Page) penceresinin handle'ı.
     * @return URL kontrolü başarılıysa true, aksi halde false döner.
     */
    public boolean verifyLeverPageRedirection(String originalWindowHandle) {

        // Geçiş başarılı olmadıysa veya handle boşsa, işlem yapmadan geri dön
        if (originalWindowHandle == null) {
            System.err.println("HATA: Pencere geçişi zaten başarısızdı, URL kontrolü atlanıyor.");
            return false;
        }

        boolean isUrlVerified = false;

        // 5. URL Kontrolü
        try {
            new WebDriverWait(BaseTest.getDriver(), java.time.Duration.ofSeconds(10)).until(
                    ExpectedConditions.urlContains(Config.getString("VIEW_ROLE_NEW_PAGE"))
            );

            String currentUrl = BaseTest.getDriver().getCurrentUrl();

            if (currentUrl.contains(Config.getString("VIEW_ROLE_NEW_PAGE"))) {
                System.out.println("INFO: Başarıyla Lever Application form sayfasına yönlendirildi. URL: " + currentUrl);
                isUrlVerified = true;
            } else {
                System.err.println("HATA: Yönlendirme başarısız. Beklenen başvuru URL'i bulunamadı. Mevcut URL: " + currentUrl);
                isUrlVerified = false;
            }
        } catch (Exception e) {
            System.err.println("HATA: URL kontrolü sırasında bir hata oluştu veya Timeout yaşandı. " + e.getMessage());
            isUrlVerified = false;
        } finally {
            // Her durumda yeni sekmeyi kapat ve ana sekmeye geri dön.
            try {
                BaseTest.getDriver().close(); // Yeni sekmeyi kapat
                BaseTest.getDriver().switchTo().window(originalWindowHandle); // Ana sekmeye geri dön
                System.out.println("INFO: Yeni sekme kapatıldı ve ana sekmeye geri dönüldü.");
            } catch (Exception closeEx) {
                System.err.println("HATA: Sekme kapatma veya ana sekmeye dönme sırasında hata oluştu. " + closeEx.getMessage());
            }
        }

        return isUrlVerified;
    }
}
