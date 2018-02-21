package com.pharmamobi.joao.rotas.model;

/**
 * Created by joao on 27/09/2017.
 */

public class OrcamentoAux {
    private int COD_ORCAMENTO;
    private int COD_CLIENTE;
    private int COD_EMPRESA;
    private String TITULO;
    private String ANEXO;
    private String OBSERVACAO;
    private int STATUS_PEDIDO;
    private String DATA_PEDIDO;
    private double VALOR_TOTAL;
    private String LOCAL_RETIRADA;

    //endereço
    private String CEP;
    private String RUA;
    private String NUMERO;
    private String COMPLEMENTO;
    private String BAIRRO;
    private String CIDADE;
    private String ESTADO;

    public OrcamentoAux(int COD_ORCAMENTO, int COD_CLIENTE, int COD_EMPRESA, String TITULO, String ANEXO, String OBSERVACAO, int STATUS_PEDIDO, String DATA_PEDIDO, double VALOR_TOTAL, String LOCAL_RETIRADA, String CEP, String RUA, String NUMERO, String COMPLEMENTO, String BAIRRO, String CIDADE, String ESTADO) {
        this.COD_ORCAMENTO = COD_ORCAMENTO;
        this.COD_CLIENTE = COD_CLIENTE;
        this.COD_EMPRESA = COD_EMPRESA;
        this.TITULO = TITULO;
        this.ANEXO = ANEXO;
        this.OBSERVACAO = OBSERVACAO;
        this.STATUS_PEDIDO = STATUS_PEDIDO;
        this.DATA_PEDIDO = DATA_PEDIDO;
        this.VALOR_TOTAL = VALOR_TOTAL;
        this.LOCAL_RETIRADA = LOCAL_RETIRADA;
        this.CEP = CEP;
        this.RUA = RUA;
        this.NUMERO = NUMERO;
        this.COMPLEMENTO = COMPLEMENTO;
        this.BAIRRO = BAIRRO;
        this.CIDADE = CIDADE;
        this.ESTADO = ESTADO;
    }

    public int getCOD_ORCAMENTO() {
        return COD_ORCAMENTO;
    }

    public void setCOD_ORCAMENTO(int COD_ORCAMENTO) {
        this.COD_ORCAMENTO = COD_ORCAMENTO;
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


    public String getLOCAL_RETIRADA() {
        return LOCAL_RETIRADA;
    }

    public void setLOCAL_RETIRADA(String LOCAL_RETIRADA) {
        this.LOCAL_RETIRADA = LOCAL_RETIRADA;
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

    public String getNUMERO() {
        return NUMERO;
    }

    public void setNUMERO(String NUMERO) {
        this.NUMERO = NUMERO;
    }

    public String getCOMPLEMENTO() {
        return COMPLEMENTO;
    }

    public void setCOMPLEMENTO(String COMPLEMENTO) {
        this.COMPLEMENTO = COMPLEMENTO;
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
}
