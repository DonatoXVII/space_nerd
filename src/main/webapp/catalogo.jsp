<%@ page import="java.util.List" %>
<%@ page import="com.example.space_nerd.model.MangaBean" %>
<%@ page import="com.example.space_nerd.model.PopBean" %>
<%@ page import="com.example.space_nerd.model.FigureBean" %>
<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    List<Object> prodotti = (List<Object>) request.getAttribute("prodotti");
    if(prodotti == null) {
        response.sendRedirect("./ProdottoControl?action=visualizzaCatalogo");
        return;
    }
%>

<%
    Locale.setDefault(Locale.US);
    String PrezzoeString;
    Locale.setDefault(Locale.ITALY);
%>

<%
    String disponibile = "DISPONIBILE";
    String nonDisp = "NON DISPONIBILE";
    String quantDisp = "DISPONIBILE SOLO 1";
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="charset=UTF-8">
    <title>Space Nerd</title>
    <link href="css/catalogo.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@include file="navbar.jsp"%>

<div class="catalogoEFiltri">
    <div class="filtri">
        <h1>Cerca per filtri</h1>
        <form id="filtroForm">
            <label>
                <input type="checkbox" name="manga" value="manga" class="filtro-tipo"> Manga
            </label><br>
            <label>
                <input type="checkbox" name="pop" value="pop" class="filtro-tipo"> Pop
            </label><br>
            <label>
                <input type="checkbox" name="figure" value="figure" class="filtro-tipo"> Figure
            </label><br><br><br>
            <label>
                <input type="checkbox" name="dragonball" value="dragonball" class="filtro-descrizione"> Dragon Ball
            </label><br>
            <label>
                <input type="checkbox" name="one piece" value="one piece" class="filtro-descrizione"> One Piece
            </label><br>
            <label>
                <input type="checkbox" name="naruto" value="naruto" class="filtro-descrizione"> Naruto
            </label><br>
            <label>
                <input type="checkbox" name="marvel" value="marvel" class="filtro-descrizione"> Marvel
            </label><br><br><br>
            <label for="rangeInput">Seleziona prezzo massimo <br>
                <span id="minValue" style="display: none">10</span> <input type="range" id="rangeInput" name="rangeInput" class="filtro-prezzo" min="10" max="500" value="500"><span id="maxValue">500</span>€
            </label><br><br><br>
            <label>
                <input type="checkbox" name="disponibili" value="disponibili" class="filtro-disponibili"> Mostra solo disponibili
            </label><br><br>
        </form>
        <button id="applicaFiltriBtn" class="applicaFiltriBtn">
            Applica Filtri
            <svg fill="currentColor" viewBox="0 0 24 24" class="icon">
                <path
                        clip-rule="evenodd"
                        d="M12 2.25c-5.385 0-9.75 4.365-9.75 9.75s4.365 9.75 9.75 9.75 9.75-4.365 9.75-9.75S17.385 2.25 12 2.25zm4.28 10.28a.75.75 0 000-1.06l-3-3a.75.75 0 10-1.06 1.06l1.72 1.72H8.25a.75.75 0 000 1.5h5.69l-1.72 1.72a.75.75 0 101.06 1.06l3-3z"
                        fill-rule="evenodd"
                ></path>
            </svg>
        </button>

        <%if(tipoUtente != null && tipoUtente){%>
        <button class="btnAggiungiProdotto" onclick="location.href='nuovoProdotto.jsp'" style="margin-top: 60px">
            <svg class="Layer" viewBox="0 0 1095.66 1095.63"><path class="cls-1" d="M1298,749.62c.4,300.41-243,548-548.1,547.9C446.23,1297.4,201.92,1051.2,202.29,749c.37-301.52,244.49-547.41,548.34-547.12C1055.43,202.18,1298.25,449.6,1298,749.62Z" transform="translate(-202.29 -201.89)"></path><path class="cls-2" d="M1285.89,749.79c-.25,297.07-241.24,535.86-536.12,535.66-296.34-.21-537-241.72-535.29-539,1.68-293.16,240.83-534.18,539.15-532.37C1046.8,215.84,1285.62,453.88,1285.89,749.79Z" transform="translate(-202.29 -201.89)"></path><path class="cls-3" d="M1195.29,749.56c.54,244.73-198.67,446.2-446.87,445.33C503.27,1194,304,994.53,304.93,748c.91-244.52,199.12-443.08,444.39-443.49C997.43,304,1195.74,505.59,1195.29,749.56Z" transform="translate(-202.29 -201.89)"></path><path class="cls-4" d="M1097.23,749.87c.22,190.31-154.42,347.43-348,346.92-192-.5-346.48-156.44-346.17-347.7C403.33,558,558.18,402,751.08,402.55,944.62,403.09,1097.69,560.56,1097.23,749.87Z" transform="translate(-202.29 -201.89)"></path><path class="cls-5" d="M1006.72,744.28c2.81,143.23-110.17,257.35-247.42,261.9C613.15,1011,498.22,895.93,493.71,758.88,488.93,613.71,603,498,740.69,493.28,886.73,488.24,1004,603.87,1006.72,744.28Z" transform="translate(-202.29 -201.89)"></path><path class="cls-6" d="M607.55,553.77c5.13,3.72,10.28,7.42,15.4,11.15l124.12,90.24a8.57,8.57,0,0,1,1.2.84c1.26,1.27,2.35,1.09,3.77,0,6.36-4.74,12.82-9.35,19.24-14l118.23-85.89c1.07-.78,2.17-1.54,3.28-2.32.82,1.1,0,2-.27,2.77Q866.29,637.48,840,718.38c-1.11,3.42-1.13,3.42,1.81,5.56l136,98.81c1.17.86,2.33,1.74,3.79,2.83-1.48.73-2.79.45-4,.45q-84.07,0-168.16,0h-.73c-3.7,0-3.68,0-4.8,3.43q-26.1,80.4-52.23,160.78c-.4,1.21-.45,2.66-1.77,3.6L735,948.24q-19.34-59.52-38.68-119c-1-3.16-1-3.17-4.6-3.17q-84.27,0-168.53,0a10.57,10.57,0,0,1-4.24-.34,13.17,13.17,0,0,1,3.33-2.77q67.55-49.08,135.1-98.18c5-3.63,4.38-1.8,2.43-7.83q-25.94-80.07-52-160.11c-.3-.91-.57-1.83-.85-2.75Z" transform="translate(-202.29 -201.89)"></path></svg>
            <p class="text">Nuovo prodotto</p>
        </button>
        <%}%>
    </div>

<div class="catalogo">
<%
    for(Object prodotto : prodotti) {
        if(prodotto instanceof MangaBean) {
%>
            <button style="cursor: pointer" class="gallery" data-tipo="manga" onclick="location.href='ProdottoControl?action=visualizzaDettagli&Tipo=manga&Id=<%=((MangaBean) prodotto).getIdManga()%>'" role="button">
               <img src="img/imgManga/<%=((MangaBean) prodotto).getImg()%>" alt="errore immagine">
                <div class="description">
                    <h5><%=((MangaBean) prodotto).getDescrizione()%></h5>
                    <%
                        PrezzoeString = String.format("%.2f", ((MangaBean) prodotto).getPrezzo());
                    %>
                    <h5><%=PrezzoeString%>€</h5>
                    <%if(((MangaBean) prodotto).getNumArticoli() > 1) {%><h5 style="color: green; margin-top: 20px"><%=disponibile%></h5>
                    <%}else if(((MangaBean) prodotto).getNumArticoli() == 1){%><h5 style="color: #c5a31d; margin-top: 20px"><%=quantDisp%></h5>
                    <%} else {%><h5 style="color: red; margin-top: 20px"><%=nonDisp%></h5><%}%>
                </div>
            </button>

<%
        } else if(prodotto instanceof PopBean) {
%>
            <button style="cursor: pointer" class="gallery" data-tipo="pop" onclick="location.href='ProdottoControl?action=visualizzaDettagli&Tipo=pop&Id=<%=((PopBean) prodotto).getIdPop()%>'" role="button">
                <img src="img/imgPop/<%=((PopBean) prodotto).getImmagini().get(0)%>" alt="errore immagine">
                <div class="description">
                    <h5><%=((PopBean) prodotto).getDescrizione()%></h5>
                    <h5><%=((PopBean) prodotto).getSerie()%></h5>
                    <%
                        PrezzoeString = String.format("%.2f", ((PopBean) prodotto).getPrezzo());
                    %>
                    <h5><%=PrezzoeString%>€</h5>
                    <%if(((PopBean) prodotto).getNumArticoli() > 1) {%><h5 style="color: green; margin-top: 20px"><%=disponibile%></h5><%if(tipoUtente!=null && tipoUtente){%><h5>Quantita in stock: <%=((PopBean) prodotto).getNumArticoli()%></h5><%}%>
                    <%}else if(((PopBean) prodotto).getNumArticoli() == 1){%><h5 style="color: #c5a31d; margin-top: 20px"><%=quantDisp%></h5>
                    <%} else {%><h5 style="color: red; margin-top: 20px"><%=nonDisp%></h5><%}%>
                </div>
            </button>
<%
        } else if(prodotto instanceof FigureBean) {
%>
            <button style="cursor: pointer" class="gallery" data-tipo="figure" onclick="location.href='ProdottoControl?action=visualizzaDettagli&Tipo=figure&Id=<%=((FigureBean) prodotto).getIdFigure()%>'" role="button">
                <img src="img/imgFigure/<%=((FigureBean) prodotto).getImmagini().get(0)%>" alt="errore immagine">
                <div class="description">
                    <h5><%=((FigureBean) prodotto).getDescrizione()%></h5>
                    <h5><%=((FigureBean) prodotto).getPersonaggio()%></h5>
                    <%
                        PrezzoeString = String.format("%.2f", ((FigureBean) prodotto).getPrezzo());
                    %>
                    <h5><%=PrezzoeString%>€</h5>
                    <%if(((FigureBean) prodotto).getNumArticoli() > 1) {%><h5 style="color: green; margin-top: 20px"><%=disponibile%></h5>
                    <%}else if(((FigureBean) prodotto).getNumArticoli() == 1){%><h5 style="color: #c5a31d; margin-top: 20px"><%=quantDisp%></h5>
                    <%} else {%><h5 style="color: red; margin-top: 20px"><%=nonDisp%></h5><%}%>
                </div>
            </button>
<%
        }
    }
%>
</div>

</div>

<script>
    $(document).ready(function() {
        var selectedTipi = [];
        var selectedDescrizione = [];
        var selectedPrezzo = 500;
        var mostraSoloDisponibili = false; // Aggiunto

        $(".filtro-tipo").change(function() {
            var tipo = $(this).val();
            if ($(this).is(":checked")) {
                selectedTipi.push(tipo);
            } else {
                var index = selectedTipi.indexOf(tipo);
                if (index !== -1) {
                    selectedTipi.splice(index, 1);
                }
            }
        });

        $(".filtro-descrizione").change(function() {
            var parola = $(this).val();
            if ($(this).is(":checked")) {
                selectedDescrizione.push(parola);
            } else {
                var index = selectedDescrizione.indexOf(parola);
                if (index !== -1) {
                    selectedDescrizione.splice(index, 1);
                }
            }
        });

        $(".filtro-prezzo").change(function() {
            var maxPrezzo = parseFloat($(this).val());
            $("#maxValue").text(maxPrezzo); // Aggiorna il valore massimo visualizzato
            selectedPrezzo = maxPrezzo;
        });

        $(".filtro-disponibili").change(function() {
            mostraSoloDisponibili = $(this).is(":checked");
            console.log("mostra disponili spuntato " + mostraSoloDisponibili)
        });

        $("#applicaFiltriBtn").click(function() {
            $(".catalogo button.gallery").each(function() {
                var tipoProdotto = $(this).data('tipo');
                var descrizione = $(this).find('.description h5').text().toLowerCase();
                var prezzoString = $(this).find('.description').text(); // Otteniamo tutto il testo all'interno di .description
                var prezzo = extractPrice(prezzoString); // Estraiamo il prezzo utilizzando la funzione extractPrice
                var includeProdotto = false;

                if (mostraSoloDisponibili) {
                    if (!($(this).find('.description h5').text().includes("NON DISPONIBILE"))) {
                        console.log("prodotto " + descrizione)
                        // Controlla se il tipo del prodotto è incluso negli elementi selezionati della prima form
                        if (selectedTipi.length === 0 || selectedTipi.includes(tipoProdotto)) {
                            if (prezzo <= selectedPrezzo) {
                                if (selectedDescrizione.length === 0) {
                                    includeProdotto = true;
                                } else {
                                    // Controlla se almeno una delle parole selezionate è presente nella descrizione
                                    for (var i = 0; i < selectedDescrizione.length; i++) {
                                        if (descrizione.includes(selectedDescrizione[i])) {
                                            includeProdotto = true;
                                            break;
                                        }
                                    }
                                }

                            }
                        }
                    }
                } else {
                    if (selectedTipi.length === 0 || selectedTipi.includes(tipoProdotto)) {
                        if (prezzo <= selectedPrezzo) {
                            if (selectedDescrizione.length === 0) {
                                includeProdotto = true;
                            } else {
                                // Controlla se almeno una delle parole selezionate è presente nella descrizione
                                for (var i = 0; i < selectedDescrizione.length; i++) {
                                    if (descrizione.includes(selectedDescrizione[i])) {
                                        includeProdotto = true;
                                        break;
                                    }
                                }
                            }

                        }
                    }
                }

                // Mostra o nascondi il prodotto in base all'inclusione
                if (includeProdotto) {
                    $(this).show();
                } else {
                    $(this).hide();
                }
            });
        });

        function extractPrice(text) {
            var regex = /([\d,]+(?:\.\d{1,2})?)\s*€/; // RegEx per trovare il prezzo seguito dal simbolo €
            var match = regex.exec(text);
            if (match) {
                return parseFloat(match[1].replace(',', '.')); // Converte il prezzo in float
            }
            return 0; // Se non viene trovato il prezzo, restituisce 0
        }
    });
</script>

<script>
    // Ottieni riferimenti agli elementi
    var rangeInput = document.getElementById("rangeInput");
    var minValueDisplay = document.getElementById("minValue");
    console.log(minValueDisplay);
    var maxValueDisplay = document.getElementById("maxValue");
    console.log(maxValueDisplay);

    // Aggiorna i valori visualizzati iniziali
    minValueDisplay.textContent = rangeInput.min;
    maxValueDisplay.textContent = rangeInput.value;

    // Aggiungi un listener per il cambiamento del valore
    rangeInput.addEventListener("input", function() {
        minValueDisplay.textContent = rangeInput.min;
        maxValueDisplay.textContent = rangeInput.value;
    });
</script>

</body>
</html>
