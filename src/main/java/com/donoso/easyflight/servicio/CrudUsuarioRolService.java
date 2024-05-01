package com.donoso.easyflight.servicio;

import com.donoso.easyflight.hibernate.HibernateSessionFactory;
import com.donoso.easyflight.modelo.UsuarioRol;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class CrudUsuarioRolService extends HibernateSessionFactory implements CrudServiceInterface<UsuarioRol> {



    public CrudUsuarioRolService() {
        super();
    }

    @Override
    public void save(UsuarioRol usuarioRol) {
        try {
            openSession();
            session.getTransaction().begin();

            session.save(usuarioRol);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
            session.getSessionFactory().close();
        }
    }

    @Override
    public void update(UsuarioRol usuarioRol) {

    }

    @Override
    public void delete(UsuarioRol usuarioRol) {
        try {
            openSession();
            session.getTransaction().begin();
            session.createQuery("delete from UsuarioRol u where u.usuario.idDni = :id")
                    .setParameter("id", usuarioRol.getId().getUsuarioId())
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
    public List<UsuarioRol> search(UsuarioRol usuarioRol) {
        return null;
    }

    @Override
    public UsuarioRol findById(UsuarioRol usuarioRol) {
        return null;
    }
}
