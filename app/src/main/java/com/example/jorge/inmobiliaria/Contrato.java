package com.example.jorge.inmobiliaria;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Jorge on 27/01/2015.
 */
public class Contrato {

    private Contrato(){}

    public static abstract class TablaInmueble implements BaseColumns {
        public static final String TABLA="inmueble";
        public static final String DIRECCION="direccion";
        public static final String NUMERO="numero";
        public static final String PRECIO = "precio";
        public static final String TIPO = "tipo";
        public static final String DESCRIPCION="descripcion";
        public static final String IMAGEN="imagen";
        public static final String SUBIDO="subido";
        public static final String CONTENT_TYPE_INMUEBLE =
                "vnd.android.cursor.dir/vnd.inmueble";
        public static final String CONTENT_TYPE_INMUEBLE_ID =
                "vnd.android.cursor.item/vnd.inmueble";
        public static final Uri CONTENT_URI =
                Uri.parse("content://" + Proveedor.AUTORIDAD + "/" + TABLA);
    }
}
