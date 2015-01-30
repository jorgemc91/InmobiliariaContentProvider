package com.example.jorge.inmobiliaria;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class Proveedor extends ContentProvider {

    private Ayudante abd;
    public static String AUTORIDAD = "com.example.jorge.inmobiliaria";
    private static final UriMatcher convierteUri2Int;
    private static final int INMUEBLES = 1;
    private static final int INMUEBLE_ID = 2;

    static {
        convierteUri2Int = new UriMatcher(UriMatcher.NO_MATCH);
        convierteUri2Int.addURI(AUTORIDAD, Contrato.TablaInmueble.TABLA, INMUEBLES);
        convierteUri2Int.addURI(AUTORIDAD, Contrato.TablaInmueble.TABLA + "/#", INMUEBLE_ID);
    }

    public Proveedor() {
    }

    @Override
    public boolean onCreate() {
        abd = new Ayudante(getContext());
        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = abd.getWritableDatabase();
        switch (convierteUri2Int.match(uri)) {
            case INMUEBLES: break;
            case INMUEBLE_ID:
                selection = Contrato.TablaInmueble._ID + " = ? ";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                break;
            default: throw new IllegalArgumentException("URI " + uri);
        }
        int cuenta = db.delete(Contrato.TablaInmueble.TABLA, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return cuenta;
    }

    @Override
    public String getType(Uri uri) {
        switch (convierteUri2Int.match(uri)) {
            case INMUEBLES:
                return Contrato.TablaInmueble.CONTENT_TYPE_INMUEBLE;
            case INMUEBLE_ID:
                return Contrato.TablaInmueble.CONTENT_TYPE_INMUEBLE_ID;
            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (convierteUri2Int.match(uri) != INMUEBLES) {
            throw new IllegalArgumentException("URI " + uri);
        }
        SQLiteDatabase db = abd.getWritableDatabase();
        long id = db.insert(Contrato.TablaInmueble.TABLA, null, values);
        if (id > 0) {
            Uri uriElemento = ContentUris.withAppendedId(Contrato.TablaInmueble.CONTENT_URI, id);
            getContext().getContentResolver().notifyChange(uriElemento,null);
            return uriElemento;
        }
        throw new SQLException("Insert" + uri);

    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(Contrato.TablaInmueble.TABLA);
        switch (convierteUri2Int.match(uri)) {
            case INMUEBLES: break;
            case INMUEBLE_ID:
                selection = Contrato.TablaInmueble._ID + " = ? ";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                break;
            default:
                throw new IllegalArgumentException("URI " + uri);
        }
        SQLiteDatabase db = abd.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
            String[] selectionArgs) {
        SQLiteDatabase db = abd.getWritableDatabase();
        int cuenta;
        switch (convierteUri2Int.match(uri)) {
            case INMUEBLES:
                break;
            case INMUEBLE_ID:
                selection = Contrato.TablaInmueble._ID + " = ? ";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                break;
            default: throw new IllegalArgumentException("URI " + uri);
        }
        cuenta = db.update(Contrato.TablaInmueble.TABLA, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return cuenta;
    }
}
