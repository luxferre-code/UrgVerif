<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="fr.valentinthuillier.urgverif.model.dao.AgentDAO" %>
<%@ page import="fr.valentinthuillier.urgverif.model.dao.VehiculeDAO" %>
<%@ page import="fr.valentinthuillier.urgverif.model.dto.Vehicule" %>
<%@ page import="fr.valentinthuillier.urgverif.model.dto.Agent" %>
<%@ page import="fr.valentinthuillier.urgverif.model.dto.Gallon" %>
<%@ page import="java.util.List" %>

<%
    if (session.getAttribute("matricule") == null) {
        response.sendRedirect("/login");
    }
    AgentDAO agentDAO = new AgentDAO();
    Agent agent = agentDAO.findById((String) session.getAttribute("matricule"));
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>UrgVerif - Home</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100">

    <!-- Header -->
    <header class="bg-white shadow">
        <div class="max-w-7xl mx-auto py-6 px-4 sm:px-6 lg:px-8 flex justify-between items-center">
            <h1 class="text-3xl font-bold text-gray-900">
                UrgVerif
            </h1>
            <nav class="flex space-x-4">
                <a href="/" class="text-gray-900 hover:text-gray-700 px-3 py-2 rounded-md text-sm font-medium">Accueil</a>
                <a href="/verif" class="text-gray-900 hover:text-gray-700 px-3 py-2 rounded-md text-sm font-medium">Vérification</a>
                <% if (agent.getGallon().getID() >= 5) { %>
                    <a href="/recap" class="text-gray-900 hover:text-gray-700 px-3 py-2 rounded-md text-sm font-medium">Récapitulatif</a> <!-- Seulement gallon >= Sergent -->
                <% } %>
                <% if (agent.getGallon().getID() >= 9) { %>
                    <a href="/gestion" class="text-gray-900 hover:text-gray-700 px-3 py-2 rounded-md text-sm font-medium">Gestion</a> <!-- Seulement gallon >= Lieutenant -->
                <% } %>
                <% if (agent.getGallon() == Gallon.ADMINISTRATEUR) { %>
                    <a href="/admin" class="text-gray-900 hover:text-gray-700 px-3 py-2 rounded-md text-sm font-medium">Administration</a> <!-- Seulement Administrateur du site -->
                <% } %>
            </nav>
        </div>
    </header>

    <!-- Hero Section -->
    <section class="bg-blue-600">
        <div class="max-w-7xl mx-auto py-16 px-4 sm:px-6 lg:px-8 text-center">
            <h2 class="text-4xl font-bold text-white">
                Bienvenue sur UrgVerif
            </h2>
            <p class="mt-4 text-lg text-blue-100">
                Votre plateforme de vérification de votre de secours.
            </p>
            <div class="mt-8">
                <a href="/verif" class="px-8 py-3 bg-white text-blue-600 font-semibold rounded-md hover:bg-blue-100">
                    Commencez Maintenant
                </a>
            </div>
        </div>
    </section>

    <section id="all-engins">
        <h2>Les engins disponible</h2>

        <%
            VehiculeDAO vehiculeDAO = new VehiculeDAO();
            List<Vehicule> vehicules = vehiculeDAO.findByCentre(agent.getIdCentre());


            for (Vehicule vehicule : vehicules) { %>
                <%= vehicule.toHTML() %>
            <% } 
        %>

    </section>


    <!-- Footer -->
    <footer class="bg-white shadow">
        <div class="max-w-7xl mx-auto py-4 px-4 sm:px-6 lg:px-8 text-center">
            <p class="text-gray-600">
                &copy; 2024 UrgVerif. Tous droits réservés.
            </p>
        </div>
    </footer>

</body>
</html>
