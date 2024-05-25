package com.donoso.easyflight.servicio;

import com.donoso.easyflight.hibernate.HibernateSessionFactory;
import com.donoso.easyflight.modelo.*;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;

import org.apache.commons.io.IOUtils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CrudReservaService extends HibernateSessionFactory implements CrudServiceInterface<Reserva> {

    private final CrudReservaExtraService reservaExtraService;
    private final CrudReservaViajeroService reservaViajeroService;

    public CrudReservaService() {
        super();
        reservaExtraService = new CrudReservaExtraService();
        reservaViajeroService = new CrudReservaViajeroService();
    }

    @Override
    public void save(Reserva reserva) {
        try {
            session.getTransaction().begin();

            reserva.setFechaReserva(LocalDate.now());
            Reserva newReserva = (Reserva) session.merge(reserva);

            session.getTransaction().commit();

            reserva.getReservaExtras().forEach(reservaExtra -> {
                reservaExtraService.save(new ReservaExtra(new ReservaExtraPK(newReserva.getId(), reservaExtra.getExtra().getId()), newReserva, reservaExtra.getExtra()));
            });

            reserva.getReservaViajeros().forEach(reservaViajero -> {
                reservaViajeroService.save(new ReservaViajero(new ReservaViajeroPK(newReserva.getId(), reservaViajero.getViajero().getId()), newReserva, reservaViajero.getViajero()));
            });

        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
            session.getSessionFactory().close();
        }
    }

    @Override
    public void update(Reserva reserva) {
        try {
            if (this.findById(reserva) != null) {
                this.openSession();
                session.getTransaction().begin();
                session.update(reserva);
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
    public void delete(Reserva reserva) {
        try {
            if (this.findById(reserva) != null) {
                this.openSession();
                session.getTransaction().begin();
                session.delete(reserva);
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
    public List<Reserva> search(Reserva reserva) {
        List<Reserva> reservaList = null;
        try {
            List<Predicate> predicados = new ArrayList<>();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Reserva> criteriaQuery = cb.createQuery(Reserva.class);

            Root<Reserva> reservaRoot = criteriaQuery.from(Reserva.class);
            criteriaQuery.select(reservaRoot);
            Join<Reserva, Usuario> usuarioJoin = reservaRoot.join("usuario", JoinType.INNER);
            Join<Reserva, Vuelo> vueloIdaJoin = reservaRoot.join("vueloIda", JoinType.INNER);
            Join<Reserva, Vuelo> vueloVueltaJoin = reservaRoot.join("vueloVuelta", JoinType.LEFT);
            Join<Reserva, Oferta> ofertaJoin = reservaRoot.join("oferta", JoinType.LEFT);

            if (reserva != null) {
                if (reserva.getId() != null) {
                    predicados.add(cb.equal(reservaRoot.get("id"), reserva.getId()));
                }
                if (reserva.getCode() != null){
                    predicados.add(cb.like(reservaRoot.get("code"), "%".concat(reserva.getCode()).concat("%")));
                }
                if (reserva.getUsuario() != null)
                    predicados.add(cb.equal(usuarioJoin.get("id"), reserva.getUsuario()));
                if (reserva.getVueloIda() != null) {
                    predicados.add(cb.like(vueloIdaJoin.get("id"), "%".concat(reserva.getVueloIda().getId()).concat("%")));
                }
                if (reserva.getVueloVuelta() != null) {
                    predicados.add(cb.like(vueloVueltaJoin.get("id"), "%".concat(reserva.getVueloIda().getId()).concat("%")));
                }
                if (reserva.getOferta() != null) {
                    predicados.add(cb.like(ofertaJoin.get("nombre"), "%".concat(reserva.getOferta().getNombre()).concat("%")));
                }
                if (reserva.getNumPasajeros() != null) {
                    predicados.add(cb.equal(reservaRoot.get("numPasajeros"), reserva.getNumPasajeros()));
                }
                if (reserva.getTotal() != null) {
                    predicados.add(cb.equal(reservaRoot.get("total"), reserva.getTotal()));
                }
            }


            if (!predicados.isEmpty())
                criteriaQuery.where(cb.or(predicados.toArray(new Predicate[0])));

            Query query = session.createQuery(criteriaQuery);
            reservaList = (List<Reserva>) query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
            session.getSessionFactory().close();
        }

        return reservaList;
    }

    @Override
    public Reserva findById(Reserva reserva) {
        Reserva r;
        try {
            r = session.createQuery("from Reserva r where r.id = :id", Reserva.class).setParameter("id", reserva.getId()).uniqueResult();

            session.close();
            session.getSessionFactory().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return r;
    }

    public List<Reserva> findByUsuario(Integer usuario) {
        List<Reserva> reservaList = null;
        try {

            List<Predicate> predicados = new ArrayList<>();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Reserva> criteriaQuery = cb.createQuery(Reserva.class);

            Root<Reserva> reservaRoot = criteriaQuery.from(Reserva.class);
            criteriaQuery.select(reservaRoot);
            Join<Reserva, Usuario> usuarioJoin = reservaRoot.join("usuario", JoinType.INNER);

            if (usuario != null)
                predicados.add(cb.equal(usuarioJoin.get("id"), usuario));

            if (!predicados.isEmpty())
                criteriaQuery.where(cb.or(predicados.toArray(new Predicate[0])));

            Query query = session.createQuery(criteriaQuery);
            reservaList = (List<Reserva>) query.getResultList();



        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            session.close();
            session.getSessionFactory().close();
        }

        return reservaList;
    }

    public InputStream generateQrImage(Integer id){
        Reserva reserva = new Reserva();
        try{
            reserva.setId(id);
            reserva = findById(reserva);
            String data="Código reservar: " + reserva.getCode() + "\n"
                    + "Cliente: " + reserva.getUsuario().getNombre() + " " + reserva.getUsuario().getApellidos() + " " + reserva.getUsuario().getDni() + "\n"
                    + "Origen:  " + reserva.getVueloIda().getOrigen().getNombre() + "\n "
                    + "Destino: " + reserva.getVueloIda().getDestino().getNombre() + "\n"
                    + "Fecha Salida: " + reserva.getVueloIda().getFechaSalida();
            String path="C:/Mercedes/reserva_" + reserva.getCode() + "_" + reserva.getUsuario().getDni() + ".jpg";

            BitMatrix matrix=new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE,500,500);

            MatrixToImageWriter.writeToPath(matrix,"jpg", Paths.get(path));
            final File file = new File(path);
            /*StreamingOutput stream = new StreamingOutput() {
                @Override
                public void write(OutputStream outputStream) throws IOException, WebApplicationException {
                    outputStream.write(IOUtils.toByteArray(new FileInputStream(file)));
                }
            };*/

            return Files.newInputStream(file.toPath());
        }catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
