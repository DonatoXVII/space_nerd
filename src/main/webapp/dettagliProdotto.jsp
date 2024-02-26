<%@ page import="com.example.space_nerd.Model.MangaBean" %>
<%@ page import="com.example.space_nerd.Model.PopBean" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.space_nerd.Model.FigureBean" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    Object prodotto = request.getAttribute("prodotto");
    List<String> immaginiPop = (List<String>) request.getAttribute("immaginiPop");
    List<String> immaginiFigure = (List<String>) request.getAttribute("immaginiFigure");
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
        <a href="ProdottoControl?action=aggiungiMangaAlCarrello&IdManga=<%=((MangaBean) prodotto).getIdManga()%>">Aggiungi al carrello</a>
<%
    } else if(prodotto instanceof PopBean){
        for(String img : immaginiPop) {
%>
        <img src="img/imgPop/<%=img%>" alt="errore immagine">
<%
        }
%>
        <%=((PopBean) prodotto).getDescrizione()%>
        <%=((PopBean) prodotto).getNumSerie()%>
        <%=((PopBean) prodotto).getSerie()%>
        <%=((PopBean) prodotto).getPrezzo()%>
        <a href="ProdottoControl?action=aggiungiPopAlCarrello&IdPop=<%=((PopBean) prodotto).getIdPop()%>">Aggiungi al carrello</a>
<%
    } else if(prodotto instanceof FigureBean) {
        for(String img : immaginiFigure) {
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
        <a href="ProdottoControl?action=aggiungiFigureAlCarrello&IdFigure=<%=((FigureBean) prodotto).getIdFigure()%>">Aggiungi al carrello</a>
<%
    }
%>
</body>
</html>
