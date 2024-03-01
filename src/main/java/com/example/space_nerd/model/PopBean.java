package com.example.space_nerd.model;

public class PopBean {
    int idPop;
    float prezzo;
    String descrizione;
    int numArticoli;
    int numSerie;
    String serie;

    public PopBean() {
        idPop = 0;
        prezzo = 0;
        descrizione = "";
        numArticoli = 0;
        numSerie = 0;
        serie = "";
    }

    public PopBean(int idPop, float prezzo, String descrizione, int numArticoli, int numSerie, String serie) {
        this.idPop = idPop;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.numArticoli = numArticoli;
        this.numSerie = numSerie;
        this.serie = serie;
    }

    public int getIdPop() {
        return idPop;
    }

    public void setIdPop(int idPop) {
        this.idPop = idPop;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getNumArticoli() {
        return numArticoli;
    }

    public void setNumArticoli(int numArticoli) {
        this.numArticoli = numArticoli;
    }

    public int getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(int numSerie) {
        this.numSerie = numSerie;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    @Override
    public String toString() {
        return "PopBean{" +
                "idPop=" + idPop +
                ", prezzo=" + prezzo +
                ", descrizione='" + descrizione + '\'' +
                ", numArticoli=" + numArticoli +
                ", numSerie=" + numSerie +
                ", serie='" + serie + '\'' +
                '}';
    }
}
