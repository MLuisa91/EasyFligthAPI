package com.donoso.easyflight.servicio;

import com.donoso.easyflight.configuration.EnvironmentConfiguration;
import com.donoso.easyflight.hibernate.HibernateSessionFactory;
import com.donoso.easyflight.modelo.Extra;
import com.donoso.easyflight.modelo.Respaldo;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class CrudBackupService extends HibernateSessionFactory implements CrudServiceInterface<Respaldo> {

    private EnvironmentConfiguration configuration;

    public CrudBackupService() {
        super();
        configuration = new EnvironmentConfiguration();
    }

    @Override
    public void save(Respaldo object) {


        if(Boolean.getBoolean(configuration.loadPropertie("ACTIVE_BACKUP"))){
            try {
                this.openSession();
                String file = this.generarBackup();
                Boolean generada = file != null;
                Respaldo respaldo = new Respaldo(null, file, LocalDate.now(), null, generada);
                session.getTransaction().begin();

                session.save(respaldo);

                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            } finally {
                session.close();
                session.getSessionFactory().close();
            }
        }


    }

    /**
     * Método que nos ayudará a realizar una copia de seguridad de la base de datos
     *
     * @return
     */
    private String generarBackup() {

        try {
            String nombreFile = LocalDateTime.now().getNano()  + configuration.loadPropertie("NOMBRE_FICHERO") ;
            // Ejecutamos el proceso mysql que realizará la copia de seguridad
            Process p = Runtime.getRuntime().exec(configuration.loadPropertie("DIRECTORIO_MYSQL") + "/mysqldump -u " + configuration.loadPropertie("USER") + " --ignore-table=vuelosdb.respaldo " + configuration.loadPropertie("DB"));

            InputStream inputStream = p.getInputStream();
            // creamos el fichero sql para guardar dentro el backup de base de datos
            FileOutputStream fos = new FileOutputStream(configuration.loadPropertie("DIRECTORIO_FICHERO") + nombreFile);
            byte[] buffer = new byte[1000];

            int contenido = inputStream.read(buffer);
            while (contenido > 0) {
                fos.write(buffer, 0, contenido);
                contenido = inputStream.read(buffer);
            }

            fos.close();//Cierra respaldo
            return nombreFile;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void update(Respaldo respaldo) {
        try {
            Respaldo result = session.createQuery("from Respaldo r where r.id = :id", Respaldo.class).setParameter("id", respaldo.getId()).uniqueResult();

            session.close();
            session.getSessionFactory().close();

            this.restore(result);
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
            session.getSessionFactory().close();
        }
    }

    /**
     * Método que nos ayuda a restaurar una copia de seguridad
     *
     * @param respaldo
     * @return
     */
    private String restore(Respaldo respaldo) {
        try {
            // Ejecutamos el proceso mysql que realizará el restaurado de la copia de seguridad
            Process process = Runtime.getRuntime().exec(configuration.loadPropertie("DIRECTORIO_MYSQL") + "/mysql -u " + configuration.loadPropertie("USER") + " " + configuration.loadPropertie("DB"));

            // leemos el fichero
            OutputStream salida = process.getOutputStream();
            FileInputStream inputStream = new FileInputStream(configuration.loadPropertie("DIRECTORIO_FICHERO") + respaldo.getNombre());
            byte[] buffer = new byte[1000];

            int leido = inputStream.read(buffer);
            while (leido > 0) {
                salida.write(buffer, 0, leido);
                leido = inputStream.read(buffer);
            }

            // cerramos el buffer y el fichero
            salida.flush();
            salida.close();
            inputStream.close();
            return configuration.loadPropertie("NOMBRE_FICHERO");
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void delete(Respaldo respaldo) {
        try {
            Respaldo result = session.createQuery("from Respaldo r where r.id = :id", Respaldo.class).setParameter("id", respaldo.getId()).uniqueResult();
            session.getTransaction().begin();
            session.delete(result);
            session.getTransaction().commit();
            session.close();
            session.getSessionFactory().close();

            this.restore(result);
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
            session.getSessionFactory().close();
        }


    }

    @Override
    public List<Respaldo> search(Respaldo respaldo) {
        List<Respaldo> respaldoList = null;
        try {
            List<Predicate> predicados = new ArrayList<>();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Respaldo> criteriaQuery = cb.createQuery(Respaldo.class);

            Root<Respaldo> respaldoRoot = criteriaQuery.from(Respaldo.class);

            if (respaldo != null) {
                if (respaldo.getId() != null) {
                    predicados.add(cb.equal(respaldoRoot.get("id"), respaldo.getId()));
                }
                if (respaldo.getNombre() != null) {
                    predicados.add(cb.equal(respaldoRoot.get("nombre"), respaldo.getNombre()));
                }
                if (respaldo.getFechaGenerada() != null) {
                    predicados.add(cb.equal(respaldoRoot.get("fechaGenerada"), respaldo.getFechaGenerada()));
                }
                if (respaldo.getFechaRestaurada() != null) {
                    predicados.add(cb.equal(respaldoRoot.get("fechaRestaurada"), respaldo.getFechaRestaurada()));
                }
            }

            criteriaQuery.select(respaldoRoot);
            criteriaQuery.where(cb.and(predicados.toArray(new Predicate[predicados.size()])));

            Query query = session.createQuery(criteriaQuery);
            respaldoList = (List<Respaldo>) query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
            session.getSessionFactory().close();
        }

        return respaldoList;
    }

    @Override
    public Respaldo findById(Respaldo respaldo) {
        Respaldo r;
        try {
            r = session.createQuery("from Respaldo r where r.id = :id", Respaldo.class).setParameter("id", respaldo.getId()).uniqueResult();

            session.close();
            session.getSessionFactory().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return r;
    }

;

}
