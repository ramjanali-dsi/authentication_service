package com.dsi.authentication.util;

import com.dsi.authentication.exception.CustomException;
import com.dsi.authentication.exception.ErrorContext;
import com.dsi.authentication.exception.ErrorMessage;
import com.squareup.okhttp.*;
import org.codehaus.jettison.json.JSONObject;

import java.io.IOException;

/**
 * Created by sabbir on 7/11/16.
 */
public class HttpClient {

    public static String sendPost(String url, String jsonObject) throws CustomException {
        final MediaType JSON =
                MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, jsonObject);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
            return response.body().string();

        } catch (IOException e) {
            ErrorContext errorContext = new ErrorContext(null, null, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.AUTHENTICATE_SERVICE_0012,
                    Constants.AUTHENTICATE_SERVICE_0012_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }

    public static String sendPut(String url, String jsonObject) throws CustomException {
        final MediaType JSON =
                MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, jsonObject);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
            return response.body().string();

        } catch (IOException e) {
            ErrorContext errorContext = new ErrorContext(null, null, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.AUTHENTICATE_SERVICE_0012,
                    Constants.AUTHENTICATE_SERVICE_0012_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }

    public static String sendDelete(String url, String jsonObject) throws CustomException {
        final MediaType JSON =
                MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, jsonObject);
        Request request = new Request.Builder()
                .url(url)
                .delete(body)
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
            return response.body().string();

        } catch (IOException e) {
            ErrorContext errorContext = new ErrorContext(null, null, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.AUTHENTICATE_SERVICE_0012,
                    Constants.AUTHENTICATE_SERVICE_0012_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }
}
