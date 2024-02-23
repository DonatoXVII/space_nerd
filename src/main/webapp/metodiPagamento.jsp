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
    <link href="css/index.css" rel="stylesheet" type="text/css">
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
        <a href="UtenteControl?action=rimuoviMetodo&IdIdMetodo=<%=metodo.getId()%>">Rimuovi</a>
    </div>

    <%
        }
    %>
    <%
            }
        }
    %>
</div>

</body>
</html>
