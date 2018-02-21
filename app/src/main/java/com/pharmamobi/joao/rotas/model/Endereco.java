package com.pharmamobi.joao.rotas.model;

/**
 * Created by joao on 31/08/2017.
 */

public class Endereco {
    private int ID;
    private int COD_CLIENTE;
    private String CEP;
    private String RUA;
    private String NUMERO;
    private String COMPLEMENTO;
    private String BAIRRO;
    private String CIDADE;
    private String ESTADO;

    private int drawable;

    public Endereco(int COD_CLIENTE, String CEP, String RUA, String NUMERO, String COMPLEMENTO, String BAIRRO, String CIDADE, String ESTADO) {
        this.COD_CLIENTE = COD_CLIENTE;
        this.CEP = CEP;
        this.RUA = RUA;
        this.NUMERO = NUMERO;
        this.COMPLEMENTO = COMPLEMENTO;
        this.BAIRRO = BAIRRO;
        this.CIDADE = CIDADE;
        this.ESTADO = ESTADO;
    }

    public Endereco(int ID, int COD_CLIENTE, String CEP, String RUA, String NUMERO, String COMPLEMENTO, String BAIRRO, String CIDADE, String ESTADO, int drawable) {
        this.ID = ID;
        this.COD_CLIENTE = COD_CLIENTE;
        this.CEP = CEP;
        this.RUA = RUA;
        this.NUMERO = NUMERO;
        this.COMPLEMENTO = COMPLEMENTO;
        this.BAIRRO = BAIRRO;
        this.CIDADE = CIDADE;
        this.ESTADO = ESTADO;
        this.drawable = drawable;
    }

    public static String Rua_Def="";
    public static String Bairro_Def="";

    public static int casa_loja=3;

    public Endereco(){}

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public String getRUA() {
        return RUA;
    }

    public void setRUA(String RUA) {
        this.RUA = RUA;
    }

    public int getCOD_CLIENTE() {
        return COD_CLIENTE;
    }

    public void setCOD_CLIENTE(int COD_CLIENTE) {
        this.COD_CLIENTE = COD_CLIENTE;
    }

    public String getBAIRRO() {
        return BAIRRO;
    }

    public void setBAIRRO(String BAIRRO) {
        this.BAIRRO = BAIRRO;
    }

    public String getCIDADE() {
        return CIDADE;
    }

    public void setCIDADE(String CIDADE) {
        this.CIDADE = CIDADE;
    }

    public String getESTADO() {
        return ESTADO;
    }

    public void setESTADO(String ESTADO) {
        this.ESTADO = ESTADO;
    }

    public String getCOMPLEMENTO() {
        return COMPLEMENTO;
    }

    public void setCOMPLEMENTO(String COMPLEMENTO) {
        this.COMPLEMENTO = COMPLEMENTO;
    }

    public String getNUMERO() {
        return NUMERO;
    }

    public void setNUMERO(String NUMERO) {
        this.NUMERO = NUMERO;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }


}
