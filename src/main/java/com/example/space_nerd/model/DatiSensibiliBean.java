package com.example.space_nerd.model;

import java.sql.Date;

public class DatiSensibiliBean {
    String email;
    String nome;
    String cognome;
    Date dataNascita;
    String via;
    int civico;
    String provincia;
    String comune;

    public DatiSensibiliBean() {
        email = "";
        nome = "";
        cognome = "";
        dataNascita = null;
        via = "";
        civico = 0;
        provincia = "";
        comune = "";
    }

    public DatiSensibiliBean(String email, String nome, String cognome, Date dataNascita, String via, int civico, String provincia, String comune) {
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.via = via;
        this.civico = civico;
        this.provincia = provincia;
        this.comune = comune;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public int getCivico() {
        return civico;
    }

    public void setCivico(int civico) {
        this.civico = civico;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }

    @Override
    public String toString() {
        return "DatiSensibiliBean{" +
                "email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", dataNascita=" + dataNascita +
                ", via='" + via + '\'' +
                ", civico=" + civico +
                ", provincia='" + provincia + '\'' +
                ", comune='" + comune + '\'' +
                '}';
    }
}
