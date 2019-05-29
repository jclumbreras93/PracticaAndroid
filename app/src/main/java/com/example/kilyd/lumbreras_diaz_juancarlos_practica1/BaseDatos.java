package com.example.kilyd.lumbreras_diaz_juancarlos_practica1;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class BaseDatos extends SQLiteOpenHelper {

    Context c;
    public BaseDatos(Context context) {
        super(context, "MiBD", null, 1);
        c=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //Creamos la tabla

            db.execSQL("CREATE TABLE jugador " +
                    "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre VARCHAR,puntuacion INT, dificultad VARCHAR, horafecha VARCHAR)");


        }
        catch (SQLException e){
            //Mensaje de error si no se ha ejecutado correctamente
            Toast.makeText(c,""+e,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            //Eliminamos la tabla anterior (si existe)
            db.execSQL("DROP TABLE IF EXISTS jugador");
            //Llamamos a onCreate para que cree la tabla con las nuevas especificaciones
            onCreate(db);
        }
        catch (SQLException e){
            //Mensaje de error si no se ha ejecutado correctamente
            Toast.makeText(c,""+e,Toast.LENGTH_SHORT).show();
        }
    }
}
