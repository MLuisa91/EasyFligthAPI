package com.donoso.easyflight.controller;


import com.donoso.easyflight.modelo.Avion;
import com.donoso.easyflight.modelo.Usuario;
import com.donoso.easyflight.servicio.CrudAvionService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/aviones")
public class AvionesController {

    private final CrudAvionService crudAvionService;

    public AvionesController() {
        this.crudAvionService = new CrudAvionService();
    }

    @POST
    @Path("/insertar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveAvion(Avion avion) {
        this.crudAvionService.save(avion);
        return Response.ok(avion, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/search")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchAviones(Avion avion) {
        List<Avion> aviones = this.crudAvionService.search(avion);
        return Response.ok(aviones, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvionById(@PathParam("id") String id) {
        Avion a = new Avion();
        a.setId(id);
        Avion avion = this.crudAvionService.findById(a);
        return Response.ok(avion, MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Path("/actualizar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAvion(Avion avion) {
        this.crudAvionService.update(avion);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAvion(@PathParam("id") String id) {
        Avion a = new Avion();
        a.setId(id);
        this.crudAvionService.delete(a);
        return Response.ok().build();
    }

}
