package it.android.hal.http;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;

import it.android.hal.resource.Resource;

public class RestRequest<E extends Resource> extends JacksonRequest {

    private RestRequest<Resource> config;

    public RestRequest(int method,
                       String url,
                       E body,
                       Response.Listener<String> listener,
                       Response.ErrorListener errorListener) throws JsonProcessingException {
        super(method, url, Mapper.get().writeValueAsString(body), listener, errorListener);
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

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }
}
