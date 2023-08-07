// Generated by delombok at Mon Aug 07 15:45:02 CEST 2023
package com.github.openspaceapp.jbe.infrastructure.client.impl;

import com.github.openspaceapp.jbe.infrastructure.client.GoogleSheetsApi;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsRequestInitializer;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class GoogleSheetsApiWrapper implements GoogleSheetsApi {
    @SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(GoogleSheetsApiWrapper.class);
    private Sheets sheets;

    @Override
    public void init(String apiKey) throws GeneralSecurityException, IOException {
        log.info("starting with apiKey ending in -{}", apiKey.substring(apiKey.length() - 5));
        this.sheets = getSheetsService(apiKey);
    }

    @Override
    public List<List<Object>> getValues(String spreadsheetId, String range) throws IOException {
        return sheets.spreadsheets().values().get(spreadsheetId, range).execute().getValues();
    }

    private Sheets getSheetsService(String apiKey) throws GeneralSecurityException, IOException {
        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), null).setGoogleClientRequestInitializer(new SheetsRequestInitializer(apiKey)).setApplicationName("OpenSpaceApp jbe").build();
    }
}
