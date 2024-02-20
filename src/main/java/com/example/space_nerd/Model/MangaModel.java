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

public class MangaModel {
    private static Logger logger = Logger.getLogger(MangaModel.class.getName());
    private static final String TABLE_NAME_MANGA = "Manga";
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

    public List<MangaBean> miglioriManga() throws SQLException {
        List<MangaBean> bestManga = new ArrayList<>();
        MangaBean manga = new MangaBean();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = ds.getConnection();
            String query = "SELECT * FROM " + TABLE_NAME_MANGA + " WHERE IdManga = 1";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()) {
                manga.setIdManga(rs.getInt("IdManga"));
                manga.setPrezzo(rs.getFloat("Prezzo"));
                manga.setDescrizione(rs.getString("Descrizione"));
                manga.setNumArticoli(rs.getInt("NumeroArticoli"));
                manga.setCasaEditrice(rs.getString("CasaEditrice"));
                manga.setLingua(rs.getString("Lingua"));
                manga.setNumPagine(rs.getInt("NumeroPagine"));
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
                logger.log(Level.WARNING, "Errore durante la chiusura del ResultSet", e);
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, "Errore durante la chiusura del PreparedStatement", e);
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, "Errore durante la chiusura della Connection", e);
            }
        }

        return  bestManga;
    }
}
