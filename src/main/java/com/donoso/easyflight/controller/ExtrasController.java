package com.donoso.easyflight.controller;


import com.donoso.easyflight.modelo.Extra;
import com.donoso.easyflight.servicio.CrudExtraService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/extras")
public class ExtrasController {

    private CrudExtraService crudExtraService;

    public ExtrasController() {
        this.crudExtraService = new CrudExtraService();
    }

    @POST
    @Path("/insertar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveExtra(Extra extra) {
        this.crudExtraService.save(extra);
        return Response.ok(extra, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getExtra(@PathParam("id") Integer id) {
        Extra e = new Extra();
        e.setId(id);
        Extra extra = this.crudExtraService.findById(e);
        return Response.ok(extra, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchExtra(Extra extra) {
        List<Extra> extras = this.crudExtraService.search(extra);
        return Response.ok(extras, MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Path("/actualizar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateExtra(Extra extra) {
        this.crudExtraService.update(extra);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteExtra(@PathParam("id") Integer id) {
        Extra e = new Extra();
        e.setId(id);
        this.crudExtraService.delete(e);
        return Response.ok().build();
    }

}
