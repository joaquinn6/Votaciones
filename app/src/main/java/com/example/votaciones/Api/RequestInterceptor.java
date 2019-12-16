package com.example.votaciones.Api;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.votaciones.Activity.MainActivity;

import java.io.IOException;
import java.util.Map;

import okhttp3.CacheControl;
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
            String token="Bearer " + spSesion.getString("token", null);
            Log.e("API", token);
            Request originalRequest = chain.request();
            Headers headers = new Headers.Builder()
                    .add("Authorization", token)
                    .build();
            Request newRequest = originalRequest.newBuilder()
                    .addHeader("Authorization", token) //Adds a header with name and value.
                    .cacheControl(CacheControl.FORCE_CACHE) // Sets this request's Cache-Control header, replacing any cache control headers already present.
                    .headers(headers) //Removes all headers on this builder and adds headers.
                    //.method(originalRequest.method(), null) // Adds request method and request body
                    //.removeHeader("Authorization") // Removes all the headers with this name
                    .build();

        /*
        chain.proceed(request) is the call which will initiate the HTTP work. This call invokes the
        request and returns the response as per the request.
        */
        Response response = chain.proceed(newRequest);
        return response;
        }else{
            return chain.proceed(chain.request());
        }

    }
}
