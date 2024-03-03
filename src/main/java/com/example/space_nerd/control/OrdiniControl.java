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
                switch (action.toLowerCase()) {
                    case "visualizzaordini" :
                        visualizzaOrdini(req, resp);
                        break;
                    case "visualizzadettagliordine" :
                        visualizzaDettagliOrdini(req, resp);
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

    private void visualizzaOrdini(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<OrdineBean> ordini;
        HttpSession session = request.getSession();
        ordini = ordineModel.getOrdiniPerUtente((String) session.getAttribute("email"));
        request.setAttribute("ordini", ordini);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/ordini.jsp");
        dispatcher.forward(request, response);
    }

    private void visualizzaDettagliOrdini(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    }
}

