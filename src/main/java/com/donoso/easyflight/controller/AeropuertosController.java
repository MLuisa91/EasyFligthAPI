package com.donoso.easyflight.controller;

import com.donoso.easyflight.modelo.Aeropuerto;
import com.donoso.easyflight.servicio.CrudAeropuertoService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/aeropuertos")
public class AeropuertosController {

    private final CrudAeropuertoService crudAeropuertoService;

    public AeropuertosController() {
        this.crudAeropuertoService = new CrudAeropuertoService();
    }

    @POST
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchAeropuertos(Aeropuerto aeropuerto) {
        List<Aeropuerto> aeropuertos = this.crudAeropuertoService.search(null);
        return Response.ok(aeropuertos, MediaType.APPLICATION_JSON).build();
    }


}
