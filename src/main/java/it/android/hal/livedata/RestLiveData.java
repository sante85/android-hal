package com.example.hal.it.rest.hal.livedata;

import android.arch.lifecycle.LiveData;

public class RestLiveData<Result> extends LiveData<Result> {

    @Override
    public void postValue(Result liveData) {
        super.postValue(liveData);
    }
}
