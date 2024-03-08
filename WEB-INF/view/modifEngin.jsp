<!DOCTYPE html>
<html lang="fr">
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="fr.valentinthuillier.urgverif.model.Immatriculation,fr.valentinthuillier.urgverif.model.dto.*,fr.valentinthuillier.urgverif.model.dao.*,java.util.*" %>

<%
    Vehicule vehicule = (Vehicule) request.getAttribute("vehicule");
%>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modification du <%= vehicule.getTypeEngin() %> (<%= vehicule.getImmatriculation() %>)</title>
</head>
<body>

    <section>



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