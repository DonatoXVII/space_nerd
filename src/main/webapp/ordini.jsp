<%@ page import="java.util.List" %>
<%@ page import="com.example.space_nerd.model.OrdineBean" %>
<%@ page import="java.util.Iterator" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    List<OrdineBean> ordini = (List<OrdineBean>) request.getAttribute("ordini");
    if(ordini == null) {
        response.sendRedirect("./OrdiniControl?action=visualizzaOrdini");
        return;
    }
%>

<%
    String email = (String) request.getAttribute("emailOrdine");
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="charset=UTF-8">
    <title>Space Nerd</title>
    <link href="css/utility.css" rel="stylesheet" type="text/css">
    <style>
        .botao {
            width: 125px;
            height: 45px;
            border-radius: 20px;
            border: none;
            box-shadow: 1px 1px rgba(107, 221, 215, 0.37);
            padding: 5px 10px;
            background: rgb(47,93,197);
            background: linear-gradient(160deg, rgba(47,93,197,1) 0%, rgba(46,86,194,1) 5%, rgba(47,93,197,1) 11%, rgba(59,190,230,1) 57%, rgba(0,212,255,1) 71%);
            color: #fff;
            font-family: Roboto, sans-serif;
            font-weight: 505;
            font-size: 16px;
            line-height: 1;
            cursor: pointer;
            filter: drop-shadow(0 0 10px rgba(59, 190, 230, 0.568));
            transition: .5s linear;
        }

        .botao .mysvg {
            display: none;
        }

        .botao:hover {
            width: 50px;
            height: 50px;
            border-radius: 50%;
            transition: .5s linear;
        }

        .botao:hover .texto {
            display: none;
        }

        .botao:hover .mysvg {
            display: inline;
        }

        .botao:hover::before {
            content: '';
            position: absolute;
            top: -3px;
            left: -3px;
            width: 100%;
            height: 100%;
            border: 3.5px solid transparent;
            border-top: 3.5px solid #fff;
            border-right: 3.5px solid #fff;
            border-radius: 50%;
            animation: animateC 2s linear infinite;
        }

        @keyframes animateC {
            0% {
                transform: rotate(0deg);
            }

            100% {
                transform: rotate(360deg);
            }
        }
    </style>
</head>
<body>
<%@include file="navbar.jsp"%>
<style>
    body {
       background: #f1f2f4;
    }
</style>

<div class="filtriEOrdini">
<div class="filtriOrdini">
    <h1>Cerca per filtri</h1>
    <form name="filtriPerOrdini" onsubmit="return validate()">
        <fieldset>
            <legend>FILTRA PER DATA</legend>
            <label for="dataInizio">Data inizio: </label>
            <input type="date" id="dataInizio" name="dataInizio"><br>
            <label for="dataFine">Data fine: </label>
            <input type="date" id="dataFine" name="dataFine">
        </fieldset>
        <br><br><br>
        <fieldset>
            <legend>FILTRA PER PREZZO</legend>
            <label for="prezzoMinimo">Prezzo minimo: </label>€
            <input type="text" id="prezzoMinimo" name="prezzoMinimo"><br>
            <label for="prezzoMassimo">Prezzo massimo: </label>€
            <input type="text" id="prezzoMassimo" name="prezzoMassimo">
        </fieldset>

        <br><br><br>
        <button type="submit" id="applicaFiltriOrdine" class="applicaFiltriBtn">
            Applica filtri
            <svg fill="currentColor" viewBox="0 0 24 24" class="icon">
                <path
                        clip-rule="evenodd"
                        d="M12 2.25c-5.385 0-9.75 4.365-9.75 9.75s4.365 9.75 9.75 9.75 9.75-4.365 9.75-9.75S17.385 2.25 12 2.25zm4.28 10.28a.75.75 0 000-1.06l-3-3a.75.75 0 10-1.06 1.06l1.72 1.72H8.25a.75.75 0 000 1.5h5.69l-1.72 1.72a.75.75 0 101.06 1.06l3-3z"
                        fill-rule="evenodd"
                ></path>
            </svg>
        </button><br>
        <button type="reset" class="applicaFiltriBtn">
            Ripristina
            <svg fill="currentColor" viewBox="0 0 24 24" class="icon">
                <path
                        clip-rule="evenodd"
                        d="M12 2.25c-5.385 0-9.75 4.365-9.75 9.75s4.365 9.75 9.75 9.75 9.75-4.365 9.75-9.75S17.385 2.25 12 2.25zm4.28 10.28a.75.75 0 000-1.06l-3-3a.75.75 0 10-1.06 1.06l1.72 1.72H8.25a.75.75 0 000 1.5h5.69l-1.72 1.72a.75.75 0 101.06 1.06l3-3z"
                        fill-rule="evenodd"
                ></path>
            </svg>
        </button>
    </form>
</div>
<div class="ordini-container">
    <%if(!tipoUtente){%><h1>I tuoi ordini</h1><%}else{%><h1>Ordini utente <%=email%></h1><%}%>
    <%
        if(ordini != null && ordini.size()!=0) {
            Iterator<?> it = ordini.iterator();
            while (it.hasNext()) {
                OrdineBean ordine = (OrdineBean) it.next();
    %>
    <%
        if(ordine != null) {
    %>

    <div class="cardOrdine">
        <div class="contentOrdine">
            <p class="heading">ORDINE DEL <%=ordine.getData()%>
            </p><p class="para">
            Prezzo totale dell' ordine : <%=ordine.getPrezzo()%>€<br>
            Qui puoi visualizzare i dettagli di questo ordine e scaricarne la fattura
        </p>
            <div style="display: flex; flex-direction: row">
            <button class="btnOrdine" onclick="location.href='OrdiniControl?action=visualizzaDettagliOrdine&IdOrdine=<%=ordine.getId()%>'">Dettagli ordine</button>
            <button class="botao" style="margin-left: 40px" onclick="location.href='OrdiniControl?action=generaFattura'">
                <svg width="24px" height="24px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" class="mysvg"><g id="SVGRepo_bgCarrier" stroke-width="0">
                </g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier">
                    <g id="Interface / Download">
                        <path id="Vector" d="M6 21H18M12 3V17M12 17L17 12M12 17L7 12" stroke="#f1f1f1" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        </path>
                    </g> </g>
                </svg>
                <span class="texto">Scarica fattura</span>
            </button>
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

<script>
    function validate(){
        var dataFineInput = document.filtriPerOrdini.dataInizio.value;
        var dataDiOggi = new Date();

        var dataFine = new Date(dataFineInput);

        if(dataFine > dataDiOggi) {
            alert("Inserisci una data valida");
            document.filtriPerOrdini.dataFine.focus();
            return false;
        }

        var dataInizioValue = document.getElementById("dataInizio").value;
        var dataFineValue = document.getElementById("dataFine").value;

        // Controlla se entrambi i campi nel primo fieldset sono vuoti
        if ((dataInizioValue.trim() === "" && dataFineValue.trim() !== "") ||
            (dataFineValue.trim() === "" && dataInizioValue.trim() !== "")) {
            alert("Devi compilare entrambi i campi di data");
            return false;
        }

        var prezzoMinimoValue = document.getElementById("prezzoMinimo").value;
        var prezzoMassimoValue = document.getElementById("prezzoMassimo").value;

        // Controlla se entrambi i campi nel secondo fieldset sono vuoti
        if ((prezzoMinimoValue.trim() === "" && prezzoMassimoValue.trim() !== "") ||
            (prezzoMassimoValue.trim() === "" && prezzoMinimoValue.trim() !== "")) {
            alert("Devi compilare entrambi i campi di prezzo.");
            return false;
        }

        return true;
    }
</script>

<script>/*
    $(document).ready(function() {
        $("#applicaFiltriOrdine").click(function(event) {
            event.preventDefault(); // Previeni il comportamento di default del form

            var dataInizio = $("#dataInizio").val(); // Ottieni la data di inizio
            var dataFine = $("#dataFine").val(); // Ottieni la data di fine
            var prezzoMinimo = parseFloat($("#prezzoMinimo").val()); // Ottieni il prezzo minimo e convertilo in float
            var prezzoMassimo = parseFloat($("#prezzoMassimo").val()); // Ottieni il prezzo massimo e convertilo in float

            $(".cardOrdine").each(function() {
                var dataOrdine = $(this).find('.heading').text().split("DEL ")[1]; // Ottieni la data dell'ordine
                var prezzoOrdine = parseFloat($(this).find('.para').text().split("Prezzo totale dell' ordine : ")[1].split("€")[0]); // Ottieni il prezzo totale dell'ordine e convertilo in float

                // Controllo per la data
                var dataValida = true;
                if (dataInizio && dataFine) {
                    if (dataOrdine < dataInizio || dataFine < dataOrdine) {
                        dataValida = false;
                    }
                }

                // Controllo per il prezzo
                var prezzoValido = true;
                if (!isNaN(prezzoMinimo) && !isNaN(prezzoMassimo)) {
                    if (prezzoOrdine < prezzoMinimo || prezzoOrdine > prezzoMassimo) {
                        prezzoValido = false;
                    }
                }

                // Mostra o nascondi la cardOrdine in base ai filtri
                if (dataValida && prezzoValido) {
                    $(this).show();
                } else {
                    $(this).hide();
                }
            });
        });
    });*/
</script>

</body>
</html>
