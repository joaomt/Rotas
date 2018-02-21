package com.pharmamobi.joao.rotas.model;

/**
 * Created by joao on 17/10/2017.
 */
public class Orcamentos {
    private int ID;
    private int COD_CLIENTE;
    private int COD_EMPRESA;
    private String TITULO;
    private String ANEXO;
    private String OBSERVACAO;
    private int STATUS_PEDIDO;
    private String DATA_PEDIDO;
    private double VALOR_TOTAL;
    private int COD_ENDERECO;
    private String LOCAL_RETIRADA;
    public static int entrega=0;
    //private String ENDRECO_ENTREGA;



    //itens orçamento
    private String NOME;
    private double VALOR_ITEM;

    public Orcamentos(){}
    public Orcamentos(String NOME, double VALOR_ITEM) {
        this.VALOR_ITEM = VALOR_ITEM;
        this.NOME = NOME;
    }

    public Orcamentos(int ID,int COD_CLIENTE, int COD_EMPRESA, String TITULO, String ANEXO, String OBSERVACAO, int STATUS_PEDIDO, String DATA_PEDIDO, double VALOR_TOTAL, String LOCAL_RETIRADA) {
        this.COD_CLIENTE = COD_CLIENTE;
        this.COD_EMPRESA = COD_EMPRESA;
        this.TITULO = TITULO;
        this.ANEXO = ANEXO;
        this.OBSERVACAO = OBSERVACAO;
        this.STATUS_PEDIDO = STATUS_PEDIDO;
        this.DATA_PEDIDO = DATA_PEDIDO;
        this.VALOR_TOTAL = VALOR_TOTAL;
        this.LOCAL_RETIRADA = LOCAL_RETIRADA;
        this.ID = ID;
    }

    public Orcamentos(int COD_CLIENTE, int COD_EMPRESA, String TITULO, String ANEXO, String OBSERVACAO, int STATUS_PEDIDO, String DATA_PEDIDO, double VALOR_TOTAL, int COD_ENDERECO, String LOCAL_RETIRADA) {
        this.COD_CLIENTE = COD_CLIENTE;
        this.COD_EMPRESA = COD_EMPRESA;
        this.TITULO = TITULO;
        this.ANEXO = ANEXO;
        this.OBSERVACAO = OBSERVACAO;
        this.STATUS_PEDIDO = STATUS_PEDIDO;
        this.DATA_PEDIDO = DATA_PEDIDO;
        this.VALOR_TOTAL = VALOR_TOTAL;
        this.COD_ENDERECO = COD_ENDERECO;
        this.LOCAL_RETIRADA = LOCAL_RETIRADA;
    }

    public Orcamentos(int ID, int COD_CLIENTE, int COD_EMPRESA, String TITULO, String ANEXO, String OBSERVACAO, int STATUS_PEDIDO, String DATA_PEDIDO, double VALOR_TOTAL, String LOCAL_RETIRADA, String NOME, double VALOR_ITEM) {
        this.ID = ID;
        this.COD_CLIENTE = COD_CLIENTE;
        this.COD_EMPRESA = COD_EMPRESA;
        this.TITULO = TITULO;
        this.ANEXO = ANEXO;
        this.OBSERVACAO = OBSERVACAO;
        this.STATUS_PEDIDO = STATUS_PEDIDO;
        this.DATA_PEDIDO = DATA_PEDIDO;
        this.VALOR_TOTAL = VALOR_TOTAL;
        this.LOCAL_RETIRADA = LOCAL_RETIRADA;
        this.NOME = NOME;
        this.VALOR_ITEM = VALOR_ITEM;
    }
    /*
        getters and setters
    */

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCOD_CLIENTE() {
        return COD_CLIENTE;
    }

    public void setCOD_CLIENTE(int COD_CLIENTE) {
        this.COD_CLIENTE = COD_CLIENTE;
    }

    public int getCOD_EMPRESA() {
        return COD_EMPRESA;
    }

    public void setCOD_EMPRESA(int COD_EMPRESA) {
        this.COD_EMPRESA = COD_EMPRESA;
    }

    public String getTITULO() {
        return TITULO;
    }

    public void setTITULO(String TITULO) {
        this.TITULO = TITULO;
    }

    public String getANEXO() {
        return ANEXO;
    }

    public void setANEXO(String ANEXO) {
        this.ANEXO = ANEXO;
    }

    public String getOBSERVACAO() {
        return OBSERVACAO;
    }

    public void setOBSERVACAO(String OBSERVACAO) {
        this.OBSERVACAO = OBSERVACAO;
    }

    public int getSTATUS_PEDIDO() {
        return STATUS_PEDIDO;
    }

    public void setSTATUS_PEDIDO(int STATUS_PEDIDO) {
        this.STATUS_PEDIDO = STATUS_PEDIDO;
    }

    public String getDATA_PEDIDO() {
        return DATA_PEDIDO;
    }

    public void setDATA_PEDIDO(String DATA_PEDIDO) {
        this.DATA_PEDIDO = DATA_PEDIDO;
    }

    public double getVALOR_TOTAL() {
        return VALOR_TOTAL;
    }

    public void setVALOR_TOTAL(double VALOR_TOTAL) {
        this.VALOR_TOTAL = VALOR_TOTAL;
    }

    public int getCOD_ENDERECO() {
        return COD_ENDERECO;
    }

    public void setCOD_ENDERECO(int COD_ENDERECO) {
        this.COD_ENDERECO = COD_ENDERECO;
    }

    public String getLOCAL_RETIRADA() {
        return LOCAL_RETIRADA;
    }

    public void setLOCAL_RETIRADA(String LOCAL_RETIRADA) {
        this.LOCAL_RETIRADA = LOCAL_RETIRADA;
    }


    public String getNOME() {
        return NOME;
    }

    public void setNOME(String NOME) {
        this.NOME = NOME;
    }

    public double getVALOR_ITEM() {
        return VALOR_ITEM;
    }

    public void setVALOR_ITEM(double VALOR_ITEM) {
        this.VALOR_ITEM = VALOR_ITEM;
    }
}
