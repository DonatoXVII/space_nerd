<%@ page import="java.util.List" %>
<%@ page import="com.example.space_nerd.model.MangaBean" %>
<%@ page import="com.example.space_nerd.model.PopBean" %>
<%@ page import="com.example.space_nerd.model.FigureBean" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    List<Object> prodotti = (List<Object>) request.getAttribute("prodotti");
    if(prodotti == null) {
        response.sendRedirect("./ProdottoControl?action=visualizzaCatalogo");
        return;
    }
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
<div class="catalogo">
<%
    for(Object prodotto : prodotti) {
        if(prodotto instanceof MangaBean) {
%>
            <div class="gallery">
                <a href="ProdottoControl?action=visualizzaDettagli&Tipo=manga&Id=<%=((MangaBean) prodotto).getIdManga()%>"><img src="img/imgManga/<%=((MangaBean) prodotto).getImg()%>" alt="errore immagine"></a>
                <div class="description"><%=((MangaBean) prodotto).getDescrizione()%></div>
            </div>

<%
        } else if(prodotto instanceof PopBean) {
%>
            <div class="gallery">
                <a href="ProdottoControl?action=visualizzaDettagli&Tipo=pop&Id=<%=((PopBean) prodotto).getIdPop()%>"><img src="img/imgPop/<%=((PopBean) prodotto).getImmagini().get(0)%>" alt="errore immagine"></a>
                <div class="description"><%=((PopBean) prodotto).getDescrizione()%></div>
            </div>
<%
        } else if(prodotto instanceof FigureBean) {
%>
            <div class="gallery">
                <a href="ProdottoControl?action=visualizzaDettagli&Tipo=figure&Id=<%=((FigureBean) prodotto).getIdFigure()%>"><img src="img/imgFigure/<%=((FigureBean) prodotto).getImmagini().get(0)%>" alt="errore immagine"></a>
                <div class="description"><%=((FigureBean) prodotto).getPersonaggio()%></div>
            </div>
<%
        }
    }
%>
</div>
</body>
</html>
