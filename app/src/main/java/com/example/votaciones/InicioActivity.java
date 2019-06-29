package com.example.votaciones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.votaciones.RecyclerViews.RvAdaptadorPlancha;
import com.example.votaciones.objetos.Integrante;
import com.example.votaciones.objetos.Planchas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicioActivity extends AppCompatActivity {
    private List<Planchas> planchasList = new ArrayList<>();
    private RvAdaptadorPlancha adapter;
    private String carnet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        Bundle extras= getIntent().getExtras();
        if(extras!=null){
            carnet=extras.getString("carnet");
        }
        RecyclerView rvPlanchas = findViewById(R.id.rvPlanchas);

        final Call<List<Planchas>> planchas= ServicioApi.getInstancia().obtenerPlanchas();
        planchas.enqueue(new Callback<List<Planchas>>() {
            @Override
            public void onResponse(Call<List<Planchas>> call, Response<List<Planchas>> response) {
                if(response.isSuccessful()){
                    for(Planchas P : response.body()){
                        planchasList.add(P);
                    }
                    adapter.notifyDataSetChanged();
                }else
                    Toast.makeText(InicioActivity.this, "succesful pero error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Planchas>> call, Throwable t) {
                Toast.makeText(InicioActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RvAdaptadorPlancha.OnItemClickListener onItemClickListener = new RvAdaptadorPlancha.OnItemClickListener() {
            @Override
            public void OnItemClick(int posicion, long id) {
                if(id==R.id.btnPropuestas){
                    Intent intent = new Intent(InicioActivity.this,PropuestasActivity.class);
                    Bundle x = new Bundle();
                    x.putSerializable("Propuestas", (Serializable) planchasList.get(posicion).getPropuestas());
                    intent.putExtras(x);
                    startActivity(intent);
                    }
                else if(id==R.id.btnIntegrantes){
                    List<Integrante> integranteList = new ArrayList<>();

                    integranteList.add(new Integrante("Presidente", planchasList.get(posicion).getPresidente()));
                    integranteList.add(new Integrante("Vicepresidente", planchasList.get(posicion).getVicepresidente()));
                    integranteList.add(new Integrante("Tesorero", planchasList.get(posicion).getTesorero()));
                    integranteList.add(new Integrante("Secretario", planchasList.get(posicion).getSecretario()));

                    Intent intent = new Intent(InicioActivity.this,IntegrantesActivity.class);
                    Bundle x = new Bundle();
                    x.putSerializable("Integrantes", (Serializable) integranteList);
                    intent.putExtras(x);
                    startActivity(intent);
                }else if(id==R.id.ivTwitter){
                    if(planchasList.get(posicion).getTwitter().isEmpty() || planchasList.get(posicion).getTwitter()==null){

                    }else{
                        Uri uri = Uri.parse("http://www.twitter.com/"+planchasList.get(posicion).getTwitter());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                }else if(id==R.id.ivFacebook){
                    if(planchasList.get(posicion).getFacebook().isEmpty() || planchasList.get(posicion).getFacebook()==null){

                    }else {
                        Uri uri = Uri.parse("http://www.facebook.com/" + planchasList.get(posicion).getFacebook());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                }else{
                    if(planchasList.get(posicion).getInstagram().isEmpty() || planchasList.get(posicion).getInstagram()==null){

                    }else {
                        Uri uri = Uri.parse("http://www.instagram.com/" + planchasList.get(posicion).getInstagram());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                }
            }
        };

        adapter= new RvAdaptadorPlancha(planchasList, onItemClickListener);
        GridLayoutManager manager = new GridLayoutManager(this, 1);
        rvPlanchas.setLayoutManager(manager);
        rvPlanchas.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inicial, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnUsuario:
                Intent intent = new Intent(InicioActivity.this, UsuarioActivity.class);
                intent.putExtra("carnet", carnet);
                startActivity(intent);
                break;
            case R.id.mnVoto:
                Intent intent1 =new Intent(InicioActivity.this,VotarActivity.class);
                intent1.putExtra("carnet",carnet);
                startActivity(intent1);
        }
        return super.onOptionsItemSelected(item);
    }
}
