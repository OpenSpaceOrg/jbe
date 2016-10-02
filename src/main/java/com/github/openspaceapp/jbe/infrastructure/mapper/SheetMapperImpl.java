package com.github.openspaceapp.jbe.infrastructure.mapper;

import com.github.openspaceapp.jbe.application.exception.MissingHeaderException;
import com.github.openspaceapp.jbe.domain.model.KonopasPerson;
import com.github.openspaceapp.jbe.domain.model.KonopasSession;
import com.github.openspaceapp.jbe.infrastructure.client.model.SheetImport;
import com.github.openspaceapp.jbe.infrastructure.client.model.SheetRow;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SheetMapperImpl implements SheetMapper {
    private final List<String> requiredHeaders = Lists.newArrayList("id", "title", "date", "time");

    @Override
    public List<KonopasSession> map(SheetImport sheetImport) {
        Headers headers = new Headers(sheetImport.getHeaders());
        requiredHeaders.forEach(requiredHeader -> throwIfHeaderMissing(headers, requiredHeader));
        return sheetImport.getRows().stream()
            .map(sheetRow -> new Row(sheetRow, headers))
            .map(x -> KonopasSession.builder()
                .id(x.get("id"))
                .title(x.get("title"))
                .desc(x.get("desc"))
                .date(x.get("date"))
                .time(x.get("time"))
                .mins(x.get("mins"))
                .loc(Collections.singletonList(x.get("loc.0")))
                .tags(Lists.newArrayList(
                    x.get("tags.0"),
                    x.get("tags.1"),
                    x.get("tags.2")
                ))
                .people(Lists.newArrayList(
                    new KonopasPerson(
                        x.get("people.0.id"),
                        x.get("people.0.name")
                    ),
                    new KonopasPerson(
                        x.get("people.1.id"),
                        x.get("people.1.name")
                    ),
                    new KonopasPerson(
                        x.get("people.2.id"),
                        x.get("people.2.name")
                    ),
                    new KonopasPerson(
                        x.get("people.3.id"),
                        x.get("people.3.name")
                    ),
                    new KonopasPerson(
                        x.get("people.4.id"),
                        x.get("people.4.name")
                    )
                ))
                .build())
            .collect(Collectors.toList());
    }

    private void throwIfHeaderMissing(Headers headers, String x) {
        if (!headers.containsKey(x)) {
            throw new MissingHeaderException("Header " + x + " is missing");
        }
    }
}

class Headers {
    private Map<String, Integer> headers = Maps.newHashMap();

    Headers(List<String> headerList) {
        int i = 0;
        for (String header : headerList) {
            headers.put(header, i++);
        }
    }

    Integer get(String key) {
        return headers.get(key);
    }

    boolean containsKey(String key) {
        return headers.containsKey(key);
    }
}

@AllArgsConstructor
class Row {
    private final SheetRow sheetRow;
    private final Headers headers;

    String get(String key) {
        return sheetRow.get(headers.get(key));
    }
}