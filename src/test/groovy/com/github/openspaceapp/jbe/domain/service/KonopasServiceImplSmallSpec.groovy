package com.github.openspaceapp.jbe.domain.service

import com.github.openspaceapp.jbe.domain.mapper.SessionMapper
import com.github.openspaceapp.jbe.domain.model.KonopasSession
import com.github.openspaceapp.jbe.domain.service.impl.KonopasServiceImpl
import com.github.openspaceapp.jbe.domain.port.SheetImporter
import com.github.openspaceapp.jbe.domain.model.SheetImport
import spock.lang.Specification

class KonopasServiceImplSmallSpec extends Specification {
    def final sheetMapper = Mock SessionMapper
    def final sheetImporter = Mock SheetImporter
    def final sheetId = "FUV65FR"
    def final serviceImpl = new KonopasServiceImpl(sheetImporter, sheetMapper, sheetId)

    def "get data from client and map it"() {
        when:
            def program = serviceImpl.getProgram()
        then:
            1 * sheetImporter.get(sheetId) >> Optional.of(new SheetImport([""], []))
            1 * sheetMapper.map(_) >> [KonopasSession.builder().build()]
    }

    def "get run time exception if no sheet imported"() {
        given:
            sheetImporter.get(_) >> Optional.empty()
        when:
            serviceImpl.getProgram()
        then:
            thrown RuntimeException
    }
}
