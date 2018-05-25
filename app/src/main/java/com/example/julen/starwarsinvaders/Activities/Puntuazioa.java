package com.example.julen.starwarsinvaders.Activities;

/**
 * Created by jonur on 2018/5/9.
 */

public class Puntuazioa {
    private String zenbakia;
    private String izena;
    private int puntuak;

    //konstruktora

    public Puntuazioa(String zenbakia, String izena, int puntuak) {
        this.zenbakia = zenbakia;
        this.izena = izena;
        this.puntuak = puntuak;
    }

    public Puntuazioa() {

    }

    //get eta set


    public String getZenbakia() {
        return zenbakia;
    }

    public void setZenbakia(String zenbakia) {
        this.zenbakia = zenbakia;
    }

    public String getIzena() {
        return izena;
    }

    public void setIzena(String izena) {
        this.izena = izena;
    }

    public int getPuntuak() {
        return puntuak;
    }

    public void setPuntuak(int puntuak) {
        this.puntuak = puntuak;
    }
}
