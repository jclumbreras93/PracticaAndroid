package com.example.kilyd.lumbreras_diaz_juancarlos_practica1;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class ScoreBoardActivity extends AppCompatActivity {

    BDadaptador bDadaptador;
    Cursor cursorResultado;
    SimpleCursorAdapter adaptadorLista;
    ListView listaPuntuacion;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_scoreboard);

        bDadaptador = new BDadaptador(getApplicationContext());
        cursorResultado=bDadaptador.consultar();

        //Preparar el adaptador del listView
        String[]from=new String[]{"nombre","puntuacion"};
        int[]to=new int[]{R.id.nombreJug,R.id.puntosJug};
        adaptadorLista=new SimpleCursorAdapter(getApplicationContext(),R.layout.fila,cursorResultado,from,to);
        //aplicarlo a la lista

        listaPuntuacion = findViewById(R.id.lista);
        listaPuntuacion.setAdapter(adaptadorLista);

        listaPuntuacion.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Sacar el id del elemento a eliminar
                cursorResultado.moveToPosition(position);
                final int idd = cursorResultado.getInt(0);
                AlertDialog.Builder alerta = new AlertDialog.Builder(ScoreBoardActivity.this);
                alerta.setTitle("Mensaje")
                        .setMessage("¿Estas seguro que desea eliminar?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                bDadaptador.eliminar(idd);
                                cursorResultado=bDadaptador.consultar();
                                adaptadorLista.swapCursor(cursorResultado);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                alerta.show();

                return true;
            }
        });
        listaPuntuacion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursorResultado.moveToPosition(position);
                String mensaje ="Nombre: "+cursorResultado.getString(1)+
                        "\nPuntuacion: "+cursorResultado.getInt(2)+"\nDificultad: "+cursorResultado.getString(3)
                        +"\nFecha/Hora: "+cursorResultado.getString(4);
                Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        intent = new Intent(ScoreBoardActivity.this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ScoreBoardActivity.this.finish();
        startActivity(intent);
    }
}
