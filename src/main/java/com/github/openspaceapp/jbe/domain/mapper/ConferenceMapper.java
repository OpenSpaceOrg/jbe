package com.github.openspaceapp.jbe.domain.mapper;

import com.github.openspaceapp.jbe.domain.model.Conference;
import com.github.openspaceapp.jbe.domain.model.SheetImport;

import java.util.List;

public interface ConferenceMapper {
    List<Conference> map(SheetImport sheetImport);
}
