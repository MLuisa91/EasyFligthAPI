package com.donoso.easyflight.servicio;

import com.donoso.easyflight.hibernate.HibernateSessionFactory;
import com.donoso.easyflight.modelo.Aeropuerto;
import com.donoso.easyflight.modelo.Avion;
import com.donoso.easyflight.modelo.Vuelo;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CrudVueloService extends HibernateSessionFactory implements CrudServiceInterface<Vuelo> {


    public CrudVueloService() {
        super();
    }

    @Override
    public void save(Vuelo vuelo) {
        try {
            session.getTransaction().begin();

            session.persist(vuelo);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
            session.getSessionFactory().close();
        }
    }

    @Override
    public void update(Vuelo vuelo) {
        try {
            if (this.findById(vuelo) != null) {
                session.getTransaction().begin();
                session.update(vuelo);
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
            session.getSessionFactory().close();
        }
    }

    @Override
    public void delete(Vuelo vuelo) {
        try {
            if (this.findById(vuelo) != null) {
                session.getTransaction().begin();
                session.delete(vuelo);
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
            session.getSessionFactory().close();
        }
    }

    @Override
    public List<Vuelo> search(Vuelo vuelo) {
        List<Vuelo> vueloList = null;

        try {
            List<Predicate> predicados = new ArrayList<>();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Vuelo> criteriaQuery = cb.createQuery(Vuelo.class);

            Root<Vuelo> vueloRoot = criteriaQuery.from(Vuelo.class);
            criteriaQuery.select(vueloRoot);
            Join<Vuelo, Aeropuerto> origenJoin = vueloRoot.join("origen", JoinType.INNER);
            Join<Vuelo, Aeropuerto> destinoJoin = vueloRoot.join("destino", JoinType.INNER);
            Join<Vuelo, Avion> avionJoin = vueloRoot.join("avion", JoinType.INNER);

            if (vuelo.getId() != null) {
                predicados.add(cb.equal(vueloRoot.get("id"), vuelo.getId()));
            }
            if (vuelo.getAvion() != null) {
                predicados.add(cb.like(avionJoin.get("modelo"), "%".concat(vuelo.getAvion().getModelo()).concat("%")));
            }
            if (vuelo.getOrigen() != null) {
                predicados.add(cb.like(origenJoin.get("nombre"), "%".concat(vuelo.getOrigen().getNombre()).concat("%")));
            }
            if (vuelo.getDestino() != null) {
                predicados.add(cb.like(destinoJoin.get("nombre"), "%".concat(vuelo.getDestino().getNombre()).concat("%")));
            }
            if (vuelo.getFechaSalida() != null) {
                predicados.add(cb.equal(vueloRoot.get("fechaSalida"), vuelo.getFechaSalida()));
            }
            if (vuelo.getHoraLlegada() != null) {
                predicados.add(cb.equal(vueloRoot.get("horaLlegada"), vuelo.getHoraLlegada()));
            }
            if (vuelo.getHoraSalida() != null) {
                predicados.add(cb.equal(vueloRoot.get("horaSalida"), vuelo.getHoraSalida()));
            }
            if (vuelo.getPrecio() != null) {
                predicados.add(cb.equal(vueloRoot.get("precio"), vuelo.getPrecio()));
            }

            if (!predicados.isEmpty())
                criteriaQuery.where(cb.or(predicados.toArray(new Predicate[0])));

            Query query = session.createQuery(criteriaQuery);
            vueloList = (List<Vuelo>) query.getResultList();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
            session.getSessionFactory().close();
        }

        return vueloList;

    }

    public List<Vuelo> searchLimitado(Vuelo vuelo) {
        List<Vuelo> vueloList = null;

        try {
            List<Predicate> predicados = new ArrayList<>();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Vuelo> criteriaQuery = cb.createQuery(Vuelo.class);

            Root<Vuelo> vueloRoot = criteriaQuery.from(Vuelo.class);
            criteriaQuery.select(vueloRoot);
            Join<Vuelo, Aeropuerto> origenJoin = vueloRoot.join("origen", JoinType.INNER);
            Join<Vuelo, Aeropuerto> destinoJoin = vueloRoot.join("destino", JoinType.INNER);

            if (vuelo.getOrigen() != null) {
                predicados.add(cb.like(origenJoin.get("nombre"), "%".concat(vuelo.getOrigen().getNombre()).concat("%")));
            }
            if (vuelo.getDestino() != null) {
                predicados.add(cb.like(destinoJoin.get("nombre"), "%".concat(vuelo.getDestino().getNombre()).concat("%")));
            }
            if (vuelo.getFechaSalida() != null) {
                predicados.add(cb.equal(vueloRoot.get("fechaSalida"), vuelo.getFechaSalida()));
            }

            if (!predicados.isEmpty())
                criteriaQuery.where(cb.and(predicados.toArray(new Predicate[0])));

            Query query = session.createQuery(criteriaQuery);
            vueloList = (List<Vuelo>) query.getResultList();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
            session.getSessionFactory().close();
        }

        return vueloList;

    }

    @Override
    public Vuelo findById(Vuelo vuelo) {
        Vuelo v;
        try {
            v = session.createQuery("from Vuelo v where v.id = :id", Vuelo.class).setParameter("id", vuelo.getId()).uniqueResult();

            session.close();
            session.getSessionFactory().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return v;
    }

    public BigInteger contadorVuelos(String tipo) {
        BigInteger vuelos = null;
        try {
            switch (tipo) {
                case "MONTH":
                    vuelos = (BigInteger) session.createNativeQuery("SELECT COUNT(*) AS total_vuelos_mes_actual FROM vuelo WHERE YEAR(fechaSalida) = YEAR(CURDATE()) AND MONTH(fechaSalida) = MONTH(CURDATE())").uniqueResult();
                    break;
                case "YEAR":
                    vuelos = (BigInteger) session.createNativeQuery("SELECT COUNT(*) AS total_vuelos_año_actual FROM vuelo WHERE YEAR(fechaSalida) = YEAR(CURDATE())").uniqueResult();
                    break;
                case "TOTAL":
                    vuelos = (BigInteger) session.createNativeQuery("SELECT COUNT(*) AS total_vuelos FROM vuelo").uniqueResult();
                    break;
            }


        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return vuelos;
    }
}
