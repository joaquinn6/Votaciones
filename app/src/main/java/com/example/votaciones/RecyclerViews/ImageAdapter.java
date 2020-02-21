package com.example.votaciones.RecyclerViews;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.votaciones.Activity.GanadorActivity;
import com.example.votaciones.Api.ServicioApi;
import com.example.votaciones.R;
import com.example.votaciones.objetos.Foto;
import com.example.votaciones.objetos.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends PagerAdapter {
    private Context mContext;
    private List<String> mlistaImagenes;
    //private List<String> mlistaNombre;
    private List<Usuario> mListUsuario;
    public ImageAdapter(Context context, List<Usuario> listUsuario){
        mContext=context;
        mListUsuario=listUsuario;
        mlistaImagenes=new ArrayList<>();
        //mlistaNombre=new ArrayList<>();
        for (Usuario u :mListUsuario) {
            mlistaImagenes.add(ServicioApi.HTTP+"/uploads/images/"+u.getFoto());
        }
    }
    @Override
    public int getCount() {
        return mlistaImagenes.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        ImageView imageView =new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //imageView.setImageURI();
        Glide.with(mContext).load(mlistaImagenes.get(position)).into(imageView);
        //imageView.setImageResource(mImages[position]);
        TextView etNombre =new TextView(mContext);
        container.addView(imageView,0);
        //container.addView(etNombre,1);
        return imageView;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView)object);
    }


}
