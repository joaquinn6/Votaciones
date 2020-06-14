package com.example.votaciones.Class;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.provider.SyncStateContract;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.votaciones.Activity.GanadorActivity;
import com.example.votaciones.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class IntentServiNotificacion extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    private final static String CHANNEL_ID="Winner";
    private final static int NOTIFICACION_ID=0;
    private PendingIntent pi;
    private static final String FECHA="FechaGanador";
    public Context context=this;
    public IntentServiNotificacion() {
        super("IntentServiNotificacion");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //Toast.makeText(context, "Entramos IntentServiNotificacion", Toast.LENGTH_LONG).show();
        if (intent!=null) {
            enviarNotificacion();
        }
    }
    private void enviarNotificacion() {
        SharedPreferences spFecha = context.getSharedPreferences(FECHA, context.MODE_PRIVATE);
        String fechaWin = spFecha.getString("fechaVotar", "");
        String horaWin = spFecha.getString("horaVotar", "");
        //Toast.makeText(context,fechaWin+" ||| "+horaWin,Toast.LENGTH_LONG).show();
        ComprobarFechaHoraFinalVotaciones c = new ComprobarFechaHoraFinalVotaciones(context);
        boolean bucle=true;
        while (bucle) {
            Toast.makeText(context, "Entre seguro", Toast.LENGTH_LONG).show();
            if (c.fnDiaHoraLlego(fechaWin,horaWin)) {
                Toast.makeText(context, "Entre al if", Toast.LENGTH_LONG).show();
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
                bucle=false;
                onDestroy();
            }//else
            //Toast.makeText(context, "No entre", Toast.LENGTH_LONG).show();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    /*private Boolean fnVerificarFechaHora2(){
        boolean check = false;
        Calendar fechaActual =Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateHoraVotar,dateHoraActual;
        fechaActual.set(Calendar.HOUR_OF_DAY,0);
        fechaActual.set(Calendar.MINUTE,0);
        fechaActual.set(Calendar.SECOND,0);
        SharedPreferences spFecha=context.getSharedPreferences(FECHA, MODE_PRIVATE);
        String fechaWin=spFecha.getString("fechaVotar","");

        try {
            Date strDate=sdf.parse(fechaWin);
            String stringFechaActual=fechaActual.getTime().toString();
            String stringStrDate=strDate.toString();

            if (stringFechaActual.equals(stringStrDate)){
                String horaVotar=spFecha.getString("horaVotar","");
                Calendar c = Calendar.getInstance();
                String horaActual=c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);
                DateFormat df=new SimpleDateFormat("HH:mm");
                try {
                    dateHoraVotar=df.parse(horaVotar);
                    dateHoraActual=df.parse(horaActual);
                    if (dateHoraActual.equals(dateHoraVotar)){
                        Toast.makeText(context, dateHoraActual+" True "+dateHoraVotar, Toast.LENGTH_LONG+Toast.LENGTH_LONG+Toast.LENGTH_LONG+Toast.LENGTH_LONG).show();
                        check= true;
                    }else {
                        Toast.makeText(context, dateHoraActual+" Falso "+dateHoraVotar, Toast.LENGTH_LONG+Toast.LENGTH_LONG+Toast.LENGTH_LONG+Toast.LENGTH_LONG).show();
                        check= false;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(context, strDate+" |Sali| "+fechaActual.getTime(), Toast.LENGTH_LONG).show();
                check= false;
            }

        } catch (ParseException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();;
        }
        return check;
    }*/
}
