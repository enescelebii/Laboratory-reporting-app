package com.report.reportProject.DAO;

import com.report.reportProject.Entity.Laborant;
import com.report.reportProject.Entity.Report;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class LaborantDAOImpl implements LaborantDAO {

    @PersistenceContext
    private EntityManager entityManager;
    // entitt manager database ile ilgili veri alışverişimizi sağlar




    @Override
    public List<Laborant> findAll() {
        TypedQuery<Laborant> query = entityManager.createQuery("FROM Laborant", Laborant.class);//SQL kodları ile erişim sağladık
        return query.getResultList();
    }

    @Override
    public Laborant findById(int id) {
        return entityManager.find(Laborant.class, id);    }

    @Override
    @Transactional
    public Laborant save(Laborant lab) {
        return entityManager.merge(lab);
    }

    @Override
    @Transactional
    public void delete(int id) {
        Laborant laborant = entityManager.find(Laborant.class, id);
        if (laborant != null) {
            entityManager.remove(laborant);
        }
    }

}
