package com.eric.app.meteogaliciapp;

/**
 * Created by Eric on 14/03/2017.
 */

public class Tiempo {

    private String data;
    private String tMax;
    private String tMin;
    private String ceoM;
    private String ceoT;
    private String ceoN;
    private String pChoivaM;
    private String pChoivaT;
    private String pChoivaN;

    public Tiempo(){

    }

    public Tiempo(String Data, String tMax, String tMin, String ceoM, String ceoT, String ceoN, String pChoivaM, String pChoivaT, String pChoivaN) {
        this.data = Data;
        this.tMax = tMax;
        this.tMin = tMin;
        this.ceoM = ceoM;
        this.ceoT = ceoT;
        this.ceoN = ceoN;
        this.pChoivaM = pChoivaM;
        this.pChoivaT = pChoivaT;
        this.pChoivaN = pChoivaN;
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

    public String getpChoivaN() {
        return pChoivaN;
    }

    public void setpChoivaN(String pChoivaN) {
        this.pChoivaN = pChoivaN;
    }

    public String getpChoivaT() {
        return pChoivaT;
    }

    public void setpChoivaT(String pChoivaT) {
        this.pChoivaT = pChoivaT;
    }

    public String getpChoivaM() {
        return pChoivaM;
    }

    public void setpChoivaM(String pChoivaM) {
        this.pChoivaM = pChoivaM;
    }

    public String getCeoN() {
        return ceoN;
    }

    public void setCeoN(String ceoN) {
        this.ceoN = ceoN;
    }

    public String getCeoM() {
        return ceoM;
    }

    public void setCeoM(String ceoM) {
        this.ceoM = ceoM;
    }
}
