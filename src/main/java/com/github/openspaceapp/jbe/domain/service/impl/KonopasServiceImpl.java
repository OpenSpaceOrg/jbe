package com.github.openspaceapp.jbe.domain.service.impl;

import com.github.openspaceapp.jbe.domain.mapper.SheetMapper;
import com.github.openspaceapp.jbe.domain.model.KonopasPerson;
import com.github.openspaceapp.jbe.domain.model.KonopasSession;
import com.github.openspaceapp.jbe.domain.service.KonopasService;
import com.github.openspaceapp.jbe.infrastructure.client.SheetImporter;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class KonopasServiceImpl implements KonopasService {
    private final SheetImporter sheetImporter;
    private final SheetMapper sheetMapper;
    private final String sheetId;

    @Override
    public List<KonopasSession> getProgram() {
        return Lists.newArrayList(KonopasSession.builder()
            .id("1234")
            .title("A Really Cool Item Title")
            .date("2013-12-24")
            .time("14:30")
            .mins("90")
            .tags(Lists.newArrayList("Some track", "Another track"))
            .desc("Every prögrammé item really ought to have an explanation, unless it's really evident from the title itself what it'll be about.")
            .loc(Lists.newArrayList("Some Room", "Some Area"))
            .people(Lists.newArrayList(
                new KonopasPerson("2345", "Just Sömeguy"),
                new KonopasPerson("4567", "Andhis Friend, Jr.")
            ))
            .build()
        );
    }

    public List<KonopasSession> getProgramWIP() {
        return sheetImporter.get(sheetId)
            .map(sheetMapper::map)
            .orElseThrow(RuntimeException::new);
    }
}