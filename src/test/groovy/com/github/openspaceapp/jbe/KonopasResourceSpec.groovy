package com.github.openspaceapp.jbe

import com.github.openspaceapp.jbe.application.OpenSpaceApplication
import com.github.openspaceapp.jbe.application.OpenSpaceConfiguration
import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient
import io.dropwizard.testing.ConfigOverride
import io.dropwizard.testing.DropwizardTestSupport
import io.dropwizard.testing.ResourceHelpers
import spock.lang.Specification

class KonopasResourceSpec extends Specification {

    public static final DropwizardTestSupport<OpenSpaceConfiguration> SUPPORT =
            new DropwizardTestSupport<OpenSpaceConfiguration>(OpenSpaceApplication.class,
                    ResourceHelpers.resourceFilePath("openspaceapp.yaml"),
                    ConfigOverride.config("server.connector.port", "0")
            );

    def setup() {
        SUPPORT.before()
    }

    def "get static return for /v1/konopas"() {
        setup:
            def client = new RESTClient("http://localhost:" + SUPPORT.getLocalPort())
        when:
            def response = client.get(path: "/v1/konopas") as HttpResponseDecorator
        then:
            response.status == 200
            response.contentType == ContentType.JSON.toString()
            response.data == [
                    [
                            "id"    : "1",
                            "title" : "Test",
                            "tags"  : ["test", "", ""],
                            "date"  : "01-01-1970",
                            "time"  : "18:00",
                            "mins"  : "60",
                            "loc"   : ["nowhere"],
                            "people": [
                                    [id: "101", name: "Jason"],
                                    [id: "102", name: "Christian"],
                                    [id: "103", name: "TruBlu"],
                                    [id: "104", name: "Mx Peper"],
                                    [id: "105", name: "Jason Peper"]
                            ],
                            "desc"  : "none"
                    ]
            ]
    }

    def "get CORS header"() {
        setup:
            def client = new RESTClient("http://localhost:" + SUPPORT.getLocalPort())
        when:
            def response = client.get(
                    path: "/v1/konopas",
                    headers: ["Origin": "http://localhost"]
            ) as HttpResponseDecorator
        then:
            response.status == 200
            response.contentType == ContentType.JSON.toString()
            response.headers."Access-Control-Allow-Credentials" == "true"
            response.headers."Access-Control-Allow-Origin" == "http://localhost"
    }
}
