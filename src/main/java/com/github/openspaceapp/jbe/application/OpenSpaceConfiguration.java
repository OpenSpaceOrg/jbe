package com.github.openspaceapp.jbe.application;

import io.dropwizard.Configuration;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpenSpaceConfiguration extends Configuration {
    private String apiKey;
    private String sheetId;
    private String conferenceSheetId;
}
