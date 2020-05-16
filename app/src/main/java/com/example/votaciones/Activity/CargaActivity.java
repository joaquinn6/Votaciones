package com.example.votaciones.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.votaciones.Api.ServicioApi;
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

    private final String SESION="VariabesDeSesion";
    private  final Token token=new Token();
    private static int TIME=1000;
    public String FECHA="FechaGanador";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_carga);
        /*createNotificaionChannel();
        VerificarFechaSegundoPlano v=new VerificarFechaSegundoPlano(this);
        v.execute();*/
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

                                    /*fechaActual.set(Calendar.HOUR_OF_DAY,0);
                                    fechaActual.set(Calendar.MINUTE,0);
                                    fechaActual.set(Calendar.SECOND,0);*/
                                    SharedPreferences sp=getSharedPreferences(FECHA,MODE_PRIVATE);
                                    SharedPreferences.Editor edit =sp.edit();
                                    edit.putString("fechaVotar",fechaVotar);
                                    edit.putString("horaVotar",horaFinalVota);
                                    edit.commit();
                                    /*Inicio Notificacion*/
                                    createNotificaionChannel();
                                    Toast.makeText(CargaActivity.this, "Antes de IntentServiNotificacion", Toast.LENGTH_SHORT).show();
                                    startService(new Intent(CargaActivity.this, IntentServiNotificacion.class));
                                    //VerificarFechaSegundoPlano v=new VerificarFechaSegundoPlano(CargaActivity.this);
                                    //v.execute();
                                    /*Fin Notificacion*/
                                    Intent i;
                                    if(fnVerificarFechaHora()) {
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
    private Boolean fnVerificarFechaHora(){
        boolean check = false;
        Calendar fechaActual =Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateHoraVotar,dateHoraActual;
        fechaActual.set(Calendar.HOUR_OF_DAY,0);
        fechaActual.set(Calendar.MINUTE,0);
        fechaActual.set(Calendar.SECOND,0);
        SharedPreferences spFecha=getSharedPreferences(FECHA, MODE_PRIVATE);
        String fechaWin=spFecha.getString("fechaVotar","");
        try {
            Date strDate=sdf.parse(fechaWin);
            //Toast.makeText(this, ""+fechaActual.getTime(), Toast.LENGTH_LONG).show();
            if (fechaActual.getTime().before(strDate)){
                check= false;
            }else {
                //Toast.makeText(this, fechaWin+" true "+fechaActual, Toast.LENGTH_LONG+Toast.LENGTH_LONG+Toast.LENGTH_LONG+Toast.LENGTH_LONG).show();
                String horaVotar=spFecha.getString("horaVotar","");
                Calendar c = Calendar.getInstance();
                String horaActual=c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);
                DateFormat df=new SimpleDateFormat("HH:mm");
                try {
                    dateHoraVotar=df.parse(horaVotar);
                    dateHoraActual=df.parse(horaActual);
                    if (dateHoraActual.before(dateHoraVotar)){
                        //Toast.makeText(this, dateHoraActual+" Falso "+dateHoraVotar, Toast.LENGTH_LONG+Toast.LENGTH_LONG+Toast.LENGTH_LONG+Toast.LENGTH_LONG).show();
                        check= false;
                    }else {
                        //Toast.makeText(this, dateHoraActual+" True "+dateHoraVotar, Toast.LENGTH_LONG+Toast.LENGTH_LONG+Toast.LENGTH_LONG+Toast.LENGTH_LONG).show();
                        check= true;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return check;
    }
}
