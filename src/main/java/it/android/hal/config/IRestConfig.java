package com.example.hal.it.rest.hal.config;

import java.util.Map;

public interface IRestConfig {
    String getRootUri();

    Map<String,String> getHeaders();
}
