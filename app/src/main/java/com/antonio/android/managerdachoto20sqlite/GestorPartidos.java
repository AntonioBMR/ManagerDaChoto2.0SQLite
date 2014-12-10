package com.antonio.android.managerdachoto20sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio on 07/12/2014.
 */
public class GestorPartidos{
    private Ayudante abd;
    private SQLiteDatabase bd;


    public GestorPartidos(Context c) {
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

    public boolean insert(Partido p) {
        ContentValues valores = new ContentValues();
            valores.put(Contrato.TablaPartido.IDCONT, p.getIdC());
            valores.put(Contrato.TablaPartido.CONTRINCANTE, p.getContrincante());
            valores.put(Contrato.TablaPartido.IDJUGADOR,p.getIdJ());
            valores.put(Contrato.TablaPartido.VALORACION,p.getValoracionP());
            bd.insert(Contrato.TablaPartido.TABLA, null, valores);

        return true;
    }
    public int delete(Partido p) {
        String condicion = Contrato.TablaPartido._ID + " = ?";
        String[] argumentos = { p.getIdP() + "" };
        int cuenta = bd.delete(
                Contrato.TablaPartido.TABLA, condicion, argumentos);
        return cuenta;
    }

    public List<Partido> select() {
        List<Partido> alp = new ArrayList<Partido>();
        Cursor cursor = bd.query(Contrato.TablaPartido.TABLA, null,
                null, null, null, null, null);
        cursor.moveToFirst();
        Partido p;
        while (!cursor.isAfterLast()) {
            p = getRow(cursor);
            alp.add(p);
            cursor.moveToNext();
        }
        cursor.close();
        return alp;
    }

    public List<Partido> select(String condicion, String[] parametros) {
        List<Partido> alj = new ArrayList<Partido>();
        Cursor cursor = bd.query(Contrato.TablaPartido.TABLA, null,
                condicion, parametros, null, null, null);
        cursor.moveToFirst();
        Partido p;
        while (!cursor.isAfterLast()) {
            p = getRow(cursor);
            alj.add(p);
            cursor.moveToNext();
        }
        cursor.close();
        return alj;
    }

    public static Partido getRow(Cursor c) {
        Partido j = new Partido();
        j.setIdP(c.getInt(0));
        j.setIdC(c.getLong(1));
        j.setContrincante(c.getString(2));
        j.setIdJ(c.getLong(3));
        j.setValoracionP(c.getInt(4));
        return j;
    }

    public Partido getRow(long id){
        List<Partido> alj= select(Contrato.TablaPartido._ID + " = ?",new String[]{ id+"" } );
        if (alj.isEmpty()) {
            return alj.get(0);
        }
        return null;
    }
    public List<Partido> getArrayL(){
        List<Partido> alp= select();
        if (alp.isEmpty()) {
            return null;
        }
        return alp;
    }
    public Partido getRow(String cont){
        List<Partido> alj= select(Contrato.TablaPartido.CONTRINCANTE + " = ?",new String[]{ cont+"" } );
        if (alj.isEmpty()) {
            return alj.get(0);
        }
        return null;
    }

    public boolean existeIdC(long id){
        List<Partido> alj= select(Contrato.TablaPartido.IDCONT + " = ?",new String[]{ id+"" } );
        if (alj.isEmpty()) {
            return false;
        }
        return true;
    }

    public int count(){
        int ct;
        ct=getCursor().getCount();
        return ct;
    }
    public boolean existeCont(String cont){
        List<Partido> alj= select(Contrato.TablaPartido.CONTRINCANTE + " = ?",new String[]{ cont+"" } );
        if (alj.isEmpty()) {
            return false;
        }
        return true;
    }
    public Partido partidoXNombre(String cont){
        List<Partido> alj= select(Contrato.TablaPartido.CONTRINCANTE + " = ?",new String[]{ cont+"" } );
        if (alj.isEmpty()) {
            return null;
        }
        return alj.get(0);
    }
    public boolean jugoPartido(String cont,long id){
        String[] parametros = {cont+"",id+""};
        Cursor c= getCursor(Contrato.TablaPartido.CONTRINCANTE + " = ? AND "+Contrato.TablaPartido.IDJUGADOR+" = ?",parametros,null );
        c.moveToFirst();
        Partido objeto;
        List<Partido> alp = new ArrayList<Partido>();
        while (!c.isAfterLast()) {
            objeto = getRow(c);
            alp.add(objeto);
            Log.v(" resultado",objeto.getContrincante()+" "+objeto.getIdJ());
            System.out.println(objeto.getContrincante()+" resultado "+objeto.getIdJ());
            c.moveToNext();
            return true;
        }
        c.close();
        return false;
    }
    public int  mediaJugador(long idJ){
        List<Partido> alj= select(Contrato.TablaPartido.IDJUGADOR + " = ?",new String[]{ idJ+"" } );
        if (!alj.isEmpty()) {
            int sumatoria=0;
            for(int i=0;i<alj.size();i++){
                sumatoria+=alj.get(i).getValoracionP();
            }
            int res=sumatoria/alj.size();
            return res;
        }
        return 0;
    }
    public Cursor getCursor(String condicion, String[] parametros, String orderby) {
        Cursor cursor = bd.query(
                Contrato.TablaPartido.TABLA, null, condicion, parametros, null, null,
                orderby);
        return cursor;
    }

    public Cursor getCursor() {
        Cursor cursor = bd.query(
                Contrato.TablaPartido.TABLA, null, null, null, null, null,
                null);
        return cursor;
    }


}