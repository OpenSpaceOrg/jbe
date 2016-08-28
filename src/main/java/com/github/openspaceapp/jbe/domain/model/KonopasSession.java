package com.github.openspaceapp.jbe.domain.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class KonopasSession {
    private String id;
    private String title;
    private String date;
    private String time;
    private String mins;
    private List<String> tags;
    private List<String> loc;
    private String desc;
    private List<KonopasPerson> people;
}