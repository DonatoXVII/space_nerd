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
                    <%
                        for(String immagine : ((PopBean) prodotto).getImmagini()){
                    %>
                    <img src="img/imgPop/<%=immagine%>" alt="errore immagine">
                    <%
                        }
                    %>
                </div>

                <div class="dettagliProd">
                    <%=((PopBean) prodotto).getDescrizione()%><br>
                    <%=((PopBean) prodotto).getNumSerie()%><br>
                    <%=((PopBean) prodotto).getSerie()%><br>
                    <%=((PopBean) prodotto).getPrezzo()%><br>
                </div>
<%
            }else if(prodotto instanceof FigureBean) {
%>
                <div class="imgOrdine">
                    <%
                        for(String immagine : ((FigureBean) prodotto).getImmagini()){
                    %>
                    <img src="img/imgFigure/<%=immagine%>" alt="errore immagine">
                    <%
                        }
                    %>
                </div>
                <div class="dettagliProd">
                    <%=((FigureBean) prodotto).getDescrizione()%><br>
                    <%=((FigureBean) prodotto).getAltezza()%><br>
                    <%=((FigureBean) prodotto).getMateriale()%><br>
                    <%=((FigureBean) prodotto).getPersonaggio()%><br>
                    <%=((FigureBean) prodotto).getPrezzo()%><br>
                </div>
<%
            }
%>
            </div>
<%
        }
%>
<%
    }
%>
</div>
</body>
</html>
