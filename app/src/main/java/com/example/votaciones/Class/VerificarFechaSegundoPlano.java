package com.example.votaciones.Class;

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

import com.example.votaciones.Activity.CargaActivity;
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
    SharedPreferences notificacion;

    public VerificarFechaSegundoPlano(Context _context) {
        context=_context;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        ejecutar();
        notificacion=context.getSharedPreferences("Notificacion",context.MODE_PRIVATE);
        enviarNotificacion();
    }
    private void enviarNotificacion() {
        SharedPreferences.Editor e=notificacion.edit();
        e.putBoolean("llegoNotificacion",false);
        e.commit();
        SharedPreferences spFecha = context.getSharedPreferences(FECHA, context.MODE_PRIVATE);
        String fechaWin = spFecha.getString("fechaVotar", "");
        String horaWin = spFecha.getString("horaVotar", "");
        ComprobarFechaHoraFinalVotaciones comprobarFechaHoraFinalVotaciones = new ComprobarFechaHoraFinalVotaciones(context);
        //while (bucle) {
            if (comprobarFechaHoraFinalVotaciones.fnDiaHoraLlego(fechaWin, horaWin) /*&&  bucle*/) {
                if (!notificacion.getBoolean("llegoNotificacion",false)) {
                    Intent intent = new Intent(context, GanadorActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    pi = PendingIntent.getActivity(context, 0, intent, 0);

                    NotificationCompat.Builder builderNtf = new NotificationCompat.Builder(context.getApplicationContext(), CHANNEL_ID);
                    builderNtf.setSmallIcon(R.drawable.ic_logo1);
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
                    e.putBoolean("llegoNotificacion",true);
                    e.commit();
                    //context.stopService(new Intent(context, ServicioNotificacion.class));
                }
            }
    }




    @Override
    protected Boolean doInBackground(Void... voids) {
        /*for (int i=0;i<3;i++){
            hilo();
        }*/
        return true;
    }
    private void hilo (){
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void ejecutar(){
        VerificarFechaSegundoPlano verificarFechaSegundoPlano= new VerificarFechaSegundoPlano(context);
        verificarFechaSegundoPlano.execute();
        //execute();
    }
    public void finalizar(){
        /*VerificarFechaSegundoPlano verificarFechaSegundoPlano= new VerificarFechaSegundoPlano(context);
        verificarFechaSegundoPlano.cancel(true);*/
        this.cancel(true);
    }

}
