<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <title>Space Nerd</title>
    <style>
        .error-container {
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            background-color: #f1f2f4;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h2 {
            color: #d9534f;
            font-family: Montserrat, serif;
        }
        p {
            color: #333;
            font-family: Montserrat, serif;
        }
    </style>
</head>
<body>
<div class="error-container">
    <h2>Errore</h2>
    <p><%= request.getAttribute("error") %></p>
    <p>Torna alla <a href="index.jsp">home</a></p>
</div>
</body>
</html>
