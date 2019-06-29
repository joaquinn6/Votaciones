package com.example.votaciones.RecyclerViews;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.votaciones.R;
import com.example.votaciones.objetos.Planchas;

import java.util.List;

public class RVAdaptadorVotar extends RecyclerView.Adapter<RVAdaptadorVotar.VotarHolder>{

    private List<Planchas> planchasList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void OnItemClick(int posicion);
    }
    public RVAdaptadorVotar(List<Planchas> planchasList, OnItemClickListener onItemClickListener) {
        this.planchasList = planchasList;
        this.onItemClickListener = onItemClickListener;
    }


    @NonNull
    @Override
    public VotarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context=parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.item_votar,parent,false);
        VotarHolder votarHolder= new VotarHolder(view);
        return votarHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VotarHolder holder, int position) {
        holder.tvNombrePlancha.setText(planchasList.get(position).getAcronimo());
        holder.cvVotar.setCardBackgroundColor(Color.parseColor(planchasList.get(position).getColor()));
        Glide.with(context).load("http://10.0.2.2:8000/uploads/images/"+ planchasList.get(position).getImagen()).into(holder.ivPlancha);
    }

    @Override
    public int getItemCount() {
        return planchasList.size();
    }

    public class VotarHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvNombrePlancha;
        ImageView ivPlancha;
        CardView cvVotar;


        public VotarHolder(@NonNull View itemView) {
            super(itemView);
            tvNombrePlancha= itemView.findViewById(R.id.tvNombrePlacha);
            ivPlancha= itemView.findViewById(R.id.ivPlancha);
            cvVotar= itemView.findViewById(R.id.cvVotar);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.OnItemClick(getAdapterPosition());
        }
    }
}
