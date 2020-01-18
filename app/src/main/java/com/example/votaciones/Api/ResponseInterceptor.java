package com.example.votaciones.Api;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ResponseInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        try {
            JSONObject jsonObject = new JSONObject();
            if (response.code() == 200) {
                jsonObject.put("code", 200);
                jsonObject.put("status", "OK");
                jsonObject.put("message", new JSONObject(response.body().string()));
            }else if(response.code()!=200){
                Log.e("responseInterceptor",response.body().string());
            }
                else {
                jsonObject.put("code", 404);
                jsonObject.put("status", "ERROR");
                jsonObject.put("message", new JSONObject(response.body().string()));
            }
            MediaType contentType = response.body().contentType();
            ResponseBody body = ResponseBody.create(contentType, jsonObject.toString());
            return response.newBuilder().body(body).build();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }
}
