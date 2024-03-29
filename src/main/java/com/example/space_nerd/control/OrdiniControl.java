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
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


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
    static String fattureString = "fatture";
    private static final String ORDINI_PAGE = "/ordini.jsp";
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
                    case "generafattura" :
                        generaFattura(req, resp);
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
            RequestDispatcher dispatcher = request.getRequestDispatcher(ORDINI_PAGE);
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
            RequestDispatcher dispatcher = request.getRequestDispatcher(ORDINI_PAGE);
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

    private void generaFattura(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("IdOrdine"));
        String email = ordineModel.getById(id).getEmail();

        String nomeCognome = datiModel.getDatiUtentePerEmail(email).getNome() + " " + datiModel.getDatiUtentePerEmail(email).getCognome();
        Date data = ordineModel.getById(id).getData();

        List<Object> prodotti = ordineModel.getProdottiOrdine(id);
        int limit = 21;
        int numProd = 0;

        try {
            String servletPath = request.getServletContext().getRealPath("");
            String totalPath = servletPath + File.separator  + fattureString + File.separator  + "Fattura" + id + ".pdf";

            File file = new File(servletPath + fattureString + File.separator  + "spaceNerdFattura.pdf");
            PDDocument fattura = PDDocument.load(file);
            PDPage page = fattura.getDocumentCatalog().getPages().get(0);
            PDPageContentStream contentStream = new PDPageContentStream(fattura, page, PDPageContentStream.AppendMode.APPEND, true, true);
            PDType1Font font = PDType1Font.TIMES_ROMAN;

            //Coordinate

            //Nome e cognome cliente
            float coordinataX1 = 422;
            float coordinataY1 = 714.50f;

            //Numero fattura
            float coordinataX2 = 450;
            float coordinataY2 = 759.55f;

            //Data ordine
            float coordinataX3 = 450;
            float coordinataY3 = 745.3f;

            //Prodotto
            float coordinataProdottoX = 83;
            float coordinataProdottoY = 601.8f;

            contentStream.beginText();
            contentStream.setFont(font, 12);
            contentStream.newLineAtOffset(coordinataX2, coordinataY2);
            contentStream.showText(String.valueOf(id));
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 12);
            contentStream.newLineAtOffset(coordinataX3, coordinataY3);
            contentStream.showText(String.valueOf(data));
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 12);
            contentStream.newLineAtOffset(coordinataX1, coordinataY1);
            contentStream.showText(nomeCognome);
            contentStream.endText();

            int quantita = 0;
            float prezzo = 0;
            float prezzoTotale = 0;
            String descrizione = "";

            float prezzoSpesa = 0;

            for(Object prod : prodotti) {
                numProd++;
                if(numProd > limit ) {
                    file = new File(servletPath + fattureString +  File.separator  + "spaceNerd2.pdf");
                    page = PDDocument.load(file).getDocumentCatalog().getPages().get(0);

                    fattura.addPage(page);

                    contentStream.close();

                    coordinataProdottoY = 731.7f;
                    contentStream = new PDPageContentStream(fattura, page, PDPageContentStream.AppendMode.APPEND, true, true);
                    numProd = 1;
                    limit = 26;
                }

                //Definisci il prodotto da scrivere
                quantita = quantitaForTipo(prod);
                prezzo = prezzoForTipo(prod, id);
                prezzoTotale = prezzoTotaleForTipo(prod, id);
                descrizione = descrizioneForTipo(prod);
                prezzoSpesa += prezzoTotale;

                contentStream.beginText();
                contentStream.setFont(font, 12);
                contentStream.newLineAtOffset(coordinataProdottoX, coordinataProdottoY);
                contentStream.showText(String.valueOf(quantita));
                contentStream.endText();
                coordinataProdottoX = 126.5f;

                contentStream.beginText();
                contentStream.setFont(font, 12);
                contentStream.newLineAtOffset(coordinataProdottoX, coordinataProdottoY);
                contentStream.showText(descrizione);
                contentStream.endText();
                coordinataProdottoX = 360;

                contentStream.beginText();
                contentStream.setFont(font, 12);
                contentStream.newLineAtOffset(coordinataProdottoX, coordinataProdottoY);
                contentStream.showText(String.valueOf(prezzo + " €"));
                contentStream.endText();
                coordinataProdottoX = 455.5f;

                contentStream.beginText();
                contentStream.setFont(font, 12);
                contentStream.newLineAtOffset(coordinataProdottoX, coordinataProdottoY);
                contentStream.showText(String.valueOf(prezzoTotale + " €"));
                contentStream.endText();

                coordinataProdottoX = 83;
                coordinataProdottoY = coordinataProdottoY - 24.9f;

            }

            coordinataProdottoX = 360;
            contentStream.beginText();
            contentStream.setFont(font, 12);
            contentStream.setNonStrokingColor(new Color(255, 0, 0));
            contentStream.newLineAtOffset(coordinataProdottoX, coordinataProdottoY);
            contentStream.showText(String.valueOf("TOTALE"));
            contentStream.endText();

            coordinataProdottoX = 455.5f;

            Locale.setDefault(Locale.US);
            String prezzoArrotondato;
            Locale.setDefault(Locale.ITALY);

            prezzoSpesa = prezzoSpesa + 5.0f; //spedizione

            prezzoArrotondato = String.format("%.2f", prezzoSpesa);

            contentStream.beginText();
            contentStream.setFont(font, 12);
            contentStream.setNonStrokingColor(new Color(255, 0, 0));
            contentStream.newLineAtOffset(coordinataProdottoX, coordinataProdottoY);
            contentStream.showText(String.valueOf(prezzoArrotondato + " €"));
            contentStream.endText();

            contentStream.close();
            fattura.save(totalPath);
            fattura.close();

            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    desktop.open(new File(totalPath));
                }
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher(ORDINI_PAGE);
            dispatcher.forward(request, response);

        } catch (IOException | ServletException e) {
            request.setAttribute(ERROR_PARAMETER, ERROR_MESSAGE + e);
            RequestDispatcher errorDispatcher = getServletContext().getRequestDispatcher(ERROR_PAGE);
            try {
                errorDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                log(ERROR_MESSAGE_TWO, ex);
            }
        }

    }

    private int quantitaForTipo(Object prod) {
        int quantita = 0;
        if (prod instanceof MangaBean) {
            quantita = ((MangaBean) prod).getQuantitaCarrello();
        }
        if(prod instanceof PopBean) {
            quantita = ((PopBean) prod).getQuantitaCarrello();
        }
        if(prod instanceof FigureBean) {
            quantita = ((FigureBean) prod).getQuantitaCarrello();
        }
        return quantita;
    }

    private float prezzoForTipo(Object prod, int id) {
        float prezzo = 0;
        if (prod instanceof MangaBean) {
            prezzo = mangaModel.getPrezzoUnitarioInThatOrdine(((MangaBean) prod).getIdManga(), id);
        }
        if(prod instanceof PopBean) {
            prezzo = popModel.getPrezzoUnitarioInThatOrdine(((PopBean) prod).getIdPop(), id);
        }
        if(prod instanceof FigureBean) {
            prezzo = figureModel.getPrezzoUnitarioInThatOrdine(((FigureBean) prod).getIdFigure(), id);
        }
        return prezzo;
    }

    private float prezzoTotaleForTipo(Object prod, int id) {
        float prezzo;
        float prezzoTotale = 0;
        if (prod instanceof MangaBean) {
            prezzo = mangaModel.getPrezzoUnitarioInThatOrdine(((MangaBean) prod).getIdManga(), id);
            prezzoTotale = prezzo * ((MangaBean) prod).getQuantitaCarrello();
        }
        if(prod instanceof PopBean) {
            prezzo = popModel.getPrezzoUnitarioInThatOrdine(((PopBean) prod).getIdPop(), id);
            prezzoTotale = prezzo * ((PopBean) prod).getQuantitaCarrello();
        }
        if(prod instanceof FigureBean) {
            prezzo = figureModel.getPrezzoUnitarioInThatOrdine(((FigureBean) prod).getIdFigure(), id);
            prezzoTotale = prezzo * ((FigureBean) prod).getQuantitaCarrello();
        }
        return prezzoTotale;
    }

    private String descrizioneForTipo(Object prod) {
        String descrizione = "";
        if (prod instanceof MangaBean) {
            descrizione = ((MangaBean) prod).getDescrizione();
        }
        if(prod instanceof PopBean) {
            descrizione = ((PopBean) prod).getDescrizione();
            
        }
        if(prod instanceof FigureBean) {
            descrizione = ((FigureBean) prod).getDescrizione();
        }
        return descrizione;
    }
}

