package com.example.space_nerd.Model;

public class FigureBean {
    int idFigure;
    float prezzo;
    String descrizione;
    int numArticoli;
    String materiale;
    int altezza;
    String personaggio;

    public FigureBean() {
        idFigure = 0;
        prezzo = 0;
        descrizione = "";
        numArticoli = 0;
        materiale = "";
        altezza = 0;
        personaggio = "";
    }

    public FigureBean(int idFigure, float prezzo, String descrizione, int numArticoli, String materiale, int altezza, String personaggio) {
        this.idFigure = idFigure;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.numArticoli = numArticoli;
        this.materiale = materiale;
        this.altezza = altezza;
        this.personaggio = personaggio;
    }

    public int getIdFigure() {
        return idFigure;
    }

    public void setIdFigure(int idFigure) {
        this.idFigure = idFigure;
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

    public String getMateriale() {
        return materiale;
    }

    public void setMateriale(String materiale) {
        this.materiale = materiale;
    }

    public int getAltezza() {
        return altezza;
    }

    public void setAltezza(int altezza) {
        this.altezza = altezza;
    }

    public String getPersonaggio() {
        return personaggio;
    }

    public void setPersonaggio(String personaggio) {
        this.personaggio = personaggio;
    }

    @Override
    public String toString() {
        return "FigureBean{" +
                "idFigure=" + idFigure +
                ", prezzo=" + prezzo +
                ", descrizione='" + descrizione + '\'' +
                ", numArticoli=" + numArticoli +
                ", materiale='" + materiale + '\'' +
                ", altezza=" + altezza +
                ", personaggio='" + personaggio + '\'' +
                '}';
    }
}