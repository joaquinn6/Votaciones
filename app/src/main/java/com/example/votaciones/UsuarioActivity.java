package com.example.votaciones;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.votaciones.objetos.Foto;
import com.example.votaciones.objetos.Usuario;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioActivity extends AppCompatActivity {
    private String carnet;
    private final String SESION="VariabesDeSesion";
    private String fotoguardada;
    private int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE=1;

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
                    fotoguardada=response.body().getFoto();

                }else
                    Toast.makeText(UsuarioActivity.this, "succesful pero error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(UsuarioActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        ivUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int permissionCheck = ContextCompat.checkSelfPermission(UsuarioActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE);
                if (ContextCompat.checkSelfPermission(UsuarioActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(UsuarioActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {

                        // Show an expanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                    } else {

                        // No explanation needed, we can request the permission.

                        ActivityCompat.requestPermissions(UsuarioActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                }

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                String[] mimeTypes = {"image/jpeg", "image/png"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
                startActivityForResult(intent,1234);
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
                    Usuario usuario=new Usuario("",tvNombre.getText().toString(),"","",etContrasena.getText().toString(),carnet,"",fotoguardada,"",etinformacion.getText().toString(),true,"","",etTwitter.getText().toString(),etInstagram.getText().toString(),etFacebook.getText().toString(),false);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1234){
            if(resultCode==RESULT_OK){
                ImageView ivUsuario= findViewById(R.id.ivUsuario);
                Uri uri= data.getData();

                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();

                ivUsuario.setImageURI(uri);

                File file = new File(imgDecodableString);
                RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData("foto", file.getName(), fileReqBody);
                Call<Foto> subirFoto = ServicioApi.getInstancia().subirFoto(part);

                subirFoto.enqueue(new Callback<Foto>() {
                    @Override
                    public void onResponse(Call<Foto> call, Response<Foto> response) {
                        if(response.isSuccessful()){
                            fotoguardada = response.body().getFotoNombre();
                        }else{
                            Toast.makeText(UsuarioActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Foto> call, Throwable t) {
                        Log.println(Log.ERROR,"error",t.getMessage());
                        Toast.makeText(UsuarioActivity.this, "ERRRROR " +t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }
    }
}
