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
    <link href="css/utility.css" rel="stylesheet" type="text/css">
    <script src="js/caricaCitta.js"></script>
</head>
<body>
<%@include file="navbar.jsp"%>

<div class="form-container-accesso">
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
                    <a href="UtenteControl?action=rimuoviIndirizzo&IdIndirizzo=<%=indirizzo.getId()%>"><div class="circle">
                        <span class="red box"></span>
                    </div></a>
                    <div class="circle">
                        <span class="yellow box"></span>
                    </div>
                    <div class="circle">
                        <span class="green box"></span>
                    </div>
                </div>
                <div class="card__content">
                    <div class="parametri">
                        Nome : <label><%=indirizzo.getNome()%></label><br>
                        Cognome : <label><%=indirizzo.getCognome()%></label><br>
                        Via : <label><%=indirizzo.getVia()%></label><br>
                        Civico : <label><%=indirizzo.getCivico()%></label><br>
                        Provincia : <label><%=indirizzo.getProvincia()%></label><br>
                        Comune : <label><%=indirizzo.getComune()%></label><br>
                        Cap : <label><%=indirizzo.getCap()%></label><br>
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

</body>
</html>
