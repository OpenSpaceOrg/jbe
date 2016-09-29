package com.github.openspaceapp.jbe.infrastructure.mapper;

import com.github.openspaceapp.jbe.domain.model.KonopasSession;
import com.github.openspaceapp.jbe.infrastructure.client.model.SheetImport;

import java.util.List;

public interface SheetMapper {
    List<KonopasSession> map(SheetImport sheetImport);
}
