package com.example.jorge.inmobiliaria;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Jorge on 28/01/2015.
 */
public class Adaptador  extends CursorAdapter {
    private TextView tvDirec, tvNum, tvTipo;
    private ImageView ivImg;

    public Adaptador(Context context, Cursor c) {
        super(context, c,true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup vg) {
        LayoutInflater i = LayoutInflater.from(vg.getContext());
        View v = i.inflate(R.layout.detalle, vg, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        tvDirec = (TextView) view.findViewById(R.id.tvDireccion);
        tvNum = (TextView) view.findViewById(R.id.tvNumero);
        tvTipo = (TextView) view.findViewById(R.id.tvTipo);
        ivImg = (ImageView) view.findViewById(R.id.ivDetalle);

        tvDirec.setText(cursor.getString(1));
        tvNum.setText(cursor.getString(2));
        tvTipo.setText(cursor.getString(4));
        ivImg.setImageDrawable(context.getResources().getDrawable(R.drawable.casa));
    }
}

