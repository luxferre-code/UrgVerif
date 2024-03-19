package fr.valentinthuillier.urgverif.controls;

import java.io.IOException;
import java.util.List;

import fr.valentinthuillier.urgverif.model.dao.MaterielDAO;
import fr.valentinthuillier.urgverif.model.dto.Materiel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/endVerif")
public class EndVerif extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        if(session.getAttribute("matosIndspo") == null) {
            req.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(req, resp);
            return;
        }
        @SuppressWarnings("unchecked")
        List<Materiel> matosIndspo = (List<Materiel>) session.getAttribute("matosIndspo");
        if(matosIndspo == null || matosIndspo.size() == 0) {
            req.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(req, resp);
            return;
        }
        MaterielDAO dao = new MaterielDAO();
        for(Materiel m : matosIndspo) {
            m.setValide(false);
            if(dao.update(m) == null) {
                req.setAttribute("erreur", "Erreur lors de la mise à jour");
                req.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(req, resp);
                return;
            }
        }
        session.removeAttribute("matosIndspo");
        req.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(req, resp);
    }

}