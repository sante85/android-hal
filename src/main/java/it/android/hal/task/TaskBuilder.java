package it.android.hal.task;

import com.android.volley.Response;

import junit.framework.Assert;

import it.android.hal.service.RestService;

public class TaskBuilder {

    private static TaskBuilder instance;

    public static TaskBuilder build() {
        if (instance == null)
            instance = new TaskBuilder();
        return instance;
    }

    public <Params, Progress, Result> RestServiceAsync<Params, Progress, Result> create(RestService service, Response.ErrorListener errorListener) {
        Assert.assertNotNull(instance);
        service.setErrorListener(errorListener);
        RestServiceAsync<Params, Progress, Result> restServiceAsync = new RestServiceAsync<>("create", service);
        return restServiceAsync;
    }

    @SuppressWarnings("unused")
    public <Params, Progress, Result> RestServiceAsync<Params, Progress, Result> create(RestService service) {
       return create(service, null);
    }

    public <Params, Progress, Result> RestServiceAsync<Params, Progress, Result> update(RestService service, Response.ErrorListener errorListener) {
        Assert.assertNotNull(instance);
        service.setErrorListener(errorListener);
        RestServiceAsync<Params, Progress, Result> restServiceAsync = new RestServiceAsync<>("update", service);
        return restServiceAsync;
    }

    @SuppressWarnings("unused")
    public <Params, Progress, Result> RestServiceAsync<Params, Progress, Result> update(RestService service) {
        return update(service, null);
    }

    public <Params, Progress, Result> RestServiceAsync<Params, Progress, Result> findAll(RestService service, Response.ErrorListener errorListener) {
        Assert.assertNotNull(instance);
        service.setErrorListener(errorListener);
        RestServiceAsync<Params, Progress, Result> restServiceAsync = new RestServiceAsync<>("findAll", service);
        return restServiceAsync;
    }

    @SuppressWarnings("unused")
    public <Params, Progress, Result> RestServiceAsync<Params, Progress, Result> findAll(RestService service) {
        return findAll(service, null);
    }
}
