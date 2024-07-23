package com.report.reportProject.Services;

import com.report.reportProject.Entity.Laborant;

import java.util.List;

public interface LaborantServiceInterface {
    List<Laborant> getLaborants();

    Laborant getLaborantById(int id);

    void SaveLaborant(Laborant lab);

    void deleteLaborant(int id);
}
