package com.github.openspaceapp.jbe.infrastructure.rest;

import com.github.openspaceapp.jbe.domain.service.KonopasService;
import com.github.openspaceapp.jbe.infrastructure.rest.response.KonopasSessionResponse;
import lombok.RequiredArgsConstructor;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class KonopasResource {

    private final KonopasService konopasService;

    @GET
    @Path("/konopas")
    public List<KonopasSessionResponse> get() {
        return konopasService.getProgram()
                .stream()
                .map(KonopasSessionResponse::new)
                .collect(Collectors.toList());
    }

}
