<!DOCTYPE html>
<html lang="fr">
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="fr.valentinthuillier.urgverif.model.Immatriculation,fr.valentinthuillier.urgverif.model.dto.*,fr.valentinthuillier.urgverif.model.dao.*,java.util.*,fr.valentinthuillier.urgverif.controls.AlertBox" %>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ajout d'un engin - UrgVerif</title>
    <link rel="stylesheet" href="css/root.css">
    <link rel="stylesheet" href="css/ajout.css">
</head>
<body>

    <%
    // fr.valentinthuillier.urgverif.controls.AlertBox
        AlertBox.checkError(request, response);
    %>
    
    <section>
        <h1>Ajout d'un engin</h1>
        <p>
            Bienvenue sur la page d'ajout d'engin !<br>
            Vous pouvez ajouter un engin en remplissant le formulaire ci-dessous.
        </p>
    </section>

    <form action="addEngin" method="post">
        <label for="type">Type d'engin: </label>
        <select name="type" id="type">
            <%
                VehiculeDAO vehiculeDAO = new VehiculeDAO();
                List<String> types = vehiculeDAO.findAllTypeEngin();
                for(String s : types) {
                    out.println("<option value=\"" + s + "\">" + s + "</option>");
                }
            %>
        </select>
        <br>
        <label for="immatriculation">Immatriculation: </label>
        <input type="text" id="immatriculation" name="immatriculation" required>
        <br>
        <label for="cis">Centre de secours affecté: </label>
        <select name="cis" id="cis">
            <%
                CentreDAO centreDAO = new CentreDAO();
                List<Centre> centres = centreDAO.findAll();
                for(Centre centre : centres) {
                    out.println("<option value=\"" + centre.getID() + "\">" + centre.getNom() + "</option>");
                }
            %>
        </select><br>
        <label for="initialize">Valeur par défaut: </label>
        <input type="checkbox" name="initialize" id="initialize" value="true"><br>
        <input type="submit" value="Ajouter">
    </form>

    <script>
        // This script checks if the immatriculation is valid
        const immatInput = document.querySelector('form #immatriculation');
        const submitBtn = document.querySelector('form input[type="submit"]');
        submitBtn.setAttribute('disabled', 'disabled');
        immatInput.addEventListener('input', () => {
            const value = immatInput.value.replaceAll(' ', '').replaceAll('-', '').toUpperCase();
            console.log(value);
            if (immatInput.value.length > 0) {
                if (!/^[A-Z]{2}[0-9]{3}[A-Z]{2}$/.test(value)) {
                    submitBtn.setAttribute('disabled', 'disabled');
                } else {
                    console.log('ok');
                    submitBtn.removeAttribute('disabled');
                }
            }
        });

        submitBtn.addEventListener('click', () => {
            immatInput.value = immatInput.value.replaceAll(' ', '').replaceAll('-', '').toUpperCase();
            if (!/^[A-Z]{2}[0-9]{3}[A-Z]{2}$/.test(immatInput.value)) {
                alert('Le numéro d\'immatriculation n\'est pas valide');
                return false;
            } 
        });
    </script>

    <% 
        String erreur = (String) request.getAttribute("erreur");
        if(erreur != null && !erreur.isBlank()) {
            out.println("<script>alert(\"" + erreur + "\")</script>");
        }
    %>

</body>
</html>