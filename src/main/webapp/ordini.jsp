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
    String email = (String) request.getAttribute("email");
    String nomeOrdine = (String) request.getAttribute("nome");
    String cognomeOrdine = (String) request.getAttribute("cognome");
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

        .acquista-ora {
            display: flex;
            align-items: center;
            font-family: inherit;
            cursor: pointer;
            font-weight: 500;
            font-size: 17px;
            padding: 0.8em 1.3em 0.8em 0.9em;
            color: white;
            background: #ad5389;
            background: linear-gradient(to right, #0f0c29, #302b63, #24243e);
            border: none;
            letter-spacing: 0.05em;
            border-radius: 16px;
        }

        .acquista-ora svg {
            margin-right: 3px;
            transform: rotate(30deg);
            transition: transform 0.5s cubic-bezier(0.76, 0, 0.24, 1);
        }

        .acquista-ora span {
            transition: transform 0.5s cubic-bezier(0.76, 0, 0.24, 1);
        }

        .acquista-ora:hover svg {
            transform: translateX(5px) rotate(90deg);
        }

        .acquista-ora:hover span {
            transform: translateX(7px);
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
    <form action="OrdiniControl?action=visualizzaOrdiniFiltrati&email=<%=email%>" method="post" name="filtriPerOrdini" onsubmit="return validate()">
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
    <%
        if(!tipoUtente){
            if(!ordini.isEmpty()){
    %>
        <h1>I tuoi ordini</h1>
    <%
            } else {
    %>
    <div style="display: flex; flex-wrap: wrap; justify-content: flex-start">
        <h1 style="justify-content: flex-start">Non hai effettuato nessun ordine</h1>
        <button class="acquista-ora" onclick="location.href='./catalogo.jsp'">
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
            <span>Consulta il nostro catalogo!</span>
        </button>
    </div>
    <%
            }
        }else{
            if(!ordini.isEmpty()){
    %>
        <h1>Ordini <%=nomeOrdine%> <%=cognomeOrdine%></h1>
    <%
            }else {
    %>
        <h1 style="margin-left: 20px">Nessun ordine trovato per <%=nomeOrdine%> <%=cognomeOrdine%></h1>
    <%
            }
        }
    %>

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
        var dataInizioInput = document.filtriPerOrdini.dataInizio.value;
        var dataFineInput = document.filtriPerOrdini.dataFine.value;
        var dataDiOggi = new Date();

        var dataInizio = new Date(dataInizioInput);
        var dataFine = new Date(dataFineInput);

        if(dataInizio > dataFine) {
            alert("Effettua un controllo valido");
            document.filtriPerOrdini.dataFine.focus();
            return false;
        }

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

</body>
</html>
