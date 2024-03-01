package com.example.space_nerd.model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatiSensibiliModel {
    private static Logger logger = Logger.getLogger(DatiSensibiliModel.class.getName());
    private static final String TABLE_NAME_DATI = "DatiSensibili";
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

    public void registraDati(DatiSensibiliBean dati) {
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

    public void modificaDati (DatiSensibiliBean datiUtente) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();
            String query1 = "UPDATE " + TABLE_NAME_DATI + " SET Nome = ?, Cognome = ?, DataNascita = ?," +
                    " Via = ?, Civico = ?, Provincia = ?, Comune = ? WHERE Email = ?";
            ps = con.prepareStatement(query1);
            ps.setString(1, datiUtente.getNome());
            ps.setString(2, datiUtente.getCognome());
            ps.setDate(3, datiUtente.getDataNascita());
            ps.setString(4, datiUtente.getVia());
            ps.setInt(5, datiUtente.getCivico());
            ps.setString(6, datiUtente.getProvincia());
            ps.setString(7, datiUtente.getComune());
            ps.setString(8, datiUtente.getEmail());
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
     public DatiSensibiliBean recuperaDati (String email) {
         DatiSensibiliBean bean = new DatiSensibiliBean();
         Connection con = null;
         PreparedStatement ps = null;
         ResultSet rs = null;
         try {
             con = ds.getConnection();
             String query = "SELECT Email, Nome, Cognome FROM " + TABLE_NAME_DATI + " WHERE Email = ?";
             ps = con.prepareStatement(query);
             ps.setString(1, email);
             rs = ps.executeQuery();
             while(rs.next()) {
                bean.setEmail(email);
                bean.setNome(rs.getString("Nome"));
                bean.setCognome(rs.getString("Cognome"));
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
         return bean;
     }
}
