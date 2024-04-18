package com.donoso.easyflight.controller;

import com.donoso.easyflight.modelo.Avion;
import com.donoso.easyflight.modelo.Usuario;
import com.donoso.easyflight.modelo.Vuelo;
import com.donoso.easyflight.servicio.CrudVueloService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/vuelos")
public class VuelosController {

    private final CrudVueloService crudVueloService;


    public VuelosController() {
        this.crudVueloService = new CrudVueloService();
    }

    @POST
    @Path("/insertar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveVuelo(Vuelo vuelo) {
        this.crudVueloService.save(vuelo);
        return Response.ok(vuelo, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVuelo(@PathParam("id") String id) {
        Vuelo v = new Vuelo();
        v.setId(id);
        Vuelo vuelo = this.crudVueloService.findById(v);
        return Response.ok(vuelo, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVuelos() {

        List<Vuelo> vuelos = this.crudVueloService.search(null);
        return Response.ok(vuelos, MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Path("/actualizar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateVuelo(Vuelo vuelo) {
        this.crudVueloService.update(vuelo);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteVuelo(@PathParam("id") String id) {
        Vuelo v = new Vuelo();
        v.setId(id);
        this.crudVueloService.delete(v);
        return Response.ok().build();
    }
}
