package com.example.votaciones.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.votaciones.R;
import com.example.votaciones.RecyclerViews.RvAdaptadorIntegrantes;
import com.example.votaciones.objetos.Integrante;

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
                public void OnItemClick(int posicion, long id) {
                    if(id==R.id.ivFacebook){
                        if( integranteList.get(posicion).getUsuario().getFacebook().isEmpty() || integranteList.get(posicion).getUsuario().getFacebook()==null){

                        }else {
                            Uri uri = Uri.parse("http://www.facebook.com/"+integranteList.get(posicion).getUsuario().getFacebook());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }
                    }else if(id==R.id.ivTwitter){
                        if( integranteList.get(posicion).getUsuario().getTwitter().isEmpty() || integranteList.get(posicion).getUsuario().getTwitter()==null){

                        }else {
                            Uri uri = Uri.parse("http://www.twitter.com/"+integranteList.get(posicion).getUsuario().getTwitter());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }
                    }else if(id==R.id.ivInstagram){
                        if( integranteList.get(posicion).getUsuario().getInstagram().isEmpty() || integranteList.get(posicion).getUsuario().getInstagram()==null){

                        }else {
                            Uri uri = Uri.parse("http://www.instagram.com/" + integranteList.get(posicion).getUsuario().getInstagram());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(IntegrantesActivity.this);
                        builder.setTitle("Acercar De");
                        builder.setMessage(integranteList.get(posicion).getUsuario().getAcercade());
                        builder.setPositiveButton("Aceptar",null);

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            };
            adapter= new RvAdaptadorIntegrantes(integranteList,onItemClickListener);
            GridLayoutManager manager = new GridLayoutManager(this, 1);
            rvIntegrantes.setLayoutManager(manager);
            rvIntegrantes.setAdapter(adapter);
        }
    }
}
