package com.antonio.android.managerdachoto20sqlite;

import java.io.Serializable;

/**
 * Created by Antonio on 17/11/2014.
 */
public class Jugador implements Serializable,Comparable<Jugador> {
    private long id;
    private String nombre,telefono ;
    private String fnac;
    public Jugador(){
        this(0,"","","");
    }

    public Jugador(long id, String nombre, String telefono, String fnac) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.fnac = fnac;

    }
    public Jugador(String nombre, String telefono, String fnac){
        this.nombre = nombre;
        this.telefono = telefono;
        this.fnac = fnac;

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFnac() {
        return fnac;
    }

    public void setFnac(String fnac) {
        this.fnac = fnac;
    }



    @Override
    public String toString() {
        return "Jugador{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fnac='" + fnac + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Jugador.class != o.getClass()) return false;

        Jugador jugador = (Jugador) o;

        if (id != jugador.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public int compareTo(Jugador another) {

        if(this.nombre.compareTo(another.nombre)!=0){
            this.nombre.compareTo(another.nombre);
        }else{
            return this.fnac.compareTo(another.fnac);
        }
        return 0;
    }
}
