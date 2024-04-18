package com.donoso.easyflight.servicio;

import com.donoso.easyflight.modelo.Avion;
import com.donoso.easyflight.modelo.Usuario;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.Serializable;
import java.util.List;

public class CrudAvionService implements CrudServiceInterface<Avion>{

    private org.hibernate.Session session;

    public CrudAvionService() {
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        this.session = sf.openSession();
    }

    @Override
    public void save(Avion avion) {
        try{
            session.getTransaction().begin();

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

        return session.createQuery("from Avion", Avion.class).getResultList();
    }

    @Override
    public Avion findById(Avion avion) {
        Avion a;
        try {
            a = session.createQuery("from Avion a where a.id = :id", Avion.class).setParameter("id", avion.getId()).getSingleResult();

            session.close();
            session.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return a;
    }
}
