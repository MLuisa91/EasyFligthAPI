package com.donoso.easyflight.controller;


import com.donoso.easyflight.modelo.Usuario;
import com.donoso.easyflight.servicio.CrudUsuarioService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/usuarios")
public class UsuariosController {

    private final CrudUsuarioService usuarioService;

    public UsuariosController() {
        this.usuarioService = new CrudUsuarioService();
    }

    @POST
    @Path("/insertar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveUsuario(Usuario usuario) {
        this.usuarioService.save(usuario);
        return Response.ok(usuario, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuarioById(@PathParam("id") String id) {
        Usuario u = new Usuario();
        u.setIdDni(id);
        Usuario usuario = this.usuarioService.findById(u);
        return Response.ok(usuario, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{user}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuarioByIdAndPass(@PathParam("user") String user, @PathParam("password") String password) {
        Usuario u = new Usuario();
        u.setUser(user);
        u.setPassword(password);
        Usuario usuario = this.usuarioService.findByUserAndPassword(u);
        return Response.ok(usuario, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/search")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchUsuario(Usuario usuario) {
        List<Usuario> usuarios = this.usuarioService.search(usuario);
        return Response.ok(usuarios, MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Path("/actualizar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUsuario(Usuario usuario) {
        this.usuarioService.update(usuario);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUsuario(@PathParam("id") String id) {
        Usuario u = new Usuario();
        u.setIdDni(id);
        this.usuarioService.delete(u);
        return Response.ok().build();
    }

}
