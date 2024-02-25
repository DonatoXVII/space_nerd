<%@ page import="java.util.List" %>
<%@ page import="com.example.space_nerd.Model.MangaBean" %>
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
<%
            }
        }
    } else {
%>
        <h2> ancora nessun prodotto nel carrello</h2>
<%
    }
%>
</body>
</html>
