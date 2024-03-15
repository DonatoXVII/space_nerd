package com.example.space_nerd.control;

import com.example.space_nerd.model.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet("/AdminControl")
@MultipartConfig
public class AdminControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static DatiSensibiliModel datiModel = new DatiSensibiliModel();
    static UtenteModel utenteModel = new UtenteModel();
    static OrdineModel ordineModel = new OrdineModel();
    static MangaModel mangaModel = new MangaModel();
    static PopModel popModel = new PopModel();
    static FigureModel figureModel = new FigureModel();
    public AdminControl() {super();}

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, NumberFormatException, NullPointerException {
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
                    case "restock" :
                        restock(request, response);
                        break;
                    default:
                        RequestDispatcher errorDispatcher = getServletContext().getRequestDispatcher("/errore.jsp");
                        errorDispatcher.forward(request, response);
                        break;
                }
            }
        } catch (ServletException | IOException e) {
            request.setAttribute("error", "Si è verificato un errore: " + e);
            RequestDispatcher errorDispatcher = getServletContext().getRequestDispatcher("/errore.jsp");
            try {
                errorDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                log("Errore durante il reindirizzamento alla pagina di errore", ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            doGet(req, resp);
        } catch (ServletException | IOException e) {
            req.setAttribute("error", "Si è verificato un errore: " + e);
            RequestDispatcher errorDispatcher = getServletContext().getRequestDispatcher("/errore.jsp");
            try {
                errorDispatcher.forward(req, resp);
            } catch (ServletException | IOException ex) {
                log("Errore durante il reindirizzamento alla pagina di errore", ex);
            }
        }
    }

    private void visualizzaUtenti(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            List<String> utentiPerEmail = new ArrayList<>(utenteModel.getAllUtenti());
            List<DatiSensibiliBean> utenti = new ArrayList<>();

            for(String email : utentiPerEmail) {
                utenti.add(datiModel.getDatiUtentePerEmail(email));
            }

            request.setAttribute("utenti", utenti);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin.jsp");
            dispatcher.forward(request, response);
        } catch (IOException | ServletException e) {
            request.setAttribute("error", "Si è verificato un errore: " + e);
            RequestDispatcher errorDispatcher = getServletContext().getRequestDispatcher("/errore.jsp");
            try {
                errorDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                log("Errore durante il reindirizzamento alla pagina di errore", ex);
            }
        }
    }

    private void visualizzaOrdini(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String email = request.getParameter("Email");
            List<OrdineBean> ordini = new ArrayList<>(ordineModel.getOrdiniPerUtente(email));
            request.removeAttribute("ordini");
            request.setAttribute("ordini", ordini);
            request.setAttribute("emailOrdine", email);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ordini.jsp");
            dispatcher.forward(request, response);
        } catch (IOException | ServletException e) {
            request.setAttribute("error", "Si è verificato un errore: " + e);
            RequestDispatcher errorDispatcher = getServletContext().getRequestDispatcher("/errore.jsp");
            try {
                errorDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                log("Errore durante il reindirizzamento alla pagina di errore", ex);
            }
        }

    }

    private void eliminaProdotto(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
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
        } catch (IOException e) {
            request.setAttribute("error", "Si è verificato un errore: " + e);
            RequestDispatcher errorDispatcher = getServletContext().getRequestDispatcher("/errore.jsp");
            try {
                errorDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                log("Errore durante il reindirizzamento alla pagina di errore", ex);
            }
        }

    }

    private void aggiungiProdotto(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
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
        } catch (IOException e) {
            request.setAttribute("error", "Si è verificato un errore: " + e);
            RequestDispatcher errorDispatcher = getServletContext().getRequestDispatcher("/errore.jsp");
            try {
                errorDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                log("Errore durante il reindirizzamento alla pagina di errore", ex);
            }
        }
    }

    private void rimuoviProdotto(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
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
        } catch (IOException e) {
            request.setAttribute("error", "Si è verificato un errore: " + e);
            RequestDispatcher errorDispatcher = getServletContext().getRequestDispatcher("/errore.jsp");
            try {
                errorDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                log("Errore durante il reindirizzamento alla pagina di errore", ex);
            }
        }
    }

    private void verificaTipo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
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
        } catch (IOException | ServletException e) {
            request.setAttribute("error", "Si è verificato un errore: " + e);
            RequestDispatcher errorDispatcher = getServletContext().getRequestDispatcher("/errore.jsp");
            try {
                errorDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                log("Errore durante il reindirizzamento alla pagina di errore", ex);
            }
        }
    }

    private void aggiungiNuovoProdotto(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, NullPointerException {
        try {
            String descrizione = request.getParameter("descrizione");
            int nArticoli = Integer.parseInt(request.getParameter("numeroArticoli"));
            float prezzo = Float.parseFloat(request.getParameter("prezzo"));

            String tipo = request.getParameter("tipo");

            if(tipo.equalsIgnoreCase("manga")) {
                String casa = request.getParameter("casa");
                int nPagine = Integer.parseInt(request.getParameter("pagine"));
                String lingua = request.getParameter("lingua");

                Part imgFile = request.getPart("immagine");
                String immagine = String.valueOf(UUID.randomUUID());
                String directory = "img/imgManga/";
                String path = request.getServletContext().getRealPath("/") +directory;
                String path2 = path + immagine;
                try(FileOutputStream fos = new FileOutputStream(path2);
                    InputStream is = imgFile.getInputStream()){
                    byte[] data = new byte[is.available()];
                    if(is.read(data) > 0)
                    {
                        fos.write(data);
                    }
                }catch(IOException e){
                    response.sendRedirect("./error.jsp");
                }

                MangaBean manga = new MangaBean();
                manga.setDescrizione(descrizione);
                manga.setNumArticoli(nArticoli);
                manga.setPrezzo(prezzo);
                manga.setCasaEditrice(casa);
                manga.setNumPagine(nPagine);
                manga.setLingua(lingua);
                manga.setImg(immagine);
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
                int id = popModel.getLastPop();

                int countImmagini;
                String count = request.getParameter("imageCount");
                if(count == null) {
                    countImmagini = 1;
                } else {
                    countImmagini = Integer.parseInt(count);
                }

                for(int i = 1 ; i < countImmagini+1 ; i++) {
                    Part imgFile = request.getPart("image" + i);
                    String immagine = String.valueOf(UUID.randomUUID());
                    String directory = "img/imgPop/";
                    String path = request.getServletContext().getRealPath("/") + directory;
                    String path2 = path + immagine;
                    try(FileOutputStream fos = new FileOutputStream(path2);
                        InputStream is = imgFile.getInputStream()){
                        byte[] data = new byte[is.available()];
                        if(is.read(data) > 0)
                        {
                            fos.write(data);
                        }
                    }catch(IOException e){
                        response.sendRedirect("./error.jsp");
                    }
                    popModel.aggiungiImmaginiPop(id, immagine);
                }
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

                int id = figureModel.getLastFigure();

                int countImmagini;
                String count = request.getParameter("imageCount");
                if(count == null) {
                    countImmagini = 1;
                } else {
                    countImmagini = Integer.parseInt(count);
                }

                for(int i = 1 ; i < countImmagini+1 ; i++) {
                    Part imgFile = request.getPart("image" + i);
                    String immagine = String.valueOf(UUID.randomUUID());
                    String directory = "img/imgFigure/";
                    String path = request.getServletContext().getRealPath("/") + directory;
                    String path2 = path + immagine;
                    try(FileOutputStream fos = new FileOutputStream(path2);
                        InputStream is = imgFile.getInputStream()){
                        byte[] data = new byte[is.available()];
                        if(is.read(data) > 0)
                        {
                            fos.write(data);
                        }
                    }catch(IOException e){
                        response.sendRedirect("./error.jsp");
                    }
                    figureModel.aggiungiImmaginiFigure(id, immagine);
                }
            }

            response.sendRedirect("./catalogo.jsp");
        } catch (IOException | ServletException | NullPointerException | NumberFormatException e) {
            request.setAttribute("error", "Si è verificato un errore: " + e);
            RequestDispatcher errorDispatcher = getServletContext().getRequestDispatcher("/errore.jsp");
            try {
                errorDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                log("Errore durante il reindirizzamento alla pagina di errore", ex);
            }
        }
    }

    private void restock (HttpServletRequest request, HttpServletResponse response) throws IOException{
        try {
            String tipo = request.getParameter("tipo");
            int id = Integer.parseInt(request.getParameter("id"));
            if(tipo.equalsIgnoreCase("manga")) {
                mangaModel.restock(id);
            } else if(tipo.equalsIgnoreCase("pop")){
                popModel.restock(id);
            } else if(tipo.equalsIgnoreCase("figure")){
                figureModel.restock(id);
            }
            response.sendRedirect("./catalogo.jsp");
        } catch (IOException e) {
            request.setAttribute("error", "Si è verificato un errore: " + e);
            RequestDispatcher errorDispatcher = getServletContext().getRequestDispatcher("/errore.jsp");
            try {
                errorDispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                log("Errore durante il reindirizzamento alla pagina di errore", ex);
            }
        }
    }
}
