package com.example.votaciones.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.votaciones.Api.ServicioApi;
import com.example.votaciones.R;
import com.example.votaciones.RecyclerViews.ImageAdapter;
import com.example.votaciones.objetos.Planchas;
import com.example.votaciones.objetos.Usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

import ahmed.easyslider.EasySlider;
import ahmed.easyslider.SliderItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GanadorActivity extends AppCompatActivity {
    private List<Planchas>planchasList= new ArrayList<>();
    private TextView txtCantidadVotos,txtNombre;
    public TextView etNombreUser;
    private ImageView ivPlancha;
    private String carnet;
    public EasySlider esIntegrantes;
    public List<Usuario> listUsuario=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganador);
        txtCantidadVotos=findViewById(R.id.txtCantidadVotos);
        txtNombre=findViewById(R.id.txtNombre);
        ivPlancha=findViewById(R.id.ivPlancha);
        /*final Call<List<Planchas>> planchas= ServicioApi.getInstancia(this).obtenerPlanchas();
        planchas.enqueue(new Callback<List<Planchas>>() {
            @Override
            public void onResponse(Call<List<Planchas>> call, Response<List<Planchas>> response) {
                if(response.isSuccessful()){
                    for(Planchas P : response.body()){
                        planchasList.add(P);
                    }
                    txtNombre.setText(ServicioApi.HTTP +"/uploads/images/"+ planchasList.get(0).getPresidente().getFoto());
                    txtCantidadVotos.setText("Cantidad de Votos: "+planchasList.get(0).getVotos());
                    Glide.with(GanadorActivity.this).load(ServicioApi.HTTP +"/uploads/images/"+ planchasList.get(0).getPresidente().getFoto()).into(ivPlancha);

                }else
                    Toast.makeText(GanadorActivity.this, "succesful pero error"+response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Planchas>> call, Throwable t) {
                Toast.makeText(GanadorActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/
        LayoutInflater inflater= getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_integrantes,null);
        final ViewPager viewPager =view.findViewById(R.id.vpImagen);
        etNombreUser=view.findViewById(R.id.etNombreUsers);
        final Call<List<Planchas>> planchas= ServicioApi.getInstancia(this).obtenerPlanchas();
        planchas.enqueue(new Callback<List<Planchas>>() {
            @Override
            public void onResponse(Call<List<Planchas>> call, Response<List<Planchas>> response) {
                if(response.isSuccessful()){
                    for(Planchas P : response.body()){
                        planchasList.add(P);
                    }
                    int pos= fnGetMayorVotos(planchasList);
                    txtNombre.setText( planchasList.get(pos).getNombrePlancha());
                    txtCantidadVotos.setText("Cantidad de Votos: "+planchasList.get(pos).getVotos());
                    Glide.with(GanadorActivity.this).load(ServicioApi.HTTP +"/uploads/images/"+ planchasList.get(pos).getImagen()).into(ivPlancha);
                    listUsuario.add(planchasList.get(pos).getPresidente());
                    listUsuario.add(planchasList.get(pos).getVicepresidente());
                    listUsuario.add(planchasList.get(pos).getSecretario());
                    listUsuario.add(planchasList.get(pos).getTesorero());

                    ImageAdapter adapter= new ImageAdapter(GanadorActivity.this,listUsuario);
                    viewPager.setAdapter(adapter);
                }else
                    Toast.makeText(GanadorActivity.this, "succesful pero error"+response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Planchas>> call, Throwable t) {
                Toast.makeText(GanadorActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Toast.makeText(GanadorActivity.this, ""+position, Toast.LENGTH_LONG+Toast.LENGTH_LONG+Toast.LENGTH_LONG).show();
                String name="";
                switch (position){
                    case 0:
                        name="Presidente "+listUsuario.get(position).getNombre();
                        break;
                    case 1:
                        name="Vicepresidente "+listUsuario.get(position).getNombre();
                        break;
                    case 2:
                        name="Secretario "+listUsuario.get(position).getNombre();
                        break;
                    case 3:
                        name="Tesorero "+listUsuario.get(position).getNombre();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(GanadorActivity.this);
                builder.setTitle("Integrantes");
                builder.setView(view);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    private int fnGetMayorVotos(List<Planchas> planchasList) {
        int pos=-1;
        float mayor=-9999;
        for (int i=0;i<planchasList.size();i++) {
            if (planchasList.get(i).getVotos()>mayor){
                pos=i;
            }
        }
        return pos;
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
                Intent intent = new Intent(GanadorActivity.this, UsuarioActivity.class);
                intent.putExtra("carnet", carnet);
                startActivity(intent);
                break;
            case R.id.mnVoto:
                Intent intent1 =new Intent(GanadorActivity.this,InicioActivity.class);
                intent1.putExtra("carnet",carnet);
                startActivity(intent1);
                break;
            case R.id.mnGrafica:
                Intent intent2= new Intent(GanadorActivity.this,GraficaActivity.class);
                Bundle x = new Bundle();
                x.putSerializable("Planchas", (Serializable) planchasList);
                intent2.putExtras(x);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
