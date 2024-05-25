package com.donoso.easyflight.controller;

import com.donoso.easyflight.modelo.Oferta;
import com.donoso.easyflight.servicio.CrudOfertaService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/ofertas")
public class OfertasController {

    private CrudOfertaService crudOfertaService;

    public OfertasController() {
        this.crudOfertaService = new CrudOfertaService();
    }

    @POST
    @Path("/insertar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveOferta(Oferta oferta) {
        this.crudOfertaService.save(oferta);
        return Response.ok(oferta, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOferta(@PathParam("id") Integer id) {
        Oferta o = new Oferta();
        o.setId(id);
        Oferta oferta = this.crudOfertaService.findById(o);
        return Response.ok(oferta, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchOferta(Oferta oferta) {
        List<Oferta> ofertas = this.crudOfertaService.search(oferta);
        return Response.ok(ofertas, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/ofertasValidas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchOfertasValidas(Oferta oferta) {
        List<Oferta> ofertas = this.crudOfertaService.searchOfertasValidas(oferta);
        return Response.ok(ofertas, MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Path("/actualizar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOferta(Oferta oferta) {
        this.crudOfertaService.update(oferta);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteOferta(@PathParam("id") Integer id) {
        Oferta o = new Oferta();
        o.setId(id);
        this.crudOfertaService.delete(o);
        return Response.ok().build();
    }

}
