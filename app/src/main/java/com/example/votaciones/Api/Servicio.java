package com.example.votaciones.Api;

import com.example.votaciones.objetos.Configuracion;
import com.example.votaciones.objetos.Foto;
import com.example.votaciones.objetos.Plancha;
import com.example.votaciones.objetos.Planchas;
import com.example.votaciones.objetos.Respuesta;
import com.example.votaciones.objetos.Token;
import com.example.votaciones.objetos.Usuario;
import com.example.votaciones.objetos.Voto;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Servicio {
    @GET("api/plancha/mostrar")
    Call<List<Planchas>> obtenerPlanchas();

    @GET("api/plancha/mostrarGanador")
    Call<Planchas> obtenerPlanchaGanadora();

    @POST("api/login_check")
    Call<Token> iniciarSesion(@Body Token usuario);

    @POST("api/usuario/crear")
    Call<Respuesta> crearUsuario(@Body Usuario usuario);

    @GET("api/usuario/Buscar/{carnet}")
    Call<Usuario> obtenerUsuarioCarnet(@Path("carnet") String carnet);

    @POST("api/usuario/Editar")
    Call<Respuesta>editarUsuario(@Body Usuario usuario);

    @POST("api/votar/guardar")
    Call<Respuesta> votar(@Body Voto voto);

    @POST("api/votar/votante")
    Call<String> votante(@Body Usuario usuario);

    @POST("api/votar/verificar")
    Call<Respuesta> verficarVotante(@Body Usuario usuario);

    @Multipart
    @POST("api/usuario/foto")
    Call<Foto> subirFoto(@Part MultipartBody.Part imagen);

    @GET("api/configuracion/extraer")
    Call<Configuracion> extraerConfiguracion();

    @GET("api/plancha/grafica")
    Call<List<Plancha>> extraerGrafica();

    @POST("api/usuario/IniciarSesion")
    Call<Respuesta> VerificarPreInicio(@Body Usuario usuario);


}
