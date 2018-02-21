package com.pharmamobi.joao.rotas.model;

/**
 * Created by joao on 12/11/2017.
 */

public class RelacaoEntregas {
    private int ID,QTD_ENTREGAS,QTD_ENTREGAS_REALIZADAS,QTD_ENTREGAS_RETORNADAS;
    private float VALOR_TOTAL,VALOR_TOTAL_RECEBIDO;
    private String DATA_RELACAO;

    public RelacaoEntregas(int ID,int QTD_ENTREGAS, int QTD_ENTREGAS_REALIZADAS, int QTD_ENTREGAS_RETORNADAS, float VALOR_TOTAL, float VALOR_TOTAL_RECEBIDO, String DATA_RELACAO) {
        this.ID = ID;
        this.QTD_ENTREGAS = QTD_ENTREGAS;
        this.QTD_ENTREGAS_REALIZADAS = QTD_ENTREGAS_REALIZADAS;
        this.QTD_ENTREGAS_RETORNADAS = QTD_ENTREGAS_RETORNADAS;
        this.VALOR_TOTAL = VALOR_TOTAL;
        this.VALOR_TOTAL_RECEBIDO = VALOR_TOTAL_RECEBIDO;
        this.DATA_RELACAO = DATA_RELACAO;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getQTD_ENTREGAS() {
        return QTD_ENTREGAS;
    }

    public void setQTD_ENTREGAS(int QTD_ENTREGAS) {
        this.QTD_ENTREGAS = QTD_ENTREGAS;
    }

    public int getQTD_ENTREGAS_REALIZADAS() {
        return QTD_ENTREGAS_REALIZADAS;
    }

    public void setQTD_ENTREGAS_REALIZADAS(int QTD_ENTREGAS_REALIZADAS) {
        this.QTD_ENTREGAS_REALIZADAS = QTD_ENTREGAS_REALIZADAS;
    }

    public int getQTD_ENTREGAS_RETORNADAS() {
        return QTD_ENTREGAS_RETORNADAS;
    }

    public void setQTD_ENTREGAS_RETORNADAS(int QTD_ENTREGAS_RETORNADAS) {
        this.QTD_ENTREGAS_RETORNADAS = QTD_ENTREGAS_RETORNADAS;
    }

    public float getVALOR_TOTAL() {
        return VALOR_TOTAL;
    }

    public void setVALOR_TOTAL(float VALOR_TOTAL) {
        this.VALOR_TOTAL = VALOR_TOTAL;
    }

    public float getVALOR_TOTAL_RECEBIDO() {
        return VALOR_TOTAL_RECEBIDO;
    }

    public void setVALOR_TOTAL_RECEBIDO(float VALOR_TOTAL_RECEBIDO) {
        this.VALOR_TOTAL_RECEBIDO = VALOR_TOTAL_RECEBIDO;
    }

    public String getDATA_RELACAO() {
        String data =  DATA_RELACAO.replaceAll("-","/");
        String[]s = data.split("/");
        String novaData = s[2]+"/"+s[1]+"/"+s[0];
        return novaData;
    }

    public void setDATA_RELACAO(String DATA_RELACAO) {
        this.DATA_RELACAO = DATA_RELACAO;
    }
}
