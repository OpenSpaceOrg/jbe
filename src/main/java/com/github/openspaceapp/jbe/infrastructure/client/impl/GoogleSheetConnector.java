package com.github.openspaceapp.jbe.infrastructure.client.impl;

import com.github.openspaceapp.jbe.infrastructure.client.model.SheetImport;
import com.github.openspaceapp.jbe.infrastructure.client.model.SheetRow;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsRequestInitializer;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Optional<SheetImport> get(String spreadsheetId) throws IOException {
        ValueRange response = getSheetsService().spreadsheets().values()
            .get(spreadsheetId, "A1:T2")
            .execute();
        List<List<Object>> rows = response.getValues();
        List<String> headers = rows.stream()
            .findFirst()
            .map(this::toStringList)
            .orElse(Collections.emptyList());
        rows.remove(0);
        List<SheetRow> sheetRows = rows.stream()
            .map(SheetRow::new)
            .collect(Collectors.toList());
        return Optional.of(new SheetImport(headers, sheetRows));
    }

    private List<String> toStringList(List<Object> objects) {
        return objects.stream()
            .map(x -> (String) x)
            .collect(Collectors.toList());
    }


}