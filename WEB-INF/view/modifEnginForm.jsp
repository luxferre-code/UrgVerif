<!DOCTYPE html>
<html lang="fr">
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="fr.valentinthuillier.urgverif.model.Immatriculation,fr.valentinthuillier.urgverif.model.dto.*,fr.valentinthuillier.urgverif.model.dao.*,java.util.*,fr.valentinthuillier.urgverif.controls.AlertBox" %>

<%
    // fr.valentinthuillier.urgverif.controls.AlertBox
    AlertBox.checkError(request, response);
%>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/root.css">
    <title>Modification d'un engin</title>
</head>
<body>

    <form action="modifEngin" method="get">
        <label for="immatriculation">Immatriculation: </label>
        <input type="text" id="immatriculation" name="immatriculation" required>
        <br>
        <input type="submit" value="Modifier">
    </form>

    <script>
        // This script checks if the immatriculation is valid
        const immatInput = document.querySelector('form #immatriculation');
        const submitBtn = document.querySelector('form input[type="submit"]');
        immatInput.addEventListener('input', () => {
            const value = immatInput.value.replaceAll(' ', '').replaceAll('-', '').toUpperCase();
            // Example: fr-941-yq -> FR941YQ
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

</body>
</html>