package tests;

import base.BaseTest;
import base.Drivers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CareersPage;
import pages.HomePage;
import pages.JobPage;
import utils.ElementHelper;

import static org.testng.Assert.*;
import static base.BaseTest.Config;
import static base.BaseTest.FilterParam;
public class InsiderQATest extends BaseTest {

    private HomePage homePage;
    private CareersPage careersPage;
    private JobPage jobPage;

    // Testten önce Page Object'leri başlat
    @BeforeMethod
    public void initPages() {
        // BaseTest'ten gelen ElementHelper'ı kullanarak Page Object'leri başlatır
        ElementHelper elementHelper = new ElementHelper();
        homePage = new HomePage(elementHelper);
        careersPage = new CareersPage(elementHelper);
        jobPage = new JobPage(elementHelper);

        // BASE_URL'i Config.properties'ten çeker
        String baseUrl = Config.getString("BASE_URL");
        elementHelper.goToUrl(baseUrl);
    }

    @Test(description = "5 asamali task")
    public void kazim_nihat_tekiner_case_all_steps() {

        // --- Adım 1: Anasayfayı Ziyaret Etme ve Kontrol Etme ---
        test.info("1. Adim: Anasayfaya git ve kontrol et");
        assertTrue(homePage.isHomePageOpened(), "Anasayfa acilmadi veya URL yanlis");
        test.pass("Anasayfa basarili bir sekilde acildi");

        // --- Adım 2.1: Kariyer Sayfasına Gitme
        test.info("2. Adim 1. Kisim: Company menusunden Carrers sayfasina git");
        assertTrue(homePage.selectCompanySelectCarersAndCheckCareerPage(), "Careers sayfasi acilmadi veya URL yanlis");
        test.pass("Careers sayfasi basarili bir sekilde acildi");

        // Adım 2.2: Kariyer sayfasındaki blokları kontrol et (Yeni Adım)
        test.info("2. Adim 2. Kisim: Kariyer sayfasındaki blokları kontrol et");
        boolean allBlocksAreVisible = homePage.checkCareerPageBlocksAreOpen();
        assertTrue(allBlocksAreVisible, "Kariyer sayfasındaki Locations, Teams veya Life at Insider bloklarından biri/birkaçı görünür değil.");
        test.pass("Kariyer sayfasındaki Locations, Teams veya Life at Insider blokları görünüyor");
    }


    /*

    @Test(description = "Insider QA İş Başvurusu Akışı Testi")
    public void qaJobApplicationWorkflowTest() {

        // --- Adım 1: Anasayfayı Ziyaret Etme ve Kontrol Etme ---
        test.info("1. Adım: Anasayfayı ziyaret et ve kontrol et.");
        assertTrue(homePage.isHomePageOpened(), "Anasayfa açılmadı veya URL yanlış.");
        test.pass("Anasayfa başarıyla açıldı.");

        // --- Adım 2: Kariyer Sayfasına Gitme ve Blokları Kontrol Etme ---
        test.info("2. Adım: Kariyer sayfasına git ve blokları kontrol et.");
        homePage.goToCareersPage();
        assertTrue(careersPage.verifyPageSectionsAreVisible(),
                "Kariyer sayfasında beklenen bloklar (Locations, Teams, Life at Insider) eksik.");
        test.pass("Kariyer sayfasına gidildi ve tüm bloklar göründü.");

        // --- Adım 3: QA İş İlanlarına Gitme ve Filtreleme ---
        test.info("3. Adım: QA iş ilanlarına git ve filtrele.");
        careersPage.goToQAJobs();

        jobPage.filterJobs(); // Lokasyon ve Departman filtresini uygular

        // İş listesinin varlığını kontrol et
        assertTrue(jobPage.getJobList().size() > 0, "Filtrelenmiş iş listesi boş. İstanbul/QA işi bulunamadı.");
        test.pass("İş ilanları listesi geldi ve boş değil.");

        // --- Adım 4: İş Detaylarını Kontrol Etme ---
        test.info("4. Adım: İş ilanlarının detaylarını kontrol et.");
        String expectedPosition = FilterParam.getString("JOB_POSITION_KEYWORD");
        String expectedDepartment = FilterParam.getString("JOB_DEPARTMENT");
        String expectedLocation = FilterParam.getString("JOB_LOCATION");

        // Doğrulama işlemini JobPage içinde yapan metodu çağırıyoruz.
        // Bu metot, her bir ilanın içeriğini kontrol eder.
        jobPage.verifyAllJobDetails(expectedPosition, expectedDepartment, expectedLocation);
        test.pass("Tüm iş ilanlarının Pozisyon, Departman ve Lokasyon bilgileri doğru.");

        // --- Adım 5: Lever Uygulama Formuna Yönlendirmeyi Kontrol Etme ---
        test.info("5. Adım: İlk işin 'View Role' butonuna tıkla ve Lever sayfasına yönlendir.");
        jobPage.clickFirstViewRoleButton();

        // Yeni pencereye geçildiği varsayılır. URL kontrolü yapılır.
        assertTrue(Drivers.CHROME.getDriver().getCurrentUrl().contains("lever.co/jobs/"),
                "Kullanıcı Lever Application form sayfasına yönlendirilmedi.");
        test.pass("Başvuru için Lever sayfasına başarıyla yönlendirildi.");
    }
*/

}
