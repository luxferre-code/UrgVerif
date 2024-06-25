<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String matricule = (String) request.getAttribute("matricule");
    if (matricule != null) {
        response.sendRedirect("home");
        return;
    }
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>UrgVerif - Connexion</title>
    <link rel="stylesheet" href="css/login.css">
</head>
<body>

    <div class="container">
        <h1>UrgVerif</h1>
        <p>Connectez-vous pour accéder à votre espace</p>
    </div>
    
    <form method="post" action="login">
        <label for="matricule"><b>Matricule</b></label>
        <input type="text" placeholder="Entrez votre matricule" name="matricule" required>

        <label for="password"><b>Mot de passe</b></label>
        <input type="password" placeholder="Entrez votre mot de passe" name="password" required>

        <button type="submit" class="loginbtn">Connexion</button>
    </form>

    <div class="container">
        <span class="psw">Mot de passe oublié ? <a href="forgot-password">Cliquez ici</a></span>
    </div>

</body>
</html>