<%@ page import="com.example.space_nerd.model.MangaBean" %>
<%@ page import="com.example.space_nerd.model.PopBean" %>
<%@ page import="com.example.space_nerd.model.FigureBean" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    Object prodotto = request.getAttribute("prodotto");
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

<%
    if(prodotto instanceof MangaBean){
%>
        <img src="img/imgManga/<%=((MangaBean) prodotto).getImg()%>" alt="errore immagine"><br>
        <%=((MangaBean) prodotto).getDescrizione()%><br>
        <%=((MangaBean) prodotto).getCasaEditrice()%><br>
        <%=((MangaBean) prodotto).getLingua()%><br>
        <%=((MangaBean) prodotto).getNumPagine()%><br>
        <%=((MangaBean) prodotto).getPrezzo()%><br>
        <a href="ProdottoControl?action=aggiungiAlCarrello&Tipo=manga&Id=<%=((MangaBean) prodotto).getIdManga()%>">Aggiungi al carrello</a>
<%
    } else if(prodotto instanceof PopBean){
        for(String img : ((PopBean) prodotto).getImmagini()) {
%>
        <img src="img/imgPop/<%=img%>" alt="errore immagine">
<%
        }
%>
        <%=((PopBean) prodotto).getDescrizione()%>
        <%=((PopBean) prodotto).getNumSerie()%>
        <%=((PopBean) prodotto).getSerie()%>
        <%=((PopBean) prodotto).getPrezzo()%>
        <a href="ProdottoControl?action=aggiungiAlCarrello&Tipo=pop&Id=<%=((PopBean) prodotto).getIdPop()%>">Aggiungi al carrello</a>
<%
    } else if(prodotto instanceof FigureBean) {
        for(String img : ((FigureBean) prodotto).getImmagini()) {
%>
        <img src="img/imgFigure/<%=img%>" alt="errore immagine">
<%
        }
%>
        <%=((FigureBean) prodotto).getDescrizione()%>
        <%=((FigureBean) prodotto).getAltezza()%>
        <%=((FigureBean) prodotto).getMateriale()%>
        <%=((FigureBean) prodotto).getPersonaggio()%>
        <%=((FigureBean) prodotto).getPrezzo()%>
        <a href="ProdottoControl?action=aggiungiAlCarrello&Tipo=figure&Id=<%=((FigureBean) prodotto).getIdFigure()%>">Aggiungi al carrello</a>
<%
    }
%>
</body>
</html>
