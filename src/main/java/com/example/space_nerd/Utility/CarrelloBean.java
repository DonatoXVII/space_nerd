package com.example.space_nerd.Utility;

import com.example.space_nerd.Model.FigureBean;
import com.example.space_nerd.Model.MangaBean;
import com.example.space_nerd.Model.PopBean;

import java.util.ArrayList;
import java.util.List;

public class CarrelloBean {
    List<Object> listaCarrello;

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
            if (prod instanceof MangaBean && ((MangaBean) prod).getIdManga() == id) {
                this.listaCarrello.remove(prod);
                break;
            } else if(prod instanceof PopBean && ((PopBean) prod).getIdPop() == id) {
                this.listaCarrello.remove(prod);
                break;
            } else if(prod instanceof FigureBean && ((FigureBean) prod).getIdFigure() == id) {
                this.listaCarrello.remove(prod);
                break;
            }
        }
    }
    public void svuotaCarrello() { this.listaCarrello.clear(); }
}
