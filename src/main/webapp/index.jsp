<%@ page import="java.util.List" %>
<%@ page import="com.example.space_nerd.Model.MangaBean" %>
<%@ page import="com.example.space_nerd.Model.PopBean" %>
<%@ page import="com.example.space_nerd.Model.FigureBean" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    List<Object> bestProdotti = (List<Object>) request.getAttribute("bestProdotti");
    if(bestProdotti == null) {
        response.sendRedirect("./ProdottoControl");
        return;
    }

    List<String> immaginiPop = (List<String>) request.getAttribute("immaginiPop");
    List<String> immaginiFigure = (List<String>) request.getAttribute("immaginiFigure");
    int countPop = 0;
    int countFigure = 0;
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

<div class="prodotti-container">
    <div class="manga-container">
        <h2>Manga più venduti</h2>
            <div class="manga-items">
                <%
                    for(Object prodotto : bestProdotti){
                        if(prodotto instanceof MangaBean) {
                %>

                            <a href="ProdottoControl?action=visualizzaDettagli&Tipo=manga&Id=<%=((MangaBean) prodotto).getIdManga()%>"><div class="manga-item">
                                <img src="img/imgManga/<%=((MangaBean) prodotto).getImg()%>" alt="errore immagine">
                                <div class="description">
                                    <%=((MangaBean) prodotto).getDescrizione()%>
                                </div>
                            </div></a>
                <%
                        }
                    }
                %>
            </div>
    </div>

    <div class="pop-container">
        <h2>Pop più venduti</h2>
            <div class="pop-items">
                <%
                    for(Object prodotto : bestProdotti) {
                        if(prodotto instanceof PopBean) {
                            String img = immaginiPop.get(countPop);
                            countPop++;
                %>
                                <a href="ProdottoControl?action=visualizzaDettagli&Tipo=pop&Id=<%=((PopBean) prodotto).getIdPop()%>"><div class="pop-item">
                                <img src="img/imgPop/<%=img%>" alt="errore immagine">
                                <div class="description">
                                    <%=((PopBean) prodotto).getDescrizione()%>
                                </div>
                                </div></a>
                <%
                        }
                    }
                %>
            </div>
    </div>


    <div class="figure-container">
        <h2>Action figure più vendute</h2>
            <div class="figure-items">
                <%
                    for(Object prodotto : bestProdotti){
                        if(prodotto instanceof FigureBean) {
                            String img = immaginiFigure.get(countFigure);
                            countFigure++;
                %>

                                <a href="ProdottoControl?action=visualizzaDettagli&Tipo=figure&Id=<%=((FigureBean) prodotto).getIdFigure()%>"><div class="figure-item">
                                <img src="img/imgFigure/<%=img%>" alt="errore immagine">
                                <div class="description">
                                    <%=((FigureBean) prodotto).getDescrizione()%>
                                </div>
                                </div></a>
                <%
                        }
                    }
                %>
            </div>
    </div>
</div>

<script>
    function initSlideshows() {
        const slideshows = document.querySelectorAll(".slideshow");

        slideshows.forEach(slideshow => {
            const slides = slideshow.querySelectorAll(".slide");
            let slideIndex = 0;

            function showSlides(index) {
                if (index >= slides.length) { slideIndex = 0; }
                if (index < 0) { slideIndex = slides.length - 1; }

                slides.forEach((slide, i) => {
                    if (i === slideIndex) {
                        slide.style.display = "block";
                    } else {
                        slide.style.display = "none";
                    }
                });

                const descriptions = slideshow.querySelectorAll(".description");
                descriptions.forEach((desc, i) => {
                    if (i === slideIndex) {
                        desc.style.display = "block";
                    } else {
                        desc.style.display = "none";
                    }
                });

                const images = slideshow.querySelectorAll(".slide img");
                images.forEach((img, i) => {
                    // Aggiunta: Centra l'immagine all'interno dello slide
                    img.style.display = "block";
                    img.style.margin = "auto";
                    img.style.maxWidth = "100%";
                    img.style.maxHeight = "100%";
                });
            }

            function plusSlides(n) {
                showSlides(slideIndex += n);
            }

            showSlides(slideIndex);

            const prevBtn = slideshow.parentElement.querySelector(".prev");
            const nextBtn = slideshow.parentElement.querySelector(".next");

            prevBtn.addEventListener("click", () => {
                plusSlides(-1);
            });

            nextBtn.addEventListener("click", () => {
                plusSlides(1);
            });
        });
    }

    // Chiama la funzione per inizializzare gli slideshow
    initSlideshows();
</script>

</body>
</html>