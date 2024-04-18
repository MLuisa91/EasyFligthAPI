package com.donoso.easyflight.servicio;

import com.donoso.easyflight.modelo.Oferta;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CrudOfertaService implements CrudServiceInterface<Oferta>{

    private org.hibernate.Session session;
    public CrudOfertaService(){
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        this.session = sf.openSession();
    }

    @Override
    public void save(Oferta oferta) {
        try{
            session.getTransaction().begin();

            session.persist(oferta);
            session.getTransaction().commit();
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            session.close();
            session.getSessionFactory().close();
        }
    }

    @Override
    public void update(Oferta oferta) {
        try{
            if (this.findById(oferta) != null) {
                session.getTransaction().begin();
                session.update(oferta);
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
    public void delete(Oferta oferta) {
        try{
            if (this.findById(oferta) != null) {
                session.getTransaction().begin();
                session.delete(oferta);
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
    public List<Oferta> search(Oferta oferta) {
        List<Oferta> ofertaList = null;
        try{
            List<Predicate> predicados = new ArrayList<>();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Oferta> criteriaQuery = cb.createQuery(Oferta.class);

            Root<Oferta> ofertaRoot = criteriaQuery.from(Oferta.class);

            if (oferta.getId()!=null) {
                predicados.add(cb.equal(ofertaRoot.get("id"), oferta.getId()));
            }
            if (oferta.getNombre()!=null)
                predicados.add(cb.equal(ofertaRoot.get("nombre"),oferta.getNombre()));
            if(oferta.getDescripcion()!=null){
                predicados.add(cb.like(ofertaRoot.get("descripcion"),"%".concat(oferta.getDescripcion()).concat("%")));
            }
            if(oferta.getDescuento()!=null){
                predicados.add(cb.equal(ofertaRoot.get("descuento"), oferta.getDescuento()));
            }
            if(oferta.getFechaInicio()!=null){
                predicados.add(cb.equal(ofertaRoot.get("fechaInicio"), oferta.getFechaInicio()));
            }
            if(oferta.getFechaFinal()!=null){
                predicados.add(cb.equal(ofertaRoot.get("fechaFinal"), oferta.getFechaFinal()));
            }

            criteriaQuery.select(ofertaRoot);
            criteriaQuery.where(cb.and(predicados.toArray(new Predicate[predicados.size()])));

            Query query = session.createQuery(criteriaQuery);
            ofertaList = (List<Oferta>) query.getResultList();
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            session.close();
            session.getSessionFactory().close();
        }

        return ofertaList;
    }

    @Override
    public Oferta findById(Oferta oferta) {
        Oferta o;

        try {
            o = session.createQuery("from Oferta o where o.id = :id", Oferta.class).setParameter("id", oferta.getId()).getSingleResult();

            session.close();
            session.getSessionFactory().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return o;
    }
}
