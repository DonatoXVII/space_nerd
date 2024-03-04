<%@ page import="com.example.space_nerd.model.IndirizzoBean" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.space_nerd.model.PagamentoBean" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    List<IndirizzoBean> indirizzi = (List<IndirizzoBean>) request.getAttribute("indirizzi");
    List<PagamentoBean> pagamenti = (List<PagamentoBean>) request.getAttribute("pagamenti");
    if(indirizzi == null || pagamenti == null) {
        response.sendRedirect("./OrdiniControl?action=visualizzaIndirizziEMetodi");
        return;
    }
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="charset=UTF-8">
    <title>Space Nerd</title>
    <link rel="stylesheet" type="text/css" href="css/carrello.css">
</head>
<body>
<%@include file="navbar.jsp"%>
<form action="OrdiniControl?action=checkout" method="POST">
    <%
        for(IndirizzoBean indirizzo : indirizzi) {
    %>
            <input type="radio" name="indirizzoScelto" value="<%=indirizzo.getId()%>" id="<%=indirizzo.getId()%>" required>
                <label><%=indirizzo.getNome()%> <%=indirizzo.getCognome()%> <%=indirizzo.getProvincia()%>
                    <%=indirizzo.getComune()%> <%=indirizzo.getVia()%> <%=indirizzo.getCivico()%>
                </label>
    <%
        }
    %>

    <%
        for(PagamentoBean pagamento : pagamenti) {
    %>
            <input type="radio" name="metodoScelto" value="<%=pagamento.getId()%>" id="<%=pagamento.getId()%>" required>
                <label><%=pagamento.getTitolare()%> <%=pagamento.getNumeroCarta()%> <%=pagamento.getScadenza()%>
                    <%=pagamento.getCcv()%>
                </label>
    <%
        }
    %>
    <button type="submit">Acquista</button>
</form>
</body>
</html>
