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

public class IndirizzoModel {
    private static Logger logger = Logger.getLogger(IndirizzoModel.class.getName());
    private static final String TABLE_NAME_INDIRIZZO = "IndirizzoSpedizione";
    private static final String TABLE_NAME_UTILIZZA = "Utilizza";
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

    public List<Integer> indirizziUtilizzati(String email) {
        List<Integer> indirizzi = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = ds.getConnection();
            String query = "SELECT IdIndirizzo FROM " + TABLE_NAME_UTILIZZA + " WHERE Email = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            while (rs.next()) {
                int i = rs.getInt("IdIndirizzo");
                indirizzi.add(i);
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
        return indirizzi;
    }

    public IndirizzoBean getIndirizzo (int i) throws SQLException {
        IndirizzoBean bean = new IndirizzoBean();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            String query = "SELECT * FROM " + TABLE_NAME_INDIRIZZO + " WHERE IdIndirizzo = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, i);
            rs = ps.executeQuery();
            while (rs.next()) {
                bean.setId(i);
                bean.setNome(rs.getString("Nome"));
                bean.setCognome(rs.getString("Cognome"));
                bean.setVia(rs.getString("Via"));
                bean.setCivico(rs.getInt("Civico"));
                bean.setProvincia(rs.getString("Provincia"));
                bean.setComune(rs.getString("Comune"));
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

    public void rimuoviIndirizzo(int i) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();
            String query = "DELETE FROM " + TABLE_NAME_INDIRIZZO + " WHERE IdIndirizzo = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, i);
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

    public void aggiungiIndirizzo(String nome, String cognome, String via, int civico, String provincia, String comune) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();
            String query = "INSERT INTO " + TABLE_NAME_INDIRIZZO + "(Nome, Cognome, Via, Civico, Provincia, Comune)" +
                    "VALUES(?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(query);
            ps.setString(1, nome);
            ps.setString(2, cognome);
            ps.setString(3, via);
            ps.setInt(4, civico);
            ps.setString(5, provincia);
            ps.setString(6, comune);
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

    public int getLastIndirizzo() {
        int i = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            String query = "SELECT Max(IdIndirizzo) AS Last FROM " + TABLE_NAME_INDIRIZZO;
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                i = rs.getInt("Last");
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

    public void aggiornaUtilizza(String email) {
        int indirizzo = getLastIndirizzo();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();
            String query = "INSERT INTO " + TABLE_NAME_UTILIZZA + "(IdIndirizzo, Email)" +
                    "VALUES(?, ?)";
            ps = con.prepareStatement(query);
            ps.setInt(1, indirizzo);
            ps.setString(2, email);
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
