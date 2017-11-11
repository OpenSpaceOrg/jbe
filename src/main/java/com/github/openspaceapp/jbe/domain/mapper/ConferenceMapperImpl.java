package com.github.openspaceapp.jbe.domain.mapper;

import com.github.openspaceapp.jbe.domain.model.Conference;
import com.github.openspaceapp.jbe.domain.model.Headers;
import com.github.openspaceapp.jbe.domain.model.SheetImport;
import com.github.openspaceapp.jbe.domain.model.SheetRow;

import java.util.List;
import java.util.stream.Collectors;

public class ConferenceMapperImpl implements ConferenceMapper {
    @Override
    public List<Conference> map(SheetImport sheetImport) {
        Headers headers = new Headers(sheetImport.getHeaders());
        return sheetImport.getRows().stream()
                .map(x -> createConference(x, headers))
                .collect(Collectors.toList());
    }

    private Conference createConference(SheetRow sheetRow, Headers headers) {
        return Conference.builder()
                .id(sheetRow.get(headers.get("id")))
                .name(sheetRow.get(headers.get("name")))
                .sheetId(sheetRow.get(headers.get("programSheetId")))
                .build();
    }
}
