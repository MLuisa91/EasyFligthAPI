package com.donoso.easyflight.servicio;

import com.donoso.easyflight.hibernate.HibernateSessionFactory;
import com.donoso.easyflight.modelo.Aeropuerto;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class CrudAeropuertoService extends HibernateSessionFactory implements CrudServiceInterface<Aeropuerto>{

    public CrudAeropuertoService() {
        super();
    }

    @Override
    public void save(Aeropuerto object) {

    }

    @Override
    public void update(Aeropuerto object) {

    }

    @Override
    public void delete(Aeropuerto object) {

    }

    @Override
    public List<Aeropuerto> search(Aeropuerto aeropuerto) {

        return session.createQuery("from Aeropuerto", Aeropuerto.class).getResultList();
    }

    @Override
    public Aeropuerto findById(Aeropuerto aeropuerto) {
        Aeropuerto a;
        try {
            a = session.createQuery("from Aeropuerto a where a.id = :id", Aeropuerto.class).setParameter("id", aeropuerto.getId()).uniqueResult();

            session.close();
            session.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return a;
    }
}
