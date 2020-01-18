package com.example.votaciones.Api;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;
import static android.content.Context.MODE_PRIVATE;

public class RequestInterceptor implements okhttp3.Interceptor {
    private final String SESION="VariabesDeSesion";
    private Context context;
    public RequestInterceptor(Context context) {
        this.context=context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        SharedPreferences spSesion= context.getSharedPreferences(SESION, MODE_PRIVATE);
        Map<String, ?> recuperarTexto = spSesion.getAll();
        if(!((Map) recuperarTexto).isEmpty()) {
            String token="Bearer " + spSesion.getString("token", null).trim();
            Log.e("API", token.trim());
            Request originalRequest = chain.request();
            Headers headers = new Headers.Builder()
                    .add("Authorization", token.trim())
                    .add("Content-Type","application/json")
                    //.add("Cache-Control", "no-cache")
                    .add("accept","application/json")
                    .build();
            Request newRequest = originalRequest.newBuilder()
                    //.addHeader("Authorization", token) //Adds a header with name and value.
                    //.cacheControl(CacheControl.FORCE_CACHE) // Sets this request's Cache-Control header, replacing any cache control headers already present.
                    .headers(headers) //Removes all headers on this builder and adds headers.
                    //.method(originalRequest.method(), null) // Adds request method and request body
                    //.removeHeader("Authorization") // Removes all the headers with this name
                    .build();

        Response response = chain.proceed(newRequest);
        return response;
        }else{
            return chain.proceed(chain.request());
        }

    }
}
