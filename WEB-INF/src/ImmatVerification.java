import java.io.IOException;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.text.translate.CharSequenceTranslator;

import fr.valentinthuillier.urgverif.model.dao.VehiculeDAO;
import fr.valentinthuillier.urgverif.model.dto.Vehicule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/startVerif")
public class ImmatVerification extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String immatriculation = req.getParameter("immat");
        if(immatriculation == null || immatriculation.isBlank()) {
            req.getRequestDispatcher("/WEB-INF/view/index.jsp?error=1").forward(req, resp);
        }
        CharSequenceTranslator cst = StringEscapeUtils.ESCAPE_HTML4;
        immatriculation = cst.translate(immatriculation);
        VehiculeDAO dao = new VehiculeDAO();
        Vehicule vehicule = dao.findById(immatriculation);
        if(vehicule == null) {
            req.getRequestDispatcher("/WEB-INF/view/index.jsp?error=2").forward(req, resp);
        } else {
            req.setAttribute("vehicule", vehicule);
            req.getRequestDispatcher("/WEB-INF/view/vehicule.jsp").forward(req, resp);
        }
    }

}
