package com.example.votaciones;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicioApi {
    public static Servicio INSTANCIA;

    public static Servicio getInstancia(){
        if(INSTANCIA==null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8000")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            INSTANCIA = retrofit.create(Servicio.class);
        }
        return INSTANCIA;
    }
}
