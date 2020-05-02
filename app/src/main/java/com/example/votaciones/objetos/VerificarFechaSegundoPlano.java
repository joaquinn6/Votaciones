package com.example.votaciones.objetos;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.votaciones.Activity.GanadorActivity;
import com.example.votaciones.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class VerificarFechaSegundoPlano extends AsyncTask<Void,Integer,Boolean> {
    private Context context;

    private final static String CHANNEL_ID="Winner";
    private final static int NOTIFICACION_ID=0;
    private PendingIntent pi;
    private static final String FECHA="FechaGanador";

    public VerificarFechaSegundoPlano(Context _context) {
        context=_context;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        ejecutar();
        //Toast.makeText(context, "Mensaje PUSH", Toast.LENGTH_SHORT).show();
        enviarNotificacion();
    }

    private void enviarNotificacion() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar fechaActual= Calendar.getInstance();
        fechaActual.set(Calendar.HOUR_OF_DAY,0);
        fechaActual.set(Calendar.MINUTE,0);
        fechaActual.set(Calendar.SECOND,0);
        SharedPreferences spFecha=context.getSharedPreferences(FECHA, context.MODE_PRIVATE);
        String fechaWin=spFecha.getString("fechaVotar","");
        try {
            Date strDate=sdf.parse(fechaWin);
            if(fechaActual.equals(strDate)) {
                Intent intent = new Intent(context, GanadorActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                pi = PendingIntent.getActivity(context, 0, intent, 0);

                NotificationCompat.Builder builderNtf = new NotificationCompat.Builder(context.getApplicationContext(), CHANNEL_ID);
                builderNtf.setSmallIcon(R.drawable.ic_stat_ntf);
                builderNtf.setContentTitle("Ganador");
                builderNtf.setContentText("El momento llego, Puedes ver al Ganador ya");
                builderNtf.setColor(Color.parseColor("#1B5B94"));
                builderNtf.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                builderNtf.setLights(Color.parseColor("#1B5B94"), 1000, 1000);
                builderNtf.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
                builderNtf.setDefaults(Notification.DEFAULT_SOUND);
                builderNtf.setContentIntent(pi);

                NotificationManagerCompat nmtc = NotificationManagerCompat.from(context.getApplicationContext());
                nmtc.notify(NOTIFICACION_ID, builderNtf.build());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        for (int i=0;i<=2;i++)
            hilo();
        return true;
    }
    private void hilo (){
        /*int segundo=43200000;
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
    private void ejecutar(){
        VerificarFechaSegundoPlano verificarFechaSegundoPlano= new VerificarFechaSegundoPlano(context);
        verificarFechaSegundoPlano.execute();
    }

}
