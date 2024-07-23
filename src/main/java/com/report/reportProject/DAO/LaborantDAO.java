package com.report.reportProject.DAO;

import com.report.reportProject.Entity.Laborant;

import java.util.List;

public interface LaborantDAO {

    // raporda açıkladıgım gibi burdaki kodlarda ona benzer
    //isterseniz ordaki açıklamalarıma bakabilirsiniz

    List<Laborant> findAll();
    Laborant findById(int id);
    Laborant save(Laborant lab);
    void delete(int id);
}
