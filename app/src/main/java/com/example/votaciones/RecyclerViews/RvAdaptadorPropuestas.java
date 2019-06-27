package com.example.votaciones.RecyclerViews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votaciones.R;
import com.example.votaciones.objetos.Propuesta;

import java.util.List;

public class RvAdaptadorPropuestas extends RecyclerView.Adapter<RvAdaptadorPropuestas.PropuestaHolder> {

    List<Propuesta> propuestaList;

    public RvAdaptadorPropuestas(List<Propuesta> propuestaList) {
        this.propuestaList = propuestaList;
    }

    @NonNull
    @Override
    public PropuestaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_propuesta, parent, false);
        PropuestaHolder propuestaHolder= new PropuestaHolder(view);
        return propuestaHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PropuestaHolder holder, int position) {
        holder.tvNombrePropuesta.setText(propuestaList.get(position).getTitulo());
        holder.tvContenido.setText(propuestaList.get(position).getContenido());
    }

    @Override
    public int getItemCount() {
        return propuestaList.size();
    }

    public class PropuestaHolder extends RecyclerView.ViewHolder{
        TextView tvNombrePropuesta;
        TextView tvContenido;

        public PropuestaHolder(@NonNull View itemView) {
            super(itemView);
            tvContenido=itemView.findViewById(R.id.tvContenido);
            tvNombrePropuesta=itemView.findViewById(R.id.tvNombrePropuesta);
        }
    }
}
