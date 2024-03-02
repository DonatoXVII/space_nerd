<%@ page import="java.util.List" %>
<%@ page import="com.example.space_nerd.model.OrdineBean" %>
<%@ page import="java.util.Iterator" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    List<OrdineBean> ordini = (List<OrdineBean>) request.getAttribute("ordini");
    if(ordini == null) {
        response.sendRedirect("./OrdiniControl?action=visualizzaOrdini");
        return;
    }
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="charset=UTF-8">
    <title>Space Nerd</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <link href="css/utility.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@include file="navbar.jsp"%>

<div class="ordini-container">
    <h1>I tuoi ordini</h1>
    <%
        if(ordini != null && ordini.size()!=0) {
            Iterator<?> it = ordini.iterator();
            while (it.hasNext()) {
                OrdineBean ordine = (OrdineBean) it.next();
    %>
    <%
        if(ordine != null) {
    %>

    <div
            class="flex flex-col items-center bg-white w-72 h-auto pt-5 pb-7 border border-gray-200 rounded-lg space-y-8"
    >
        <section class="flex flex-col text-center space-y-1">
            <h2 class="text-2xl font-bold tracking-tight text-gray-900">
                Ordine
            </h2>
            <p class="text-slate-500 text-sm">DATA ORDINE</p>
        </section>
        <section class="space-y-2">
            <div class="flex gap-2">
                <svg
                        fill="currentColor"
                        viewBox="0 0 20 20"
                        class="w-5 h-5 text-blue-500"
                        xmlns="http://www.w3.org/2000/svg"
                >
                    <path
                            clip-rule="evenodd"
                            d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z"
                            fill-rule="evenodd"
                    ></path>
                </svg>
                <span class="text-slate-500 text-sm"><%=ordine.getFattura()%></span>
            </div>
            <div class="flex gap-2">
                <svg
                        fill="currentColor"
                        viewBox="0 0 20 20"
                        class="w-5 h-5 text-blue-500"
                        xmlns="http://www.w3.org/2000/svg"
                >
                    <path
                            clip-rule="evenodd"
                            d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z"
                            fill-rule="evenodd"
                    ></path>
                </svg>
                <span class="text-slate-500 text-sm"><%=ordine.getPrezzo()%></span>
            </div>
        </section>
        <section class="flex w-full flex-col space-y-2 px-9">
            <a href="OrdiniControl?action=visualizzaDettagliOrdine&IdOrdine=<%=ordine.getId()%>"><button
                    class="py-3 font-medium tracking-wide capitalize transition-colors duration-300 transform bg-gray-100 rounded-md hover:bg-gray-200 text-sm text-gray-600"
            >
                visualizza Dettagli
            </button></a>
            <button
                    class="py-3 text-sm font-medium tracking-wide text-white capitalize transition-colors duration-300 transform bg-blue-500 rounded-md hover:bg-blue-600"
            >
                Scarica fattura
            </button>
        </section>
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
