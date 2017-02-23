package com.eric.app.meteogaliciapp;

/**
 * Created by DAM229 on 23/02/2017.
 */

public class Estado {


    private int estadoCielo;
    private int varTemperatura;
    private double temperatura;

    public Estado(){

    }

    public Estado (int estadoCielo, int varTemperatura, int temperatura){
        this.estadoCielo = estadoCielo;
        this.varTemperatura = varTemperatura;
        this.temperatura= temperatura;
    }

    public int getEstadoCielo(){
        return estadoCielo;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public int getVarTemperatura() {
        return varTemperatura;
    }

    public void setEstadoCielo(int estadoCielo) {
        this.estadoCielo = estadoCielo;
    }

    public void setVarTemperatura(int varTemperatura) {
        this.varTemperatura = varTemperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

}

