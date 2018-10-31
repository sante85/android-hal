package com.example.hal.it.rest.hal.test;

import com.example.hal.it.rest.hal.service.RestService;
import com.masvis.meappostmark.domain.Timestamp;

public class TimestampService extends RestService<Timestamp, Timestamp> {

    public TimestampService() {
        super("timestamps");
    }
}
