package com.donoso.easyflight.controller;

import com.donoso.easyflight.modelo.Pais;
import com.donoso.easyflight.modelo.Usuario;
import com.donoso.easyflight.servicio.CrudPaisesService;
import com.donoso.easyflight.servicio.CrudRolService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/paises")
public class PaisesController {

    private final CrudPaisesService crudPaisesService;

    public PaisesController(){
        this.crudPaisesService = new CrudPaisesService();
    }

    @POST
    @Path("/search")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchPais(Pais pais) {
        List<Pais> paises = this.crudPaisesService.search(pais);
        return Response.ok(paises, MediaType.APPLICATION_JSON).build();
    }

}
