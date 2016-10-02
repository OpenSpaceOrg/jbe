package com.github.openspaceapp.jbe.infrastructure.client;

import com.github.openspaceapp.jbe.infrastructure.client.model.SheetImport;

import java.util.Optional;

public interface SheetImporter {

    Optional<SheetImport> get(String identifier);

}
