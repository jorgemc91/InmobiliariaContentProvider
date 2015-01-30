package com.example.jorge.inmobiliaria;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Anadir extends Activity{
    private EditText etDirec, etNum, etPrecio, etDescri;
    private TextView tvNomFoto;
    private Spinner spTipo;
    private String nomFoto="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogo_anadir);

        etDirec = (EditText) findViewById(R.id.etDireccion);
        etNum = (EditText) findViewById(R.id.etNumero);
        etPrecio = (EditText) findViewById(R.id.etPrecio);
        etDescri = (EditText) findViewById(R.id.etDesc);
        tvNomFoto = (TextView) findViewById(R.id.tvNomfoto);
        spTipo = (Spinner) findViewById(R.id.sTipo);
        Spinner spinner = (Spinner) this.findViewById(R.id.sTipo);
        ArrayAdapter<CharSequence> adTipos = ArrayAdapter.createFromResource(this, R.array.tipos,
                android.R.layout.simple_spinner_item);
        adTipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adTipos);
    }

    public void anadir(View v) {
        Uri uri = Contrato.TablaInmueble.CONTENT_URI;
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaInmueble.DIRECCION, etDirec.getText().toString());
        valores.put(Contrato.TablaInmueble.NUMERO, etNum.getText().toString());
        valores.put(Contrato.TablaInmueble.PRECIO, etPrecio.getText().toString());
        valores.put(Contrato.TablaInmueble.TIPO, String.valueOf(spTipo.getSelectedItem()));
        valores.put(Contrato.TablaInmueble.DESCRIPCION, etDescri.getText().toString());
        valores.put(Contrato.TablaInmueble.IMAGEN, nomFoto);
        getContentResolver().insert(uri, valores);
        tostada("Inmueble añadido");
        Intent intent = new Intent(this,Principal.class);
        startActivity(intent);
    }

    private static final int IDACTIVIDADFOTO=1;
    public void foto(View v){
        Intent i = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
        String nombreImg = "inmueble_"+getDatePhone()+".jpg";
        File f = new File(getExternalFilesDir(null), nombreImg);
        i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        startActivityForResult(i, IDACTIVIDADFOTO);
        tvNomFoto.setText(nombreImg);
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
