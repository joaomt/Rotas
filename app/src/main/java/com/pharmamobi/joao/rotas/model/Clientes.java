package com.pharmamobi.joao.rotas.model;

import java.sql.Date;

/**
 * Created by joao on 25/09/2017.
 */

public class Clientes {
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    private int ID;
    private String NOME;
    private String SEXO;
    private Date DATA_NASCIMENTO;
    private byte[] IMG_PERFIL;
    private String CPF;

    private String EMAIL;
    private String SENHA;
    private String USER_ID_FACE;
    private String USER_ID_GOOGLE;

    public Clientes(String EMAIL, String NOME, String CPF, String SENHA) {
        this.NOME = NOME;
        this.EMAIL = EMAIL;
        this.CPF = CPF;
        this.SENHA = SENHA;
    }

    public Clientes(String NOME, String SEXO, Date DATA_NASCIMENTO, byte[] IMG_PERFIL, String CPF, String EMAIL, String SENHA, String USER_ID_FACE, String USER_ID_GOOGLE) {
        this.NOME = NOME;
        this.SEXO = SEXO;
        this.DATA_NASCIMENTO = DATA_NASCIMENTO;
        this.IMG_PERFIL = IMG_PERFIL;
        this.CPF = CPF;
        this.EMAIL = EMAIL;
        this.SENHA = SENHA;
        this.USER_ID_FACE = USER_ID_FACE;
        this.USER_ID_GOOGLE = USER_ID_GOOGLE;
    }

    public String getNOME() {
        return NOME;
    }

    public void setNOME(String NOME) {
        this.NOME = NOME;
    }

    public String getSEXO() {
        return SEXO;
    }

    public void setSEXO(String SEXO) {
        this.SEXO = SEXO;
    }

    public Date getDATA_NASCIMENTO() {
        return DATA_NASCIMENTO;
    }

    public void setDATA_NASCIMENTO(Date DATA_NASCIMENTO) {
        this.DATA_NASCIMENTO = DATA_NASCIMENTO;
    }

    public byte[] getIMG_PERFIL() {
        return IMG_PERFIL;
    }

    public void setIMG_PERFIL(byte[] IMG_PERFIL) {
        this.IMG_PERFIL = IMG_PERFIL;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getSENHA() {
        return SENHA;
    }

    public void setSENHA(String SENHA) {
        this.SENHA = SENHA;
    }

    public String getUSER_ID_FACE() {
        return USER_ID_FACE;
    }

    public void setUSER_ID_FACE(String USER_ID_FACE) {
        this.USER_ID_FACE = USER_ID_FACE;
    }

    public String getUSER_ID_GOOGLE() {
        return USER_ID_GOOGLE;
    }

    public void setUSER_ID_GOOGLE(String USER_ID_GOOGLE) {
        this.USER_ID_GOOGLE = USER_ID_GOOGLE;
    }
}
