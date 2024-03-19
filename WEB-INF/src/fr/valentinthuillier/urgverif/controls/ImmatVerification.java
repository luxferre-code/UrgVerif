package fr.valentinthuillier.urgverif.controls;
import java.io.IOException;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.text.translate.CharSequenceTranslator;

import fr.valentinthuillier.urgverif.model.dao.MaterielDAO;
import fr.valentinthuillier.urgverif.model.dao.VehiculeDAO;
import fr.valentinthuillier.urgverif.model.dto.Materiel;
import fr.valentinthuillier.urgverif.model.dto.Vehicule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/startVerif")
public class ImmatVerification extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idMateriel = req.getParameter("idMateriel");
        if(idMateriel == null || idMateriel.isBlank()) {
            req.setAttribute("erreur", "Identifiant vide !");
            req.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(req, resp);
        }
        int id = -1;
        try { id = Integer.parseInt(idMateriel); }
        catch(Exception e) {}
        if(id != -1) {
            MaterielDAO matDao = new MaterielDAO();
            Materiel mat = matDao.findById(id);
            if(mat == null) {
                req.setAttribute("erreur", "Materiel introuvable");
                req.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(req, resp);
            } else {
                mat.setValide(!mat.getValide());
                req.setAttribute("vehicule", mat.getVehicule());
                matDao.update(mat);
                req.getRequestDispatcher("/WEB-INF/view/vehicule.jsp").forward(req, resp);
            }
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String immatriculation = req.getParameter("immat");
        if(immatriculation == null || immatriculation.isBlank()) {
            req.setAttribute("erreur", "Immatriculation vide !");
            req.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(req, resp);
        }
        CharSequenceTranslator cst = StringEscapeUtils.ESCAPE_HTML4;
        immatriculation = cst.translate(immatriculation);
        VehiculeDAO dao = new VehiculeDAO();
        Vehicule vehicule = dao.findById(immatriculation);
        if(vehicule == null) {
            req.setAttribute("erreur", "Véhicule introuvable");
            req.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(req, resp);
        } else {
            req.setAttribute("vehicule", vehicule);
            req.getRequestDispatcher("/WEB-INF/view/vehicule.jsp").forward(req, resp);
        }
    }

}
