package com.example.space_nerd.Model;

public class IndirizzoBean {
    int id;
    String nome;
    String cognome;
    String via;
    int civico;
    String provincia;
    String comune;
    int cap;

    public IndirizzoBean() {
        id = 0;
        nome = "";
        cognome = "";
        via = "";
        civico = 0;
        provincia = "";
        comune = "";
        cap = 0;
    }

    public IndirizzoBean(int id, String nome, String cognome, String via, int civico, String provincia, String comune, int cap) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.via = via;
        this.civico = civico;
        this.provincia = provincia;
        this.comune = comune;
        this.cap = cap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    @Override
    public String toString() {
        return "IndirizzoBean{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", via='" + via + '\'' +
                ", civico=" + civico +
                ", provincia='" + provincia + '\'' +
                ", comune='" + comune + '\'' +
                ", cap=" + cap +
                '}';
    }
}
