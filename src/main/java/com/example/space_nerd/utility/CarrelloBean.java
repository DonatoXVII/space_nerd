package com.example.space_nerd.utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CarrelloBean implements Serializable {
    transient List<Object> listaCarrello;

    public CarrelloBean() {
        listaCarrello = new ArrayList<>();
    }

    public CarrelloBean(List<Object> listaCarrello) {
        this.listaCarrello = listaCarrello;
    }

    public List<Object> getListaCarrello() {
        return listaCarrello;
    }

    public void setListaCarrello(List<Object> listaCarrello) {
        this.listaCarrello = listaCarrello;
    }

    @Override
    public String toString() {
        return "CarrelloBean{" +
                "listaCarrello=" + listaCarrello +
                '}';
    }

    public void aggiungiProdotto(Object prodotto) {
        this.listaCarrello.add(prodotto);
    }
    public void rimuoviProdotto() {
        for (Object prod : listaCarrello) {
            boolean rimuovi = false;

            if (prod != null) {
                rimuovi = true;
            }

            if (rimuovi) {
                this.listaCarrello.remove(prod);
                break;
            }
        }
    }
    public void svuotaCarrello() { this.listaCarrello.clear(); }
}
