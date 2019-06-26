package com.example.votaciones;

import com.example.votaciones.objetos.Planchas;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Servicio {
    @GET("api/plancha/mostrar")
    Call<List<Planchas>> obtenerPlanchas();
}
