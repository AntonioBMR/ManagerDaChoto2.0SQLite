package com.antonio.android.managerdachoto20sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Antonio on 01/12/2014.
 */
public class Ayudante  extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ManagerDaChoto.db";
    public static final int DATABASE_VERSION = 2;

    public Ayudante(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;
        sql="create table "+Contrato.TablaJugadores.TABLA+
                " ("+ Contrato.TablaJugadores._ID+
                " integer primary key autoincrement, "+
                Contrato.TablaJugadores.NOMBRE+" text, "+
                Contrato.TablaJugadores.TELEFONO+" text, "+
                Contrato.TablaJugadores.FNAC+" text)";
        db.execSQL(sql);
        String sql1;
        sql1="create table "+ Contrato.TablaPartido.TABLA+
                " ("+ Contrato.TablaPartido._ID+
                " integer primary key autoincrement, "+
                Contrato.TablaPartido.IDCONT+" integer, "+
                Contrato.TablaPartido.CONTRINCANTE+" text, "+
                Contrato.TablaPartido.IDJUGADOR+" integer,"+
                Contrato.TablaPartido.VALORACION+" integer);";
        Log.v("sql1", sql1);
        db.execSQL(sql1);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {

            //creo tablas respaldo
            String sql = "CREATE TABLE RespaldoJ (id INTEGER, nombre TEXT, telefono TEXT, valoracion INTEGER, fnac TEXT)";
            db.execSQL(sql);
            //copio los datos
            sql = "INSERT INTO RespaldoJ SELECT * FROM jugador";
            db.execSQL(sql);
            //borro tablas originales
            sql = "drop table if exists " + Contrato.TablaJugadores.TABLA;
            db.execSQL(sql);
            //creo tablas nuevas
            onCreate(db);
            //meto datos en tablas nuevas
            sql = "INSERT INTO " + Contrato.TablaJugadores.TABLA + " (" + Contrato.TablaJugadores.NOMBRE + " , " +
                    Contrato.TablaJugadores.TELEFONO + " , " + Contrato.TablaJugadores.FNAC + ") SELECT nombre, telefono, fnac FROM Juga";
            db.execSQL(sql);
            sql = "INSERT INTO " + Contrato.TablaPartido.TABLA + " (" + Contrato.TablaPartido.VALORACION + " , "
                    + Contrato.TablaPartido.IDJUGADOR +
                    " ) SELECT valoracion, " + Contrato.TablaJugadores._ID + " FROM RespaldoJ j INNER JOIN " +
                    Contrato.TablaJugadores.TABLA + " ju WHERE j.nombre=ju." + Contrato.TablaJugadores.NOMBRE +
                    " AND j.telefono=ju." + Contrato.TablaJugadores.TELEFONO +
                    " AND j.fnac=ju." + Contrato.TablaJugadores.FNAC;
            db.execSQL(sql);
            sql = "UPDATE " + Contrato.TablaPartido.TABLA + " SET " + Contrato.TablaPartido.CONTRINCANTE + "='Contrincante'";
            db.execSQL(sql);
            //borro tablas respaldo
            sql = "drop table RespaldoJ";
            db.execSQL(sql);
        }

    }
}