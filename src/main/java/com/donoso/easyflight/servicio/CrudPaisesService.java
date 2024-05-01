package com.donoso.easyflight.servicio;

import com.donoso.easyflight.hibernate.HibernateSessionFactory;
import com.donoso.easyflight.modelo.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class CrudPaisesService extends HibernateSessionFactory implements CrudServiceInterface<Pais>{

    public CrudPaisesService(){
        super();
    }

    @Override
    public void save(Pais object) {

    }

    @Override
    public void update(Pais object) {

    }

    @Override
    public void delete(Pais object) {

    }

    @Override
    public List<Pais> search(Pais pais) {
        List<Pais> paisList = null;
        try{
            List<Predicate> predicados = new ArrayList<>();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Pais> criteriaQuery = cb.createQuery(Pais.class);

            Root<Pais> paisRoot = criteriaQuery.from(Pais.class);

            criteriaQuery.select(paisRoot);
            criteriaQuery.where(cb.and(predicados.toArray(new Predicate[predicados.size()])));

            Query query = session.createQuery(criteriaQuery);
            paisList = (List<Pais>) query.getResultList();
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            session.close();
            session.getSessionFactory().close();
        }

        return paisList;
    }

    @Override
    public Pais findById(Pais object) {
        return null;
    }
}
