<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.example.space_nerd.Model.PagamentoBean" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    List<PagamentoBean> metodi = (List<PagamentoBean>) request.getAttribute("pagamenti");
    if(metodi == null) {
        response.sendRedirect("./UtenteControl?action=visualizzaMetodi");
        return;
    }
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

<div class="pagamenti-container">
    <h2>I tuoi metodi di pagamento</h2>
    <%
        if(metodi != null && metodi.size()!=0) {
            Iterator<?> it = metodi.iterator();
            while (it.hasNext()) {
                PagamentoBean metodo = (PagamentoBean) it.next();
    %>
    <%
        if(metodo != null) {
    %>
    <div class="metodo-item">
        <%=metodo.getNumeroCarta()%>
        <%=metodo.getScadenza()%>
        <%=metodo.getCcv()%>
        <%=metodo.getTitolare()%>
        <a href="UtenteControl?action=rimuoviMetodo&IdMetodo=<%=metodo.getId()%>">Rimuovi</a>
    </div>

    <%
        }
    %>
    <%
            }
        }
    %>
</div>

<div class="form-container">
    <form action="UtenteControl?action=inserisciMetodo" method="post" class="form">
        <div class="form-title"><span>come vuoi pagare nel tuo space</span></div>
        <div class="title-2"><span>SPACE</span></div>

        <div class="input-container">
            <input class="input-numero" name="numero" type="text" placeholder="Numero carta">
            <span> </span>
        </div>

        <section class="bg-stars">
            <span class="star"></span>
            <span class="star"></span>
            <span class="star"></span>
            <span class="star"></span>
        </section>

        <div class="input-container">
            <input class="input-data" name="data" type="date" placeholder="Data Scadenza">
        </div>

        <div class="input-container">
            <input class="input-ccv" name="ccv" type="text" placeholder="Ccv">
        </div>

        <div class="input-container">
            <input class="input-titolae" name="titolare" type="text" placeholder="Titolare">
        </div>

        <button type="submit" class="submit">
            <span class="sign-text">Conferma</span>
        </button>
    </form>
</div>

</body>
</html>
