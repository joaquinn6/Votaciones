package com.example.votaciones.Class;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.votaciones.Activity.GanadorActivity;
import com.example.votaciones.R;
import com.example.votaciones.objetos.Configuracion;

import java.util.concurrent.TimeUnit;

public class WorMaNotificacion extends Worker {
    Context context;
    private final static String CHANNEL_ID="Winner";
    private final static int NOTIFICACION_ID=0;
    //public String tag;
    public WorMaNotificacion(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context=context;
    }
    public static void fnGuardarNoti(Long duracion, Data data,String tag,Context context){
        OneTimeWorkRequest noti =new OneTimeWorkRequest.Builder(WorMaNotificacion.class)
                .setInitialDelay(duracion, TimeUnit.MILLISECONDS).addTag(tag)
                .setInputData(data).build();
        WorkManager instance=WorkManager.getInstance();
        instance.enqueue(noti);
    }


        @NonNull
    @Override
    public Result doWork() {
        String titulo=getInputData().getString("titulo");
        String detalle=getInputData().getString("detalle");
        int id=getInputData().getInt("idNoti",0);
        fnCreateNotification();
        return Result.success();
    }
    private void fnCreateNotification(){
        SharedPreferences sp = context.getSharedPreferences("noti", context.MODE_PRIVATE);
        //Toast.makeText(context, ""+sp.getBoolean("notificado", false), (Toast.LENGTH_LONG+Toast.LENGTH_LONG)).show();
        //if (!sp.getBoolean("notificado", false)) {
            PendingIntent pi;
            Intent intent = new Intent(context, GanadorActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            pi = PendingIntent.getActivity(context, 0, intent, 0);

            NotificationCompat.Builder builderNtf = new NotificationCompat.Builder(context.getApplicationContext(), CHANNEL_ID);
            builderNtf.setSmallIcon(R.drawable.ic_logo1);
            builderNtf.setContentTitle("Ganador");
            builderNtf.setContentText("El momento llego, Puedes ver al Ganador ya");
            builderNtf.setColor(Color.parseColor("#1B5B94"));
            builderNtf.setPriority(NotificationCompat.PRIORITY_HIGH);
            builderNtf.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
            builderNtf.setLights(Color.parseColor("#1B5B94"), 1000, 1000);
            builderNtf.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
            builderNtf.setDefaults(Notification.DEFAULT_SOUND);
            builderNtf.setContentIntent(pi);
            NotificationManagerCompat nmtc = NotificationManagerCompat.from(context.getApplicationContext());
            nmtc.notify(NOTIFICACION_ID, builderNtf.build());
            SharedPreferences.Editor edit = sp.edit();
            //WorkManager.getInstance(context).cancelAllWork();
            edit.putBoolean("notificado", true);
            edit.commit();
        //}
    }

}
