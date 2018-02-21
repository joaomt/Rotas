package com.pharmamobi.joao.rotas.model;

/**
 * Created by joao on 11/11/2017.
 */

public class AvaliacaoEntrega {
    private int ID;
    private String COD_ENTREGA;
    private float NOTA;
    private String OBS_1,OBS_2,OBS_3, SUGESTAO;

    public AvaliacaoEntrega(String COD_ENTREGA, float NOTA, String OBS_1, String OBS_2, String OBS_3, String SUGESTAO) {
        this.COD_ENTREGA = COD_ENTREGA;
        this.NOTA = NOTA;
        this.OBS_1 = OBS_1;
        this.OBS_2 = OBS_2;
        this.OBS_3 = OBS_3;
        this.SUGESTAO = SUGESTAO;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCOD_ENTREGA() {
        return COD_ENTREGA;
    }

    public void setCOD_ENTREGA(String COD_ENTREGA) {
        this.COD_ENTREGA = COD_ENTREGA;
    }

    public float getNOTA() {
        return NOTA;
    }

    public void setNOTA(float NOTA) {
        this.NOTA = NOTA;
    }

    public String getOBS_1() {
        return OBS_1;
    }

    public void setOBS_1(String OBS_1) {
        this.OBS_1 = OBS_1;
    }

    public String getOBS_2() {
        return OBS_2;
    }

    public void setOBS_2(String OBS_2) {
        this.OBS_2 = OBS_2;
    }

    public String getOBS_3() {
        return OBS_3;
    }

    public void setOBS_3(String OBS_3) {
        this.OBS_3 = OBS_3;
    }

    public String getSUGESTAO() {
        return SUGESTAO;
    }

    public void setSUGESTAO(String SUGESTAO) {
        this.SUGESTAO = SUGESTAO;
    }
}
