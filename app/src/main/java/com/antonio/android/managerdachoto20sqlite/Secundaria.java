package com.antonio.android.managerdachoto20sqlite;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Secundaria extends Activity {
    private GestorPartidos gp;
    private GestorJugadores gj;
    private Button agregar;
    private EditText val;
    private long idJ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secundaria);
        Bundle b = getIntent().getExtras();
        long id=0;
        if (b != null) {
             id = b.getLong("id");
        }
        idJ=id;
        final EditText etCont=(EditText)findViewById(R.id.etContAP);
        agregar=(Button)findViewById(R.id.buttonAP);
        val=(EditText)findViewById(R.id.etValAP);
        val.setFilters(new InputFilter[]{new InputFilterMinMax("1", "10")});
        gp = new GestorPartidos(this);
        gj=new GestorJugadores(this);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gp.existeCont(etCont.getText().toString())){
                    if(gp.jugoPartido(etCont.getText().toString(),idJ)){
                        tostada("ya ha jugado ese partido");
                    }else{
                        int valoracion=0;
                        try{
                            valoracion=Integer.parseInt(val.getText().toString());
                        }catch(Exception e){
                        }
                        Partido partido=gp.partidoXNombre(etCont.getText().toString());
                        long idContrincante=partido.getIdC();
                        Partido p=new Partido();
                        p.setContrincante(etCont.getText().toString());
                        p.setIdC(idContrincante);
                        p.setIdJ(idJ);
                        p.setValoracionP(valoracion);
                        gp.insert(p);
                        tostada("la VALORACIÓN media es: "+gp.mediaJugador(idJ)+"");
                        finish();
                    }
                }else{
                    int valoracion=0;
                    try{
                        valoracion=Integer.parseInt(val.getText().toString());
                    }catch(Exception e){
                    }
                    long idcontador=0;
                    long idcontadorfinl=0;
                    for(long i=0;i<gp.count();i++){
                        if(gp.existeIdC(i)){
                            idcontador++;
                        }else{
                            idcontadorfinl=idcontador;
                        }
                    }
                    Partido p=new Partido();
                    p.setContrincante(etCont.getText().toString());
                    p.setIdC(idcontadorfinl);
                    p.setIdJ(idJ);
                    p.setValoracionP(valoracion);
                    gp.insert(p);
                    tostada("la VALORACIÓN media es: "+gp.mediaJugador(idJ)+"");
                    finish();
                }

            }
        });

    }

    public Toast tostada(String t) {
        Toast toast =
                Toast.makeText(getApplicationContext(),
                        t + "", Toast.LENGTH_SHORT);
        toast.show();
        return toast;
    }

    @Override
    protected void onResume() {
        super.onResume();
        gp.open();
        gj.open();
    }


    @Override
    protected void onPause() {
        super.onPause();
        gp.close();
        gj.close();
    }




}
