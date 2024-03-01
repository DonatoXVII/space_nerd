package com.example.space_nerd.model;

public class MangaBean {
    int idManga;
    float prezzo;
    String descrizione;
    int numArticoli;
    String casaEditrice;
    String lingua;
    int numPagine;
    String img;

    public MangaBean() {
        idManga = 0;
        prezzo = 0;
        descrizione = "";
        numArticoli = 0;
        casaEditrice = "";
        lingua = "";
        numPagine = 0;
        img = "";
    }

    public MangaBean(int idManga, float prezzo, String descrizione, int numArticoli, String casaEditrice, String lingua, int numPagine, String img) {
        this.idManga = idManga;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.numArticoli = numArticoli;
        this.casaEditrice = casaEditrice;
        this.lingua = lingua;
        this.numPagine = numPagine;
        this.img = img;
    }

    public int getIdManga() {
        return idManga;
    }

    public void setIdManga(int idManga) {
        this.idManga = idManga;
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

    public String getCasaEditrice() {
        return casaEditrice;
    }

    public void setCasaEditrice(String casaEditrice) {
        this.casaEditrice = casaEditrice;
    }

    public String getLingua() {
        return lingua;
    }

    public void setLingua(String lingua) {
        this.lingua = lingua;
    }

    public int getNumPagine() {
        return numPagine;
    }

    public void setNumPagine(int numPagine) {
        this.numPagine = numPagine;
    }

    public String getImg() { return img; }

    public void setImg(String img) { this.img = img; }

    @Override
    public String toString() {
        return "MangaBean{" +
                "idManga=" + idManga +
                ", prezzo=" + prezzo +
                ", descrizione='" + descrizione + '\'' +
                ", numArticoli=" + numArticoli +
                ", casaEditrice='" + casaEditrice + '\'' +
                ", lingua='" + lingua + '\'' +
                ", numPagine=" + numPagine +
                ", img='" + img + '\'' +
                '}';
    }
}
