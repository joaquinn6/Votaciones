package com.example.votaciones.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.votaciones.Api.ServicioApi;
import com.example.votaciones.Class.ComprobarFechaHoraFinalVotaciones;
import com.example.votaciones.Class.IntentServiNotificacion;
import com.example.votaciones.Class.ServicioNotificacion;
import com.example.votaciones.R;
import com.example.votaciones.objetos.Configuracion;
import com.example.votaciones.objetos.Token;
import com.example.votaciones.Class.VerificarFechaSegundoPlano;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CargaActivity extends AppCompatActivity {
    Context context=this;
    private final String SESION="VariabesDeSesion";
    private  final Token token=new Token();
    private static int TIME=1000;
    public String FECHA="FechaGanador";
    private ComprobarFechaHoraFinalVotaciones cffv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_carga);
        cffv=new ComprobarFechaHoraFinalVotaciones(context);
        SharedPreferences spSesion=getSharedPreferences(SESION, MODE_PRIVATE);
        //SharedPreferences spFecha=getSharedPreferences(FECHA, MODE_PRIVATE);
        Map<String, ?> recuperarTexto = spSesion.getAll();
        if(!((Map) recuperarTexto).isEmpty()) {
            token.setToken(spSesion.getString("token", null));
            if (!token.getToken().isEmpty()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final Call<Configuracion> configuracionCall= ServicioApi.getInstancia(CargaActivity.this).extraerConfiguracion();
                        configuracionCall.enqueue(new Callback<Configuracion>() {
                            @Override
                            public void onResponse(Call<Configuracion> call, Response<Configuracion> response) {
                                if(response.isSuccessful()){
                                    Configuracion confi =response.body();
                                    String fechaVotar= confi.getFechaVotaciones().split("T")[0];
                                    String horaFinalVota=confi.getHoraFinVotaciones();
                                    String fechaFinInscrip= confi.getFechaInscripcionFin().split("T")[0];
                                    String horaInicioVota= confi.getHoraInicioVotaciones();
                                    /*fechaActual.set(Calendar.HOUR_OF_DAY,0);
                                    fechaActual.set(Calendar.MINUTE,0);
                                    fechaActual.set(Calendar.SECOND,0);*/
                                    SharedPreferences sp=getSharedPreferences(FECHA,MODE_PRIVATE);
                                    SharedPreferences.Editor edit =sp.edit();
                                    edit.putString("fechaVotar",fechaVotar);
                                    edit.putString("horaVotar",horaFinalVota);
                                    edit.putString("fechaFinInscrip",fechaFinInscrip);
                                    edit.putString("horaInicioVota",horaInicioVota);
                                    edit.commit();
                                    //cffv=new ComprobarFechaHoraFinalVotaciones(fechaVotar,horaFinalVota,context);
                                    /*Inicio Notificacion*/
                                    createNotificaionChannel();
                                    startService(new Intent(CargaActivity.this, IntentServiNotificacion.class));
                                    //VerificarFechaSegundoPlano v=new VerificarFechaSegundoPlano(CargaActivity.this);
                                    //v.execute();
                                    /*Fin Notificacion*/
                                    //Toast.makeText(CargaActivity.this, ""+cffv.fnMostrarGanador(fechaVotar,horaFinalVota), Toast.LENGTH_SHORT).show();
                                    Intent i;
                                    if(cffv.fnMostrarGanador(fechaVotar,horaFinalVota)) {
                                        i= new Intent(CargaActivity.this,
                                                GanadorActivity.class);
                                    }else{
                                        i= new Intent(CargaActivity.this,
                                                InicioActivity.class);
                                    }
                                    //Intent is used to switch from one activity to another.
                                    //i.putExtra("carnet", usuario.getCarnet());
                                    startActivity(i);
                                    //invoke the SecondActivity.
                                    finish();
                                        //the current activity will get finished.*/
                                }
                            }
                            @Override
                            public void onFailure(Call<Configuracion> call, Throwable t) {
                                Toast.makeText(CargaActivity.this,t.getMessage(),Toast.LENGTH_LONG+Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                }, TIME);
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

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
    private void createNotificaionChannel(){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name="CHANNEL";
            String description="Canal de Notificaciones";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel =new NotificationChannel("Winner",name,importance);
            channel.setDescription(description);
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
