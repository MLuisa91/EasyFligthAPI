package com.donoso.easyflight.controller;


import com.donoso.easyflight.modelo.Rol;
import com.donoso.easyflight.servicio.CrudRolService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/roles")
public class RolController {

    private CrudRolService crudRolService;

    public RolController(){
        this.crudRolService = new CrudRolService();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRolById(@PathParam("id") Integer id) {
        Rol r = new Rol();
        r.setId(id);
        Rol rol = this.crudRolService.findById(r);
        return Response.ok(rol, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchRol(Rol rol) {
        List<Rol> roles = this.crudRolService.search(rol);
        return Response.ok(roles, MediaType.APPLICATION_JSON).build();
    }


}
