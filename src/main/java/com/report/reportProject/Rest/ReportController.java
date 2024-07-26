package com.report.reportProject.Rest;

import com.report.reportProject.DTO.ReportRequest;
import com.report.reportProject.Entity.Laborant;
import com.report.reportProject.Entity.Report;
import com.report.reportProject.Services.LaborantService;
import com.report.reportProject.Services.ReportService;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private LaborantService laborantService;

    @Value("${file.upload-dir}")// dosyaların yolunu veriyoruz
    private String uploadDir;


    @GetMapping
    public List<Report> getReports() {
        return reportService.getAllReports();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReport(@PathVariable int id) {
        Report report = reportService.getReportById(id);
        if (report == null){
            return new ResponseEntity<>("Rapor bulunamadı", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(report);
    }

  /*  @PostMapping
    public Report addReport(@RequestBody Report report) {
        reportService.saveReport(report);
        return report;
    }*/


    @PostMapping // bu kısmı yazarken biraz zorlandım cıkan bir hata sonrası laborantın varlıgını kontrol etemem ona gore bir action yapmam gerekti
    public ResponseEntity<?> addReport(@RequestBody ReportRequest reportRequest) {
        Laborant laborant = laborantService.getLaborantById(reportRequest.getLaborantId());
        if (laborant == null) {
            return new ResponseEntity<>("Laborant bulunamadı", HttpStatus.NOT_FOUND);
        }

        // Requestparam json ile gonderilen veriler ile çalışmıyor
        // biraz ugraşmak zorunda kaldım oyuzden raw olarak JSON gönderilen veriler RequestBody ile cok daha duzgun çalışıyor
        // body deki spesifik verileri (laborantId) çekmek içinde DTO kullanmamız gerekti

        Report report = new Report();
        report.setFileNumber(reportRequest.getFileNumber());
        report.setPatientFirstName(reportRequest.getPatientFirstName());
        report.setPatientLastName(reportRequest.getPatientLastName());
        report.setPatientIdentityNumber(reportRequest.getPatientIdentityNumber());
        report.setDiagnosisTitle(reportRequest.getDiagnosisTitle());
        report.setDiagnosisDetails(reportRequest.getDiagnosisDetails());
        report.setReportDate(LocalDate.parse(reportRequest.getReportDate()));
        report.setLaborant(laborant);

        reportService.saveReport(report);
        return ResponseEntity.ok().build();
    }


    // image upload kısmı
    @PostMapping("/{id}/upload")// upload yapacagımız url yolu
    public ResponseEntity<?> uploadReport(@PathVariable int id, @RequestParam("file") MultipartFile file) {
        try {// http durumunu guncellemek için oluştuurlan webden yakalanan dosyanın başarılı olup olmadıgının kontrolu
            String fileName = reportService.saveReportImage(id, file);// service dosyasından fonksiyonlarımız ile db ye kaydetme
            return ResponseEntity.ok(fileName);
        }catch(IOException e) {// başarısız olan yukleme sonrası cıkan hata ve kodunu gonderme
            return new ResponseEntity<>("Dosya yüklenemedi",HttpStatus.INTERNAL_SERVER_ERROR);
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{id}/image")// bu kısım uploadlanmış resimleri göruntuleme kısmı // bu kodu değiştirdim yeni versiyonu bu
    public ResponseEntity<Resource> serveFile(@PathVariable int id) {
        try {
            Report report = reportService.getReportById(id);
            if (report == null || report.getReportImagePath()==null) { // rapor olabilir fakat resim olmayabilir
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Path file = Paths.get(uploadDir).resolve(report.getReportImagePath());
            //yüklenen dosyanın temsili olan bir path objesi oluşturur
            //Resource resource = new UrlResource(file.toUri());
            //file nesnesini uniform resource ıdentifier yani dosya yolunun bir url temsili haline getirir
            if (!Files.exists(file) || !Files.isReadable(file)){// dosyadaki okunamazlık ve olmama durumundaki hata
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Resource resource = new UrlResource(file.toUri());
            /*urlresource objesini ust sınıf olan resourceye çevirmemiz gerek*/
            // SubClass -> superClass UPCASTİNG yapıyoruz yani

            String DosyaTuru = Files.probeContentType(file);
            // bu kod gelen verinin hangi türde oldugunu saptar ve dosyayı görebilmezi sağlar

            return ResponseEntity.ok().contentType(MediaType.parseMediaType(DosyaTuru)).body(resource);
            //medya dosyasını gösterricez ve http kodunu başarılı ayarlayalım tarayıcıya medya tipini de göndermiş olduk


        } catch (MalformedURLException e) {// malformed urllerin oluşturulması sırasında çıkan hataları için kullanılıyor
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);//probeContentType için gerekli catch bloğu
        }
        /*Path file = Paths.get(uploadDir).resolve(fileName);// yolu alıp bir String haline geitirip sunmamız gerek
        Resource resource = (Resource) new UrlResource(file.toUri());
        return ResponseEntity.ok().body(resource);*/
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReport(@PathVariable int id) {
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build(); // NoContent 204 no content durumunu http ye gönderir .build() ise bunu gönderme paketi oluşturur
    }

    @GetMapping("/search")
    public ResponseEntity< List<Report>> searchReport(// opsiyonel verilen datalar ile query sınaması yapicak DAO kısmında kodu görebilirsiniz
                                     @RequestParam(required = false) String patientFirstName,
                                     @RequestParam(required = false) String patientLastName,
                                     @RequestParam(required = false) String patientIdentityNumber,
                                     @RequestParam(required = false) String laborantFirstName,
                                     @RequestParam(required = false) String laborantLastName) {

        List<Report> reports = reportService.searchReports(patientFirstName,patientLastName,patientIdentityNumber,laborantFirstName,laborantLastName);
        return ResponseEntity.ok(reports); // ok fonksşiyonu ile 200 http kodu gondererek basarılı demis olduk

    }


    /*@PutMapping("/{id}")
    public Report updateReport(@PathVariable int id, @RequestBody Report updatedReport) {
        Report existingReport = reportService.getReportById(id);
        if (existingReport == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Report Report = reportService.saveReport(updatedReport);
        return Report;
    }*/

    @PutMapping("/{id}")
    public ResponseEntity<Report> updateReport(@PathVariable int id, @RequestBody Report updatedReport) {
        // Mevcut Report'ı al
        Report existingReport = reportService.getReportById(id);
        if (existingReport == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found döndürür
        }


        // Güncellenmiş report'ı mevcut report ile güncelle
        existingReport.setDiagnosisTitle(updatedReport.getDiagnosisTitle());
        existingReport.setDiagnosisDetails(updatedReport.getDiagnosisDetails());
        existingReport.setReportDate(updatedReport.getReportDate());

        // Güncellenmiş report nesnesini kaydet
        Report savedReport = reportService.saveReport(existingReport);

        return ResponseEntity.ok(savedReport); // 200 OK ve güncellenmiş report döndürür
    }

    @GetMapping("/asc")
    public List<Report> ascReport() {
        return reportService.getAllReportsByAsc();
    }

}

