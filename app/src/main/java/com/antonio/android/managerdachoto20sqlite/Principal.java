package com.antonio.android.managerdachoto20sqlite;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


public class Principal extends Activity {
    private GestorJugadores gj;
    private GestorPartidos gp;
    private Cursor c;
    private ListView lv;
    private AdaptadorCursor ac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        gj = new GestorJugadores(this);
        gp=new GestorPartidos(this);
        lv = (ListView) findViewById(R.id.lvLista);
        registerForContextMenu(lv);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String[] opc = new String[]{"Borrar", "Modificar","Añadir partido"};
                final int posicion = position;
                AlertDialog opciones = new AlertDialog.Builder(
                        Principal.this)
                        .setTitle("Opciones")
                        .setItems(opc,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int selected) {
                                        if (selected == 0) {
                                            borrar(posicion);
                                        } else if (selected == 1) {
                                            modificar(posicion);
                                        }else if(selected==2){
                                            List<Jugador>jugadores=gj.select();
                                            final Jugador j=jugadores.get(posicion);

                                            Intent i=new Intent(Principal.this,Secundaria.class);
                                            i.putExtra("id",j.getId());
                                            startActivity(i);
                                        }
                                    }
                                }).create();
                opciones.show();
                return true;
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        gj.open();
        gp.open();
        c = gj.getCursor(null, null, null);
        ac = new AdaptadorCursor(this, c);
        lv.setAdapter(ac);
        //datosPrueba();
    }


    @Override
    protected void onPause() {
        super.onPause();
        gj.close();
        gp.close();
    }

    public void datosPrueba() {
        String[] nombres = {"Bili", "Bob", "Zorton"};
        String[] tlfs = {"666555555", "666444444", "664444444"};
        String[] fnac = {"20-09-1988", "13-08-2000", "21-05-2001"};
        for (int i = 0; i < nombres.length; i++) {
            Jugador jugador = new Jugador(nombres[i], tlfs[i], fnac[i]);
            Long id = gj.insert(jugador);
        }
        c = gj.getCursor(null, null, null);
        ac.changeCursor(c);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.agregar) {
            agregar();

            return true;
        }else if(id == R.id.partidos) {
            Intent i=new Intent(Principal.this,Tercera.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean agregar() {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogo, null);
        final EditText etFD, etFM,etFA, etN, etT;
        etFD = (EditText) vista.findViewById(R.id.et1DA);
        etFM = (EditText) vista.findViewById(R.id.et1MA);
        etFA = (EditText) vista.findViewById(R.id.et1AA);
        etN = (EditText) vista.findViewById(R.id.et2A);
        etT = (EditText) vista.findViewById(R.id.et3A);
        etFD.setHint("Dia");
        etFM.setHint("Mes");
        etFA.setHint("Año");
        etN.setHint("Introduzca nombre");
        etT.setHint("Introduzca teléfono");
        final AlertDialog d = new AlertDialog.Builder(this)
                .setView(vista)
                .setTitle("Añadir jugador")
                .setPositiveButton(android.R.string.ok, null) //Set to null. We override the onclick
                .setNegativeButton(android.R.string.cancel, null)
                .create();
        d.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button b = d.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        //filtros
                        if (esTelefono(etT.getText().toString()) && isNumeric(etT.getText().toString()) &&
                                etN.getText().toString().length() > 0 && etFD.getText().length() > 0
                                && etFM.getText().length() > 0 && etFA.getText().length() > 0) {
                            String texto = etFD.getText().toString() + "-" + etFM.getText().toString() + "-" + etFA.getText().toString();
                            if(validarFecha(texto)) {
                                Jugador j = new Jugador();
                                j.setFnac(texto);
                                j.setNombre(etN.getText().toString());
                                j.setTelefono(etT.getText().toString());
//                      añadimos jugados y mostramos
                                gj.insert(j);
                                c = gj.getCursor(null, null, null);
                                ac.changeCursor(c);
                                tostada("El jugador " + j.getNombre() + " ha sido añadido");
                                d.dismiss();
                            }else{
                                tostada("fecha erronea");
                            }
                        }
                        // Filtramos que nos este vacios
                        if (etN.getText().toString().length() == 0) {
                            tostada("¡Introduzca nombre!");
                        }
                        if (etT.getText().toString().length() == 0) {
                            tostada("¡Introduzca teléfono!");
                        }
                        if (etFD.getText().toString().length() == 0) {
                            tostada("¡Introduzca dorsal!");
                        }

                        if (!esTelefono(etT.getText().toString())) {
                            tostada("¡Teléfono incorrecto!");
                        }
                    }
                });
            }
        });
        d.show();
        c = gj.getCursor(null, null, null);
        ac.changeCursor(c);
        return true;

    }
    private boolean modificar(final int index) {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogo, null);
        final EditText etFD, etFM,etFA, etN, etT;
        etFD = (EditText) vista.findViewById(R.id.et1DA);
        etFM = (EditText) vista.findViewById(R.id.et1MA);
        etFA = (EditText) vista.findViewById(R.id.et1AA);
        etN = (EditText) vista.findViewById(R.id.et2A);
        etT = (EditText) vista.findViewById(R.id.et3A);
        List<Jugador>jugadores=gj.select();
        final Jugador j=jugadores.get(index);
        etFD.setText(j.getFnac().split("-")[0]);
        etFM.setText(j.getFnac().split("-")[1]);
        etFA.setText(j.getFnac().split("-")[2]);
        etN.setText(j.getNombre());
        etT.setText(j.getTelefono());

        final AlertDialog d = new AlertDialog.Builder(this)
                .setView(vista)
                .setTitle("Modificar jugador")
                .setPositiveButton(android.R.string.ok, null) //Set to null. We override the onclick
                .setNegativeButton(android.R.string.cancel, null)
                .create();
        d.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button b = d.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        //filtro
                        if (esTelefono(etT.getText().toString()) && isNumeric(etT.getText().toString()) &&
                                etN.getText().toString().length() > 0 && etFD.getText().length() > 0
                                && etFM.getText().length() > 0 && etFA.getText().length() > 0) {
                            String texto = etFD.getText().toString() + "-" + etFM.getText().toString() + "-" + etFA.getText().toString();
                            if(validarFecha(texto)) {
                                j.setNombre(etN.getText().toString());
                                j.setTelefono(etT.getText().toString());
                                j.setFnac(texto);
//                      editamos jugadores y mostramos
                                gj.update(j);
                                c = gj.getCursor(null,null,null);
                                ac.changeCursor(c);
                                tostada("El jugador " + j.getNombre() + " ha sido modificado");
                                d.dismiss();
                            }else{
                                tostada("fecha erronea");
                            }

                        }
                        // Filtramos que nos este vacios
                        if (etN.getText().toString().length() == 0) {
                            tostada("¡Introduzca nombre!");
                        }
                        if (etT.getText().toString().length() == 0) {
                            tostada("¡Introduzca teléfono!");
                        }
                        if (etFD.getText().toString().length() == 0) {
                            tostada("¡Introduzca dia!");
                        }
                        if (etFM.getText().toString().length() == 0) {
                            tostada("¡Introduzca mes!");
                        }
                        if (etFA.getText().toString().length() == 0) {
                            tostada("¡Introduzca año!");
                        }
                        if (!esTelefono(etT.getText().toString())) {
                            tostada("¡Teléfono incorrecto!");
                        }
                    }
                });
            }
        });
        d.show();
        c = gj.getCursor(null, null, null);
        ac.changeCursor(c);
        return true;

    }
    public boolean borrar(final int pos){
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(Principal.this);
        dialogo1.setTitle("Importante");
        dialogo1.setMessage("¿ Desea borrar el inmueble seleccionado ?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

                List<Jugador> jugadores=gj.select();
                Jugador j=jugadores.get(pos);
                System.out.println("jugador "+j.toString());
                gj.delete(j);
                c = gj.getCursor(null, null, null);
                ac.changeCursor(c);
                dialogo1.dismiss();
                tostada("Inmueble borrado");
            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                dialogo1.cancel();
            }
        });
        dialogo1.show();
        return true;
    }
    public Toast tostada(String t) {
        Toast toast =
                Toast.makeText(getApplicationContext(),
                        t + "", Toast.LENGTH_SHORT);
        toast.show();
        return toast;
    }
    private static boolean isNumeric(String cadena){
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }

    private static boolean esTelefono(String cadena){
        try {
            int num=Integer.parseInt(cadena);
            if(num<600000000||(num>799999999&&num<900000000)||num>999999999)
                return false;

            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }
    public boolean validarFecha(String fecha) {

        if (fecha == null)
            return false;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); //año-mes-dia

        if (fecha.trim().length() != dateFormat.toPattern().length())
            return false;

        dateFormat.setLenient(false);

        try {
            dateFormat.parse(fecha.trim());
        }
        catch (ParseException pe) {
            return false;
        }
        return true;
    }
}