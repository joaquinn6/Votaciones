package com.example.votaciones.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Data;
import androidx.work.WorkManager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.auth0.android.jwt.JWT;
import com.example.votaciones.Api.ServicioApi;
import com.example.votaciones.Class.ComprobarFechaHoraFinalVotaciones;
import com.example.votaciones.Class.IntentServiNotificacion;
import com.example.votaciones.Class.ServicioNotificacion;
import com.example.votaciones.Class.WorMaNotificacion;
import com.example.votaciones.R;
import com.example.votaciones.objetos.Configuracion;
import com.example.votaciones.objetos.Token;
import com.example.votaciones.objetos.Usuario;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final String SESION="VariabesDeSesion";

    private final Token token =new Token();
    private final Intent[] intent = new Intent[1];
    public String FECHA="FechaGanador";
    private EditText etCarnet;
    private EditText etContrasena;
    private ComprobarFechaHoraFinalVotaciones cffv;
    String fechaFinInscrip;
    public String userRole="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //createNotificaionChannel();
        //noti();


        cffv=new ComprobarFechaHoraFinalVotaciones(this);
        etCarnet = findViewById(R.id.etCarnet);
        etContrasena = findViewById(R.id.etContrasena);
        Button btnSesion = findViewById(R.id.btnSesion);
        TextView tvCrearCenta = findViewById(R.id.tvCrearCuenta);
        btnSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(etCarnet.getText().toString().isEmpty() || etContrasena.getText().toString().isEmpty())){
                    token.setUsarname(etCarnet.getText().toString());
                    token.setPassword(md5(etContrasena.getText().toString()));
                    SharedPreferences.Editor editor = getSharedPreferences(SESION, MODE_PRIVATE).edit();
                    editor.clear();
                    editor.apply();
                    IniciarSesion();
                }
            }
        });

        tvCrearCenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent[0] = new Intent(MainActivity.this, CrearUsuarioActivity.class);
                startActivity(intent[0]);
                finish();
            }
        });
    }
    public String md5(@NotNull String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes("UTF-8"));
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        } catch(UnsupportedEncodingException ex){
        }
        return null;
    }
    public void IniciarSesion(){
        Call<Token> respuesta= ServicioApi.getInstancia(this).iniciarSesion(token);
        respuesta.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                final Token tokenResponse=response.body();
                if(response.isSuccessful()){
                    if(!response.body().getToken().isEmpty()){
                        String admin="";
                        try {
                            admin=decoded(tokenResponse.getToken());
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                        if (admin.equals("ROLE_ADMIN")){
                            dialogInscripcion("Acceso de administrador no recomendado");
                        }else {
                            //**//
                            final Call<Configuracion> configuracionCall = ServicioApi.getInstancia(MainActivity.this).extraerConfiguracion();
                            configuracionCall.enqueue(new Callback<Configuracion>() {
                                /*AQui bien*/
                                @Override
                                public void onResponse(Call<Configuracion> call, Response<Configuracion> response) {
                                    if (response.isSuccessful()) {
                                        Configuracion confi = response.body();
                                        String fechaVotar = confi.getFechaVotaciones().split("T")[0];
                                        String horaFinalVota = confi.getHoraFinVotaciones();
                                        String fechaFinInscrip = confi.getFechaInscripcionFin().split("T")[0];
                                        String horaInicioVota = confi.getHoraInicioVotaciones();
                                        SharedPreferences sp = getSharedPreferences(FECHA, MODE_PRIVATE);
                                        SharedPreferences.Editor edit = sp.edit();
                                        edit.putString("fechaVotar", fechaVotar);
                                        edit.putString("horaVotar", horaFinalVota);
                                        edit.putString("fechaFinInscrip", fechaFinInscrip);
                                        edit.putString("horaInicioVota", horaInicioVota);
                                        edit.commit();
                                        MainActivity.this.fechaFinInscrip = fechaFinInscrip;
                                        //Toast.makeText(MainActivity.this, "Antes de IntentServiNotificacion", Toast.LENGTH_LONG).show();
                                        createNotificaionChannel();
                                        noti(fechaVotar, horaFinalVota);
                                        //startService(new Intent(MainActivity.this, ServicioNotificacion.class));
                                        //inicio
                                        if (cffv.fnFechaInscripcion(fechaFinInscrip)) {
                                            if (cffv.fnMostrarGanador(fechaVotar, horaFinalVota)) {
                                                SharedPreferences.Editor editor = getSharedPreferences(SESION, MODE_PRIVATE).edit();
                                                editor.putString("carnet", etCarnet.getText().toString());
                                                editor.putString("token", tokenResponse.getToken());
                                                editor.apply();
                                                intent[0] = new Intent(MainActivity.this, GanadorActivity.class);
                                                //Intent is used to switch from one activity to another.
                                                //i.putExtra("carnet", usuario.getCarnet());
                                                startActivity(intent[0]);
                                                //invoke the SecondActivity.

                                                finish();
                                                //the current activity will get finished.
                                            } else {
                                                SharedPreferences.Editor editor = getSharedPreferences(SESION, MODE_PRIVATE).edit();
                                                editor.putString("carnet", etCarnet.getText().toString());
                                                editor.putString("token", tokenResponse.getToken());
                                                editor.apply();

                                                intent[0] = new Intent(MainActivity.this, InicioActivity.class);
                                                startActivity(intent[0]);
                                                finish();
                                            }
                                        } else
                                            dialogInscripcion("Bienvenido, en este momento no hay acceso debido a que estamos en etapa de inscripción de Planchas, vuelva cuando estén todas las Planchas inscritas despues del " + fechaFinInscrip);



                                    }
                                }

                                @Override
                                public void onFailure(Call<Configuracion> call, Throwable t) {
                                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                            //**//
                        }


                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Error");
                        builder.setMessage("Usuario o contraseña incorrecto");
                        builder.setPositiveButton("Aceptar",null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }


                    //Fin

                }else {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    /*Crear Dialogo para admin y inscripcion*/
    private void dialogInscripcion(String mensaje){
        TextView txtMensaje;
        ImageView ivSalir;
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater=getLayoutInflater();
        View view =inflater.inflate(R.layout.layout_inscripciones,null);
        txtMensaje=view.findViewById(R.id.txtMensaje);
        txtMensaje.setText(mensaje);
        //txtMensaje.setText("Bienvenido, en este momento no hay acceso debido a que estamos en etapa de inscripción de Planchas, vuelva cuando estén todas las Planchas inscritas despues del "+fechaFinInscrip);
        ivSalir=view.findViewById(R.id.ivSalir);
        builder.setView(view);
        final AlertDialog dialog=builder.create();
        dialog.show();
        Button btnOk=view.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ivSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    /*Fin Dialog*/
    /*Creando Notificacion y canal INICIO*/
    private void createNotificaionChannel(){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name="CHANNEL";
            String description="Canal de Notificaciones";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel =new NotificationChannel("Winner",name,importance);
            channel.setDescription(description);
            //channel.setImportance(NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private void noti(String fechaVotar, String llegaHora) {

            int hh = Integer.parseInt(llegaHora.split(":")[0]);
            int mm = Integer.parseInt(llegaHora.split(":")[1]);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar fecha = Calendar.getInstance();
            try {
                fecha.setTime(sdf.parse(fechaVotar));
                fecha.set(Calendar.HOUR, hh);
                fecha.set(Calendar.MINUTE, mm);
                //Toast.makeText(this, "Entre " + fecha.getTime(), Toast.LENGTH_SHORT).show();
                String tag = generateKey();
                Long alertaTime = fecha.getTimeInMillis() - System.currentTimeMillis();
                int rand = (int) (Math.random() * 50 + 1);
                Data data = fnGuardarData("Work Manager", "Soy un detalle", rand);
                WorMaNotificacion.fnGuardarNoti(alertaTime, data, tag,MainActivity.this);
            } catch (ParseException e) {
                e.printStackTrace();
            }

    }
    private String generateKey(){
        return UUID.randomUUID().toString();
    }
    private Data fnGuardarData(String titulo, String detalle, int idNoti){
        return new Data.Builder()
                .putString("titulo",titulo)
                .putString("detalle",detalle)
                .putInt("idNoti",idNoti).build();
    }
    /*FIN Noti*/
    /*Inicio para Extraer el role INICIO*/
    public String decoded(String JWTEncoded) throws Exception {
        String res="";
        String[] split = JWTEncoded.split("\\.");
        try {
            res=getJson(split[1]).split(",")[2].split("\"")[3];

        } catch (UnsupportedEncodingException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return res;
    }

    private String getJson(String strEncoded) throws UnsupportedEncodingException{
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }
    /*Fin Role*/
}
