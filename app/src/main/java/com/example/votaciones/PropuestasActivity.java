package com.example.votaciones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.votaciones.RecyclerViews.RvAdaptadorPropuestas;
import com.example.votaciones.objetos.Propuesta;

import java.util.List;

public class PropuestasActivity extends AppCompatActivity {
    private List<Propuesta> propuestaList;
    private RvAdaptadorPropuestas adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propuestas);

        RecyclerView rvPropuestas = findViewById(R.id.rvPropuestas);
        Bundle extras= getIntent().getExtras();
        if(extras!=null){
            propuestaList = (List<Propuesta>) extras.getSerializable("Propuestas");
            adapter= new RvAdaptadorPropuestas(propuestaList);
            GridLayoutManager manager = new GridLayoutManager(this, 1);
            rvPropuestas.setLayoutManager(manager);
            rvPropuestas.setAdapter(adapter);
        }
    }
}
