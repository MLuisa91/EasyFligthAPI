package com.donoso.easyflight.servicio;

import com.donoso.easyflight.hibernate.HibernateSessionFactory;
import com.donoso.easyflight.modelo.Oferta;
import com.donoso.easyflight.modelo.Viajero;
import com.donoso.easyflight.modelo.Vuelo;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class CrudViajeroService extends HibernateSessionFactory implements CrudServiceInterface<Viajero>{

    public CrudViajeroService(){
        super();
    }


    @Override
    public void save(Viajero viajero) {
        try{
            session.getTransaction().begin();

            session.persist(viajero);
            session.getTransaction().commit();
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            session.close();
            session.getSessionFactory().close();
        }
    }

    @Override
    public void update(Viajero viajero) {
        try{
            if (this.findById(viajero) != null) {
                openSession();
                session.getTransaction().begin();
                session.update(viajero);
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
    public void delete(Viajero viajero) {
        try{
            if (this.findById(viajero) != null) {
                openSession();
                session.getTransaction().begin();
                session.delete(viajero);
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
    public List<Viajero> search(Viajero viajero) {
        List<Viajero> viajeroList = null;
        try{
            List<Predicate> predicados = new ArrayList<>();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Viajero> criteriaQuery = cb.createQuery(Viajero.class);

            Root<Viajero> ofertaRoot = criteriaQuery.from(Viajero.class);
            criteriaQuery.select(ofertaRoot);

            if (viajero.getId()!=null) {
                predicados.add(cb.equal(ofertaRoot.get("id"), viajero.getId()));
            }
            if (viajero.getNombre()!=null)
                predicados.add(cb.equal(ofertaRoot.get("nombre"),viajero.getNombre()));
            if(viajero.getApellidos()!=null){
                predicados.add(cb.like(ofertaRoot.get("descripcion"),"%".concat(viajero.getApellidos()).concat("%")));
            }

            if(!predicados.isEmpty())
                criteriaQuery.where(cb.or(predicados.toArray(new Predicate[0])));

            Query query = session.createQuery(criteriaQuery);
            viajeroList = (List<Viajero>) query.getResultList();
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            session.close();
            session.getSessionFactory().close();
        }

        return viajeroList;
    }

    @Override
    public Viajero findById(Viajero viajero) {
        Viajero v;

        try {
            v = session.createQuery("from Viajero v where v.id = :id", Viajero.class).setParameter("id", viajero.getId()).uniqueResult();

            session.close();
            session.getSessionFactory().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return v;
    }
}
