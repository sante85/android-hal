package it.android.hal.service;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import it.android.hal.http.JacksonRequest;
import it.android.hal.http.RestRequest;
import it.android.hal.resource.Resource;
import it.android.hal.resource.ResourceHelper;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RestService<E extends Resource, R extends Object> {

    public RestService(String resource) {
        this.resource = resource;
    }

    private String resource;

    public void create(E entity) {
        String url = ResourceHelper.getInstance().getConfig().getRootUri() + resource;

        RequestFuture<String> response = RequestFuture.newFuture();
        JavaType type = (JavaType) new TypeReference<R>() {
        }.getType();

        JacksonRequest request = new RestRequest(Request.Method.POST, url, entity, type, response);
        RequestQueue requestQueue = Volley.newRequestQueue(ResourceHelper.getInstance().getContext());
        requestQueue.add(request);
        try {
            response.get(10, TimeUnit.MINUTES);
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void update(E entity) {
        String url = ResourceHelper.getInstance().getConfig().getRootUri() + resource;

        RequestFuture<String> response = RequestFuture.newFuture();
        JavaType type = (JavaType) new TypeReference<R>() {
        }.getType();

        JacksonRequest request = new RestRequest(Request.Method.PUT, url, entity, type, response);
        RequestQueue requestQueue = Volley.newRequestQueue(ResourceHelper.getInstance().getContext());
        requestQueue.add(request);
        try {
            response.get(10, TimeUnit.MINUTES);
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void delete(E entity) {
        String url = ResourceHelper.getInstance().getConfig().getRootUri() + resource + "/" + entity.getId();

        RequestFuture<String> response = RequestFuture.newFuture();
        JavaType type = (JavaType) new TypeReference<R>() {
        }.getType();

        JacksonRequest request = new RestRequest(Request.Method.DELETE, url, null, type, response);
        RequestQueue requestQueue = Volley.newRequestQueue(ResourceHelper.getInstance().getContext());
        requestQueue.add(request);
        try {
            response.get(10, TimeUnit.MINUTES);
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public List<R> search(String query) {
        String url = ResourceHelper.getInstance().getConfig().getRootUri() + resource + "search/" + query;

        RequestFuture<JsonObject> response = RequestFuture.newFuture();
        JavaType type = (JavaType) new TypeReference<R>() {
        }.getType();

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

    private List<R> parseResponse(JsonObject jsonObject) throws IOException {
        TypeReference<List<R>> typeReference = new TypeReference<List<R>>() {
        };
        return ResourceHelper.getInstance().getConfig().getMapper()
                .readValue(jsonObject.getAsJsonObject("_embedded")
                        .getAsJsonArray(resource).getAsString(), typeReference);
    }

    private List<R> parseResponse(String jsonObject) throws IOException {
        TypeReference<List<R>> typeReference = new TypeReference<List<R>>() {
        };
        return ResourceHelper.getInstance().getConfig().getMapper()
                .readValue(jsonObject, typeReference);
    }


}
