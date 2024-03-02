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
        for(Object prodotto : carrelloBean.getListaCarrello()) {
            if(prodotto instanceof MangaBean){
%>
                <%=((MangaBean) prodotto).getDescrizione()%>
                <a href="ProdottoControl?action=rimuoviDalCarrello&Tipo=manga&Id=<%=((MangaBean) prodotto).getIdManga()%>">Rimuovi prodotto</a>
<%
            } else if(prodotto instanceof PopBean){

%>
                <%=((PopBean) prodotto).getDescrizione()%>
                <a href="ProdottoControl?action=rimuoviDalCarrello&Tipo=pop&Id=<%=((PopBean) prodotto).getIdPop()%>">Rimuovi prodotto</a>
<%
            } else if (prodotto instanceof FigureBean) {

%>
                <%=((FigureBean) prodotto).getDescrizione()%>
                <a href="ProdottoControl?action=rimuoviDalCarrello&Tipo=figure&Id=<%=((FigureBean) prodotto).getIdFigure()%>">Rimuovi prodotto</a>
<%
            }
        }
%>
        <a href="ProdottoControl?action=checkout">Procedi al checkout</a>
        <a href="ProdottoControl?action=svuotaCarrello">Svuota carrello</a>
<%
    } else {
%>
        <h2> ancora nessun prodotto nel carrello</h2>
<%
    }
%>
</body>
</html>
