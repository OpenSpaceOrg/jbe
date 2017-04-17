package com.github.openspaceapp.jbe.infrastructure.rest.response;

import com.github.openspaceapp.jbe.domain.model.KonopasSession;
import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class KonopasSessionResponse {
    private String id;
    private String title;
    private String date;
    private String time;
    private String mins;
    private List<String> tags;
    private List<String> loc;
    private String desc;
    private List<KonopasPersonResponse> people;

    public KonopasSessionResponse(KonopasSession session) {
        this.id = session.getId();
        this.title = session.getTitle();
        this.date = session.getDate();
        this.time = session.getTime();
        this.mins = session.getMins();
        this.desc = session.getDesc();
        this.tags = Lists.newArrayList(session.getTags());
        this.loc = Lists.newArrayList(session.getLoc());
        this.people = session.getPeople().stream()
                .map(KonopasPersonResponse::new)
                .collect(Collectors.toList());
    }
}