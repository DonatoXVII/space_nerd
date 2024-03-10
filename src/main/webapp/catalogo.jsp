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
        </form>
        <button id="applicaFiltriBtn">Applica Filtri</button>
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

        $("#applicaFiltriBtn").click(function() {
            $(".catalogo button.gallery").each(function() {
                var tipoProdotto = $(this).data('tipo');
                var descrizione = $(this).find('.description h5').text().toLowerCase();
                var includeProdotto = false;

                // Controlla se il tipo del prodotto è incluso negli elementi selezionati della prima form
                if (selectedTipi.length === 0 || selectedTipi.includes(tipoProdotto) ) {
                    if(selectedDescrizione.length === 0) {
                        includeProdotto = true;
                    }
                    else {
                        // Controlla se almeno una delle parole selezionate è presente nella descrizione
                        for (var i = 0; i < selectedDescrizione.length; i++) {
                            if (descrizione.includes(selectedDescrizione[i])) {
                                includeProdotto = true;
                                break;
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
    });
</script>

</body>
</html>
