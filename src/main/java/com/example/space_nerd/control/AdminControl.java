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
    static PopModel popModel = new PopModel();
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
        if(tipo.equalsIgnoreCase("pop")){
            int id = Integer.parseInt(request.getParameter("IdPop"));
            popModel.eliminaProdotto(id);
        }
        response.sendRedirect("./catalogo.jsp");
    }

    private void aggiungiProdotto(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String tipo = request.getParameter("tipo");
        int tot = Integer.parseInt(request.getParameter("tot"));
        if(tipo.equalsIgnoreCase("pop")){
            int id = Integer.parseInt(request.getParameter("IdPop"));
            popModel.aggiungiNumArticoli(id, tot);
        }
        response.sendRedirect("./catalogo.jsp");
    }

    private void rimuoviProdotto(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String tipo = request.getParameter("tipo");
        int tot = Integer.parseInt(request.getParameter("tot"));
        if(tipo.equalsIgnoreCase("pop")){
            int id = Integer.parseInt(request.getParameter("IdPop"));
            popModel.rimuoviNumeroArticoli(id, tot);
        }
        response.sendRedirect("./catalogo.jsp");
    }
}
