package com.example.space_nerd.control;

import com.example.space_nerd.model.*;
import com.example.space_nerd.utility.CarrelloBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
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
    static String idMangaParameter = "IdManga";
    static String idPopParameter = "IdPop";
    static String idFigureParameter = "IdFigure";
    static String prodottoParameter = "prodotto";
    static String dettagliJSP = "/dettagliProdotto.jsp";
    static String carrelloJSP = "/carrello.jsp";

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
                    case "aggiungimangaalcarrello":
                        aggiungiMangaAlCarrello(req, resp);
                        break;
                    case "aggiungipopalcarrello":
                        aggiungiPopAlCarrello(req, resp);
                        break;
                    case "aggiungifigurealcarrello":
                        aggiungiFigureAlCarrello(req, resp);
                        break;
                    case "rimuovimangadalcarrello":
                        rimuoviMangaDalCarrello(req, resp);
                        break;
                    case "rimuovipopdalcarrello":
                        rimuoviPopDalCarrello(req, resp);
                        break;
                    case "rimuovifiguredalcarrello":
                        rimuoviFigureDalCarrello(req, resp);
                        break;
                    default:
                        RequestDispatcher errorDispatcher = getServletContext().getRequestDispatcher("/errore.jsp");
                        errorDispatcher.forward(req, resp);
                        break;
                }
            } else {
                showHomePage(req, resp);
            }
        } catch (ServletException | IOException | SQLException e) {
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
        List<MangaBean> allManga = mangaModel.allManga();
        List<PopBean> allPop = popModel.allPop();
        List<FigureBean> allFigure = figureModel.allFigure();

        List<String> imgPerPop = popModel.oneImgPerPop();
        List<String> imgPerFigure = figureModel.oneImgPerFigure();

        List<Object> prodotti = new ArrayList<>();
        prodotti.addAll(allManga);
        prodotti.addAll(allPop);
        prodotti.addAll(allFigure);

        req.setAttribute("prodotti", prodotti);
        req.setAttribute("imgPop", imgPerPop);
        req.setAttribute("imgFigure", imgPerFigure);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/catalogo.jsp");
        dispatcher.forward(req, resp);
    }

    private void visualizzaDettagli(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tipo = req.getParameter("Tipo");
        int id = Integer.parseInt(req.getParameter("Id"));
        Object prodotto = null;
        if(tipo.equalsIgnoreCase("manga")){
            prodotto = mangaModel.getById(id);
        } else if(tipo.equalsIgnoreCase("pop")) {
            prodotto = popModel.getById(id);
            List<String> immaginiPop = popModel.imgPerPop((PopBean) prodotto);
            req.setAttribute("immaginiPop", immaginiPop);
        } else if(tipo.equalsIgnoreCase("figure")) {
            prodotto = figureModel.getById(id);
            List<String> immaginiFigure = figureModel.imgPerFigure((FigureBean) prodotto);
            req.setAttribute("immaginiFigure", immaginiFigure);
        }
        req.setAttribute(prodottoParameter, prodotto);
        RequestDispatcher dispatcher = req.getRequestDispatcher(dettagliJSP);
        dispatcher.forward(req, resp);
    }

    private void aggiungiMangaAlCarrello(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter(idMangaParameter));
        if (mangaModel.verificaDisponibilita(id)) {
            CarrelloBean carrelloBean = getCarrelloBeanFromSession(req);
            carrelloBean.aggiungiProdotto(mangaModel.getById(id));
            req.getSession().setAttribute(carrelloParameter, carrelloBean);
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher(carrelloJSP);
        dispatcher.forward(req, resp);
    }

    private void aggiungiPopAlCarrello(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter(idPopParameter));
        if (popModel.verificaDisponibilita(id)) {
            CarrelloBean carrelloBean = getCarrelloBeanFromSession(req);
            carrelloBean.aggiungiProdotto(popModel.getById(id));
            req.getSession().setAttribute(carrelloParameter, carrelloBean);
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher(carrelloJSP);
        dispatcher.forward(req, resp);
    }

    private void aggiungiFigureAlCarrello(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter(idFigureParameter));
        if (figureModel.verificaDisponibilita(id)) {
            CarrelloBean carrelloBean = getCarrelloBeanFromSession(req);
            carrelloBean.aggiungiProdotto(figureModel.getById(id));
            req.getSession().setAttribute(carrelloParameter, carrelloBean);
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher(carrelloJSP);
        dispatcher.forward(req, resp);
    }

    private void rimuoviMangaDalCarrello(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter(idMangaParameter));
        CarrelloBean carrelloBean = getCarrelloBeanFromSession(req);
        carrelloBean.rimuoviProdotto(mangaModel.getById(id));
        req.getSession().setAttribute(carrelloParameter, carrelloBean);
        RequestDispatcher dispatcher = req.getRequestDispatcher(carrelloJSP);
        dispatcher.forward(req, resp);
    }

    private void rimuoviPopDalCarrello(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter(idPopParameter));
        CarrelloBean carrelloBean = getCarrelloBeanFromSession(req);
        carrelloBean.rimuoviProdotto(popModel.getById(id));
        req.getSession().setAttribute(carrelloParameter, carrelloBean);
        RequestDispatcher dispatcher = req.getRequestDispatcher(carrelloJSP);
        dispatcher.forward(req, resp);
    }

    private void rimuoviFigureDalCarrello(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter(idFigureParameter));
        CarrelloBean carrelloBean = getCarrelloBeanFromSession(req);
        carrelloBean.rimuoviProdotto(figureModel.getById(id));
        req.getSession().setAttribute(carrelloParameter, carrelloBean);
        RequestDispatcher dispatcher = req.getRequestDispatcher(carrelloJSP);
        dispatcher.forward(req, resp);
    }

    private void showHomePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        List<MangaBean> bestManga = mangaModel.miglioriManga();
        List<PopBean> bestPop = popModel.miglioriPop();
        List<FigureBean> bestFigure = figureModel.miglioriFigure();

        List<Object> bestProdotti = new ArrayList<>();
        bestProdotti.addAll(bestManga);
        bestProdotti.addAll(bestPop);
        bestProdotti.addAll(bestFigure);
        req.setAttribute("bestProdotti", bestProdotti);

        List<String> immaginiPop = new ArrayList<>();
        for (PopBean bean : bestPop) {
            immaginiPop.add(popModel.imgPerPop(bean).get(0));
        }
        req.setAttribute("immaginiPop", immaginiPop);

        List<String> immaginiFigure = new ArrayList<>();
        for (FigureBean bean : bestFigure) {
            immaginiFigure.add(figureModel.imgPerFigure(bean).get(0));
        }
        req.setAttribute("immaginiFigure", immaginiFigure);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/index.jsp");
        dispatcher.forward(req, resp);
    }
}
