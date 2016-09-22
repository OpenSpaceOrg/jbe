package com.github.openspaceapp.jbe.infrastructure.client.impl;

import com.github.openspaceapp.jbe.infrastructure.client.model.SheetImport;
import com.github.openspaceapp.jbe.infrastructure.client.model.SheetRow;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsRequestInitializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class GoogleSheetConnector {

    private final String apiKey;
    private HttpTransport HTTP_TRANSPORT;

    public GoogleSheetConnector(String apiKey) {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Throwable t) {
            System.exit(1);
        }
        this.apiKey = apiKey;
    }

    private Sheets getSheetsService() throws IOException {
        return new Sheets
            .Builder(HTTP_TRANSPORT, JacksonFactory.getDefaultInstance(), null)
            .setGoogleClientRequestInitializer(new SheetsRequestInitializer(apiKey))
            .setApplicationName("OpenSpaceApi jbe").build();
    }

    public Optional<SheetImport> get(String spreadsheetId) {
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
                getSheetsService().spreadsheets().values()
                    .get(spreadsheetId, range)
                    .execute()
                    .getValues()
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