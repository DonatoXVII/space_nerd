package com.example.space_nerd.model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
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
    private static final String WHERE_IDORDINE = "WHERE IdOrdine = ?";
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
            String queryManga = "SELECT IdManga, Quantita FROM " + TABLE_NAME_COMPRENDE_MANGA + " " + WHERE_IDORDINE;
            String queryPop = "SELECT IdPop, Quantita FROM " + TABLE_NAME_COMPRENDE_POP + " " + WHERE_IDORDINE;
            String queryFigure = "SELECT IdFigure, Quantita FROM " + TABLE_NAME_COMPRENDE_FIGURE + " " + WHERE_IDORDINE;

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
                MangaBean manga = (mangaModel.getById(rsManga.getInt("IdManga")));
                manga.setQuantitaCarrello(rsManga.getInt("Quantita"));
                prodottiOrdine.add(manga);
            }
            while(rsPop.next()) {
                PopBean pop = popModel.getById(rsPop.getInt("IdPop"));
                pop.setQuantitaCarrello(rsPop.getInt("Quantita"));
                prodottiOrdine.add(pop);
            }
            while(rsFigure.next()) {
                FigureBean figure = figureModel.getById(rsFigure.getInt("IdFigure"));
                figure.setQuantitaCarrello(rsFigure.getInt("Quantita"));
                prodottiOrdine.add(figure);
            }

        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            closeResultSet(rsManga, msgRs);
            closeResultSet(rsPop, msgRs);
            closeResultSet(rsFigure, msgRs);

            closePreparedStatement(psManga, msgPs);
            closePreparedStatement(psPop, msgPs);
            closePreparedStatement(psFigure, msgPs);

            closeConnection(con, msgCon);
        }
        return prodottiOrdine;
    }

    private void closeResultSet(ResultSet rs, String message) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, message, e);
        }
    }

    private void closePreparedStatement(PreparedStatement ps, String message) {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, message, e);
        }
    }

    private void closeConnection(Connection con, String message) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, message, e);
        }
    }

    public int getLastIdOrdine() {
        int i = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            String query = "SELECT MAX(IdOrdine) AS Max FROM " + TABLE_NAME_ORDINE;
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
                i = rs.getInt("Max");
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
        return i;
    }

    public void regitraNuovoOrdine(float prezzo, Date data, String fattura, String email) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();
            String query = "INSERT INTO " + TABLE_NAME_ORDINE + "(PrezzoTotale, DataOrdine, Fattura, Email)" +
                    "VALUES(?, ?, ?, ?)";
            ps = con.prepareStatement(query);
            ps.setFloat(1, prezzo);
            ps.setDate(2, data);
            ps.setString(3, fattura);
            ps.setString(4, email);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
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
    }

    public void aggiornaComprendeManga(int ordine, int manga, int quantita) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();
            String query = "INSERT INTO " + TABLE_NAME_COMPRENDE_MANGA + "(IdOrdine, IdManga, Quantita)" +
                    "VALUES(?, ?, ?)";
            ps = con.prepareStatement(query);
            ps.setInt(1, ordine);
            ps.setInt(2, manga);
            ps.setInt(3, quantita);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
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
    }

    public void aggiornaComprendePop(int ordine, int pop, int quantita) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();
            String query = "INSERT INTO " + TABLE_NAME_COMPRENDE_POP + "(IdOrdine, IdPop, Quantita)" +
                    "VALUES(?, ?, ?)";
            ps = con.prepareStatement(query);
            ps.setInt(1, ordine);
            ps.setInt(2, pop);
            ps.setInt(3, quantita);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
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
    }

    public void aggiornaComprendeFigure(int ordine, int figure, int quantita) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();
            String query = "INSERT INTO " + TABLE_NAME_COMPRENDE_FIGURE + "(IdOrdine, IdFigure, Quantita)" +
                    "VALUES(?, ?, ?)";
            ps = con.prepareStatement(query);
            ps.setInt(1, ordine);
            ps.setInt(2, figure);
            ps.setInt(3, quantita);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
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
    }

}
