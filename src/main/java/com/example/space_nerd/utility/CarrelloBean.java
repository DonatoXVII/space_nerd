package com.example.space_nerd.utility;

import com.example.space_nerd.model.FigureBean;
import com.example.space_nerd.model.MangaBean;
import com.example.space_nerd.model.PopBean;

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
    public void rimuoviProdotto(int id) {
        for (Object prod : listaCarrello) {
            boolean rimuovi = prod instanceof MangaBean && ((MangaBean) prod).getIdManga() == id
                    || prod instanceof PopBean && ((PopBean) prod).getIdPop() == id
                    || prod instanceof FigureBean && ((FigureBean) prod).getIdFigure() == id;

            if (rimuovi) {
                this.listaCarrello.remove(prod);
                break;
            }
        }
    }
    public void svuotaCarrello() { this.listaCarrello.clear(); }
}
