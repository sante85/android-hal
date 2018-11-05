package it.android.hal.service;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.type.TypeReference;

import junit.framework.Assert;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import it.android.hal.http.RestRequest;
import it.android.hal.resource.Resource;
import it.android.hal.resource.ResourceHelper;

public class RestService<E extends Resource, R extends Resource> {

    private String resourceUrl;
    private Response.ErrorListener errorListener;

    public RestService(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    @SuppressWarnings("unused")
    public void create(E entity) {
        try {

            String url = ResourceHelper.getInstance().getConfig().getRootUri() + resourceUrl;

            RequestFuture<String> response = RequestFuture.newFuture();
            RestRequest<E> request = new RestRequest<>(Request.Method.POST, url, entity, response, errorListener);
            RequestQueue requestQueue = Volley.newRequestQueue(ResourceHelper.getInstance().getContext());
            requestQueue.add(request);

            response.get(ResourceHelper.getInstance().getConfig().getTimeout(),
                    ResourceHelper.getInstance().getConfig().getTimeoutUnit());
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    public void update(E entity) {
        try {
            String url = ResourceHelper.getInstance().getConfig().getRootUri() + resourceUrl;

            RequestFuture<String> response = RequestFuture.newFuture();
            RestRequest<E> request = new RestRequest<>(Request.Method.PUT, url, entity, response, errorListener);
            RequestQueue requestQueue = Volley.newRequestQueue(ResourceHelper.getInstance().getContext());
            requestQueue.add(request);
            response.get(ResourceHelper.getInstance().getConfig().getTimeout(),
                    ResourceHelper.getInstance().getConfig().getTimeoutUnit());
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    public void delete(E entity) {
        try {
            String url = ResourceHelper.getInstance().getConfig().getRootUri() + resourceUrl + "/" + entity.getId();

            RequestFuture<String> response = RequestFuture.newFuture();
            RestRequest<E> request = new RestRequest<>(Request.Method.DELETE, url, null, response, errorListener);
            RequestQueue requestQueue = Volley.newRequestQueue(ResourceHelper.getInstance().getContext());
            requestQueue.add(request);

            response.get(ResourceHelper.getInstance().getConfig().getTimeout(),
                    ResourceHelper.getInstance().getConfig().getTimeoutUnit());
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    public List<R> search(String query) {
        try {
            String url = ResourceHelper.getInstance().getConfig().getRootUri() + resourceUrl + "/search/" + query;

            RequestFuture<String> response = RequestFuture.newFuture();
            RestRequest<E> request = new RestRequest<>(Request.Method.GET, url, null, response, errorListener);
            RequestQueue requestQueue = Volley.newRequestQueue(ResourceHelper.getInstance().getContext());
            requestQueue.add(request);

            return parseResponse(response.get(ResourceHelper.getInstance().getConfig().getTimeout(),
                    ResourceHelper.getInstance().getConfig().getTimeoutUnit()));
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unused")
    public List<R> findAll() {
        try {
            String url = ResourceHelper.getInstance().getConfig().getRootUri() + resourceUrl;

            RequestFuture<String> response = RequestFuture.newFuture();
            RestRequest<E> request = new RestRequest<>(Request.Method.GET, url, null, response, errorListener);
            RequestQueue requestQueue = Volley.newRequestQueue(ResourceHelper.getInstance().getContext());
            requestQueue.add(request);

            return parseResponse(response.get(ResourceHelper.getInstance().getConfig().getTimeout(), ResourceHelper.getInstance().getConfig().getTimeoutUnit()));
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<R> parseResponse(String jsonObject) throws IOException {
        TypeReference<List<R>> typeReference = new TypeReference<List<R>>() {
        };
        return ResourceHelper.getInstance().getConfig().getMapper()
                .readValue(jsonObject, typeReference);
    }


    public void setErrorListener(Response.ErrorListener errorListener) {
        if (errorListener == null) {
            Assert.assertNotNull(ResourceHelper.getInstance().getErrorListener());
            errorListener = ResourceHelper.getInstance().getErrorListener();
        }
        this.errorListener = errorListener;
    }
}
