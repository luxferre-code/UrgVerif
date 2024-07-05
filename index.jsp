<%
    if(session.getAttribute("matricule") != null) {
        response.sendRedirect("home");
    } else {
        response.sendRedirect("login");
    }
%>