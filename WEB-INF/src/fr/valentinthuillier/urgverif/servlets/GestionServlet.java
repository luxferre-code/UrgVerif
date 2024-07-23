package fr.valentinthuillier.urgverif.servlets;

import java.io.IOException;

import fr.valentinthuillier.urgverif.model.DS;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/gestion")
public class GestionServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!DS.isConfigured()) {
            resp.sendRedirect(req.getContextPath() + "/install");
            return;
        }
        
        if(req.getSession().getAttribute("matricule") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
		req.getRequestDispatcher("/WEB-INF/views/gestion.jsp").forward(req, resp);
	}

}
