package com.example.space_nerd.Model;

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

public class PopModel {
    private static Logger logger = Logger.getLogger(PopModel.class.getName());
    private static final String TABLE_NAME_POP = "Pop";
    private static final String TABLE_NAME_COMPRENDE = "ComprendePop";
    private static final String TABLE_NAME_IMMAGINE = "ImmaginePop";
    private static String msgCon = "Errore durante la chiusura della Connection";
    private static String msgPs = "Errore durante la chiusura del PreparedStatement";
    private static String msgRs = "Errore durante la chiusura del ResultSet";
    private static DataSource ds;

    static {
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");

            ds = (DataSource) envCtx.lookup("jdbc/spacenerd");

        } catch (NamingException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    public List<PopBean> miglioriPop() throws SQLException {
        List<PopBean> miglioriPop = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            String query = "SELECT P.IdPop, P.Descrizione, SUM(Cm.Quantita) AS Tot FROM " + TABLE_NAME_POP +
                    " P JOIN " + TABLE_NAME_COMPRENDE +
                    " Cm ON P.IdPop = Cm.IdPop GROUP BY P.IdPop ORDER BY Tot DESC LIMIT 3";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()) {
                PopBean pop = new PopBean();
                pop.setIdPop(rs.getInt("IdPop"));
                pop.setDescrizione(rs.getString("Descrizione"));
                miglioriPop.add(pop);
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
        return miglioriPop;
    }

    public List<String> imgPerPop(PopBean popBean) {
        List<String> immagini = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = ds.getConnection();
            String query = "SELECT Nome FROM " + TABLE_NAME_IMMAGINE + " WHERE IdPop = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, popBean.getIdPop());
            rs = ps.executeQuery();
            while(rs.next()){
                String img = rs.getString("Nome");
                immagini.add(img);
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
        return immagini;
    }

    public List<PopBean> allPop() {
        List<PopBean> allPop = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            String query = "SELECT IdPop, Descrizione FROM " + TABLE_NAME_POP;
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()) {
                PopBean pop = new PopBean();
                pop.setIdPop(rs.getInt("IdPop"));
                pop.setDescrizione(rs.getString("Descrizione"));
                allPop.add(pop);
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
        return allPop;
    }

    public List<String> oneImgPerPop(){
        List<String> imgPerPop = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            String query = "SELECT Nome FROM " + TABLE_NAME_IMMAGINE + " WHERE (IdPop, IdImmagine) IN (SELECT " +
                    "IdPop, MIN(IdImmagine) FROM " + TABLE_NAME_IMMAGINE + " GROUP BY IdPop)";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()) {
                imgPerPop.add(rs.getString("Nome"));
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
        return imgPerPop;
    }

    public PopBean getById(int i) {
        PopBean pop = new PopBean();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            String query = "SELECT * FROM " + TABLE_NAME_POP + " WHERE IdPop = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, i);
            rs = ps.executeQuery();
            while (rs.next()) {
                pop.setIdPop(i);
                pop.setPrezzo(rs.getFloat("Prezzo"));
                pop.setDescrizione(rs.getString("Descrizione"));
                pop.setNumArticoli(rs.getInt("NumeroArticoli"));
                pop.setNumSerie(rs.getInt("NumeroSerie"));
                pop.setSerie(rs.getString("Serie"));
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
        return pop;
    }

    public boolean verificaDisponibilita(int i) {
        boolean res = false;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            String query = "SELECT NumeroArticoli FROM " + TABLE_NAME_POP + " WHERE IdPop = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, i);
            rs = ps.executeQuery();
            while(rs.next()) {
                if(rs.getInt("NumeroArticoli") > 0) {
                    res = true;
                }
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
        return res;
    }
}
