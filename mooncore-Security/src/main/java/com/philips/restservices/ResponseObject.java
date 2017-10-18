package com.philips.restservices;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class ResponseObject {
    public int statusCode;
    public String responseBody;
    public JSONObject responseBodyAsJSON;
    public HttpResponse rawHttpResponse;

    public ResponseObject() {
        this.rawHttpResponse = null;
        this.statusCode = -1;
        this.responseBody = "";
        this.responseBodyAsJSON = new JSONObject();
    }

    public ResponseObject(HttpResponse response) {
        this.rawHttpResponse = response;
        this.statusCode = response.getStatusLine().getStatusCode();

        try {
            this.responseBody = EntityUtils.toString(response.getEntity());
            this.responseBodyAsJSON = new JSONObject(this.responseBody);
        } catch (IOException e) {
        } catch (JSONException je) {
        }
    }

}
