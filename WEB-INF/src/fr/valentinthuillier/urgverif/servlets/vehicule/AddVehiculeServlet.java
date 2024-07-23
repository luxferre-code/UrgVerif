package fr.valentinthuillier.urgverif.servlets.vehicule;

import java.io.IOException;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.text.translate.CharSequenceTranslator;

import fr.valentinthuillier.urgverif.model.DS;
import fr.valentinthuillier.urgverif.model.dao.AgentDAO;
import fr.valentinthuillier.urgverif.model.dao.CentreDAO;
import fr.valentinthuillier.urgverif.model.dao.VehiculeDAO;
import fr.valentinthuillier.urgverif.model.dto.Agent;
import fr.valentinthuillier.urgverif.model.dto.Vehicule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/vehicule/add")
public class AddVehiculeServlet extends HttpServlet {
	
	// Allow only POST requests
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if ("POST".equals(req.getMethod())) {
			doPost(req, resp);
		} else {
			resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!DS.isConfigured()) {
            resp.sendRedirect(req.getContextPath() + "/install");
            return;
        }
        String matricule = (String) req.getSession().getAttribute("matricule");
        if(matricule == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
		Agent agent = new AgentDAO().findById(matricule);
		System.out.println(agent.toString());
		if(agent.getGallon().getID() < 9) {
			resp.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		String immatriculation = req.getParameter("immatriculation");
		String type = req.getParameter("type");

		if(immatriculation == null || type == null || immatriculation.isEmpty() || type.isEmpty()) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		CharSequenceTranslator translator = StringEscapeUtils.ESCAPE_HTML4.with(StringEscapeUtils.ESCAPE_XML10);
		immatriculation = translator.translate(immatriculation);
		type = translator.translate(type);

		VehiculeDAO dao = new VehiculeDAO();
		if(dao.findById(immatriculation) != null) {
			resp.sendError(HttpServletResponse.SC_CONFLICT);
			return;
		}

		if(dao.save(new Vehicule(immatriculation, type, new CentreDAO().findById(agent.getIdCentre()))) != null) {
			resp.sendRedirect(req.getContextPath() + "/gestion");
		} else {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

}
