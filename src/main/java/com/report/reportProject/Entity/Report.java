package com.report.reportProject.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
//tablomuz
@Entity
@Table(name = "report")
public class Report {
    // otomatik arttırılan id numarası Datası database tarafından gelecek
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    // rapor numarası
    @Column(name = "file_number")
    private String fileNumber;
    // isim
    @Column(name = "patient_first_name")
    private String patientFirstName;
    // soyisim
    @Column(name = "patient_last_name")
    private String patientLastName;
    // hasta kimlik numarası
    @Column(name = "patient_identity_number")
    private String patientIdentityNumber;
    // teşhis başlığı
    @Column(name = "diagnosis_title")
    private String diagnosisTitle;
    // teşhis detayı
    @Column(name = "diagnosis_details", length = 2000)
    private String diagnosisDetails;
    // rapor tarihi
    @Column(name = "report_date")
    private LocalDate reportDate;
    // fiziksel fotoğrafının yuklendiği yol
    @Column(name = "report_image_path")
    private String reportImagePath;

    // her raport sadece bir laborant tarafından sahip olunabilir (>-)
    @ManyToOne
    @JoinColumn(name = "laborant_id", nullable = false)
    @JsonBackReference
    private Laborant laborant;


    // constructor , getter , setterlar
    // ID otomatik oluşturulacak

    public Report() {}

    public Report(String fileNumber, String patientFirstName, String patientLastName, String patientIdentityNumber, String diagnosisTitle, String diagnosisDetails, LocalDate reportDate, String reportImagePath, Laborant laborant) {
        this.fileNumber = fileNumber;
        this.patientFirstName = patientFirstName;
        this.patientLastName = patientLastName;
        this.patientIdentityNumber = patientIdentityNumber;
        this.diagnosisTitle = diagnosisTitle;
        this.diagnosisDetails = diagnosisDetails;
        this.reportDate = reportDate;
        this.reportImagePath = reportImagePath;
        this.laborant = laborant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportImagePath() {
        return reportImagePath;
    }

    public void setReportImagePath(String reportImagePath) {
        this.reportImagePath = reportImagePath;
    }

    public Laborant getLaborant() {
        return laborant;
    }

    public void setLaborant(Laborant laborant) {
        this.laborant = laborant;
    }
}
