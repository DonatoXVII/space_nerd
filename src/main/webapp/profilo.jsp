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
    <div class="card red">
        <a href="./modifica.jsp">Modifica il tuo profilo</a>
    </div>
    <div class="card blue">
        <a href="./indirizzi.jsp">I tuoi indirizzi</a>
    </div>
    <div class="card green">
        <a href="./metodiPagamento.jsp">I tuoi metodi di pagamento</a>
    </div>
</div>
</body>
</html>
