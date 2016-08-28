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
                    ConfigOverride.config("server.applicationConnectors[0].port", "0"),
                    ConfigOverride.config("server.adminConnectors[0].port", "0")
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
            response.data.text == "test"
    }
}
