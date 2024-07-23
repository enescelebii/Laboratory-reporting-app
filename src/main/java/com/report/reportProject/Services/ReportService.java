package com.report.reportProject.Services;

import com.report.reportProject.DAO.LaborantDAO;
import com.report.reportProject.DAO.ReportDAO;
import com.report.reportProject.Entity.Laborant;
import com.report.reportProject.Entity.Report;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;


/* Service dosyaları interface ile birlikte fonksiyonların
 daha okunaklı olmasını sağlar ve erişim kolaylığı sağlar
 ayrıca MAVEN tarafından sağlanan Injectionslar ayarlanır
 */

@Service
public class ReportService implements RaportServiceInterface{

    @Autowired // DAO instance olusturma
    private ReportDAO reportDAO;

    @Autowired
    private LaborantDAO laborantDAO;


    @Value("${file.upload-dir}")// value bilirli bir alana değer atamak için kullanılır
    private String uploadDir;


    public String saveReportImage(int reportId, MultipartFile file)throws IOException {
        Report report = reportDAO.findReportById(reportId);
        if(report==null) {
            return "Report Not Found";
        }
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();//random isim ve orjinal ismin toplanması
        // bu kısım dosya adlarının çakışması için kullanılan bir fonksiyon
        Path filePath = Paths.get(uploadDir).resolve(fileName);
        // dosyanın yukleneceği yolun saklanması ve String olarak dondurulesi

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        // dosya kopyalama işlemi gerçekleşir girişi alınır yolda aynı dosya var ise uzerine yazar
        // bu tip fonksionları ezberlemek imkansız internetten gerekli şeyleri kullanarak aynı zamanda öğrenerek ilerlioyurm
        //mumkun oldugunca da açıklayıcı bir proje oluşturmak amacındayım

        report.setReportImagePath(fileName);
        reportDAO.saveReport(report);
        // raporun kaydedilmesi
        return fileName;

    }


    @Override
    public List<Report> getAllReports() {
        return reportDAO.findAllReports();
    }

    @Override
    public Report getReportById(int id) {
        return reportDAO.findReportById(id);
    }

    @Override
    @Transactional
    public Report saveReport(Report report) {
        if (report.getLaborant() != null){
            Laborant laborant = laborantDAO.findById(report.getLaborant().getId());
            if (laborant == null){
                laborantDAO.save(report.getLaborant());
            }// eger laborant yoksa hata çıkacaktır bu yuzden once laborantın oluşturulması gerek
            //çunku Laborantsız rapor olama
        }
        return reportDAO.saveReport(report);
    }

    @Override
    public void deleteReport(int id) {
        reportDAO.deleteReport(id);
    }

    @Override
    public List<Report> searchReports(String patientFirstName, String patientLastName, String patientIdentityNumber, String laborantFirstName, String laborantLastName) {
        return reportDAO.searchReports(patientFirstName, patientLastName, patientIdentityNumber, laborantFirstName, laborantLastName);
    }

    @Override
    public List<Report> getAllReportsByAsc() {
        return reportDAO.findAllByOrderByReportDateAsc();
    }


}
