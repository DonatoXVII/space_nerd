<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="charset=UTF-8">
    <title>Space Nerd</title>
    <link href="css/accesso.css" rel="stylesheet" type="text/css">
    <link href="css/profilo.css" rel="stylesheet" type="text/css">
    <script src="js/caricaCitta.js"></script>
</head>
<body>
<%@include file="navbar.jsp"%>
<div class="form-container-accesso">
<form action="UtenteControl?action=modificaProfilo" method="post" class="form-registrazione">
    <div class="form-title"><span>aggiorna il tuo</span></div>
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
        <input class="input-data" name="data" type="date" placeholder="Data di nascita" required>
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
        <input class="input-pwd" name="password" type="password" placeholder="Password">
    </div>

    <button type="submit" class="submit">
        <span class="sign-text">Conferma modifiche</span>
    </button>
</form>
</div>
</body>
</html>
