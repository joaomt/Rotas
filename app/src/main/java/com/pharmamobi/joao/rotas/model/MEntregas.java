package com.pharmamobi.joao.rotas.model;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by joao on 09/06/2017.
 */

public class MEntregas {
    private String OBSERVACAO;
    private String SITUACAO_PAGAMENTO;
    private String STATUS_ENTREGA;
    private String DATA_RELACAO;
    private String LOCAL_RETIRADA;
    private int COD_CLIENTE, COD_ORCAMENTO, NUM_COMPRO_VENDA,COD_ENTREGA,COD_ENDERECO;
    private float VALOR_ENTREGA;


    public MEntregas(){}


    public MEntregas(int COD_ENTREGA,int COD_ENDERECO, String OBSERVACAO, String SITUACAO_PAGAMENTO, String STATUS_ENTREGA,String DATA_RELACAO, int COD_CLIENTE, int COD_ORCAMENTO, int NUM_COMPRO_VENDA, float VALOR_ENTREGA,String LOCAL_RETIRADA) {
        this.COD_ENTREGA = COD_ENTREGA;
        this.COD_ENDERECO = COD_ENDERECO;
        this.OBSERVACAO = OBSERVACAO;
        this.SITUACAO_PAGAMENTO = SITUACAO_PAGAMENTO;
        this.STATUS_ENTREGA = STATUS_ENTREGA;
        this.DATA_RELACAO = DATA_RELACAO;
        this.COD_CLIENTE = COD_CLIENTE;
        this.COD_ORCAMENTO = COD_ORCAMENTO;
        this.NUM_COMPRO_VENDA = NUM_COMPRO_VENDA;
        this.VALOR_ENTREGA = VALOR_ENTREGA;
        this.LOCAL_RETIRADA = LOCAL_RETIRADA;
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

    public int getCOD_ENTREGA() {
        return COD_ENTREGA;
    }

    public void setCOD_ENTREGA(int COD_ENTREGA) {
        this.COD_ENTREGA = COD_ENTREGA;
    }

    public void setVALOR_ENTREGA(float VALOR_ENTREGA) {
        this.VALOR_ENTREGA = VALOR_ENTREGA;
    }

    public void setENDERECO(String ENDERECO) {
        ENDERECO = ENDERECO;
    }

    public void setOBSERVACAO(String observacao) {
        this.OBSERVACAO = observacao;
    }

    public void setSITUACAO_PAGAMENTO(String SITUACAO_PAGAMENTO) {
        this.SITUACAO_PAGAMENTO = SITUACAO_PAGAMENTO;
    }

    public void setSTATUS_ENTREGA(String STATUS_ENTREGA) {
        this.STATUS_ENTREGA = STATUS_ENTREGA;
    }

    public void setDATA_RELACAO(String DATA_RELACAO) {
        this.DATA_RELACAO = DATA_RELACAO;
    }

    public void setCOD_CLIENTE(int COD_CLIENTE) {
        this.COD_CLIENTE = COD_CLIENTE;
    }

    public void setCOD_ORCAMENTO(int COD_ORCAMENTO) {
        this.COD_ORCAMENTO = COD_ORCAMENTO;
    }

    public void setNUM_COMPRO_VENDA(int NUM_COMPRO_VENDA) {
        this.NUM_COMPRO_VENDA = NUM_COMPRO_VENDA;
    }

    public void setValor_entrega(float valor_entrega) {
        this.VALOR_ENTREGA = valor_entrega;
    }


    public String getOBSERVACAO() {
        return OBSERVACAO;
    }

    public String getSITUACAO_PAGAMENTO() {
        return SITUACAO_PAGAMENTO;
    }

    public String getSTATUS_ENTREGA() {
        return STATUS_ENTREGA;
    }

    public String getDATA_RELACAO() {
        String data =  DATA_RELACAO.replaceAll("-","/");
        String[]s = data.split("/");
        String novaData = s[2]+"/"+s[1]+"/"+s[0];
        return novaData;
    }

    public int getCOD_CLIENTE() {
        return COD_CLIENTE;
    }

    public int getCOD_ORCAMENTO() {
        return COD_ORCAMENTO;
    }

    public int getNUM_COMPRO_VENDA() {
        return NUM_COMPRO_VENDA;
    }

    public float getVALOR_ENTREGA() {
        return VALOR_ENTREGA;
    }
}
