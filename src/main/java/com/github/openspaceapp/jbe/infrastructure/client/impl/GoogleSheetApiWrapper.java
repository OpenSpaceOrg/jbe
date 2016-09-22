package com.github.openspaceapp.jbe.infrastructure.client.impl;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsRequestInitializer;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

class GoogleSheetApiWrapper {
    private Sheets sheets;

    public GoogleSheetApiWrapper(String apikey) throws GeneralSecurityException, IOException {
        this.sheets = getSheetsService(apikey);
    }

    public List<List<Object>> getValues(String spreadsheetId, String range) throws IOException {
        return sheets.spreadsheets().values()
            .get(spreadsheetId, range)
            .execute()
            .getValues();
    }

    private Sheets getSheetsService(String apikey) throws GeneralSecurityException, IOException {
        return new Sheets
            .Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), null)
            .setGoogleClientRequestInitializer(new SheetsRequestInitializer(apikey))
            .setApplicationName("OpenSpaceApi jbe").build();
    }
}
