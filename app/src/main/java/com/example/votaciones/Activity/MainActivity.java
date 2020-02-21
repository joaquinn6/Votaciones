package com.example.votaciones.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.votaciones.Api.ServicioApi;
import com.example.votaciones.R;
import com.example.votaciones.objetos.Configuracion;
import com.example.votaciones.objetos.Token;

import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final String SESION="VariabesDeSesion";
    private final Token token =new Token();
    private final Intent[] intent = new Intent[1];

    private EditText etCarnet;
    private EditText etContrasena;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                        //inicio
                        final Call<Configuracion> configuracionCall= ServicioApi.getInstancia(MainActivity.this).extraerConfiguracion();
                        configuracionCall.enqueue(new Callback<Configuracion>() {
                            @Override
                            public void onResponse(Call<Configuracion> call, Response<Configuracion> response) {
                                if(response.isSuccessful()){
                                    Configuracion confi =response.body();
                                    String fechaVotar= confi.getFechaVotaciones().split("T")[0];
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    Calendar fechaActual= Calendar.getInstance();
                                    fechaActual.set(Calendar.HOUR_OF_DAY,0);
                                    fechaActual.set(Calendar.MINUTE,0);
                                    fechaActual.set(Calendar.SECOND,0);
                                    try {
                                        Date strDate = sdf.parse(fechaVotar);

                                        if(fechaActual.after(strDate)) {
                                            SharedPreferences.Editor editor = getSharedPreferences(SESION, MODE_PRIVATE).edit();
                                            editor.putString("carnet", etCarnet.getText().toString());
                                            editor.putString("token", tokenResponse.getToken());
                                            editor.apply();

                                            intent[0] = new Intent(MainActivity.this, InicioActivity.class);
                                            startActivity(intent[0]);
                                            finish();
                                        }
                                        else {
                                            SharedPreferences.Editor editor = getSharedPreferences(SESION, MODE_PRIVATE).edit();
                                            editor.putString("carnet", etCarnet.getText().toString());
                                            editor.putString("token", tokenResponse.getToken());
                                            editor.apply();
                                            Intent i= new Intent(MainActivity.this,
                                                    GanadorActivity.class);
                                            //Intent is used to switch from one activity to another.
                                            //i.putExtra("carnet", usuario.getCarnet());
                                            startActivity(i);
                                            //invoke the SecondActivity.

                                            finish();
                                            //the current activity will get finished.*/
                                        }

                                    } catch (ParseException e) {
                                        Toast.makeText(MainActivity.this, "ERORR "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Configuracion> call, Throwable t) {
                                Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_LONG+Toast.LENGTH_LONG).show();
                            }
                        });
                        //Fin

                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Error");
                        builder.setMessage("Usuario o contraseña incorrecto");
                        builder.setPositiveButton("Aceptar",null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
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
}
