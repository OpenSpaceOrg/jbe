package com.github.openspaceapp.jbe.application;

import com.github.openspaceapp.jbe.domain.service.impl.KonopasServiceImpl;
import com.github.openspaceapp.jbe.rest.KonopasResource;
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
        environment.jersey().register(new KonopasResource(new KonopasServiceImpl()));
//        environment.healthChecks().register("template", new SignatureHealthCheck());
    }

}