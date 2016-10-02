package com.github.openspaceapp.jbe.domain.model;

import lombok.*;

import java.util.List;

@Builder
@Getter
@EqualsAndHashCode
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

/*
id	title	loc.0	date	time	mins    desc
tags.0	tags.1	tags.2
people.0.id	people.0.name
people.1.id	people.1.name
people.2.id	people.2.name
people.3.id	people.3.name
people.4.id	people.4.name
	*/