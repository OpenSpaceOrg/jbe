package com.github.openspaceapp.jbe

import com.github.openspaceapp.jbe.application.OpenSpaceApplication
import com.github.openspaceapp.jbe.application.OpenSpaceConfiguration
import com.squareup.okhttp.HttpUrl
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import io.dropwizard.testing.ConfigOverride
import io.dropwizard.testing.DropwizardTestSupport
import io.dropwizard.testing.ResourceHelpers
import spock.lang.Specification

class KonopasResourceSpecIT extends Specification {

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
            def client = new OkHttpClient()
        when:
            def request = new Request.Builder()
                .url(new HttpUrl.Builder()
                        .scheme("http")
                        .host("localhost")
                        .port(SUPPORT.getLocalPort())
                        .encodedPath("/v1/konopas").build())
                .build()
            def response = client.newCall(request).execute()
        then:
            response.code() == 200
            response.header("Content-Type") == org.apache.http.entity.ContentType.APPLICATION_JSON.mimeType
    }

    def "get CORS header"() {
        setup:
            def client = new OkHttpClient()
        when:
            def request = new Request.Builder()
                    .url(new HttpUrl.Builder()
                            .scheme("http")
                            .host("localhost")
                            .port(SUPPORT.getLocalPort())
                            .encodedPath("/v1/konopas").build())
                    .header("Origin", "http://localhost")
                    .build()
            def response = client.newCall(request).execute()
        then:
            response.code() == 200
            response.header("Content-Type") == org.apache.http.entity.ContentType.APPLICATION_JSON.mimeType
            response.header("Access-Control-Allow-Credentials") == "true"
            response.header("Access-Control-Allow-Origin") == "http://localhost"
    }
}
