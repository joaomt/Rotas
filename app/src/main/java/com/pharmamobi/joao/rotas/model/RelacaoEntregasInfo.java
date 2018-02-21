package com.pharmamobi.joao.rotas.model;

/**
 * Created by joao on 12/11/2017.
 */

public class RelacaoEntregasInfo {
    private int ID,QTD_ENTREGAS,QTD_ENTREGAS_REALIZADAS,QTD_ENTREGAS_RETORNADAS;
    private float VALOR_TOTAL,VALOR_TOTAL_RECEBIDO;
    private String DATA_RELACAO;
    private String OBSERVACAO;
    private String SITUACAO_PAGAMENTO;
    private String STATUS_ENTREGA,NOME;
    private int COD_CLIENTE, COD_ORCAMENTO, NUM_COMPRO_VENDA,COD_ENTREGA;
    private float VALOR_ENTREGA;
    private String CEP;
    private String RUA;
    private String NUMERO;
    private String COMPLEMENTO;
    private String BAIRRO;
    private String CIDADE;
    private String ESTADO;
    private String LOCAL_RETIRADA;

    public RelacaoEntregasInfo(int ID,int QTD_ENTREGAS, int QTD_ENTREGAS_REALIZADAS, int QTD_ENTREGAS_RETORNADAS, float VALOR_TOTAL, float VALOR_TOTAL_RECEBIDO, String DATA_RELACAO, String OBSERVACAO, String SITUACAO_PAGAMENTO, String STATUS_ENTREGA, String NOME, int COD_CLIENTE, int COD_ORCAMENTO, int NUM_COMPRO_VENDA, int COD_ENTREGA, float VALOR_ENTREGA, String CEP, String RUA, String NUMERO, String COMPLEMENTO, String BAIRRO, String CIDADE, String ESTADO,String LOCAL_RETIRADA) {
        this.ID = ID;
        this.QTD_ENTREGAS = QTD_ENTREGAS;
        this.QTD_ENTREGAS_REALIZADAS = QTD_ENTREGAS_REALIZADAS;
        this.QTD_ENTREGAS_RETORNADAS = QTD_ENTREGAS_RETORNADAS;
        this.VALOR_TOTAL = VALOR_TOTAL;
        this.VALOR_TOTAL_RECEBIDO = VALOR_TOTAL_RECEBIDO;
        this.DATA_RELACAO = DATA_RELACAO;
        this.OBSERVACAO = OBSERVACAO;
        this.SITUACAO_PAGAMENTO = SITUACAO_PAGAMENTO;
        this.STATUS_ENTREGA = STATUS_ENTREGA;
        this.NOME = NOME;
        this.COD_CLIENTE = COD_CLIENTE;
        this.COD_ORCAMENTO = COD_ORCAMENTO;
        this.NUM_COMPRO_VENDA = NUM_COMPRO_VENDA;
        this.COD_ENTREGA = COD_ENTREGA;
        this.VALOR_ENTREGA = VALOR_ENTREGA;
        this.CEP = CEP;
        this.RUA = RUA;
        this.NUMERO = NUMERO;
        this.COMPLEMENTO = COMPLEMENTO;
        this.BAIRRO = BAIRRO;
        this.CIDADE = CIDADE;
        this.ESTADO = ESTADO;
        this.LOCAL_RETIRADA = LOCAL_RETIRADA;
    }

    public String getLOCAL_RETIRADA() {
        return LOCAL_RETIRADA;
    }

    public void setLOCAL_RETIRADA(String LOCAL_RETIRADA) {
        this.LOCAL_RETIRADA = LOCAL_RETIRADA;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNOME() {
        return NOME;
    }

    public void setNOME(String NOME) {
        this.NOME = NOME;
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

    public String getOBSERVACAO() {
        return OBSERVACAO;
    }

    public void setOBSERVACAO(String OBSERVACAO) {
        this.OBSERVACAO = OBSERVACAO;
    }

    public String getSITUACAO_PAGAMENTO() {
        return SITUACAO_PAGAMENTO;
    }

    public void setSITUACAO_PAGAMENTO(String SITUACAO_PAGAMENTO) {
        this.SITUACAO_PAGAMENTO = SITUACAO_PAGAMENTO;
    }

    public String getSTATUS_ENTREGA() {
        return STATUS_ENTREGA;
    }

    public void setSTATUS_ENTREGA(String STATUS_ENTREGA) {
        this.STATUS_ENTREGA = STATUS_ENTREGA;
    }

    public int getCOD_CLIENTE() {
        return COD_CLIENTE;
    }

    public void setCOD_CLIENTE(int COD_CLIENTE) {
        this.COD_CLIENTE = COD_CLIENTE;
    }

    public int getCOD_ORCAMENTO() {
        return COD_ORCAMENTO;
    }

    public void setCOD_ORCAMENTO(int COD_ORCAMENTO) {
        this.COD_ORCAMENTO = COD_ORCAMENTO;
    }

    public int getNUM_COMPRO_VENDA() {
        return NUM_COMPRO_VENDA;
    }

    public void setNUM_COMPRO_VENDA(int NUM_COMPRO_VENDA) {
        this.NUM_COMPRO_VENDA = NUM_COMPRO_VENDA;
    }

    public int getCOD_ENTREGA() {
        return COD_ENTREGA;
    }

    public void setCOD_ENTREGA(int COD_ENTREGA) {
        this.COD_ENTREGA = COD_ENTREGA;
    }

    public float getVALOR_ENTREGA() {
        return VALOR_ENTREGA;
    }

    public void setVALOR_ENTREGA(float VALOR_ENTREGA) {
        this.VALOR_ENTREGA = VALOR_ENTREGA;
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
