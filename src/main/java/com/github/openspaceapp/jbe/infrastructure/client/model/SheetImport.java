package com.github.openspaceapp.jbe.infrastructure.client.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class SheetImport {

    private List<String> headers;
    private List<SheetRow> rows;

}
