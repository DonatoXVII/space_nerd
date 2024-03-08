        <%@ page import="com.example.space_nerd.model.IndirizzoBean" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.space_nerd.model.PagamentoBean" %>
        <%@ page import="java.util.Locale" %>
        <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    List<IndirizzoBean> indirizzi = (List<IndirizzoBean>) request.getAttribute("indirizzi");
    List<PagamentoBean> pagamenti = (List<PagamentoBean>) request.getAttribute("pagamenti");
    if (indirizzi == null || pagamenti == null) {
        response.sendRedirect("./OrdiniControl?action=visualizzaIndirizziEMetodi");
        return;
    }
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="charset=UTF-8">
    <title>Space Nerd</title>
    <link rel="stylesheet" type="text/css" href="css/checkout.css">
</head>
<body>
<%@include file="navbar.jsp"%>

<%
    Object prezzoTotaleObj = session.getAttribute("PrezzoTotale");
    float totale = 0.0f;
    if (prezzoTotaleObj instanceof String) {
        totale = Float.parseFloat((String) prezzoTotaleObj);
    }
%>
<%
    Locale.setDefault(Locale.US);
    String PrezzoeString = String.format("%.2f", totale);
    Locale.setDefault(Locale.ITALY);
%>

<%
    Locale.setDefault(Locale.US);
    String PrezzoTotaleString = String.format("%.2f", totale+5);
    Locale.setDefault(Locale.ITALY);
%>
<form action="OrdiniControl?action=checkout" method="POST">
<div class="container">
    <div class="cardCheckout cart">
        <label class="titleCardOrdine">ELABORAZIONE ORDINE</label>
        <div class="steps">
            <div class="step">
                <div>
                    <span>INDIRIZZO DI SPEDIZIONE</span>
                    <div class="indirizziOrdine">
                    <%
                        for (IndirizzoBean indirizzo : indirizzi) {
                    %>
                        <div>
                        <label for="<%=indirizzo.getId()%>"></label>
                            <input type="radio" name="indirizzoScelto" value="<%=indirizzo.getId()%>" id="
                    <%=indirizzo.getId()%>" required>
                                <%=indirizzo.getNome()%> <%=indirizzo.getCognome()%><br>
                                <%=indirizzo.getComune()%>, <%=indirizzo.getProvincia()%><br>
                                Via <%=indirizzo.getVia()%>, <%=indirizzo.getCivico()%><br>
                        </div>
                    <%
                        }
                    %>
                    </div>
                </div>
                <hr>
                <div>
                    <span>METODO DI PAGAMENTO</span>
                    <div class="metodiOrdine">
                    <%
                        for (PagamentoBean pagamento : pagamenti) {
                    %>
                        <div>
                            <label for="<%=pagamento.getId()%>"></label>
                                <input type="radio" name="metodoScelto" value="<%=pagamento.getId()%>" id="<%=pagamento.getId()%>" required>
                                    <%=pagamento.getTitolare()%><br>
                                    <%=pagamento.getNumeroCarta()%><br>
                                    <%=pagamento.getScadenza()%><br>
                                    <%=pagamento.getCcv()%>
                        </div>
                    <%
                        }
                    %>
                    </div>
                </div>
                <hr>
                <div class="promo">
                    <span>CI SIAMO QUASI!</span>
                </div>
                <div class="payments">
                    <span>Resoconto</span>
                    <div class="details">
                        <span>Parziale:</span>
                        <span><%=PrezzoeString%>€</span>
                        <span>Spedizione:</span>
                        <span>5.00€</span>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="card checkout">
        <div class="footer">
            <label class="price"><%=PrezzoTotaleString%>€</label>
            <button class="checkout-btn">Conferma</button>
        </div>
    </div>
</div>
</form>
</body>
</html>
