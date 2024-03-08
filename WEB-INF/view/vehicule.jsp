<!DOCTYPE html>
<html lang="fr">
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="fr.valentinthuillier.urgverif.model.Immatriculation,fr.valentinthuillier.urgverif.model.dto.*,fr.valentinthuillier.urgverif.model.dao.*,java.util.*" %>
<%
    Vehicule vehicule = (Vehicule) request.getAttribute("vehicule");
    if (vehicule == null) {
        request.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(request, response);
    }
    List<Materiel> matosIndspo = session.getAttribute("matosIndspo") != null ? (List<Materiel>) session.getAttribute("matosIndspo") : new ArrayList<Materiel>();
    Materiel mat = (Materiel) request.getAttribute("materiel");
    if(mat != null) {
        if(matosIndspo.contains(mat)) {
            matosIndspo.remove(mat);
        } else {
            matosIndspo.add(mat);
        }
        session.setAttribute("matosIndspo", matosIndspo);
    }
    System.out.println(matosIndspo);
%>
<head>
    <meta charset="UTF-8"> <!-- TODO: a changer -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= vehicule.getTypeEngin() %> (<%= Immatriculation.translateToOriginal(vehicule.getImmatriculation()) %>) | <%= vehicule.getCentre().getNom() %></title>
</head>
<body>

    <p>Coucou !</p>

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
                <%= materiel.toHTMLLine(!matosIndspo.contains(materiel)) %>
            <% } %>
            </table>
        <% }
    %>

</body>
</html>