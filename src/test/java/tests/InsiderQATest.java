package tests;

import base.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.ElementHelper;

import static org.testng.Assert.*;

public class InsiderQATest extends BaseTest {

    private ElementHelper elementHelper;
    private HomePage homePage;
    private CareersPage careersPage;
    private Careers_QualityAssurancePage careers_QualityAssurancePage;
    private Careers_OpenPositionsPage careers_OpenPositionsPage;
    private Jobs_Lever_Co_New_Page jobs_Lever_Co_New_Page;

    @BeforeMethod
    public void initPages() {
        elementHelper = new ElementHelper(BaseTest.getDriver());
        homePage = new HomePage(elementHelper);
        careersPage = new CareersPage(elementHelper);
        careers_QualityAssurancePage = new Careers_QualityAssurancePage(elementHelper);
        careers_OpenPositionsPage = new Careers_OpenPositionsPage(elementHelper);
        jobs_Lever_Co_New_Page = new Jobs_Lever_Co_New_Page(elementHelper);
        String baseUrl = Config.getString("BASE_URL");
        elementHelper.goToUrl(baseUrl);
    }

    @Test(description = "5 aşamalı Insider QA Test Case")
    public void insider_qa_test_case_all_steps() {
        final String LOCATION = "Istanbul, Turkiye";
        final String DEPARTMENT = "Quality Assurance";

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
        // --- Adım 3: İş Listeleme Sayfasına Geç, Filtrele ve Liste Varlığını Kontrol Et ---
        // ---------------------------------------------------------------------------------
        test.info("3. Adım: QA Sayfasına git, filtreleme yap ve iş listesinin varlığını kontrol et.");

        // 3.1 "See all QA jobs" butonuna tıkla
        String careersQaUrl = Config.getString("CAREERS_QA_URL");
        elementHelper.goToUrl(careersQaUrl);
        boolean isJobListingNavigated = careers_QualityAssurancePage.clickSeeAllJobsButtonInCareersQaPage();
        assertTrue(isJobListingNavigated, "Adım 3.1 BAŞARISIZ: İş Listeleme sayfasına (Open Positions) geçilemedi.");

        // 3.2 Filtreleri Uygula ve İş İlanı Metnini Kontrol Et
        boolean areFiltersApplied = careers_OpenPositionsPage.applyLocationAndDepartmentFilters(LOCATION, DEPARTMENT);
        assertTrue(areFiltersApplied, "Adım 3.2 BAŞARISIZ: Filtreleme (" + LOCATION + " ve " + DEPARTMENT + ") sonrası iş ilanı metni bulunamadı.");
        test.pass("Filtreleme (" + LOCATION + " ve " + DEPARTMENT + ") başarıyla uygulandı ve iş ilanı metni doğrulandı.");

        // ---------------------------------------------------------------------------------
        // --- Adım 4: FİLTRE SONUÇLARINI KONTROL ET  ---
        // ---------------------------------------------------------------------------------
        test.info("4. Adım: Filtrelenen iş ilanının (kart üzerindeki) Konum ve Departman bilgilerini kontrol et.");
        boolean areJobFiltersCorrect = careers_OpenPositionsPage.verifyJobFilters(LOCATION, DEPARTMENT);
        assertTrue(areJobFiltersCorrect, "Adım 4 BAŞARISIZ: Filtrelenen iş ilanının Konum/Departman bilgileri hatalı.");
        test.pass("Tüm iş ilanlarının '" + LOCATION + "' ve '" + DEPARTMENT + "' bilgileri filtrelenen kriterlere uygundur.");

        // ---------------------------------------------------------------------------------
        // --- Adım 5: VIEW ROLE TIKLAMA VE YÖNLENDİRME KONTROLÜ  ---
        // ---------------------------------------------------------------------------------
        test.info("5. Adım: İlk ilanda 'View Role' butonuna tıkla ve yeni sekmeyi kontrol et.");

        // 5.1 İlk ilanda View Role butonuna tıkla ve yeni sekmeye geçiş yap
        String originalHandle = careers_OpenPositionsPage.clickFirstViewRoleButtonAndSwitchWindow();
        assertNotNull(originalHandle, "Adım 5.1 BAŞARISIZ: 'View Role' butonuna tıklanamadı veya yeni pencereye geçilemedi.");

        // 5.2 Yeni sekmedeki URL'i Kontrol Et ve Pencereleri Temizle
        boolean isRedirectSuccessful = jobs_Lever_Co_New_Page.verifyLeverPageRedirection(originalHandle);
        assertTrue(isRedirectSuccessful, "Adım 5.2 BAŞARISIZ: Lever/Greenhouse başvuru sayfasına yönlendirme başarılı değil.");

        test.pass("View Role butonuna tıklandı, Lever Application form sayfasına yönlendirme başarılı ve pencere temizliği yapıldı.");
    }
}