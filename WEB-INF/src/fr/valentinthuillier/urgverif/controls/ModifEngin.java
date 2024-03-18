package fr.valentinthuillier.urgverif.controls;
import java.io.IOException;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.text.translate.CharSequenceTranslator;

import fr.valentinthuillier.urgverif.model.dao.CompartimentDAO;
import fr.valentinthuillier.urgverif.model.dao.MaterielDAO;
import fr.valentinthuillier.urgverif.model.dao.VehiculeDAO;
import fr.valentinthuillier.urgverif.model.dto.Compartiment;
import fr.valentinthuillier.urgverif.model.dto.Materiel;
import fr.valentinthuillier.urgverif.model.dto.Vehicule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/modifEngin")
public class ModifEngin extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String compartimentStr = req.getParameter("compartiment");
        String libelle = req.getParameter("libelle");
        String quantiteStr = req.getParameter("quantite");
        String immatriculation = req.getParameter("immatriculation");

        if(compartimentStr == null || compartimentStr.isBlank() || libelle == null || libelle.isBlank() || quantiteStr == null || quantiteStr.isBlank() || immatriculation == null || immatriculation.isBlank()) {
            req.setAttribute("erreur", "Veuillez remplir tous les champs");
            req.getRequestDispatcher("/WEB-INF/view/modifEngin.jsp").forward(req, resp);
            return;
        }

        CharSequenceTranslator cst = StringEscapeUtils.ESCAPE_HTML4;
        libelle = cst.translate(libelle);
        immatriculation = cst.translate(immatriculation);

        int quantite = -1;
        int compartimentId = -1;

        try {
            quantite = Integer.parseInt(quantiteStr);
            compartimentId = Integer.parseInt(compartimentStr);
        } catch(Exception e) {
            System.out.println("ModifEngin : parsing error");
            req.setAttribute("erreur", "Erreur de saisie");
            req.getRequestDispatcher("/WEB-INF/view/modifEngin.jsp").forward(req, resp);
            return;
        }

        Vehicule vehicule = new VehiculeDAO().findById(immatriculation);
        Compartiment compartiment = new CompartimentDAO().findById(compartimentId);

        if(vehicule == null) {
            req.setAttribute("erreur", "Véhicule non trouvé");
            req.getRequestDispatcher("/WEB-INF/view/modifEnginForm.jsp").forward(req, resp);
            return;
        }

        if(compartiment == null) {
            req.setAttribute("erreur", "Compartiment non trouvé");
            req.getRequestDispatcher("/WEB-INF/view/modifEngin.jsp").forward(req, resp);
            return;
        }

        if(quantite < 0) {
            req.setAttribute("erreur", "Quantité invalide");
            req.getRequestDispatcher("/WEB-INF/view/modifEngin.jsp").forward(req, resp);
            return;
        }

        if(!vehicule.getTypeEngin().equals(compartiment.getTypeEngin())) {
            req.setAttribute("erreur", "Le compartiment ne correspond pas au type d'engin");
            req.getRequestDispatcher("/WEB-INF/view/modifEngin.jsp").forward(req, resp);
            return;
        }

        if(new MaterielDAO().checkIfIsAlreadyAssignedToVehicule(immatriculation, compartiment, libelle)) {
            req.setAttribute("erreur", "Ce matériel est déjà assigné à ce compartiment");
            req.getRequestDispatcher("/WEB-INF/view/modifEngin.jsp").forward(req, resp);
            return;
        }

        new MaterielDAO().save(new Materiel(libelle, quantite, compartiment, vehicule, true));
        req.setAttribute("vehicule", vehicule);
        req.getRequestDispatcher("/WEB-INF/view/modifEngin.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String immatriculation = req.getParameter("immatriculation");
        if(immatriculation == null || immatriculation.isBlank()) {
            req.getRequestDispatcher("/WEB-INF/view/modifEnginForm.jsp").forward(req, resp);
        }
        CharSequenceTranslator cst = StringEscapeUtils.ESCAPE_HTML4;
        immatriculation = cst.translate(immatriculation);
        
        Vehicule vehicule = new VehiculeDAO().findById(immatriculation);
        if(vehicule == null) {
            req.setAttribute("erreur", "Véhicule non trouvé");
            req.getRequestDispatcher("/WEB-INF/view/modifEnginForm.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("vehicule", vehicule);
        req.getRequestDispatcher("/WEB-INF/view/modifEngin.jsp").forward(req, resp);
    }

}
