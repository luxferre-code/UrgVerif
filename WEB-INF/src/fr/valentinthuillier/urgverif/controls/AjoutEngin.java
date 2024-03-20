package fr.valentinthuillier.urgverif.controls;
import java.io.IOException;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.text.translate.CharSequenceTranslator;

import fr.valentinthuillier.urgverif.Log;
import fr.valentinthuillier.urgverif.model.dao.CentreDAO;
import fr.valentinthuillier.urgverif.model.dao.VehiculeDAO;
import fr.valentinthuillier.urgverif.model.dto.Centre;
import fr.valentinthuillier.urgverif.model.dto.Vehicule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/addEngin")
public class AjoutEngin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Log.info("AjoutEngin Servlet active");
        req.getRequestDispatcher("/WEB-INF/view/ajoutEngin.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Log.info("AjoutEngin Servlet active");

        String immatriculation = req.getParameter("immatriculation");
        String typeEngin = req.getParameter("type");
        String nomCentre = req.getParameter("cis");
        String initialize = req.getParameter("initialize");

        if(initialize == null) initialize = "false";

        if(immatriculation == null || immatriculation.isBlank() || typeEngin == null || typeEngin.isBlank() || nomCentre == null || nomCentre.isBlank()) {
            req.setAttribute("erreur", "Veuillez remplir tous les champs");
            req.getRequestDispatcher("/WEB-INF/view/ajoutEngin.jsp").forward(req, resp);
            return;
        }
        CharSequenceTranslator cst = StringEscapeUtils.ESCAPE_HTML4;
        immatriculation = cst.translate(immatriculation);
        typeEngin = cst.translate(typeEngin);
        nomCentre = cst.translate(nomCentre);
        int idCentre = -1;
        boolean initializeBool = false;
        try {
            idCentre = Integer.parseInt(nomCentre);
            initializeBool = Boolean.parseBoolean(initialize);
        }
        catch(Exception e) {
            Log.warning("Erreur lors de la conversion des paramètres");
            req.setAttribute("erreur", "Erreur lors de la conversion des paramètres");
            req.getRequestDispatcher("/WEB-INF/view/ajoutEngin.jsp").forward(req, resp);
            return;
        }

        Log.info("Ajout d'un engin : " + immatriculation + " de type " + typeEngin + " au centre " + idCentre);
        Centre centre = new CentreDAO().findById(idCentre);
        if(centre == null) {
            Log.warning("Centre non trouvé");
            req.setAttribute("erreur", "Centre non trouvé");
            req.getRequestDispatcher("/WEB-INF/view/ajoutEngin.jsp").forward(req, resp);
            return;
        }
        VehiculeDAO dao = new VehiculeDAO();
        if(!dao.findAllTypeEngin().contains(typeEngin)) {
            Log.warning("Type d'engin non trouvé");
            req.setAttribute("erreur", "Type d'engin non trouvé");
            req.getRequestDispatcher("/WEB-INF/view/ajoutEngin.jsp").forward(req, resp);
            return;
        }
        if(dao.findById(immatriculation) != null) {
            Log.warning("Engin déjà existant");
            req.setAttribute("erreur", "Engin déjà existant");
            req.getRequestDispatcher("/WEB-INF/view/ajoutEngin.jsp").forward(req, resp);
            return;
        }
        Vehicule v = new Vehicule(immatriculation, typeEngin, centre);
        dao.save(v);
        Log.info("Engin ajouté, affectation par défaut : " + (initializeBool ? "oui" : "non"));
        if(initializeBool) dao.affect(v);
        req.setAttribute("vehicule", v);
        req.getRequestDispatcher("/WEB-INF/view/modifEngin.jsp").forward(req, resp);
    }

}
