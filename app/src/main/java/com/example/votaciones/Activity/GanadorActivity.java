package com.example.votaciones.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.votaciones.Api.ServicioApi;
import com.example.votaciones.R;
import com.example.votaciones.RecyclerViews.ImageAdapter;
import com.example.votaciones.objetos.Planchas;
import com.example.votaciones.objetos.Usuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ahmed.easyslider.EasySlider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GanadorActivity extends AppCompatActivity {
    private List<Planchas>planchasList= new ArrayList<>();
    private TextView txtCantidadVotos,txtNombre,Titulo;
    public TextView etNombreUser;
    private ImageView ivPlancha;
    private String carnet;
    public EasySlider esIntegrantes;
    public List<Usuario> listUsuario=new ArrayList<>();
    public Planchas Ganadora= new Planchas();
    public FloatingActionButton btnFloatingCerrar;
    final AlertDialog[] dialog = new AlertDialog[1];
    public String FECHA="FechaGanador";
    String fechaWin;
    Button btnPropuesta,btnIntegrantes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganador);
        txtCantidadVotos=findViewById(R.id.txtCantidadVotos);
        txtNombre=findViewById(R.id.txtNombre);
        ivPlancha=findViewById(R.id.ivPlancha);
        btnIntegrantes=findViewById(R.id.btnIntegrantes);
        btnPropuesta=findViewById(R.id.btnPropuesta);
        SharedPreferences spFecha=getSharedPreferences(FECHA, MODE_PRIVATE);
        fechaWin=spFecha.getString("fechaVotar","");
        LayoutInflater inflater= getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_integrantes,null);
        final ViewPager viewPager =view.findViewById(R.id.vpImagen);
        etNombreUser=view.findViewById(R.id.etNombreUsers);
        Titulo=view.findViewById(R.id.Titulo);
        btnFloatingCerrar=view.findViewById(R.id.btnFloatingCerrar);
        btnFloatingCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog[0].cancel();
            }
        });

        final Call<Planchas> planchaGanadora= ServicioApi.getInstancia(this).obtenerPlanchaGanadora();
        planchaGanadora.enqueue(new Callback<Planchas>() {
            @Override
            public void onResponse(Call<Planchas> call, Response<Planchas> response) {
                if(response.isSuccessful()){
                    Ganadora = response.body();
                    txtNombre.setText( Ganadora.getNombrePlancha());
                    txtCantidadVotos.setText("Porcentaje de Votos: "+Ganadora.getVotos());
                    Glide.with(GanadorActivity.this).load(ServicioApi.HTTP +"/uploads/images/"+ Ganadora.getImagen()).into(ivPlancha);
                    listUsuario.add(Ganadora.getPresidente());
                    listUsuario.add(Ganadora.getVicepresidente());
                    listUsuario.add(Ganadora.getSecretario());
                    listUsuario.add(Ganadora.getTesorero());
                    listUsuario.add(Ganadora.getMinistro());
                    listUsuario.add(Ganadora.getVocal());
                    ImageAdapter adapter= new ImageAdapter(GanadorActivity.this,listUsuario);
                    viewPager.setAdapter(adapter);
                }else
                    Toast.makeText(GanadorActivity.this, "succesful pero error"+response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Planchas> call, Throwable t) {
                Toast.makeText(GanadorActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        final AlertDialog.Builder builder = new AlertDialog.Builder(GanadorActivity.this);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Toast.makeText(GanadorActivity.this, ""+position, Toast.LENGTH_LONG+Toast.LENGTH_LONG+Toast.LENGTH_LONG).show();
                String name="";
                switch (position){
                    case 0:
                        Titulo.setText("Presidente");
                        name=listUsuario.get(position).getNombre();
                        break;
                    case 1:
                        Titulo.setText("Vicepresidente");
                        name=listUsuario.get(position).getNombre();
                        break;
                    case 2:
                        Titulo.setText("Secretario");
                        name=listUsuario.get(position).getNombre();
                        break;
                    case 3:
                        Titulo.setText("Tesorero");
                        name=listUsuario.get(position).getNombre();
                        break;
                    case 4:
                        Titulo.setText("Ministro");
                        name=listUsuario.get(position).getNombre();
                        break;
                    case 5:
                        Titulo.setText("Vocal");
                        name=listUsuario.get(position).getNombre();
                        break;
                    default:
                        return;
                }
                etNombreUser.setText(name);
            }

            @Override
            public void onPageSelected(int position) {
                /*if(posAnterior!=position){
                    etNombreUser.setText(listUsuario.get(position).getNombre());
                }else
                    etNombreUser.setText("Vacio");*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        /*Evento Onclick*/
        ivPlancha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //builder.setTitle("Presidente");
                builder.setView(view);
                dialog[0] = builder.create();
                dialog[0].setCancelable(true);
                if(view.getParent()!=null)
                    ((ViewGroup)view.getParent()).removeView(view);
                    dialog[0].show();
            }
        });
        btnIntegrantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setView(view);
                dialog[0] = builder.create();
                dialog[0].setCancelable(true);
                if(view.getParent()!=null)
                    ((ViewGroup)view.getParent()).removeView(view);
                dialog[0].show();
            }
        });
        btnPropuesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(GanadorActivity.this,PropuestasActivity.class);
                Bundle x=new Bundle();
                x.putSerializable("Propuestas",(Serializable)Ganadora.getPropuestas());
                intent.putExtras(x);
                intent.putExtra("color",Ganadora.getColor());
                startActivity(intent);
            }
        });
    }

    private Planchas fnGetGanador(List<Planchas> planchasList) {
        Planchas win = null;
        float mayor=-999999;
        for (Planchas w:planchasList) {
            if (w.getVotos()>mayor)
                win=w;
        }
        return win;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inicial, menu);
        MenuItem mnUsuario= menu.findItem(R.id.mnUsuario);
        mnUsuario.setVisible(false);
        MenuItem mnVoto =menu.findItem(R.id.mnVoto);
        mnVoto.setIcon(R.drawable.ic_inicio);
        MenuItem mnGrafica =menu.findItem(R.id.mnGrafica);
        mnGrafica.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnVoto:
                Intent intent1 =new Intent(GanadorActivity.this,InicioActivity.class);
                intent1.putExtra("carnet",carnet);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
