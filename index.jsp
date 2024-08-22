<%@ page import="fr.valentinthuillier.urgverif.dto.Agent" %>

<%
    Agent agent = (Agent) session.getAttribute("agent");
    if (agent != null) {
        response.sendRedirect("home");
    }
%>

<%@ page pageEncoding="UTF-8" %>

<!doctype html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>UrgVerif - Connexion</title>
</head>
<body>

    <h1>Connexion à UrgVerif</h1>

    <form action="login" method="post">
        <label for="matricule">Matricule</label>
        <input type="text" name="matricule" id="matricule" required>

        <label for="password">Mot de passe</label>
        <input type="password" name="password" id="password" required>

        <button type="submit">Se connecter</button>
    </form>

    <%
        String error = (String) request.getAttribute("error");
        if (error != null) { %>
            <p><%= error %></p>
    <% } %>

</body>
</html>