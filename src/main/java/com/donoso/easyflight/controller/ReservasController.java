package com.donoso.easyflight.controller;

import com.donoso.easyflight.modelo.Reserva;
import com.donoso.easyflight.modelo.Usuario;
import com.donoso.easyflight.modelo.Vuelo;
import com.donoso.easyflight.servicio.CrudReservaService;
import com.google.zxing.WriterException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Path("/reservas")
public class ReservasController {

    private final CrudReservaService crudReservaService;


    public ReservasController() {
        this.crudReservaService = new CrudReservaService();
    }

    @POST
    @Path("/insertar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveReserva(Reserva reserva) {
        this.crudReservaService.save(reserva);
        return Response.ok(reserva, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReserva(@PathParam("id") Integer id) {
        Reserva r = new Reserva();
        r.setId(id);
        Reserva reserva = this.crudReservaService.findById(r);
        return Response.ok(reserva, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/search")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchReservas(Reserva reserva) {
        List<Reserva> reservas = this.crudReservaService.search(reserva);
        return Response.ok(reservas, MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Path("/actualizar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateReserva(Reserva reserva) {
        this.crudReservaService.update(reserva);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteReserva(@PathParam("id") Integer id) {
        Reserva r = new Reserva();
        r.setId(id);
        this.crudReservaService.delete(r);
        return Response.ok().build();
    }

    @GET
    @Path("/reservas-user/{usuario}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReservaByUsuario(@PathParam("usuario") Integer usuario) {
        List<Reserva> r = this.crudReservaService.findByUsuario(usuario);
        return Response.ok(r, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/generateQr/{id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generarQrReservas(@PathParam("id") Integer id) {
        InputStream file = this.crudReservaService.generateQrImage(id);
        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=qr.jpg")
                .build();
    }
}
