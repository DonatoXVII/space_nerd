<%@ page import="com.example.space_nerd.Model.IndirizzoBean" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    List<IndirizzoBean> indirizzi = (List<IndirizzoBean>) request.getAttribute("indirizzi");
    if(indirizzi == null) {
        response.sendRedirect("./UtenteControl?action=visualizzaIndirizzi");
        return;
    }
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="charset=UTF-8">
    <title>Space Nerd</title>
    <link href="css/accesso.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@include file="navbar.jsp"%>

<div class="indirizzi-container">
    <h2>I tuoi indirizzi</h2>
    <%
        if(indirizzi != null && indirizzi.size()!=0) {
            Iterator<?> it = indirizzi.iterator();
            while (it.hasNext()) {
                IndirizzoBean indirizzo = (IndirizzoBean) it.next();
    %>
    <%
        if(indirizzo != null) {
    %>
    <div class="indirizzo-item">
        <%=indirizzo.getNome()%>
        <%=indirizzo.getCognome()%>
        <%=indirizzo.getVia()%>
        <%=indirizzo.getCivico()%>
        <%=indirizzo.getProvincia()%>
        <%=indirizzo.getComune()%>
        <%=indirizzo.getCap()%>
        <a href="UtenteControl?action=rimuoviIndirizzo&IdIndirizzo=<%=indirizzo.getId()%>">Rimuovi</a>
    </div>

    <%
        }
    %>
    <%
            }
        }
    %>
</div>

<div class="form-container">
    <form action="UtenteControl?action=inserisciIndirizzo" method="post" class="form">
        <div class="form-title"><span>nuovo indirizzo dello</span></div>
        <div class="title-2"><span>SPACE</span></div>

        <div class="input-container">
            <input class="input-nome" name="nome" type="text" placeholder="Nome">
            <span> </span>
        </div>

        <section class="bg-stars">
            <span class="star"></span>
            <span class="star"></span>
            <span class="star"></span>
            <span class="star"></span>
        </section>

        <div class="input-container">
            <input class="input-cognome" name="cognome" type="text" placeholder="Cognome">
        </div>

        <div class="input-container">
            <input class="input-via" name="via" type="text" placeholder="Via">
        </div>

        <div class="input-container">
            <input class="input-civico" name="civico" type="text" placeholder="Numero civico">
        </div>

        <div class="input-container">
            <input class="input-provincia" name="provincia" type="text" placeholder="Provincia">
        </div>

        <div class="input-container">
            <input class="input-comune" name="comune" type="text" placeholder="Comune">
        </div>

        <div class="input-container">
            <input class="input-cap" name="cap" type="text" placeholder="Cap">
        </div>

        <button type="submit" class="submit">
            <span class="sign-text">Conferma</span>
        </button>
    </form>
</div>

</body>
</html>
