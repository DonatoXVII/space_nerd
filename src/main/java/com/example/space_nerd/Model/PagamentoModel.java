package com.example.space_nerd.Model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PagamentoModel {
    private static Logger logger = Logger.getLogger(PagamentoModel.class.getName());
    private static final String TABLE_NAME_METODO = "MetodoPagamento";
    private static final String TABLE_NAME_REGISTRA = "Registra";

    private static final PagamentoModel pagamentoModel = new PagamentoModel();
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

    public List<Integer> metodiUtilizzati(String email) {
        List<Integer> metodi = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = ds.getConnection();
            String query = "SELECT IdMetodo FROM " + TABLE_NAME_METODO + " WHERE Email = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            while (rs.next()) {
                int i = rs.getInt("IdMetodo");
                metodi.add(i);
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
        return metodi;
    }

    public PagamentoBean getMetodo (int i) throws SQLException {
        PagamentoBean bean = new PagamentoBean();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            String query = "SELECT * FROM " + TABLE_NAME_METODO + " WHERE IdMetodo = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, i);
            rs = ps.executeQuery();
            while (rs.next()) {
                bean.setId(i);
                bean.setNumeroCarta(rs.getString("NumeroCarta"));
                bean.setScadenza(rs.getDate("DataScadenza"));
                bean.setCcv(rs.getInt("Ccv"));
                bean.setTitolare(rs.getString("Titolare"));
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

    public void rimuoviMetodo(int i) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();
            String query = "DELETE FROM " + TABLE_NAME_METODO + " WHERE IdMetodo = ?";
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

    public void aggiungiMetodo(String numero, Date scadenza, int ccv, String titolare) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();
            String query = "INSERT INTO " + TABLE_NAME_METODO + "(NumeroCarta, DataScadenza, Ccv, Titolare)" +
                    "VALUES(?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(query);
            ps.setString(1, numero);
            ps.setDate(2, scadenza);
            ps.setInt(3, ccv);
            ps.setString(4, titolare);
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

    public int getLastMetodo() {
        int i = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            String query = "SELECT Max(IdMetodo) AS Last FROM " + TABLE_NAME_METODO;
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

    public void aggiornaRegistra(String email) {
        int metodo = pagamentoModel.getLastMetodo();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ds.getConnection();
            String query = "INSERT INTO " + TABLE_NAME_REGISTRA + "(Email, IdMetodo)" +
                    "VALUES(?, ?)";
            ps = con.prepareStatement(query);
            ps.setString(1, email);
            ps.setInt(2, metodo);
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
