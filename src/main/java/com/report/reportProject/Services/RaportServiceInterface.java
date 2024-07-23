package com.report.reportProject.Services;

import com.report.reportProject.Entity.Report;

import java.util.List;

public interface RaportServiceInterface {

    List<Report> getAllReports();

    Report getReportById(int id);

    Report saveReport(Report report);

    void deleteReport(int id);

    List<Report> searchReports(String patientFirstName, String patientLastName, String patientIdentityNumber, String laborantFirstName, String laborantLastName);

    List<Report> getAllReportsByAsc();
}
