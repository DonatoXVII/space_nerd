<%@ page import="java.util.List" %>
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
    <link href="css/catalogo.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@include file="navbar.jsp"%>

<div class="dettagliOrdine">
<%
    if(prodottiOrdine != null && !prodottiOrdine.isEmpty()) {
        for(Object prodotto : prodottiOrdine) {

%>
            <div class = "prodottoOrdine">
<%
            if(prodotto instanceof MangaBean) {
%>
                <div class="imgOrdine"><img src="img/imgManga/<%=((MangaBean) prodotto).getImg()%>"></div>
                <div class="dettagliProd"><%=((MangaBean) prodotto).getDescrizione()%></div>
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
%></div>
<%
        }
%>
<%
    }
%>
</div>
</body>
</html>
