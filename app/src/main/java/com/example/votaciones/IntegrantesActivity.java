package com.example.votaciones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.votaciones.RecyclerViews.RvAdaptadorIntegrantes;
import com.example.votaciones.RecyclerViews.RvAdaptadorPropuestas;
import com.example.votaciones.objetos.Integrante;
import com.example.votaciones.objetos.Propuesta;

import java.util.ArrayList;
import java.util.List;

public class IntegrantesActivity extends AppCompatActivity {

    private List<Integrante> integranteList = new ArrayList<>();
    RvAdaptadorIntegrantes adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integrantes);

        RecyclerView rvIntegrantes= findViewById(R.id.rvIntegrantes);
        Bundle extras= getIntent().getExtras();
        if(extras!=null){
            integranteList = (List<Integrante>) extras.getSerializable("Integrantes");

            RvAdaptadorIntegrantes.OnItemClickListener onItemClickListener = new RvAdaptadorIntegrantes.OnItemClickListener() {
                @Override
                public void OnItemClick(int posicion) {
                    Toast.makeText(IntegrantesActivity.this, integranteList.get(posicion).getUsuario().getAcercade(), Toast.LENGTH_SHORT).show();
                }
            };
            adapter= new RvAdaptadorIntegrantes(integranteList,onItemClickListener);
            GridLayoutManager manager = new GridLayoutManager(this, 1);
            rvIntegrantes.setLayoutManager(manager);
            rvIntegrantes.setAdapter(adapter);
        }
    }
}
