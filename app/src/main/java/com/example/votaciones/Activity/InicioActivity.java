package com.example.votaciones.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.votaciones.Api.ServicioApi;
import com.example.votaciones.R;
import com.example.votaciones.RecyclerViews.RvAdaptadorPlancha;
import com.example.votaciones.objetos.Integrante;
import com.example.votaciones.objetos.Planchas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    private ProgressBar pbCargando;
    public LayoutInflater inflater;
    public AlertDialog dialog;
    public FloatingActionButton botonGanador;
    ProgressDialog progressDialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        botonGanador=findViewById(R.id.botonGanador);
        //fnCargando();
        botonGanador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioActivity.this, GanadorActivity.class);
                startActivity(intent);
            }
        });
    }
    public void fnCargando(){
        /*inflater= getLayoutInflater();
        View view = inflater.inflate(R.layout.cargando,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(InicioActivity.this);
        builder.setView(view);
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();*/
        progressDialog = new ProgressDialog(this);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();
        progressDialog.setContentView(R.layout.cargando);
        //se podrá cerrar simplemente pulsando back
        progressDialog.setCancelable(true);

        //no olvidar invocar dismiss para cerrarlo
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
                break;
            case R.id.mnGrafica:
                Intent intent2= new Intent(InicioActivity.this,GraficaActivity.class);
                Bundle x = new Bundle();
                x.putSerializable("Planchas", (Serializable) planchasList);
                intent2.putExtras(x);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        fnCargarRecyclerView();
    }

    private void fnCargarRecyclerView() {fnCargando();
        planchasList.clear();
        Bundle extras= getIntent().getExtras();
        if(extras!=null){
            carnet=extras.getString("carnet");
        }

        final SwipeRefreshLayout srlRecargar= findViewById(R.id.srlRecargar);

        srlRecargar.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onResume();
                srlRecargar.setRefreshing(false);
            }
        });

        RecyclerView rvPlanchas = findViewById(R.id.rvPlanchas);

        final Call<List<Planchas>> planchas= ServicioApi.getInstancia(this).obtenerPlanchas();
        planchas.enqueue(new Callback<List<Planchas>>() {
            @Override
            public void onResponse(Call<List<Planchas>> call, Response<List<Planchas>> response) {
                if(response.isSuccessful()){
                    for(Planchas P : response.body()){
                        planchasList.add(P);
                    }
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }else
                    Toast.makeText(InicioActivity.this, "succesful pero error"+response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Planchas>> call, Throwable t) {
                Toast.makeText(InicioActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                if (t.getMessage().equals("timeout")) {
                    progressDialog.dismiss();
                    AlertDialog.Builder build=new AlertDialog.Builder(InicioActivity.this);
                    build.setTitle("Tiempo Fuera");
                    build.setMessage("Deseas volver a cargar");
                    build.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            fnCargarRecyclerView();
                        }
                    });
                    build.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    });
                    AlertDialog dialog2=build.create();
                    dialog2.setCancelable(true);
                    dialog2.show();
                }
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
                    intent.putExtra("color",planchasList.get(posicion).getColor());
                    startActivity(intent);
                }
                else if(id==R.id.btnIntegrantes){
                    List<Integrante> integranteList = new ArrayList<>();

                    integranteList.add(new Integrante("Presidente", planchasList.get(posicion).getPresidente()));
                    integranteList.add(new Integrante("Vicepresidente", planchasList.get(posicion).getVicepresidente()));
                    integranteList.add(new Integrante("Tesorero", planchasList.get(posicion).getTesorero()));
                    integranteList.add(new Integrante("Secretario", planchasList.get(posicion).getSecretario()));
                    integranteList.add(new Integrante("Ministro", planchasList.get(posicion).getMinistro()));
                    integranteList.add(new Integrante("Vocal",planchasList.get(posicion).getVocal()));
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
                }else if(id==R.id.ivInstagram){
                    if(planchasList.get(posicion).getInstagram().isEmpty() || planchasList.get(posicion).getInstagram()==null){

                    }else {
                        Uri uri = Uri.parse("http://www.instagram.com/" + planchasList.get(posicion).getInstagram());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                }else{
                    List<Integrante> integranteList = new ArrayList<>();

                    integranteList.add(new Integrante("Presidente", planchasList.get(posicion).getPresidente()));
                    integranteList.add(new Integrante("Vicepresidente", planchasList.get(posicion).getVicepresidente()));
                    integranteList.add(new Integrante("Tesorero", planchasList.get(posicion).getTesorero()));
                    integranteList.add(new Integrante("Secretario", planchasList.get(posicion).getSecretario()));
                    integranteList.add(new Integrante("Ministro", planchasList.get(posicion).getMinistro()));
                    integranteList.add(new Integrante("Vocal",planchasList.get(posicion).getVocal()));
                    Intent intent = new Intent(InicioActivity.this,IntegrantesActivity.class);
                    Bundle x = new Bundle();
                    x.putSerializable("Integrantes", (Serializable) integranteList);
                    intent.putExtras(x);
                    startActivity(intent);
                }
            }
        };

        adapter= new RvAdaptadorPlancha(planchasList, onItemClickListener);
        GridLayoutManager manager = new GridLayoutManager(this, 1);
        rvPlanchas.setLayoutManager(manager);
        rvPlanchas.setAdapter(adapter);


    }


}
