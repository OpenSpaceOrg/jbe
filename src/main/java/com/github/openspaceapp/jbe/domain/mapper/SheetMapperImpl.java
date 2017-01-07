package com.github.openspaceapp.jbe.domain.mapper;

import com.github.openspaceapp.jbe.application.exception.MissingHeaderException;
import com.github.openspaceapp.jbe.domain.model.KonopasPerson;
import com.github.openspaceapp.jbe.domain.model.KonopasSession;
import com.github.openspaceapp.jbe.infrastructure.client.model.SheetImport;
import com.github.openspaceapp.jbe.infrastructure.client.model.SheetRow;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Wither;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SheetMapperImpl implements SheetMapper {
    private final List<String> requiredHeaders = Lists.newArrayList("id", "title", "date", "time");

    @Override
    public List<KonopasSession> map(SheetImport sheetImport) {
        Headers headers = new Headers(sheetImport.getHeaders());
        requiredHeaders.forEach(requiredHeader -> throwIfHeaderMissing(headers, requiredHeader));
        return sheetImport.getRows().stream()
            .map(Row::new)
            .map(row -> row.withHeaders(headers))
            .map(this::createSession)
            .collect(Collectors.toList());
    }

    private KonopasSession createSession(Row row) {
        return KonopasSession.builder()
            .id(row.get("id"))
            .title(row.get("title"))
            .desc(row.get("desc"))
            .date(row.get("date"))
            .time(row.get("time"))
            .mins(row.get("mins"))
            .loc(createLocation(row))
            .tags(createTags(row))
            .people(createPeople(row))
            .build();
    }

    private List<String> createLocation(Row row) {
        return Collections.singletonList(row.get("loc.0"));
    }

    private List<String> createTags(Row row) {
        return Lists.newArrayList(
            row.get("tags.0"),
            row.get("tags.1"),
            row.get("tags.2")
        );
    }

    private List<KonopasPerson> createPeople(Row row) {
        return IntStream.rangeClosed(0, 4).
            mapToObj(index -> getPersonForIndex(index, row))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    private KonopasPerson getPersonForIndex(int index, Row row) {
        return KonopasPerson.of(
            row.get("people." + index + ".id"),
            row.get("people." + index + ".name")
        );
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

@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class Row {
    private final SheetRow sheetRow;
    @Wither
    private Headers headers;

    String get(String key) {
        return sheetRow.get(headers.get(key));
    }
}