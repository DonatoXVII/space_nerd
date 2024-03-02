<%@ page import="java.util.List" %>
<%@ page import="com.example.space_nerd.model.OrdineBean" %>
<%@ page import="com.example.space_nerd.model.MangaBean" %>
<%@ page import="com.example.space_nerd.model.PopBean" %>
<%@ page import="com.example.space_nerd.model.FigureBean" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    List<Object> prodottiOrdine = (List<Object>) request.getAttribute("prodottiOrdine");
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="charset=UTF-8">
    <title>Space Nerd</title>
</head>
<body>
<%@include file="navbar.jsp"%>

<%
    if(prodottiOrdine != null && !prodottiOrdine.isEmpty()) {
        for(Object prodotto : prodottiOrdine) {
            if(prodotto instanceof MangaBean) {
%>
        <%=((MangaBean) prodotto).getDescrizione()%>
<%
            } else if(prodotto instanceof PopBean) {
%>
        <%=((PopBean) prodotto).getDescrizione()%>
<%
            }else if(prodotto instanceof FigureBean) {
%>
        <%=((FigureBean) prodotto).getDescrizione()%>
<%
            }
        }
    }
%>

</body>
</html>
