package com.github.openspaceapp.jbe.domain.service.impl;

import com.github.openspaceapp.jbe.domain.mapper.SheetMapper;
import com.github.openspaceapp.jbe.domain.model.KonopasSession;
import com.github.openspaceapp.jbe.domain.service.KonopasService;
import com.github.openspaceapp.jbe.domain.port.SheetImporter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class KonopasServiceImpl implements KonopasService {
    private final SheetImporter sheetImporter;
    private final SheetMapper sheetMapper;
    private final String sheetId;

    @Override
    public List<KonopasSession> getProgram() {
        return sheetImporter.get(sheetId)
            .map(sheetMapper::map)
            .orElseThrow(RuntimeException::new);
    }
}
