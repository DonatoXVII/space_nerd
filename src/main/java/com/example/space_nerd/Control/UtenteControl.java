package com.example.space_nerd.Control;

import com.example.space_nerd.Model.UtenteBean;
import com.example.space_nerd.Model.UtenteModel;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/UtenteControl")
public class UtenteControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static UtenteModel utenteModel = new UtenteModel();
    Logger logger = Logger.getLogger(UtenteControl.class.getName());

    public UtenteControl() {
        super();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        try {
            if(action != null) {
                if(action.equalsIgnoreCase("login")) {
                    UtenteBean utente;
                    String email = request.getParameter("email");
                    String password = request.getParameter("password");
                    HttpSession session = request.getSession(true);
                    if(email == null || password == null) {
                        response.sendRedirect("./index.jsp");
                    } else {
                      utente = utenteModel.login(email, password);
                      if(utente == null) {
                          request.setAttribute("result", "Credenziali errate");
                          RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
                          dispatcher.forward(request, response);
                      } else {
                          session.setAttribute("email", utente.getEmail());
                          session.setAttribute("tipo", utente.isTipo());
                          response.sendRedirect("./admin.jsp");
                      }
                    }
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
}
