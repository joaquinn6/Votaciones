package com.example.votaciones.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.votaciones.Api.ServicioApi;
import com.example.votaciones.Class.ComprobarFechaHoraFinalVotaciones;
import com.example.votaciones.R;
import com.example.votaciones.RecyclerViews.RvAdaptadorPlancha;
import com.example.votaciones.objetos.Integrante;
import com.example.votaciones.objetos.Plancha;
import com.example.votaciones.objetos.Planchas;
import com.github.pwittchen.swipe.library.rx2.SimpleSwipeListener;
import com.github.pwittchen.swipe.library.rx2.Swipe;
import com.github.pwittchen.swipe.library.rx2.SwipeListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicioActivity extends AppCompatActivity {
    private Swipe swipe;
    private List<Planchas> planchasList = new ArrayList<>();
    List<Planchas> listPlanchas= new ArrayList<>();
    private RvAdaptadorPlancha adapter;
    private String carnet;
//    private ProgressBar pbCargando;
//    public LayoutInflater inflater;
//    public AlertDialog dialog;
    public FloatingActionButton botonGanador;
    ProgressDialog progressDialog = null;
    public String FECHA="FechaGanador";
    private PendingIntent pendingIntent;
    private ComprobarFechaHoraFinalVotaciones cffv;
    String fechaWin,horaVotar,horaInicio;
    TextView txtFechaVotacion;
    Menu menuRecargar=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        SharedPreferences spFecha=getSharedPreferences(FECHA, MODE_PRIVATE);
        fechaWin=spFecha.getString("fechaVotar","");
        horaVotar=spFecha.getString("horaVotar","");
        horaInicio=spFecha.getString("horaInicioVota","");
        txtFechaVotacion=findViewById(R.id.txtFechaVotacion);
        String[] fe=fechaWin.split("-");
        int hi=Integer.parseInt(horaInicio.split(":")[0]);
        String m=horaInicio.split(":")[1];
        int hf=Integer.parseInt(horaVotar.split(":")[0]);
        String mm=horaVotar.split(":")[1];
        txtFechaVotacion.setText("Votaciones: el "+ fe[2]+"-"+fe[1]+"-"+fe[0]+" desde las "+hi%12+":"+m+" "+((hi>=12)? "PM":"AM") +" hasta las "+hf%12+":"+mm+" "+((hf>=12)? "PM":"AM"));
        cffv=new ComprobarFechaHoraFinalVotaciones(this);
        botonGanador=findViewById(R.id.botonGanador);

        fnBotonGanador_Menu();
        botonGanador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioActivity.this, GanadorActivity.class);
                startActivity(intent);
            }
        });
    }


    public void fnCargando(){

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
        MenuItem mnVoto=menu.findItem(R.id.mnVoto);
        MenuItem mnGrafica=menu.findItem(R.id.mnGrafica);
        if (cffv.fnVerificarFechaHora(fechaWin,horaVotar,horaInicio)){
            if(cffv.fnMostrarGanador(fechaWin,horaVotar)){
                mnGrafica.setVisible(true);
                mnVoto.setVisible(false);
            }else {
                mnVoto.setVisible(true);
                mnGrafica.setVisible(true);
            }
        }else {
            mnVoto.setVisible(false);
            if (cffv.fnMostrarGanador(fechaWin,horaVotar))
                mnGrafica.setVisible(true);
            else
                mnGrafica.setVisible(false);
        }
        menuRecargar=menu;
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
                Bundle x=new Bundle();
                x.putSerializable("PlanchasVoto",(Serializable) listPlanchas);
                intent1.putExtras(x);
                //intent1.putExtra("carnet",carnet);
                startActivity(intent1);
                break;
            case R.id.mnGrafica:
                final List<Plancha> planchasList2 = new ArrayList<>();
                final Call<List<Plancha>> planchaSimple=ServicioApi.getInstancia(this).extraerGrafica();
                planchaSimple.enqueue(new Callback<List<Plancha>>() {
                    @Override
                    public void onResponse(Call<List<Plancha>> call, Response<List<Plancha>> response) {
                        for (Plancha p:response.body()
                             ) {
                            planchasList2.add(p);
                        }
                        Intent intent2= new Intent(InicioActivity.this,GraficaActivity.class);
                        Bundle x = new Bundle();
                        x.putSerializable("Plancha", (Serializable) planchasList2);
                        intent2.putExtras(x);
                        startActivity(intent2);

                    }

                    @Override
                    public void onFailure(Call<List<Plancha>> call, Throwable t) {
                        Toast.makeText(InicioActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void fnBotonGanador_Menu(){
        MenuItem mnGrafica=null,mnVoto=null;
        if (menuRecargar!=null) {
            mnGrafica = menuRecargar.findItem(R.id.mnGrafica);
            mnVoto = menuRecargar.findItem(R.id.mnVoto);
        }
        if (mnGrafica!=null && mnVoto!=null) {
            if (cffv.fnMostrarGanador(fechaWin, horaVotar)) {
                //Toast.makeText(this, "fnBotonGanador True", Toast.LENGTH_LONG).show();
                mnGrafica.setVisible(true);
                mnVoto.setVisible(false);
                txtFechaVotacion.setVisibility(View.INVISIBLE);
                botonGanador.show();
            } else if(cffv.fnVerificarFechaHora(fechaWin,horaVotar,horaInicio)) {
                //Toast.makeText(this, "fnBotonGanador Falso", Toast.LENGTH_LONG).show();
                botonGanador.hide();
                mnVoto.setVisible(true);
                txtFechaVotacion.setVisibility(View.VISIBLE);
                mnGrafica.setVisible(true);
            }
        } else {
            if (cffv.fnMostrarGanador(fechaWin, horaVotar)) {
                txtFechaVotacion.setVisibility(View.INVISIBLE);
                botonGanador.show();
            } else {
                botonGanador.hide();
                txtFechaVotacion.setVisibility(View.VISIBLE);
            }
        }    }

    @Override
    protected void onResume() {
        super.onResume();
        fnCargarRecyclerView();
        fnBotonGanador_Menu();
    }

    private void fnCargarRecyclerView() {fnCargando();
        planchasList.clear();
        Bundle extras= getIntent().getExtras();
        if(extras!=null){
            carnet=extras.getString("carnet");
        }

        final SwipeRefreshLayout srlRecargar= findViewById(R.id.srlRecargar);

        srlRecargar.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
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
                    listPlanchas=planchasList;
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }else
                    Toast.makeText(InicioActivity.this, "succesful pero error"+response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Planchas>> call, Throwable t) {
                Toast.makeText(InicioActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
