package com.example.votaciones.Api;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicioApi {
    public static Servicio INSTANCIA;
    //public static String HTTP="http://192.168.1.3:8000/";
    public static String HTTP="http://10.0.3.2:8000/";

    public static Servicio getInstancia(Context context){
        if(INSTANCIA==null){
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new RequestInterceptor(context))
                    .addInterceptor(new ResponseInterceptor(context))
                    // This is used to add ApplicationInterceptor
                    //This is used to add NetworkInterceptor.
                    .retryOnConnectionFailure(true)
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HTTP)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            INSTANCIA = retrofit.create(Servicio.class);
        }
        return INSTANCIA;
    }
}
