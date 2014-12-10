package com.antonio.android.managerdachoto20sqlite;

import android.provider.BaseColumns;

/**
 * Created by Antonio on 01/12/2014.
 */
public class Contrato {

    private Contrato(){

    }
    public static abstract class TablaJugadores implements BaseColumns {
        public static final String TABLA = "Jugadores";
        public static final String NOMBRE = "nombre";
        public static final String TELEFONO = "telefono";
        public static final String FNAC = "fechaN";
    }
    public static abstract class TablaPartido implements BaseColumns {
        public static final String TABLA = "Partido";
        public static final String IDCONT = "idCont";
        public static final String CONTRINCANTE = "contrincante";
        public static final String IDJUGADOR = "idJugador";
        public static final String VALORACION = "valoracion";

    }


}