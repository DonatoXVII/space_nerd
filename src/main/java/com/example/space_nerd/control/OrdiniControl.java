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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/OrdiniControl")
public class OrdiniControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static OrdineModel ordineModel = new OrdineModel();
    static PopModel popModel = new PopModel();
    static FigureModel figureModel = new FigureModel();
    static Logger logger = Logger.getLogger(OrdiniControl.class.getName());

    public OrdiniControl() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if(action != null) {
                if (action.equalsIgnoreCase("visualizzaOrdini")) {
                    List<OrdineBean> ordini;
                    HttpSession session = req.getSession();
                    ordini = ordineModel.visualizzaOrdini((String) session.getAttribute("email"));
                    req.setAttribute("ordini", ordini);
                    RequestDispatcher dispatcher = req.getRequestDispatcher("/ordini.jsp");
                    dispatcher.forward(req, resp);
                }
                if(action.equalsIgnoreCase("visualizzaDettagliOrdine")) {
                    int id = Integer.parseInt(req.getParameter("IdOrdine"));
                    List<Object> prodottiOrdine = new ArrayList<>(ordineModel.getProdottiOrdine(id));
                    List<String> imgPop = new ArrayList<>();
                    List<String> imgFigure = new ArrayList<>();
                    for(Object prod : prodottiOrdine) {
                        if(prod instanceof PopBean) {
                            assert false;
                            imgPop.addAll(popModel.imgPerPop((PopBean) prod));
                        } else if(prod instanceof FigureBean) {
                            assert false;
                            imgFigure.addAll(figureModel.imgPerFigure((FigureBean) prod));
                        }
                    }
                    req.setAttribute("prodottiOrdine", prodottiOrdine);
                    req.setAttribute("imgPop", imgPop);
                    req.setAttribute("imgFigure", imgFigure);
                    RequestDispatcher dispatcher = req.getRequestDispatcher("/dettagliOrdine.jsp");
                    dispatcher.forward(req, resp);
                }
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
