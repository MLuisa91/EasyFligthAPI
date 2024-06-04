package com.donoso.easyflight.servicio;

import com.donoso.easyflight.hibernate.HibernateSessionFactory;
import com.donoso.easyflight.modelo.Extra;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CrudExtraService extends HibernateSessionFactory implements CrudServiceInterface<Extra> {

    public CrudExtraService() {
        super();
    }

    @Override
    public void save(Extra extra) {
        try {
            session.getTransaction().begin();

            session.persist(extra);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
            session.getSessionFactory().close();
        }
    }

    @Override
    public void update(Extra extra) {
        try {
            if (this.findById(extra) != null) {
                this.openSession();
                session.getTransaction().begin();
                session.update(extra);
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
    public void delete(Extra extra) {
        try {
            if (this.findById(extra) != null) {
                this.openSession();
                session.getTransaction().begin();
                session.delete(extra);
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
    public List<Extra> search(Extra extra) {
        List<Extra> extraList = null;
        try {
            List<Predicate> predicados = new ArrayList<>();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Extra> criteriaQuery = cb.createQuery(Extra.class);

            Root<Extra> extraRoot = criteriaQuery.from(Extra.class);

            if (extra != null) {
                if (extra.getId() != null) {
                    predicados.add(cb.equal(extraRoot.get("id"), extra.getId()));
                }
                if (extra.getNombre() != null)
                    predicados.add(cb.equal(extraRoot.get("nombre"), extra.getNombre()));
                if (extra.getDescripcion() != null) {
                    predicados.add(cb.like(extraRoot.get("descripcion"), "%".concat(extra.getDescripcion()).concat("%")));
                }
                if (extra.getCoste() != null) {
                    predicados.add(cb.equal(extraRoot.get("coste"), extra.getCoste()));
                }
            }

            criteriaQuery.select(extraRoot);
            criteriaQuery.where(cb.and(predicados.toArray(new Predicate[predicados.size()])));

            Query query = session.createQuery(criteriaQuery);
            extraList = (List<Extra>) query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
            session.getSessionFactory().close();
        }

        return extraList;
    }

    @Override
    public Extra findById(Extra extra) {
        Extra ex;
        try {
            ex = session.createQuery("from Extra e where e.id = :id", Extra.class).setParameter("id", extra.getId()).uniqueResult();

            session.close();
            session.getSessionFactory().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ex;
    }
}
