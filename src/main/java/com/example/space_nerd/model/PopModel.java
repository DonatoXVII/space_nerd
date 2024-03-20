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
    private static final String ID_POP = "IdPop";
    private static final String PREZZO_PARAMETER = "Prezzo";
    private static final String ARTICOLI_PARAMETER = "NumeroArticoli";
    private static final String SERIE_PARAMETER = "Serie";
    private static String msgCon = "Errore durante la chiusura della Connection";
    private static String msgPs = "Errore durante la chiusura del PreparedStatement";
    private static String msgRs = "Errore durante la chiusura del ResultSet";
    private static String descrizioneParameter = "Descrizione";
    private static final String UPDATE = "UPDATE ";
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
                    " Cm ON P.IdPop = Cm.IdPop WHERE P.FlagVisibilita = 1 GROUP BY P.IdPop ORDER BY Tot DESC LIMIT 3";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()) {
                PopBean pop = new PopBean();
                pop.setIdPop(rs.getInt(ID_POP));
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
            String query = "SELECT IdPop, Descrizione, Serie, Prezzo, NumeroArticoli FROM " + TABLE_NAME_POP + " WHERE FlagVisibilita = 1";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()) {
                PopBean pop = new PopBean();
                pop.setIdPop(rs.getInt(ID_POP));
                pop.setDescrizione(rs.getString(descrizioneParameter));
                pop.setSerie(rs.getString(SERIE_PARAMETER));
                pop.setPrezzo(rs.getFloat(PREZZO_PARAMETER));
                pop.setNumArticoli(rs.getInt(ARTICOLI_PARAMETER));
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
                pop.setPrezzo(rs.getFloat(PREZZO_PARAMETER));
                pop.setDescrizione(rs.getString(descrizioneParameter));
                pop.setNumArticoli(rs.getInt(ARTICOLI_PARAMETER));
                pop.setNumSerie(rs.getInt("NumeroSerie"));
                pop.setSerie(rs.getString(SERIE_PARAMETER));
                pop.setVisibilita(rs.getBoolean("FlagVisibilita"));
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
                if(rs.getInt(ARTICOLI_PARAMETER) > 0) {
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
            String query = "SELECT * FROM " + TABLE_NAME_POP + " WHERE Descrizione LIKE ? AND FlagVisibilita = 1";
            ps = con.prepareStatement(query);
            ps.setString(1, descrizione);
            rs = ps.executeQuery();
            while(rs.next()) {
                pop.setIdPop(rs.getInt(ID_POP));
                pop.setPrezzo(rs.getFloat(PREZZO_PARAMETER));
                pop.setDescrizione(descrizione);
                pop.setNumArticoli(rs.getInt(ARTICOLI_PARAMETER));
                pop.setNumSerie(rs.getInt("NumeroSerie"));
                pop.setSerie(rs.getString(SERIE_PARAMETER));
                pop.setVisibilita(true);
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
            String query = "SELECT Descrizione FROM " + TABLE_NAME_POP + " WHERE Descrizione LIKE ? AND FlagVisibilita = 1";
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
            String query = UPDATE + TABLE_NAME_POP + " SET NumeroArticoli = NumeroArticoli - ? WHERE IdPop = ?";
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

    public void eliminaProdotto(int id) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();
            String query = UPDATE + TABLE_NAME_POP + " SET FlagVisibilita = 0 WHERE IdPop = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
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

    public void aggiungiNumArticoli(int id, int tot) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();
            String query = UPDATE + TABLE_NAME_POP + " SET NumeroArticoli = NumeroArticoli + ? WHERE IdPop = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, tot);
            ps.setInt(2, id);
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

    public void rimuoviNumeroArticoli(int id, int tot) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();
            String query = UPDATE + TABLE_NAME_POP + " SET NumeroArticoli = NumeroArticoli - ? WHERE IdPop = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, tot);
            ps.setInt(2, id);
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

    public void cambiaPrezzo(int id, float prezzo) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();
            String query = UPDATE + TABLE_NAME_POP +  " SET Prezzo = ? WHERE IdPop = ?";
            ps = con.prepareStatement(query);
            ps.setFloat(1, prezzo);
            ps.setInt(2, id);
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

    public void aggiungiProdotto(PopBean pop) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();
            String query = "INSERT INTO " + TABLE_NAME_POP + "(Prezzo, Descrizione, NumeroArticoli, NumeroSerie, Serie, FlagVisibilita)" +
                    "VALUES(?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(query);
            ps.setFloat(1, pop.getPrezzo());
            ps.setString(2, pop.getDescrizione());
            ps.setInt(3, pop.getNumArticoli());
            ps.setInt(4, pop.getNumSerie());
            ps.setString(5, pop.getSerie());
            ps.setBoolean(6, true);
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

    public int getLastPop() {
        int id = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            String query = "SELECT MAX(IdPop) AS Max FROM " + TABLE_NAME_POP;
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("Max");
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
        return id;
    }

    public void aggiungiImmaginiPop(int id, String img) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();
            String query = "INSERT INTO " + TABLE_NAME_IMMAGINE + "(Nome, IdPop)" +
                    "VALUES(?, ?)";
            ps = con.prepareStatement(query);
            ps.setString(1, img);
            ps.setInt(2, id);
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

    public void restock(int id) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();
            String query = UPDATE + TABLE_NAME_POP + " SET FlagVisibilita = 1, NumeroArticoli = 10 WHERE IdPop = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
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

    public float getPrezzoUnitarioInThatOrdine(int idManga, int idOrdine) {
        float prezzo = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            String query = "SELECT PrezzoUnitario FROM " + TABLE_NAME_COMPRENDE + " WHERE IdPop = ? AND IdOrdine = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, idManga);
            ps.setInt(2, idOrdine);
            rs = ps.executeQuery();
            while (rs.next()) {
                prezzo = rs.getFloat("PrezzoUnitario");
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
        return prezzo;
    }
}
