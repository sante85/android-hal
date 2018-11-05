package it.android.hal.resource;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import it.android.hal.http.JacksonRequest;
import it.android.hal.http.RestRequest;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class Resource implements Serializable {

    private Long id;

    public Resource() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public <R extends Resource> List<R> relations(String resource, String id, String relations) {
        String url = ResourceHelper.getInstance().getConfig().getRootUri() + resource + "/" + id + "/" + relations;

        RequestFuture<String> response = RequestFuture.newFuture();
        JavaType type = (JavaType) new TypeReference<R>() {}.getType();

        JacksonRequest request = new RestRequest(Request.Method.GET, url, null, type, response);
        RequestQueue requestQueue = Volley.newRequestQueue(ResourceHelper.getInstance().getContext());
        requestQueue.add(request);
        try {
            return parseResponse(response.get(10, TimeUnit.MINUTES), relations);
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <R extends Resource> R relation(String resource, String id, String relation) {
        String url = ResourceHelper.getInstance().getConfig().getRootUri() + resource + "/" + id + "/" + relation;

        RequestFuture<String> response = RequestFuture.newFuture();
        JavaType type = (JavaType) new TypeReference<R>() {}.getType();

        JacksonRequest request = new RestRequest(Request.Method.GET, url, null, type, response);
        RequestQueue requestQueue = Volley.newRequestQueue(ResourceHelper.getInstance().getContext());
        requestQueue.add(request);
        try {
            return parseResponse(response.get(10, TimeUnit.MINUTES));
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private <R extends Resource> List<R> parseResponse(String jsonObject, String relations) throws IOException {
        TypeReference<List<R>> typeReference = new TypeReference<List<R>>() {};
        return ResourceHelper.getInstance().getConfig().getMapper().readValue(jsonObject, typeReference);
    }

    private <R extends Resource> R parseResponse(String jsonObject) throws IOException {
        TypeReference<R> typeReference = new TypeReference<R>() {};
        return ResourceHelper.getInstance().getConfig().getMapper().readValue(jsonObject, typeReference);
    }
}
