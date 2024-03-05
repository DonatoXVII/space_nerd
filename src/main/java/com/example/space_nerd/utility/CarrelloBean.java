package com.example.space_nerd.utility;

import com.example.space_nerd.model.*;

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
        int count = 0;
        boolean prodottoGiaPresente = false;
        for(Object prod : this.listaCarrello) {
            if(prod instanceof MangaBean && prodotto instanceof MangaBean && ((MangaBean) prod).getIdManga()==((MangaBean) prodotto).getIdManga()) {
                count = ((MangaBean) prod).getQuantitaCarrello();
            } else if(prod instanceof PopBean && prodotto instanceof PopBean && ((PopBean) prod).getIdPop()==((PopBean) prodotto).getIdPop()) {
                count = ((PopBean) prod).getQuantitaCarrello();
            } else if(prod instanceof FigureBean && prodotto instanceof FigureBean && ((FigureBean) prod).getIdFigure()==((FigureBean) prodotto).getIdFigure()) {
                count = ((FigureBean) prod).getQuantitaCarrello();
            }
        }

        if(count > 0) {
            prodottoGiaPresente = true;
        }

        if(prodottoGiaPresente) {
            for(Object prod : this.listaCarrello) {
                if(prod instanceof MangaBean && prodotto instanceof MangaBean && ((MangaBean) prod).getIdManga()==((MangaBean) prodotto).getIdManga() && count < ((MangaBean) prodotto).getNumArticoli()) {
                    ((MangaBean) prod).aumentaQuantita();
                    break;
                } else if(prod instanceof PopBean && prodotto instanceof PopBean && ((PopBean) prod).getIdPop()==((PopBean) prodotto).getIdPop() && count < ((PopBean) prodotto).getNumArticoli()) {
                    ((PopBean) prod).aumentaQuantita();
                    break;
                } else if(prod instanceof FigureBean && prodotto instanceof FigureBean && ((FigureBean) prod).getIdFigure()==((FigureBean) prodotto).getIdFigure() && count < ((FigureBean) prodotto).getNumArticoli()) {
                    ((FigureBean) prod).aumentaQuantita();
                    break;
                } else {
                    break;
                }
            }
        } else {
            this.listaCarrello.add(prodotto);
            if(prodotto instanceof MangaBean) {
                ((MangaBean) prodotto).setQuantitaCarrello(1);
            } else if(prodotto instanceof PopBean) {
                ((PopBean) prodotto).setQuantitaCarrello(1);
            } else if(prodotto instanceof FigureBean) {
                ((FigureBean) prodotto).setQuantitaCarrello(1);
            }
        }
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

    public float getPrezzoTotale() {
        float prezzo = 0;
        for(Object prodotto : this.listaCarrello) {
            if(prodotto instanceof MangaBean) {
                prezzo += ((MangaBean) prodotto).getPrezzo();
            } else if(prodotto instanceof PopBean) {
                prezzo += ((PopBean) prodotto).getPrezzo();
            } else if(prodotto instanceof FigureBean) {
                prezzo += ((FigureBean) prodotto).getPrezzo();
            }
        }
        return prezzo;
    }
    public void svuotaCarrello() { this.listaCarrello.clear(); }
}
