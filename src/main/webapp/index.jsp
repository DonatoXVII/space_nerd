<%@ page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%!
    String email = "";
    String result = "";
%>
<%
    synchronized (session) {
        session = request.getSession();
        email = (String) session.getAttribute("email");
    }
    result = (String) request.getAttribute("result");
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="ISO-8859-1">
    <title>Space Nerd</title>
</head>
<body>
    <form action="UtenteControl?action=login" method="post">
        Email : <input type="email" name="email" placeholder="Email">
        Password : <input type="password" name="password" placeholder="Password">
        <%
            if(result != null) {
        %>
        <h3><%=result%></h3>
        <%
            }
        %>
        <input type="submit">
    </form>
</body>
</html>