package com.example.votaciones;

import com.example.votaciones.objetos.Planchas;
import com.example.votaciones.objetos.Respuesta;
import com.example.votaciones.objetos.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Servicio {
    @GET("api/plancha/mostrar")
    Call<List<Planchas>> obtenerPlanchas();

    @POST("api/usuario/IniciarSesion")
    Call<Respuesta> iniciarSesion(@Body Usuario usuario);

}
