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
               <div class="imgOrdine"><div class="ordManga"><img src="img/imgManga/<%=((MangaBean) prodotto).getImg()%>" alt="errore immagine"></div></div>
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
                    <div class="slideshow">
                    <%
                        for(String immagine : ((PopBean) prodotto).getImmagini()){
                    %>
                    <img class="slide" src="img/imgPop/<%=immagine%>" alt="errore immagine">
                    <%
                        }
                    %>
                    </div>
                    <button class="prev">&#10094;</button>
                    <button class="next">&#10095;</button>
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
                    <div class="slideshow">
                    <%
                        for(String immagine : ((FigureBean) prodotto).getImmagini()){
                    %>
                    <img class="slide" src="img/imgFigure/<%=immagine%>" alt="errore immagine">
                    <%
                        }
                    %>
                    </div>
                    <button class="prev">&#10094;</button>
                    <button class="next">&#10095;</button>
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
