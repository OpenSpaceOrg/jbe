package com.github.openspaceapp.jbe.domain.mapper

import com.github.openspaceapp.jbe.domain.model.Conference
import com.github.openspaceapp.jbe.domain.model.SheetImport
import com.github.openspaceapp.jbe.domain.model.SheetRow
import spock.lang.Specification

class ConferenceMapperSmallSpec extends Specification {
    def "map single conference to conference object"() {
        given:
            def headers = ["id", "name", "programSheetId"]
            def rows = [new SheetRow(["1", "SoCraTes Belgium", "sheetId6573428195"])]
            def sheetImport = new SheetImport(headers, rows)
        when:
            ConferenceMapper mapperImpl = new ConferenceMapperImpl()
            def output = mapperImpl.map(sheetImport)
        then:
            def conferences = Conference.builder()
                    .id("1")
                    .name("SoCraTes Belgium")
                    .sheetId("sheetId6573428195")
                    .build()
            [conferences] == output
    }
}
