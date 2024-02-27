<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="charset=UTF-8">
    <title>Space Nerd</title>
    <link href="css/profilo.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@include file="navbar.jsp"%>

<div class="cards">
    <a href="./modifica.jsp"><div class="card red"><p>MODIFICA IL TUO PROFILO</p></div></a>
    <a href="./indirizzi.jsp"><div class="card blue"><p>INDIRIZZI DI SPEDIZIONE</p></div></a>
    <a href="./metodiPagamento.jsp"><div class="card green"><p>METODI DI PAGAMENTO</p></div></a>
</div>

<div class="view-profilo">
    <div class="form-title"><span>il tuo</span></div>
    <div class="title-2"><span>SPACE</span></div>
    <div class="parametri">
        <label><%=emailUtente%></label><br>
        <section class="bg-stars">
            <span class="star"></span>
            <span class="star"></span>
            <span class="star"></span>
            <span class="star"></span>
        </section>
        <label>nome</label><br>
        <label>cognome</label><br>
    </div>
</div>

</body>
</html>
