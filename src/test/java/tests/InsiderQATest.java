package tests;

import base.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CareersPage;
import pages.HomePage;
import pages.JobPage;
import utils.ElementHelper;

import static org.testng.Assert.*;
import static base.BaseTest.Config;

public class InsiderQATest extends BaseTest {

    private HomePage homePage;
    private CareersPage careersPage;
    private JobPage jobPage;
    private ElementHelper elementHelper;



    @BeforeMethod
    public void initPages() {
        elementHelper = new ElementHelper(BaseTest.getDriver());
        homePage = new HomePage(elementHelper);
        careersPage = new CareersPage(elementHelper);
        jobPage = new JobPage(elementHelper);
        String baseUrl = Config.getString("BASE_URL");
        elementHelper.goToUrl(baseUrl);
    }

    @Test(description = "5 aşamalı Insider QA Test Case")
    public void insider_qa_test_case_all_steps() {

        // ---------------------------------------------------------------------------------
        // --- Adım 1: Anasayfayı Ziyaret Et ve Kontrol Et ---
        // ---------------------------------------------------------------------------------
        test.info("1. Adım: Anasayfaya git ve kontrol et.");
        assertTrue(homePage.isHomePageOpened(), "Adım 1 BAŞARISIZ: Anasayfa açılamadı veya URL yanlış.");
        test.pass("Anasayfa başarılı bir şekilde açıldı.");

        // ---------------------------------------------------------------------------------
        // --- Adım 2: Kariyer Sayfasına Geçiş ve Blok Kontrolü ---
        // ---------------------------------------------------------------------------------
        test.info("2. Adım: Company menusunden Careers sayfasına git ve blokları kontrol et.");

        // 2.1 Careers sayfasına geçiş
        assertTrue(homePage.selectCompanySelectCarersAndCheckCareerPage(), "Adım 2.1 BAŞARISIZ: Careers sayfasına geçilemedi.");
        test.pass("Careers sayfası başarılı bir şekilde açıldı.");


        // 2.2 Kariyer Sayfasındaki Blokları Kontrol Et
        boolean allBlocksAreVisible = careersPage.checkCareerPageBlocksAreOpen();
        assertTrue(allBlocksAreVisible, "Adım 2.2 BAŞARISIZ: Kariyer sayfasındaki Locations, Teams veya Life at Insider bloklarından biri/birkaçı görünür değil.");
        test.pass("Kariyer sayfasındaki tüm gerekli bloklar görünüyor.");

        // ---------------------------------------------------------------------------------
        // --- Adım 3: QA Sayfasına Git, Filtrele ve Liste Varlığını Kontrol Et ---
        // ---------------------------------------------------------------------------------
        final String LOCATION = "Istanbul, Turkiye";
        final String DEPARTMENT = "Quality Assurance";
        test.info("3. Adım: QA Sayfasına git, filtreleme yap ve iş listesinin varlığını kontrol et.");

        // 3.1 Doğrudan Careers QA URL'sine git
        String careersQualityAssuranceUrl = Config.getString("CAREERS_QA_URL");
        elementHelper.goToUrl(careersQualityAssuranceUrl);

        boolean isJobListPresent = careersPage.filterJobsAndCheckList(LOCATION, DEPARTMENT);

        assertTrue(isJobListPresent, "Adım 3 BAŞARISIZ: Filtreleme (" + LOCATION + " ve " + DEPARTMENT + ") sonrası iş listesi bulunamadı.");
        test.pass("Filtreleme uygulandı ve iş listesi ekranda görünüyor.");

        /*


        // 3.2-3.3 Filtreleme ve Liste Kontrolü (Tek bir metot çağrısıyla)
        // Not: Bu metot, QA sayfasına gittikten sonraki "See all QA jobs" tıklamasını da içermelidir.


        // ---------------------------------------------------------------------------------
        // --- Adım 4: Filtrelenmiş İş İlanlarının Detaylarını Kontrol Et ---
        // ---------------------------------------------------------------------------------
        test.info("4. Adım: Tüm ilanların Pozisyon, Departman ve Konum bilgilerini kontrol et.");

        // Pozisyon kelimesi olarak "Quality Assurance" kontrol ediliyor.
        boolean allJobsValid = homePage.checkAllJobsContainCorrectData("Quality Assurance", LOCATION, DEPARTMENT);

        assertTrue(allJobsValid, "Adım 4 BAŞARISIZ: Listelenen tüm iş ilanları, beklenen Pozisyon, Departman veya Konum kriterlerini içermiyor.");
        test.pass("Listelenen tüm iş ilanlarının Pozisyon, Departman ve Konum bilgileri doğru.");

        // ---------------------------------------------------------------------------------
        // --- Adım 5: "View Role" Butonuna Tıkla ve Lever Sayfasına Yönlendirmeyi Kontrol Et ---
        // ---------------------------------------------------------------------------------
        test.info("5. Adım: İlk ilanın 'View Role' butonuna tıkla ve harici başvuru sayfasına yönlendirmeyi kontrol et.");

        boolean isRedirected = homePage.clickFirstViewRoleAndCheckRedirection();

        assertTrue(isRedirected, "Adım 5 BAŞARISIZ: 'View Role' butonuna tıklandıktan sonra Lever/Greenhouse gibi harici bir başvuru sayfasına yönlendirme yapılamadı.");
        test.pass("'View Role' butonuna tıklandı ve başarılı bir şekilde harici başvuru sayfasına yönlendirildi.");

         */
    }
}