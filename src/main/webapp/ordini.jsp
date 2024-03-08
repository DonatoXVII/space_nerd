<%@ page import="java.util.List" %>
<%@ page import="com.example.space_nerd.model.OrdineBean" %>
<%@ page import="java.util.Iterator" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    List<OrdineBean> ordini = (List<OrdineBean>) request.getAttribute("ordini");
    if(ordini == null) {
        response.sendRedirect("./OrdiniControl?action=visualizzaOrdini");
        return;
    }
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="charset=UTF-8">
    <title>Space Nerd</title>
    <link href="css/utility.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@include file="navbar.jsp"%>
<style>
    body {
       background: #f1f2f4;
    }
</style>

<div class="ordini-container">
    <h1>I tuoi ordini</h1>
    <%
        if(ordini != null && ordini.size()!=0) {
            Iterator<?> it = ordini.iterator();
            while (it.hasNext()) {
                OrdineBean ordine = (OrdineBean) it.next();
    %>
    <%
        if(ordine != null) {
    %>

    <div class="cardOrdine">
        <div class="contentOrdine">
            <p class="heading">ORDINE DEL <%=ordine.getData()%>
            </p><p class="para">
            Prezzo totale dell' ordine : <%=ordine.getPrezzo()%>€<br>
            Qui puoi visualizzare i dettagli di questo ordine e scaricarne la fattura
        </p>
            <button class="btnOrdine" onclick="location.href='OrdiniControl?action=visualizzaDettagliOrdine&IdOrdine=<%=ordine.getId()%>'">Dettagli ordine</button>
        </div>
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
