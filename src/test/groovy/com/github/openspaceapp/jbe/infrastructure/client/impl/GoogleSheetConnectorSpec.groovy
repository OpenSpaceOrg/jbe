package com.github.openspaceapp.jbe.infrastructure.client.impl

import com.github.openspaceapp.jbe.infrastructure.client.model.SheetImport
import com.github.openspaceapp.jbe.infrastructure.client.model.SheetRow
import spock.lang.Specification

class GoogleSheetConnectorSpec extends Specification {

    def sheetConnector = new GoogleSheetConnector("AIzaSyCsdvwt5ewZCCY1c58B5mwaQK6dA36sSAk")

    def "initial connection"() {
        setup:
            def spreadsheetId = "1Vff_nSuxgiuctd2sCZ19CeZMp8OZgQRUkFweYNktJLs"
            def headers = ["id", "title", "loc.0", "date", "time", "mins", "desc", "tags.0", "tags.1", "tags.2", "people.0.id", "people.0.name", "people.1.id", "people.1.name", "people.2.id", "people.2.name", "people.3.id", "people.3.name", "people.4.id", "people.4.name"]
            def rows = [new SheetRow(["99", "Tehostemaskeerausnäytös : Näin se tehdään!", "Merikaapelihalli", "2013-07-05", "12:00", "120", "", "", "", "", "92", "Ari Savonen"])]
        expect:
            sheetConnector.get(spreadsheetId).get() == new SheetImport(headers, rows)
    }

}
