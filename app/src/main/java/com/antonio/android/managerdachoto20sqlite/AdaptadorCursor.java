package com.antonio.android.managerdachoto20sqlite;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Antonio on 01/12/2014.
 */
public class AdaptadorCursor extends CursorAdapter {
    public AdaptadorCursor(Context context, Cursor c) {
        super(context, c, true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup vg) {
        LayoutInflater i = LayoutInflater.from(vg.getContext());
        View v = i.inflate(R.layout.detalle, vg, false);
        return v;
    }

    @Override
    public void bindView(View v, Context co, Cursor c) {
        Jugador ju=new Jugador();
        ju= GestorJugadores.getRow(c);
        TextView tvN = (TextView) v.findViewById(R.id.tvTexto2);
        TextView tvT = (TextView) v.findViewById(R.id.tvTexto3);
        TextView tvF = (TextView) v.findViewById(R.id.tvTexto1);
        tvT.setText(ju.getTelefono());
        tvF.setText(ju.getFnac());
        tvN.setText(ju.getNombre()+"");

    }
}