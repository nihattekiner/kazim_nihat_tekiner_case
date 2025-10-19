# ✨ Insider QA Automation Framework

Bu repository <span style="color:green;"> **Java** </span> dilinde, <span style="color:green;">**Selenium WebDriver**</span> ve <span style="color:green;">**TestNG**</span> kullanılarak geliştirilmiş 5 adımlık Uçtan Uca (End-to-End) Test Otomasyon Çatısıdır.

**Manuel Çalıştırma:** Testi doğrudan IDE'niz (IntelliJ/JDK 21+) üzerinden çalıştırmak için:
1.  **Sınıf Konumu:** `src/test/java/tests/InsiderQATest.java`
2.  **Başlangıç:** `InsiderQATest` sınıfına sağ tıklayıp **"Run"** seçeneğini kullanın.
3. Testin başarılı olduğuna dair ekran kaydo linki : https://drive.google.com/file/d/1dyqZSD8f3g15o-lj0PJeHperX-D8Ifxh/view?usp=sharing
---

## 🎯 Otomasyon Senaryosu (QA Case)

Bu framework, aşağıdaki 5 temel iş adımını doğrular:

| Adım | Amaç | İlgili Sayfa |
| :--- | :--- | :--- |
| **1** | Insider ana sayfasını ziyaret et ve başarılı bir şekilde açıldığını doğrula. | `HomePage` |
| **2** | Menüden **Careers** sayfasına git; **Locations, Teams,** ve **Life at Insider** bloklarının görünürlüğünü doğrula. | `CareersPage` |
| **3** | QA pozisyonları sayfasına geç, **Location** filtresini "Istanbul, Turkey" ve **Department** filtresini "Quality Assurance" olarak uygula. | `Careers_QualityAssurancePage`, `Careers_OpenPositionsPage` |
| **4** | Filtrelenmiş iş listesindeki tüm ilanların **Konum** ve **Departman** bilgilerinin uygulanan filtrelere uygun olduğunu doğrula. | `Careers_OpenPositionsPage` |
| **5** | İlk pozisyondaki **"View Role"** butonuna tıkla ve açılan yeni sekmenin **Lever/Greenhouse** başvuru sayfasına yönlendirildiğini doğrula. | `Jobs_Lever_Co_New_Page` |

---

## 🛠️ Teknolojiler ve Gereksinimler

| Kategori | Teknoloji | Açıklama |
| :--- | :--- | :--- |
| **Programlama Dili** | Java (JDK 21+) | Projenin temel dili. |
| **Test Çatısı** | TestNG | Test yönetimi, sıralama ve raporlama altyapısı. |
| **UI Otomasyonu** | Selenium WebDriver | Web element etkileşimleri ve tarayıcı yönetimi. |
| **Proje Yönetimi** | Maven | Bağımlılık yönetimi ve derleme süreci. |
| **Raporlama** | Extent Reports | Detaylı, görsel test raporları üretimi. |

---

## ✅ Kod Gereksinimleri ve Uyumluluk

Bu proje, belirlenen tüm zorunlu gereksinimleri karşılamaktadır:

| Gereksinim | Durum | Açıklama |
| :--- | :--- | :--- |
| **Page Object Model (POM)** | ✔️ Tamamlandı | Tüm elementler ve metotlar ilgili Page sınıflarına ayrılmıştır. |
| **BDD Çatısı Yok** | ✔️ Tamamlandı | Cucumber, Quantum gibi BDD Framework'leri kullanılmamıştır. |
| **Optimize Edilmiş Selector** | ✔️ Tamamlandı | Elementleri bulmak için optimize edilmiş selector'lar kullanılmıştır. |
| **Clean Code** | ✔️ Tamamlandı | Kod, temizlik ve yüksek okunabilirlik standartlarına uygun olarak yazılmıştır. |
| **Cross-Browser Desteği** | ✔️ Kapsam Dahilinde | Chrome ve Firefox gibi tarayıcılarda parametrik çalıştırma desteği mevcuttur. |
| **Screenshot on Failure** | ✔️ Kapsam Dahilinde | Hata anında ekran görüntüsü yakalama mekanizması entegre edilmiştir. |

---

## ⚙️ Kurulum ve Çalıştırma

### <span style="color:red;"> 1. Depoyu Klonlama </span>

```bash
git clone [https://github.com/nihattekiner/kazim_nihat_tekiner_case.git](https://github.com/nihattekiner/kazim_nihat_tekiner_case.git)
cd kazim_nihat_tekiner_case
```

### <span style="color:red;"> 2. Bağımlılıkları Yükleme </span>
Maven'ın kurulu olduğundan emin olun ve bağımlılıkları yükleyin:

```bash
mvn clean install
```
### <span style="color:red;"> 3. Testleri Çalıştırma </span>
##### <span style="color:aqua;">  A. Maven Komutu ile Çalıştırma </span>
Tüm testleri varsayılan tarayıcıda çalıştırmak için:
```bash
mvn test
```
Belirli bir tarayıcıda (Örn: Chrome) çalıştırmak için: Tarayıcıyı Maven komutu ile parametrik olarak belirleme
```bash
mvn test -Dbrowser=chrome
```
##### <span style="color:aqua;">B.  IDE (IntelliJ/Eclipse) ile Çalıştırma </span>

İlgili test sınıfına (InsiderQATest.java) sağ tıklayıp "Run 'InsiderQATest'" seçeneğini kullanın.

Alternatif olarak, yapılandırılmış testng.xml dosyasını çalıştırın.

📄 Raporlama

Test çalışması tamamlandıktan sonra, ayrıntılı Extent Reports raporları aşağıdaki dizinde oluşturulur:

/test-output/ExtentReport.html