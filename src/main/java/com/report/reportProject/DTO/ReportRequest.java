package com.report.reportProject.DTO;


public class ReportRequest {
    private String fileNumber;
    private String patientFirstName;
    private String patientLastName;
    private String patientIdentityNumber;
    private String diagnosisTitle;
    private String diagnosisDetails;
    private String reportDate;
    private int laborantId;

    // Getter ve Setter metodları

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public String getPatientIdentityNumber() {
        return patientIdentityNumber;
    }

    public void setPatientIdentityNumber(String patientIdentityNumber) {
        this.patientIdentityNumber = patientIdentityNumber;
    }

    public String getDiagnosisTitle() {
        return diagnosisTitle;
    }

    public void setDiagnosisTitle(String diagnosisTitle) {
        this.diagnosisTitle = diagnosisTitle;
    }

    public String getDiagnosisDetails() {
        return diagnosisDetails;
    }

    public void setDiagnosisDetails(String diagnosisDetails) {
        this.diagnosisDetails = diagnosisDetails;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public int getLaborantId() {
        return laborantId;
    }

    public void setLaborantId(int laborantId) {
        this.laborantId = laborantId;
    }
}
