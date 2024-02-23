<%@ page import="java.util.List" %>
<%@ page import="com.example.space_nerd.Model.OrdineBean" %>
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
    <link href="css/index.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@include file="navbar.jsp"%>

<div class="ordini-container">
    <h2>I tuoi ordini</h2>
    <%
        if(ordini != null && ordini.size()!=0) {
            Iterator<?> it = ordini.iterator();
            while (it.hasNext()) {
                OrdineBean ordine = (OrdineBean) it.next();
    %>
    <%
        if(ordine != null) {
    %>
    <div class="ordine-item">
        <%=ordine.getFattura()%>
        <%=ordine.getPrezzo()%>
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
