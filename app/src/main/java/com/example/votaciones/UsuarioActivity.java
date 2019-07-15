package com.example.votaciones;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.votaciones.objetos.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioActivity extends AppCompatActivity {
    private String carnet;
    private final String SESION="VariabesDeSesion";

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
                    Glide.with(UsuarioActivity.this).load(ServicioApi.HTTP+"/uploads/images/"+ response.body().getFoto()).into(ivUsuario);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_usuario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final EditText etinformacion = findViewById(R.id.etInformacion);
        EditText etContrasena = findViewById(R.id.etContrasena);
        EditText etRepetirContrasena = findViewById(R.id.etRepetirContrasena);
        final EditText etFacebook = findViewById(R.id.etFacebook);
        final EditText etTwitter = findViewById(R.id.etTwitter);
        final EditText etInstagram = findViewById(R.id.etInstagram);
        final TextView tvNombre = findViewById(R.id.etNombre);
        boolean contra=true;
        switch (item.getItemId()){
            case R.id.mnAceptar:
                if (!(etContrasena.getText().toString().isEmpty() && etRepetirContrasena.getText().toString().isEmpty())) {
                    if (!(etContrasena.getText().toString().equals(etRepetirContrasena.getText().toString())))
                    {
                        etContrasena.setError("Contraseñas no coiniciden");
                        etRepetirContrasena.setError("Contraseñas no coiniciden");
                        contra=false;
                    }
                }
                if (contra){

                Usuario usuario=new Usuario("",tvNombre.getText().toString(),"","",etContrasena.getText().toString(),carnet,"","","",etinformacion.getText().toString(),true,"","",etTwitter.getText().toString(),etInstagram.getText().toString(),etFacebook.getText().toString(),false);
                Call<String> user = ServicioApi.getInstancia().editarUsuario(usuario);
            user.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    Toast.makeText(UsuarioActivity.this, "Cambios Guardados", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(UsuarioActivity.this, "Sorry", Toast.LENGTH_SHORT).show();
            }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(UsuarioActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
                }
                break;
            case R.id.mnCancelar:
                onBackPressed();
                break;
            case R.id.mnLogout:
                SharedPreferences.Editor editor = getSharedPreferences(SESION, MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(getBaseContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
