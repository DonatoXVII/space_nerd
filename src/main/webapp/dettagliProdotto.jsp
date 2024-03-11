<%@ page import="com.example.space_nerd.model.MangaBean" %>
<%@ page import="com.example.space_nerd.model.PopBean" %>
<%@ page import="com.example.space_nerd.model.FigureBean" %>
<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    Object prodotto = request.getAttribute("prodotto");
%>
<%
    Locale.setDefault(Locale.US);
    String PrezzoeString;
    Locale.setDefault(Locale.ITALY);
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

<section id="dettagliProdotto">
<%
    if(prodotto instanceof MangaBean){
%>
        <div class="galleryProdotto">
            <img src="img/imgManga/<%=((MangaBean) prodotto).getImg()%>" alt="errore immagine"><br>
        </div>
        <div class="descrizioneProdotto">
            <h1><%=((MangaBean) prodotto).getDescrizione()%></h1>
            <p><img src="img/editrice.jpg" alt="errore immagine">  Casa Editrice: <%=((MangaBean) prodotto).getCasaEditrice()%></p>
            <p><img src="img/lingua.jpg" alt="errore immagine">  Lingua: <%=((MangaBean) prodotto).getLingua()%></p>
            <p><img src="img/libro.jpg" alt="errore immagine">  Numero Pagine: <%=((MangaBean) prodotto).getNumPagine()%></p>
            <%
                PrezzoeString = String.format("%.2f", ((MangaBean) prodotto).getPrezzo());
            %>
            <p><img src="img/prezzo.jpg" alt="errore immagine">  Prezzo : <%=PrezzoeString%>€</p>
            <button class="addToCart" <%if(((MangaBean) prodotto).getNumArticoli() > 0) {%>onclick="location.href='ProdottoControl?action=aggiungiAlCarrello&Tipo=manga&Id=<%=((MangaBean) prodotto).getIdManga()%>'"<%}%>>
                <span>Aggiungi al carrello</span>
                <svg fill="#fff" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><g stroke-width="0" id="SVGRepo_bgCarrier"></g>
                    <g stroke-linejoin="round" stroke-linecap="round" id="SVGRepo_tracerCarrier"></g>
                    <g id="SVGRepo_iconCarrier"> <defs>  </defs> <g id="cart">
                        <circle r="1.91" cy="20.59" cx="10.07" class="cls-1"></circle>
                        <circle r="1.91" cy="20.59" cx="18.66" class="cls-1"></circle>
                        <path d="M.52,1.5H3.18a2.87,2.87,0,0,1,2.74,2L9.11,13.91H8.64A2.39,2.39,0,0,0,6.25,16.3h0a2.39,2.39,0,0,0,2.39,2.38h10" class="cls-1"></path>
                        <polyline points="7.21 5.32 22.48 5.32 22.48 7.23 20.57 13.91 9.11 13.91" class="cls-1"></polyline>
                    </g>
                    </g>
                </svg>
            </button>
        </div>
<%
    } else if(prodotto instanceof PopBean){
%>
    <div class="slider-wrapper">
        <div class="slider">
<%
        for(String img : ((PopBean) prodotto).getImmagini()) {
%>
            <img src="img/imgPop/<%=img%>" alt="errore immagine">
<%
        }
%>
        </div>
        <div class="gallerySlider-nav">
            <button class="gallerySlider-nav-btn" onclick="prevSlide()">&#10094;</button>
            <button class="gallerySlider-nav-btn" onclick="nextSlide()">&#10095;</button>
        </div>
    </div>
    <div class="descrizioneProdotto">
        <h1><%=((PopBean) prodotto).getDescrizione()%></h1>
        <p><img src="img/serie.jpg" alt="errore immagine">  Serie: <%=((PopBean) prodotto).getSerie()%></p>
        <p><img src="img/numeri.jpg" alt="errore immagine">  Numero di serie: <%=((PopBean) prodotto).getNumSerie()%></p>
        <%
            PrezzoeString = String.format("%.2f", ((PopBean) prodotto).getPrezzo());
        %>
        <p><img src="img/prezzo.jpg" alt="errore immagine">  Prezzo : <%=PrezzoeString%>€</p>
        <button class="addToCart" <%if(((PopBean) prodotto).getNumArticoli() > 0) {%>onclick="location.href='ProdottoControl?action=aggiungiAlCarrello&Tipo=pop&Id=<%=((PopBean) prodotto).getIdPop()%>'"<%}%>>
            <span>Aggiungi al carrello</span>
            <svg fill="#fff" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><g stroke-width="0" id="SVGRepo_bgCarrier"></g>
                <g stroke-linejoin="round" stroke-linecap="round" id="SVGRepo_tracerCarrier"></g>
                <g id="SVGRepo_iconCarrier"> <defs>  </defs> <g id="cart">
                    <circle r="1.91" cy="20.59" cx="10.07" class="cls-1"></circle>
                    <circle r="1.91" cy="20.59" cx="18.66" class="cls-1"></circle>
                    <path d="M.52,1.5H3.18a2.87,2.87,0,0,1,2.74,2L9.11,13.91H8.64A2.39,2.39,0,0,0,6.25,16.3h0a2.39,2.39,0,0,0,2.39,2.38h10" class="cls-1"></path>
                    <polyline points="7.21 5.32 22.48 5.32 22.48 7.23 20.57 13.91 9.11 13.91" class="cls-1"></polyline>
                </g>
                </g>
            </svg>
        </button>
    </div>
<%
    } else if(prodotto instanceof FigureBean) {
%>
    <div class="slider-wrapper">
        <div class="slider">
<%
        for(String img : ((FigureBean) prodotto).getImmagini()) {
%>
        <img src="img/imgFigure/<%=img%>" alt="errore immagine">
<%
        }
%>
        </div>
        <div class="gallerySlider-nav">
            <button class="gallerySlider-nav-btn" onclick="prevSlide()">&#10094;</button>
            <button class="gallerySlider-nav-btn" onclick="nextSlide()">&#10095;</button>
        </div>
    </div>
    <div class="descrizioneProdotto">
        <h1><%=((FigureBean) prodotto).getDescrizione()%></h1>
        <p><img src="img/personaggio.jpg" alt="errore immagine">  Personaggio : <%=((FigureBean) prodotto).getPersonaggio()%></p>
        <p><img src="img/altezza.jpg" alt="errore immagine">  Altezza : <%=((FigureBean) prodotto).getAltezza()%></p>
        <p><img src="img/materiale.jpg" alt="errore immagine">  Materiale : <%=((FigureBean) prodotto).getMateriale()%></p>
        <%
            PrezzoeString = String.format("%.2f", ((FigureBean) prodotto).getPrezzo());
        %>
        <p><img src="img/prezzo.jpg" alt="errore immagine">  Prezzo : <%=PrezzoeString%>€</p>
        <button class="addToCart" <%if(((FigureBean) prodotto).getNumArticoli() > 0) {%>onclick="location.href='ProdottoControl?action=aggiungiAlCarrello&Tipo=figure&Id=<%=((FigureBean) prodotto).getIdFigure()%>'"<%}%>>
            <span>Aggiungi al carrello</span>
            <svg fill="#fff" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><g stroke-width="0" id="SVGRepo_bgCarrier"></g>
                <g stroke-linejoin="round" stroke-linecap="round" id="SVGRepo_tracerCarrier"></g>
                <g id="SVGRepo_iconCarrier"> <defs>  </defs> <g id="cart">
                    <circle r="1.91" cy="20.59" cx="10.07" class="cls-1"></circle>
                    <circle r="1.91" cy="20.59" cx="18.66" class="cls-1"></circle>
                    <path d="M.52,1.5H3.18a2.87,2.87,0,0,1,2.74,2L9.11,13.91H8.64A2.39,2.39,0,0,0,6.25,16.3h0a2.39,2.39,0,0,0,2.39,2.38h10" class="cls-1"></path>
                    <polyline points="7.21 5.32 22.48 5.32 22.48 7.23 20.57 13.91 9.11 13.91" class="cls-1"></polyline>
                </g>
                </g>
            </svg>
        </button>
    </div>
<%
    } else if(prodotto instanceof String) {
%>
        <h1 style="color: #302B63FF; font-family: Montserrat, serif; margin-left: 10px;"><%=prodotto%></h1>
<%
    }
%>
</section>

<script>
    let slideIndex = 0;
    const slides = document.querySelectorAll('.slider img');

    function showSlide(n) {
        if (n >= slides.length) {
            slideIndex = 0;
        } else if (n < 0) {
            slideIndex = slides.length - 1;
        } else {
            slideIndex = n;
        }

        for (let i = 0; i < slides.length; i++) {
            slides[i].style.transform = 'translateX(' + (-slideIndex * 100) + '%)';
        }
    }

    function nextSlide() {
        showSlide(slideIndex + 1);
    }

    function prevSlide() {
        showSlide(slideIndex - 1);
    }

    showSlide(slideIndex);
</script>

</body>
</html>
