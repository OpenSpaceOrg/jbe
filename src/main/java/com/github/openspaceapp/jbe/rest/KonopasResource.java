package com.github.openspaceapp.jbe.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
public class KonopasResource {

    @GET
    @Path("/konopas")
    public String get() {
        return "{ \"text\":\"test\"}";
    }

}
