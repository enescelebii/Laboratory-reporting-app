package com.report.reportProject.DAO;

import com.report.reportProject.Entity.Report;

import java.util.List;

public interface ReportDAO {

    //
    // istenen ozelliklerin sağlanacağı fonksiyonların
    // interface ile okunabilir hale getirilmesi


    List<Report> findAllReports();
   // id ye gore arama
    Report findReportById(int id);
    // update ve create kısımları save fonksiyonu ile yapılmakta
    // eger id = 0 ise create çalışacaktır farklı bir id varsa update olur
    Report saveReport(Report report);
    // id ile silim işlemi
    void deleteReport(int id);
    //detaylı arama
    List<Report> searchReports(String patientFirstname, String patientLastName, String patientIdentityNumber, String laborantFirstName, String laborantLastName);
    // tarihe gore sıralama
    List<Report> findAllByOrderByReportDateAsc();
}

