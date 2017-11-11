package com.github.openspaceapp.jbe.application;

import com.github.openspaceapp.jbe.domain.mapper.SessionMapper;
import com.github.openspaceapp.jbe.domain.mapper.SessionMapperImpl;
import com.github.openspaceapp.jbe.domain.port.GoogleSheetsApi;
import com.github.openspaceapp.jbe.domain.port.SheetImporter;
import com.github.openspaceapp.jbe.domain.service.KonopasService;
import com.github.openspaceapp.jbe.domain.service.impl.KonopasServiceImpl;
import com.github.openspaceapp.jbe.infrastructure.client.impl.GoogleSheetConnector;
import com.github.openspaceapp.jbe.infrastructure.client.impl.GoogleSheetsApiWrapper;
import com.github.openspaceapp.jbe.infrastructure.rest.KonopasResource;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.springframework.context.annotation.Bean;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

@Slf4j
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
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );
    }

    @Override
    public void run(OpenSpaceConfiguration configuration,
                    Environment environment) {
        environment.jersey().register(getKonopasResource(configuration));
        setCorsFilter(environment);
    }

    @Bean
    private KonopasResource getKonopasResource(OpenSpaceConfiguration configuration) {
        return new KonopasResource(getKonopasService(configuration));
    }

    @Bean
    private KonopasService getKonopasService(OpenSpaceConfiguration configuration) {
        return new KonopasServiceImpl(
                getSheetImporter(configuration),
                getSheetMapper(),
                configuration.getSheetId());
    }

    @Bean
    private SheetImporter getSheetImporter(OpenSpaceConfiguration configuration) {
        return new GoogleSheetConnector(
                getGoogleSheetsApi(),
                configuration.getApiKey());
    }

    @Bean
    private SessionMapper getSheetMapper() {
        return new SessionMapperImpl();
    }

    @Bean
    private GoogleSheetsApi getGoogleSheetsApi() {
        return new GoogleSheetsApiWrapper();
    }

    private void setCorsFilter(Environment environment) {
        FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter("allowedOrigins", "*");
        filter.setInitParameter("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        filter.setInitParameter("allowedMethods", "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter("preflightMaxAge", "1800");
        filter.setInitParameter("allowCredentials", "true");
    }

}
