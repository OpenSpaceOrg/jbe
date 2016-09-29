package com.github.openspaceapp.jbe.domain.model;

import lombok.*;

import java.util.List;

@Builder
@Getter
@EqualsAndHashCode
public class KonopasSession {
    @NonNull private String id;
    @NonNull private String title;
    @NonNull private String date;
    private String time;
    private String mins;
    private List<String> tags;
    private List<String> loc;
    private String desc;
    private List<KonopasPerson> people;
}