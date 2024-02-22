package com.example.space_nerd.Model;

public class OrdineBean {
    int id;
    float prezzo;
    String fattura;
    String email;

    public OrdineBean() {
        id = 0;
        prezzo = 0;
        fattura = "";
        email = "";
    }

    public OrdineBean(int id, float prezzo, String fattura, String email) {
        this.id = id;
        this.prezzo = prezzo;
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
                ", fattura='" + fattura + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
