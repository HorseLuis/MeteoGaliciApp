package com.eric.app.meteogaliciapp;

/**
 * Created by DAM229 on 23/02/2017.
 */

public class Estado {


    private String concello;
    private String estadoCielo;
    private double sensTermica;
    private double temperatura;

    public Estado(){

    }

    public Estado (String concello, String estadoCielo, double sensTermica, double temperatura){
        this.estadoCielo = estadoCielo;
        this.sensTermica = sensTermica;
        this.temperatura= temperatura;
    }

    public String getConcello() {
        return concello;
    }

    public String getEstadoCielo() {
        return estadoCielo;
    }

    public double getSensTermica() {
        return sensTermica;
    }

    public double getTemperatura() {
        return temperatura;
    }





}

