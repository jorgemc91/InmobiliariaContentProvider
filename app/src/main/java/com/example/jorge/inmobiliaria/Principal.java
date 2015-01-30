package com.example.jorge.inmobiliaria;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


public class Principal extends Activity {
    private Adaptador ad;
    private Cursor cursor;

    private final int ACTIVIDADDOS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);
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
        final ListView lv = (ListView) findViewById(R.id.listView);
        ad = new Adaptador(this, cursor);
        lv.setAdapter(ad);
        registerForContextMenu(lv);
        ad.notifyDataSetChanged();

        //Para saber en que orientación estamos
        final FragmentoDetalle fdetalle = (FragmentoDetalle)getFragmentManager().findFragmentById(R.id.FDetalle);
        final boolean horizontal = fdetalle != null && fdetalle.isInLayout();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (horizontal){
                    fdetalle.setInmueble(getApplicationContext(),cursor,i);
                }else {
                    Intent intent = new Intent(Principal.this, Secundaria.class);
                    intent.putExtra("posicion",i);
                    startActivityForResult(intent, ACTIVIDADDOS);
                }
            }
        });
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }else if (id == R.id.action_anadir){
            Intent i = new Intent(this,Anadir.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int index = info.position;
        if (id == R.id.action_borrar) {
            AlertDialog.Builder dialogo = new AlertDialog.Builder(Principal.this);
            dialogo.setTitle("Borrar");
            dialogo.setMessage("¿Desea borrar el inmueble?");
            dialogo.setCancelable(false);
            dialogo.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo, int id) {
                    cursor.moveToPosition(index);
                    int idI = cursor.getInt(0);
                    getContentResolver().delete(Contrato.TablaInmueble.CONTENT_URI,
                            Contrato.TablaInmueble._ID + " = ?",
                            new String[]{String.valueOf(idI)});
                    tostada("Inmueble eliminado");
                }
            });
            dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo, int id) {
                    dialogo.cancel();
                }
            });
            dialogo.show();
        } else if (id == R.id.action_editar) {
            return editar(index);
        }
        return super.onContextItemSelected(item);
    }

    private boolean editar(final int index) {
        Intent i = new Intent(this,Editar.class);
        i.putExtra("id",index);
        startActivity(i);
        return true;
    }

    private void tostada(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
