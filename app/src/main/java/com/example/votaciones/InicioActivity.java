package com.example.votaciones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.votaciones.RecyclerViews.RvAdaptadorPlancha;
import com.example.votaciones.objetos.Planchas;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicioActivity extends AppCompatActivity {
    private List<Planchas> planchasList = new ArrayList<>();
    private RvAdaptadorPlancha adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

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
                    Toast.makeText(InicioActivity.this, "Propuestas", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(InicioActivity.this, "Integrates", Toast.LENGTH_SHORT).show();
                }
            }
        };

        adapter= new RvAdaptadorPlancha(planchasList, onItemClickListener);
        GridLayoutManager manager = new GridLayoutManager(this, 1);
        rvPlanchas.setLayoutManager(manager);
        rvPlanchas.setAdapter(adapter);


    }

}
