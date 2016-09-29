package com.github.openspaceapp.jbe.infrastructure.mapper;

import com.github.openspaceapp.jbe.application.exception.MissingHeaderException;
import com.github.openspaceapp.jbe.domain.model.KonopasSession;
import com.github.openspaceapp.jbe.infrastructure.client.model.SheetImport;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SheetMapperImpl implements SheetMapper {
    private final List<String> requiredHeaders = Lists.newArrayList("id", "title", "date");

    @Override
    public List<KonopasSession> map(SheetImport sheetImport) {
        int i = 0;
        Map<String, Integer> headers = Maps.newHashMap();
        for (String header : sheetImport.getHeaders()) {
            headers.put(header, i++);
        }
        requiredHeaders.forEach(requiredHeader -> throwIfHeaderMissing(headers, requiredHeader));
        return sheetImport.getRows().stream()
            .map(x -> KonopasSession.builder()
                .id(x.get(headers.get("id")))
                .title(x.get(headers.get("title")))
                .desc(x.get(headers.get("desc")))
                .date(x.get(headers.get("date")))
                .build())
            .collect(Collectors.toList());
    }

    private void throwIfHeaderMissing(Map<String, Integer> headers, String x) {
        if (!headers.containsKey(x)) {
            throw new MissingHeaderException("Header " + x + " is missing");
        }
    }
}
