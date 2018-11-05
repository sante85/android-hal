package it.android.hal.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.util.concurrent.TimeUnit;

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

    public abstract long getTimeout();

    public abstract TimeUnit getTimeoutUnit();
}
