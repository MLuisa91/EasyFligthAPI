package com.donoso.easyflight.servicio;

import com.donoso.easyflight.hibernate.HibernateSessionFactory;
import com.donoso.easyflight.modelo.Rol;
import com.donoso.easyflight.modelo.Usuario;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CrudRolService extends HibernateSessionFactory implements CrudServiceInterface<Rol>{

    public CrudRolService(){
        super();
    }

    @Override
    public void save(Rol rol) {

    }

    @Override
    public void update(Rol rol) {

    }

    @Override
    public void delete(Rol rol) {

    }

    @Override
    public List<Rol> search(Rol rol) {
        List<Rol> rolList = null;

        try{
            List<Predicate> predicados = new ArrayList<>();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Rol> criteriaQuery = cb.createQuery(Rol.class);

            Root<Rol> rolRoot = criteriaQuery.from(Rol.class);

            if (rol.getId()!=null)
                predicados.add(cb.equal(rolRoot.get("id"), rol.getId()));
            if (rol.getNombre()!=null)
                predicados.add(cb.equal(rolRoot.get("nombre"),rol.getNombre()));

            criteriaQuery.select(rolRoot);
            criteriaQuery.where(cb.and(predicados.toArray(new Predicate[predicados.size()])));

            Query query = session.createQuery(criteriaQuery);
            rolList = (List<Rol>) query.getResultList();
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            session.close();
            session.getSessionFactory().close();
        }


        return rolList;
    }

    @Override
    public Rol findById(Rol rol) {
        Rol r;

        try {
            r = session.createQuery("from Rol r where r.id = :id", Rol.class).setParameter("id", rol.getId()).uniqueResult();

            session.close();
            session.getSessionFactory().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return r;
    }
}
