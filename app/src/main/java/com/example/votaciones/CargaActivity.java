package com.example.votaciones;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.votaciones.objetos.Respuesta;
import com.example.votaciones.objetos.Usuario;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CargaActivity extends AppCompatActivity {

    private final String SESION="VariabesDeSesion";
    private final Usuario usuario = new Usuario();
    private static int TIME=1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_carga);

        SharedPreferences spSesion=getSharedPreferences(SESION, MODE_PRIVATE);
        Map<String, ?> recuperarTexto = spSesion.getAll();
        if(!((Map) recuperarTexto).isEmpty()){
            usuario.setCarnet(spSesion.getString("carnet", null));
            usuario.setContraseña(spSesion.getString("contraseña",null));
            Call<Respuesta> respuesta=ServicioApi.getInstancia().iniciarSesion(usuario);
            respuesta.enqueue(new Callback<Respuesta>() {
                @Override
                public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                    if(response.isSuccessful()){
                        if(response.body().isPermitir()){
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent i=new Intent(CargaActivity.this,
                                            InicioActivity.class);
                                    //Intent is used to switch from one activity to another.
                                    i.putExtra("carnet", usuario.getCarnet());
                                    startActivity(i);
                                    //invoke the SecondActivity.

                                    finish();
                                    //the current activity will get finished.
                                }
                            }, TIME);
                        }else{
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent i=new Intent(CargaActivity.this,
                                            MainActivity.class);
                                    //Intent is used to switch from one activity to another.

                                    startActivity(i);
                                    //invoke the SecondActivity.

                                    finish();
                                    //the current activity will get finished.
                                }
                            }, TIME);
                        }
                    }else{
                        Toast.makeText(CargaActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent i=new Intent(CargaActivity.this,
                                        MainActivity.class);
                                //Intent is used to switch from one activity to another.

                                startActivity(i);
                                //invoke the SecondActivity.

                                finish();
                                //the current activity will get finished.
                            }
                        }, TIME);
                    }
                }

                @Override
                public void onFailure(Call<Respuesta> call, Throwable t) {
                    Toast.makeText(CargaActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i=new Intent(CargaActivity.this,
                                    MainActivity.class);
                            //Intent is used to switch from one activity to another.

                            startActivity(i);
                            //invoke the SecondActivity.

                            finish();
                            //the current activity will get finished.
                        }
                    }, TIME);
                }
            });
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i=new Intent(CargaActivity.this,
                            MainActivity.class);
                    //Intent is used to switch from one activity to another.

                    startActivity(i);
                    //invoke the SecondActivity.

                    finish();
                    //the current activity will get finished.
                }
            }, TIME);
        }
    }
}
