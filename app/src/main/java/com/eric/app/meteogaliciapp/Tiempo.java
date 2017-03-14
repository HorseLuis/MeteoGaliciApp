package com.eric.app.meteogaliciapp;

/**
 * Created by Eric on 14/03/2017.
 */

public class Tiempo {

    private String data;
    private String tMax;
    private String tMin;
    private String ceoT;

    public Tiempo(String Data, String tMax, String tMin, String ceoT) {
        this.data = Data;
        this.tMax = tMax;
        this.tMin = tMin;
        this.ceoT = ceoT;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String gettMax() {
        return tMax;
    }

    public void settMax(String tMax) {
        this.tMax = tMax;
    }

    public String gettMin() {
        return tMin;
    }

    public void settMin(String tMin) {
        this.tMin = tMin;
    }

    public String getCeoT() {
        return ceoT;
    }

    public void setCeoT(String ceoT) {
        this.ceoT = ceoT;
    }
}
