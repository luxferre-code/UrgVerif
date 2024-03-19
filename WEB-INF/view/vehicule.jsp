<!DOCTYPE html>
<html lang="fr">
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="fr.valentinthuillier.urgverif.model.Immatriculation,fr.valentinthuillier.urgverif.model.dto.*,fr.valentinthuillier.urgverif.model.dao.*,java.util.*,fr.valentinthuillier.urgverif.controls.AlertBox" %>

<%
    AlertBox.checkError(request, response);
%>

<%
    Vehicule vehicule = (Vehicule) request.getAttribute("vehicule");
    if (vehicule == null) {
        request.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(request, response);
    }
%>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/verif.css">
    <title><%= vehicule.getTypeEngin() %> (<%= Immatriculation.translateToOriginal(vehicule.getImmatriculation()) %>) | <%= vehicule.getCentre().getNom() %></title>
</head>
<body>
    <%
        long start = System.currentTimeMillis();
    %>

    <h1><%= vehicule.getTypeEngin() %> (<%= Immatriculation.translateToOriginal(vehicule.getImmatriculation()) %>) | <%= vehicule.getCentre().getNom() %></h1>

    <p id="pres">
        Vous êtes sur la page de vérification du véhicule <%= vehicule.getTypeEngin() %> (<%= Immatriculation.translateToOriginal(vehicule.getImmatriculation()) %>) du centre <%= vehicule.getCentre().getNom() %>.
        Si un matériel est manquant ou en mauvais état, vous avez juste à cliquer sur la ligne correspondante pour le signaler. <br>
        Le matériel sera alors enregistré comme étant manquant ou en mauvais état. <br>
        A l'inverse, si il est de nouveau disponible ou en bon état, vous pouvez cliquer sur la ligne correspondante pour le réactiver. <br>
        En cas de problème, contacter le chef de centre ou envoyé un mail en <a href="mailto:valentin.thuillier@luxferre-code.fr">cliquant ici</a>.
    </p>

    <%
        Map<Compartiment, List<Materiel>> matos = new MaterielDAO().findByVehicule(vehicule);
        for(Compartiment compartiment : matos.keySet()) { %>

            <h2><%= compartiment.getNom() %></h2>

            <table>
                <tr>
                    <th>Matériel</th>
                    <th>Quantité</th>
                </tr>

            <% for(Materiel materiel : matos.get(compartiment)) { %>
                <%= materiel.toHTMLLine(true) %>
            <% } %>
            </table>
        <% } %>

    <form action="endVerif" method="post">
        <label for="matricule">Votre matricule: </label>
        <input type="text" name="matricule" id="matricule">
        <% out.println("<input type=\"hidden\" name=\"immatriculation\" value=\"" + vehicule.getImmatriculation() + "\">"); %>
        <input type="submit" value="Valider">
    </form>

    <%
        long end = System.currentTimeMillis();
        out.println("<p>Page générée en " + (end - start) + "ms</p>");
    %>

</body>
</html>