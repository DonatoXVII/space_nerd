<%@ page import="com.example.space_nerd.model.MangaBean" %>
<%@ page import="com.example.space_nerd.model.PopBean" %>
<%@ page import="com.example.space_nerd.model.FigureBean" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="charset=UTF-8">
    <title>Space Nerd</title>
    <link rel="stylesheet" type="text/css" href="css/carrello.css">
</head>
<body>
<%@include file="navbar.jsp"%>

<%
    if(carrelloBean != null && !carrelloBean.getListaCarrello().isEmpty()) {
%>
    <div class="carrello">
<%
        for(Object prodotto : carrelloBean.getListaCarrello()) {
            if(prodotto instanceof MangaBean){
%>
            <div class="prodottoCarrello">
                <img src="img/imgManga/<%=((MangaBean) prodotto).getImg()%>" alt="errore immagine">
                <div class="descrizioneCarrello">
                    <h1><%=((MangaBean) prodotto).getDescrizione()%></h1>
                    <p>Quantità: <%=((MangaBean) prodotto).getQuantitaCarrello()%></p>
                    <p><a href="ProdottoControl?action=aggiungiAlCarrello&Tipo=manga&Id=<%=((MangaBean) prodotto).getIdManga()%>">Aggiungi</a></p>
                    <p><a href="ProdottoControl?action=rimuoviDalCarrello&Id=<%=((MangaBean) prodotto).getIdManga()%>">Rimuovi</a></p>
                </div>
            </div>
<%
            } else if(prodotto instanceof PopBean){

%>
            <div class="prodottoCarrello">
                <img src="img/imgPop/<%=((PopBean) prodotto).getImmagini().get(0)%>" alt="errore immagine">
                <div class="descrizioneCarrello">
                    <h1><%=((PopBean) prodotto).getDescrizione()%></h1>
                    <p>Quantità: <%=((PopBean) prodotto).getQuantitaCarrello()%></p>
                    <p><a href="ProdottoControl?action=aggiungiAlCarrello&Tipo=pop&Id=<%=((PopBean) prodotto).getIdPop()%>">Aggiungi</a></p>
                    <p><a href="ProdottoControl?action=rimuoviDalCarrello&Id=<%=((PopBean) prodotto).getIdPop()%>">Rimuovi prodotto</a></p>
                </div>
            </div>
<%
            } else if (prodotto instanceof FigureBean) {

%>
            <div class="prodottoCarrello">
                <img src="img/imgFigure/<%=((FigureBean) prodotto).getImmagini().get(0)%>" alt="errore immagine">
                <div class="descrizioneCarrello">
                    <h1><%=((FigureBean) prodotto).getDescrizione()%></h1>
                    <p>Quantità: <%=((FigureBean) prodotto).getQuantitaCarrello()%></p>
                    <p><a href="ProdottoControl?action=aggiungiAlCarrello&Tipo=figure&Id=<%=((FigureBean) prodotto).getIdFigure()%>">Aggiungi</a></p>
                    <p><a href="ProdottoControl?action=rimuoviDalCarrello&Id=<%=((FigureBean) prodotto).getIdFigure()%>">Rimuovi prodotto</a></p>
                </div>
            </div>
<%
            }
        }
%>
<%
    if(emailUtente != null) {
%>
        <a href="./elaborazioneOrdine.jsp">Procedi al checkout</a>
<%
    } else {
%>
        <a href="./login.jsp">Procedi al checkout</a>
<%
    }
%>
        <a href="ProdottoControl?action=svuotaCarrello">Svuota carrello</a>
    </div>
<%
    } else {
%>
        <h1> Ancora nessun prodotto nel carrello</h1><img class="h1-img" src="img/triste.jpg" alt="errore immagine">
<%
    }
%>
</body>
</html>
