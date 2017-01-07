package com.github.openspaceapp.jbe.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
@ToString
public class KonopasPerson {
    private String id;
    private String name;

    public static KonopasPerson of(String id, String name) {
        if (id == null) {
            return null;
        }
        return new KonopasPerson(
            id,
            Optional.of(name).orElse("")
        );
    }
}