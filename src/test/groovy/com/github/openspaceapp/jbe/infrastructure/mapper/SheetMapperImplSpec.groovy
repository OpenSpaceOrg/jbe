package com.github.openspaceapp.jbe.infrastructure.mapper

import com.github.openspaceapp.jbe.application.exception.MissingHeaderException
import com.github.openspaceapp.jbe.domain.model.KonopasSession
import com.github.openspaceapp.jbe.infrastructure.client.model.SheetImport
import com.github.openspaceapp.jbe.infrastructure.client.model.SheetRow
import spock.lang.Specification
import spock.lang.Unroll

class SheetMapperImplSpec extends Specification {
    static requiredHeaders = ["id", "title", "date"]
    static allHeaders =  ["id", "title", "date", "desc"]

    def "convert table with title&id"() {
        setup:
            def sheetImport = new SheetImport(
                    allHeaders,
                    [new SheetRow(["1", "title", "2016-01-20", "a description"])])
            def expectedKonopasSession = KonopasSession.builder()
                    .id("1")
                    .title("title")
                    .date("2016-01-20")
                    .desc("a description")
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
