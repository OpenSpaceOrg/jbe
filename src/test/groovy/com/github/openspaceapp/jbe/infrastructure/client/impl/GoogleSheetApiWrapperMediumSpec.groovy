package com.github.openspaceapp.jbe.infrastructure.client.impl

import spock.lang.Specification

class GoogleSheetApiWrapperMediumSpec extends Specification {
    public static final String APIKEY = "AIzaSyCsdvwt5ewZCCY1c58B5mwaQK6dA36sSAk"
    public static final String VALID_SPREADSHEET = "1Vff_nSuxgiuctd2sCZ19CeZMp8OZgQRUkFweYNktJLs"
    public static final String VALID_SHEET_NAME = "prog"

    def sheetApiWrapper = new GoogleSheetsApiWrapper()

    def "instantiate object without exception"() {
        when:
            sheetApiWrapper.init(APIKEY)
        then:
            notThrown Throwable
    }

    def "get values from an existing spreadsheet"() {
        given:
            sheetApiWrapper.init(APIKEY)
        when:
            def values = sheetApiWrapper.getValues(VALID_SPREADSHEET, VALID_SHEET_NAME)
        then:
            notThrown Throwable
            values.size() == 103
            values.get(0).size() == 20
            values.get(0).get(0) == "id"
    }

    def "expect exception when using invalid apikey"() {
        given:
            sheetApiWrapper.init("NotQuiteCorrectApiKey.Hopefully")
        when:
            sheetApiWrapper.getValues(VALID_SPREADSHEET, VALID_SHEET_NAME)
        then:
            thrown IOException
    }

    def "expect exception when using invalid spreadsheet id"() {
        given:
            sheetApiWrapper.init(APIKEY)
        when:
            sheetApiWrapper.getValues("ProbablyIncorrectSpreadsheetId", VALID_SHEET_NAME)
        then:
            thrown IOException
    }

    def "get exception when using getValues without init"() {
        when:
            sheetApiWrapper.getValues(VALID_SPREADSHEET, VALID_SHEET_NAME)
        then:
            thrown NullPointerException
    }

}
