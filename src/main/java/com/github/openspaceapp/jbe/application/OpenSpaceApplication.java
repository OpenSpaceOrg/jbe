package com.github.openspaceapp.jbe.application;

import com.github.openspaceapp.jbe.domain.mapper.SheetMapper;
import com.github.openspaceapp.jbe.domain.mapper.SheetMapperImpl;
import com.github.openspaceapp.jbe.domain.service.KonopasService;
import com.github.openspaceapp.jbe.domain.service.impl.KonopasServiceImpl;
import com.github.openspaceapp.jbe.infrastructure.client.GoogleSheetsApi;
import com.github.openspaceapp.jbe.infrastructure.client.SheetImporter;
import com.github.openspaceapp.jbe.infrastructure.client.impl.GoogleSheetConnector;
import com.github.openspaceapp.jbe.infrastructure.client.impl.GoogleSheetsApiWrapper;
import com.github.openspaceapp.jbe.rest.KonopasResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

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
        FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter("allowedOrigins", "*");
        filter.setInitParameter("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        filter.setInitParameter("allowedMethods", "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter("preflightMaxAge", "1800");
        filter.setInitParameter("allowCredentials", "true");
    }

    private KonopasResource getKonopasResource() {
        return new KonopasResource(getKonopasService());
    }

    private KonopasService getKonopasService() {
        return new KonopasServiceImpl(getSheetImporter(), getSheetMapper(), "1DNeCYZCYoWJBee9y9zgxJDzxd8J2SDZX9NjdwKiXPUE");
    }

    private SheetImporter getSheetImporter() {
        return new GoogleSheetConnector(getGoogleSheetsApi(), "AIzaSyCsdvwt5ewZCCY1c58B5mwaQK6dA36sSAk");
    }

    private SheetMapper getSheetMapper() {
        return new SheetMapperImpl();
    }

    private GoogleSheetsApi getGoogleSheetsApi() {
        return new GoogleSheetsApiWrapper();
    }

}