package com.report.reportProject.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

// tablo oluşturma
@Entity
@Table(name = "laborant")
public class Laborant {




    // database tarafından oluşturulan laborant id numarası
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    // laborantın ismi
    @Column(name = "first_name",nullable = false)
    private String firstName;
    // laborantın soyismi
    @Column(name = "last_name",nullable = false)
    private String lastName;
    // hastane numarası
    @Column(name = "hospital_identity_number",nullable = false, unique = true , length = 7)

    private String hospitalIdentityNumber;

    // her labrant birden fazla rapora sahip olabilir (-<)
    @OneToMany(mappedBy = "laborant")
    @JsonManagedReference
    private List<Report> reports;

    // contructor , getter ve setterlar
    // ID otomatik oluşturulacak

    public Laborant() {}

    public Laborant(String firstName, String lastName, String hospitalIdentityNumber, List<Report> reports) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.hospitalIdentityNumber = hospitalIdentityNumber;
        this.reports = reports;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHospitalIdentityNumber() {
        return hospitalIdentityNumber;
    }

    public void setHospitalIdentityNumber(String hospitalIdentityNumber) {
        this.hospitalIdentityNumber = hospitalIdentityNumber;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }
}

