<%@ page import="java.util.List" %>
<%@ page import="com.example.space_nerd.Model.MangaBean" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.example.space_nerd.Model.PopBean" %>
<%@ page import="com.example.space_nerd.Model.FigureBean" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="ISO-8859-1">
    <title>Space Nerd</title>
    <link rel="stylesheet" type="text/css" href="css/index.css">
</head>
<body>
<%@include file="navbar.jsp"%>

<div class="welcome-container">
    <div class="slideshow">
        <img class="slide" src="img/index1.jpg" alt="errore immagine">
        <img class="slide" src="img/index2.jpg" alt="errore immagine">
        <img class="slide" src="img/index3.jpg" alt="errore immagine">
    </div>
    <button class="prev">&#10094;</button>
    <button class="next">&#10095;</button>
</div>

<div class="manga-container">
    <h2>Best Manga</h2>
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

<div class="pop-container">
    <h2>Best Pop</h2>
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

<div class="figure-container">
    <h2>Best Figure</h2>
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

<script>
    let slideIndex = 0;
    showSlides(slideIndex);

    function showSlides(index) {
        const slides = document.getElementsByClassName("slide");
        if (index >= slides.length) { slideIndex = 0; }
        if (index < 0) { slideIndex = slides.length - 1; }
        for (let i = 0; i < slides.length; i++) {
            slides[i].style.display = "none";
        }
        slides[slideIndex].style.display = "block";
    }

    function plusSlides(n) {
        showSlides(slideIndex += n);
    }

    document.querySelector(".prev").addEventListener("click", () => {
        plusSlides(-1);
    });

    document.querySelector(".next").addEventListener("click", () => {
        plusSlides(1);
    });
</script>

</body>
</html>