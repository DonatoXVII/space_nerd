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
    <form action="UtenteControl?action=registrati" method="post" class="form-registrazione">
        <div class="form-title"><span>registrati anche tu nello</span></div>
        <div class="title-2"><span>SPACE</span></div>

        <div class="input-container">
            <input class="input-nome" name="nome" type="text" placeholder="Nome" required>
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
            <input class="input-mail" name="email" type="email" placeholder="Email" required>
        </div>

        <div class="input-container">
                <input class="input-pwd" name="password" type="password" placeholder="Password" required>
        </div>

        <button type="submit" class="submit">
            <span class="sign-text">Registrati</span>
        </button>
    </form>

<%
    if(result != null) {
%>
<h3><%=result%></h3>
<%
    }
%>

<script>
    // Funzione per caricare le province dal JSON
    function caricaProvince() {
        fetch('js/citta.json')
            .then(response => response.json())
            .then(data => {
                const provinciaSelect = document.getElementById('provincia');
                data.province.forEach(provincia => {
                    let option = document.createElement('option');
                    option.textContent = provincia;
                    option.value = provincia;
                    provinciaSelect.appendChild(option);
                });
            });
    }

    // Funzione per caricare i comuni in base alla provincia selezionata
    function caricaComuni() {
        const provinciaSelect = document.getElementById('provincia');
        const comuneSelect = document.getElementById('comune');

        provinciaSelect.addEventListener('change', () => {
            comuneSelect.innerHTML = ''; // Pulisce le opzioni precedenti

            const provinciaSelezionata = provinciaSelect.value;
            const comuni = datiComuni[provinciaSelezionata];

            comuni.forEach(comune => {
                let option = document.createElement('option');
                option.textContent = comune;
                option.value = comune;
                comuneSelect.appendChild(option);
            });
        });
    }

    // Carica le province all'avvio
    caricaProvince();

    // Dati citta.json caricati
    let datiComuni = null;
    fetch('js/citta.json')
        .then(response => response.json())
        .then(data => {
            datiComuni = data.comuni;
            caricaComuni();
        });
</script>


</body>
</html>
