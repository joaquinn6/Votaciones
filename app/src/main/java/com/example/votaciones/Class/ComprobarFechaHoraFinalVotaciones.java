package com.example.votaciones.Class;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class ComprobarFechaHoraFinalVotaciones {
    private String fechaVota;
    private String horaVota;
    private Context context;
    public static String FECHA="FechaGanador";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date dateHoraVotar,dateHoraActual;

    public ComprobarFechaHoraFinalVotaciones(String fechaVota, String horaVota,Context context) {
        this.fechaVota = fechaVota;
        this.horaVota = horaVota;
        this.context=context;
    }

    public Boolean fnVerificarFechaHora(){
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
            //Toast.makeText(this, ""+fechaActual.getTime(), Toast.LENGTH_LONG).show();
            if (fechaActual.getTime().before(strDate)){
                check= false;
            }else {
                //Toast.makeText(this, fechaWin+" true "+fechaActual, Toast.LENGTH_LONG+Toast.LENGTH_LONG+Toast.LENGTH_LONG+Toast.LENGTH_LONG).show();
                String horaVotar=spFecha.getString("horaVotar","");
                Calendar c = Calendar.getInstance();
                String horaActual=c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);
                DateFormat df=new SimpleDateFormat("HH:mm");
                try {
                    dateHoraVotar=df.parse(horaVotar);
                    dateHoraActual=df.parse(horaActual);
                    //Toast.makeText(this, horaActual+" Falso "+horaVotar, Toast.LENGTH_LONG+Toast.LENGTH_LONG+Toast.LENGTH_LONG+Toast.LENGTH_LONG).show();
                    if (dateHoraActual.before(dateHoraVotar)){
                        //Toast.makeText(this, dateHoraActual+" Falso "+dateHoraVotar, Toast.LENGTH_LONG+Toast.LENGTH_LONG+Toast.LENGTH_LONG+Toast.LENGTH_LONG).show();
                        check= false;
                    }else {
                        //Toast.makeText(this, dateHoraActual+" True "+dateHoraVotar, Toast.LENGTH_LONG+Toast.LENGTH_LONG+Toast.LENGTH_LONG+Toast.LENGTH_LONG).show();
                        check= true;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return check;
    }
    public Boolean fnDiaHoraLlego(){
        boolean check = false;
        Calendar fechaActual =Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateHoraVotar,dateHoraActual;
        fechaActual.set(Calendar.HOUR_OF_DAY,0);
        fechaActual.set(Calendar.MINUTE,0);
        fechaActual.set(Calendar.SECOND,0);
        SharedPreferences spFecha=context.getSharedPreferences(FECHA, MODE_PRIVATE);
        String fechaWin=fechaVota;//spFecha.getString("fechaVotar","");
        try {
            Date strDate=sdf.parse(fechaWin);
            String stringFechaActual=fechaActual.getTime().toString();
            String stringStrDate=strDate.toString();

            if (stringFechaActual.equals(stringStrDate)){
                String horaVotar=horaVota;//spFecha.getString("horaVotar","");
                Calendar c = Calendar.getInstance();
                String horaActual=c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);
                DateFormat df=new SimpleDateFormat("HH:mm");
                //Toast.makeText(context, "Entre al dia", Toast.LENGTH_SHORT).show();
                try {
                    dateHoraVotar=df.parse(horaVotar);
                    dateHoraActual=df.parse(horaActual);
                    if (dateHoraActual.equals(dateHoraVotar)){
                        //Toast.makeText(context, dateHoraActual+" True "+dateHoraVotar, Toast.LENGTH_LONG+Toast.LENGTH_LONG+Toast.LENGTH_LONG+Toast.LENGTH_LONG).show();
                        check= true;
                    }else {
                        //Toast.makeText(context, dateHoraActual+" Falso "+dateHoraVotar, Toast.LENGTH_LONG+Toast.LENGTH_LONG+Toast.LENGTH_LONG+Toast.LENGTH_LONG).show();
                        check= false;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else {
                //Toast.makeText(context, strDate+" |Sali| "+fechaActual.getTime(), Toast.LENGTH_LONG).show();
                check= false;
            }

        } catch (ParseException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();;
        }
        //Toast.makeText(context, "Aqui en fnDiaHoraLlego "+check, Toast.LENGTH_LONG).show();

        return check;
    }
}
