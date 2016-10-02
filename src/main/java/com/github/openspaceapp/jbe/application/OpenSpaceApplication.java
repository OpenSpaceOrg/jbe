package com.github.openspaceapp.jbe.application;

import com.github.openspaceapp.jbe.domain.service.KonopasService;
import com.github.openspaceapp.jbe.domain.service.impl.KonopasServiceImpl;
import com.github.openspaceapp.jbe.infrastructure.client.GoogleSheetsApi;
import com.github.openspaceapp.jbe.infrastructure.client.SheetImporter;
import com.github.openspaceapp.jbe.infrastructure.client.impl.GoogleSheetConnector;
import com.github.openspaceapp.jbe.infrastructure.client.impl.GoogleSheetsApiWrapper;
import com.github.openspaceapp.jbe.rest.KonopasResource;
import com.github.openspaceapp.jbe.domain.mapper.SheetMapper;
import com.github.openspaceapp.jbe.domain.mapper.SheetMapperImpl;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class OpenSpaceApplication extends Application<OpenSpaceConfiguration> {
    public static void main(String[] args) throws Exception {
        new OpenSpaceApplication().run(args);
    }

    @Override
    public String getName() {
        return "Open Space App";
    }

    @Override
    public void initialize(Bootstrap<OpenSpaceConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(OpenSpaceConfiguration configuration,
                    Environment environment) {
        environment.jersey().register(getKonopasResource());
    }

    private KonopasResource getKonopasResource() {
        return new KonopasResource(getKonopasService());
    }

    private KonopasService getKonopasService() {
        return new KonopasServiceImpl(getSheetImporter(), getSheetMapper(), "");
    }

    private SheetImporter getSheetImporter() {
        return new GoogleSheetConnector(getGoogleSheetsApi(), "");
    }

    private SheetMapper getSheetMapper() {
        return new SheetMapperImpl();
    }

    private GoogleSheetsApi getGoogleSheetsApi() {
        return new GoogleSheetsApiWrapper();
    }

}