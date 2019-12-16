package com.example.votaciones.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import com.example.votaciones.R;
import com.example.votaciones.objetos.Token;

import java.util.Map;


public class CargaActivity extends AppCompatActivity {

    private final String SESION="VariabesDeSesion";
    private  final Token token=new Token();
    private static int TIME=1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_carga);

        SharedPreferences spSesion=getSharedPreferences(SESION, MODE_PRIVATE);
        Map<String, ?> recuperarTexto = spSesion.getAll();
        if(!((Map) recuperarTexto).isEmpty()) {
            token.setToken(spSesion.getString("token", null));
            if (!token.getToken().isEmpty()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(CargaActivity.this,
                                InicioActivity.class);
                        //Intent is used to switch from one activity to another.
                        //i.putExtra("carnet", usuario.getCarnet());
                        startActivity(i);
                        //invoke the SecondActivity.

                        finish();
                        //the current activity will get finished.
                    }
                }, TIME);
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(CargaActivity.this,
                                MainActivity.class);
                        //Intent is used to switch from one activity to another.

                        startActivity(i);
                        //invoke the SecondActivity.

                        finish();
                        //the current activity will get finished.
                    }
                }, TIME);
            }

        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(CargaActivity.this,
                            MainActivity.class);
                    //Intent is used to switch from one activity to another.

                    startActivity(i);
                    //invoke the SecondActivity.

                    finish();
                    //the current activity will get finished.
                }
            }, TIME);
        }

    }
}
