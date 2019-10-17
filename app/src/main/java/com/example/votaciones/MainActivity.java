package com.example.votaciones;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.votaciones.objetos.Respuesta;
import com.example.votaciones.objetos.Usuario;

import java.io.UnsupportedEncodingException;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final String SESION="VariabesDeSesion";
    private final Usuario usuario = new Usuario();
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
                    usuario.setCarnet(etCarnet.getText().toString());
                    usuario.setContraseña(md5(etContrasena.getText().toString()));
                    SharedPreferences.Editor editor = getSharedPreferences(SESION, MODE_PRIVATE).edit();
                    editor.putString("carnet", etCarnet.getText().toString());
                    editor.putString("contraseña", md5(etContrasena.getText().toString()));
                    String TAG="";
                    Log.e(TAG, md5(etContrasena.getText().toString()));
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
    public String md5(String md5) {
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
        Call<Respuesta> respuesta=ServicioApi.getInstancia().iniciarSesion(usuario);
        respuesta.enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                if(response.isSuccessful()){
                    if(response.body().isPermitir()){
                        intent[0] = new Intent(MainActivity.this, InicioActivity.class);
                        intent[0].putExtra("carnet", usuario.getCarnet());
                        startActivity(intent[0]);
                        finish();
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Error");
                        builder.setMessage(response.body().getError());
                        builder.setPositiveButton("Aceptar",null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        }
                }else
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
