package it.android.hal.resource;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.type.TypeReference;

import junit.framework.Assert;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import it.android.hal.http.RestRequest;

public abstract class Resource implements Serializable {

    private Long id;

    private Response.ErrorListener errorListener;

    public Resource() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public <R extends Resource> List<R> relations(String resource, String id, String relations) {
        try {
            String url = ResourceHelper.getInstance().getConfig().getRootUri() + resource + "/" + id + "/" + relations;

            RequestFuture<String> response = RequestFuture.newFuture();
            RestRequest<R> request = new RestRequest<>(Request.Method.GET, url, null, response, errorListener);
            RequestQueue requestQueue = Volley.newRequestQueue(ResourceHelper.getInstance().getContext());
            requestQueue.add(request);

            return parseResponse(response.get(ResourceHelper.getInstance().getConfig().getTimeout(),
                    ResourceHelper.getInstance().getConfig().getTimeoutUnit()), relations);
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <R extends Resource> R relation(String resource, String id, String relation) {
        try {
            String url = ResourceHelper.getInstance().getConfig().getRootUri() + resource + "/" + id + "/" + relation;

            RequestFuture<String> response = RequestFuture.newFuture();
            RestRequest<R> request = new RestRequest<>(Request.Method.GET, url, null, response, errorListener);
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


    private <R extends Resource> List<R> parseResponse(String jsonObject, String relations) throws
            IOException {
        TypeReference<List<R>> typeReference = new TypeReference<List<R>>() {
        };
        return ResourceHelper.getInstance().getConfig().getMapper().readValue(jsonObject, typeReference);
    }

    private <R extends Resource> R parseResponse(String jsonObject) throws IOException {
        TypeReference<R> typeReference = new TypeReference<R>() {
        };
        return ResourceHelper.getInstance().getConfig().getMapper().readValue(jsonObject, typeReference);
    }

    public void setErrorListener(Response.ErrorListener errorListener) {
        if (errorListener == null) {
            Assert.assertNotNull(ResourceHelper.getInstance().getErrorListener());
            errorListener = ResourceHelper.getInstance().getErrorListener();
        }
        this.errorListener = errorListener;
    }
}
