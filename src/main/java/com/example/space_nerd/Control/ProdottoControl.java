package com.example.space_nerd.Control;

import com.example.space_nerd.Model.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/ProdottoControl")
public class ProdottoControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static MangaModel mangaModel = new MangaModel();
    static PopModel popModel = new PopModel();
    static FigureModel figureModel = new FigureModel();
    static Logger logger = Logger.getLogger(ProdottoControl.class.getName());

    public ProdottoControl() {super();}

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            List<MangaBean> bestManga = mangaModel.miglioriManga();
            List<PopBean> bestPop = popModel.miglioriPop();
            List<FigureBean> bestFigure = figureModel.miglioriFigure();

            List<Object> bestProdotti = new ArrayList<>();
            bestProdotti.addAll(bestManga);
            bestProdotti.addAll(bestPop);
            bestProdotti.addAll(bestFigure);
            req.setAttribute("bestProdotti", bestProdotti);

            List<String> immaginiPop = new ArrayList<>();
            for(PopBean bean : bestPop) {
                immaginiPop.add(popModel.imgPerPop(bean).get(0));
            }
            req.setAttribute("immaginiPop", immaginiPop);

            List<String> immaginiFigure = new ArrayList<>();
            for(FigureBean bean : bestFigure) {
                immaginiFigure.add(figureModel.imgPerFigure(bean).get(0));
            }
            req.setAttribute("immaginiFigure", immaginiFigure);

            RequestDispatcher dispatcher = req.getRequestDispatcher("/index.jsp");
            dispatcher.forward(req, resp);

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
}
