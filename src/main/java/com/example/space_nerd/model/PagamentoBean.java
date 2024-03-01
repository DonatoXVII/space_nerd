package com.example.space_nerd.model;

import java.sql.Date;

public class PagamentoBean {
    int id;
    String numeroCarta;
    int ccv;
    Date scadenza;
    String titolare;

    public PagamentoBean() {
        id = 0;
        numeroCarta = "";
        ccv = 0;
        scadenza = null;
        titolare = "";
    }

    public PagamentoBean(int id, String numeroCarta, int ccv, Date scadenza, String titolare) {
        this.id = id;
        this.numeroCarta = numeroCarta;
        this.ccv = ccv;
        this.scadenza = scadenza;
        this.titolare = titolare;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroCarta() {
        return numeroCarta;
    }

    public void setNumeroCarta(String numeroCarta) {
        this.numeroCarta = numeroCarta;
    }

    public int getCcv() {
        return ccv;
    }

    public void setCcv(int ccv) {
        this.ccv = ccv;
    }

    public Date getScadenza() {
        return scadenza;
    }

    public void setScadenza(Date scadenza) {
        this.scadenza = scadenza;
    }

    public String getTitolare() {
        return titolare;
    }

    public void setTitolare(String titolare) {
        this.titolare = titolare;
    }

    @Override
    public String toString() {
        return "PagamentoBean{" +
                "id=" + id +
                ", numeroCarta='" + numeroCarta + '\'' +
                ", ccv=" + ccv +
                ", scadenza=" + scadenza +
                ", titolare='" + titolare + '\'' +
                '}';
    }
}
