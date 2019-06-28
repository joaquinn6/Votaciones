package com.example.votaciones;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.votaciones.objetos.Planchas;
import com.example.votaciones.objetos.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioActivity extends AppCompatActivity {
    private String carnet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        Bundle extras= getIntent().getExtras();
        if(extras!=null){
            carnet=extras.getString("carnet");
        }

        final ImageView ivUsuario= findViewById(R.id.ivUsuario);
        final EditText etinformacion = findViewById(R.id.etInformacion);
        EditText etContrasena = findViewById(R.id.etContrasena);
        EditText etRepetirContrasena = findViewById(R.id.etRepetirContrasena);
        final EditText etFacebook = findViewById(R.id.etFacebook);
        final EditText etTwitter = findViewById(R.id.etTwitter);
        final EditText etInstagram = findViewById(R.id.etInstagram);
        final TextView tvNombre = findViewById(R.id.etNombre);
        Call<Usuario> usuarioCall = ServicioApi.getInstancia().obtenerUsuarioCarnet(carnet);
        usuarioCall.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){
                    Glide.with(UsuarioActivity.this).load("http://10.0.2.2:8000/uploads/images/"+ response.body().getFoto()).into(ivUsuario);

                    etinformacion.setText(response.body().getAcercade());
                    etFacebook.setText(response.body().getFacebook());
                    etTwitter.setText(response.body().getTwitter());
                    etInstagram.setText(response.body().getInstagram());
                    tvNombre.setText(response.body().getNombre() +" "+response.body().getApellido());

                }else
                    Toast.makeText(UsuarioActivity.this, "succesful pero error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(UsuarioActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
