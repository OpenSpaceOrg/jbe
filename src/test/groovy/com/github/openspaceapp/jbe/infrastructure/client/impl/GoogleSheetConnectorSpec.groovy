package com.github.openspaceapp.jbe.infrastructure.client.impl

import com.github.openspaceapp.jbe.infrastructure.client.model.SheetRow
import spock.lang.Specification

class GoogleSheetConnectorSpec extends Specification {

    def "get all values from existing spreadsheet"() {
        setup:
            def sheetConnector = new GoogleSheetConnector("AIzaSyCsdvwt5ewZCCY1c58B5mwaQK6dA36sSAk")
            def spreadsheetId = "1Vff_nSuxgiuctd2sCZ19CeZMp8OZgQRUkFweYNktJLs"
            def expectedHeaders = ["id", "title", "loc.0", "date", "time", "mins", "desc", "tags.0", "tags.1", "tags.2", "people.0.id", "people.0.name", "people.1.id", "people.1.name", "people.2.id", "people.2.name", "people.3.id", "people.3.name", "people.4.id", "people.4.name"]
            def expectedRowZero = new SheetRow(["99", "Tehostemaskeerausnäytös : Näin se tehdään!", "Merikaapelihalli", "2013-07-05", "12:00", "120", "", "", "", "", "92", "Ari Savonen"])
        when:
            def sheetImport = sheetConnector.get(spreadsheetId).get()
        then:
            sheetImport.headers == expectedHeaders
            sheetImport.rows.size() == 102
            sheetImport.rows.get(0) == expectedRowZero
    }

    def "empty optional if exception during import"() {
        setup:
            def sheetConnector = new GoogleSheetConnector("NotQuiteCorrectApiKey.Hopefully")
            def spreadsheetId = "1Vff_nSuxgiuctd2sCZ19CeZMp8OZgQRUkFweYNktJLs"
        expect:
            !sheetConnector.get(spreadsheetId).isPresent()
    }

}
