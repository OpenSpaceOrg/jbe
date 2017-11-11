package com.github.openspaceapp.jbe.domain.model;

import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class Headers {
    private Map<String, Integer> map = Maps.newHashMap();

    public Headers(List<String> headerList) {
        int i = 0;
        for (String header : headerList) {
            map.put(header, i++);
        }
    }

    public Integer get(String key) {
        return map.get(key);
    }

    public boolean containsKey(String key) {
        return map.containsKey(key);
    }
}
