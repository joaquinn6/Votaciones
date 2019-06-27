package com.example.votaciones;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.votaciones.objetos.Respuesta;
import com.example.votaciones.objetos.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etCarnet = findViewById(R.id.etCarnet);
        final EditText etContrasena = findViewById(R.id.etContrasena);
        Button btnSesion = findViewById(R.id.btnSesion);
        TextView tvCrearCenta = findViewById(R.id.tvCrearCuenta);

        final Usuario usuario = new Usuario();

        btnSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent[] intent = new Intent[1];

                usuario.setCarnet(etCarnet.getText().toString());
                usuario.setContraseña(etContrasena.getText().toString());


                Call<Respuesta> respuesta=ServicioApi.getInstancia().iniciarSesion(usuario);
                respuesta.enqueue(new Callback<Respuesta>() {
                    @Override
                    public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                        if(response.isSuccessful()){
                            if(response.body().isPermitir()){
                                intent[0] = new Intent(MainActivity.this, InicioActivity.class);
                                intent[0].putExtra("carnet", etCarnet.getText().toString());
                                startActivity(intent[0]);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setTitle("Error");
                                builder.setMessage(response.body().getError());
                                builder.setPositiveButton("Aceptar",null);

                                AlertDialog dialog = builder.create();
                                dialog.show();
                                //Toast.makeText(MainActivity.this, response.body().getError(), Toast.LENGTH_SHORT).show();
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
        });


        tvCrearCenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
