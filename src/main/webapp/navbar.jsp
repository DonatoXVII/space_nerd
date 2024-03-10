<%@ page import="com.example.space_nerd.utility.CarrelloBean" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%!
    String emailUtente = "";
    Boolean tipoUtente = false;
    String nome = "";
    String cognome = "";
    CarrelloBean carrelloBean = null;
%>
<%
    synchronized (session) {
        session = request.getSession();
        emailUtente = (String) session.getAttribute("email");
        nome = (String) session.getAttribute("nome");
        cognome = (String) session.getAttribute("cognome");
        tipoUtente = (Boolean) session.getAttribute("tipo");
        carrelloBean = (CarrelloBean) session.getAttribute("carrello");
    }
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Space Nerd</title>
    <link href="css/navbar.css" rel="stylesheet" type="text/css">
    <script src="https://code.jquery.com/jquery-3.6.4.min.js" integrity="sha384-UG8ao2jwOWB7/oDdObZc6ItJmwUkR/PfMyt9Qs5AwX7PsnYn1CRKCTWyncPTWvaS" crossorigin="anonymous"></script>
</head>
<body>

<section id="header">
    <a href="./index.jsp"><img src="img/logo_scontornato.jpg" class="logo" alt="errore immagine"></a>
    <div>
        <ul id="navbar">
            <form action="ProdottoControl?action=ricerca" method="post">
                <div class="searchBox">
                    <input class="searchInput" type="search" name="ricerca" placeholder="Cerca" list="suggerimentiProdotti">
                    <datalist id="suggerimentiProdotti" class="suggerimentiProdotti"></datalist>
                    <button type="submit" class="searchButton">
                        <svg xmlns="http://www.w3.org/2000/svg" width="29" height="29" viewBox="0 0 29 29" fill="none">
                            <g clip-path="url(#clip0_2_17)">
                                <g filter="url(#filter0_d_2_17)">
                                    <path d="M23.7953 23.9182L19.0585 19.1814M19.0585 19.1814C19.8188 18.4211 20.4219 17.5185 20.8333 16.5251C21.2448 15.5318 21.4566 14.4671 21.4566 13.3919C21.4566 12.3167 21.2448 11.252 20.8333 10.2587C20.4219 9.2653 19.8188 8.36271 19.0585 7.60242C18.2982 6.84214 17.3956 6.23905 16.4022 5.82759C15.4089 5.41612 14.3442 5.20435 13.269 5.20435C12.1938 5.20435 11.1291 5.41612 10.1358 5.82759C9.1424 6.23905 8.23981 6.84214 7.47953 7.60242C5.94407 9.13789 5.08145 11.2204 5.08145 13.3919C5.08145 15.5634 5.94407 17.6459 7.47953 19.1814C9.01499 20.7168 11.0975 21.5794 13.269 21.5794C15.4405 21.5794 17.523 20.7168 19.0585 19.1814Z" stroke="white" stroke-width="3" stroke-linecap="round" stroke-linejoin="round" shape-rendering="crispEdges"></path>
                                </g>
                            </g>
                            <defs>
                                <filter id="filter0_d_2_17" x="-0.418549" y="3.70435" width="29.7139" height="29.7139" filterUnits="userSpaceOnUse" color-interpolation-filters="sRGB">
                                    <feFlood flood-opacity="0" result="BackgroundImageFix"></feFlood>
                                    <feColorMatrix in="SourceAlpha" type="matrix" values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0" result="hardAlpha"></feColorMatrix>
                                    <feOffset dy="4"></feOffset>
                                    <feGaussianBlur stdDeviation="2"></feGaussianBlur>
                                    <feComposite in2="hardAlpha" operator="out"></feComposite>
                                    <feColorMatrix type="matrix" values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.25 0"></feColorMatrix>
                                    <feBlend mode="normal" in2="BackgroundImageFix" result="effect1_dropShadow_2_17"></feBlend>
                                    <feBlend mode="normal" in="SourceGraphic" in2="effect1_dropShadow_2_17" result="shape"></feBlend>
                                </filter>
                                <clipPath id="clip0_2_17">
                                    <rect width="28.0702" height="28.0702" fill="white" transform="translate(0.403503 0.526367)"></rect>
                                </clipPath>
                            </defs>
                        </svg>
                    </button>
                </div>
            </form>
            <li><a class="active" href="./catalogo.jsp">CATALOGO</a></li>
            <li id="userIconLi"><a href="./login.jsp"><img src="img/user.jpg" id="userIcon"></a></li>
            <%if(emailUtente == null){%>
                <li><a href="./login.jsp"><img src="img/cart.jpg"></a></li>
            <%}else{%>
                <li><a href="./carrello.jsp"><img src="img/cart.jpg"></a></li>
                <li><a href="UtenteControl?action=logout"><img src="img/logout.jpg"></a></li>
            <%}%>
        </ul>
    </div>
</section>

<script>
    $(document).ready(function(){
        $('.searchInput').keyup(function (){
            var ricerca = $(this).val();
            if(ricerca !== ''){
                $.ajax({
                    url: 'ProdottoControl?action=ricercaSuggerimenti',
                    method: 'POST',
                    data: {ricerca: ricerca},
                    dataType: 'json',
                    success: function (respone) {
                        var suggerimenti = respone.suggerimenti;
                        var suggerimentiHTML = '';
                        for(var i = 0; i < suggerimenti.length; i++) {
                            suggerimentiHTML += '<option value="' + suggerimenti[i] + '">';
                        }
                        $('.suggerimentiProdotti').html(suggerimentiHTML);
                    }
                })
            }else {
                $('.suggerimentiProdotti').html('');
            }
        })
    })
</script>

<script>
    const userIcon = document.getElementById("userIcon");
    const userIconCard = document.createElement("div");
    userIconCard.id = "userIconCard";
    userIconCard.innerHTML = <%if(emailUtente!=null){%> `<a style="color: black; font-family: Montserrat, serif" class="cardActive" href="./ordini.jsp">Ordini</a>` <%}%>;

    const userIconLi = document.getElementById("userIconLi");
    userIconLi.appendChild(userIconCard);

    let isMouseOverIcon = false;
    let isMouseOverCard = false;

    userIcon.addEventListener("mouseover", function() {
        isMouseOverIcon = true;
        showCard();
    });

    userIcon.addEventListener("mouseout", function() {
        isMouseOverIcon = false;
        setTimeout(hideCard, 150); // Ritardo di 200ms prima di nascondere la card
    });

    userIconCard.addEventListener("mouseover", function() {
        isMouseOverCard = true;
        showCard();
    });

    userIconCard.addEventListener("mouseout", function() {
        isMouseOverCard = false;
        setTimeout(hideCard, 150); // Ritardo di 200ms prima di nascondere la card
    });

    function showCard() {
        if (isMouseOverIcon || isMouseOverCard) {
            userIconCard.style.display = "block";
        }
    }

    function hideCard() {
        if (!isMouseOverIcon && !isMouseOverCard) {
            userIconCard.style.display = "none";
        }
    }
</script>

</body>
</html>
