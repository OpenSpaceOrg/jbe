package com.github.openspaceapp.jbe.domain.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Builder
@EqualsAndHashCode
@ToString
public class Conference {
    private String id;
    private String name;
    private String sheetId;
}
