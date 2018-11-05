package it.android.hal.http;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public final class Mapper {
    private static ObjectMapper MAPPER;

    public static ObjectMapper get() {
        if (MAPPER == null) {
            MAPPER = new ObjectMapper();

            // This is useful for me in case I add new object properties on the server side which are not yet available on the client.
            MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }

        return MAPPER;
    }

    public static String string(Object data) {
        try {
            return get().writeValueAsString(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T objectOrThrow(String data, JavaType type) throws IOException {
        return get().readValue(data, type);
    }

    public static <T> T object(String data, JavaType type) {
        try {
            return objectOrThrow(data, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
