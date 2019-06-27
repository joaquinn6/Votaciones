package com.example.votaciones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.votaciones.RecyclerViews.RvAdapterPropuestas;
import com.example.votaciones.objetos.Propuesta;

import java.util.List;

public class PropuestasActivity extends AppCompatActivity {
    private List<Propuesta> propuestaList;
    private RvAdapterPropuestas adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propuestas);

        RecyclerView rvPropuestas = findViewById(R.id.rvPropuestas);
        Bundle extras= getIntent().getExtras();
        if(extras!=null){
            propuestaList = (List<Propuesta>) extras.getSerializable("Propuestas");
            adapter= new RvAdapterPropuestas(propuestaList);
            GridLayoutManager manager = new GridLayoutManager(this, 1);
            rvPropuestas.setLayoutManager(manager);
            rvPropuestas.setAdapter(adapter);
            Toast.makeText(this, "Recibido", Toast.LENGTH_SHORT).show();
        }
    }
}
