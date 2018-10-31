package com.example.hal.it.rest.hal.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public abstract class RestConfig implements IRestConfig {

    public RestConfig() {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(module);
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    private final ObjectMapper mapper = new ObjectMapper();
    private final SimpleModule module = new SimpleModule();
}
