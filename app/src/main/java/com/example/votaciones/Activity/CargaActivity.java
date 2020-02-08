package com.example.votaciones.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.votaciones.Api.ServicioApi;
import com.example.votaciones.R;
import com.example.votaciones.objetos.Configuracion;
import com.example.votaciones.objetos.Token;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CargaActivity extends AppCompatActivity {

    private final String SESION="VariabesDeSesion";
    private  final Token token=new Token();
    private static int TIME=1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_carga);

        SharedPreferences spSesion=getSharedPreferences(SESION, MODE_PRIVATE);
        Map<String, ?> recuperarTexto = spSesion.getAll();
        if(!((Map) recuperarTexto).isEmpty()) {
            token.setToken(spSesion.getString("token", null));
            if (!token.getToken().isEmpty()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        /*final Call<Configuracion> configuracionCall= ServicioApi.getInstancia(CargaActivity.this).extraerConfiguracion();
                        configuracionCall.enqueue(new Callback<Configuracion>() {
                            @Override
                            public void onResponse(Call<Configuracion> call, Response<Configuracion> response) {
                                if(response.isSuccessful()){
                                    Toast.makeText(CargaActivity.this,response.body().getFechaVotaciones().toString(),Toast.LENGTH_LONG+Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Configuracion> call, Throwable t) {
                                Toast.makeText(CargaActivity.this,"1 "+t.getMessage(),Toast.LENGTH_LONG+Toast.LENGTH_LONG).show();
                            }
                        });*/
                        Intent i = new Intent(CargaActivity.this,
                                InicioActivity.class);
                        //Intent is used to switch from one activity to another.
                        //i.putExtra("carnet", usuario.getCarnet());
                        startActivity(i);
                        //invoke the SecondActivity.

                        finish();
                        //the current activity will get finished.*/
                    }
                }, TIME);
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        /*final Call<Configuracion> configuracionCall= ServicioApi.getInstancia(CargaActivity.this).extraerConfiguracion();
                        configuracionCall.enqueue(new Callback<Configuracion>() {
                            @Override
                            public void onResponse(Call<Configuracion> call, Response<Configuracion> response) {
                                if(response.isSuccessful()){
                                    Toast.makeText(CargaActivity.this,response.body().getFechaVotaciones().toString(),Toast.LENGTH_LONG+Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Configuracion> call, Throwable t) {
                                Toast.makeText(CargaActivity.this,"2 "+t.getMessage(),Toast.LENGTH_LONG+Toast.LENGTH_LONG).show();
                            }
                        });*/
                        Intent i = new Intent(CargaActivity.this,
                                MainActivity.class);
                        //Intent is used to switch from one activity to another.

                        startActivity(i);
                        //invoke the SecondActivity.

                        finish();
                        //the current activity will get finished.*/
                    }
                }, TIME);
            }

        }else{
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    /*final Call<Configuracion> configuracionCall= ServicioApi.getInstancia(CargaActivity.this).extraerConfiguracion();
                    configuracionCall.enqueue(new Callback<Configuracion>() {
                        @Override
                        public void onResponse(Call<Configuracion> call, Response<Configuracion> response) {
                            if(response.isSuccessful()){
                                Configuracion confi =response.body();
                                String fechaVotar= confi.getFechaVotaciones().split("T")[0];
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
                                try {
                                    Date strDate = sdf.parse(fechaVotar);
                                    //if(new Date().after(strDate))
                                        Toast.makeText(CargaActivity.this, strDate.toString(), Toast.LENGTH_SHORT).show();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(CargaActivity.this,fechaVotar ,Toast.LENGTH_LONG+Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Configuracion> call, Throwable t) {
                            Toast.makeText(CargaActivity.this,"3 "+t.getMessage(),Toast.LENGTH_LONG+Toast.LENGTH_LONG).show();
                        }
                    });*/
                    Intent i = new Intent(CargaActivity.this,
                            MainActivity.class);
                    //Intent is used to switch from one activity to another.

                    startActivity(i);
                    //invoke the SecondActivity.

                    finish();
                    //the current activity will get finished.*/
                }
            }, TIME);
        }

    }
}
