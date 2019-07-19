package com.example.votaciones;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicioApi {
    public static Servicio INSTANCIA;
    public static String HTTP="http://192.168.164.113:8000";
//    public static String HTTP="http://10.0.2.2:8000";

    public static Servicio getInstancia(){
        if(INSTANCIA==null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HTTP)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            INSTANCIA = retrofit.create(Servicio.class);
        }
        return INSTANCIA;
    }
}
