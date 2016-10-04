package com.github.openspaceapp.jbe.infrastructure.client.impl

import com.github.openspaceapp.jbe.infrastructure.client.GoogleSheetsApi
import com.github.openspaceapp.jbe.infrastructure.client.model.SheetRow
import spock.lang.Specification

class GoogleSheetConnectorSmallSpec extends Specification {

    def wrapper = Mock(GoogleSheetsApi.class)

    def "get all values from existing spreadsheet"() {
        def ApiKey = "ApiKey"
        setup:
            def spreadsheetId = "SpreadsheetId"
            def expectedHeaders = ["id", "title"]
            def expectedRowZero = new SheetRow(["1", "Title"])
        when:
            def sheetConnector = new GoogleSheetConnector(wrapper, ApiKey)
            def sheetImport = sheetConnector.get(spreadsheetId).get()
        then:
            1 * wrapper.init(ApiKey)
            1 * wrapper.getValues(spreadsheetId, "prog") >> [["id", "title"], ["1", "Title"]]
            sheetImport.headers == expectedHeaders
            sheetImport.rows.size() == 1
            sheetImport.rows.get(0) == expectedRowZero
    }

    def "empty optional if exception during import"() {
        setup:
            def sheetConnector = new GoogleSheetConnector(wrapper, "NotQuiteCorrectApiKey.Hopefully")
            wrapper.getValues(*_) >> { throw new IOException() }
        expect:
            !sheetConnector.get("empty").isPresent()
    }

    def "expect exception if wrapper throws expection on init"() {
        setup:
            wrapper.init(_) >> { throw new IOException() }
        when:
            def sheetConnector = new GoogleSheetConnector(wrapper, "NotQuiteCorrectApiKey.Hopefully")
        then:
            thrown RuntimeException
    }

}