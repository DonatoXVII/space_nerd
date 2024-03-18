<%@ page import="com.example.space_nerd.model.MangaBean" %>
<%@ page import="com.example.space_nerd.model.PopBean" %>
<%@ page import="com.example.space_nerd.model.FigureBean" %>
<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page errorPage="errore.jsp"%>
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
            <%if(tipoUtente!=null && tipoUtente && ((MangaBean) prodotto).isVisibilita()){%><br><br><p>Quantità in stock : <%=((MangaBean) prodotto).getNumArticoli()%></p><%}%>

            <%
                if(tipoUtente == null || !tipoUtente){
                    if(((MangaBean) prodotto).isVisibilita()){
                        if(((MangaBean) prodotto).getNumArticoli() > 0 && ((MangaBean) prodotto).getQuantitaCarrello() < ((MangaBean) prodotto).getNumArticoli()){
            %>
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
            <%

                    }else {
            %>
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
            <p>Questo prodotto non è più disponibile</p>
            <%
                    } } else {
            %>
            <div class="comandiAdmin">

                <%if(((MangaBean) prodotto).isVisibilita()) {%>

                <form class="modificaQuantita" name="modifica" action="AdminControl?action=aggiungiProdotto&tipo=manga&IdManga=<%=((MangaBean) prodotto).getIdManga()%>" method="post" onsubmit="return validate(this)">
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

                <form class="modificaQuantita" name="modifica" action="AdminControl?action=rimuoviProdotto&tipo=manga&IdManga=<%=((MangaBean) prodotto).getIdManga()%>" method="post" onsubmit="return validate(this)">
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

                <form class="modificaPrezzo" name="modifica" action="AdminControl?action=modificaPrezzo&tipo=manga&IdManga=<%=((MangaBean) prodotto).getIdManga()%>" method="post" onsubmit="return validatePrezzo(this)">
                    <label style="font-family: Montserrat, serif; font-weight: bold">
                        Nuovo prezzo: <input type="number" step="0.01" name="prezzo" required>
                    </label>
                    <button class="changePrezzo">Cambia prezzo
                        <svg class="svgChangePrezzo" viewBox="0 0 512 512">
                            <path d="M410.3 231l11.3-11.3-33.9-33.9-62.1-62.1L291.7 89.8l-11.3 11.3-22.6 22.6L58.6 322.9c-10.4 10.4-18 23.3-22.2 37.4L1 480.7c-2.5 8.4-.2 17.5 6.1 23.7s15.3 8.5 23.7 6.1l120.3-35.4c14.1-4.2 27-11.8 37.4-22.2L387.7 253.7 410.3 231zM160 399.4l-9.1 22.7c-4 3.1-8.5 5.4-13.3 6.9L59.4 452l23-78.1c1.4-4.9 3.8-9.4 6.9-13.3l22.7-9.1v32c0 8.8 7.2 16 16 16h32zM362.7 18.7L348.3 33.2 325.7 55.8 314.3 67.1l33.9 33.9 62.1 62.1 33.9 33.9 11.3-11.3 22.6-22.6 14.5-14.5c25-25 25-65.5 0-90.5L453.3 18.7c-25-25-65.5-25-90.5 0zm-47.4 168l-144 144c-6.2 6.2-16.4 6.2-22.6 0s-6.2-16.4 0-22.6l144-144c6.2-6.2 16.4-6.2 22.6 0s6.2 16.4 0 22.6z"></path></svg>
                    </button>
                </form>

                <div style="width: 15%; display:flex ;justify-content: center">
                <button class="button" onclick="location.href='AdminControl?action=eliminaProdotto&tipo=manga&IdManga=<%=((MangaBean) prodotto).getIdManga()%>'">
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

                <%} else {%>

                <p>Questo prodotto non è più disponibile</p>
                <button class="buttonRestock" onclick="location.href='AdminControl?action=restock&tipo=manga&id=<%=((MangaBean) prodotto).getIdManga()%>'">
                    <div class="containerRestock">
                        <div class="folder folder_one"></div>
                        <div class="folder folder_two"></div>
                        <div class="folder folder_three"></div>
                        <div class="folder folder_four"></div>
                    </div>
                    <div class="active_line"></div>
                    <span class="textRestock">Esegui restock di 10 quantità</span>
                </button>

                <%}%>
            </div>

            <%
                }
            %>
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
        <p><img src="img/serie.jpg" alt="errore immagine">  Universo: <%=((PopBean) prodotto).getSerie()%></p>
        <p><img src="img/numeri.jpg" alt="errore immagine">  Numero di serie: <%=((PopBean) prodotto).getNumSerie()%></p>
        <%
            PrezzoeString = String.format("%.2f", ((PopBean) prodotto).getPrezzo());
        %>
        <p><img src="img/prezzo.jpg" alt="errore immagine">  Prezzo : <%=PrezzoeString%>€</p>
        <%if(tipoUtente!=null && tipoUtente && ((PopBean) prodotto).isVisibilita()){%><br><br><p>Quantità in stock : <%=((PopBean) prodotto).getNumArticoli()%></p><%}%>

        <%
            if(tipoUtente==null || !tipoUtente){
                if(((PopBean) prodotto).isVisibilita()){
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
        <%
                } else {
        %>
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
        <p>Questo prodotto non è più disponibile</p>
        <%
                } } else {
        %>
        <div class="comandiAdmin">

            <%if(((PopBean) prodotto).isVisibilita()){%>
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

            <form class="modificaPrezzo" action="AdminControl?action=modificaPrezzo&tipo=pop&IdPop=<%=((PopBean) prodotto).getIdPop()%>" method="post" onsubmit="return validatePrezzo(this)">
                <label style="font-family: Montserrat, serif; font-weight: bold">
                    Nuovo prezzo: <input type="number" step="0.01" name="prezzo" required>
                </label>
                <button class="changePrezzo">Cambia prezzo
                    <svg class="svgChangePrezzo" viewBox="0 0 512 512">
                        <path d="M410.3 231l11.3-11.3-33.9-33.9-62.1-62.1L291.7 89.8l-11.3 11.3-22.6 22.6L58.6 322.9c-10.4 10.4-18 23.3-22.2 37.4L1 480.7c-2.5 8.4-.2 17.5 6.1 23.7s15.3 8.5 23.7 6.1l120.3-35.4c14.1-4.2 27-11.8 37.4-22.2L387.7 253.7 410.3 231zM160 399.4l-9.1 22.7c-4 3.1-8.5 5.4-13.3 6.9L59.4 452l23-78.1c1.4-4.9 3.8-9.4 6.9-13.3l22.7-9.1v32c0 8.8 7.2 16 16 16h32zM362.7 18.7L348.3 33.2 325.7 55.8 314.3 67.1l33.9 33.9 62.1 62.1 33.9 33.9 11.3-11.3 22.6-22.6 14.5-14.5c25-25 25-65.5 0-90.5L453.3 18.7c-25-25-65.5-25-90.5 0zm-47.4 168l-144 144c-6.2 6.2-16.4 6.2-22.6 0s-6.2-16.4 0-22.6l144-144c6.2-6.2 16.4-6.2 22.6 0s6.2 16.4 0 22.6z"></path></svg>
                </button>
            </form>

            <div style="width: 15%; display:flex ;justify-content: center">
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
            <%}else{%>
            <p>Questo prodotto non è più disponibile</p>
            <button class="buttonRestock" onclick="location.href='AdminControl?action=restock&tipo=pop&id=<%=((PopBean) prodotto).getIdPop()%>'">
                <div class="containerRestock">
                    <div class="folder folder_one"></div>
                    <div class="folder folder_two"></div>
                    <div class="folder folder_three"></div>
                    <div class="folder folder_four"></div>
                </div>
                <div class="active_line"></div>
                <span class="textRestock">Esegui restock di 10 quantità</span>
            </button>
            <%}%>

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
        <%if(tipoUtente!=null && tipoUtente && ((FigureBean) prodotto).isVisibilita()){%><br><br><p>Quantità in stock : <%=((FigureBean) prodotto).getNumArticoli()%></p><%}%>

        <%
            if(tipoUtente==null || !tipoUtente){
                if(((FigureBean) prodotto).isVisibilita()) {
                if(((FigureBean) prodotto).getNumArticoli() > 0 && ((FigureBean) prodotto).getQuantitaCarrello() < ((FigureBean) prodotto).getNumArticoli()){
        %>
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
        <%

                }else{
        %>
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
            } else{
        %>
        <p>Questo prodotto non è più disponibile</p>
        <%
                }
            } else {
        %>
        <div class="comandiAdmin">

            <%if(((FigureBean) prodotto).isVisibilita()) {%>
            <form class="modificaQuantita" name="modifica" action="AdminControl?action=aggiungiProdotto&tipo=figure&IdFigure=<%=((FigureBean) prodotto).getIdFigure()%>" method="post" onsubmit="return validate(this)">
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

            <form class="modificaQuantita" name="modifica" action="AdminControl?action=rimuoviProdotto&tipo=figure&IdFigure=<%=((FigureBean) prodotto).getIdFigure()%>" method="post" onsubmit="return validate(this)">
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

            <form class="modificaPrezzo" action="AdminControl?action=modificaPrezzo&tipo=figure&IdFigure=<%=((FigureBean) prodotto).getIdFigure()%>" method="post" onsubmit="return validatePrezzo(this)">
                <label style="font-family: Montserrat, serif; font-weight: bold">
                    Nuovo prezzo: <input type="number" step="0.01" name="prezzo" required>
                </label>
                <button class="changePrezzo">Cambia prezzo
                    <svg class="svgChangePrezzo" viewBox="0 0 512 512">
                        <path d="M410.3 231l11.3-11.3-33.9-33.9-62.1-62.1L291.7 89.8l-11.3 11.3-22.6 22.6L58.6 322.9c-10.4 10.4-18 23.3-22.2 37.4L1 480.7c-2.5 8.4-.2 17.5 6.1 23.7s15.3 8.5 23.7 6.1l120.3-35.4c14.1-4.2 27-11.8 37.4-22.2L387.7 253.7 410.3 231zM160 399.4l-9.1 22.7c-4 3.1-8.5 5.4-13.3 6.9L59.4 452l23-78.1c1.4-4.9 3.8-9.4 6.9-13.3l22.7-9.1v32c0 8.8 7.2 16 16 16h32zM362.7 18.7L348.3 33.2 325.7 55.8 314.3 67.1l33.9 33.9 62.1 62.1 33.9 33.9 11.3-11.3 22.6-22.6 14.5-14.5c25-25 25-65.5 0-90.5L453.3 18.7c-25-25-65.5-25-90.5 0zm-47.4 168l-144 144c-6.2 6.2-16.4 6.2-22.6 0s-6.2-16.4 0-22.6l144-144c6.2-6.2 16.4-6.2 22.6 0s6.2 16.4 0 22.6z"></path></svg>
                </button>
            </form>

            <div style="width: 15%; display:flex ;justify-content: center">
            <button class="button" onclick="location.href='AdminControl?action=eliminaProdotto&tipo=figure&IdFigure=<%=((FigureBean) prodotto).getIdFigure()%>'">
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
            <%}else{%>
            <p>Questo prodotto non è più disponibile</p>
            <button class="buttonRestock" onclick="location.href='AdminControl?action=restock&tipo=figure&id=<%=((FigureBean) prodotto).getIdFigure()%>'">
                <div class="containerRestock">
                    <div class="folder folder_one"></div>
                    <div class="folder folder_two"></div>
                    <div class="folder folder_three"></div>
                    <div class="folder folder_four"></div>
                </div>
                <div class="active_line"></div>
                <span class="textRestock">Esegui restock di 10 quantità</span>
            </button>
            <%}%>

        </div>
        <%
            }
        %>
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
        console.log("Slider index : " + slideIndex)
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

<script>
    function validatePrezzo(form) {
        var prezzo = form.prezzo.value;

        if(prezzo !== "") {
            if(prezzo < 0) {
                alert("Inserire una quantità maggiore di 0");
                form.prezzo.focus();
                return false;
            }
        }
    }
</script>

</body>
</html>
