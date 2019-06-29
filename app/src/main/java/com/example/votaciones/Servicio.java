package com.example.votaciones;

import com.example.votaciones.objetos.Planchas;
import com.example.votaciones.objetos.Respuesta;
import com.example.votaciones.objetos.Usuario;
import com.example.votaciones.objetos.Voto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Servicio {
    @GET("api/plancha/mostrar")
    Call<List<Planchas>> obtenerPlanchas();

    @POST("api/usuario/IniciarSesion")
    Call<Respuesta> iniciarSesion(@Body Usuario usuario);

    @GET("api/usuario/Buscar/{carnet}")
    Call<Usuario> obtenerUsuarioCarnet(@Path("carnet") String carnet);

    @POST("api/usuario/Editar")
    Call<String>editarUsuario(@Body Usuario usuario);

    @POST("api/votar/guardar")
    Call<Respuesta> votar(@Body Voto voto);

    @POST("api/votar/votante")
    Call<String> votante(@Body Usuario usuario);

    @POST("api/votar/verificar")
    Call<Respuesta> verficarVotante(@Body Usuario usuario);



}
