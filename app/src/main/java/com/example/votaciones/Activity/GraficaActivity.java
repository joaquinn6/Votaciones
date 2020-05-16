package com.example.votaciones.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.example.votaciones.R;
import com.example.votaciones.objetos.Plancha;
import com.example.votaciones.objetos.Planchas;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class GraficaActivity extends AppCompatActivity {
    private List<Plancha>planchasList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafica);

        Bundle extras= getIntent().getExtras();
        if(extras!=null){
            planchasList = (List<Plancha>) extras.getSerializable("Plancha");
            //Toast.makeText(this, "Entre "+planchasList.size(), Toast.LENGTH_LONG+Toast.LENGTH_LONG+Toast.LENGTH_LONG).show();
        }
            PieChart pcPorcentaje = findViewById(R.id.pc);

        List<PieEntry> entries = new ArrayList<>();
        List<Integer> colores = new ArrayList<Integer>();
        for ( Plancha plancha : planchasList){
            entries.add(new PieEntry(plancha.getVotos(),plancha.getAcronimo()));
            colores.add(Color.parseColor(plancha.getColor()));
        }
        PieDataSet set = new PieDataSet(entries, "Resultados: ");
        set.setColors(colores);
        set.setValueTextColor(R.color.white);
        set.setValueTextSize(19);



        Legend legend= pcPorcentaje.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setFormSize(18f);
        legend.setDirection(Legend.LegendDirection.RIGHT_TO_LEFT);
        legend.setFormToTextSpace(8);

        Description description = pcPorcentaje.getDescription();
        description.setText("Porcentajes de votaciones a tiempo real");
        description.setTextSize(15);

        PieData data = new PieData(set);
        pcPorcentaje.setData(data);
        pcPorcentaje.setHoleRadius(44);
        pcPorcentaje.setDrawRoundedSlices(true);
        pcPorcentaje.invalidate(); // refresh
    }
}

