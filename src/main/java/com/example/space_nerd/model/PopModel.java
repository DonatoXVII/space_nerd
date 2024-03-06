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

public class PopModel {
    private static Logger logger = Logger.getLogger(PopModel.class.getName());
    private static final String TABLE_NAME_POP = "Pop";
    private static final String TABLE_NAME_COMPRENDE = "ComprendePop";
    private static final String TABLE_NAME_IMMAGINE = "ImmaginePop";
    private static final String WHERE_IDPOP = "WHERE IdPop = ?";
    private static String msgCon = "Errore durante la chiusura della Connection";
    private static String msgPs = "Errore durante la chiusura del PreparedStatement";
    private static String msgRs = "Errore durante la chiusura del ResultSet";
    private static String descrizioneParameter = "Descrizione";
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

    public List<PopBean> getMiglioriPop() {
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
                pop.setDescrizione(rs.getString(descrizioneParameter));
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

    public List<String> getAllImgPop(PopBean popBean) {
        List<String> immagini = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = ds.getConnection();
            String query = "SELECT Nome FROM " + TABLE_NAME_IMMAGINE + " " + WHERE_IDPOP;
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

    public List<PopBean> getAllPop() {
        List<PopBean> allPop = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            String query = "SELECT IdPop, Descrizione, Prezzo FROM " + TABLE_NAME_POP;
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()) {
                PopBean pop = new PopBean();
                pop.setIdPop(rs.getInt("IdPop"));
                pop.setDescrizione(rs.getString(descrizioneParameter));
                pop.setPrezzo(rs.getFloat("Prezzo"));
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

    public PopBean getById(int i) {
        PopBean pop = new PopBean();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            String query = "SELECT * FROM " + TABLE_NAME_POP + " " + WHERE_IDPOP;
            ps = con.prepareStatement(query);
            ps.setInt(1, i);
            rs = ps.executeQuery();
            while (rs.next()) {
                pop.setIdPop(i);
                pop.setPrezzo(rs.getFloat("Prezzo"));
                pop.setDescrizione(rs.getString(descrizioneParameter));
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
            String query = "SELECT NumeroArticoli FROM " + TABLE_NAME_POP + " " + WHERE_IDPOP;
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

    public PopBean getPopPerDescrizione(String descrizione) {
        PopBean pop = new PopBean();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            String query = "SELECT * FROM " + TABLE_NAME_POP + " WHERE Descrizione LIKE ?";
            ps = con.prepareStatement(query);
            ps.setString(1, descrizione);
            rs = ps.executeQuery();
            while(rs.next()) {
                pop.setIdPop(rs.getInt("IdPop"));
                pop.setPrezzo(rs.getFloat("Prezzo"));
                pop.setDescrizione(descrizione);
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

    public List<String> getSuggerimentiPop(String ricerca) {
        List<String> suggerimenti = new ArrayList<>();
        ricerca = "%" + ricerca + "%";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            String query = "SELECT Descrizione FROM " + TABLE_NAME_POP + " WHERE Descrizione LIKE ?";
            ps = con.prepareStatement(query);
            ps.setString(1, ricerca);
            rs = ps.executeQuery();
            while(rs.next()) {
                suggerimenti.add(rs.getString("Descrizione"));
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
        return suggerimenti;
    }

    public void decrementaDisponibilita(PopBean pop, int quantita) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();
            String query = "UPDATE " + TABLE_NAME_POP + " SET NumeroArticoli = NumeroArticoli - ? WHERE IdPop = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, quantita);
            ps.setInt(2, pop.getIdPop());
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
