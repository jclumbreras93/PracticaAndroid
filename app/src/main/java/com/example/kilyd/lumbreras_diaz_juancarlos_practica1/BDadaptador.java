package com.example.kilyd.lumbreras_diaz_juancarlos_practica1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BDadaptador {
    BaseDatos baseDatos;

    Context c;

    public BDadaptador (Context context){

        c = context;
        baseDatos = new BaseDatos(c);
    }

    public void insertar(String nombre, int puntuacion, String dificultad, String feh){

        SQLiteDatabase miBD = baseDatos.getWritableDatabase();

        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre",nombre);
        contenedor.put("puntuacion",puntuacion);
        contenedor.put("dificultad",dificultad);
        contenedor.put("horafecha",feh);

        miBD.insert("jugador",null,contenedor);
        baseDatos.close();


    }


    public Cursor consultar() {

        SQLiteDatabase miBD = baseDatos.getReadableDatabase();

        Cursor c =miBD.rawQuery("select * from jugador ORDER BY puntuacion DESC Limit 10", null);
        //miBD.close();

        return c;
    }

    public String eliminar(int id){

        SQLiteDatabase miBD = baseDatos.getWritableDatabase();


        long resul = miBD.delete("jugador","_id=?",new String[]{Integer.toString(id)});


        return String.valueOf(resul);
    }
}
