package com.donoso.easyflight.servicio;

import com.donoso.easyflight.hibernate.HibernateSessionFactory;
import com.donoso.easyflight.modelo.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class CrudUsuarioService extends HibernateSessionFactory implements CrudServiceInterface<Usuario> {

    private CrudUsuarioRolService crudUsuarioRolService;
    public CrudUsuarioService(){
        super();
        this.crudUsuarioRolService = new CrudUsuarioRolService();
    }

    @Override
    public void save(Usuario usuario) {
        try {
            Usuario user = null;
            session.getTransaction().begin();
            user= (Usuario) session.merge(usuario);
            session.getTransaction().commit();

            Usuario finalUser = user;
            usuario.getUsuarioRol().forEach(usuarioRol -> {
                crudUsuarioRolService.save(new UsuarioRol(new UsuarioRolPK(finalUser.getId(), usuarioRol.getRol().getId()), finalUser,new Rol(usuarioRol.getRol().getId(),null,null)));
            });

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
            Usuario oldUser = this.findById(usuario);
            if (oldUser != null) {
                this.openSession();
                session.getTransaction().begin();
                session.update(usuario);
                session.getTransaction().commit();

                oldUser.getUsuarioRol().forEach(usuarioRol -> {
                    crudUsuarioRolService.delete(usuarioRol);
                });

                usuario.getUsuarioRol().forEach(usuarioRol -> {
                    crudUsuarioRolService.save(new UsuarioRol(usuarioRol.getId(),usuario,new Rol(usuarioRol.getId().getRolId(),null,null)));
                });

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
            usuario = this.findById(usuario);

            if (usuario != null) {
                usuario.getUsuarioRol().forEach(usuarioRol -> {
                    crudUsuarioRolService.delete(usuarioRol);
                });
                this.openSession();
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
            criteriaQuery.select(usuarioRoot);
            Join<Usuario, Pais> paisJoin =  usuarioRoot.join("pais", JoinType.INNER);
            if(usuario!=null) {
                if (usuario.getDni() != null)
                    predicados.add(cb.equal(usuarioRoot.get("dni"), usuario.getDni()));
                if (usuario.getNombre() != null)
                    predicados.add(cb.equal(usuarioRoot.get("nombre"), usuario.getNombre()));
                if (usuario.getApellidos() != null) {
                    predicados.add(cb.like(usuarioRoot.get("apellidos"), "%".concat(usuario.getApellidos()).concat("%")));
                }
                if (usuario.getPais() != null) {
                    predicados.add(cb.like(paisJoin.get("nombre"), "%".concat(usuario.getPais().getNombre()).concat("%")));
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
            }

            if(!predicados.isEmpty())
                criteriaQuery.where(cb.or(predicados.toArray(new Predicate[0])));

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
            user = session.
                    createQuery("from Usuario u where u.id = :id", Usuario.class).setParameter("id", usuario.getId()).uniqueResult();

            session.close();
            session.getSessionFactory().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return user;
    }
}
