package com.example.votaciones.Api;

import android.content.Context;
import android.content.Intent;

import com.example.votaciones.Activity.GraficaActivity;
import com.example.votaciones.Activity.MainActivity;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ResponseInterceptor implements Interceptor {
    private Context context;
    public ResponseInterceptor(Context context) {
        this.context=context;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
            if (response.code() == 200) {
                /*jsonObject.put("code", 200);
                jsonObject.put("status", "OK");
                jsonObject.put("message", new JSONObject(response.body().string()));*/
            }else /*if(response.code()!=200)*/{
                Intent intent=new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }/*else {
                Intent intent=new Intent(context,MainActivity.class);
                context.startActivity(intent);
            }*/
            return response;
   }
}
