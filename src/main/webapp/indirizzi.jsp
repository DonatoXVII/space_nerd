<%@ page import="com.example.space_nerd.Model.IndirizzoBean" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    List<IndirizzoBean> indirizzi = (List<IndirizzoBean>) request.getAttribute("indirizzi");
    if(indirizzi == null) {
        response.sendRedirect("./UtenteControl?action=visualizzaIndirizzi");
        return;
    }
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="charset=UTF-8">
    <title>Space Nerd</title>
    <link href="css/index.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@include file="navbar.jsp"%>

<div class="indirizzi-container">
    <h2>I tuoi indirizzi</h2>
    <%
        if(indirizzi != null && indirizzi.size()!=0) {
            Iterator<?> it = indirizzi.iterator();
            while (it.hasNext()) {
                IndirizzoBean indirizzo = (IndirizzoBean) it.next();
    %>
    <%
        if(indirizzo != null) {
    %>
    <div class="indirizzo-item">
        <%=indirizzo.getNome()%>
        <%=indirizzo.getCognome()%>
        <%=indirizzo.getVia()%>
        <%=indirizzo.getCivico()%>
        <%=indirizzo.getProvincia()%>
        <%=indirizzo.getComune()%>
        <a href="UtenteControl?action=rimuoviIndirizzo&IdIndirizzo=<%=indirizzo.getId()%>">Rimuovi</a>
    </div>

    <%
        }
    %>
    <%
            }
        }
    %>
</div>

</body>
</html>
