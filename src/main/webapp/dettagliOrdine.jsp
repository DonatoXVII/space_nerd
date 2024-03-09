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

<div class="catalogoOrdini">
<%
    if(prodottiOrdine != null && !prodottiOrdine.isEmpty()) {
        for(Object prodotto : prodottiOrdine) {

%>
<%
            if(prodotto instanceof MangaBean) {
%>
               <button style="cursor: pointer" class="gallery" onclick="location.href='ProdottoControl?action=visualizzaDettagli&Tipo=manga&Id=<%=((MangaBean) prodotto).getIdManga()%>'" role="button">
                   <img src="img/imgManga/<%=((MangaBean) prodotto).getImg()%>" alt="errore immagine">
                   <div class="description">
                       <h5><%=((MangaBean) prodotto).getDescrizione()%></h5>
                       <h5><%=((MangaBean) prodotto).getCasaEditrice()%></h5>
                       <h5><%=((MangaBean) prodotto).getLingua()%></h5>
                       <h5>Numero pagine: <%=((MangaBean) prodotto).getNumPagine()%></h5>
                       <h5>Prezzo unità: <%=((MangaBean) prodotto).getPrezzo()%>€</h5>
                       <h5>Quantità: <%=((MangaBean) prodotto).getQuantitaCarrello()%></h5>
                    </div>
               </button>
<%
            } else if(prodotto instanceof PopBean) {
%>
                <button style="cursor: pointer" class="gallery" onclick="location.href='ProdottoControl?action=visualizzaDettagli&Tipo=pop&Id=<%=((PopBean) prodotto).getIdPop()%>'" role="button">
                    <img src="img/imgPop/<%=((PopBean) prodotto).getImmagini().get(0)%>" alt="errore immagine">
                    <div class="description">
                        <h5><%=((PopBean) prodotto).getDescrizione()%></h5>
                        <h5>Seriale: <%=((PopBean) prodotto).getNumSerie()%></h5>
                        <h5>Anime: <%=((PopBean) prodotto).getSerie()%></h5>
                        <h5>Prezzo unità: <%=((PopBean) prodotto).getPrezzo()%>€</h5>
                        <h5>Quantità: <%=((PopBean) prodotto).getQuantitaCarrello()%></h5>
                    </div>
                </button>
<%
            }else if(prodotto instanceof FigureBean) {
%>
                <button style="cursor: pointer" class="gallery" onclick="location.href='ProdottoControl?action=visualizzaDettagli&Tipo=figure&Id=<%=((FigureBean) prodotto).getIdFigure()%>'" role="button">
                    <img src="img/imgFigure/<%=((FigureBean) prodotto).getImmagini().get(0)%>" alt="errore immagine">
                    <div class="description">
                        <h5><%=((FigureBean) prodotto).getDescrizione()%></h5>
                        <h5><%=((FigureBean) prodotto).getAltezza()%> cm</h5>
                        <h5>Materiale: <%=((FigureBean) prodotto).getMateriale()%></h5>
                        <h5><%=((FigureBean) prodotto).getPersonaggio()%></h5>
                        <h5>Prezzo unità: <%=((FigureBean) prodotto).getPrezzo()%>€</h5>
                        <h5>Quantità: <%=((FigureBean) prodotto).getQuantitaCarrello()%></h5>
                    </div>
                </button>
<%
            }
%>
<%
        }
%>
<%
    }
%>

</div>
</body>
</html>
