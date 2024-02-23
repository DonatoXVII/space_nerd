package com.example.space_nerd.Control;

import com.example.space_nerd.Model.OrdineBean;
import com.example.space_nerd.Model.OrdineModel;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/OrdiniControl")
public class OrdiniControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static OrdineModel ordineModel = new OrdineModel();
    static Logger logger = Logger.getLogger(OrdiniControl.class.getName());

    public OrdiniControl() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if (action != null && action.equalsIgnoreCase("visualizzaOrdini")) {
                List<OrdineBean> ordini;
                HttpSession session = req.getSession();
                ordini = ordineModel.visualizzaOrdini((String) session.getAttribute("email"));
                req.setAttribute("ordini", ordini);
                RequestDispatcher dispatcher = req.getRequestDispatcher("/visualizzaOrdini.jsp");
                dispatcher.forward(req, resp);
            }
        } catch (ServletException | IOException e) {
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
}
