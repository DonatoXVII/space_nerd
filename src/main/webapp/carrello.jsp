<%@ page import="com.example.space_nerd.Model.MangaBean" %>
<%@ page import="com.example.space_nerd.Model.PopBean" %>
<%@ page import="com.example.space_nerd.Model.FigureBean" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="charset=UTF-8">
    <title>Space Nerd</title>
</head>
<body>
<%@include file="navbar.jsp"%>

<%
    if(carrelloBean != null && !carrelloBean.getListaCarrello().isEmpty()) {
        for(Object prodotto : carrelloBean.getListaCarrello()) {
            if(prodotto instanceof MangaBean){
%>
                <%=((MangaBean) prodotto).getDescrizione()%>
                <a href="ProdottoControl?action=rimuoviMangaDalCarrello&IdManga=<%=((MangaBean) prodotto).getIdManga()%>">Rimuovi prodotto</a>
<%
            } else if(prodotto instanceof PopBean){

%>
                <%=((PopBean) prodotto).getDescrizione()%>
                <a href="ProdottoControl?action=rimuoviPopDalCarrello&IdPop=<%=((PopBean) prodotto).getIdPop()%>">Rimuovi prodotto</a>
<%
            } else if (prodotto instanceof FigureBean) {

%>
                <%=((FigureBean) prodotto).getDescrizione()%>
                <a href="ProdottoControl?action=rimuoviFigureDalCarrello&IdFigure=<%=((FigureBean) prodotto).getIdFigure()%>">Rimuovi prodotto</a>
<%
            }
        }
%>
        <a href="ProdottoControl?action=checkout">Procedi al checkout</a>
<%
    } else {
%>
        <h2> ancora nessun prodotto nel carrello</h2>
<%
    }
%>
</body>
</html>
