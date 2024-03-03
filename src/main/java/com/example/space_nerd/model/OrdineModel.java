package com.example.space_nerd.model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrdineModel {
    private static Logger logger = Logger.getLogger(OrdineModel.class.getName());
    private static final String TABLE_NAME_ORDINE = "Ordine";
    private static final String TABLE_NAME_COMPRENDE_MANGA = "ComprendeManga";
    private static final String TABLE_NAME_COMPRENDE_POP = "ComprendePop";
    private static final String TABLE_NAME_COMPRENDE_FIGURE = "ComprendeFigure";
    private static final MangaModel mangaModel = new MangaModel();
    private static final PopModel popModel = new PopModel();
    private static final FigureModel figureModel = new FigureModel();
    private static DataSource ds;
    private static String msgCon = "Errore durante la chiusura della Connection";
    private static String msgPs = "Errore durante la chiusura del PreparedStatement";
    private static String msgRs = "Errore durante la chiusura del ResultSet";

    static {
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");

            ds = (DataSource) envCtx.lookup("jdbc/spacenerd");

        } catch (NamingException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    public List<OrdineBean> getOrdiniPerUtente(String email) {
        List<OrdineBean> ordini = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            String query = "SELECT * FROM " + TABLE_NAME_ORDINE + " WHERE Email = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            while (rs.next()) {
                OrdineBean bean = new OrdineBean();
                bean.setId(rs.getInt("IdOrdine"));
                bean.setPrezzo(rs.getFloat("PrezzoTotale"));
                bean.setData(rs.getDate("DataOrdine"));
                bean.setFattura(rs.getString("Fattura"));
                bean.setEmail(email);
                ordini.add(bean);
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, msgRs, e);
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, msgPs, e);
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, msgCon, e);
            }
        }
        return ordini;
    }

    public List<Object> getProdottiOrdine(int id) {
        List<Object> prodottiOrdine = new ArrayList<>();
        Connection con = null;
        PreparedStatement psManga = null;
        PreparedStatement psPop = null;
        PreparedStatement psFigure = null;
        ResultSet rsManga = null;
        ResultSet rsPop = null;
        ResultSet rsFigure = null;
        try {
            con = ds.getConnection();
            String queryManga = "SELECT IdManga FROM " + TABLE_NAME_COMPRENDE_MANGA + " WHERE IdOrdine = ?";
            String queryPop = "SELECT IdPop FROM " + TABLE_NAME_COMPRENDE_POP + " WHERE IdOrdine = ?";
            String queryFigure = "SELECT IdFigure FROM " + TABLE_NAME_COMPRENDE_FIGURE + " WHERE IdOrdine = ?";

            psManga = con.prepareStatement(queryManga);
            psPop = con.prepareStatement(queryPop);
            psFigure= con.prepareStatement(queryFigure);

            psManga.setInt(1, id);
            psPop.setInt(1, id);
            psFigure.setInt(1, id);

            rsManga = psManga.executeQuery();
            rsPop = psPop.executeQuery();
            rsFigure = psFigure.executeQuery();

            while(rsManga.next()) {
                prodottiOrdine.add(mangaModel.getById(rsManga.getInt("IdManga")));
            }
            while(rsPop.next()) {
                prodottiOrdine.add(popModel.getById(rsPop.getInt("IdPop")));
            }
            while(rsFigure.next()) {
                prodottiOrdine.add(figureModel.getById(rsFigure.getInt("IdFigure")));
            }

        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            try {
                if (rsManga != null) {
                    rsManga.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, msgRs, e);
            }
            try {
                if (rsPop != null) {
                    rsPop.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, msgRs, e);
            }
            try {
                if (rsFigure != null) {
                    rsFigure.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, msgRs, e);
            }
            try {
                if (psManga != null) {
                    psManga.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, msgPs, e);
            }
            try {
                if (psPop != null) {
                    psPop.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, msgPs, e);
            }
            try {
                if (psFigure != null) {
                    psFigure.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, msgPs, e);
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, msgCon, e);
            }
        }
        return prodottiOrdine;
    }

}
