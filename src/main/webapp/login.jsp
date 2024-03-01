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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
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

<div class="form-container-accesso">
    <form action="UtenteControl?action=login" method="post" class="form" name="registrazione" onsubmit="return validate()">
        <div class="form-title"><span>entra nel tuo</span></div>
        <div class="title-2"><span>SPACE</span></div>
        <div class="input-container">
            <input class="input-mail" name="email" type="email" placeholder="Email" required>
            <span> </span>
        </div>

        <section class="bg-stars">
            <span class="star"></span>
            <span class="star"></span>
            <span class="star"></span>
            <span class="star"></span>
        </section>

        <div class="input-container">
            <input class="input-pwd" name="password" type="password" placeholder="Password" required>
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

<script>
    function validate() {
        var email = document.registrazione.email.value;
        var pwd = document.registrazione.password.value;

        if(email !== "" && pwd !== ""){
            $.ajax({
                url: "UtenteControl?action=UtenteRegistrato",
                type: "POST",
                data: { email: email, password: pwd },
                success: function(response) {
                    if (response === "non esiste")
                    {
                        alert("Credenziali errate");
                        return false;
                    }
                },
                error: function() {
                    alert("Si è verificato un errore durante la verifica dell'email");
                    return false;
                }
            });
        }

    }
</script>

</body>
</html>
