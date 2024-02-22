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
<%
    if(emailUtente != null) {
        if(tipoUtente){
            response.sendRedirect("./admin.jsp");
        } else {
            response.sendRedirect("./profilo.jsp");
        }
    }
%>

<div class="form-container">
    <form action="UtenteControl?action=login" method="post" class="form">
        <div class="form-title"><span>entra nel tuo</span></div>
        <div class="title-2"><span>SPACE</span></div>
        <div class="input-container">
            <input class="input-mail" name="email" type="email" placeholder="Email">
            <span> </span>
        </div>

        <section class="bg-stars">
            <span class="star"></span>
            <span class="star"></span>
            <span class="star"></span>
            <span class="star"></span>
        </section>

        <div class="input-container">
            <input class="input-pwd" name="password" type="password" placeholder="Password">
        </div>

        <button type="submit" class="submit">
            <span class="sign-text">Login</span>
        </button>

        <p class="signup-link">
            Non sei ancora registrato?
            <a href="registrati.jsp" class="up">Registrati!</a>
        </p>
    </form>
</div>

<%
    if(result != null) {
%>
<h3><%=result%></h3>
<%
    }
%>

</body>
</html>
