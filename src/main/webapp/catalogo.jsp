<%@ page import="java.util.List" %>
<%@ page import="com.example.space_nerd.Model.MangaBean" %>
<%@ page import="com.example.space_nerd.Model.PopBean" %>
<%@ page import="com.example.space_nerd.Model.FigureBean" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    List<Object> prodotti = (List<Object>) request.getAttribute("prodotti");
    List<String> imgPerPop = (List<String>) request.getAttribute("imgPop");
    List<String> imgPerFigure = (List<String>) request.getAttribute("imgFigure");
    if(prodotti == null) {
        response.sendRedirect("./ProdottoControl?action=visualizzaCatalogo");
        return;
    }
    int countPop = 0;
    int countFigure = 0;
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
                <a href="ProdottoControl?action=visualizzaDettagli&Tipo=pop&Id=<%=((PopBean) prodotto).getIdPop()%>"><img src="img/imgPop/<%=imgPerPop.get(countPop)%>" alt="errore immagine"></a>
                <div class="description"><%=((PopBean) prodotto).getDescrizione()%></div>
            </div>
            <%countPop++;%>
<%
        } else if(prodotto instanceof FigureBean) {
%>
            <div class="gallery">
                <a href="ProdottoControl?action=visualizzaDettagli&Tipo=figure&Id=<%=((FigureBean) prodotto).getIdFigure()%>"><img src="img/imgFigure/<%=imgPerFigure.get(countFigure)%>" alt="errore immagine"></a>
                <div class="description"><%=((FigureBean) prodotto).getPersonaggio()%></div>
            </div>
            <%countFigure++;%>
<%
        }
    }
%>
</div>
</body>
</html>
