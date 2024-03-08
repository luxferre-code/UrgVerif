<!DOCTYPE html>
<html lang="fr">
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="fr.valentinthuillier.urgverif.model.Immatriculation,fr.valentinthuillier.urgverif.model.dto.*,fr.valentinthuillier.urgverif.model.dao.*,java.util.*" %>

<%
    Vehicule vehicule = (Vehicule) request.getAttribute("vehicule");
    if(vehicule == null) {
        response.sendRedirect("home");
        return;
    }
%>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modification du <%= vehicule.getTypeEngin() %> (<%= vehicule.getImmatriculation() %>)</title>
</head>
<body>

    <section>

        <h2>Modification du <%= vehicule.getTypeEngin() %> (<%= vehicule.getImmatriculation() %>)</h2>

        <form action="modifEngin" method="post">
            <label for="compartiment">Compartiment: </label>
            <select name="compartiment" id="compartiment" required>
                <%
                    for(Compartiment compartiment : new CompartimentDAO().findAllByVehicule(vehicule)) {
                        out.println("<option value=\"" + compartiment.getID() + "\">" + compartiment.getNom() + "</option>");
                } %>
            </select>
            <label for="libelle">Libellé: </label>
            <input type="text" name="libelle" id="libelle" required>
            <label for="quantite">Quantité: </label>
            <input type="number" name="quantite" id="quantite" min="1" required>
            <input type="hidden" name="immatriculation" value="<%= vehicule.getImmatriculation() %>">
            <input type="submit" value="Ajouter">
        </form>

    </section>
    
    <section>
        <h2>Equipement(s) présent(s):</h2>
        <%
            Map<Compartiment, List<Materiel>> matos = new MaterielDAO().findByVehicule(vehicule);
            if(matos.isEmpty()) { %>
                <p>Aucun équipement n'est présent dans ce véhicule.</p>
            <% } else {
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
                <% }
            }
        %>
    </section>

</body>
</html>