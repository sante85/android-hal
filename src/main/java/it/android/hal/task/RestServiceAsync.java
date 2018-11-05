package it.android.hal.task;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.os.AsyncTask;

import it.android.hal.livedata.RestLiveData;
import it.android.hal.service.RestService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RestServiceAsync<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    private RestLiveData<Result> liveData = new RestLiveData<>();
    private final RestService restService;
    private final String method;

    public RestServiceAsync(String method, RestService restService) {
        this.restService = restService;
        this.method = method;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Result doInBackground(Params... params) {
        Method method;
        try {
            method = restService.getClass().getMethod(this.method);
            return (Result) method.invoke(restService, params);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void observe(LifecycleOwner owner, Observer<Result> observer) {
        this.liveData.observe(owner, observer);
    }

    @Override
    protected void onPostExecute(Result result) {
        liveData.postValue(result);
    }
}
