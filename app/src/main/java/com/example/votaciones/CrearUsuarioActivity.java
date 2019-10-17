package com.example.votaciones;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
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

public class CrearUsuarioActivity extends AppCompatActivity {
    private final Usuario usuario = new Usuario();
    private Intent intent = new Intent();

    private EditText etCarnet;
    private EditText etPin;
    private EditText etContrasena;
    private EditText etRepetirC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);

        etCarnet = findViewById(R.id.etCarnet);
        etPin = findViewById(R.id.etPin);
        etContrasena = findViewById(R.id.etContrasena);
        etRepetirC = findViewById(R.id.etRepetirContrasena);
        Button btnCrearUsuario = findViewById(R.id.btnSesion);

        btnCrearUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(etCarnet.getText().toString().isEmpty() || etContrasena.getText().toString().isEmpty() || etRepetirC.getText().toString().isEmpty() || etPin.getText().toString().isEmpty() )){
                    if(etContrasena.getText().toString().equals(etRepetirC.getText().toString())){
                        usuario.setCarnet(etCarnet.getText().toString());
                        usuario.setContraseña(md5(etContrasena.getText().toString()));
                        usuario.setPin(etPin.getText().toString());
                        String TAG="";
                        Log.e(TAG, md5(etContrasena.getText().toString()));
                        CrearUsuario();
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(CrearUsuarioActivity.this);
                        builder.setTitle("Error");
                        builder.setMessage("Contraseñas no coinciden, por favor verificar");
                        builder.setPositiveButton("Aceptar",null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(CrearUsuarioActivity.this);
                    builder.setTitle("Error");
                    builder.setMessage("Llenar todos los campos");
                    builder.setPositiveButton("Aceptar",null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        TextView tvCorreo = findViewById(R.id.tvCorreoEstudiantil);
        tvCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://estud.unanleon.edu.ni/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
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

    public void CrearUsuario(){
        Call<Respuesta> respuesta=ServicioApi.getInstancia().crearUsuario(usuario);
        respuesta.enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                if(response.isSuccessful()){
                    if(response.body().isPermitir()){
                        AlertDialog.Builder builder = new AlertDialog.Builder(CrearUsuarioActivity.this);
                        builder.setTitle("Usuario Creado");
                        builder.setMessage("Por favor ve a tu correo universitario y verifica tu cuenta");
                        builder.setPositiveButton("Aceptar",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                intent = new Intent(CrearUsuarioActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(CrearUsuarioActivity.this);
                        builder.setTitle("Error");
                        builder.setMessage(response.body().getError());
                        builder.setPositiveButton("Aceptar",null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }else
                    Toast.makeText(CrearUsuarioActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                Toast.makeText(CrearUsuarioActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
