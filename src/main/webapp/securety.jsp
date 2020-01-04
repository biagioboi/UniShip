<%@ page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<%
    String controllo = (String) session.getAttribute("login");

    if (controllo == null || controllo.equals("no")) {

        response.sendRedirect("signin.html");
        return;
    }
%>