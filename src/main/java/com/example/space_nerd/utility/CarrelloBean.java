package com.example.space_nerd.utility;

import com.example.space_nerd.model.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CarrelloBean implements Serializable {
    transient List<Object> listaCarrello;
    static PopModel popModel = new PopModel();
    static FigureModel figureModel = new FigureModel();

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
                } else if(prod instanceof PopBean && prodotto instanceof PopBean && ((PopBean) prod).getIdPop()==((PopBean) prodotto).getIdPop() && count < ((PopBean) prodotto).getNumArticoli()) {
                    ((PopBean) prod).aumentaQuantita();
                } else if(prod instanceof FigureBean && prodotto instanceof FigureBean && ((FigureBean) prod).getIdFigure()==((FigureBean) prodotto).getIdFigure() && count < ((FigureBean) prodotto).getNumArticoli()) {
                    ((FigureBean) prod).aumentaQuantita();
                }
            }
        } else {
            if(prodotto instanceof MangaBean) {
                ((MangaBean) prodotto).setQuantitaCarrello(1);
            } else if(prodotto instanceof PopBean) {
                ((PopBean) prodotto).setImmagini(popModel.getAllImgPop((PopBean) prodotto));
                ((PopBean) prodotto).setQuantitaCarrello(1);
            } else if(prodotto instanceof FigureBean) {
                ((FigureBean) prodotto).setImmagini(figureModel.getAllImgFigure((FigureBean) prodotto));
                ((FigureBean) prodotto).setQuantitaCarrello(1);
            }
            this.listaCarrello.add(prodotto);
        }
    }

    public void rimuoviProdotto(int id) {
        for (Object prod : listaCarrello) {
            boolean rimuovi = prod instanceof MangaBean && ((MangaBean) prod).getIdManga() == id
                    || prod instanceof PopBean && ((PopBean) prod).getIdPop() == id
                    || prod instanceof FigureBean && ((FigureBean) prod).getIdFigure() == id;

            if (rimuovi) {
                if(prod instanceof MangaBean && ((MangaBean) prod).getIdManga() == id && ((MangaBean) prod).getQuantitaCarrello() == 1
                        || prod instanceof PopBean && ((PopBean) prod).getIdPop() == id && ((PopBean) prod).getQuantitaCarrello() == 1
                        || prod instanceof FigureBean && ((FigureBean) prod).getIdFigure() == id && ((FigureBean) prod).getQuantitaCarrello() == 1){
                    this.listaCarrello.remove(prod);
                } else if(prod instanceof MangaBean && ((MangaBean) prod).getIdManga() == id && ((MangaBean) prod).getQuantitaCarrello() > 1
                        || prod instanceof PopBean && ((PopBean) prod).getIdPop() == id && ((PopBean) prod).getQuantitaCarrello() > 1
                        || prod instanceof FigureBean && ((FigureBean) prod).getIdFigure() == id && ((FigureBean) prod).getQuantitaCarrello() > 1){
                    if(prod instanceof MangaBean) {
                        ((MangaBean) prod).decrementaQuantita();
                    } else if(prod instanceof PopBean) {
                        ((PopBean) prod).decrementaQuantita();
                    } else {
                        ((FigureBean) prod).decrementaQuantita();
                    }
                }
                break;
            }
        }
    }

    public void eliminaDalCarrello(int id) {
        for (Object prod : listaCarrello) {
            boolean rimuovi = prod instanceof MangaBean && ((MangaBean) prod).getIdManga() == id
                    || prod instanceof PopBean && ((PopBean) prod).getIdPop() == id
                    || prod instanceof FigureBean && ((FigureBean) prod).getIdFigure() == id;

            if (rimuovi) {
                if(prod instanceof MangaBean && ((MangaBean) prod).getIdManga() == id
                        || prod instanceof PopBean && ((PopBean) prod).getIdPop() == id
                        || prod instanceof FigureBean && ((FigureBean) prod).getIdFigure() == id){
                    this.listaCarrello.remove(prod);
                }
                break;
            }
        }
    }

    public int isPresente(Object prodotto) {
        for(Object prod : this.listaCarrello) {
            if(prodotto instanceof MangaBean && prod instanceof MangaBean && ((MangaBean) prodotto).getIdManga() == ((MangaBean) prod).getIdManga()) {
                return ((MangaBean) prod).getQuantitaCarrello();
            } else if(prodotto instanceof PopBean && prod instanceof PopBean && ((PopBean) prodotto).getIdPop() == ((PopBean) prod).getIdPop()) {
                return ((PopBean) prod).getQuantitaCarrello();
            } else if(prodotto instanceof FigureBean && prod instanceof FigureBean && ((FigureBean) prodotto).getIdFigure() == ((FigureBean) prod).getIdFigure()) {
                return ((FigureBean) prod).getQuantitaCarrello();
            }
        }
        return 0;
    }

    public float getPrezzoTotale() {
        float prezzo = 0;
        for(Object prodotto : this.listaCarrello) {
            if(prodotto instanceof MangaBean) {
                float parziale = ((MangaBean) prodotto).getPrezzo() * ((MangaBean) prodotto).getQuantitaCarrello();
                prezzo += parziale;
            } else if(prodotto instanceof PopBean) {
                float parziale = ((PopBean) prodotto).getPrezzo() * ((PopBean) prodotto).getQuantitaCarrello();
                prezzo += parziale;
            } else if(prodotto instanceof FigureBean) {
                float parziale = ((FigureBean) prodotto).getPrezzo() * ((FigureBean) prodotto).getQuantitaCarrello();
                prezzo += parziale;
            }
        }
        return prezzo;
    }

    public void svuotaCarrello() { this.listaCarrello.clear(); }
}
