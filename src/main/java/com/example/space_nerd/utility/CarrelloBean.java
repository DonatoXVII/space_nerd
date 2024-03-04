package com.example.space_nerd.utility;

import com.example.space_nerd.model.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CarrelloBean implements Serializable {
    static MangaModel mangaModel = new MangaModel();
    static PopModel popModel = new PopModel();
    static FigureModel figureModel = new FigureModel();
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
        boolean prodottoGiaPresente = false;
        for(Object prod : this.listaCarrello) {
            if(prod instanceof MangaBean && prodotto instanceof MangaBean) {
                if(((MangaBean) prod).getIdManga() == ((MangaBean) prodotto).getIdManga()) {
                    ((MangaBean) prod).aumentaQuantita();
                    mangaModel.decrementaDisponibilita((MangaBean) prodotto);
                    prodottoGiaPresente = true;
                    break;
                }
            } else if(prod instanceof PopBean && prodotto instanceof PopBean) {
                if(((PopBean) prod).getIdPop() == ((PopBean) prodotto).getIdPop()) {
                    ((PopBean) prod).aumentaQuantita();
                    popModel.decrementaDisponibilita((PopBean) prodotto);
                    prodottoGiaPresente = true;
                    break;
                }
            } else if(prod instanceof FigureBean && prodotto instanceof FigureBean) {
                if(((FigureBean) prod).getIdFigure() == ((FigureBean) prodotto).getIdFigure()) {
                    ((FigureBean) prod).aumentaQuantita();
                    figureModel.decrementaDisponibilita((FigureBean) prodotto);
                    prodottoGiaPresente = true;
                    break;
                }
            }
        }

        if(!prodottoGiaPresente) {
            this.listaCarrello.add(prodotto);
            if(prodotto instanceof MangaBean) {
                ((MangaBean) prodotto).aumentaQuantita();
                mangaModel.decrementaDisponibilita((MangaBean) prodotto);
            } else if(prodotto instanceof PopBean) {
                ((PopBean) prodotto).aumentaQuantita();
                popModel.decrementaDisponibilita((PopBean) prodotto);
            } else if(prodotto instanceof FigureBean) {
                ((FigureBean) prodotto).aumentaQuantita();
                figureModel.decrementaDisponibilita((FigureBean) prodotto);
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
