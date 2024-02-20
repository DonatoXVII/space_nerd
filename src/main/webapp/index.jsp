<%@ page import="java.util.List" %>
<%@ page import="com.example.space_nerd.Model.MangaBean" %>
<%@ page import="java.util.Iterator" %>
<%@ page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
    List<MangaBean> bestManga = (List<MangaBean>) request.getAttribute("bestManga");
    if(bestManga == null) {
        response.sendRedirect("./ProdottoControl");
        return;
    }
%>
<%!
    String email = "";
    Boolean tipo = false;
%>
<%
    synchronized (session) {
        session = request.getSession();
        email = (String) session.getAttribute("email");
        tipo = (Boolean) session.getAttribute("tipo");
    }
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="ISO-8859-1">
    <title>Space Nerd</title>
</head>
<body>
<%@include file="navbar.jsp"%>

<h2>Best Manga</h2>
<%
    if(bestManga != null && bestManga.size()!=0) {
        Iterator<?> it = bestManga.iterator();
        while (it.hasNext()) {
            MangaBean manga = (MangaBean) it.next();
%>
<%
            if(manga != null) {
%>
            <%=manga.getDescrizione()%>
<%
            }
%>
<%
            }
    }
%>
</body>
</html>