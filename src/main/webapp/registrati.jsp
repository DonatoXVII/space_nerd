<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%!
    String result = "";
%>
<%
    result = (String) request.getAttribute("result");
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
<div class="form-container">
    <form action="UtenteControl?action=registrati" method="post" class="form">
        <div class="form-title"><span>registrati anche tu nello space</span></div>
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

        <section class="bg-stars">
            <span class="star"></span>
            <span class="star"></span>
            <span class="star"></span>
            <span class="star"></span>
        </section>

        <div class="input-container">
            <input class="input-data" name="data" type="date" placeholder="Data di nascita">
        </div>

        <section class="bg-stars">
            <span class="star"></span>
            <span class="star"></span>
            <span class="star"></span>
            <span class="star"></span>
        </section>

        <div class="input-container">
            <input class="input-via" name="via" type="text" placeholder="Via">
        </div>

        <section class="bg-stars">
            <span class="star"></span>
            <span class="star"></span>
            <span class="star"></span>
            <span class="star"></span>
        </section>

        <div class="input-container">
            <input class="input-civico" name="civico" type="text" placeholder="Numero civico">
        </div>

        <section class="bg-stars">
            <span class="star"></span>
            <span class="star"></span>
            <span class="star"></span>
            <span class="star"></span>
        </section>

        <div class="input-container">
            <input class="input-provincia" name="provincia" type="text" placeholder="Provincia">
        </div>

        <section class="bg-stars">
            <span class="star"></span>
            <span class="star"></span>
            <span class="star"></span>
            <span class="star"></span>
        </section>

        <div class="input-container">
            <input class="input-comune" name="comune" type="text" placeholder="Comune">
        </div>

        <section class="bg-stars">
            <span class="star"></span>
            <span class="star"></span>
            <span class="star"></span>
            <span class="star"></span>
        </section>

        <div class="input-container">
            <input class="input-mail" name="email" type="email" placeholder="Email">
        </div>

        <section class="bg-stars">
            <span class="star"></span>
            <span class="star"></span>
            <span class="star"></span>
            <span class="star"></span>
        </section>

        <div class="input-container">
            <input class="input-pwd" name="password" type="password" placeholder="Password">
        </div>

        <button type="submit" class="submit">
            <span class="sign-text">Registrati</span>
        </button>
    </form>
</div>

<%
    if(result != null) {
%>
<h3><%=result%></h3>
<%
    }
%>

</body>
</html>
