<%@ page import="java.util.List" %>
<%@ page import="com.example.space_nerd.Model.MangaBean" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.example.space_nerd.Model.PopBean" %>
<%@ page import="com.example.space_nerd.Model.PopModel" %>
<%@ page import="com.example.space_nerd.Model.FigureBean" %>
<%@ page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
    List<MangaBean> bestManga = (List<MangaBean>) request.getAttribute("bestManga");
    if(bestManga == null) {
        response.sendRedirect("./ProdottoControl");
        return;
    }
    List<PopBean> bestPop = (List<PopBean>) request.getAttribute("bestPop");
    List<String> immaginiPop = (List<String>) request.getAttribute("immaginiPop");
    if(bestPop == null) {
        response.sendRedirect("./ProdottoControl");
        return;
    }
    List<FigureBean> bestFigure = (List<FigureBean>) request.getAttribute("bestFigure");
    List<String> immaginiFigure = (List<String>) request.getAttribute("immaginiFigure");
    if(bestFigure == null) {
        response.sendRedirect("./ProdottoControl");
        return;
    }
%>
<%!
    String email = "";
    Boolean tipo = false;
%>
<%
    synchronized (session) {
        session = request.getSession();
        email = (String) session.getAttribute("email");
        tipo = (Boolean) session.getAttribute("tipo");
    }
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="ISO-8859-1">
    <title>Space Nerd</title>
    <link rel="stylesheet" type="text/css" href="css/index.css">
</head>
<body>
<%@include file="navbar.jsp"%>

<h2>Best Manga</h2>
<div class="manga-container">
<%
    if(bestManga != null && bestManga.size()!=0) {
        Iterator<?> it = bestManga.iterator();
        while (it.hasNext()) {
            MangaBean manga = (MangaBean) it.next();
%>
<%
            if(manga != null) {
%>
            <div class="manga-item">
                <img src="img/imgManga/<%=manga.getImg()%>" alt="errore immagine">
                <%=manga.getDescrizione()%>
            </div>

<%
            }
%>
<%
            }
    }
%>
</div>

<h2>Best Pop</h2>
<div class="pop-container">
    <%
        if(bestPop != null && bestPop.size()!=0) {
            Iterator<?> it = bestPop.iterator();
            Iterator<?> i = immaginiPop.iterator();
            while (it.hasNext()) {
                PopBean pop = (PopBean) it.next();
                String img = (String) i.next();
    %>
    <%
        if(pop != null) {
    %>
    <div class="pop-item">
        <img src="img/imgPop/<%=img%>" alt="errore immagine">
        <%=pop.getDescrizione()%>
    </div>

    <%
        }
    %>
    <%
            }
        }
    %>
</div>

<h2>Best Figure</h2>
<div class="figure-container">
    <%
        if(bestFigure != null && bestFigure.size()!=0) {
            Iterator<?> it = bestFigure.iterator();
            Iterator<?> i = immaginiFigure.iterator();
            while (it.hasNext()) {
                FigureBean figure = (FigureBean) it.next();
                String img = (String) i.next();
    %>
    <%
        if(figure != null) {
    %>
    <div class="figure-item">
        <img src="img/imgFigure/<%=img%>" alt="errore immagine">
        <%=figure.getDescrizione()%>
    </div>

    <%
        }
    %>
    <%
            }
        }
    %>
</div>

</body>
</html>