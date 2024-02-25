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
    <link href="css/index.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@include file="navbar.jsp"%>
<%
    for(Object prodotto : prodotti) {
        if(prodotto instanceof MangaBean) {
%>
    <%=((MangaBean) prodotto).getDescrizione()%><br>
    <img src="img/imgManga/<%=((MangaBean) prodotto).getImg()%>" alt="errore immagine">
<%
        } else if(prodotto instanceof PopBean) {
%>
    <%=((PopBean) prodotto).getDescrizione()%><br>
    <img src="img/imgPop/<%=imgPerPop.get(countPop)%>" alt="errore immagine">
    <%countPop++;%>
<%
        } else if(prodotto instanceof FigureBean) {
%>
    <%=((FigureBean) prodotto).getPersonaggio()%><br>
    <img src="img/imgFigure/<%=imgPerFigure.get(countFigure)%>" alt="errore immagine">
    <%countFigure++;%>
<%
        }
    }
%>
</body>
</html>
