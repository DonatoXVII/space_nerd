<%@ page import="com.example.space_nerd.model.DatiSensibiliBean" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page errorPage="errore.jsp"%>
<%
    List<DatiSensibiliBean> utenti = (List<DatiSensibiliBean>) request.getAttribute("utenti");
    if(utenti == null) {
        response.sendRedirect("./AdminControl?action=visualizzaUtenti");
        return;
    }
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>Space Nerd</title>
    <link href="css/admin.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@include file="navbar.jsp"%>

<%if(emailUtente != null){%>

<div class="utentiEFiltri">

<div class="filtriUtenti">
    <h1>CERCA</h1>
    <form name="filtriPerOrdini">
        <fieldset>
            <legend>CLIENTE</legend>
            <label for="cliente"></label>
            <select id="cliente" name="cliente">
                <option value="">Seleziona un cliente</option>
                <%for(DatiSensibiliBean utente : utenti){%>
                <option value="<%=utente.getEmail()%>"><%=utente.getEmail()%></option>
                <%}%>
                <!-- Aggiungi altre opzioni secondo necessità -->
            </select>
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

<div class="utenti">
    <h1>Utenti registrati sulla piattaforma</h1>
<%
    for(DatiSensibiliBean utente : utenti) {
%>
    <div class="flip-card">
        <div class="flip-card-inner">
            <div class="flip-card-front">
                <p class="title">FLIP CARD</p>
                <%=utente.getNome() %>
                <%=utente.getCognome()%><br>
                <%=utente.getEmail()%><br>
                nato il <%=utente.getDataNascita()%><br>
                via <%=utente.getVia()%>,
                <%=utente.getCivico()%><br>
                <%=utente.getProvincia()%><br>
                <%=utente.getComune()%>
            </div>
            <div class="flip-card-back">
                <p class="title">BACK</p>


                <button class="Documents-btn" onclick="location.href='AdminControl?action=visualizzaOrdini&Email=<%=utente.getEmail()%>'">
  <span class="folderContainer">
    <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 146 113"
            height="113"
            width="146"
            class="fileBack"
    >
      <path
              fill="url(#paint0_linear_117_4)"
              d="M0 4C0 1.79086 1.79086 0 4 0H50.3802C51.8285 0 53.2056 0.627965 54.1553 1.72142L64.3303 13.4371C65.2799 14.5306 66.657 15.1585 68.1053 15.1585H141.509C143.718 15.1585 145.509 16.9494 145.509 19.1585V109C145.509 111.209 143.718 113 141.509 113H3.99999C1.79085 113 0 111.209 0 109V4Z"
      ></path>
      <defs>
        <linearGradient
                gradientUnits="userSpaceOnUse"
                y2="95.4804"
                x2="72.93"
                y1="0"
                x1="0"
                id="paint0_linear_117_4"
        >
          <stop stop-color="#8F88C2"></stop>
          <stop stop-color="#5C52A2" offset="1"></stop>
        </linearGradient>
      </defs>
    </svg>
    <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 88 99"
            height="99"
            width="88"
            class="filePage"
    >
      <rect fill="url(#paint0_linear_117_6)" height="99" width="88"></rect>
      <defs>
        <linearGradient
                gradientUnits="userSpaceOnUse"
                y2="160.5"
                x2="81"
                y1="0"
                x1="0"
                id="paint0_linear_117_6"
        >
          <stop stop-color="white"></stop>
          <stop stop-color="#686868" offset="1"></stop>
        </linearGradient>
      </defs>
    </svg>

    <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 160 79"
            height="79"
            width="160"
            class="fileFront"
    >
      <path
              fill="url(#paint0_linear_117_5)"
              d="M0.29306 12.2478C0.133905 9.38186 2.41499 6.97059 5.28537 6.97059H30.419H58.1902C59.5751 6.97059 60.9288 6.55982 62.0802 5.79025L68.977 1.18034C70.1283 0.410771 71.482 0 72.8669 0H77H155.462C157.87 0 159.733 2.1129 159.43 4.50232L150.443 75.5023C150.19 77.5013 148.489 79 146.474 79H7.78403C5.66106 79 3.9079 77.3415 3.79019 75.2218L0.29306 12.2478Z"
      ></path>
      <defs>
        <linearGradient
                gradientUnits="userSpaceOnUse"
                y2="82.8317"
                x2="66.9106"
                y1="8.71323"
                x1="38.7619"
                id="paint0_linear_117_5"
        >
          <stop stop-color="#C3BBFF"></stop>
          <stop stop-color="#51469A" offset="1"></stop>
        </linearGradient>
      </defs>
    </svg>
  </span>
                    <p class="text">Ordini utente</p>
                </button>


            </div>
        </div>
    </div>
<%
    }
%>
</div>
</div>

<%}%>

<script>
    $(document).ready(function() {
        $("#applicaFiltriOrdine").click(function(event) {
            event.preventDefault(); // Previeni il comportamento di default del form

            var clienteInput = $("#cliente").val().toLowerCase(); // Ottieni il valore selezionato dal menu a tendina
            console.log("nome " + clienteInput);

            $(".flip-card").each(function() {
                var nomeCognome = $(this).find('.flip-card-front').text().toLowerCase().trim(); // Ottieni il nome e cognome dell'utente e trasformalo in minuscolo, rimuovendo gli spazi vuoti

                // Controlla se il nome e cognome dell'utente corrispondono alla ricerca
                if (clienteInput === "" || nomeCognome.includes(clienteInput)) {
                    $(this).show(); // Mostra la flip-card se c'è corrispondenza o se non è selezionato alcun cliente
                } else {
                    $(this).hide(); // Nascondi altrimenti
                }
            });
        });
    });
</script>




</body>
</html>
