package com.antonio.android.managerdachoto20sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio on 26/11/2014.
 */
public class GestorJugadores {
    private Ayudante abd;
    private SQLiteDatabase bd;


    public GestorJugadores(Context c) {
        abd = new Ayudante(c);
    }

    public void open() {
        bd = abd.getWritableDatabase();
    }

    public void openRead() {
        bd = abd.getReadableDatabase();
    }

    public void close() {
        abd.close();
    }

    public long insert(Jugador j) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaJugadores.NOMBRE, j.getNombre());
        valores.put(Contrato.TablaJugadores.TELEFONO, j.getTelefono() );
        valores.put(Contrato.TablaJugadores.FNAC, j.getFnac());
        long id = bd.insert(Contrato.TablaJugadores.TABLA, null, valores);
        return id;
    }
    public int delete(Jugador j) {
        String condicion = Contrato.TablaJugadores._ID + " = ?";
        String[] argumentos = { j.getId() + "" };
        int cuenta = bd.delete(
                Contrato.TablaJugadores.TABLA, condicion, argumentos);
        return cuenta;
    }
    public int update(Jugador j) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaJugadores.NOMBRE, j.getNombre());
        valores.put(Contrato.TablaJugadores.TELEFONO, j.getTelefono());
        valores.put(Contrato.TablaJugadores.FNAC, j.getFnac());
        String condicion = Contrato.TablaJugadores._ID + " = ?";
        String[] argumentos = { j.getId() + "" };
        int cuenta = bd.update(Contrato.TablaJugadores.TABLA, valores,
                condicion, argumentos);
        return cuenta;
    }

    public List<Jugador> select() {
        List<Jugador> alj = new ArrayList<Jugador>();
        Cursor cursor = bd.query(Contrato.TablaJugadores.TABLA, null,
                null, null, null, null, null);
        cursor.moveToFirst();
        Jugador in;
        while (!cursor.isAfterLast()) {
            in = getRow(cursor);
            alj.add(in);
            cursor.moveToNext();
        }
        cursor.close();
        return alj;
    }

    public List<Jugador> select(String condicion, String[] parametros, String ord) {
        List<Jugador> alj = new ArrayList<Jugador>();
        Cursor cursor = bd.query(Contrato.TablaJugadores.TABLA, null,
                condicion, parametros, null, null, ord);
        cursor.moveToFirst();
        Jugador in;
        while (!cursor.isAfterLast()) {
            in = getRow(cursor);
            alj.add(in);
            cursor.moveToNext();
        }
        cursor.close();
        return alj;
    }

    public static Jugador getRow(Cursor c) {
        Jugador j = new Jugador();
        j.setId(c.getInt(0));
        j.setNombre(c.getString(1));
        j.setTelefono(c.getString(2));
        j.setFnac(c.getString(3));
        return j;
    }

    public  Jugador getRow(long id){
        List<Jugador> alj= select(Contrato.TablaJugadores._ID + " = ?",new String[]{ id+"" },null );
        if (alj.isEmpty()) {
            return alj.get(0);
        }
        return null;
    }

    public Cursor getCursor(String condicion, String[] parametros, String orderby) {
        Cursor cursor = bd.query(
                Contrato.TablaJugadores.TABLA, null, condicion, parametros, null, null,
                orderby);
        return cursor;
    }

    public Cursor getCursor() {
        Cursor cursor = bd.query(
                Contrato.TablaJugadores.TABLA, null, null, null, null, null,
                null);
        return cursor;
    }


}