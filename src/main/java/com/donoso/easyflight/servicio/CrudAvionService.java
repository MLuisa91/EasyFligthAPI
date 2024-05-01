package com.donoso.easyflight.servicio;

import com.donoso.easyflight.hibernate.HibernateSessionFactory;
import com.donoso.easyflight.modelo.Avion;
import com.donoso.easyflight.modelo.Pais;
import com.donoso.easyflight.modelo.Usuario;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CrudAvionService extends HibernateSessionFactory implements CrudServiceInterface<Avion>{

    public CrudAvionService(){
        super();
    }

    @Override
    public void save(Avion avion) {
        try{
            this.session.getTransaction().begin();

            session.persist(avion);
            session.getTransaction().commit();
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            session.close();
            session.getSessionFactory().close();
        }
    }

    @Override
    public void update(Avion avion) {
        try{
            if (this.findById(avion) != null) {
                openSession();
                session.getTransaction().begin();
                session.update(avion);
                session.getTransaction().commit();
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            session.close();
            session.getSessionFactory().close();
        }
    }

    @Override
    public void delete(Avion avion) {
        try{
            if (this.findById(avion) != null) {
                openSession();
                session.getTransaction().begin();
                session.delete(avion);
                session.getTransaction().commit();
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            session.close();
            session.getSessionFactory().close();
        }
    }

    @Override
    public List<Avion> search(Avion avion) {
        List<Avion> avionList = null;
        try {
            List<Predicate> predicados = new ArrayList<>();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Avion> criteriaQuery = cb.createQuery(Avion.class);

            Root<Avion> avionRoot = criteriaQuery.from(Avion.class);
            criteriaQuery.select(avionRoot);

            if(avion!=null) {
                if (avion.getId() != null){
                    predicados.add(cb.like(avionRoot.get("id"), "%".concat(avion.getId()).concat("%")));
                }
                if (avion.getModelo() != null) {
                    predicados.add(cb.like(avionRoot.get("modelo"), "%".concat(avion.getModelo()).concat("%")));
                }
                if (avion.getPasajeros() != null) {
                    predicados.add(cb.equal(avionRoot.get("pasajeros"), avion.getPasajeros()));
                }
            }

            if(!predicados.isEmpty())
                criteriaQuery.where(cb.or(predicados.toArray(new Predicate[0])));

            Query query = session.createQuery(criteriaQuery);
            avionList = (List<Avion>) query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
            session.getSessionFactory().close();
        }

        return avionList;
    }

    @Override
    public Avion findById(Avion avion) {
        Avion a;
        try {
            a = session.createQuery("from Avion a where a.id = :id", Avion.class).setParameter("id", avion.getId()).uniqueResult();

            session.close();
            session.getSessionFactory().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return a;
    }
}
