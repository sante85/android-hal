package it.android.hal.http;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonRequest;

import it.android.hal.resource.ResourceHelper;

public abstract class JacksonRequest extends JsonRequest<String> {

    /**
     * Creates a new request.
     *
     * @param method      the HTTP method to use
     * @param url         URL to fetch the JSON from
     * @param requestData A {@link Object} to post and convert into json as the request. Null is allowed and indicates no parameters will be posted along with request.
     * @param listener    Listener to receive the JSON response
     * @param errorListener    Listener to receive the JSON response error
     */
    JacksonRequest(int method,
                   String url,
                   Object requestData,
                   Response.Listener<String> listener,
                   Response.ErrorListener errorListener
    ) {
        super(method,
                url,
                (requestData == null) ? null : Mapper.string(requestData),
                listener,
                errorListener
        );
    }

}
