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
        test.pass("Filtreleme (" + LOCATION + " ve " + DEPARTMENT + ") başarıyla uygulandı.");



        // ADIM 4: FİLTRE SONUÇLARINI KONTROL ET (Yeni metot)
        boolean isVerificationSuccessful = careersPage.verifyJobFilters(LOCATION, DEPARTMENT);
        assertTrue(isVerificationSuccessful, "Adım 4 BAŞARISIZ: Filtreleme ," + LOCATION + " ve " + DEPARTMENT + ", sonrası iş ilanları kriterlere uymuyor veya liste bulunamadı.");
        test.pass("Tüm iş ilanlarının"  + LOCATION + " ve " + DEPARTMENT + "bilgileri filtrelenen kriterlere uygundur.");

        // ------------------- ADIM 5: VIEW ROLE TIKLAMA VE YÖNLENDİRME KONTROLÜ -------------------
        boolean isRedirectSuccessful = careersPage.clickAndVerifyLeverApplicationPage();
        assertTrue(isRedirectSuccessful, "Adım 5 BAŞARISIZ: 'View Role' tıklaması veya Lever başvuru sayfasına yönlendirme başarısız.");
        test.pass("View Role butonuna tıklandı ve Lever Application form sayfasına yönlendirme başarılı.");

    }
}