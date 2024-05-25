package com.donoso.easyflight.controller;


import com.donoso.easyflight.modelo.Respaldo;
import com.donoso.easyflight.servicio.CrudBackupService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Path("/respaldos")
public class BackupController {

    private final CrudBackupService crudBackupService;

    public BackupController() {
        this.crudBackupService = new CrudBackupService();
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBackup() {
        Timer timer = new Timer();
        TimerTask ejecuta = new TimerTask () {
            @Override
            public void run () {
                crudBackupService.save(null);
            }
        };
        timer.schedule(ejecuta,1,  1000*60);

        return Response.ok().build();
    }

    @GET
    @Path("/restore/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response restoreBackup(@PathParam("id") Integer id) throws IOException {
        Respaldo respaldo = new Respaldo();
        respaldo.setId(id);
        crudBackupService.update(respaldo);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRespaldoById(@PathParam("id") Integer id) {
        Respaldo r = new Respaldo();
        r.setId(id);
        Respaldo respaldo = this.crudBackupService.findById(r);
        return Response.ok(respaldo, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/search")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchBackup(Respaldo respaldo) {
        List<Respaldo> respaldos = this.crudBackupService.search(respaldo);
        return Response.ok(respaldos, MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBackup(@PathParam("id") Integer id) {
        Respaldo r = new Respaldo();
        r.setId(id);
        this.crudBackupService.delete(r);
        return Response.ok().build();
    }


}
