package com.example.votaciones.RecyclerViews;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.votaciones.R;
import com.example.votaciones.objetos.Planchas;

import java.text.DecimalFormat;
import java.util.List;

public class RvAdaptadorPlancha extends RecyclerView.Adapter<RvAdaptadorPlancha.PlanchaHolder> {

    List<Planchas> planchasList;
    private final OnItemClickListener onItemClickListener;
    private Context context;
    public interface OnItemClickListener{
        void OnItemClick(int posicion, long id);
    }
    public RvAdaptadorPlancha(List<Planchas> planchasList, OnItemClickListener onItemClickListener) {
        this.planchasList = planchasList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public PlanchaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_plancha, parent, false);
        PlanchaHolder planchaHolder = new PlanchaHolder(view);
        return planchaHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlanchaHolder holder, int position) {
        holder.tvNombrePlancha.setText(planchasList.get(position).getNombrePlancha());
        holder.tvAcronimo.setText(planchasList.get(position).getAcronimo());
        DecimalFormat format= new DecimalFormat("#.##");
        holder.tvVotos.setText(String.valueOf(format.format(planchasList.get(position).getVotos()))+"%");
        holder.cv.setCardBackgroundColor(Color.parseColor(planchasList.get(position).getColor()));
        Glide.with(context).load("http://10.0.2.2:8000/uploads/images/"+ planchasList.get(position).getImagen()).into(holder.ip);
    }

    @Override
    public int getItemCount() {
        return planchasList.size();
    }

    public class PlanchaHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tvNombrePlancha;
        private TextView tvAcronimo;
        private TextView tvVotos;
        private Button btnPropuestas;
        private Button btnIntegranes;
        private CardView cv;
        private ImageView ip;
        private ImageView ivFacebook;
        private ImageView ivTwitter;
        private ImageView ivInstagram;


        public PlanchaHolder(@NonNull View itemView) {
            super(itemView);
            tvNombrePlancha = itemView.findViewById(R.id.tvNombrePlancha);
            tvAcronimo=itemView.findViewById(R.id.tvAcronimo);
            tvVotos=itemView.findViewById(R.id.tvPorcentajeVotos);
            btnIntegranes=itemView.findViewById(R.id.btnIntegrantes);
            btnPropuestas=itemView.findViewById(R.id.btnPropuestas);
            cv=itemView.findViewById(R.id.cvPlancha);
            ip=itemView.findViewById(R.id.ivPlancha);
            ivFacebook= itemView.findViewById(R.id.ivFacebook);
            ivInstagram=itemView.findViewById(R.id.ivInstagram);
            ivTwitter=itemView.findViewById(R.id.ivTwitter);

            ivFacebook.setOnClickListener(this);
            ivTwitter.setOnClickListener(this);
            ivInstagram.setOnClickListener(this);
            btnPropuestas.setOnClickListener(this);
            btnIntegranes.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onItemClickListener.OnItemClick(getAdapterPosition(), view.getId());
        }
    }

}
