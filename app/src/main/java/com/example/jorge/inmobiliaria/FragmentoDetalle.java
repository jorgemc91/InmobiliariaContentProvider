package com.example.jorge.inmobiliaria;


import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class FragmentoDetalle extends Fragment {
    private View v;
    private TextView tvDirec, tvNum, tvPrecio, tvTipo, tvDesc;
    private ImageView ivFoto;

    public FragmentoDetalle() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_fragmento_detalle, container, false);
        return v;
    }

    public void setInmueble(Context context,Cursor c, int position){
        c.moveToPosition(position);
        tvDirec = (TextView) v.findViewById(R.id.tvDireccion);
        tvNum = (TextView) v.findViewById(R.id.tvNumero);
        tvPrecio = (TextView) v.findViewById(R.id.tvPrecio);
        tvTipo = (TextView) v.findViewById(R.id.tvTipo);
        tvDesc = (TextView) v.findViewById(R.id.tvDescripcion);
        ivFoto = (ImageView) v.findViewById(R.id.ivFoto);
        tvDirec.setText(c.getString(1));
        tvNum.setText(c.getString(2));
        tvPrecio.setText(c.getString(3));
        tvTipo.setText(c.getString(4));
        tvDesc.setText(c.getString(5));
        File f = new File(context.getExternalFilesDir(null),c.getString(6));
        Bitmap foto = BitmapFactory.decodeFile(f.getAbsolutePath());
        ivFoto.setImageBitmap(foto);
    }
}
