package com.example.space_nerd.model;

import java.sql.Date;

public class OrdineBean {
    int id;
    float prezzo;
    Date data;
    String fattura;
    String email;

    public OrdineBean() {
        id = 0;
        prezzo = 0;
        data = null;
        fattura = "";
        email = "";
    }

    public OrdineBean(int id, float prezzo, Date data, String fattura, String email) {
        this.id = id;
        this.prezzo = prezzo;
        this.data = data;
        this.fattura = fattura;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    public Date getData() { return data; }

    public void setData(Date data) { this.data = data; }

    public String getFattura() {
        return fattura;
    }

    public void setFattura(String fattura) {
        this.fattura = fattura;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "OrdineBean{" +
                "id=" + id +
                ", prezzo=" + prezzo +
                ", data=" + data +
                ", fattura='" + fattura + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
