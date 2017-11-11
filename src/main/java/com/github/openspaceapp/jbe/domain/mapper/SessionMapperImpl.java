package com.github.openspaceapp.jbe.domain.mapper;

import com.github.openspaceapp.jbe.domain.model.Headers;
import com.github.openspaceapp.jbe.domain.model.KonopasPerson;
import com.github.openspaceapp.jbe.domain.model.KonopasSession;
import com.github.openspaceapp.jbe.domain.model.MissingHeaderException;
import com.github.openspaceapp.jbe.domain.model.SheetImport;
import com.github.openspaceapp.jbe.domain.model.SheetRow;
import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Wither;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class SessionMapperImpl implements SessionMapper {
    private final List<String> requiredHeaders = Lists.newArrayList("id", "title", "date", "time");

    @Override
    public List<KonopasSession> map(SheetImport sheetImport) {
        Headers headers = new Headers(sheetImport.getHeaders());
        requiredHeaders.forEach(requiredHeader -> throwIfHeaderMissing(headers, requiredHeader));
        return sheetImport.getRows().stream()
                .map(Row::new)
                .map(row -> row.withHeaders(headers))
                .map(Row::createSession)
                .filter(this::isSessionValid)
                .collect(Collectors.toList());
    }

    private boolean isSessionValid(KonopasSession session) {
        if (session == null || session.getTime() == null) {
            log.warn("session or session time is null session={}", session);
            return false;
        }
        if (session.getTime().equals("/")) {
            log.info("session name={}, id={} is marked invalid by time=/",
                    session.getTitle(), session.getId());
            return false;
        }
        return true;

    }

    private void throwIfHeaderMissing(Headers headers, String x) {
        if (!headers.containsKey(x)) {
            throw new MissingHeaderException("Header " + x + " is missing");
        }
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

    KonopasSession createSession() {
        return KonopasSession.builder()
                .id(get("id"))
                .title(get("title"))
                .desc(get("desc"))
                .date(get("date"))
                .time(get("time"))
                .mins(get("mins"))
                .loc(createLocation(this))
                .tags(createTags(this))
                .people(createPeople(this))
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
}


