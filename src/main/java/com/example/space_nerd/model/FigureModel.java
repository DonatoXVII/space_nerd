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

public class FigureModel {
    private static final Logger logger = Logger.getLogger(FigureModel.class.getName());
    private static final String TABLE_NAME_FIGURE = "Figure";
    private static final String TABLE_NAME_COMPRENDE = "ComprendeFigure";
    private static final String TABLE_NAME_IMMAGINE = "ImmagineFigure";
    private static final String WHERE_IDFIGURE = "WHERE IdFigure = ?";
    private static final String ID_FIGURE_PARAMETER = "IdFigure";
    private static final String PERSONAGGIO_PARAMETER = "Personaggio";
    private static final String N_ARTICOLI_PARAMETER = "NumeroArticoli";
    private static final String PREZZO_PARAMETER = "Prezzo";
    private static final String DESCRIZIONE_PARAMETER = "Descrizione";
    private static final String MSG_CON = "Errore durante la chiusura della Connection";
    private static final String MSG_PS = "Errore durante la chiusura del PreparedStatement";
    private static final String MSG_RS = "Errore durante la chiusura del ResultSet";
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

    public List<FigureBean> getMiglioriFigure() {
        List<FigureBean> miglioriFigure = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            String query = "SELECT F.IdFigure, F.Descrizione, SUM(Cm.Quantita) AS Tot FROM " + TABLE_NAME_FIGURE +
                    " F JOIN " + TABLE_NAME_COMPRENDE +
                    " Cm ON F.IdFigure = Cm.IdFigure WHERE F.FlagVisibilita = 1 GROUP BY F.IdFigure ORDER BY Tot DESC LIMIT 3";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()) {
                FigureBean figure = new FigureBean();
                figure.setIdFigure(rs.getInt(ID_FIGURE_PARAMETER));
                figure.setDescrizione(rs.getString(DESCRIZIONE_PARAMETER));
                miglioriFigure.add(figure);
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, MSG_RS, e);
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, MSG_PS, e);
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, MSG_CON, e);
            }
        }
        return miglioriFigure;
    }

    public List<String> getAllImgFigure(FigureBean figureBean) {
        List<String> immagini = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = ds.getConnection();
            String query = "SELECT Nome FROM " + TABLE_NAME_IMMAGINE + " " + WHERE_IDFIGURE;
            ps = con.prepareStatement(query);
            ps.setInt(1, figureBean.getIdFigure());
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
                logger.log(Level.WARNING, MSG_RS, e);
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, MSG_PS, e);
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, MSG_CON, e);
            }
        }
        return immagini;
    }

    public List<FigureBean> getAllFigure() {
        List<FigureBean> allFigure = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            String query = "SELECT IdFigure, Personaggio, Descrizione, Prezzo, NumeroArticoli FROM " + TABLE_NAME_FIGURE + " WHERE FlagVisibilita = 1";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()) {
                FigureBean figure = new FigureBean();
                figure.setIdFigure(rs.getInt(ID_FIGURE_PARAMETER));
                figure.setPersonaggio(rs.getString(PERSONAGGIO_PARAMETER));
                figure.setPrezzo(rs.getFloat(PREZZO_PARAMETER));
                figure.setNumArticoli(rs.getInt(N_ARTICOLI_PARAMETER));
                figure.setDescrizione(rs.getString(DESCRIZIONE_PARAMETER));
                allFigure.add(figure);
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, MSG_RS, e);
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, MSG_PS, e);
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, MSG_CON, e);
            }
        }
        return allFigure;
    }

    public FigureBean getById(int i) {
        FigureBean figure = new FigureBean();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            String query = "SELECT * FROM " + TABLE_NAME_FIGURE + " " + WHERE_IDFIGURE;
            ps = con.prepareStatement(query);
            ps.setInt(1, i);
            rs = ps.executeQuery();
            while (rs.next()) {
                figure.setIdFigure(i);
                figure.setPrezzo(rs.getFloat(PREZZO_PARAMETER));
                figure.setDescrizione(rs.getString(DESCRIZIONE_PARAMETER));
                figure.setNumArticoli(rs.getInt(N_ARTICOLI_PARAMETER));
                figure.setMateriale(rs.getString("Materiale"));
                figure.setAltezza(rs.getInt("Altezza"));
                figure.setPersonaggio(rs.getString(PERSONAGGIO_PARAMETER));
                figure.setVisibilita(rs.getBoolean("FlagVisibilita"));
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, MSG_RS, e);
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, MSG_PS, e);
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, MSG_CON, e);
            }
        }
        return figure;
    }

    public boolean verificaDisponibilita(int i) {
        boolean res = false;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            String query = "SELECT NumeroArticoli FROM " + TABLE_NAME_FIGURE + " " + WHERE_IDFIGURE;
            ps = con.prepareStatement(query);
            ps.setInt(1, i);
            rs = ps.executeQuery();
            while(rs.next()) {
                if(rs.getInt(N_ARTICOLI_PARAMETER) > 0) {
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
                logger.log(Level.WARNING, MSG_RS, e);
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, MSG_PS, e);
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, MSG_CON, e);
            }
        }
        return res;
    }

    public FigureBean getFigurePerDescrizione(String descrizione) {
        FigureBean figure = new FigureBean();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            String query = "SELECT * FROM " + TABLE_NAME_FIGURE + " WHERE CONCAT(Descrizione, ' ', Personaggio) LIKE ? AND FlagVisibilita = 1";
            ps = con.prepareStatement(query);
            ps.setString(1, descrizione);
            rs = ps.executeQuery();
            while(rs.next()) {
                figure.setIdFigure(rs.getInt(ID_FIGURE_PARAMETER));
                figure.setPrezzo(rs.getFloat(PREZZO_PARAMETER));
                figure.setDescrizione(descrizione);
                figure.setNumArticoli(rs.getInt(N_ARTICOLI_PARAMETER));
                figure.setMateriale(rs.getString("Materiale"));
                figure.setAltezza(rs.getInt("Altezza"));
                figure.setPersonaggio(rs.getString(PERSONAGGIO_PARAMETER));
                figure.setVisibilita(true);
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, MSG_RS, e);
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, MSG_PS, e);
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, MSG_CON, e);
            }
        }
        return figure;
    }

    public List<String> getSuggerimentiFigure(String ricerca) {
        List<String> suggerimenti = new ArrayList<>();
        ricerca = "%" + ricerca + "%";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            String query = "SELECT CONCAT(Descrizione, ' ', Personaggio) AS Action FROM " + TABLE_NAME_FIGURE + " WHERE CONCAT(Descrizione, ' ', Personaggio) LIKE ? AND FlagVisibilita = 1";
            ps = con.prepareStatement(query);
            ps.setString(1, ricerca);
            rs = ps.executeQuery();
            while(rs.next()) {
                suggerimenti.add(rs.getString("Action"));
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, MSG_RS, e);
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, MSG_PS, e);
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, MSG_CON, e);
            }
        }
        return suggerimenti;
    }

    public void decrementaDisponibilita(FigureBean figure, int quantita) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();
            String query = UPDATE + TABLE_NAME_FIGURE + " SET NumeroArticoli = NumeroArticoli - ? WHERE IdFigure = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, quantita);
            ps.setInt(2, figure.getIdFigure());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, MSG_PS, e);
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, MSG_CON, e);
            }
        }
    }

    public void eliminaProdotto(int id) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();
            String query = UPDATE + TABLE_NAME_FIGURE + " SET FlagVisibilita = 0 WHERE IdFigure = ?";
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
                logger.log(Level.WARNING, MSG_PS, e);
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, MSG_CON, e);
            }
        }
    }

    public void aggiungiNumArticoli(int id, int tot) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();
            String query = UPDATE + TABLE_NAME_FIGURE + " SET NumeroArticoli = NumeroArticoli + ? WHERE IdFigure = ?";
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
                logger.log(Level.WARNING, MSG_PS, e);
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, MSG_CON, e);
            }
        }
    }

    public void rimuoviNumeroArticoli(int id, int tot) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();
            String query = UPDATE + TABLE_NAME_FIGURE + " SET NumeroArticoli = NumeroArticoli - ? WHERE IdFigure = ?";
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
                logger.log(Level.WARNING, MSG_PS, e);
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, MSG_CON, e);
            }
        }
    }

    public void cambiaPrezzo(int id, float prezzo) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();
            String query = UPDATE + TABLE_NAME_FIGURE +  " SET Prezzo = ? WHERE IdFigure = ?";
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
                logger.log(Level.WARNING, MSG_PS, e);
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, MSG_CON, e);
            }
        }
    }

    public void aggiungiProdotto(FigureBean figure) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();
            String query = "INSERT INTO " + TABLE_NAME_FIGURE + "(Prezzo, Descrizione, NumeroArticoli, Materiale, Altezza, Personaggio, FlagVisibilita)" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(query);
            ps.setFloat(1, figure.getPrezzo());
            ps.setString(2, figure.getDescrizione());
            ps.setInt(3, figure.getNumArticoli());
            ps.setString(4, figure.getMateriale());
            ps.setInt(5, figure.getAltezza());
            ps.setString(6, figure.getPersonaggio());
            ps.setBoolean(7, true);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, MSG_PS, e);
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, MSG_CON, e);
            }
        }
    }

    public int getLastFigure() {
        int id = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            String query = "SELECT MAX(IdFigure) AS Max FROM " + TABLE_NAME_FIGURE;
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
                logger.log(Level.WARNING, MSG_RS, e);
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, MSG_PS, e);
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, MSG_CON, e);
            }
        }
        return id;
    }

    public void aggiungiImmaginiFigure(int id, String img) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();
            String query = "INSERT INTO " + TABLE_NAME_IMMAGINE + "(Nome, IdFigure)" +
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
                logger.log(Level.WARNING, MSG_PS, e);
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, MSG_CON, e);
            }
        }
    }

    public void restock(int id) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();
            String query = UPDATE + TABLE_NAME_FIGURE + " SET FlagVisibilita = 1, NumeroArticoli = 10 WHERE IdFigure = ?";
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
                logger.log(Level.WARNING, MSG_PS, e);
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, MSG_CON, e);
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
            String query = "SELECT PrezzoUnitario FROM " + TABLE_NAME_COMPRENDE + " WHERE IdFigure = ? AND IdOrdine = ?";
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
                logger.log(Level.WARNING, MSG_RS, e);
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, MSG_PS, e);
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, MSG_CON, e);
            }
        }
        return prezzo;
    }
}
