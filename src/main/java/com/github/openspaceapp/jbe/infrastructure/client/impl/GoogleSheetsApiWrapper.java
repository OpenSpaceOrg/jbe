package com.github.openspaceapp.jbe.infrastructure.client.impl;

import com.github.openspaceapp.jbe.infrastructure.client.GoogleSheetsApi;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsRequestInitializer;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

class GoogleSheetsApiWrapper implements GoogleSheetsApi {
    private Sheets sheets;

    @Override
    public void init(String apiKey) throws GeneralSecurityException, IOException {
        this.sheets = getSheetsService(apiKey);
    }

    @Override
    public List<List<Object>> getValues(String spreadsheetId, String range) throws IOException {
        return sheets.spreadsheets().values()
            .get(spreadsheetId, range)
            .execute()
            .getValues();
    }

    private Sheets getSheetsService(String apiKey) throws GeneralSecurityException, IOException {
        return new Sheets
            .Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), null)
            .setGoogleClientRequestInitializer(new SheetsRequestInitializer(apiKey))
            .setApplicationName("OpenSpaceApi jbe").build();
    }
}
