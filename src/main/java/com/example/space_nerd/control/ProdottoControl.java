package com.example.space_nerd.control;

import com.google.gson.Gson;
import com.example.space_nerd.model.*;
import com.example.space_nerd.utility.CarrelloBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.print.attribute.standard.MediaName;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/ProdottoControl")
public class ProdottoControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static MangaModel mangaModel = new MangaModel();
    static PopModel popModel = new PopModel();
    static FigureModel figureModel = new FigureModel();
    static Logger logger = Logger.getLogger(ProdottoControl.class.getName());
    static String carrelloParameter = "carrello";
    static String prodottoParameter = "prodotto";
    static String dettagliJSP = "/dettagliProdotto.jsp";
    static String carrelloJSP = "./carrello.jsp";
    static String mangaParameter = "manga";
    static String figureParameter = "figure";
    static String ricercaParameter = "ricerca";

    public ProdottoControl() {super();}

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if (action != null) {
                switch (action.toLowerCase()) {
                    case "visualizzacatalogo":
                        visualizzaCatalogo(req, resp);
                        break;
                    case "visualizzadettagli":
                        visualizzaDettagli(req, resp);
                        break;
                    case "aggiungialcarrello":
                        aggiungiAlCarrello(req, resp);
                        break;
                    case "rimuovidalcarrello":
                        rimuoviDalCarrello(req, resp);
                        break;
                    case "svuotacarrello":
                        svuotaCarrello(req, resp);
                        break;
                    case "ricerca":
                        ricerca(req, resp);
                        break;
                    case "ricercasuggerimenti" :
                        ricercaSuggerimenti(req, resp);
                        break;
                    default:
                        RequestDispatcher errorDispatcher = getServletContext().getRequestDispatcher("/errore.jsp");
                        errorDispatcher.forward(req, resp);
                        break;
                }
            } else {
                showHomePage(req, resp);
            }
        } catch (ServletException | IOException e) {
            logger.info("Si è verificata un'eccezione:" + e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        try {
            doGet(req, resp);
        } catch (ServletException | IOException e) {
            logger.info("Si è verificata un'eccezione:" + e);
        }
    }

    private CarrelloBean getCarrelloBeanFromSession(HttpServletRequest req) {
        CarrelloBean carrelloBean = (CarrelloBean) req.getSession().getAttribute(carrelloParameter);
        if (carrelloBean == null) {
            carrelloBean = new CarrelloBean();
            req.getSession().setAttribute(carrelloParameter, carrelloBean);
        }
        return carrelloBean;
    }

    private void visualizzaCatalogo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<MangaBean> allManga = mangaModel.getAllManga();
        List<PopBean> allPop = popModel.getAllPop();
        List<FigureBean> allFigure = figureModel.getAllFigure();
        for(PopBean pop : allPop) {
            for(String immagine : popModel.getAllImgPop(pop)) {
                pop.aggiungiImmagine(immagine);
            }
        }
        for(FigureBean figure : allFigure) {
            for(String immagine : figureModel.getAllImgFigure(figure)) {
                figure.aggiungiImmagine(immagine);
            }
        }

        List<Object> prodotti = new ArrayList<>();
        prodotti.addAll(allManga);
        prodotti.addAll(allPop);
        prodotti.addAll(allFigure);
        req.setAttribute("prodotti", prodotti);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/catalogo.jsp");
        dispatcher.forward(req, resp);
    }

    private void visualizzaDettagli(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tipo = req.getParameter("Tipo");
        int id = Integer.parseInt(req.getParameter("Id"));
        Object prodotto = null;
        if(tipo.equalsIgnoreCase(mangaParameter)){
            prodotto = mangaModel.getById(id);
        } else if(tipo.equalsIgnoreCase("pop")) {
            prodotto = popModel.getById(id);
            List<String> immaginiPop = popModel.getAllImgPop((PopBean) prodotto);
            for(String immagine : immaginiPop) {
                ((PopBean) prodotto).aggiungiImmagine(immagine);
            }
        } else if(tipo.equalsIgnoreCase(figureParameter)) {
            prodotto = figureModel.getById(id);
            List<String> immaginiFIgure = figureModel.getAllImgFigure((FigureBean) prodotto);
            for(String immagine : immaginiFIgure) {
                ((FigureBean) prodotto).aggiungiImmagine(immagine);
            }
        }
        req.setAttribute(prodottoParameter, prodotto);
        RequestDispatcher dispatcher = req.getRequestDispatcher(dettagliJSP);
        dispatcher.forward(req, resp);
    }

    private void aggiungiAlCarrello(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CarrelloBean carrelloBean = getCarrelloBeanFromSession(req);
        String tipo = req.getParameter("Tipo");
        int id = Integer.parseInt(req.getParameter("Id"));
        int countProdotto = 0;
        if (tipo.equalsIgnoreCase(mangaParameter)) {
            if(mangaModel.verificaDisponibilita(id)) {
                carrelloBean.aggiungiProdotto(mangaModel.getById(id));
                for(Object manga : carrelloBean.getListaCarrello()){
                    if(manga instanceof MangaBean) {
                        countProdotto = ((MangaBean) manga).getQuantitaCarrello();
                    }
                }
            }
        }else if (tipo.equalsIgnoreCase("pop")) {
            if(popModel.verificaDisponibilita(id)) {
                carrelloBean.aggiungiProdotto(popModel.getById(id));
            }
        } else if (tipo.equalsIgnoreCase(figureParameter) && figureModel.verificaDisponibilita(id)) {
            carrelloBean.aggiungiProdotto(figureModel.getById(id));
        }
        req.getSession().removeAttribute("countProdotto");
        req.getSession().setAttribute("countProdotto", countProdotto);
        req.getSession().setAttribute(carrelloParameter, carrelloBean);
        resp.sendRedirect(carrelloJSP);
    }

    private void rimuoviDalCarrello(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CarrelloBean carrelloBean = getCarrelloBeanFromSession(req);
        int id = Integer.parseInt(req.getParameter("Id"));
        int count = 0;
        carrelloBean.rimuoviProdotto(id);
        for(Object proddotto : carrelloBean.getListaCarrello()) {
            if(proddotto instanceof MangaBean) {
                count = ((MangaBean) proddotto).getQuantitaCarrello();
            }
        }
        req.getSession().removeAttribute("countProdotto");
        req.getSession().setAttribute("countProdotto", count);
        req.getSession().setAttribute(carrelloParameter, carrelloBean);
        resp.sendRedirect(carrelloJSP);
    }

    private void svuotaCarrello(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        CarrelloBean carrelloBean = getCarrelloBeanFromSession(request);
        carrelloBean.svuotaCarrello();
        int count = 0;
        for(Object proddotto : carrelloBean.getListaCarrello()) {
            if(proddotto instanceof MangaBean) {
                count = ((MangaBean) proddotto).getQuantitaCarrello();
            }
        }
        request.getSession().removeAttribute("countProdotto");
        request.getSession().setAttribute("countProdotto", count);
        request.getSession().setAttribute(carrelloParameter, carrelloBean);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/carrello.jsp");
        dispatcher.forward(request, response);
    }

    private void ricerca(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String descrizione = request.getParameter(ricercaParameter);
        MangaBean mangaBean = mangaModel.getMangaPerDescrizione(descrizione);
        PopBean popBean = popModel.getPopPerDescrizione(descrizione);
        FigureBean figureBean = figureModel.getFigurePerDescrizione(descrizione);
        if(mangaBean.getIdManga() != 0){
            request.setAttribute(prodottoParameter, mangaBean);
        }
        if(popBean.getIdPop() != 0) {
            List<String> immagini = popModel.getAllImgPop(popBean);
            for(String immagine : immagini) {
                popBean.aggiungiImmagine(immagine);
            }
            request.setAttribute(prodottoParameter, popBean);
        }
        if(figureBean.getIdFigure() != 0) {
            List<String> immagini = figureModel.getAllImgFigure(figureBean);
            for(String immagine : immagini) {
                figureBean.aggiungiImmagine(immagine);
            }
            request.setAttribute(prodottoParameter, figureBean);
        }
        if(mangaBean.getIdManga() == 0 && popBean.getIdPop() == 0 && figureBean.getIdFigure() == 0) {
            request.setAttribute(prodottoParameter, "Nessun Prodotto Trovato!");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(dettagliJSP);
        dispatcher.forward(request, response);
    }

    private void ricercaSuggerimenti(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ricerca = request.getParameter(ricercaParameter);
        List<String> suggerimenti = new ArrayList<>();
        suggerimenti.addAll(mangaModel.getSuggerimentiManga(ricerca));
        suggerimenti.addAll(popModel.getSuggerimentiPop(ricerca));
        suggerimenti.addAll(figureModel.getSuggerimentiFigure(ricerca));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print("{\"suggerimenti\": " + new Gson().toJson(suggerimenti) + "}");
        out.flush();
    }

    private void showHomePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        List<MangaBean> bestManga = mangaModel.getMiglioriManga();
        List<PopBean> bestPop = popModel.getMiglioriPop();
        List<FigureBean> bestFigure = figureModel.getMiglioriFigure();
        for(PopBean pop : bestPop) {
            for(String immagine : popModel.getAllImgPop(pop)) {
                pop.aggiungiImmagine(immagine);
            }
        }
        for(FigureBean figure : bestFigure) {
            for(String immagine : figureModel.getAllImgFigure(figure)) {
                figure.aggiungiImmagine(immagine);
            }
        }
        List<Object> bestProdotti = new ArrayList<>();
        bestProdotti.addAll(bestManga);
        bestProdotti.addAll(bestPop);
        bestProdotti.addAll(bestFigure);
        req.setAttribute("bestProdotti", bestProdotti);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/index.jsp");
        dispatcher.forward(req, resp);
    }
}
