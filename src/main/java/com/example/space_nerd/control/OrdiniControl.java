package com.example.space_nerd.control;

import com.example.space_nerd.model.*;
import com.example.space_nerd.utility.CarrelloBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/OrdiniControl")
public class OrdiniControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static OrdineModel ordineModel = new OrdineModel();
    static MangaModel mangaModel = new MangaModel();
    static PopModel popModel = new PopModel();
    static FigureModel figureModel = new FigureModel();
    static IndirizzoModel indirizzoModel = new IndirizzoModel();
    static PagamentoModel pagamentoModel = new PagamentoModel();
    static Logger logger = Logger.getLogger(OrdiniControl.class.getName());

    public OrdiniControl() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if(action != null) {
                switch (action.toLowerCase()) {
                    case "visualizzaordini" :
                        visualizzaOrdini(req, resp);
                        break;
                    case "visualizzadettagliordine" :
                        visualizzaDettagliOrdini(req, resp);
                        break;
                    case "visualizzaindirizziemetodi" :
                        visulizzaIndirizziEMetodi(req, resp);
                        break;
                    case "checkout" :
                        checkout(req, resp);
                        break;
                    default:
                        RequestDispatcher errorDispatcher = getServletContext().getRequestDispatcher("/errore.jsp");
                        errorDispatcher.forward(req, resp);
                        break;
                }
            }
        } catch (ServletException | IOException e) {
            logger.info("Si è verificata un'eccezione:" + e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            doGet(req, resp);
        } catch (ServletException | IOException e) {
            logger.info("Si è verificata un'eccezione:" + e);
        }
    }

    private void visualizzaOrdini(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<OrdineBean> ordini;
        HttpSession session = request.getSession();
        ordini = ordineModel.getOrdiniPerUtente((String) session.getAttribute("email"));
        request.setAttribute("ordini", ordini);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/ordini.jsp");
        dispatcher.forward(request, response);
    }

    private void visualizzaDettagliOrdini(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("IdOrdine"));
        List<Object> prodottiOrdine = new ArrayList<>(ordineModel.getProdottiOrdine(id));
        for(Object prod : prodottiOrdine) {
            if(prod instanceof PopBean) {
                List<String> immaginiPop = popModel.getAllImgPop((PopBean) prod);
                for(String immagine : immaginiPop) {
                    ((PopBean) prod).aggiungiImmagine(immagine);
                }
            } else if(prod instanceof FigureBean) {
                List<String> immaginiFigure = figureModel.getAllImgFigure((FigureBean) prod);
                for(String immagine : immaginiFigure) {
                    ((FigureBean) prod).aggiungiImmagine(immagine);
                }
            }
        }
        request.setAttribute("prodottiOrdine", prodottiOrdine);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/dettagliOrdine.jsp");
        dispatcher.forward(request, response);

    }

    private void visulizzaIndirizziEMetodi(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        List<Integer> indirizziPerEmail = indirizzoModel.getIndiririzziUtente(email);
        List<IndirizzoBean> indirizziUtilizzati = new ArrayList<>();
        for (int i : indirizziPerEmail) {
            indirizziUtilizzati.add(indirizzoModel.getIndirizzo(i));
        }

        List<Integer> metodiPerEmail = pagamentoModel.getMetodiUtente(email);
        List<PagamentoBean> metodiUtilizzati = new ArrayList<>();
        for (Integer i : metodiPerEmail) {
            metodiUtilizzati.add(pagamentoModel.getMetodo(i));
        }

        request.setAttribute("indirizzi", indirizziUtilizzati);
        request.setAttribute("pagamenti", metodiUtilizzati);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/elaborazioneOrdine.jsp");
        dispatcher.forward(request, response);
    }

    private void checkout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        CarrelloBean carrelloBean = (CarrelloBean) session.getAttribute("carrello");

        int last = ordineModel.getLastIdOrdine() + 1;
        LocalDate oggi = LocalDate.now();
        Date dataSQL = Date.valueOf(oggi);
        ordineModel.regitraNuovoOrdine(carrelloBean.getPrezzoTotale(), dataSQL, "Fattura." +last+".pdf", email);

        int nuovoLast = ordineModel.getLastIdOrdine();
        List<Object> prodotti = carrelloBean.getListaCarrello();
        for(Object prodotto : prodotti) {
            if(prodotto instanceof MangaBean) {
                ordineModel.aggiornaComprendeManga(nuovoLast, ((MangaBean) prodotto).getIdManga(), ((MangaBean) prodotto).getQuantitaCarrello());
            } else if(prodotto instanceof PopBean) {
                ordineModel.aggiornaComprendePop(nuovoLast, ((PopBean) prodotto).getIdPop(), ((PopBean) prodotto).getQuantitaCarrello());
            } else if(prodotto instanceof FigureBean) {
                ordineModel.aggiornaComprendeFigure(nuovoLast, ((FigureBean) prodotto).getIdFigure(), ((FigureBean) prodotto).getQuantitaCarrello());
            }
        }

        carrelloBean.svuotaCarrello();

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }
}

