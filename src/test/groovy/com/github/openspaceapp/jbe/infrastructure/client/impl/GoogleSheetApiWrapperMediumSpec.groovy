package com.github.openspaceapp.jbe.infrastructure.client.impl

import spock.lang.Specification

class GoogleSheetApiWrapperMediumSpec extends Specification {
    static final String API_KEY = "AIzaSyCsdvwt5ewZCCY1c58B5mwaQK6dA36sSAk"
    static final String SPREADSHEET_FINN_CON = "1Vff_nSuxgiuctd2sCZ19CeZMp8OZgQRUkFweYNktJLs"
    static final String SPREADSHEET_TEST_CON = "1qE68MWfcHFBHNApmUkcSCTA5uGMSTFf4bhJ5191jx5A"
    static final String VALID_SHEET_NAME = "prog"
    static final ALL_HEADERS =
            ["id", "title", "date", "time", "desc", "mins", "loc.0", "tags.0", "tags.1", "tags.2", "people.0.id", "people.0.name", "people.1.id", "people.1.name", "people.2.id", "people.2.name", "people.3.id", "people.3.name", "people.4.id", "people.4.name"]

    def sheetApiWrapper = new GoogleSheetsApiWrapper()

    def "instantiate object without exception"() {
        when:
            sheetApiWrapper.init(API_KEY)
        then:
            notThrown Throwable
    }

    def "get values from existing FinnConn example spreadsheet"() {
        given:
            sheetApiWrapper.init(API_KEY)
        when:
            def values = sheetApiWrapper.getValues(SPREADSHEET_FINN_CON, VALID_SHEET_NAME)
        then:
            notThrown Throwable
            values.size() == 103
            values.get(0).size() == 20
            values.get(0).get(0) == "id"
    }

    def "get values from existing TestCon example spreadsheet"() {
        given:
            sheetApiWrapper.init(API_KEY)
        when:
            def values = sheetApiWrapper.getValues(SPREADSHEET_TEST_CON, VALID_SHEET_NAME)
        then:
            notThrown Throwable
            values.size() == 2
            values.get(0) == ALL_HEADERS
            values.get(1) == ["1", "Test", "01-01-1970", "18:00", "none", "60", "nowhere", "test", "", "", "101", "Jason", "102", "Christian", "103", "TruBlu", "104", "Mx Peper", "105", "Jason Peper"]
    }

    def "expect exception when using invalid apikey"() {
        given:
            sheetApiWrapper.init("NotQuiteCorrectApiKey.Hopefully")
        when:
            sheetApiWrapper.getValues(SPREADSHEET_FINN_CON, VALID_SHEET_NAME)
        then:
            thrown IOException
    }

    def "expect exception when using invalid spreadsheet id"() {
        given:
            sheetApiWrapper.init(API_KEY)
        when:
            sheetApiWrapper.getValues("ProbablyIncorrectSpreadsheetId", VALID_SHEET_NAME)
        then:
            thrown IOException
    }

    def "get exception when using getValues without init"() {
        when:
            sheetApiWrapper.getValues(SPREADSHEET_FINN_CON, VALID_SHEET_NAME)
        then:
            thrown NullPointerException
    }

}
