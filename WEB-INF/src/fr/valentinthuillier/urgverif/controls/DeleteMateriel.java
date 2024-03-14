package fr.valentinthuillier.urgverif.controls;

import java.io.IOException;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.text.translate.CharSequenceTranslator;

import fr.valentinthuillier.urgverif.model.dao.MaterielDAO;
import fr.valentinthuillier.urgverif.model.dto.Materiel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/deleteMateriel")
public class DeleteMateriel extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idString = req.getParameter("id");
        String immatriculation = req.getParameter("immatriculation");

        if(idString == null || idString.isBlank() || immatriculation == null || immatriculation.isBlank()) {
            req.setAttribute("erreur", "Erreur de saisie");
            req.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(req, resp);
        }

        CharSequenceTranslator cst = StringEscapeUtils.ESCAPE_HTML4;

        immatriculation = cst.translate(immatriculation);
        int id = -1;
        try { id = Integer.parseInt(idString); }
        catch(Exception e) {  }

        Materiel mat = new MaterielDAO().findById(id);
        if(mat == null) {
            req.setAttribute("erreur", "Matériel introuvable !");
            req.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(req, resp);
            return;
        }

        if(!mat.getVehicule().getImmatriculation().equals(immatriculation)) {
            req.setAttribute("erreur", "Matériel introuvable !");
            req.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(req, resp);
            return;
        }

        new MaterielDAO().delete(mat);
        resp.sendRedirect(req.getContextPath() + "/modifEngin?immatriculation=" + immatriculation);

    }

}
