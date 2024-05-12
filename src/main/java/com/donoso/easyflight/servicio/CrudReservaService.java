package com.donoso.easyflight.servicio;

import com.donoso.easyflight.hibernate.HibernateSessionFactory;
import com.donoso.easyflight.modelo.Oferta;
import com.donoso.easyflight.modelo.Reserva;
import com.donoso.easyflight.modelo.Usuario;
import com.donoso.easyflight.modelo.Vuelo;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class CrudReservaService extends HibernateSessionFactory implements CrudServiceInterface<Reserva> {


    public CrudReservaService(){
        super();
    }

    @Override
    public void save(Reserva reserva) {
        try{
            session.getTransaction().begin();

            session.persist(reserva);
            session.getTransaction().commit();
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            session.close();
            session.getSessionFactory().close();
        }
    }

    @Override
    public void update(Reserva reserva) {
        try{
            if (this.findById(reserva) != null) {
                session.getTransaction().begin();
                session.update(reserva);
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
    public void delete(Reserva reserva) {
        try{
            if (this.findById(reserva) != null) {
                session.getTransaction().begin();
                session.delete(reserva);
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
    public List<Reserva> search(Reserva reserva) {
        List<Reserva> reservaList = null;
        try{
            List<Predicate> predicados = new ArrayList<>();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Reserva> criteriaQuery = cb.createQuery(Reserva.class);

            Root<Reserva> reservaRoot = criteriaQuery.from(Reserva.class);
            criteriaQuery.select(reservaRoot);
            Join<Reserva, Usuario> usuarioJoin =  reservaRoot.join("usuario", JoinType.INNER);
            Join<Reserva, Vuelo> vueloJoin =  reservaRoot.join("vuelo", JoinType.INNER);
            Join<Reserva, Oferta> ofertaJoin =  reservaRoot.join("oferta", JoinType.INNER);

            if(reserva != null){
                if (reserva.getId()!=null) {
                    predicados.add(cb.equal(reservaRoot.get("id"), reserva.getId()));
                }
                if (reserva.getUsuario()!=null)
                    predicados.add(cb.like(usuarioJoin.get("idDni"), "%".concat(reserva.getUsuario().getId().toString()).concat("%")));
                if(reserva.getVuelo()!=null){
                    predicados.add(cb.like(vueloJoin.get("id"), "%".concat(reserva.getVuelo().getId()).concat("%")));
                }
                if(reserva.getOferta()!=null){
                    predicados.add(cb.like(ofertaJoin.get("nombre"), "%".concat(reserva.getOferta().getNombre()).concat("%")));
                }
                if(reserva.getNumPasajeros()!=null){
                    predicados.add(cb.equal(reservaRoot.get("numPasajeros"), reserva.getNumPasajeros()));
                }
                if(reserva.getTotal()!=null){
                    predicados.add(cb.equal(reservaRoot.get("total"), reserva.getTotal()));
                }
            }


            if(!predicados.isEmpty())
                criteriaQuery.where(cb.or(predicados.toArray(new Predicate[0])));

            Query query = session.createQuery(criteriaQuery);
            reservaList = (List<Reserva>) query.getResultList();
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            session.close();
            session.getSessionFactory().close();
        }

        return reservaList;
    }

    @Override
    public Reserva findById(Reserva reserva) {
        Reserva r;
        try {
            r = session.createQuery("from Reserva r where r.id = :id", Reserva.class).setParameter("id", reserva.getId()).uniqueResult();

            session.close();
            session.getSessionFactory().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return r;
    }

   /* public Reserva findByUsuario(Reserva reserva) {
        Reserva r;
        try {
            r = session.createQuery("from Reserva r where r.usuario = :usuario", Reserva.class)
                    .setParameter("usuario", reserva.getUsuario())
                    .getSingleResult();

            session.close();
            session.getSessionFactory().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return r;
    }*/
}
