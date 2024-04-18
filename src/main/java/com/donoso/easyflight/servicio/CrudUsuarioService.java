package com.donoso.easyflight.servicio;

import com.donoso.easyflight.modelo.Usuario;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CrudUsuarioService implements CrudServiceInterface<Usuario> {

    private final org.hibernate.Session session;

    public CrudUsuarioService() {
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        this.session = sf.openSession();
    }

    @Override
    public void save(Usuario usuario) {
        try {
            session.getTransaction().begin();

            session.persist(usuario);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
            session.getSessionFactory().close();
        }
    }

    @Override
    public void update(Usuario usuario) {
        try {
            if (this.findById(usuario) != null) {
                session.getTransaction().begin();
                session.update(usuario);
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
    public void delete(Usuario usuario) {
        try {
            if (this.findById(usuario) != null) {
                session.getTransaction().begin();
                session.delete(usuario);
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
    public List<Usuario> search(Usuario usuario) {
        List<Usuario> usuarioList = null;
        try {
            List<Predicate> predicados = new ArrayList<>();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Usuario> criteriaQuery = cb.createQuery(Usuario.class);

            Root<Usuario> usuarioRoot = criteriaQuery.from(Usuario.class);

            if (usuario.getIdDni() != null)
                predicados.add(cb.equal(usuarioRoot.get("idDni"), usuario.getIdDni()));
            if (usuario.getNombre() != null)
                predicados.add(cb.equal(usuarioRoot.get("nombre"), usuario.getNombre()));
            if (usuario.getApellidos() != null) {
                predicados.add(cb.like(usuarioRoot.get("apellidos"), "%".concat(usuario.getApellidos()).concat("%")));
            }
            if (usuario.getPais() != null) {
                predicados.add(cb.equal(usuarioRoot.get("pais"), usuario.getPais()));
            }
            if (usuario.getEmail() != null) {
                predicados.add(cb.like(usuarioRoot.get("email"), "%".concat(usuario.getEmail()).concat("%")));
            }
            if (usuario.getUser() != null) {
                predicados.add(cb.like(usuarioRoot.get("user"), "%".concat(usuario.getUser()).concat("%")));
            }
            if (usuario.getTelefono() != null) {
                predicados.add(cb.like(usuarioRoot.get("telefono"), "%".concat(usuario.getTelefono()).concat("%")));
            }

            criteriaQuery.select(usuarioRoot);
            criteriaQuery.where(cb.and(predicados.toArray(new Predicate[predicados.size()])));

            Query query = session.createQuery(criteriaQuery);
            usuarioList = (List<Usuario>) query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
            session.getSessionFactory().close();
        }

        return usuarioList;
    }

    public Usuario findByUserAndPassword(Usuario usuario) {
        Usuario user;
        try {
            user = session.createQuery("from Usuario u where u.user = :user and u.password = :password", Usuario.class)
                    .setParameter("user", usuario.getUser())
                    .setParameter("password", usuario.getPassword())
                    .getSingleResult();

            session.close();
            session.getSessionFactory().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    @Override
    public Usuario findById(Usuario usuario) {
        Usuario user;
        try {
            user = session.createQuery("from Usuario u where u.id = :id", Usuario.class).setParameter("id", usuario.getIdDni()).getSingleResult();

            session.close();
            session.getSessionFactory().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return user;
    }
}
