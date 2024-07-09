<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>UrgVerif - Install page</title>
</head>
<body>

    <h1>Installation d'UrgVerif !</h1>

    <p>
        Bienvenue sur la page d'installation d'UrgVerif !<br>
        Veuillez remplir les champs ci-dessous pour installer l'application.
    </p>
    
    <form action="install" method="post">
        <div id="dbhost">
            <label for="hostip">IP de la base de données</label>
            <input type="text" name="hostip" value="localhost" required>

            <label for="port">Port de la base de données</label>
            <input type="number" name="port" value="5432" required>

            <label for="dbname">Nom de la base de données</label>
            <input type="text" name="dbname" required>

            <label for="dbuser">Nom d'utilisateur de la base de données</label>
            <input type="text" name="dbuser" required>

            <label for="dbpassword">Mot de passe de la base de données</label>
            <input type="password" name="dbpassword" required>
        </div>

        <div id="center">
            <label for="center">Nom du centre</label>
            <input type="text" name="center" required>

            <label for="address">Adresse du centre</label>
            <input type="text" name="address" required>

            <label for="phone">Téléphone du centre</label>
            <input type="text" name="phone" required>
        </div>

        <div id="admin">
            <label for="adminnom">Nom de l'adiministrateur</label>
            <input type="text" name="adminnom" required>

            <label for="adminprenom">Prénom de l'administrateur</label>
            <input type="text" name="adminprenom" required>

            <label for="adminmatricule">Matricule de l'administrateur</label>
            <input type="text" name="adminmatricule" required>

            <label for="adminemail">Email de l'administrateur</label>
            <input type="email" name="adminemail" required>

            <label for="adminpassword">Mot de passe de l'administrateur</label>
            <input type="password" name="adminpassword" required>

            <label for="adminconfirmpassword">Confirmer le mot de passe de l'administrateur</label>
            <input type="password" name="adminconfirmpassword" required>
        </div>

        <input type="submit" value="Installer">
    </form>

</body>
</html>