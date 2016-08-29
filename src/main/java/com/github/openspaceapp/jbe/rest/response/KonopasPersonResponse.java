package com.github.openspaceapp.jbe.rest.response;

import com.github.openspaceapp.jbe.domain.model.KonopasPerson;
import lombok.Getter;

@Getter
public class KonopasPersonResponse {
    private String id;
    private String name;

    public KonopasPersonResponse(KonopasPerson person) {
        this.id = person.getId();
        this.name = person.getName();
    }
}