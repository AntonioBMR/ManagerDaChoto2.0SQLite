package com.antonio.android.managerdachoto20sqlite;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;


public class Tercera extends Activity {
    private GestorPartidos gp;
    private AdapterCursorPartido ac;
    private Cursor c;
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tercera);
        gp = new GestorPartidos(this);
        TextView tvP=(TextView)findViewById(R.id.tvPartidos);
        lv = (ListView) findViewById(R.id.lvPartido);


    }

    @Override
    protected void onResume() {
        super.onResume();
        gp.open();
        c = gp.getCursor(null, null, null);
        ac = new AdapterCursorPartido(this, c);
        lv.setAdapter(ac);
    }
    @Override
    protected void onPause() {
        super.onPause();
        gp.close();
    }


}
