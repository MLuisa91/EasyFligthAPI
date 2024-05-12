package com.donoso.easyflight.controller;

import com.donoso.easyflight.modelo.Viajero;
import com.donoso.easyflight.servicio.CrudViajeroService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/viajeros")
public class ViajerosController {

    private CrudViajeroService crudViajeroService;

    public ViajerosController(){
        this.crudViajeroService = new CrudViajeroService();
    }

    @POST
    @Path("/insertar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveViajero(Viajero viajero) {
        this.crudViajeroService.save(viajero);
        return Response.ok(viajero, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getViajeroById(@PathParam("id") Integer id) {
        Viajero v = new Viajero();
        v.setId(id);
        Viajero viajero = this.crudViajeroService.findById(v);
        return Response.ok(viajero, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/search")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchViajero(Viajero viajero) {
        List<Viajero> viajeros = this.crudViajeroService.search(viajero);
        return Response.ok(viajeros, MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Path("/actualizar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateViajero(Viajero viajero) {
        this.crudViajeroService.update(viajero);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteViajero(@PathParam("id") Integer id) {
        Viajero v = new Viajero();
        v.setId(id);
        this.crudViajeroService.delete(v);
        return Response.ok().build();
    }

}
