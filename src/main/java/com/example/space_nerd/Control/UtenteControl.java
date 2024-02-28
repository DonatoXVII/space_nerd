package com.example.space_nerd.Control;

import com.example.space_nerd.Model.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/UtenteControl")
public class UtenteControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static UtenteModel utenteModel = new UtenteModel();
    static DatiSensibiliModel datiModel = new DatiSensibiliModel();
    static IndirizzoModel indirizzoModel = new IndirizzoModel();
    static PagamentoModel pagamentoModel = new PagamentoModel();
    static Logger logger = Logger.getLogger(UtenteControl.class.getName());
    private static final String INDEX_PAGE = "./index.jsp";
    private static final String emailParameter = "email";
    private static final String pwdParameter = "password";
    private static final String cognomeParameter = "cognome";
    private static final String civicoParameter = "civico";
    private static final String provinciaParameter = "provincia";
    private static final String comuneParameter = "comune";

    public UtenteControl() {
        super();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        try {
            if (action != null) {
                switch (action.toLowerCase()) {
                    case "login":
                        login(request, response);
                        break;
                    case "registrati":
                        registrati(request, response);
                        break;
                    case "modificaprofilo":
                        modificaProfilo(request, response);
                        break;
                    case "visualizzaindirizzi":
                        visualizzaIndirizzi(request, response);
                        break;
                    case "rimuoviindirizzo":
                        rimuoviIndirizzo(request, response);
                        break;
                    case "visualizzametodi":
                        visualizzaMetodi(request, response);
                        break;
                    case "rimuovimetodo":
                        rimuoviMetodo(request, response);
                        break;
                    case "logout":
                        logout(request, response);
                        break;
                    case "inserisciindirizzo" :
                        inserisciIndirizzo(request, response);
                        break;
                    case "inseriscimetodo" :
                        inserisciMetodo(request, response);
                        break;
                    default:
                        RequestDispatcher errorDispatcher = getServletContext().getRequestDispatcher("/errore.jsp");
                        errorDispatcher.forward(request, response);
                        break;
                }
            }
        } catch (ServletException | IOException | SQLException e) {
            logger.info("Si è verificata un'eccezione:" + e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doGet(req, resp);
        } catch (ServletException | IOException e) {
            logger.info("Si è verificata un'eccezione:" + e);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String email = request.getParameter(emailParameter);
        String password = request.getParameter(pwdParameter);
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
                DatiSensibiliBean dati = datiModel.recuperaDati(utente.getEmail());
                session.setAttribute(emailParameter, utente.getEmail());
                session.setAttribute("nome", dati.getNome());
                session.setAttribute("cognome", dati.getCognome());
                session.setAttribute("tipo", utente.isTipo());
                response.sendRedirect(INDEX_PAGE);
            }
        }
    }

    private void registrati(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String nome = request.getParameter("nome");
        String cognome = request.getParameter(cognomeParameter);
        Date data = Date.valueOf(request.getParameter("data"));
        String via = request.getParameter("via");
        int civico = Integer.parseInt(request.getParameter(civicoParameter));
        String provincia = request.getParameter(provinciaParameter);
        String comune = request.getParameter(comuneParameter);
        String email = request.getParameter(emailParameter);
        String password = request.getParameter(pwdParameter);
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
            session.setAttribute("nome", dati.getNome());
            session.setAttribute("cognome", dati.getCognome());
            session.setAttribute("tipo", utente.isTipo());
            response.sendRedirect(INDEX_PAGE);
        }
    }

    public void modificaProfilo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String nome = request.getParameter("nome");
        String cognome = request.getParameter(cognomeParameter);
        Date data = Date.valueOf(request.getParameter("data"));
        String via = request.getParameter("via");
        int civico = Integer.parseInt(request.getParameter(civicoParameter));
        String provincia = request.getParameter(provinciaParameter);
        String comune = request.getParameter(comuneParameter);

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute(emailParameter);

        String password = request.getParameter(pwdParameter);
        UtenteBean utente = new UtenteBean(email, password, false);
        DatiSensibiliBean datiUtente = new DatiSensibiliBean(email, nome, cognome, data, via, civico, provincia, comune);

        utenteModel.modificaProfilo(utente);
        datiModel.modificaDati(datiUtente);
        response.sendRedirect("./profilo.jsp");
    }

    private void visualizzaIndirizzi(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute(emailParameter);
        List<Integer> indirizziPerEmail = indirizzoModel.indirizziUtilizzati(email);
        List<IndirizzoBean> indirizziUtilizzati = new ArrayList<>();
        for (int i : indirizziPerEmail) {
            indirizziUtilizzati.add(indirizzoModel.getIndirizzo(i));
        }
        request.setAttribute("indirizzi", indirizziUtilizzati);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/indirizzi.jsp");
        dispatcher.forward(request, response);
    }

    public void rimuoviIndirizzo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int i = Integer.parseInt(request.getParameter("IdIndirizzo"));
        indirizzoModel.rimuoviIndirizzo(i);
        response.sendRedirect("./indirizzi.jsp");
    }

    private void inserisciIndirizzo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nome = request.getParameter("nome");
        String cognome = request.getParameter(cognomeParameter);
        String via = request.getParameter("via");
        int civico = Integer.parseInt(request.getParameter(civicoParameter));
        String provincia = request.getParameter(provinciaParameter);
        String comune = request.getParameter(comuneParameter);
        int cap = Integer.parseInt(request.getParameter("cap"));

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute(emailParameter);

        indirizzoModel.aggiungiIndirizzo(nome, cognome, via, civico, provincia, comune, cap);
        indirizzoModel.aggiornaUtilizza(email);
        response.sendRedirect("./indirizzi.jsp");
    }

    private void visualizzaMetodi(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute(emailParameter);
        List<Integer> metodiPerEmail = pagamentoModel.metodiUtilizzati(email);
        List<PagamentoBean> metodiUtilizzati = new ArrayList<>();
        for (Integer i : metodiPerEmail) {
            metodiUtilizzati.add(pagamentoModel.getMetodo(i));
        }
        request.setAttribute("pagamenti", metodiUtilizzati);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/metodiPagamento.jsp");
        dispatcher.forward(request, response);
    }

    public void rimuoviMetodo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int i = Integer.parseInt(request.getParameter("IdMetodo"));
        pagamentoModel.rimuoviMetodo(i);
        response.sendRedirect("./metodiPagamento.jsp");
    }

    public void inserisciMetodo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String numero = request.getParameter("numero");
        Date data = Date.valueOf(request.getParameter("data"));
        int ccv = Integer.parseInt(request.getParameter("ccv"));
        String titolare = request.getParameter("titolare");

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute(emailParameter);

        pagamentoModel.aggiungiMetodo(numero, data, ccv, titolare);
        pagamentoModel.aggiornaRegistra(email);
        response.sendRedirect("./metodiPagamento.jsp");
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        response.sendRedirect(INDEX_PAGE);
    }
}
