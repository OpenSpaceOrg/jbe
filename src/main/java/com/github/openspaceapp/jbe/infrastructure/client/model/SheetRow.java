package com.github.openspaceapp.jbe.infrastructure.client.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode
public class SheetRow {

    List<String> columns;

    public SheetRow(List<Object> objects) {
        this.columns = objects.stream()
            .map(x -> (String) x)
            .collect(Collectors.toList());
    }
}
