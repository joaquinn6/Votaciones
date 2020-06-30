package com.example.votaciones.Class;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.votaciones.Activity.GanadorActivity;
import com.example.votaciones.R;

public class ServicioNotificacion extends Service {
    private Context context=this;
    /*private final static String CHANNEL_ID="Winner";
    private final static int NOTIFICACION_ID=0;
    private PendingIntent pi;
    private static final String FECHA="FechaGanador";*/
    VerificarFechaSegundoPlano v;
    @Override
    public void onCreate(){
        //Toast.makeText(context,"OnCreate", Toast.LENGTH_SHORT).show();
        v=new VerificarFechaSegundoPlano(context);
    }
    @Override
    public int onStartCommand(Intent intent,int flag, int id){
        //Toast.makeText(context, "ServicioNotificacion", Toast.LENGTH_SHORT).show();
        v.execute();
        return Service.START_STICKY;
    }
    @Override
    public void onDestroy(){
        /*VerificarFechaSegundoPlano v=new VerificarFechaSegundoPlano(context);
        v.finalizar();*/
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
