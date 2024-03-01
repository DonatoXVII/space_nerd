package com.example.space_nerd.model;

public class UtenteBean {
    String email;
    String password;
    boolean tipo;

    public UtenteBean(){
        email="";
        password="";
        tipo=false; /*false utente registrato, true admin*/
    }

    public UtenteBean(String email, String password, boolean tipo){
        this.email=email;
        this.password=password;
        this.tipo=tipo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isTipo() {
        return tipo;
    }

    public void setTipo(boolean tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "UtenteBean{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", tipo=" + tipo +
                '}';
    }
}
