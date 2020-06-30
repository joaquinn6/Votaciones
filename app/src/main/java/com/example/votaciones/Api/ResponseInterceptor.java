package com.example.votaciones.Api;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

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
            if (response.code() == 401) {
                Toast.makeText(context, "Sesion expirada", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }else if(response.code() != 200) /*if(response.code()!=200)*/{
                Toast.makeText(context, "Error en el servidor", Toast.LENGTH_SHORT).show();
            }
            return response;
   }
}
