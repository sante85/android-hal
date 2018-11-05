package it.android.hal.resource;

import android.content.Context;

import com.android.volley.Response;

import junit.framework.Assert;

import it.android.hal.config.RestConfig;

public class ResourceHelper {

    private final Context context;
    private final RestConfig config;
    private final Response.ErrorListener errorListener;

    private static ResourceHelper instance;

    private ResourceHelper(Context context, RestConfig config, Response.ErrorListener errorListener) {
        this.context = context;
        this.config = config;
        this.errorListener = errorListener;
    }

    private ResourceHelper(Context context, RestConfig config) {
        this(context, config, null);
    }

    public Context getContext() {
        return context;
    }

    public RestConfig getConfig() {
        return config;
    }

    public Response.ErrorListener getErrorListener() {
        return errorListener;
    }

    public static void build(Context context, RestConfig config, Response.ErrorListener errorListener) {
        if (instance == null)
            instance = new ResourceHelper(context, config, errorListener);
    }

    public static ResourceHelper getInstance() {
        return instance;
    }
}
