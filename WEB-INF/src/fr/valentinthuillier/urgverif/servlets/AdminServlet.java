package fr.valentinthuillier.urgverif.servlets;

import java.io.IOException;

import fr.valentinthuillier.urgverif.model.dao.AgentDAO;
import fr.valentinthuillier.urgverif.model.dto.Agent;
import fr.valentinthuillier.urgverif.model.dto.Gallon;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		RequestDispatcher rd;
		if(session.getAttribute("matricule") == null) {
			rd = req.getRequestDispatcher("/WEB-INF/views/login.jsp");
		} else {
			String matricule = (String) session.getAttribute("matricule");
			AgentDAO dao = new AgentDAO();
			Agent agent = dao.findById(matricule);
			if(agent.getGallon() == Gallon.ADMINISTRATEUR) {
				rd = req.getRequestDispatcher("/WEB-INF/views/admin.jsp");
			} else {
				rd = req.getRequestDispatcher("/WEB-INF/views/home.jsp");
			}
		}
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

}