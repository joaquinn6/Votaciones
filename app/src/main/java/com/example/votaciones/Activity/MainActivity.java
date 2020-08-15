package com.example.votaciones.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.votaciones.Api.Servicio;
import com.example.votaciones.Api.ServicioApi;
import com.example.votaciones.Class.ComprobarFechaHoraFinalVotaciones;
import com.example.votaciones.R;
import com.example.votaciones.objetos.Configuracion;
import com.example.votaciones.objetos.Respuesta;
import com.example.votaciones.objetos.Token;
import com.example.votaciones.objetos.Usuario;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final String SESION="VariabesDeSesion";

    private final Token token =new Token();
    private final Intent[] intent = new Intent[1];
    public String FECHA="FechaGanador";
    private EditText etCarnet;
    //private EditText etContrasena;
    private TextInputEditText etContrasena;
    private TextView tvOlvide;
    private ComprobarFechaHoraFinalVotaciones cffv;
    String fechaFinInscrip;
    private Usuario usuarioPreLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences.Editor editor = getSharedPreferences(SESION, MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        editor.commit();
        setContentView(R.layout.activity_main);
        tvOlvide=findViewById(R.id.tvOlvide);
        cffv=new ComprobarFechaHoraFinalVotaciones(this);
        etCarnet = findViewById(R.id.etCarnet);
        etContrasena = findViewById(R.id.etContrasena);
        Button btnSesion = findViewById(R.id.btnSesion);
        TextView tvCrearCenta = findViewById(R.id.tvCrearCuenta);
        btnSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(etCarnet.getText().toString().isEmpty() || etContrasena.getText().toString().isEmpty())){
                    PreLogin(etCarnet.getText().toString(),md5(etContrasena.getText().toString()));
                }
            }
        });

        tvCrearCenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent[0] = new Intent(MainActivity.this, CrearUsuarioActivity.class);
                startActivity(intent[0]);
            }
        });
        tvOlvide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btnCancelar,btnAceptar;
                final EditText edCarnet;
                final TextView txtCorreo;
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater=getLayoutInflater();
                View view =inflater.inflate(R.layout.layout_recuperar,null);
                btnCancelar=view.findViewById(R.id.btnCancelar);
                btnAceptar=view.findViewById(R.id.btnAceptar);
                txtCorreo=view.findViewById(R.id.txtCorreo);
                edCarnet=view.findViewById(R.id.edCarnet);
                builder.setView(view);
                final AlertDialog dialog=builder.create();
                dialog.show();
                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                /*Restablecer contrasenia*/
                btnAceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edCarnet.getText().toString().isEmpty()){
                            Toast.makeText(MainActivity.this, "Ingrese su carnet", Toast.LENGTH_SHORT).show();
                        }else {
                            Usuario userOlvidoPass=new Usuario("","","","","",edCarnet.getText().toString(),"","","",true,"","","","","",false,"","","");
                            final Call<Respuesta> resp= ServicioApi.getInstancia(MainActivity.this).Restablecer_Contrasenia(userOlvidoPass);
                            resp.enqueue(new Callback<Respuesta>() {
                                @Override
                                public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                                    if (response.isSuccessful()){
                                        if (response.body().isPermitir()){
                                            Toast.makeText(MainActivity.this, "Se ha enviado un mensaje a su correo estudiantil", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                        else {
                                            txtCorreo.setText(response.body().getError());
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<Respuesta> call, Throwable t) {
                                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            /*try {
                            Thread.sleep(3000);
                            } catch (InterruptedException e) {
                            e.printStackTrace();
                            }*/
                        txtCorreo.setText("El correo fue enviado");
                        edCarnet.setText("");
                        }
                    }
                });
                /*Fin Restablecer*/
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
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch(UnsupportedEncodingException ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    private void PreLogin(final String carnet, final String password){
        usuarioPreLogin = new Usuario("","","","",password,carnet,"","","",true,"","","","","",false,"","","");
        Call<Respuesta> respuesta = ServicioApi.getInstancia(this).VerificarPreInicio(usuarioPreLogin);
        respuesta.enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                if(response.isSuccessful()){
                    final Respuesta res = response.body();
                    if(res.isPermitir()) {
                        token.setUsarname(carnet);
                        token.setPassword(password);
                        IniciarSesion();
                    }else{
                        Toast.makeText(MainActivity.this, res.getError(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Error al iniciar sesion", Toast.LENGTH_SHORT).show();
            }
        });
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
                                        //createNotificaionChannel();
                                        //noti(fechaVotar, horaFinalVota);
                                        //startService(new Intent(MainActivity.this, ServicioNotificacion.class));
                                        //inicio
                                        if (cffv.fnFechaInscripcion(fechaFinInscrip)) {
                                            if (cffv.fnMostrarGanador(fechaVotar, horaFinalVota)) {
                                                SharedPreferences.Editor editor = getSharedPreferences(SESION, MODE_PRIVATE).edit();
                                                editor.putString("carnet", etCarnet.getText().toString());
                                                editor.putString("pass",etContrasena.getText().toString());
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
                                                editor.putString("pass",etContrasena.getText().toString());
                                                editor.putString("token", tokenResponse.getToken());
                                                editor.apply();

                                                intent[0] = new Intent(MainActivity.this, InicioActivity.class);
                                                startActivity(intent[0]);
                                                finish();
                                            }
                                        } else {
                                            String[] fe=fechaFinInscrip.split("-");
                                            dialogInscripcion("Bienvenido, en este momento no hay acceso debido a que estamos en etapa de inscripción de planchas, vuelva el día  " + fe[2]+"-"+fe[1]+"-"+fe[0] + ".");
                                        }


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
