package com.report.reportProject.Services;

import com.report.reportProject.DAO.LaborantDAO;
import com.report.reportProject.Entity.Laborant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/* Service dosyaları interface ile birlikte fonksiyonların
 daha okunaklı olmasını sağlar ve erişim kolaylığı sağlar
 ayrıca MAVEN tarafından sağlanan Injectionslar ayarlanır
 */



import java.util.List;
@Service
public class LaborantService implements LaborantServiceInterface{

    @Autowired
    private LaborantDAO laborantDAO;


    @Override
    public List<Laborant> getLaborants() {
        return laborantDAO.findAll();
    }

    @Override
    public Laborant getLaborantById(int id) {
        return laborantDAO.findById(id);
    }

    @Override
    public Laborant SaveLaborant(Laborant lab) {
        return laborantDAO.save(lab);
    }

    @Override
    public void deleteLaborant(int id) {
        laborantDAO.delete(id);
    }
}
