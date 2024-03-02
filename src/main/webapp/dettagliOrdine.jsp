<%@ page import="java.util.List" %>
<%@ page import="com.example.space_nerd.model.MangaBean" %>
<%@ page import="com.example.space_nerd.model.PopBean" %>
<%@ page import="com.example.space_nerd.model.FigureBean" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    List<Object> prodottiOrdine = (List<Object>) request.getAttribute("prodottiOrdine");
    List<String> imgPop = (List<String>) request.getAttribute("imgPop");
    List<String> imgFigure = (List<String>) request.getAttribute("imgFigure");
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
                <div class="dettagliProd">
                    <%=((MangaBean) prodotto).getDescrizione()%><br>
                    <%=((MangaBean) prodotto).getCasaEditrice()%><br>
                    <%=((MangaBean) prodotto).getLingua()%><br>
                    <%=((MangaBean) prodotto).getNumPagine()%><br>
                    <%=((MangaBean) prodotto).getPrezzo()%><br>
                </div>
<%
            } else if(prodotto instanceof PopBean) {
%>
                <div class="imgOrdine">
                    <img src="img/imgPop/<%=imgPop.get(countPop)%>" alt="errore immagine">
                    <img src="img/imgPop/<%=imgPop.get(countPop+1)%>" alt="errore immagine">
                </div>

                <div class="dettagliProd">
                    <%=((PopBean) prodotto).getDescrizione()%><br>
                    <%=((PopBean) prodotto).getNumSerie()%><br>
                    <%=((PopBean) prodotto).getSerie()%><br>
                    <%=((PopBean) prodotto).getPrezzo()%><br>
                </div>
<%
                countPop+=2;
            }else if(prodotto instanceof FigureBean) {
%>
                <div class="imgOrdine">
                    <img src="img/imgFigure/<%=imgFigure.get(countFigure)%>" alt="errore immagine">
                    <img src="img/imgFigure/<%=imgFigure.get(countFigure+1)%>" alt="errore immagine">
                    <img src="img/imgFigure/<%=imgFigure.get(countFigure+2)%>" alt="errore immagine">
                </div>
                <div class="dettagliProd">
                    <%=((FigureBean) prodotto).getDescrizione()%><br>
                    <%=((FigureBean) prodotto).getAltezza()%><br>
                    <%=((FigureBean) prodotto).getMateriale()%><br>
                    <%=((FigureBean) prodotto).getPersonaggio()%><br>
                    <%=((FigureBean) prodotto).getPrezzo()%><br>
                </div>
<%
                countFigure+=3;
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
