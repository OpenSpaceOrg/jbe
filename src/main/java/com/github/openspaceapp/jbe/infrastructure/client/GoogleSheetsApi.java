package com.github.openspaceapp.jbe.infrastructure.client;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface GoogleSheetsApi {

    void init(String apiKey) throws GeneralSecurityException, IOException;

    List<List<Object>> getValues(String spreadsheetId, String range) throws IOException;
}
