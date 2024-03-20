<!DOCTYPE html>
<html lang="fr">
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="fr.valentinthuillier.urgverif.model.Immatriculation,fr.valentinthuillier.urgverif.model.dto.*,fr.valentinthuillier.urgverif.model.dao.*,java.util.*,fr.valentinthuillier.urgverif.controls.AlertBox" %>

<%
    // fr.valentinthuillier.urgverif.controls.AlertBox
    AlertBox.checkError(request, response);
%>

<%
    Vehicule vehicule = (Vehicule) request.getAttribute("vehicule");
    if(vehicule == null) {
        response.sendRedirect("home");
        return;
    }
%>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/root.css">
    <link rel="stylesheet" href="css/modif.css">
    <title>Modification du <%= vehicule.getTypeEngin() %> (<%= vehicule.getImmatriculation() %>)</title>
</head>
<body>
    <%
        long start = System.currentTimeMillis();
    %>
    <h1>Modification du <%= vehicule.getTypeEngin() %> (<%= vehicule.getImmatriculation() %>)</h1>

    <p>
        Bienvenur sur la page de modification du <%= vehicule.getTypeEngin() %> immatriculé <%= vehicule.getImmatriculation() %> !<br>
        Vous pouvez modifier les informations relatives à cet engin ci-dessous. <br>
        Toute modification est instantanément enregistrée dans la base de données.
    </p>

    <section>
        <%
            Map<Compartiment, List<Materiel>> matos = new MaterielDAO().findByVehicule(vehicule);
            List<Compartiment> comparts = new CompartimentDAO().findAllByVehicule(vehicule);
            // Tri des compartiments par l'id du compartiment
            comparts.sort((c1, c2) -> c1.getID() - c2.getID());
            for(Compartiment c : comparts){ 
                out.println("<div id=\"" + c.getNom().replaceAll(" ", "") + "\">"); %>
                <h2><%= c.getNom() %></h2>
                <% System.out.println(c.getNom()); %>
                <% if(matos.get(c) != null && !matos.get(c).isEmpty()) { %>
                    <table>
                        <tr>
                            <th>Matériel</th>
                            <th>Quantité</th>
                            <th>Supprimer ?</th>
                        </tr>
                    <% for(Materiel materiel : matos.get(c)) { %>
                        <%= materiel.toHTMLModifLine() %>
                    <% } %>
                    </table>
                <% } %>

                    <form action="modifEngin" method="post">
                        <% out.println("<input type=\"hidden\" name=\"compartiment\" value=\"" + c.getID() + "\" required>"); %>
                        <label for="libelle">Libellé: </label>
                        <input type="text" name="libelle" id="libelle" required>
                        <label for="quantite">Quantité: </label>
                        <input type="number" name="quantite" id="quantite" min="1" required>
                        <input type="hidden" name="immatriculation" value="<%= vehicule.getImmatriculation() %>">
                        <input type="submit" value="Ajouter">
                    </form>
                </div>           
            <% } %>
    </section>

    <form action="modifEngin" method="post">
        <input type="hidden" name="immatriculation" value="<%= vehicule.getImmatriculation() %>">
        <label for="compartiment">Ajout d'un compartiment: </label>
        <input type="text" name="compartiment" id="compartiment" required>
        <input type="submit" value="Ajouter">
    </form>

    <script>

        const allBtnDelete = document.querySelectorAll('section table tr td .delete');
        allBtnDelete.forEach(btn => {
            btn.addEventListener('click', () => {
                if(confirm('Voulez-vous vraiment supprimer ce matériel?')) {
                    // Get id from value on the button
                    const id = btn.value;
                    <% 
                    out.println("const immatriculation = \"" + vehicule.getImmatriculation() + "\";");
                    out.println("window.location.href = `deleteMateriel?id=${id}&immatriculation=${immatriculation}`;");
                    %>
                }
            });
        });

    </script>

    <%
    long end = System.currentTimeMillis();
    out.println("<p>Page générée en " + (end - start) + "ms</p>");
    %>

</body>
</html>