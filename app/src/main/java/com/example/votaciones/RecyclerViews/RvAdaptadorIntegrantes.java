package com.example.votaciones.RecyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.votaciones.R;
import com.example.votaciones.Api.ServicioApi;
import com.example.votaciones.objetos.Integrante;

import java.util.List;

public class RvAdaptadorIntegrantes extends RecyclerView.Adapter<RvAdaptadorIntegrantes.IntegranteHolder> {

    List<Integrante> integrantesList;
    private final RvAdaptadorIntegrantes.OnItemClickListener onItemClickListener;
    Context context;

    @NonNull
    @Override
    public IntegranteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_integrante, parent, false);
        RvAdaptadorIntegrantes.IntegranteHolder integranteHolder= new IntegranteHolder(view);
        return integranteHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IntegranteHolder holder, int position) {
        holder.tvPuesto.setText(integrantesList.get(position).getPuesto());
        holder.tvCarrera.setText(integrantesList.get(position).getUsuario().getCarrera());
        holder.tvNombre.setText(integrantesList.get(position).getUsuario().getNombre()+" "+ integrantesList.get(position).getUsuario().getApellido());
        Glide.with(context).load(ServicioApi.HTTP+"/uploads/images/"+ integrantesList.get(position).getUsuario().getFoto()).into(holder.ivIntegrante);
    }

    @Override
    public int getItemCount() {
        return integrantesList.size();
    }

    public interface OnItemClickListener{
        void OnItemClick(int posicion, long id);
    }

    public RvAdaptadorIntegrantes(List<Integrante> integrantesList, OnItemClickListener onItemClickListener) {
        this.integrantesList = integrantesList;
        this.onItemClickListener = onItemClickListener;
    }

    public class IntegranteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ivIntegrante;
        private TextView tvNombre;
        private TextView tvCarrera;
        private TextView tvPuesto;
        private ImageView ivFacebook;
        private ImageView ivTwitter;
        private ImageView ivInstagram;


        public IntegranteHolder(@NonNull View itemView) {
            super(itemView);
            ivIntegrante = itemView.findViewById(R.id.ivIntegrante);
            tvNombre= itemView.findViewById(R.id.tvNombreIntegrante);
            tvCarrera= itemView.findViewById(R.id.tvCarrera);
            tvPuesto=itemView.findViewById(R.id.tvPuesto);
            ivFacebook= itemView.findViewById(R.id.ivFacebook);
            ivInstagram=itemView.findViewById(R.id.ivInstagram);
            ivTwitter=itemView.findViewById(R.id.ivTwitter);

            ivFacebook.setOnClickListener(this);
            ivTwitter.setOnClickListener(this);
            ivInstagram.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.OnItemClick(getAdapterPosition(), view.getId());
        }
    }
}
