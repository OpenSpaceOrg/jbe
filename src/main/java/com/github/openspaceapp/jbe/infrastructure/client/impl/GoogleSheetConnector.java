package com.github.openspaceapp.jbe.infrastructure.client.impl;

import com.github.openspaceapp.jbe.infrastructure.client.GoogleSheetsApi;
import com.github.openspaceapp.jbe.infrastructure.client.SheetImporter;
import com.github.openspaceapp.jbe.infrastructure.client.model.SheetImport;
import com.github.openspaceapp.jbe.infrastructure.client.model.SheetRow;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class GoogleSheetConnector implements SheetImporter {

    private final GoogleSheetsApi sheets;

    public GoogleSheetConnector(GoogleSheetsApi wrapper, String apiKey) {
        try {
            sheets = wrapper;
            sheets.init(apiKey);
        } catch (Throwable t) {
            throw new RuntimeException("Google is down, retreat to underground survival bunker");
        }
    }

    public Optional<SheetImport> get(String spreadsheetId) {
        log.info("reading from spreadsheetId={}", spreadsheetId);
        return getValuesForRange(spreadsheetId, "prog")
            .map(this::createSheetImportFromRow);
    }

    private SheetImport createSheetImportFromRow(List<List<Object>> rows) {
        return new SheetImport(extractHeaders(rows), convertRowsExceptFirst(rows));
    }

    private List<SheetRow> convertRowsExceptFirst(List<List<Object>> rows) {
        return rows.stream()
            .skip(1)
            .map(SheetRow::new)
            .collect(Collectors.toList());
    }

    private List<String> extractHeaders(List<List<Object>> rows) {
        return rows.stream()
            .findFirst()
            .map(this::toStringList)
            .orElse(Collections.emptyList());
    }

    private Optional<List<List<Object>>> getValuesForRange(String spreadsheetId, String range) {
        try {
            return Optional.of(
                sheets.getValues(spreadsheetId, range)
            );
        } catch (IOException e) {
            log.error("cannot read from spreadsheet={}, reason: exception={}", spreadsheetId, e.getMessage());
            return Optional.empty();
        }
    }

    private List<String> toStringList(List<Object> objects) {
        return objects.stream()
            .map(x -> (String) x)
            .collect(Collectors.toList());
    }


}