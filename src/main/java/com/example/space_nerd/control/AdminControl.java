package com.example.space_nerd.control;

import com.example.space_nerd.model.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/AdminControl")
public class AdminControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static DatiSensibiliModel datiModel = new DatiSensibiliModel();
    static UtenteModel utenteModel = new UtenteModel();
    static OrdineModel ordineModel = new OrdineModel();
    static MangaModel mangaModel = new MangaModel();
    static PopModel popModel = new PopModel();
    static FigureModel figureModel = new FigureModel();
    static Logger logger = Logger.getLogger(AdminControl.class.getName());
    public AdminControl() {super();}

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        try {
            if (action != null) {
                switch (action.toLowerCase()) {
                    case "visualizzautenti" :
                        visualizzaUtenti(request, response);
                        break;
                    case "visualizzaordini" :
                        visualizzaOrdini(request, response);
                        break;
                    case "eliminaprodotto" :
                        eliminaProdotto(request, response);
                        break;
                    case "aggiungiprodotto" :
                        aggiungiProdotto(request, response);
                        break;
                    case "rimuoviprodotto" :
                        rimuoviProdotto(request, response);
                        break;
                    case "verificatipo" :
                        verificaTipo(request, response);
                        break;
                    case "aggiunginuovoprodotto" :
                        aggiungiNuovoProdotto(request, response);
                        break;
                    default:
                        RequestDispatcher errorDispatcher = getServletContext().getRequestDispatcher("/errore.jsp");
                        errorDispatcher.forward(request, response);
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

    private void visualizzaUtenti(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<String> utentiPerEmail = new ArrayList<>(utenteModel.getAllUtenti());
        List<DatiSensibiliBean> utenti = new ArrayList<>();

        for(String email : utentiPerEmail) {
            utenti.add(datiModel.getDatiUtentePerEmail(email));
        }

        request.setAttribute("utenti", utenti);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin.jsp");
        dispatcher.forward(request, response);
    }

    private void visualizzaOrdini(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("Email");
        List<OrdineBean> ordini = new ArrayList<>(ordineModel.getOrdiniPerUtente(email));
        request.removeAttribute("ordini");
        request.setAttribute("ordini", ordini);
        request.setAttribute("emailOrdine", email);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ordini.jsp");
        dispatcher.forward(request, response);
    }

    private void eliminaProdotto(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String tipo = request.getParameter("tipo");
        if(tipo.equalsIgnoreCase("manga")) {
            int id = Integer.parseInt(request.getParameter("IdManga"));
            mangaModel.eliminaProdotto(id);
        }
        if(tipo.equalsIgnoreCase("pop")){
            int id = Integer.parseInt(request.getParameter("IdPop"));
            popModel.eliminaProdotto(id);
        }
        if(tipo.equalsIgnoreCase("figure")){
            int id = Integer.parseInt(request.getParameter("IdFigure"));
            figureModel.eliminaProdotto(id);
        }
        response.sendRedirect("./catalogo.jsp");
    }

    private void aggiungiProdotto(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String tipo = request.getParameter("tipo");
        int tot = Integer.parseInt(request.getParameter("tot"));
        if(tipo.equalsIgnoreCase("manga")) {
            int id = Integer.parseInt(request.getParameter("IdManga"));
            mangaModel.aggiungiNumArticoli(id, tot);
        }
        if(tipo.equalsIgnoreCase("pop")){
            int id = Integer.parseInt(request.getParameter("IdPop"));
            popModel.aggiungiNumArticoli(id, tot);
        }
        if(tipo.equalsIgnoreCase("figure")) {
            int id = Integer.parseInt(request.getParameter("IdFigure"));
            figureModel.aggiungiNumArticoli(id, tot);
        }
        response.sendRedirect("./catalogo.jsp");
    }

    private void rimuoviProdotto(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String tipo = request.getParameter("tipo");
        int tot = Integer.parseInt(request.getParameter("tot"));
        if(tipo.equalsIgnoreCase("manga")) {
            int id = Integer.parseInt(request.getParameter("IdManga"));
            mangaModel.rimuoviNumeroArticoli(id, tot);
        }
        if(tipo.equalsIgnoreCase("pop")){
            int id = Integer.parseInt(request.getParameter("IdPop"));
            popModel.rimuoviNumeroArticoli(id, tot);
        }
        if(tipo.equalsIgnoreCase("figure")){
            int id = Integer.parseInt(request.getParameter("IdFigure"));
            figureModel.rimuoviNumeroArticoli(id, tot);
        }
        response.sendRedirect("./catalogo.jsp");
    }

    private void verificaTipo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String tipo = request.getParameter("prod");
        if(tipo.equalsIgnoreCase("manga")){
            request.setAttribute("tipo", "manga");
        }
        if(tipo.equalsIgnoreCase("pop")) {
            request.setAttribute("tipo", "pop");
        }
        if(tipo.equalsIgnoreCase("figure")) {
            request.setAttribute("tipo", "figure");
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/nuovoProdotto.jsp");
        dispatcher.forward(request, response);
    }

    private void aggiungiNuovoProdotto(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String descrizione = request.getParameter("descrizione");
        int nArticoli = Integer.parseInt(request.getParameter("numeroArticoli"));
        float prezzo = Float.parseFloat(request.getParameter("prezzo"));

        String tipo = request.getParameter("tipo");

        if(tipo.equalsIgnoreCase("manga")) {
            String casa = request.getParameter("casa");
            int nPagine = Integer.parseInt(request.getParameter("pagine"));
            String lingua = request.getParameter("lingua");

            MangaBean manga = new MangaBean();
            manga.setDescrizione(descrizione);
            manga.setNumArticoli(nArticoli);
            manga.setPrezzo(prezzo);
            manga.setCasaEditrice(casa);
            manga.setNumPagine(nPagine);
            manga.setLingua(lingua);
            mangaModel.aggiungiProdotto(manga);
        }

        if(tipo.equalsIgnoreCase("pop")) {
            String universo = request.getParameter("universo");
            int nSerie = Integer.parseInt(request.getParameter("numeroSerie"));

            PopBean pop = new PopBean();
            pop.setDescrizione(descrizione);
            pop.setNumArticoli(nArticoli);
            pop.setPrezzo(prezzo);
            pop.setSerie(universo);
            pop.setNumSerie(nSerie);
            popModel.aggiungiProdotto(pop);
        }

        if(tipo.equalsIgnoreCase("figure")) {
            String materiale = request.getParameter("materiale");
            int altezza = Integer.parseInt(request.getParameter("altezza"));
            String personaggio = request.getParameter("personaggio");

            FigureBean figure = new FigureBean();
            figure.setDescrizione(descrizione);
            figure.setNumArticoli(nArticoli);
            figure.setPrezzo(prezzo);
            figure.setMateriale(materiale);
            figure.setAltezza(altezza);
            figure.setPersonaggio(personaggio);
            figureModel.aggiungiProdotto(figure);
        }

        response.sendRedirect("./catalogo.jsp");
    }
}
