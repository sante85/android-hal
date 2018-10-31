package com.example.hal.it.rest.hal.test;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.masvis.meappostmark.R;
import com.example.hal.it.rest.hal.config.RestConfig;
import com.example.hal.it.rest.hal.resource.ResourceHelper;
import com.example.hal.it.rest.hal.task.RestServiceAsync;
import com.masvis.meappostmark.domain.Timestamp;

import java.util.Map;

public class Test extends AppCompatActivity implements Response.ErrorListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ResourceHelper.build(this, new RestConfig() {
            @Override
            public String getRootUri() {
                return null;
            }

            @Override
            public Map<String, String> getHeaders() {
                return null;
            }
        }, this);

        Timestamp t = new Timestamp();
        TimestampService timestampService = new TimestampService();

        RestServiceAsync<Timestamp, Void, Void> restServiceAsync = new RestServiceAsync<>("create", timestampService);
        restServiceAsync.observe(this, aVoid -> {


        });
        restServiceAsync.execute(t);


    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
