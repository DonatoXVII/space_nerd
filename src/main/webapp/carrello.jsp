<%@ page import="com.example.space_nerd.model.MangaBean" %>
<%@ page import="com.example.space_nerd.model.PopBean" %>
<%@ page import="com.example.space_nerd.model.FigureBean" %>
<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    float prezzoTotale = 0;
%>
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
%>
    <div class="carrello">
<%
        for(Object prodotto : carrelloBean.getListaCarrello()) {
            if(prodotto instanceof MangaBean){
%>
            <div class="prodottoCarrello">
                <img src="img/imgManga/<%=((MangaBean) prodotto).getImg()%>" alt="errore immagine">
                <div class="descrizioneCarrello">
                    <h1><%=((MangaBean) prodotto).getDescrizione()%></h1>
                    <p>Quantità: <%=((MangaBean) prodotto).getQuantitaCarrello()%></p>
                    <%
                        Locale.setDefault(Locale.US);
                        String PrezzoeString = String.format("%.2f", ((MangaBean) prodotto).getPrezzo() * ((MangaBean) prodotto).getQuantitaCarrello());
                        Locale.setDefault(Locale.ITALY);
                    %>
                    <p>Prezzo: <%=PrezzoeString%>€</p>
                    <div class="comandiProdotto">
                        <button class="buttonAdd" onclick="location.href='ProdottoControl?action=aggiungiAlCarrello&Tipo=manga&Id=<%=((MangaBean) prodotto).getIdManga()%>'" <%if(((MangaBean) prodotto).getQuantitaCarrello() == ((MangaBean) prodotto).getNumArticoli()){%>style="display: none"<%}%>></button>
                        <button class="buttonRemove" onclick="location.href='ProdottoControl?action=rimuoviDalCarrello&Id=<%=((MangaBean) prodotto).getIdManga()%>'"></button>
                    </div>
                </div>
            </div>
<%
                prezzoTotale += Float.parseFloat(PrezzoeString);
            } else if(prodotto instanceof PopBean){
%>
            <div class="prodottoCarrello">
                <img src="img/imgPop/<%=((PopBean) prodotto).getImmagini().get(0)%>" alt="errore immagine">
                <div class="descrizioneCarrello">
                    <h1><%=((PopBean) prodotto).getDescrizione()%></h1>
                    <p>Quantità: <%=((PopBean) prodotto).getQuantitaCarrello()%></p>
                    <%
                        Locale.setDefault(Locale.US);
                        String PrezzoeString = String.format("%.2f", ((PopBean) prodotto).getPrezzo() * ((PopBean) prodotto).getQuantitaCarrello());
                        Locale.setDefault(Locale.ITALY);
                    %>
                    <p>Prezzo: <%=PrezzoeString%>€</p>
                    <div class="comandiProdotto">
                        <button class="buttonAdd" onclick="location.href='ProdottoControl?action=aggiungiAlCarrello&Tipo=pop&Id=<%=((PopBean) prodotto).getIdPop()%>'" <%if(((PopBean) prodotto).getQuantitaCarrello() == ((PopBean) prodotto).getNumArticoli()){%>style="display: none"<%}%>></button>
                        <button class="buttonRemove" onclick="location.href='ProdottoControl?action=rimuoviDalCarrello&Id=<%=((PopBean) prodotto).getIdPop()%>'"></button>
                    </div>
                </div>
            </div>
<%
                prezzoTotale += Float.parseFloat(PrezzoeString);
            } else if (prodotto instanceof FigureBean) {
%>
            <div class="prodottoCarrello">
                <img src="img/imgFigure/<%=((FigureBean) prodotto).getImmagini().get(0)%>" alt="errore immagine">
                <div class="descrizioneCarrello">
                    <h1><%=((FigureBean) prodotto).getDescrizione()%></h1>
                    <p>Quantità: <%=((FigureBean) prodotto).getQuantitaCarrello()%></p>
                    <%
                        Locale.setDefault(Locale.US);
                        String PrezzoeString = String.format("%.2f", ((FigureBean) prodotto).getPrezzo() * ((FigureBean) prodotto).getQuantitaCarrello());
                        Locale.setDefault(Locale.ITALY);
                    %>
                    <p>Prezzo: <%=PrezzoeString%>€</p>
                    <div class="comandiProdotto">
                        <button class="buttonAdd" onclick="location.href='ProdottoControl?action=aggiungiAlCarrello&Tipo=figure&Id=<%=((FigureBean) prodotto).getIdFigure()%>'" <%if(((FigureBean) prodotto).getQuantitaCarrello() == ((FigureBean) prodotto).getNumArticoli()){%>style="display: none"<%}%>></button>
                        <button class="buttonRemove" onclick="location.href='ProdottoControl?action=rimuoviDalCarrello&Id=<%=((FigureBean) prodotto).getIdFigure()%>'"></button>
                    </div>
                    </div>
            </div>
<%
            prezzoTotale += Float.parseFloat(PrezzoeString);
            }
        }
%>
    </div>
<div class="riepilogo">
    <div class="datiRiepilogo">
        <h1>IL TUO CARRELLO</h1>
        <%
            Locale.setDefault(Locale.US);
            String PrezzoeString = String.format("%.2f", prezzoTotale);
            Locale.setDefault(Locale.ITALY);
        %>
        <p>Prezzo totale : <%=PrezzoeString%>€</p>
        <%
            session.setAttribute("PrezzoTotale", PrezzoeString);
        %>
    </div>
    <div class="comandi">
        <%
            if(emailUtente != null) {
        %>
        <button class="checkout" onclick="location.href='./elaborazioneOrdine.jsp'">
            <span>Vai al pagamento</span>
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 74 74" height="34" width="34">
                <circle stroke-width="3" stroke="black" r="35.5" cy="37" cx="37"></circle>
                <path fill="black" d="M25 35.5C24.1716 35.5 23.5 36.1716 23.5 37C23.5 37.8284 24.1716 38.5 25 38.5V35.5ZM49.0607 38.0607C49.6464 37.4749 49.6464 36.5251 49.0607 35.9393L39.5147 26.3934C38.9289 25.8076 37.9792 25.8076 37.3934 26.3934C36.8076 26.9792 36.8076 27.9289 37.3934 28.5147L45.8787 37L37.3934 45.4853C36.8076 46.0711 36.8076 47.0208 37.3934 47.6066C37.9792 48.1924 38.9289 48.1924 39.5147 47.6066L49.0607 38.0607ZM25 38.5L48 38.5V35.5L25 35.5V38.5Z"></path>
            </svg>
        </button>
        <%
        } else {
        %>
        <button class="checkout" onclick="location.href='./login.jsp'">
            <span>Effettua prima il login</span>
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 74 74" height="34" width="34">
                <circle stroke-width="3" stroke="black" r="35.5" cy="37" cx="37"></circle>
                <path fill="black" d="M25 35.5C24.1716 35.5 23.5 36.1716 23.5 37C23.5 37.8284 24.1716 38.5 25 38.5V35.5ZM49.0607 38.0607C49.6464 37.4749 49.6464 36.5251 49.0607 35.9393L39.5147 26.3934C38.9289 25.8076 37.9792 25.8076 37.3934 26.3934C36.8076 26.9792 36.8076 27.9289 37.3934 28.5147L45.8787 37L37.3934 45.4853C36.8076 46.0711 36.8076 47.0208 37.3934 47.6066C37.9792 48.1924 38.9289 48.1924 39.5147 47.6066L49.0607 38.0607ZM25 38.5L48 38.5V35.5L25 35.5V38.5Z"></path>
            </svg>
        </button>
        <%
            }
        %>
        <button class="button" onclick="location.href='ProdottoControl?action=svuotaCarrello'">
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
</div>
<%
    } else {
%>
        <h1> Ancora nessun prodotto nel carrello</h1><img class="h1-img" src="img/triste.jpg" alt="errore immagine">
<%
    }
%>
</body>
</html>
