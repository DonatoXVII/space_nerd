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


@WebServlet("/OrdiniControl")
public class OrdiniControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static OrdineModel ordineModel = new OrdineModel();
    static DatiSensibiliModel datiModel = new DatiSensibiliModel();
    static MangaModel mangaModel = new MangaModel();
    static PopModel popModel = new PopModel();
    static FigureModel figureModel = new FigureModel();
    static IndirizzoModel indirizzoModel = new IndirizzoModel();
    static PagamentoModel pagamentoModel = new PagamentoModel();
    static String emailParameter = "email";
    private static final String ERROR_PAGE = "/errore.jsp";
    private static final String ERROR_PARAMETER = "error";
    private static final String ERROR_MESSAGE = "Si è verificato un errore: ";
    private static final String ERROR_MESSAGE_TWO = "Errore durante il reindirizzamento alla pagina di errore";

    public OrdiniControl() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, NullPointerException {
        String action = req.getParameter("action");
        try {
            if(action != null) {
                switch (action.toLowerCase()) {
                    case "visualizzaordini" :
                        visualizzaOrdini(req, resp);
                        break;
                    case "visualizzaordinifiltrati" :
                        visualizzaOrdiniFiltrati(req, resp);
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
                        RequestDispatcher errorDispatcher = getServletContext().getRequestDispatcher(ERROR_PAGE);
                        errorDispatcher.forward(req, resp);
                        break;
                }
            }
        } catch (ServletException | IOException e) {
            req.setAttribute(ERROR_PARAMETER, ERROR_MESSAGE + e);
            RequestDispatcher errorDispatcher = getServletContext().getRequestDispatcher(ERROR_PAGE);
            try {
                errorDispatcher.forward(req, resp);
            } catch (ServletException | IOException ex) {
                log(ERROR_MESSAGE_TWO, ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            doGet(req, resp);
        } catch (ServletException | IOException e) {
            req.setAttribute(ERROR_PARAMETER, ERROR_MESSAGE + e);
            RequestDispatcher errorDispatcher = getServletContext().getRequestDispatcher(ERROR_PAGE);
            try {
                errorDispatcher.forward(req, resp);
            } catch (ServletException | IOException ex) {
                log(ERROR_MESSAGE_TWO, ex);
            }
        }
    }

    private void visualizzaOrdini(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<OrdineBean> ordini;
            HttpSession session = request.getSession();
            ordini = ordineModel.getOrdiniPerUtente((String) session.getAttribute(emailParameter));
            request.setAttribute(emailParameter, session.getAttribute(emailParameter));
            request.setAttribute("ordini", ordini);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/ordini.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            request.setAttribute(ERROR_PARAMETER, ERROR_MESSAGE + e);
            RequestDispatcher errorDispatcher = getServletContext().getRequestDispatcher(ERROR_PAGE);
            try {
                errorDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                log(ERROR_MESSAGE_TWO, ex);
            }
        }
    }

    private void visualizzaOrdiniFiltrati(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String email = request.getParameter(emailParameter);
            List<OrdineBean> ordini = ordineModel.getOrdiniPerUtente(email);
            String dataInizio = request.getParameter("dataInizio");
            String dataFine = request.getParameter("dataFine");
            String prezzoMinimo = request.getParameter("prezzoMinimo");
            String prezzoMassimo = request.getParameter("prezzoMassimo");

            if(!dataInizio.isEmpty() && !dataFine.isEmpty()) {
                Date sqlDataInizio = Date.valueOf(dataInizio);
                Date sqlDataFine = Date.valueOf(dataFine);
                ordini = ordineModel.getOrdiniPerUtentePerData(email, sqlDataInizio, sqlDataFine);
            }

            if(!prezzoMinimo.isEmpty() && !prezzoMassimo.isEmpty()) {
                float sqlPrezzoMinimo = Float.parseFloat(prezzoMinimo);
                float sqlPrezzoMassimo = Float.parseFloat(prezzoMassimo);
                ordini = ordineModel.getOrdiniPerUtentePerPrezzo(email, sqlPrezzoMinimo, sqlPrezzoMassimo);
            }

            if(!dataInizio.isEmpty() && !dataFine.isEmpty() && !prezzoMinimo.isEmpty() && !prezzoMassimo.isEmpty()) {
                Date sqlDataInizio = Date.valueOf(dataInizio);
                Date sqlDataFine = Date.valueOf(dataFine);
                float sqlPrezzoMinimo = Float.parseFloat(prezzoMinimo);
                float sqlPrezzoMassimo = Float.parseFloat(prezzoMassimo);
                ordini = ordineModel.getOrdiniPerUtentePerPrezzoEPerData(email, sqlPrezzoMinimo, sqlPrezzoMassimo, sqlDataInizio, sqlDataFine);
            }

            request.setAttribute(emailParameter, email);
            request.setAttribute("ordini", ordini);
            request.setAttribute("nome", datiModel.getDatiUtentePerEmail(email).getNome());
            request.setAttribute("cognome", datiModel.getDatiUtentePerEmail(email).getCognome());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/ordini.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            request.setAttribute(ERROR_PARAMETER, ERROR_MESSAGE + e);
            RequestDispatcher errorDispatcher = getServletContext().getRequestDispatcher(ERROR_PAGE);
            try {
                errorDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                log(ERROR_MESSAGE_TWO, ex);
            }
        }
    }

    private void visualizzaDettagliOrdini(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
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
        } catch (ServletException | IOException e) {
            request.setAttribute(ERROR_PARAMETER, ERROR_MESSAGE + e);
            RequestDispatcher errorDispatcher = getServletContext().getRequestDispatcher(ERROR_PAGE);
            try {
                errorDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                log(ERROR_MESSAGE_TWO, ex);
            }
        }

    }

    private void visulizzaIndirizziEMetodi(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            String email = (String) session.getAttribute(emailParameter);

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
        } catch (ServletException | IOException e) {
            request.setAttribute(ERROR_PARAMETER, ERROR_MESSAGE + e);
            RequestDispatcher errorDispatcher = getServletContext().getRequestDispatcher(ERROR_PAGE);
            try {
                errorDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                log(ERROR_MESSAGE_TWO, ex);
            }
        }
    }

    private void checkout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            String email = (String) session.getAttribute(emailParameter);
            CarrelloBean carrelloBean = (CarrelloBean) session.getAttribute("carrello");

            int last = ordineModel.getLastIdOrdine() + 1;
            LocalDate oggi = LocalDate.now();
            Date dataSQL = Date.valueOf(oggi);
            ordineModel.regitraNuovoOrdine(carrelloBean.getPrezzoTotale()+5, dataSQL, "Fattura." +last+".pdf", email);

            int nuovoLast = ordineModel.getLastIdOrdine();
            List<Object> prodotti = carrelloBean.getListaCarrello();
            for(Object prodotto : prodotti) {
                if(prodotto instanceof MangaBean) {
                    ordineModel.aggiornaComprendeManga(nuovoLast, ((MangaBean) prodotto).getIdManga(), ((MangaBean) prodotto).getQuantitaCarrello(), ((MangaBean) prodotto).getPrezzo());
                    mangaModel.decrementaDisponibilita((MangaBean) prodotto, ((MangaBean) prodotto).getQuantitaCarrello());
                } else if(prodotto instanceof PopBean) {
                    ordineModel.aggiornaComprendePop(nuovoLast, ((PopBean) prodotto).getIdPop(), ((PopBean) prodotto).getQuantitaCarrello(), ((PopBean) prodotto).getPrezzo());
                    popModel.decrementaDisponibilita((PopBean) prodotto, ((PopBean) prodotto).getQuantitaCarrello());
                } else if(prodotto instanceof FigureBean) {
                    ordineModel.aggiornaComprendeFigure(nuovoLast, ((FigureBean) prodotto).getIdFigure(), ((FigureBean) prodotto).getQuantitaCarrello(), ((FigureBean) prodotto).getPrezzo());
                    figureModel.decrementaDisponibilita((FigureBean) prodotto, ((FigureBean) prodotto).getQuantitaCarrello());
                }
            }

            carrelloBean.svuotaCarrello();

            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            request.setAttribute(ERROR_PARAMETER, ERROR_MESSAGE + e);
            RequestDispatcher errorDispatcher = getServletContext().getRequestDispatcher(ERROR_PAGE);
            try {
                errorDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                log(ERROR_MESSAGE_TWO, ex);
            }
        }
    }
}

