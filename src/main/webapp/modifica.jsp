<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page errorPage="errore.jsp"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="charset=UTF-8">
    <title>Space Nerd</title>
    <link href="css/accesso.css" rel="stylesheet" type="text/css">
    <link href="css/profilo.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@include file="navbar.jsp"%>
<%if(emailUtente != null){%>
<div class="form-container-accesso">
<form action="UtenteControl?action=modificaProfilo" method="post" class="form-registrazione" name="registrazione" onsubmit="return validate()">
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

<%}%>

<script src="js/caricaCitta.js"></script>
<script>
    function validate() {
        var pwd = document.registrazione.password.value;
        var passwordRegex = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
        var data = document.registrazione.data.value;
        var dataDiOggi = new Date();
        var dataLimite = new Date();
        dataLimite.setFullYear(dataDiOggi.getFullYear() - 18);

        if (!passwordRegex.test(pwd)) {
            alert("Il campo Password è errato, deve contenere almeno 8 caratteri"
                + "di cui almeno uno speciale, una maiuscola, una minuscola e un numero");
            document.registrazione.password.focus();
            return false;
        }

        if(data !== null) {
            var dataNascita = new Date(data);
        }
        if(dataNascita > dataDiOggi){
            alert("Inserisci una data valida");
            document.registrazione.data.focus();
            return false;
        }else if(dataNascita > dataLimite){
            alert("Per registrarti devi avere almeno 18 anni");
            document.registrazione.data.focus();
            return false;
        }
    }
</script>

</body>
</html>
