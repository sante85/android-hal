package it.android.hal.test;

import it.android.hal.service.RestService;
import com.masvis.meappostmark.domain.Timestamp;

public class TimestampService extends RestService<Timestamp, Timestamp> {

    public TimestampService() {
        super("timestamps");
    }
}
