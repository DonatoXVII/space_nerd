<%@ page import="com.example.space_nerd.model.IndirizzoBean" %>
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
    <link href="css/utility.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@include file="navbar.jsp"%>

<div class="form-container-accesso" style="height:100%;">
    <form action="UtenteControl?action=inserisciIndirizzo" method="post" class="form-registrazione">
        <div class="form-title"><span>nuovo indirizzo dello</span></div>
        <div class="title-2"><span>SPACE</span></div>

        <div class="input-container">
            <input class="input-nome" name="nome" type="text" placeholder="Nome" required>
            <span> </span>
        </div>

        <section class="bg-stars">
            <span class="star"></span>
            <span class="star"></span>
            <span class="star"></span>
            <span class="star"></span>
        </section>

        <div class="input-container">
            <input class="input-cognome" name="cognome" type="text" placeholder="Cognome" required>
        </div>

        <div class="input-container">
            <input class="input-via" name="via" type="text" placeholder="Via" required>
        </div>

        <div class="input-container">
            <input class="input-civico" name="civico" type="text" placeholder="Numero civico" required>
        </div>

        <div class="input-container">
            <select id="provincia" name="provincia" required>
                <option value="" selected>Scegli la provincia</option>
            </select>
        </div>

        <div class="input-container">
            <select id="comune" name="comune" required>
                <option value="" selected>Scegli il comune</option>
            </select>
        </div>

        <div class="input-container">
            <input class="input-cap" name="cap" type="text" placeholder="Cap" required>
        </div>

        <button type="submit" class="submit">
            <span class="sign-text">Conferma</span>
        </button>
    </form>

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

        <div class="carta-container">
            <div class="card">
                <div class="tools">
                    <button style="background: none; border: none; cursor: pointer" class="circle" onclick="location.href='UtenteControl?action=rimuoviIndirizzo&IdIndirizzo=<%=indirizzo.getId()%>'" role="button">
                        <span class="red box"></span>
                    </button>
                    <div class="circle">
                        <span class="yellow box"></span>
                    </div>
                    <div class="circle">
                        <span class="green box"></span>
                    </div>
                </div>
                <div class="card__content">
                    <div class="parametri">
                        <div>Nome: <%=indirizzo.getNome()%></div>
                        <div>Cognome: <%=indirizzo.getCognome()%></div>
                        <div>Via: <%=indirizzo.getVia()%></div>
                        <div>Civico: <%=indirizzo.getCivico()%></div>
                        <div>Provincia: <%=indirizzo.getProvincia()%></div>
                        <div>Comune: <%=indirizzo.getComune()%></div>
                        <div>Cap: <%=indirizzo.getCap()%></div>
                    </div>
                </div>
            </div>
        </div>
        <%
            }
        %>
        <%
                }
            }
        %>
    </div>
</div>
<script src="js/caricaCitta.js"></script>
</body>
</html>
