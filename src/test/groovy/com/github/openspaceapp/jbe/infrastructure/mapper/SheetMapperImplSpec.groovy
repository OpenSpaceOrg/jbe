package com.github.openspaceapp.jbe.infrastructure.mapper

import com.github.openspaceapp.jbe.application.exception.MissingHeaderException
import com.github.openspaceapp.jbe.domain.model.KonopasPerson
import com.github.openspaceapp.jbe.domain.model.KonopasSession
import com.github.openspaceapp.jbe.infrastructure.client.model.SheetImport
import com.github.openspaceapp.jbe.infrastructure.client.model.SheetRow
import spock.lang.Specification
import spock.lang.Unroll

class SheetMapperImplSpec extends Specification {
    static requiredHeaders = ["id", "title", "date", "time"]
    static allHeaders = ["id", "title", "date", "time", "desc", "mins", "loc.0", "tags.0", "tags.1", "tags.2", "people.0.id", "people.0.name", "people.1.id", "people.1.name", "people.2.id", "people.2.name", "people.3.id", "people.3.name", "people.4.id", "people.4.name"]

    def "convert table with one full row"() {
        setup:
            def sheetImport = new SheetImport(
                    allHeaders,
                    [new SheetRow(["1", "title", "2016-01-20", "12:00", "a description", "45", "location",
                                   "tag1", "tag2", "tag3",
                                   "101", "Jason",
                                   "102", "Christian",
                                   "103", "TruBlu",
                                   "104", "Mx Peper",
                                   "105", "Jason Peper"])])
            def expectedKonopasSession = KonopasSession.builder()
                    .id("1")
                    .title("title")
                    .date("2016-01-20")
                    .desc("a description")
                    .time("12:00")
                    .mins("45")
                    .loc(["location"])
                    .tags(["tag1", "tag2", "tag3"])
                    .people([new KonopasPerson("101", "Jason"),
                             new KonopasPerson("102", "Christian"),
                             new KonopasPerson("103", "TruBlu"),
                             new KonopasPerson("104", "Mx Peper"),
                             new KonopasPerson("105", "Jason Peper")])
                    .build()
        expect:
            new SheetMapperImpl().map(sheetImport) == [expectedKonopasSession]
    }

    @Unroll
    def "abort if field #header is missing in Header"() {
        setup:
            def headers = requiredHeaders.clone() as List
            headers.remove(header)
            def sheetImport = new SheetImport(headers, [])
        when:
            new SheetMapperImpl().map(sheetImport)
        then:
            def ex = thrown MissingHeaderException
            ex.getMessage() == "Header " + header + " is missing"
        where:
            header << requiredHeaders
    }
}
