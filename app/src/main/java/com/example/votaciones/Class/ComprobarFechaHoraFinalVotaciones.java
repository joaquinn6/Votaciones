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

    private Context context;
    public static String FECHA="FechaGanador";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date dateHoraVotar,dateHoraActual;

    public ComprobarFechaHoraFinalVotaciones(Context context) {
        this.context=context;
    }

    public Boolean fnFechaInscripcion(String fechaFinInscrip){
        boolean check = false;
        Calendar fechaActual =Calendar.getInstance();
        fechaActual.set(Calendar.HOUR_OF_DAY,0);
        fechaActual.set(Calendar.MINUTE,0);
        fechaActual.set(Calendar.SECOND,0);
        String fechaFinInscripcion=fechaFinInscrip;
        try {
            Date strDate=sdf.parse(fechaFinInscripcion);
            if (fechaActual.getTime().after(strDate)){
                check=true;
            }else check=false;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return check;
    }

    public Boolean fnVerificarFechaHora(String fechaVota,String horaVota,String horaIni){
        boolean check = false;
        Calendar fechaActual =Calendar.getInstance();
        Date dateHoraVotar,dateHoraActual,dateHoraInicio;
        fechaActual.set(Calendar.HOUR_OF_DAY,0);
        fechaActual.set(Calendar.MINUTE,0);
        fechaActual.set(Calendar.SECOND,0);
        //SharedPreferences spFecha=context.getSharedPreferences(FECHA, MODE_PRIVATE);
        String fechaWin=fechaVota;
        try {
            Date strDate=sdf.parse(fechaWin);
            if (fechaActual.getTime().before(strDate)){
                check=false;
            }else {
                String horaVotar= horaVota;
                String horaInicio=horaIni;
                Calendar c = Calendar.getInstance();
                String horaActual=c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);
                DateFormat df=new SimpleDateFormat("HH:mm");
                if(fechaActual.getTime().toString().equals(strDate.toString())) {
                    try {
                        dateHoraVotar = df.parse(horaVotar);
                        dateHoraActual = df.parse(horaActual);
                        dateHoraInicio=df.parse(horaInicio);
                        if (dateHoraActual.before(dateHoraVotar) && dateHoraActual.after(dateHoraInicio)) {
                            check = true;
                        } else {
                            check = false;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }else check=true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return check;
    }
    public Boolean fnMostrarGanador(String fechaVota,String horaVota){
        boolean check = false;
        Calendar fechaActual =Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateHoraVotar,dateHoraActual;
        fechaActual.set(Calendar.HOUR_OF_DAY,0);
        fechaActual.set(Calendar.MINUTE,0);
        fechaActual.set(Calendar.SECOND,0);
        //SharedPreferences spFecha=context.getSharedPreferences(FECHA, MODE_PRIVATE);
        String fechaWin=fechaVota;
        try {
            Date strDate=sdf.parse(fechaWin);
            String stringFechaActual=fechaActual.getTime().toString();
            String stringStrDate=strDate.toString();
            if (fechaActual.getTime().after(strDate)){
                if (stringFechaActual.equals(stringStrDate)) {
                    String horaVotar = horaVota;
                    Calendar c = Calendar.getInstance();
                    String horaActual = c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
                    DateFormat df = new SimpleDateFormat("HH:mm");
                    try {
                        dateHoraVotar = df.parse(horaVotar);
                        dateHoraActual = df.parse(horaActual);
                        if (dateHoraActual.after(dateHoraVotar) || dateHoraActual.equals(dateHoraVotar)) {
                            check = true;
                        } else {
                            check = false;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    check = true;
                }

            }else {
                check=false;
            }

        } catch (ParseException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();;
        }
        //Toast.makeText(context, "Aqui en fnDiaHoraLlego "+check, Toast.LENGTH_LONG).show();

        return check;
    }
    public Boolean fnDiaHoraLlego(String fechaVota,String horaVota){
        boolean check = false;
        Calendar fechaActual =Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateHoraVotar,dateHoraActual;
        fechaActual.set(Calendar.HOUR_OF_DAY,0);
        fechaActual.set(Calendar.MINUTE,0);
        fechaActual.set(Calendar.SECOND,0);
        //SharedPreferences spFecha=context.getSharedPreferences(FECHA, MODE_PRIVATE);
        String fechaWin=fechaVota;
        try {
            Date strDate=sdf.parse(fechaWin);
            String stringFechaActual=fechaActual.getTime().toString();
            String stringStrDate=strDate.toString();

            if (stringFechaActual.equals(stringStrDate)){
                String horaVotar=horaVota;//spFecha.getString("horaVotar","");
                Calendar c = Calendar.getInstance();
                String horaActual=c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);
                DateFormat df=new SimpleDateFormat("HH:mm");
                try {
                    dateHoraVotar=df.parse(horaVotar);
                    dateHoraActual=df.parse(horaActual);
                    if (dateHoraActual.equals(dateHoraVotar)){
                        check= true;
                    }else {
                        check= false;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else {
                check= false;
            }

        } catch (ParseException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();;
        }

        return check;
    }
    public Boolean fnAntesInscripcion(String fechaInscripcion){
        boolean check = false;
        Calendar fechaActual =Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //Date dateHoraVotar,dateHoraActual;
        fechaActual.set(Calendar.HOUR_OF_DAY,0);
        fechaActual.set(Calendar.MINUTE,0);
        fechaActual.set(Calendar.SECOND,0);
        //SharedPreferences spFecha=context.getSharedPreferences(FECHA, MODE_PRIVATE);
        String fechaWin=fechaInscripcion;
        try {
            Date strDate=sdf.parse(fechaWin);
            if (fechaActual.getTime().before(strDate)){
                check =true;
            }
            else check =false;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return check;
    }
    
}
