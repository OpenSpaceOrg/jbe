package com.github.openspaceapp.jbe.rest;

import com.github.openspaceapp.jbe.domain.model.KonopasSession;
import com.github.openspaceapp.jbe.domain.service.KonopasService;
import lombok.RequiredArgsConstructor;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class KonopasResource {

    private final KonopasService konopasService;

    @GET
    @Path("/konopas")
    public List<KonopasSession> get() {
        return konopasService.getProgram();
    }

}
