package com.example.jorge.inmobiliaria;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jorge on 27/01/2015.
 */
public class Ayudante extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "inmobiliaria.sqlite";
    public static final int DATABASE_VERSION = 1;

    public Ayudante(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;
        sql="create table "+Contrato.TablaInmueble.TABLA+
                " ("+ Contrato.TablaInmueble._ID+
                " integer primary key autoincrement, "+
                Contrato.TablaInmueble.DIRECCION+" text, "+
                Contrato.TablaInmueble.NUMERO+" text, "+
                Contrato.TablaInmueble.PRECIO+" text, "+
                Contrato.TablaInmueble.TIPO+" text, "+
                Contrato.TablaInmueble.DESCRIPCION+" text, "+
                Contrato.TablaInmueble.IMAGEN+" text, "+
                Contrato.TablaInmueble.SUBIDO+" integer default 0)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
