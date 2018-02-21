package com.pharmamobi.joao.rotas.model;

import android.icu.util.Calendar;

/**
 * Created by joao on 21/11/2017.
 */

public class LocationLatLng {
    private int ID,COD_MOTOBOY;
    private double LATI,LONG;
    private String DATE_CLOCK;

    public LocationLatLng(int COD_MOTOBOY, double LATI, double LONG, String DATE_CLOCK) {
        this.COD_MOTOBOY = COD_MOTOBOY;
        this.LATI = LATI;
        this.LONG = LONG;
        this.DATE_CLOCK = DATE_CLOCK;
    }

    public String getDATE_CLOCK() {
        return DATE_CLOCK;
    }

    public void setDATE_CLOCK(String DATE_CLOCK) {
        this.DATE_CLOCK = DATE_CLOCK;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCOD_MOTOBOY() {
        return COD_MOTOBOY;
    }

    public void setCOD_MOTOBOY(int COD_MOTOBOY) {
        this.COD_MOTOBOY = COD_MOTOBOY;
    }

    public double getLATI() {
        return LATI;
    }

    public void setLATI(double LATI) {
        this.LATI = LATI;
    }

    public double getLONG() {
        return LONG;
    }

    public void setLONG(double LONG) {
        this.LONG = LONG;
    }
}
