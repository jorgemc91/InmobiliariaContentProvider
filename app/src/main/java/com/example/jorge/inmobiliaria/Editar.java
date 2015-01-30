package com.example.jorge.inmobiliaria;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class Editar extends Activity {
    private Cursor cursor;
    private EditText etDirec, etNum, etDescri, etPrecio;
    private TextView tvNomfoto;
    private Spinner spTipo;
    private int index;
    private String nomFoto="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogo_anadir);

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
        index = getIntent().getExtras().getInt("id");
        cursor.moveToPosition(index);

        etDirec = (EditText) findViewById(R.id.etDireccion);
        etNum = (EditText) findViewById(R.id.etNumero);
        etDescri = (EditText) findViewById(R.id.etDesc);
        etPrecio = (EditText) findViewById(R.id.etPrecio);
        tvNomfoto = (TextView) findViewById(R.id.tvNomfoto);
        spTipo = (Spinner) findViewById(R.id.sTipo);
        Spinner spinner = (Spinner) this.findViewById(R.id.sTipo);
        ArrayAdapter<CharSequence> adTipos = ArrayAdapter.createFromResource(this, R.array.tipos,
                android.R.layout.simple_spinner_item);
        adTipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adTipos);
        etDirec.setText(cursor.getString(1));
        etNum.setText(cursor.getString(2));
        etPrecio.setText(cursor.getString(3));
        etDescri.setText(cursor.getString(5));
        tvNomfoto.setText(cursor.getString(6));
        if (cursor.getString(4).equals("Piso")) {
            spTipo.setSelection(0);
        } else if (cursor.getString(4).equals("Casa")) {
            spTipo.setSelection(1);
        } else if (cursor.getString(4).equals("Chalet")) {
            spTipo.setSelection(2);
        } else if (cursor.getString(4).equals("Cortijo")) {
            spTipo.setSelection(3);
        } else if (cursor.getString(4).equals("Ático")) {
            spTipo.setSelection(4);
        }
    }

    public void anadir(View v) {
        int id = cursor.getInt(0);
        Uri uri = Contrato.TablaInmueble.CONTENT_URI;
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaInmueble.DIRECCION, etDirec.getText().toString());
        valores.put(Contrato.TablaInmueble.NUMERO, etNum.getText().toString());
        valores.put(Contrato.TablaInmueble.PRECIO, etPrecio.getText().toString());
        valores.put(Contrato.TablaInmueble.TIPO, String.valueOf(spTipo.getSelectedItem()));
        valores.put(Contrato.TablaInmueble.DESCRIPCION, etDescri.getText().toString());
        if (nomFoto != ""){
            valores.put(Contrato.TablaInmueble.IMAGEN, nomFoto);
        }else{
            valores.put(Contrato.TablaInmueble.IMAGEN, tvNomfoto.getText().toString());
        }
        getContentResolver().update(uri, valores, "_id = ?",new String[]{id + ""});
        tostada("Inmueble modificado");
        Intent i = new Intent(this, Principal.class);
        startActivity(i);
    }

    private static final int IDACTIVIDADFOTO=1;
    public void foto(View v){
        Intent i = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
        String nombreImg = "inmueble_"+getDatePhone()+".jpg";
        File f = new File(getExternalFilesDir(null), nombreImg);
        i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        startActivityForResult(i, IDACTIVIDADFOTO);
        nomFoto = nombreImg;
    }

    private String getDatePhone(){
        Calendar cal = new GregorianCalendar();
        Date date = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
        String formatteDate = df.format(date);
        return formatteDate;
    }

    private void tostada(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
