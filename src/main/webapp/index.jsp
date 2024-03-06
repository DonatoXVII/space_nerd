<%@ page import="java.util.List" %>
<%@ page import="com.example.space_nerd.model.MangaBean" %>
<%@ page import="com.example.space_nerd.model.PopBean" %>
<%@ page import="com.example.space_nerd.model.FigureBean" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    List<Object> bestProdotti = (List<Object>) request.getAttribute("bestProdotti");
    if(bestProdotti == null) {
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

<section id="homePage">

</section>

<section id="hero">
    <h4>Un mondo di offerte</h4>
    <h2>Per gli appassionati di anime</h2>
    <h1>Di ogni genere</h1>
    <p>Non lasciarti sfuggire nessuna occasione, saldi in ogni periodo dell'anno</p>
    <a href="./catalogo.jsp" ><button class="acquista-ora">
        <svg
                height="24"
                width="24"
                viewBox="0 0 24 24"
                xmlns="http://www.w3.org/2000/svg"
        >
            <path d="M0 0h24v24H0z" fill="none"></path>
            <path
                    d="M5 13c0-5.088 2.903-9.436 7-11.182C16.097 3.564 19 7.912 19 13c0 .823-.076 1.626-.22 2.403l1.94 1.832a.5.5 0 0 1 .095.603l-2.495 4.575a.5.5 0 0 1-.793.114l-2.234-2.234a1 1 0 0 0-.707-.293H9.414a1 1 0 0 0-.707.293l-2.234 2.234a.5.5 0 0 1-.793-.114l-2.495-4.575a.5.5 0 0 1 .095-.603l1.94-1.832C5.077 14.626 5 13.823 5 13zm1.476 6.696l.817-.817A3 3 0 0 1 9.414 18h5.172a3 3 0 0 1 2.121.879l.817.817.982-1.8-1.1-1.04a2 2 0 0 1-.593-1.82c.124-.664.187-1.345.187-2.036 0-3.87-1.995-7.3-5-8.96C8.995 5.7 7 9.13 7 13c0 .691.063 1.372.187 2.037a2 2 0 0 1-.593 1.82l-1.1 1.039.982 1.8zM12 13a2 2 0 1 1 0-4 2 2 0 0 1 0 4z"
                    fill="currentColor"
            ></path>
        </svg>
        <span>Acquista ora!</span>
    </button></a>
</section>

<section id="piuVenduti">
    <h2>PRODOTTI PIÙ VENDUTI</h2>
    <div class="prodotti">
        <div class="mangaPiuVenuti">
            <%
                for(Object prodotto : bestProdotti){
                    if(prodotto instanceof MangaBean) {
            %>
            <a href="ProdottoControl?action=visualizzaDettagli&Tipo=manga&Id=<%=((MangaBean) prodotto).getIdManga()%>">
                <div class="item">
                <img src="img/imgManga/<%=((MangaBean) prodotto).getImg()%>" alt="errore immagine">
                <h6><%=((MangaBean) prodotto).getDescrizione()%></h6>
                </div>
            </a>
            <%
                    }
                }
            %>
        </div>

        <div class="popPiuVenuti">
            <%
                for(Object prodotto : bestProdotti) {
                    if(prodotto instanceof PopBean) {
            %>
            <a href="ProdottoControl?action=visualizzaDettagli&Tipo=pop&Id=<%=((PopBean) prodotto).getIdPop()%>">
                <div class="item">
                <img src="img/imgPop/<%=((PopBean) prodotto).getImmagini().get(0)%>" alt="errore immagine">
                    <h6><%=((PopBean) prodotto).getDescrizione()%></h6>
                </div>
            </a>
            <%
                    }
                }
            %>
        </div>

        <div class="figurePiuVenuti">
            <%
                for(Object prodotto : bestProdotti){
                    if(prodotto instanceof FigureBean) {
            %>
            <a href="ProdottoControl?action=visualizzaDettagli&Tipo=figure&Id=<%=((FigureBean) prodotto).getIdFigure()%>">
                <div class="item">
                <img src="img/imgFigure/<%=((FigureBean) prodotto).getImmagini().get(0)%>" alt="errore immagine">
                <h6><%=((FigureBean) prodotto).getDescrizione()%></h6>
                </div>
            </a>
            <%
                    }
                }
            %>
        </div>
    </div>
</section>

</body>
</html>