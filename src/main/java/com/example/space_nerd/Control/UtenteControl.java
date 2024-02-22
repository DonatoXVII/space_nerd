package com.example.space_nerd.Control;

import com.example.space_nerd.Model.DatiSensibiliBean;
import com.example.space_nerd.Model.DatiSensibiliModel;
import com.example.space_nerd.Model.UtenteBean;
import com.example.space_nerd.Model.UtenteModel;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/UtenteControl")
public class UtenteControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static UtenteModel utenteModel = new UtenteModel();
    static DatiSensibiliModel datiModel = new DatiSensibiliModel();
    static Logger logger = Logger.getLogger(UtenteControl.class.getName());
    private static final String INDEX_PAGE = "./index.jsp";
    private static final String emailParameter = "email";

    public UtenteControl() {
        super();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        try {
            if(action != null) {
                if(action.equalsIgnoreCase("login")) {
                    login(request, response);
                }
                if(action.equalsIgnoreCase("registrati")){
                    registrati(request, response);
                }
                if(action.equalsIgnoreCase("logout")) {
                    logout(request, response);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String email = request.getParameter(emailParameter);
        String password = request.getParameter("password");
        HttpSession session = request.getSession(true);
        if (email == null || password == null) {
            response.sendRedirect(INDEX_PAGE);
        } else {
            UtenteBean utente = utenteModel.login(email, password);
            if (utente == null) {
                request.setAttribute("result", "Credenziali errate");
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
                dispatcher.forward(request, response);
            } else {
                session.setAttribute(emailParameter, utente.getEmail());
                session.setAttribute("tipo", utente.isTipo());
                response.sendRedirect(INDEX_PAGE);
            }
        }
    }

    private void registrati(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        Date data = Date.valueOf(request.getParameter("data"));
        String via = request.getParameter("via");
        int civico = Integer.parseInt(request.getParameter("civico"));
        String provincia = request.getParameter("provincia");
        String comune = request.getParameter("comune");
        String email = request.getParameter(emailParameter);
        String password = request.getParameter("password");
        UtenteBean utente = new UtenteBean(email, password, false);

        HttpSession session = request.getSession(true);

        if(utenteModel.emailPresente(email)) {
            request.setAttribute("result", "Email già presente");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/registrati.jsp");
            dispatcher.forward(request, response);
        } else {
            utenteModel.aggiungiUtente(email, password);
            DatiSensibiliBean dati = new DatiSensibiliBean(email, nome, cognome, data, via, civico, provincia, comune);
            datiModel.registraDati(dati);
            session.setAttribute(emailParameter, utente.getEmail());
            session.setAttribute("tipo", utente.isTipo());
            response.sendRedirect(INDEX_PAGE);
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        response.sendRedirect(INDEX_PAGE);
    }
}
