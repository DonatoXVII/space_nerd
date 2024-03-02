package com.example.space_nerd.control;

import com.example.space_nerd.model.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
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
    private static final String EMAIL_PARAMETER = "email";
    private static final String PWD_PARAMETER = "password";
    private static final String NOME_PARAMETER = "nome";
    private static final String COGNOME_PARAMETER = "cognome";
    private static final String CIVICO_PARAMETER = "civico";
    private static final String PROVINCIA_PARAMETER = "provincia";
    private static final String COMUNE_PARAMETER = "comune";

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
                    case "utenteregistrato":
                        utenteRegistrato(request, response);
                        break;
                    case "registrati":
                        registrati(request, response);
                        break;
                    case "verificaemail" :
                        verificaEmail(request, response);
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

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter(EMAIL_PARAMETER);
        String password = request.getParameter(PWD_PARAMETER);
        HttpSession session = request.getSession(true);
        if (email == null || password == null) {
            response.sendRedirect(INDEX_PAGE);
        } else {
            UtenteBean utente = utenteModel.login(email, password);
            if (utente == null) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
                dispatcher.forward(request, response);
            } else {
                DatiSensibiliBean dati = datiModel.recuperaDati(utente.getEmail());
                session.setAttribute(EMAIL_PARAMETER, utente.getEmail());
                session.setAttribute(NOME_PARAMETER, dati.getNome());
                session.setAttribute(COGNOME_PARAMETER, dati.getCognome());
                session.setAttribute("tipo", utente.isTipo());
                response.sendRedirect(INDEX_PAGE);
            }
        }
    }

    private void utenteRegistrato(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter(EMAIL_PARAMETER);
        String pwd = request.getParameter(PWD_PARAMETER);
        UtenteBean utente = utenteModel.login(email, pwd);
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        if(utente == null) {
            PrintWriter out = response.getWriter();
            out.print("non esiste");
            out.flush();
        } else {
            PrintWriter out = response.getWriter();
            out.print("esiste");
            out.flush();
        }
    }

    private void verificaEmail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter(EMAIL_PARAMETER);
        boolean trovato = utenteModel.emailPresente(email);
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        if(trovato) {
            PrintWriter out = response.getWriter();
            out.print("esiste");
            out.flush();
        } else {
            PrintWriter out = response.getWriter();
            out.print("non esiste");
            out.flush();
        }
    }

    private void registrati(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String nome = request.getParameter(NOME_PARAMETER);
        String cognome = request.getParameter(COGNOME_PARAMETER);
        Date data = Date.valueOf(request.getParameter("data"));
        String via = request.getParameter("via");
        int civico = Integer.parseInt(request.getParameter(CIVICO_PARAMETER));
        String provincia = request.getParameter(PROVINCIA_PARAMETER);
        String comune = request.getParameter(COMUNE_PARAMETER);
        String email = request.getParameter(EMAIL_PARAMETER);
        String password = request.getParameter(PWD_PARAMETER);

        UtenteBean utente = new UtenteBean(email, password, false);
        HttpSession session = request.getSession(true);

        if(utenteModel.emailPresente(email)) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/registrati.jsp");
            dispatcher.forward(request, response);
        } else {
            utenteModel.registraUtente(email, password);
            DatiSensibiliBean dati = new DatiSensibiliBean(email, nome, cognome, data, via, civico, provincia, comune);
            datiModel.registraDati(dati);
            session.setAttribute(EMAIL_PARAMETER, utente.getEmail());
            session.setAttribute(NOME_PARAMETER, dati.getNome());
            session.setAttribute(COGNOME_PARAMETER, dati.getCognome());
            session.setAttribute("tipo", utente.isTipo());
            response.sendRedirect(INDEX_PAGE);
        }
    }

    private void modificaProfilo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nome = request.getParameter(NOME_PARAMETER);
        String cognome = request.getParameter(COGNOME_PARAMETER);
        Date data = Date.valueOf(request.getParameter("data"));
        String via = request.getParameter("via");
        int civico = Integer.parseInt(request.getParameter(CIVICO_PARAMETER));
        String provincia = request.getParameter(PROVINCIA_PARAMETER);
        String comune = request.getParameter(COMUNE_PARAMETER);
        String password = request.getParameter(PWD_PARAMETER);

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute(EMAIL_PARAMETER);

        UtenteBean utente = new UtenteBean(email, password, false);
        DatiSensibiliBean datiUtente = new DatiSensibiliBean(email, nome, cognome, data, via, civico, provincia, comune);
        utenteModel.modificaPassword(utente);
        datiModel.modificaDati(datiUtente);
        response.sendRedirect("./profilo.jsp");
    }

    private void visualizzaIndirizzi(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute(EMAIL_PARAMETER);
        List<Integer> indirizziPerEmail = indirizzoModel.getIndiririzziUtente(email);
        List<IndirizzoBean> indirizziUtilizzati = new ArrayList<>();
        for (int i : indirizziPerEmail) {
            indirizziUtilizzati.add(indirizzoModel.getIndirizzo(i));
        }
        request.setAttribute("indirizzi", indirizziUtilizzati);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/indirizzi.jsp");
        dispatcher.forward(request, response);
    }

    private void rimuoviIndirizzo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int i = Integer.parseInt(request.getParameter("IdIndirizzo"));
        indirizzoModel.rimuoviIndirizzo(i);
        response.sendRedirect("./indirizzi.jsp");
    }

    private void inserisciIndirizzo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nome = request.getParameter(NOME_PARAMETER);
        String cognome = request.getParameter(COGNOME_PARAMETER);
        String via = request.getParameter("via");
        int civico = Integer.parseInt(request.getParameter(CIVICO_PARAMETER));
        String provincia = request.getParameter(PROVINCIA_PARAMETER);
        String comune = request.getParameter(COMUNE_PARAMETER);
        int cap = Integer.parseInt(request.getParameter("cap"));

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute(EMAIL_PARAMETER);

        indirizzoModel.aggiungiIndirizzo(nome, cognome, via, civico, provincia, comune, cap);
        indirizzoModel.aggiornaUtilizza(email);
        response.sendRedirect("./indirizzi.jsp");
    }

    private void visualizzaMetodi(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute(EMAIL_PARAMETER);
        List<Integer> metodiPerEmail = pagamentoModel.getMetodiUtente(email);
        List<PagamentoBean> metodiUtilizzati = new ArrayList<>();
        for (Integer i : metodiPerEmail) {
            metodiUtilizzati.add(pagamentoModel.getMetodo(i));
        }
        request.setAttribute("pagamenti", metodiUtilizzati);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/metodiPagamento.jsp");
        dispatcher.forward(request, response);
    }

    private void rimuoviMetodo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int i = Integer.parseInt(request.getParameter("IdMetodo"));
        pagamentoModel.rimuoviMetodo(i);
        response.sendRedirect("./metodiPagamento.jsp");
    }

    private void inserisciMetodo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String numero = request.getParameter("numero");
        Date data = Date.valueOf(request.getParameter("data"));
        int ccv = Integer.parseInt(request.getParameter("ccv"));
        String titolare = request.getParameter("titolare");

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute(EMAIL_PARAMETER);

        pagamentoModel.aggiungiMetodo(numero, data, ccv, titolare);
        pagamentoModel.aggiornaRegistra(email);
        response.sendRedirect("./metodiPagamento.jsp");
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        response.sendRedirect(INDEX_PAGE);
    }
}