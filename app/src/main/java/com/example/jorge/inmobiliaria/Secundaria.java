package com.example.jorge.inmobiliaria;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;


public class Secundaria extends Activity {
    private Cursor cursor;
    private int posicion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_secundaria);
        Uri uri = Contrato.TablaInmueble.CONTENT_URI;
        String [] proyeccion = null;
        String condicion = null;
        String [] parametros = null;
        String orden = null;
        cursor =  getContentResolver().query (
                uri,
                proyeccion,
                condicion,
                parametros,
                orden);
        posicion = getIntent().getExtras().getInt("posicion");
        final FragmentoDetalle fdetalle = (FragmentoDetalle)getFragmentManager().findFragmentById(R.id.fragmentoSecundaria);
        fdetalle.setInmueble(getApplicationContext(),cursor,posicion);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        finish();
    }
}
