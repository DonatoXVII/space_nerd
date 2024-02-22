package com.example.space_nerd.Model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatiSensibiliModel {
    private static Logger logger = Logger.getLogger(DatiSensibiliModel.class.getName());
    private static final String TABLE_NAME_DATI = "DatiSensibili";
    private static DataSource ds;
    private static String msgCon = "Errore durante la chiusura della Connection";
    private static String msgPs = "Errore durante la chiusura del PreparedStatement";

    static {
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");

            ds = (DataSource) envCtx.lookup("jdbc/spacenerd");

        } catch (NamingException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    public void registraDati(DatiSensibiliBean dati) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();
            String query = "INSERT INTO " + TABLE_NAME_DATI + "(Email, Nome, Cognome, DataNascita, Via, Civico, Provincia, Comune)" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(query);
            ps.setString(1, dati.getEmail());
            ps.setString(2, dati.getNome());
            ps.setString(3, dati.getCognome());
            ps.setDate(4, dati.getDataNascita());
            ps.setString(5, dati.getVia());
            ps.setInt(6, dati.getCivico());
            ps.setString(7, dati.getProvincia());
            ps.setString(8, dati.getComune());
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
