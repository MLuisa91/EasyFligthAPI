package com.donoso.easyflight.controller;

import com.donoso.easyflight.modelo.Reserva;
import com.donoso.easyflight.modelo.Vuelo;
import com.donoso.easyflight.servicio.CrudReservaService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/reservas")
public class ReservasController {

    private final CrudReservaService crudReservaService;


    public ReservasController() {
        this.crudReservaService = new CrudReservaService();
    }

    @POST
    @Path("/insertar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveReserva(Reserva reserva) {
        this.crudReservaService.save(reserva);
        return Response.ok(reserva, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReserva(@PathParam("id") String id) {
        Reserva r = new Reserva();
        r.setId(id);
        Reserva reserva = this.crudReservaService.findById(r);
        return Response.ok(reserva, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReservas() {

        List<Reserva> reservas = this.crudReservaService.search(null);
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
    public Response deleteReserva(@PathParam("id") String id) {
        Reserva r = new Reserva();
        r.setId(id);
        this.crudReservaService.delete(r);
        return Response.ok().build();
    }
}
