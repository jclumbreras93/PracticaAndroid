package com.example.kilyd.lumbreras_diaz_juancarlos_practica1;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GameActivity extends AppCompatActivity {

    TextView puntos,tiempo;
    Button click;
    int cont=0;
    CountDownTimer temp;
    BDadaptador miBDadaptador;
    String nombre;
    String dificultad;
    SharedPreferences preferencias;
    SimpleDateFormat formatohoraf;
    Calendar fecha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_game);
        click = findViewById(R.id.click);
        puntos = findViewById(R.id.puntos);
        tiempo = findViewById(R.id.tiempo);
        puntos.setText(""+cont);
        click.setEnabled(true);
        jugar();
        preferencias = PreferenceManager.getDefaultSharedPreferences(this);
        nombre = preferencias.getString("nombre","jugador");
        dificultad = preferencias.getString("dificultad","facil");
         formatohoraf=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
         fecha = Calendar.getInstance();
        miBDadaptador = new BDadaptador(GameActivity.this);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cont++;
                puntos.setText(""+cont);
            }
        });

    }

    @Override
    public void onBackPressed() {
        temp.cancel();
        AlertDialog.Builder alerta = new AlertDialog.Builder(GameActivity.this);
        alerta.setTitle("Salir")
                .setMessage("¿Estas seguro que desea salir?")
                .setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(GameActivity.this, MenuActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        GameActivity.this.finish();
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        temp.start();


                    }
                });
        alerta.show();
    }

    private void jugar() {
       temp= new CountDownTimer(10000,1000){
            @Override
            public void onTick(long segundos) {
                tiempo.setText(""+segundos/1000);
            }

            @Override
            public void onFinish() {
                click.setEnabled(false);
                miBDadaptador.insertar(nombre,cont,dificultad,formatohoraf.format(fecha.getTime()));

                AlertDialog.Builder alerta = new AlertDialog.Builder(GameActivity.this);
                alerta.setTitle("Fin de Juego")
                        .setPositiveButton("Jugar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                jugar();
                                cont=0;
                                click.setEnabled(true);
                                puntos.setText(""+cont);
                            }
                        })
                        .setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(GameActivity.this, ScoreBoardActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                GameActivity.this.finish();
                                startActivity(intent);
                            }
                        });
                alerta.show();

            }

        }.start();
    }
}
