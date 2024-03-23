package fr.valentinthuillier.urgverif.controls;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.text.translate.CharSequenceTranslator;

import fr.valentinthuillier.urgverif.Log;
import fr.valentinthuillier.urgverif.model.dao.MaterielDAO;
import fr.valentinthuillier.urgverif.model.dto.Materiel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/endVerif")
public class EndVerif extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String immatriculation = req.getParameter("immatriculation");
        String updateMatos = req.getParameter("matos");

        if(immatriculation == null || immatriculation.isBlank() || updateMatos == null) {
            Log.error("Erreur lors de la récupération des paramètres");
            req.setAttribute("erreur", "Veuillez remplir tous les champs");
            req.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(req, resp);
            return;
        }

        CharSequenceTranslator cst = StringEscapeUtils.ESCAPE_HTML4;
        immatriculation = cst.translate(immatriculation);
        updateMatos = cst.translate(updateMatos);
        List<Integer> idMatos = new ArrayList<>();

        try {
            for(String s : updateMatos.split(","))  {
                idMatos.add(Integer.parseInt(s));
            }
        } catch(Exception e) {
            Log.error("Erreur lors de la conversion des paramètres (" + e.getMessage() + ") " + updateMatos + " immatriculation: " + immatriculation);
            req.setAttribute("erreur", "Erreur lors de la conversion des paramètres (" + e.getMessage() + ")");
            req.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(req, resp);
            return;
        }

        List<Materiel> matos = new ArrayList<>();
        MaterielDAO dao = new MaterielDAO();
        for(int i : idMatos) {
            Materiel m = dao.findById(i);
            if(m != null) {
                m.setValide(!m.getValide());
                matos.add(m);
            } else {
                Log.warning("Materiel introuvable : " + i);
                req.setAttribute("erreur", "Materiel introuvable : " + i);
                req.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(req, resp);
                return;
            }
        }

        if(!dao.updateAll(matos)) {
            Log.error("Erreur lors de la mise à jour du materiel");
            req.setAttribute("erreur", "Erreur lors de la mise à jour du materiel");
            req.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(req, resp);
            return;
        }

        Log.info("Mise à jour de la vérification de l'immatriculation " + immatriculation + " : " + updateMatos);
        req.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(req, resp);
        return;
    }

}