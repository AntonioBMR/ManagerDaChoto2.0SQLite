package com.antonio.android.managerdachoto20sqlite;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Antonio on 09/12/2014.
 */
public class AdapterCursorPartido extends CursorAdapter {
    public AdapterCursorPartido(Context context, Cursor c) {
        super(context, c, true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup vg) {
        LayoutInflater i = LayoutInflater.from(vg.getContext());
        View v = i.inflate(R.layout.detalle_partido, vg, false);
        return v;
    }

    @Override
    public void bindView(View v, Context co, Cursor c) {
        Partido p=new Partido();
        p= GestorPartidos.getRow(c);
        Jugador j=new Jugador();
        TextView tvC = (TextView) v.findViewById(R.id.tvCont);
        TextView tvV = (TextView) v.findViewById(R.id.tvVal);
        TextView tvN = (TextView) v.findViewById(R.id.tvNombreJP);
        tvC.setText(p.getContrincante().toString()+"");
        tvV.setText(p.getValoracionP()+"");
        tvN.setText(p.getIdJ()+"");
    }
}