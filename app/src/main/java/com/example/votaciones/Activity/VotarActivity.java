package com.example.votaciones.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.votaciones.Api.ServicioApi;
import com.example.votaciones.Class.WorMaNotificacion;
import com.example.votaciones.R;
import com.example.votaciones.RecyclerViews.RVAdaptadorVotar;
import com.example.votaciones.objetos.Planchas;
import com.example.votaciones.objetos.Respuesta;
import com.example.votaciones.objetos.Usuario;
import com.example.votaciones.objetos.Voto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VotarActivity extends AppCompatActivity {
    private RVAdaptadorVotar adapter;
    private List<Planchas>planchasList= new ArrayList<>();
    private String carnet;
    private Usuario usuario;
    private final String SESION="VariabesDeSesion";
    public String fechaVotar, horaVotar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votar);
        SharedPreferences spFecha=getSharedPreferences("FechaGanador",MODE_PRIVATE);
        fechaVotar=spFecha.getString("fechaVotar","");
        horaVotar=spFecha.getString("horaVotar","");
        SharedPreferences spSesion=getSharedPreferences(SESION, MODE_PRIVATE);
        Map<String, ?> recuperarTexto = spSesion.getAll();
        if(!((Map) recuperarTexto).isEmpty()) {
            carnet=spSesion.getString("carnet", null);
        }else
            Toast.makeText(this, "ERROR de sharedPreferences", Toast.LENGTH_SHORT).show();

        Call<Usuario> usuarioCall = ServicioApi.getInstancia(this).obtenerUsuarioCarnet(carnet);
        usuarioCall.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    usuario=response.body();
                } else {
                    Toast.makeText(VotarActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(VotarActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



        RecyclerView rvVotar= findViewById(R.id.rvVotar);

        RVAdaptadorVotar.OnTouchListener onTouchListener=new RVAdaptadorVotar.OnTouchListener() {
            @Override
            public void OnTouch(int posicion, View view) {
                Position=posicion;
                ClipData data  =ClipData.newPlainText("","");
                View.DragShadowBuilder mydrag=new View.DragShadowBuilder(view);
                view.startDrag(data, mydrag, view, 0);
            }
        };

        /*Pasar el evento long click por el constructor del adapter*/
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        rvVotar.setLayoutManager(manager);
        Bundle extras= getIntent().getExtras();
        if(extras!=null){
            planchasList = (List<Planchas>) extras.getSerializable("PlanchasVoto");
            Planchas votoNulo=new Planchas();
            votoNulo.setNombrePlancha("NULO");
            votoNulo.setColor("#f9f4f3");
            votoNulo.setAcronimo("NULO");
            planchasList.add(votoNulo);
            adapter= new RVAdaptadorVotar(planchasList,onTouchListener);
            adapter.notifyDataSetChanged();
        }
        rvVotar.setAdapter(adapter);

        ImageView DragDropVotar=findViewById(R.id.DragDropVotar);

        /*Agregar el evento drag a la imagen*/
        DragDropVotar.setOnDragListener(dragListener);
    }
    /*Variable de posicion del recycler view*/
    private int Position;
    /*evento drag*/
    View.OnDragListener dragListener=new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int dragEvent = event.getAction();
            switch (dragEvent) {
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DROP:
                    final View view = (View) event.getLocalState();
                    final Call<Respuesta> respuestaCall = ServicioApi.getInstancia(VotarActivity.this).verficarVotante(usuario);
                    respuestaCall.enqueue(new Callback<Respuesta>() {
                        @Override
                        public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                            if(response.isSuccessful()){
                                if(response.body().isPermitir()){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(VotarActivity.this);
                                    builder.setTitle("Voto");
                                    builder.setMessage("¿Está seguro que desea votar por esta plancha?");
                                    builder.setNegativeButton("Cancelar",null);
                                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Call<Respuesta> respuestaCall= ServicioApi.getInstancia(VotarActivity.this).votar(new Voto("",planchasList.get(Position).getNombrePlancha()));
                                            respuestaCall.enqueue(new Callback<Respuesta>() {
                                                @Override
                                                public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                                                    if(response.isSuccessful()){
                                                        if(response.body().isPermitir()){
                                                            Call<String> stringCall = ServicioApi.getInstancia(VotarActivity.this).votante(usuario);
                                                            stringCall.enqueue(new Callback<String>() {
                                                                @Override
                                                                public void onResponse(Call<String> call, Response<String> response) {
                                                                    if (response.isSuccessful()){
                                                                        /*Llamar noti*/
                                                                        createNotificaionChannel();
                                                                        noti(fechaVotar,horaVotar);
                                                                        /*fin llamar noti*/
                                                                        Toast.makeText(VotarActivity.this, "Gracias por votar", Toast.LENGTH_SHORT).show();
                                                                        Intent intent = new Intent(VotarActivity.this, InicioActivity.class);
                                                                        intent.putExtra("carnet", carnet);
                                                                        startActivity(intent);
                                                                        finish();
                                                                    }else
                                                                        Toast.makeText(VotarActivity.this, "No se pudo realizar el voto, intente de nuevo por favor.", Toast.LENGTH_SHORT).show();
                                                                }

                                                                @Override
                                                                public void onFailure(Call<String> call, Throwable t) {
                                                                    Toast.makeText(VotarActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                        }else
                                                            Toast.makeText(VotarActivity.this, "No se pudo realizar el voto, intente de nuevo por favor.", Toast.LENGTH_SHORT).show();
                                                    }else {
                                                        Toast.makeText(VotarActivity.this, "Hubo algun problema, intente d enuevo mas tarde", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<Respuesta> call, Throwable t) {
                                                    Toast.makeText(VotarActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    });
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                }else {
                                    Toast.makeText(VotarActivity.this, response.body().getError(), Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(VotarActivity.this, "Error al verificar", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Respuesta> call, Throwable t) {
                            Toast.makeText(VotarActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
            }
            return true;
        }
    };
    /*Creando Notificacion y canal INICIO*/
    private void createNotificaionChannel(){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name="CHANNEL";
            String description="Canal de Notificaciones";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel =new NotificationChannel("Winner",name,importance);
            channel.setDescription(description);
            //channel.setImportance(NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private void noti(String fechaVotar, String llegaHora) {

        int hh = Integer.parseInt(llegaHora.split(":")[0]);
        int mm = Integer.parseInt(llegaHora.split(":")[1]);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar fecha = Calendar.getInstance();
        try {
            fecha.setTime(sdf.parse(fechaVotar));
            fecha.set(Calendar.HOUR, hh);
            fecha.set(Calendar.MINUTE, mm);
            //Toast.makeText(this, "Entre " + fecha.getTime(), Toast.LENGTH_SHORT).show();
            String tag = generateKey();
            Long alertaTime = fecha.getTimeInMillis() - System.currentTimeMillis();
            int rand = (int) (Math.random() * 50 + 1);
            Data data = fnGuardarData("Work Manager", "Soy un detalle", rand);
            WorMaNotificacion.fnGuardarNoti(alertaTime, data, tag,VotarActivity.this);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
    private String generateKey(){
        return UUID.randomUUID().toString();
    }
    private Data fnGuardarData(String titulo, String detalle, int idNoti){
        return new Data.Builder()
                .putString("titulo",titulo)
                .putString("detalle",detalle)
                .putInt("idNoti",idNoti).build();
    }
    /*FIN Noti*/
}
