package com.ndl.erp.repository;

import com.ndl.erp.domain.ErrorEnvio;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ErrorEnvioRepository extends JpaRepository<ErrorEnvio, Integer> {

//    private final transient Logger log = LoggerFactory.getLogger(ErrorEnvioRepositoryImpl.class);
//
//    @Autowired
//    private SessionFactory sessionFactory;
//
//    private Session getSession() {
//        return sessionFactory.getCurrentSession();
//    }
//
//    public List<ErrorEnvio> getAll() {
//        Query query = this.getSession().createQuery("from ErrorEnvio ");
//        return query.list();
//    }
//
//
//    public ErrorEnvio get(Integer id) {
//        return (ErrorEnvio) this.getSession().get(ErrorEnvio.class, id);
//    }
//
//    public ErrorEnvio add(ErrorEnvio e) {
//        Integer id = (Integer) this.getSession().save(e);
//        e.setId(id);
//        return e;
//    }
//
//    public ErrorEnvio delete(Integer id) {
//        ErrorEnvio e = this.get(id);
//        if (e != null)
//            this.getSession().delete(e);
//        return e;
//    }
//
//    public boolean update(ErrorEnvio bank) {
//        this.getSession().update(bank);
//        return true;
//    }




}
