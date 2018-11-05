package it.android.hal.test;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import it.android.hal.config.RestConfig;

public class CustomConfig extends RestConfig {
    @Override
    public long getTimeout() {
        return 10;
    }

    @Override
    public TimeUnit getTimeoutUnit() {
        return TimeUnit.MINUTES;
    }

    @Override
    public String getRootUri() {
        return "";
    }

    @Override
    public Map<String, String> getHeaders() {
        return null;
    }
}
