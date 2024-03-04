package com.example.space_nerd.model;

import java.util.ArrayList;
import java.util.List;

public class PopBean {
    int idPop;
    float prezzo;
    String descrizione;
    int numArticoli;
    int numSerie;
    String serie;
    List<String> immagini;
    int quantitaCarrello;

    public PopBean() {
        idPop = 0;
        prezzo = 0;
        descrizione = "";
        numArticoli = 0;
        numSerie = 0;
        serie = "";
        immagini = new ArrayList<>();
        quantitaCarrello = 0;
    }

    public PopBean(int idPop, float prezzo, String descrizione, int numArticoli, int numSerie, String serie, List<String> immagini, int quantitaCarrello) {
        this.idPop = idPop;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.numArticoli = numArticoli;
        this.numSerie = numSerie;
        this.serie = serie;
        this.immagini = immagini;
        this.quantitaCarrello = quantitaCarrello;
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

    public List<String> getImmagini() { return immagini; }

    public void setImmagini(List<String> immagini) { this.immagini = immagini; }

    public void aggiungiImmagine(String immagine) {
        this.immagini.add(immagine);
    }

    public int getQuantitaCarrello() { return quantitaCarrello; }

    public void setQuantitaCarrello(int quantitaCarrello) { this.quantitaCarrello = quantitaCarrello; }

    public void aumentaQuantita() { this.quantitaCarrello++;}

    public void decrementaQuantita() { this.quantitaCarrello--;}

    @Override
    public String toString() {
        return "PopBean{" +
                "idPop=" + idPop +
                ", prezzo=" + prezzo +
                ", descrizione='" + descrizione + '\'' +
                ", numArticoli=" + numArticoli +
                ", numSerie=" + numSerie +
                ", serie='" + serie + '\'' +
                ", immagini=" + immagini +
                ", quantitaCarrello=" + quantitaCarrello +
                '}';
    }
}
