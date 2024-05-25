package com.donoso.easyflight.servicio;

import com.donoso.easyflight.hibernate.HibernateSessionFactory;
import com.donoso.easyflight.modelo.ReservaExtra;
import com.donoso.easyflight.modelo.UsuarioRol;

import java.util.List;

public class CrudReservaExtraService extends HibernateSessionFactory implements CrudServiceInterface<ReservaExtra> {

    public CrudReservaExtraService() {
        super();
    }

    @Override
    public void save(ReservaExtra reservaExtra) {
        try {
            openSession();
            session.getTransaction().begin();

            session.save(reservaExtra);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
            session.getSessionFactory().close();
        }
    }

    @Override
    public void update(ReservaExtra reservaExtra) {

    }

    @Override
    public void delete(ReservaExtra reservaExtra) {
        try {
            openSession();
            session.getTransaction().begin();
            session.createQuery("delete from ReservaExtra r where r.reserva.id = :id")
                    .setParameter("id", reservaExtra.getReserva().getId())
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
    public List<ReservaExtra> search(ReservaExtra usuarioRol) {
        return null;
    }

    @Override
    public ReservaExtra findById(ReservaExtra usuarioRol) {
        return null;
    }
}
