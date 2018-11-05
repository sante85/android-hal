package it.android.hal.http;

import com.android.volley.Response;
import com.fasterxml.jackson.databind.JavaType;

import java.util.Map;

import it.android.hal.resource.Resource;

public class RestRequest<E extends Resource> extends JacksonRequest {

    private RestRequest<Resource> config;

    public RestRequest(int method, String url, E body, JavaType type, Response.Listener<E> listener) {
        super(method, url, body, type, listener);
    }

    public RestRequest<Resource> getConfig() {
        return config;
    }

    public void setConfig(RestRequest<Resource> config) {
        this.config = config;
    }

    @Override
    public Map<String, String> getHeaders() {
        return config.getHeaders();
    }
}
