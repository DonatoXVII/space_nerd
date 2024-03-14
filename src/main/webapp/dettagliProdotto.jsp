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
    <link rel="stylesheet" type="text/css" href="css/carrello.css">
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
            <%if(((MangaBean) prodotto).getNumArticoli() > 0 && ((MangaBean) prodotto).getQuantitaCarrello() < ((MangaBean) prodotto).getNumArticoli()){%>
            <button class="addToCart" onclick="location.href='ProdottoControl?action=aggiungiAlCarrello&Tipo=manga&Id=<%=((MangaBean) prodotto).getIdManga()%>'">
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
            <%}else {%>
            <button class="addToCart">
                <span>Prodotto esaurito</span>
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
            <%}%>
        </div>
<%
    }else if(prodotto instanceof PopBean){
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
        <p><img src="img/serie.jpg" alt="errore immagine">  Universo: <%=((PopBean) prodotto).getSerie()%></p>
        <p><img src="img/numeri.jpg" alt="errore immagine">  Numero di serie: <%=((PopBean) prodotto).getNumSerie()%></p>
        <%
            PrezzoeString = String.format("%.2f", ((PopBean) prodotto).getPrezzo());
        %>
        <p><img src="img/prezzo.jpg" alt="errore immagine">  Prezzo : <%=PrezzoeString%>€</p>
        <%if(tipoUtente){%><br><br><p>Quantità in stock : <%=((PopBean) prodotto).getNumArticoli()%></p><%}%>

        <%
            if(!tipoUtente){
                if(((PopBean) prodotto).getNumArticoli() > 0 && ((PopBean) prodotto).getQuantitaCarrello() < ((PopBean) prodotto).getNumArticoli()){
        %>
        <button class="addToCart" onclick="location.href='ProdottoControl?action=aggiungiAlCarrello&Tipo=pop&Id=<%=((PopBean) prodotto).getIdPop()%>'">
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
        <%} else { %>
        <button class="addToCart">
            <span>Prodotto esaurito</span>
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
        <%
                }
            } else {
        %>
        <div class="comandiAdmin">

            <form class="modificaQuantita" name="modifica" action="AdminControl?action=aggiungiProdotto&tipo=pop&IdPop=<%=((PopBean) prodotto).getIdPop()%>" method="post" onsubmit="return validate(this)">
                <label style="font-family: Montserrat, serif; font-weight: bold">
                    TOT: <input type="number" name="tot" required>
                </label>
                <button class="animated-button" type="submit">
                    <svg xmlns="http://www.w3.org/2000/svg" class="arr-2" viewBox="0 0 24 24">
                        <path
                                d="M16.1716 10.9999L10.8076 5.63589L12.2218 4.22168L20 11.9999L12.2218 19.778L10.8076 18.3638L16.1716 12.9999H4V10.9999H16.1716Z"
                        ></path>
                    </svg>
                    <span class="text">Aggiungi</span>
                    <span class="circle"></span>
                    <svg xmlns="http://www.w3.org/2000/svg" class="arr-1" viewBox="0 0 24 24">
                        <path
                                d="M16.1716 10.9999L10.8076 5.63589L12.2218 4.22168L20 11.9999L12.2218 19.778L10.8076 18.3638L16.1716 12.9999H4V10.9999H16.1716Z"
                        ></path>
                    </svg>
                </button>
            </form>

            <form class="modificaQuantita" name="modifica" action="AdminControl?action=rimuoviProdotto&tipo=pop&IdPop=<%=((PopBean) prodotto).getIdPop()%>" method="post" onsubmit="return validate(this)">
                <label style="font-family: Montserrat, serif; font-weight: bold">
                    TOT: <input type="number" name="tot" required>
                </label>
                <button class="animated-button" type="submit">
                    <svg xmlns="http://www.w3.org/2000/svg" class="arr-2" viewBox="0 0 24 24">
                        <path
                                d="M16.1716 10.9999L10.8076 5.63589L12.2218 4.22168L20 11.9999L12.2218 19.778L10.8076 18.3638L16.1716 12.9999H4V10.9999H16.1716Z"
                        ></path>
                    </svg>
                    <span class="text">Rimuovi</span>
                    <span class="circle"></span>
                    <svg xmlns="http://www.w3.org/2000/svg" class="arr-1" viewBox="0 0 24 24">
                        <path
                                d="M16.1716 10.9999L10.8076 5.63589L12.2218 4.22168L20 11.9999L12.2218 19.778L10.8076 18.3638L16.1716 12.9999H4V10.9999H16.1716Z"
                        ></path>
                    </svg>
                </button>
            </form>

            <button class="button" onclick="location.href='AdminControl?action=eliminaProdotto&tipo=pop&IdPop=<%=((PopBean) prodotto).getIdPop()%>'">
                <svg
                        xmlns="http://www.w3.org/2000/svg"
                        fill="none"
                        viewBox="0 0 69 14"
                        class="svgIcon bin-top"
                >
                    <g clip-path="url(#clip0_35_24)">
                        <path
                                fill="black"
                                d="M20.8232 2.62734L19.9948 4.21304C19.8224 4.54309 19.4808 4.75 19.1085
                            4.75H4.92857C2.20246 4.75 0 6.87266 0 9.5C0 12.1273 2.20246 14.25 4.92857
                            14.25H64.0714C66.7975 14.25 69 12.1273 69 9.5C69 6.87266 66.7975 4.75 64.0714
                            4.75H49.8915C49.5192 4.75 49.1776 4.54309 49.0052 4.21305L48.1768 2.62734C47.3451
                            1.00938 45.6355 0 43.7719 0H25.2281C23.3645 0 21.6549 1.00938 20.8232 2.62734ZM64.0023
                            20.0648C64.0397 19.4882 63.5822 19 63.0044 19H5.99556C5.4178 19 4.96025 19.4882
                            4.99766 20.0648L8.19375 69.3203C8.44018 73.0758 11.6746 76 15.5712 76H53.4288C57.3254
                            76 60.5598 73.0758 60.8062 69.3203L64.0023 20.0648Z"
                        ></path>
                    </g>
                    <defs>
                        <clipPath id="clip0_35_24">
                            <rect fill="white" height="14" width="69"></rect>
                        </clipPath>
                    </defs>
                </svg>

                <svg
                        xmlns="http://www.w3.org/2000/svg"
                        fill="none"
                        viewBox="0 0 69 57"
                        class="svgIcon bin-bottom"
                >
                    <g clip-path="url(#clip0_35_22)">
                        <path
                                fill="black"
                                d="M20.8232 -16.3727L19.9948 -14.787C19.8224 -14.4569 19.4808 -14.25 19.1085 -14.25H4.92857C2.20246
                            -14.25 0 -12.1273 0 -9.5C0 -6.8727 2.20246 -4.75 4.92857 -4.75H64.0714C66.7975 -4.75 69
                            -6.8727 69 -9.5C69 -12.1273 66.7975 -14.25 64.0714 -14.25H49.8915C49.5192 -14.25 49.1776
                            -14.4569 49.0052 -14.787L48.1768 -16.3727C47.3451 -17.9906 45.6355 -19 43.7719 -19H25.2281C23.3645
                            -19 21.6549 -17.9906 20.8232 -16.3727ZM64.0023 1.0648C64.0397 0.4882 63.5822 0 63.0044 0H5.99556C5.4178
                            0 4.96025 0.4882 4.99766 1.0648L8.19375 50.3203C8.44018 54.0758 11.6746 57 15.5712 57H53.4288C57.3254 57
                            60.5598 54.0758 60.8062 50.3203L64.0023 1.0648Z"
                        ></path>
                    </g>
                    <defs>
                        <clipPath id="clip0_35_22">
                            <rect fill="white" height="57" width="69"></rect>
                        </clipPath>
                    </defs>
                </svg>
            </button>

        </div>
        <%
            }
        %>
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
        <%if(((FigureBean) prodotto).getNumArticoli() > 0 && ((FigureBean) prodotto).getQuantitaCarrello() < ((FigureBean) prodotto).getNumArticoli()){%>
        <button class="addToCart" onclick="location.href='ProdottoControl?action=aggiungiAlCarrello&Tipo=figure&Id=<%=((FigureBean) prodotto).getIdFigure()%>'">
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
        <%}else{%>
        <button class="addToCart">
            <span>Prodotto esaurito</span>
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
        <%}%>
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

<script>
    function validate(form) {
        console.log("ciao");
        var tot = form.tot.value;

        if(tot !== "") {
            if(tot < 1) {
                alert("Inserire una quantità maggiore di 0");
                form.tot.focus();
                return false;
            }
        }
    }
</script>
</body>
</html>
