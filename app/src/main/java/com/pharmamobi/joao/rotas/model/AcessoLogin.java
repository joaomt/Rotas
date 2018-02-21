package com.pharmamobi.joao.rotas.model;

/**
 * Created by joao on 27/09/2017.
 */

public class AcessoLogin {
    String USUARIO, EMAIL,SENHA,USER_ID_FACE,USER_ID_GOOGLE;
    int TIPO_USER,ID;

    public AcessoLogin(String USUARIO, String SENHA) {
        this.USUARIO = USUARIO;
        this.SENHA = SENHA;
    }

    public AcessoLogin(String EMAIL, String SENHA, String USER_ID_FACE, String USER_ID_GOOGLE, int TIPO_USER) {
        this.EMAIL = EMAIL;
        this.SENHA = SENHA;
        this.USER_ID_FACE = USER_ID_FACE;
        this.USER_ID_GOOGLE = USER_ID_GOOGLE;
        this.TIPO_USER = TIPO_USER;
    }

    public String getUSUARIO() {

        return USUARIO;
    }

    public void setUSUARIO(String USUARIO) {
        this.USUARIO = USUARIO;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public int getTIPO_USER() {
        return TIPO_USER;
    }

    public void setTIPO_USER(int TIPO_USER) {
        this.TIPO_USER = TIPO_USER;
    }
}
