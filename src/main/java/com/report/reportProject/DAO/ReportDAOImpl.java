package com.report.reportProject.DAO;

import com.report.reportProject.Entity.Report;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/* merhaba bu kısımlari Spring data JPA kendisi yapabiliyor
kodları hazır şekilde elde edebiliyoruz fakat ben kendim yazmak
istedim. Mumkun oldugunca cok yorum satırı yazmaya çalıştım.
*/

@Repository
@Transactional
public class ReportDAOImpl implements ReportDAO {

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public ReportDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Report> findAllReports() {
        // database access ile SQL komutunu kullanarak veri çekiyoruz
        TypedQuery<Report> query = entityManager.createQuery("SELECT r FROM Report r", Report.class);
        //bu query aslında bir collection onu bir diziye çevirmek için getresultlist kullanacağız

        return query.getResultList();
    }

    @Override
    public Report findReportById(int id) {

        return entityManager.find(Report.class, id);
    }

    @Override
    @Transactional // veri yazmamızı sağlayacak
    public Report saveReport(Report report) {
        // eger gelen raporda id = 0 ise
        // yeni bir rapor oluşturulur değilse uzerine yazılır update gibi
        // databaseye veri yazma
         return entityManager.merge(report);
    }

    @Override
    public void deleteReport(int id) {
        // raporu bulma
        Report report = findReportById(id);
        if (report != null) {
            entityManager.remove(report);
        }
    }
    // bu kodlar ile sadece isim ile veya baska bilgiler ile istediği gibi arama yapılabiecek
    // Frontenden gelen bilgilere göre arama yapılacaktır
    // POSTMAN kullanarak denemeler yapabilirsiniz
    // bu kısım sorguyu cratequery fonksiyonuna gödereceğimiz SQL komutu
    // ayrıca oracle data manupilation ve database design serfitikalarımda bulunmakta
    @Override
    public List<Report> searchReports(String patientFirstName, String patientLastName, String patientIdentityNumber, String laborantFirstName, String laborantLastName) {

        StringBuilder queryStr = new StringBuilder("FROM Report r WHERE 1=1 ");

        if (patientFirstName != null && !patientFirstName.isEmpty()) {
            queryStr.append("AND r.patientFirstName LIKE :patientFirstName ");
        }
        if (patientLastName != null && !patientLastName.isEmpty()) {
            queryStr.append("AND r.patientLastName LIKE :patientLastName ");
        }
        if (patientIdentityNumber != null && !patientIdentityNumber.isEmpty()) {
            queryStr.append("AND r.patientIdentityNumber LIKE :patientIdentityNumber ");
        }
        if (laborantFirstName != null && !laborantFirstName.isEmpty()) {
            queryStr.append("AND r.laborant.firstName LIKE :laborantFirstName ");
        }
        if (laborantLastName != null && !laborantLastName.isEmpty()) {
            queryStr.append("AND r.laborant.lastName LIKE :laborantLastName ");
        }

        TypedQuery<Report> query = entityManager.createQuery(queryStr.toString(), Report.class);

        if (patientFirstName != null && !patientFirstName.isEmpty()) {
            query.setParameter("patientFirstName", "%" + patientFirstName + "%");
        }
        if (patientLastName != null && !patientLastName.isEmpty()) {
            query.setParameter("patientLastName", "%" + patientLastName + "%");
        }
        if (patientIdentityNumber != null && !patientIdentityNumber.isEmpty()) {
            query.setParameter("patientIdentityNumber", "%" + patientIdentityNumber + "%");
        }
        if (laborantFirstName != null && !laborantFirstName.isEmpty()) {
            query.setParameter("laborantFirstName", "%" + laborantFirstName + "%");
        }
        if (laborantLastName != null && !laborantLastName.isEmpty()) {
            query.setParameter("laborantLastName", "%" + laborantLastName + "%");
        }

        return query.getResultList();
    }

    // query ismindeki raporumuza detayları verileri ekledik şimdi dödurelim
    // bu bir collection diziye dödurulmesi gerek
    @Override
    public List<Report> findAllByOrderByReportDateAsc() {
        TypedQuery<Report> query = entityManager.createQuery("SELECT r FROM Report r order by r.reportDate ASC", Report.class);
        //SQL sorugusu ile AScending sıralamada dödurduk
        return query.getResultList();
    }
}
