package com.donoso.easyflight.servicio;

import com.donoso.easyflight.hibernate.HibernateSessionFactory;
import com.donoso.easyflight.modelo.ReservaExtra;
import com.donoso.easyflight.modelo.ReservaViajero;
import com.donoso.easyflight.modelo.UsuarioRol;

import java.util.List;

public class CrudReservaViajeroService extends HibernateSessionFactory implements CrudServiceInterface<ReservaViajero> {

    public CrudReservaViajeroService() {
        super();
    }

    @Override
    public void save(ReservaViajero reservaViajero) {
        try {
            openSession();
            session.getTransaction().begin();

            session.save(reservaViajero);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
            session.getSessionFactory().close();
        }
    }

    @Override
    public void update(ReservaViajero reservaViajero) {

    }

    @Override
    public void delete(ReservaViajero reservaViajero) {
        try {
            openSession();
            session.getTransaction().begin();
            session.createQuery("delete from ReservaViajero r where r.reserva.id = :id")
                    .setParameter("id", reservaViajero.getReserva().getId())
                    .executeUpdate();
            session.getTransaction().commit();

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
            session.getSessionFactory().close();
        }
    }

    @Override
    public List<ReservaViajero> search(ReservaViajero usuarioRol) {
        return null;
    }

    @Override
    public ReservaViajero findById(ReservaViajero usuarioRol) {
        return null;
    }
}
