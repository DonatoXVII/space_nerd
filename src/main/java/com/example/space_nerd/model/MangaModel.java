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

public class MangaModel {
    private static Logger logger = Logger.getLogger(MangaModel.class.getName());
    private static final String TABLE_NAME_MANGA = "Manga";
    private static final String TABLE_NAME_COMPRENDE = "ComprendeManga";
    private static String msgCon = "Errore durante la chiusura della Connection";
    private static String msgPs = "Errore durante la chiusura del PreparedStatement";
    private static String msgRs = "Errore durante la chiusura del ResultSet";
    private static String immagineParameter = "Immagine";
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

    public List<MangaBean> getMiglioriManga() {
        List<MangaBean> bestManga = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = ds.getConnection();
            String query = "SELECT M.IdManga, M.Descrizione, M.Immagine, SUM(Cm.Quantita) as Tot FROM "
                    + TABLE_NAME_MANGA
                    + " M JOIN " + TABLE_NAME_COMPRENDE
                    + " Cm ON M.IdManga = Cm.IdManga GROUP BY M.IdManga"
                    + " ORDER BY Tot DESC LIMIT 3";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()) {
                MangaBean manga = new MangaBean();
                manga.setIdManga(rs.getInt("IdManga"));
                manga.setDescrizione(rs.getString(descrizioneParameter));
                manga.setImg(rs.getString(immagineParameter));
                bestManga.add(manga);
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
        return bestManga;
    }

    public List<MangaBean> getAllManga() {
        List<MangaBean> allManga = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            String query = "SELECT IdManga, Descrizione, Immagine FROM " + TABLE_NAME_MANGA;
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()) {
                MangaBean manga = new MangaBean();
                manga.setIdManga(rs.getInt("IdManga"));
                manga.setDescrizione(rs.getString(descrizioneParameter));
                manga.setImg(rs.getString(immagineParameter));
                allManga.add(manga);
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
        return allManga;
    }

    public MangaBean getById(int i) {
        MangaBean manga = new MangaBean();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            String query = "SELECT * FROM " + TABLE_NAME_MANGA + " WHERE IdManga = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, i);
            rs = ps.executeQuery();
            while (rs.next()) {
                manga.setIdManga(i);
                manga.setPrezzo(rs.getFloat("Prezzo"));
                manga.setDescrizione(rs.getString(descrizioneParameter));
                manga.setNumArticoli(rs.getInt("NumeroArticoli"));
                manga.setCasaEditrice(rs.getString("CasaEditrice"));
                manga.setLingua(rs.getString("Lingua"));
                manga.setNumPagine(rs.getInt("NumeroPagine"));
                manga.setImg(rs.getString(immagineParameter));
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
        return manga;
    }

    public boolean verificaDisponibilita(int i) {
        boolean res = false;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            String query = "SELECT NumeroArticoli FROM " + TABLE_NAME_MANGA + " WHERE IdManga = ?";
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

    public MangaBean getMangaPerDescrizione(String descrizione) {
        MangaBean manga = new MangaBean();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            String query = "SELECT * FROM " + TABLE_NAME_MANGA + " WHERE Descrizione LIKE ?";
            ps = con.prepareStatement(query);
            ps.setString(1, descrizione);
            rs = ps.executeQuery();
            while(rs.next()) {
                manga.setIdManga(rs.getInt("IdManga"));
                manga.setPrezzo(rs.getFloat("Prezzo"));
                manga.setDescrizione(descrizione);
                manga.setNumArticoli(rs.getInt("NumeroArticoli"));
                manga.setCasaEditrice(rs.getString("CasaEditrice"));
                manga.setLingua(rs.getString("Lingua"));
                manga.setNumPagine(rs.getInt("NumeroPagine"));
                manga.setImg(rs.getString(immagineParameter));
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
        return manga;
    }

    public List<String> getSuggerimentiManga(String ricerca) {
        List<String> suggerimenti = new ArrayList<>();
        ricerca = "%" + ricerca + "%";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            String query = "SELECT Descrizione FROM " + TABLE_NAME_MANGA + " WHERE Descrizione LIKE ?";
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
    public void decrementaDisponibilita(MangaBean manga, int quantita) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();
            String query = "UPDATE " + TABLE_NAME_MANGA + " SET NumeroArticoli = NumeroArticoli - ? WHERE IdManga = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, quantita);
            ps.setInt(2, manga.getIdManga());
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
