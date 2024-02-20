<%@ page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%!
    String email = "";
    String password ="";
    boolean tipo = false;
%>
<%
    synchronized (session) {
        session = request.getSession();
        if(session.getAttribute("email") != null) {
            email = (String) session.getAttribute("email");
            password = (String) session.getAttribute("password");
            tipo = (Boolean) session.getAttribute("tipo");
        }
    }
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="ISO-8859-1">
    <title>Space Nerd</title>
    <link href="css/navbar.css" rel="stylesheet" type="text/css">
</head>
<body>
<nav>
    <div class="logo">
        <a href="./index.jsp">
            <img src="img/logo.jpg" alt="Logo">
        </a>
    </div>
    <div class="profilo">
        <a href="accesso.jsp">
            <img src="img/profilo.jpg" alt="Profilo">
        </a>
    </div>
    <%
        if(email != null){
    %>
    <div class="logout">
        <a href="UtenteControl?action=logout">
            <img src="img/logout.png" alt="Logout">
        </a>
    </div>
    <%
        }
    %>
</nav>
</body>
</html>
