package com.antonio.android.managerdachoto20sqlite;

import java.io.Serializable;

/**
 * Created by Antonio on 07/12/2014.
 */
public class Partido  implements Serializable,Comparable<Jugador> {
    private long idP;
    private long idC;
    private String contrincante;
    private long idJ;
    private int valoracionP;

    public Partido(){
        this(0,0,"",0,0);

    }
    public Partido(long idP,long idC,String cont,long idj,int val){
        this.idP=idP;
        this.idC=idC;
        this.contrincante=cont;
        this.idJ=idj;
        this.valoracionP=val;
        }

    public String getContrincante() {
        return contrincante;
    }

    public void setContrincante(String contrincante) {
        this.contrincante = contrincante;
    }

    public long getIdC() {
        return idC;
    }


    public Long getIdJ() {
        return idJ;
    }

    public void setIdJ(Long idJ) {
        this.idJ = idJ;
    }

    public int getValoracionP() {
        return valoracionP;
    }

    public void setValoracionP(int valoracionP) {
        this.valoracionP = valoracionP;
    }

    public void setIdC(long idC) {
        this.idC = idC;
    }

    public long getIdP() {
        return idP;
    }

    public void setIdP(long idP) {
        this.idP = idP;
    }

    @Override
    public int compareTo(Jugador another) {
        return 0;
    }
}
