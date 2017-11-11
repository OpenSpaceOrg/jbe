package com.github.openspaceapp.jbe.domain.mapper;

import com.github.openspaceapp.jbe.domain.model.KonopasSession;
import com.github.openspaceapp.jbe.domain.model.SheetImport;

import java.util.List;

public interface SessionMapper {
    List<KonopasSession> map(SheetImport sheetImport);
}
